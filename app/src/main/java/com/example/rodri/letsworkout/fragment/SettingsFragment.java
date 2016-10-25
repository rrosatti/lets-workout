package com.example.rodri.letsworkout.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;

/**
 * Created by rodri on 10/24/2016.
 */

public class SettingsFragment extends Fragment {

    private static final String MY_PREFERENCES = "com.example.rodri.letsworkout";
    private static final String AUTO_LOGIN = "com.example.rodri.letsworkout.autologin";

    private Switch switchAutoLogin;
    private Spinner spinnerLanguages;

    private String[] languages;
    private boolean switchStatus = false;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        initViews(v);

        languages = getActivity().getResources().getStringArray(R.array.languages);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, languages);
        spinnerLanguages.setAdapter(adapter);

        sharedPreferences = getActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        switchStatus = sharedPreferences.getBoolean(AUTO_LOGIN, false);

        if (switchStatus) {
            switchAutoLogin.setChecked(true);
        } else {
            switchAutoLogin.setChecked(false);
        }

        switchAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchStatus = isChecked;
                Toast.makeText(getContext(), "Checked is " + switchStatus, Toast.LENGTH_SHORT).show();
            }
        });

        return v;

    }

    public void initViews(View v) {
        switchAutoLogin = (Switch) v.findViewById(R.id.fragmentSettings_switchAutomaticLogin);
        spinnerLanguages = (Spinner) v.findViewById(R.id.fragmentSettings_spinnerLanguages);
    }
}
