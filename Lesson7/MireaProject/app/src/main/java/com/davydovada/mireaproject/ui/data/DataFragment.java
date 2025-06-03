package com.davydovada.mireaproject.ui.data;

import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.davydovada.mireaproject.R;
import com.google.firebase.firestore.*;

public class DataFragment extends Fragment {

    private FirebaseFirestore db;
    private TextView titleText, descriptionText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        titleText = view.findViewById(R.id.text_title);
        descriptionText = view.findViewById(R.id.text_description);
        db = FirebaseFirestore.getInstance();

        loadDataFromFirestore();

        return view;
    }

    private void loadDataFromFirestore() {
        db.collection("info").document("medical_it")
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        titleText.setText(document.getString("title"));
                        descriptionText.setText(document.getString("description"));
                    }
                })
                .addOnFailureListener(e ->
                        Log.e("Firestore", "Ошибка получения данных", e));
    }
}
