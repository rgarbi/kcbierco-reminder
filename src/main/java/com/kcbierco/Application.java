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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws IOException, ParseException {

        try {
            LOGGER.info("Starting up.");

            CommandLineParser commandLineParser = new DefaultParser();
            CommandLine cli = commandLineParser.parse(new OptionsManager().buildOptions(), args);

            String excelFileConfig = cli.getOptionValue(OptionsManager.EXCEL_OPTION);
            String dbProps = cli.getOptionValue(OptionsManager.DB_OPTION);

            if((excelFileConfig == null || !new File(excelFileConfig).exists()) &&
                    (dbProps == null || !new File(dbProps).exists())){
                throw new ParseException("Files not found!");
            }


            //Load Config
            ExcelParsingConfig excelParsingConfig = ConfigLoader.loadTheConfig(excelFileConfig);

            LOGGER.info("Done loading the config.");
            LOGGER.info("Initializing the DB connection.");

            //Check DB connection
            DBConnectionManager.init(dbProps);
            DBConnectionManager.getConnection();

            LOGGER.info("Done initializing the DB connection.");
            ReminderManager reminderManager = Guice.createInjector(new ServiceInjector()).getInstance(ReminderManager.class);
            reminderManager.sendReminderIfNeeded(excelParsingConfig);

        } finally {
            DBConnectionManager.close();
        }

    }
}
