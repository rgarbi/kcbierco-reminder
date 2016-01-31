package com.kcbierco.models;

import java.util.Date;
import java.util.List;

public class KcBierCoBatch {
    private String batchSpreadsheetFilePath;
    private String tankName;
    private List<Date> importantCheckDates;
    private String baseMessage;

    public String getBatchSpreadsheetFilePath() {
        return batchSpreadsheetFilePath;
    }

    public void setBatchSpreadsheetFilePath(String batchSpreadsheetFilePath) {
        this.batchSpreadsheetFilePath = batchSpreadsheetFilePath;
    }

    public String getTankName() {
        return tankName;
    }

    public void setTankName(String tankName) {
        this.tankName = tankName;
    }

    public List<Date> getImportantCheckDates() {
        return importantCheckDates;
    }

    public void setImportantCheckDates(List<Date> importantCheckDates) {
        this.importantCheckDates = importantCheckDates;
    }

    public String getBaseMessage() {
        return baseMessage;
    }

    public void setBaseMessage(String baseMessage) {
        this.baseMessage = baseMessage;
    }
}
