package com.kcbierco;

import com.google.inject.Guice;
import com.kcbierco.core.ReminderManager;
import com.kcbierco.db.DBConnectionManager;
import com.kcbierco.models.ExcelParsingConfig;
import com.kcbierco.parser.ConfigLoader;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

import java.io.IOException;


public class Application {

    public static void main(String[] args) throws IOException, ParseException {

        try {

            CommandLineParser commandLineParser = new DefaultParser();
            CommandLine cli = commandLineParser.parse(new OptionsManager().buildOptions(), args);

            String excelFileConfig = cli.getOptionValue(OptionsManager.EXCEL_OPTION);
            String dbProps = cli.getOptionValue(OptionsManager.DB_OPTION);

            //Load Config
            ExcelParsingConfig excelParsingConfig = ConfigLoader.loadTheConfig(excelFileConfig);

            //Check DB connection
            DBConnectionManager.init(dbProps);
            DBConnectionManager.getConnection();

            ReminderManager reminderManager = Guice.createInjector(new ServiceInjector()).getInstance(ReminderManager.class);
            reminderManager.sendReminderIfNeeded(excelParsingConfig);

        } finally {
            DBConnectionManager.close();
        }

    }
}
