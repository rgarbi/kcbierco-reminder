package com.kcbierco.config;

import com.kcbierco.models.DBConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Richard on 2/4/16.
 */
public class DbPropertiesLoader {
    private static final String DB_PATH = "db.path";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";

    public static DBConfig getDBConfig(String dbPropertiesFile) throws IOException {
        Properties dbProperties = new Properties();
        InputStream propertiesStream = new FileInputStream(new File(dbPropertiesFile));
        dbProperties.load(propertiesStream);

        DBConfig dbConfig = new DBConfig();
        dbConfig.setDbPath(dbProperties.getProperty(DB_PATH));
        dbConfig.setDbUsername(dbProperties.getProperty(DB_USERNAME));
        dbConfig.setDbPassword(dbProperties.getProperty(DB_PASSWORD));
        return dbConfig;
    }

}
