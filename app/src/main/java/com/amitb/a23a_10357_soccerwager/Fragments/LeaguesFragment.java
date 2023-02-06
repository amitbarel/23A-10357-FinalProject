package com.amitb.a23a_10357_soccerwager.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.amitb.a23a_10357_soccerwager.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeaguesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View view;
    private AppCompatSpinner leagues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_leagues, container, false);
        leagues = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(getContext(),R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leagues.setAdapter(adapter);
        leagues.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}