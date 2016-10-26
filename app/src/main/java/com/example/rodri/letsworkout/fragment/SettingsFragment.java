package com.example.rodri.letsworkout.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.util.LocaleHelper;

/**
 * Created by rodri on 10/24/2016.
 */

public class SettingsFragment extends Fragment {

    private static final String MY_PREFERENCES = "com.example.rodri.letsworkout";
    private static final String AUTO_LOGIN = "com.example.rodri.letsworkout.autologin";

    private Switch switchAutoLogin;
    private Spinner spinnerLanguages;
    private Button btConfirm;

    private String[] languages;
    private String[] languagesCode;
    private boolean switchStatus = false;
    private SharedPreferences sharedPreferences;
    private int chosenLanguage = -1;
    private int initialLanguage = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        initViews(v);

        languagesCode = getActivity().getResources().getStringArray(R.array.language_code);
        languages = getActivity().getResources().getStringArray(R.array.languages);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, languages);
        spinnerLanguages.setAdapter(adapter);

        sharedPreferences = getActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        switchStatus = sharedPreferences.getBoolean(AUTO_LOGIN, false);
        String lang = LocaleHelper.getLanguage(getContext());
        if (!lang.equals("")) {
            chosenLanguage = getSavedLanguage(lang);
            initialLanguage = chosenLanguage;
            System.out.println("I was here -> " + lang);
            spinnerLanguages.setSelection(chosenLanguage);
        }

        if (switchStatus) {
            switchAutoLogin.setChecked(true);
        } else {
            switchAutoLogin.setChecked(false);
        }

        switchAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchStatus = isChecked;
                //Toast.makeText(getContext(), "Checked is " + switchStatus, Toast.LENGTH_SHORT).show();
                btConfirm.setVisibility(View.VISIBLE);
            }
        });

        spinnerLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenLanguage = position;
                System.out.println("The chosen language is: " + languagesCode[position]);
                if (chosenLanguage != initialLanguage) btConfirm.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chosenLanguage != -1) {
                    LocaleHelper.setLocale(getContext(), languagesCode[chosenLanguage]);
                    getActivity().recreate();
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(AUTO_LOGIN, switchStatus);
                editor.apply();

            }
        });

        return v;

    }

    public void initViews(View v) {
        switchAutoLogin = (Switch) v.findViewById(R.id.fragmentSettings_switchAutomaticLogin);
        spinnerLanguages = (Spinner) v.findViewById(R.id.fragmentSettings_spinnerLanguages);
        btConfirm = (Button) v.findViewById(R.id.fragmentSettings_btConfirm);
    }

    public int getSavedLanguage(String lang) {
        for (int i=0; i<languagesCode.length; i++) {
            if (languagesCode[i].equals(lang)) return i;
        }

        return 0;
    }
}
