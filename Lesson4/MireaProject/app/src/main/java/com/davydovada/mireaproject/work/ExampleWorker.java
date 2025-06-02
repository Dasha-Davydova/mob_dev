package com.davydovada.mireaproject.work;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import java.util.concurrent.TimeUnit;

public class ExampleWorker extends Worker {
    public static final String TAG = "ExampleWorker";

    public ExampleWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Задача началась");
        try {
            TimeUnit.SECONDS.sleep(10); // симуляция фоновой работы
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.failure();
        }
        Log.d(TAG, "Задача завершена");
        return Result.success();
    }
}
