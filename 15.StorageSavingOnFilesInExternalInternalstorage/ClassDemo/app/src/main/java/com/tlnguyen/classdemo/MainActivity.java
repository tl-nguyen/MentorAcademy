package com.tlnguyen.classdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private String filename = "TEST_FILE";
    private String fileExtension = ".txt";
    private String file = filename + fileExtension;
    private String fileContent = "Sample text for our file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        writeToFile(file);
        writeToExternalStorageFile(file);
    }

    private void writeToExternalStorageFile(String fileName) {
        File file;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            file = new File(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(fileContent);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeToFile(String fileName) {
        File file;
        file = new File(this.getFilesDir(), fileName);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(fileContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
