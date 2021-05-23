package com.example.unitconverter;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class QuantityKindsViewViewModel extends ViewModel {

    private List<String> quantityKindList = new ArrayList<String>();
    private MutableLiveData<List<String>> quantityKinds;
    private MutableLiveData<String> selectedQuantityKind = new MutableLiveData<>();

    private MutableLiveData<Conversion> conversion = new MutableLiveData<>(new Conversion());

    private static QuantityKindsViewViewModel instance = new QuantityKindsViewViewModel();

    public static QuantityKindsViewViewModel getInstance() {return instance;}

    public LiveData<String> getSelectedQuantityKind() {
        return selectedQuantityKind;
    }

    public void setSelectedQuantityKind(String selectedQuantityKind) {
        this.selectedQuantityKind.postValue(selectedQuantityKind);
    }

    public LiveData<Conversion> getConversion() {
        return conversion;
    }

    public void postConversion(Conversion conversion) {
        this.conversion.postValue(conversion);
    }

    public MutableLiveData<List<String>> getQuantityKinds() {
        if (quantityKinds == null) {
            quantityKinds = new MutableLiveData<List<String>>();
            quantityKinds.setValue(new ArrayList<String>());
        }
        return quantityKinds;
    }

    public void addQuantityKinds(List<String> quantityKinds) {
        quantityKinds = new ArrayList<>(new HashSet<>(quantityKinds));
        if (this.quantityKinds == null) {
            this.quantityKinds = new MutableLiveData<List<String>>();
        }
        quantityKindList.addAll(quantityKinds);
        quantityKindList = new ArrayList<>(new HashSet<>(quantityKindList));
        quantityKindList.sort(String::compareTo);
        this.quantityKinds.postValue(quantityKindList);

    }
}