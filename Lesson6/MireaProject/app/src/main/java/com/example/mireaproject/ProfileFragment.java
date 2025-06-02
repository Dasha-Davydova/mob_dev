package com.example.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class ProfileFragment extends Fragment {

    private EditText editFullName, editCity, editBio;
    private Button btnSave;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        editFullName = view.findViewById(R.id.editFullName);
        editCity = view.findViewById(R.id.editCity);
        editBio = view.findViewById(R.id.editBio);
        btnSave = view.findViewById(R.id.btnSaveProfile);

        prefs = requireActivity().getSharedPreferences("user_profile", Context.MODE_PRIVATE);
        loadData();

        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("fullName", editFullName.getText().toString());
            editor.putString("city", editCity.getText().toString());
            editor.putString("bio", editBio.getText().toString());
            editor.apply();

            Toast.makeText(getContext(), "Профиль обновлён", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadData() {
        editFullName.setText(prefs.getString("fullName", ""));
        editCity.setText(prefs.getString("city", ""));
        editBio.setText(prefs.getString("bio", ""));
    }
}
