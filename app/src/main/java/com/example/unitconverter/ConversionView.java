package com.example.unitconverter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.unitconverter.database.AppDatabase;
import com.example.unitconverter.database.DAOs.UnitDao;
import com.example.unitconverter.database.model.Unit;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConversionView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConversionView extends Fragment implements AdapterView.OnItemSelectedListener {

    private QuantityKindsViewViewModel mViewModel;
    private Context context;
    private EditText fromValueEditText;
    private TextView toValueTextView;
    private Spinner fromUnitSpinner;
    private Spinner toUnitSpinner;

    public ConversionView() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConversionView.
     */
    public static ConversionView newInstance() {
        ConversionView fragment = new ConversionView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = QuantityKindsViewViewModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conversion_view, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fromUnitSpinner = view.findViewById(R.id.convertFromSpinner);
        toUnitSpinner = view.findViewById(R.id.convertToSpinner);
        fromValueEditText = view.findViewById(R.id.editTextConvertFrom);
        fromValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("CONVERT", "text changed to: " + s);
                try{
                Conversion conversion = mViewModel.getConversion().getValue();
                conversion.setFromValue(Double.parseDouble(s.toString()));
                mViewModel.postConversion(conversion);
                }catch(NumberFormatException e){
                    
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        toValueTextView = getView().findViewById(R.id.toValueTextView);
        mViewModel.getConversion().observe(getViewLifecycleOwner(), conversion -> {
            Log.i("CONVERT", "Conversion changed");
            toValueTextView.setText("" + conversion.getToValue());
        });
        AsyncTask.execute(() ->{
            UnitDao unitDao = AppDatabase.getInstance(context).unitDao();
            List<Unit> units = unitDao.getUnits("%" + mViewModel.getSelectedQuantityKind() + "%").blockingGet();
            units = units.stream().filter(u -> u.getSplitQuantityKinds().contains(mViewModel.getSelectedQuantityKind())).collect(Collectors.toList());
            Conversion conversion = mViewModel.getConversion().getValue();
            conversion.setFromUnit(units.get(0));
            conversion.setToUnit(units.get(0));
            mViewModel.postConversion(conversion);
            ArrayAdapter<Unit> toAdapter = new ArrayAdapter<Unit>(context, android.R.layout.simple_spinner_item, units);
            ArrayAdapter<Unit> fromAdapter = new ArrayAdapter<Unit>(context, android.R.layout.simple_spinner_item, units);
            fromUnitSpinner.setAdapter(fromAdapter);
            toUnitSpinner.setAdapter(toAdapter);

            fromUnitSpinner.setOnItemSelectedListener(this);
            toUnitSpinner.setOnItemSelectedListener(this);
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Conversion conversion = mViewModel.getConversion().getValue();
        if(parent.getId() == R.id.convertFromSpinner){
            Unit unit = (Unit) parent.getItemAtPosition(position);
            conversion.setFromUnit(unit);
            mViewModel.postConversion(conversion);
        }else{ //convertToSpinner
            Unit unit = (Unit) parent.getItemAtPosition(position);
            conversion.setToUnit(unit);
            mViewModel.postConversion(conversion);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}