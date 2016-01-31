package com.kcbeirco.parser;

import com.kcbeirco.models.ExcelCellCoordinates;
import com.kcbeirco.models.ExcelParsingConfig;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
    private static final int CHAR_OFFSET = 64;
    private String excelFilePath;
    private ExcelParsingConfig excelParsingConfig;

    public ExcelFileReader(String excelFilePath, ExcelParsingConfig excelParsingConfig){
        this.excelFilePath = excelFilePath;
        this.excelParsingConfig = excelParsingConfig;
    }

    public List<Date> parseExcelFile() throws IOException, WorksheetNotFoundException {
        List<Date> dates = new ArrayList<>();
        FileInputStream file = new FileInputStream(new File(excelFilePath));
        HSSFWorkbook workbook = new HSSFWorkbook(file);

        HSSFSheet sheet = workbook.getSheet(excelParsingConfig.getWorksheetName());

        if(sheet == null){
            LOGGER.info(String.format("Could not find a worksheet by the name of %s. In the file with a path of %s.",
                    excelParsingConfig.getWorksheetName(), excelFilePath));
            throw new WorksheetNotFoundException("Could not find worksheet");
        }

        excelParsingConfig.getCellsWithDates().stream().forEach(cell -> {
            try {
                ExcelCellCoordinates cellCoordinates = translateCommonNameToCoordinates(cell);
                dates.add(sheet.getRow(cellCoordinates
                        .getRowNumber())
                        .getCell(cellCoordinates.getCellNumber())
                        .getDateCellValue());

            } catch (CellNameParseException e) {
                throw new RuntimeException(e);
            }
        });

        return dates;
    }


    protected ExcelCellCoordinates translateCommonNameToCoordinates(String cellName) throws CellNameParseException {
        ExcelCellCoordinates excelCellCoordinates = new ExcelCellCoordinates();

        int columnNumber = getColumnNumberFromLetter(getColumnLetter(cellName));
        int rowNumber = getRowNumber(cellName);

        excelCellCoordinates.setCellNumber(columnNumber);
        excelCellCoordinates.setRowNumber(rowNumber);

        return excelCellCoordinates;
    }

    protected String getColumnLetter(String cellName) throws CellNameParseException {
        if(Character.isLetter(cellName.charAt(0))){
            return cellName.substring(0, 1);
        }

        LOGGER.info("Could not parse the cell column letter from the cell name. -> " + cellName);
        throw new CellNameParseException("Could not parse the column from the cell name");
    }

    protected int getRowNumber(String cellName){
        return Integer.parseInt(cellName.substring(1, cellName.length()));
    }

    protected int getColumnNumberFromLetter(String columnLetter){
        return columnLetter.toUpperCase().charAt(0) - CHAR_OFFSET;
    }

}
