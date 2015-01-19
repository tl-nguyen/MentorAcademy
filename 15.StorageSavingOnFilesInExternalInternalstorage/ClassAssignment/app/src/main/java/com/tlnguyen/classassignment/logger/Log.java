package com.tlnguyen.classassignment.logger;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Log {
    private static String fileName = "log.csv";

    public static void normal(Context context, String fileContent) {
        writeToFile(context, fileContent, LogMode.NORMAL);
    }

    public static void critical(Context context, String fileContent) {
        writeToFile(context, fileContent, LogMode.CRITICAL);
    }

    public static ArrayList<String> read(Context context, LogMode mode) {
        ArrayList<String> logs = new ArrayList<String>();

        File file;
        BufferedReader reader = null;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
        }
        else {
            file = new File(context.getFilesDir(), fileName);
        }

        try {
            reader = new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null) {
                if (mode != null) {
                    if (!line.contains(mode.toString())) {
                        continue;
                    }
                }

                logs.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return logs;
    }

    private static void writeToFile(Context context, String fileContent, LogMode mode) {
        File file;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
        }
        else {
            file = new File(context.getFilesDir(), fileName);
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(mode + ": " + fileContent);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
