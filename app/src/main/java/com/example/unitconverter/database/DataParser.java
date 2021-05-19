package com.example.unitconverter.database;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import androidx.room.Room;

import com.example.unitconverter.database.DAOs.UnitDao;
import com.example.unitconverter.database.model.DimensionVector;
import com.example.unitconverter.database.model.Unit;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataParser {

    private static String file_url = "https://raw.githubusercontent.com/qudt/qudt-public-repo/master/vocab/unit/VOCAB_QUDT-UNITS-ALL-v2.1.ttl";

    public void SeedDatabase(Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "database-name").build();
        UnitDao unitDao = db.unitDao();
        if(unitDao.unitCount() == 0){
            new DownloadFileFromURL().doInBackground(db);
        }
    }

    /**
     * Background Async Task to download file
     */
    class DownloadFileFromURL extends AsyncTask<AppDatabase, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(AppDatabase... db) {
            int count;
            try {
                URL url = new URL(file_url);
                URLConnection connection = url.openConnection();
                connection.connect();

                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                byte[] contents = new byte[1024];

                int bytesRead = 0;
                String strFileContents = "";
                while ((bytesRead = input.read(contents)) != -1) {
                    strFileContents += new String(contents, 0, bytesRead);
                }

                String[] contentbyLine = strFileContents.split("\n");

                List<Unit> units = new ArrayList<Unit>();
                Unit currentUnit = new Unit();
                Boolean skippingInitial = true;
                Boolean skipTillNextDot = false;
                Boolean isInDescription = false;
                int currentLineOfUnit = 0;

                for (String line : contentbyLine) {
                    if (skippingInitial && line != ".") {
                        continue;
                    } else {
                        skippingInitial = false;
                    }

                    //Skip empty lines
                    if (line.trim() == "") {
                        currentLineOfUnit++;
                        continue;
                    }

                    //Dot indicates new element
                    if (line == ".") {
                        if (!skipTillNextDot) units.add(currentUnit);
                        skipTillNextDot = false;
                        isInDescription = false;
                        currentLineOfUnit = 0;
                        continue;
                    }

                    if (skipTillNextDot) continue;

                    //Is new element a unit or not
                    if (currentLineOfUnit == 0 && !line.startsWith("unit:")) {
                        skipTillNextDot = true;
                        continue;
                    } else if (currentLineOfUnit == 0 && line.startsWith("unit:")) {
                        currentUnit = new Unit();
                        currentUnit.systemName = line.substring(5);
                        currentLineOfUnit++;
                        continue;
                    }

                    String[] splitLine = line.split(" ");

                    //Hopefully this means the description is over
                    if (line.startsWith("  ")) {
                        isInDescription = false;
                    }

                    //Descriptions can have new lines, so we need a mechanism to deal with it
                    if (isInDescription) {
                        currentUnit.description += " " + line;
                        currentLineOfUnit++;
                        continue;
                    }

                    //Read the element variable
                    switch (splitLine[2]) {
                        case "qudt:conversionMultiplier":
                            currentUnit.conversionMultiplier = splitLine[3].length() > 15 ? Double.parseDouble(splitLine[3].substring(0, 16)) : Double.parseDouble(splitLine[3]);
                            break;
                        case "qudt:conversionOffset":
                            currentUnit.conversionOffset = splitLine[3].length() > 15 ? Double.parseDouble(splitLine[3].substring(0, 16)) : Double.parseDouble(splitLine[3]);
                            break;
                        case "qudt:hasDimensionVector":
                            currentUnit.DimensionVector = DimensionVector.parse(splitLine[3].substring(5));
                            break;
                        case "qudt:symbol":
                            currentUnit.symbol = splitLine[3].replace("\"", "");
                            break;
                        case "rdfs:label":
                            if (line.contains("@en-us")) break;
                            currentUnit.unitName = line.replace("  rdfs:label \"", "").replace("\"@en ;", "");
                            break;
                        case "dcterms:description":
                            currentUnit.description = line.replace("  dcterms:description ", "");
                            isInDescription = true;
                            break;
                        case "qudt:plainTextDescription":
                            currentUnit.description = line.replace("  qudt:plainTextDescription ", "");
                            break;
                        case "qudt:hasQuantityKind":
                            currentUnit.quantityKinds.add(splitLine[3].replace("quantitykind:", ""));
                            break;
                    }

                    currentLineOfUnit++;

                }
                input.close();

                units = units.stream().filter(u -> !u.getQuantityKinds().contains("Currency") && u.getSystemName().length() != 0).collect(Collectors.toList());

                db[0].unitDao().insertAll(Arrays.copyOf(units.toArray(), units.size(), Unit[].class));

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {

        }

    }
}
