package com.example.mireaproject.ui.worker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.mireaproject.R;
import com.example.mireaproject.work.ExampleWorker;

public class WorkerFragment extends Fragment {

    public WorkerFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worker, container, false);

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresCharging(true)
                .build();

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ExampleWorker.class)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(requireContext()).enqueue(workRequest);

        return view;
    }
}
