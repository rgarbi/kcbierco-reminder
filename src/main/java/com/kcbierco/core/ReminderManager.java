package com.kcbierco.core;

import com.kcbierco.models.ExcelParsingConfig;
import com.kcbierco.parser.ExcelFileReader;

import java.io.IOException;
import java.util.List;

/**
 * Created by Richard on 1/31/16.
 */
public class ReminderManager {

    public void sendReminderIfNeeded(ExcelParsingConfig excelParsingConfig) throws IOException {

        List<String> allExcelFiles = new FileManager()
                .getAllExcelFilesInADirectory(excelParsingConfig.getActiveBatchDirectory());

        allExcelFiles.stream().forEach(file -> {
            new ExcelFileReader(file, excelParsingConfig.)
        });

    }


}
