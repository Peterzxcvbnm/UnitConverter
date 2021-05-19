package com.example.unitconverter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class QuantityKindsViewViewModel extends ViewModel {

    private MutableLiveData<List<String>> quantityKinds;

    public MutableLiveData<List<String>> getQuantityKinds() {
        if (quantityKinds == null) {
            quantityKinds = new MutableLiveData<List<String>>();
            quantityKinds.setValue(new ArrayList<String>() {{ add("test1");}});
        }
        return quantityKinds;
    }

    public void addQuantityKinds(List<String> quantityKinds) {
        if (this.quantityKinds == null) {
            this.quantityKinds = new MutableLiveData<List<String>>();
            this.quantityKinds.setValue(new ArrayList<String>() {{ add("test2");}});
        }
        quantityKinds.addAll(this.quantityKinds.getValue());
        this.quantityKinds.postValue(quantityKinds);
    }
}