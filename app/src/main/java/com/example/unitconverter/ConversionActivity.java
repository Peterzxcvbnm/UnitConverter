package com.example.unitconverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class ConversionActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        mTextView = (TextView) findViewById(R.id.text);

        QuantityKindsViewViewModel mViewModel = QuantityKindsViewViewModel.getInstance();
        String qk = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        Log.i("SPINNER", "vm: " + mViewModel.toString());
        mViewModel.setSelectedQuantityKind(qk);
    }
}