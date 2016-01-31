package com.kcbierco.parser;

import com.kcbierco.models.ExcelParsingConfig;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class ExcelFileReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelFileReader.class);
    private String excelFilePath;
    private ExcelParsingConfig excelParsingConfig;

    public ExcelFileReader(String excelFilePath, ExcelParsingConfig excelParsingConfig){
        this.excelFilePath = excelFilePath;
        this.excelParsingConfig = excelParsingConfig;
    }

    public List<Date> parseDatesFromExcelFile() throws IOException, WorksheetNotFoundException, ExcelFormatNotSupportedException {
        List<Date> dates = new ArrayList<>();

        Workbook workbook = getAppropriateInstance(excelFilePath);
        Sheet sheet = workbook.getSheet(excelParsingConfig.getWorksheetName());

        if(sheet == null){
            LOGGER.info(String.format("Could not find a worksheet by the name of %s. In the file with a path of %s.",
                    excelParsingConfig.getWorksheetName(), excelFilePath));
            throw new WorksheetNotFoundException("Could not find worksheet");
        }

        excelParsingConfig.getCellsWithDates().stream().forEach(cell -> {
                CellReference cellReference = new CellReference(cell);
                dates.add(sheet
                        .getRow(cellReference.getRow())
                        .getCell(cellReference.getCol())
                        .getDateCellValue());
        });

        return dates;
    }

    public String parseTankInfoFromExcelFile() throws IOException, ExcelFormatNotSupportedException {
        Workbook workbook = getAppropriateInstance(excelFilePath);
        Sheet sheet = workbook.getSheet(excelParsingConfig.getWorksheetName());
        CellReference cellReference = new CellReference(excelParsingConfig.getCellWithTankInfo());

        return sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).getStringCellValue();
    }

    protected Workbook getAppropriateInstance(String filePath) throws IOException, ExcelFormatNotSupportedException {
        FileInputStream file = new FileInputStream(new File(filePath));
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        switch (extension) {
            case "xls":
                return new HSSFWorkbook(file);
            case "xlsx":
                return new XSSFWorkbook(file);
            default:
                LOGGER.info("Files with the extension " + extension + " are not currently supported.");
                throw new ExcelFormatNotSupportedException("Excel extension not supported");
        }
    }
}
