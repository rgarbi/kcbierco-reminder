package com.kcbierco.parser;

import com.kcbierco.fixture.ResourceFileFinder;

import com.kcbierco.models.ImportantDatesToWatchConfig;
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
import java.util.stream.Collectors;

public class ExcelFileReaderTest {
    protected ExcelFileReader excelFileReader;
    protected static final String WORKSHEET = "Cellar Record";
    protected static final List<String> CELLS_WITH_DATES = Arrays.asList(new String[] {"B16", "B17", "B18", "B19"});
    protected static final String TANK_INFO = "I5";

    @Before
    public void setUp(){
        excelFileReader = new ExcelFileReader("", new ImportantDatesToWatchConfig());
    }

    @Test
    public void testParseDatesFromExcelFile() throws IOException, WorksheetNotFoundException, ExcelFormatNotSupportedException {
        ImportantDatesToWatchConfig importantDatesToWatchConfig = new ImportantDatesToWatchConfig();
        importantDatesToWatchConfig.setWorksheetName(WORKSHEET);
        importantDatesToWatchConfig.setCellsWithDates(CELLS_WITH_DATES);

        ExcelFileReader excelFileReader = new ExcelFileReader(ResourceFileFinder.findFileInResources("DatesFromFormula.xlsx")
                , importantDatesToWatchConfig);

        List<Date> dates = excelFileReader.parseDatesFromExcelFile();
        Assert.assertNotNull(dates);
        Assert.assertEquals(4, dates.size());
    }

    @Test
    public void testParseDatesFromExcelFileCaseDoesNotMatter() throws IOException, WorksheetNotFoundException, ExcelFormatNotSupportedException {
        ImportantDatesToWatchConfig importantDatesToWatchConfig = new ImportantDatesToWatchConfig();
        importantDatesToWatchConfig.setWorksheetName(WORKSHEET);
        importantDatesToWatchConfig.setCellsWithDates(CELLS_WITH_DATES.stream().map(cell -> cell.toLowerCase()).collect(Collectors.toList()));

        ExcelFileReader excelFileReader = new ExcelFileReader(ResourceFileFinder.findFileInResources("DatesFromFormula.xlsx")
                , importantDatesToWatchConfig);

        List<Date> dates = excelFileReader.parseDatesFromExcelFile();
        Assert.assertNotNull(dates);
        Assert.assertEquals(4, dates.size());
    }

    @Test
    public void testParseDatesFromExcelXlsFile() throws IOException, WorksheetNotFoundException, ExcelFormatNotSupportedException {
        ImportantDatesToWatchConfig importantDatesToWatchConfig = new ImportantDatesToWatchConfig();
        importantDatesToWatchConfig.setWorksheetName(WORKSHEET);
        importantDatesToWatchConfig.setCellsWithDates(CELLS_WITH_DATES.stream().map(cell -> cell.toLowerCase()).collect(Collectors.toList()));


        ExcelFileReader excelFileReader = new ExcelFileReader(ResourceFileFinder.findFileInResources("DatesFromFormulaXLS.xls")
                , importantDatesToWatchConfig);

        List<Date> dates = excelFileReader.parseDatesFromExcelFile();
        Assert.assertNotNull(dates);
        Assert.assertEquals(4, dates.size());
    }

    @Test
    public void testParseTankInfoFromExcelFile() throws IOException, WorksheetNotFoundException, ExcelFormatNotSupportedException {
        ImportantDatesToWatchConfig importantDatesToWatchConfig = new ImportantDatesToWatchConfig();
        importantDatesToWatchConfig.setWorksheetName(WORKSHEET);
        importantDatesToWatchConfig.setCellsWithDates(CELLS_WITH_DATES.stream().map(cell -> cell.toLowerCase()).collect(Collectors.toList()));
        importantDatesToWatchConfig.setCellWithTankInfo(TANK_INFO);

        ExcelFileReader excelFileReader = new ExcelFileReader(ResourceFileFinder.findFileInResources("DatesFromFormula.xlsx")
                , importantDatesToWatchConfig);

        String tankName = excelFileReader.parseTankInfoFromExcelFile();
        Assert.assertNotNull(tankName);
        Assert.assertEquals("Tank 34", tankName);
    }

    @Test
    public void testParseTankInfoFromExcelXlsFile() throws IOException, WorksheetNotFoundException, ExcelFormatNotSupportedException {
        ImportantDatesToWatchConfig importantDatesToWatchConfig = new ImportantDatesToWatchConfig();
        importantDatesToWatchConfig.setWorksheetName(WORKSHEET);
        importantDatesToWatchConfig.setCellsWithDates(CELLS_WITH_DATES.stream().map(cell -> cell.toLowerCase()).collect(Collectors.toList()));
        importantDatesToWatchConfig.setCellWithTankInfo(TANK_INFO);

        ExcelFileReader excelFileReader = new ExcelFileReader(ResourceFileFinder.findFileInResources("DatesFromFormulaXLS.xls")
                , importantDatesToWatchConfig);

        String tankName = excelFileReader.parseTankInfoFromExcelFile();
        Assert.assertNotNull(tankName);
        Assert.assertEquals("Tank 34", tankName);
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
