package com.davydovada.mireaproject;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import java.io.*;
import java.util.*;

public class FileToolsFragment extends Fragment {

    private RecyclerView recyclerView;
    private FileAdapter adapter;
    private File filesDir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_tools, container, false);

        recyclerView = view.findViewById(R.id.fileRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        filesDir = requireContext().getFilesDir();
        adapter = new FileAdapter(loadFiles(), this::showFileContent);
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.fabAddNote).setOnClickListener(v -> showAddNoteDialog());

        return view;
    }

    private List<File> loadFiles() {
        File[] files = filesDir.listFiles((dir, name) -> name.endsWith(".txt"));
        return files != null ? Arrays.asList(files) : new ArrayList<>();
    }

    private void showAddNoteDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_note, null);
        EditText editNoteContent = dialogView.findViewById(R.id.editNoteContent);

        new AlertDialog.Builder(getContext())
                .setTitle("Новая заметка")
                .setView(dialogView)
                .setPositiveButton("Сохранить", (dialog, which) -> {
                    String content = editNoteContent.getText().toString();
                    if (!content.isEmpty()) {
                        saveNoteToFile(content);
                        adapter.updateData(loadFiles());
                        Toast.makeText(getContext(), "Заметка сохранена", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void saveNoteToFile(String content) {
        String fileName = "note_" + System.currentTimeMillis() + ".txt";
        try (FileOutputStream fos = requireContext().openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showFileContent(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder text = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
            reader.close();

            new AlertDialog.Builder(getContext())
                    .setTitle("Содержимое файла")
                    .setMessage(text.toString())
                    .setPositiveButton("Ок", null)
                    .show();

        } catch (IOException e) {
            Toast.makeText(getContext(), "Ошибка чтения файла", Toast.LENGTH_SHORT).show();
        }
    }
}
