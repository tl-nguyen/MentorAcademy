package com.mentormate.academy.lecture14;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by Stefan.Doychev on 15.01.2015.
 */
public class Util {

    private static File localDatabase = new File(Environment.getDataDirectory(),
            Constants.PATH_TO_APP_FOLDER + Constants.LOCAL_DATABASE);
    private static File exportedDatabase = new File(Environment.getDataDirectory(),
            Constants.PATH_TO_APP_FOLDER + Constants.EXPORT_DATABASE);
    private static File databaseToImport = new File(Environment.getDataDirectory(),
            Constants.PATH_TO_APP_FOLDER + Constants.IMPORT_DATABASE);

    public static boolean exportDB() {

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(localDatabase).getChannel();
            destination = new FileOutputStream(exportedDatabase).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean importDB() {

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(databaseToImport).getChannel();
            destination = new FileOutputStream(localDatabase).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
