package com.tlnguyen.homework_catalog.data;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.tlnguyen.homework_catalog.models.Book;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by TL on 12/5/2014.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[]{Book.class};

    public static void main(String[] args) throws IOException, SQLException {
        writeConfigFile("ormlite_config.txt");
    }
}
