package com.kcbierco;

import com.kcbierco.models.ExcelParsingConfig;
import com.kcbierco.parser.ConfigLoader;

import java.io.IOException;

/**
 * Created by Richard on 1/30/16.
 */
public class Application {

    public static void main(String[] args) throws IOException {

        //Load Config
        ExcelParsingConfig excelParsingConfig = ConfigLoader.loadTheConfig("./config.json");

        //Check DB connection


    }
}
