package com.kcbierco.models;

import java.util.List;

/**
 * Created by Richard on 1/30/16.
 */
public class ExcelParsingConfig {
    private String activeBatchDirectory;
    private List<String> emailRecipients;
    private String sourceEmailAddress;
    private String sourceEmailPassword;
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

    public List<String> getEmailRecipients() {
        return emailRecipients;
    }

    public void setEmailRecipients(List<String> emailRecipients) {
        this.emailRecipients = emailRecipients;
    }

    public String getSourceEmailAddress() {
        return sourceEmailAddress;
    }

    public void setSourceEmailAddress(String sourceEmailAddress) {
        this.sourceEmailAddress = sourceEmailAddress;
    }

    public String getSourceEmailPassword() {
        return sourceEmailPassword;
    }

    public void setSourceEmailPassword(String sourceEmailPassword) {
        this.sourceEmailPassword = sourceEmailPassword;
    }
}
