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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataParser {

    private static String file_url = "https://raw.githubusercontent.com/qudt/qudt-public-repo/master/vocab/unit/VOCAB_QUDT-UNITS-ALL-v2.1.ttl";

    public void SeedDatabase(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);

        Log.i("DB", "Before do in background");
            new DownloadFileFromURL().execute(db);
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
            Log.i("DB", "Start do in background");
            try {
                UnitDao unitDao = db[0].unitDao();
                if(unitDao.unitCount() != 0){
                    Log.i("DB", "Unit table not empty");
                    return null;
                }
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
                Log.i("DB", "Lines in file: " + contentbyLine.length);
                List<Unit> units = new ArrayList<Unit>();
                Unit currentUnit = new Unit();
                Boolean skippingInitial = true;
                Boolean skipTillNextDot = false;
                Boolean isInDescription = false;
                int currentLineOfUnit = 0;

                for (String line : contentbyLine) {
                    if (skippingInitial && !line.equals(".")) {
                        continue;
                    } else {
                        skippingInitial = false;
                    }

                    //Skip empty lines
                    if (line.trim().equals("")) {
                        currentLineOfUnit++;
                        continue;
                    }

                    //Dot indicates new element
                    if (line.equals(".")) {
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
                        currentUnit.setQuantityKinds("");
                        currentUnit.setSystemName(line.substring(5));
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
                        currentUnit.setDescription(currentUnit.getDescription() + " " + line);
                        currentLineOfUnit++;
                        continue;
                    }

                    //Read the element variable
                    switch (splitLine[2]) {
                        case "qudt:conversionMultiplier":
                            currentUnit.setConversionMultiplier(splitLine[3].length() > 15 ? Double.parseDouble(splitLine[3].substring(0, 16)) : Double.parseDouble(splitLine[3]));
                            break;
                        case "qudt:conversionOffset":
                            currentUnit.setConversionOffset(splitLine[3].length() > 15 ? Double.parseDouble(splitLine[3].substring(0, 16)) : Double.parseDouble(splitLine[3]));
                            break;
                        case "qudt:hasDimensionVector":
                            currentUnit.setDimensionVector(DimensionVector.parse(splitLine[3].substring(5)));
                            break;
                        case "qudt:symbol":
                            currentUnit.setSymbol(splitLine[3].replace("\"", ""));
                            break;
                        case "rdfs:label":
                            if (line.contains("@en-us")) break;
                            currentUnit.setUnitName(line.replace("  rdfs:label \"", "").replace("\"@en ;", ""));
                            break;
                        case "dcterms:description":
                            currentUnit.setDescription(line.replace("  dcterms:description ", ""));
                            isInDescription = true;
                            break;
                        case "qudt:plainTextDescription":
                            currentUnit.setDescription(line.replace("  qudt:plainTextDescription ", ""));
                            break;
                        case "qudt:hasQuantityKind":
                            currentUnit.setQuantityKinds(currentUnit.getQuantityKinds() + "," + splitLine[3].replace("quantitykind:", ""));
                            break;
                    }

                    currentLineOfUnit++;

                }
                input.close();
                Log.i("DB", "Units b4 filter " + units.size());
                units = units.stream().filter(u -> !u.getSplitQuantityKinds().contains("Currency") && u.getSystemName() != null && u.getSystemName().length() != 0).collect(Collectors.toList());
                Log.i("DB", "Inserting units " + units.size());
                db[0].unitDao().insertAll(Arrays.copyOf(units.toArray(), units.size(), Unit[].class));
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                Log.e("DB ERROR", e.getMessage() + sw.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {

        }

    }
}
