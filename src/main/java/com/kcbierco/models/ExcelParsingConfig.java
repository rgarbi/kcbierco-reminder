package com.kcbierco.models;

import java.util.List;

/**
 * Created by Richard on 1/30/16.
 */
public class ExcelParsingConfig {
    private String worksheetName;
    private List<String> cellsWithDates;
    private String cellWithTankInfo;

    public String getWorksheetName() {
        return worksheetName;
    }

    public void setWorksheetName(String worksheetName) {
        this.worksheetName = worksheetName;
    }

    public List<String> getCellsWithDates() {
        return cellsWithDates;
    }

    public void setCellsWithDates(List<String> cellsWithDates) {
        this.cellsWithDates = cellsWithDates;
    }

    public String getCellWithTankInfo() {
        return cellWithTankInfo;
    }

    public void setCellWithTankInfo(String cellWithTankInfo) {
        this.cellWithTankInfo = cellWithTankInfo;
    }
}
