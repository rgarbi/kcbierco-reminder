package com.kcbierco.db;


import com.kcbierco.config.DbPropertiesLoader;
import com.kcbierco.models.DBConfig;
import com.kcbierco.models.EmailSentRecord;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

import java.io.IOException;

/**
 * Created by Richard on 2/1/16.
 */
public class DBConnectionManager {
    private static final String DB_NAME = "KCBIERCO";
    private static OObjectDatabaseTx db = null;
    private static String dbConnectionPath;
    private static String dbUsername;
    private static String dbPassword;

    public static void init(String dbPropertiesFile) throws IOException {
        DBConfig config = DbPropertiesLoader.getDBConfig(dbPropertiesFile);
        DBConnectionManager.dbConnectionPath = config.getDbPath();
        DBConnectionManager.dbUsername = config.getDbUsername();
        DBConnectionManager.dbPassword = config.getDbPassword();
    }

    public static OObjectDatabaseTx getConnection(){
        if(db == null){
            db = createIfFirstTime();
        }

        db.getEntityManager().registerEntityClass(EmailSentRecord.class);

        if(db.isClosed()){
            return db.open(dbUsername, dbPassword);
        }

        return db;
    }

    protected static OObjectDatabaseTx createIfFirstTime(){
        OObjectDatabaseTx dbConn = new OObjectDatabaseTx(formatConnectionPath());
        if(dbConn.exists()){
            return dbConn;
        }

        dbConn.create();
        return dbConn;
    }

    protected static String formatConnectionPath(){
        if(dbConnectionPath.equals("memory")){
            return dbConnectionPath + ":" + DB_NAME;
        } else {
            return dbConnectionPath + "/" + DB_NAME;
        }
    }

    public static void close(){
        if(db != null) {
            db.close();
        }
    }

}
