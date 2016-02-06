package com.kcbierco;

import org.apache.commons.cli.Options;

/**
 * Created by Richard on 2/6/16.
 */
public class OptionsManager {
    public static final String DB_OPTION = "db";
    public static final String EXCEL_OPTION = "excel";

    public Options buildOptions(){
        Options options = new Options();
        options.addOption(DB_OPTION, true, "DB properties file location");
        options.addOption(EXCEL_OPTION, true, "Excel file location");
        return options;
    }
}
