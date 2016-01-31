package com.kcbeirco.parser;

import com.kcbeirco.fixture.ResourceFileFinder;
import com.kcbeirco.models.ExcelParsingConfig;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ExcelFileReaderTest {
    protected ExcelFileReader excelFileReader;

    @Before
    public void setUp(){
        excelFileReader = new ExcelFileReader("", new ExcelParsingConfig());
    }

    @Test
    public void testParseDatesFromExcelFile() throws IOException, WorksheetNotFoundException, ExcelFormatNotSupportedException {
        ExcelParsingConfig excelParsingConfig = new ExcelParsingConfig();
        excelParsingConfig.setWorksheetName("Test");
        excelParsingConfig.setCellsWithDates(Arrays.asList(new String[]{"B3", "B4", "B5", "B6"}));

        ExcelFileReader excelFileReader = new ExcelFileReader(ResourceFileFinder.findFileInResources("DatesFromFormula.xlsx")
                , excelParsingConfig);

        List<Date> dates = excelFileReader.parseDatesFromExcelFile();
        Assert.assertNotNull(dates);
        Assert.assertEquals(4, dates.size());
    }

    @Test
    public void testParseDatesFromExcelFileCaseDoesNotMatter() throws IOException, WorksheetNotFoundException, ExcelFormatNotSupportedException {
        ExcelParsingConfig excelParsingConfig = new ExcelParsingConfig();
        excelParsingConfig.setWorksheetName("Test");
        excelParsingConfig.setCellsWithDates(Arrays.asList(new String[] {"b3", "b4", "b5", "b6"}));

        ExcelFileReader excelFileReader = new ExcelFileReader(ResourceFileFinder.findFileInResources("DatesFromFormula.xlsx")
                , excelParsingConfig);

        List<Date> dates = excelFileReader.parseDatesFromExcelFile();
        Assert.assertNotNull(dates);
        Assert.assertEquals(4, dates.size());
    }

    @Test
    public void testParseDatesFromExcelXlsFile() throws IOException, WorksheetNotFoundException, ExcelFormatNotSupportedException {
        ExcelParsingConfig excelParsingConfig = new ExcelParsingConfig();
        excelParsingConfig.setWorksheetName("Test");
        excelParsingConfig.setCellsWithDates(Arrays.asList(new String[] {"B3", "B4", "B5", "B6"}));

        ExcelFileReader excelFileReader = new ExcelFileReader(ResourceFileFinder.findFileInResources("DatesFromFormulaXLS.xls")
                , excelParsingConfig);

        List<Date> dates = excelFileReader.parseDatesFromExcelFile();
        Assert.assertNotNull(dates);
        Assert.assertEquals(4, dates.size());
    }

    @Test
    public void testParseTankInfoFromExcelFile() throws IOException, WorksheetNotFoundException, ExcelFormatNotSupportedException {
        ExcelParsingConfig excelParsingConfig = new ExcelParsingConfig();
        excelParsingConfig.setWorksheetName("Test");
        excelParsingConfig.setCellsWithDates(Arrays.asList(new String[]{"B3", "B4", "B5", "B6"}));
        excelParsingConfig.setCellWithTankInfo("E3");

        ExcelFileReader excelFileReader = new ExcelFileReader(ResourceFileFinder.findFileInResources("DatesFromFormula.xlsx")
                , excelParsingConfig);

        String tankName = excelFileReader.parseTankInfoFromExcelFile();
        Assert.assertNotNull(tankName);
        Assert.assertEquals("Tank 334", tankName);
    }

    @Test
    public void testParseTankInfoFromExcelXlsFile() throws IOException, WorksheetNotFoundException, ExcelFormatNotSupportedException {
        ExcelParsingConfig excelParsingConfig = new ExcelParsingConfig();
        excelParsingConfig.setWorksheetName("Test");
        excelParsingConfig.setCellsWithDates(Arrays.asList(new String[]{"B3", "B4", "B5", "B6"}));
        excelParsingConfig.setCellWithTankInfo("E3");

        ExcelFileReader excelFileReader = new ExcelFileReader(ResourceFileFinder.findFileInResources("DatesFromFormulaXLS.xls")
                , excelParsingConfig);

        String tankName = excelFileReader.parseTankInfoFromExcelFile();
        Assert.assertNotNull(tankName);
        Assert.assertEquals("Tank 334", tankName);
    }


    @Test
    public void testGetAppropriateInstanceForXLSX() throws IOException, ExcelFormatNotSupportedException {
        String path = ResourceFileFinder.findFileInResources("DatesFromFormula.xlsx");
        Workbook workbook = excelFileReader.getAppropriateInstance(path);

        Assert.assertTrue(workbook instanceof XSSFWorkbook);
    }

    @Test
    public void testGetAppropriateInstanceXLS() throws IOException, ExcelFormatNotSupportedException {
        String path = ResourceFileFinder.findFileInResources("DatesFromFormulaXLS.xls");
        Workbook workbook = excelFileReader.getAppropriateInstance(path);

        Assert.assertTrue(workbook instanceof HSSFWorkbook);
    }

    @Test(expected = ExcelFormatNotSupportedException.class)
    public void testGetAppropriateInstanceNotSupported() throws IOException, ExcelFormatNotSupportedException {
        String path = ResourceFileFinder.findFileInResources("sometext.txt");
        excelFileReader.getAppropriateInstance(path);
    }

}
