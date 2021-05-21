package com.example.unitconverter;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.unitconverter.database.AppDatabase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class QuantityKindsView extends Fragment {

    private QuantityKindsViewViewModel mViewModel;
    private Context context;
    private Button chooseQuantityKindButton;

    public static QuantityKindsView newInstance() {
        return new QuantityKindsView();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quantity_kinds_view_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        mViewModel = QuantityKindsViewViewModel.getInstance();

        AppDatabase db = AppDatabase.getInstance(context);

        Log.i("UI", "About to get quantityKinds");
        AsyncTask.execute(() ->{
            db.unitDao().getQuantityKinds().subscribe(q -> q.forEach(qk ->{
                String[] quantityKindsArray = qk.split(",");
                mViewModel.addQuantityKinds(new ArrayList<String>(Arrays.asList(quantityKindsArray)));
            }), e -> {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                Log.e("UI ERROR", e.getMessage() + sw.toString());
            });
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView quantityKindList = (RecyclerView) view.findViewById(R.id.quantityKindList);
        MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(mViewModel.getQuantityKinds());

        quantityKindList.setAdapter(adapter);

        quantityKindList.setLayoutManager(new LinearLayoutManager(context));
    }

}