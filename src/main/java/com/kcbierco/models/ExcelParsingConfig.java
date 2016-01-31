package com.kcbierco.models;

import java.util.List;

/**
 * Created by Richard on 1/30/16.
 */
public class ExcelParsingConfig {
    private String activeBatchDirectory;
    private List<ImportantDatesToWatchConfig> importantDatesToWatch;

    public String getActiveBatchDirectory() {
        return activeBatchDirectory;
    }

    public void setActiveBatchDirectory(String activeBatchDirectory) {
        this.activeBatchDirectory = activeBatchDirectory;
    }

    public List<ImportantDatesToWatchConfig> getImportantDatesToWatch() {
        return importantDatesToWatch;
    }

    public void setImportantDatesToWatch(List<ImportantDatesToWatchConfig> importantDatesToWatch) {
        this.importantDatesToWatch = importantDatesToWatch;
    }
}
