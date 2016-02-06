package com.kcbierco.models;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Richard on 1/31/16.
 */
public class EmailSentRecord {

    private String id = UUID.randomUUID().toString();
    private String batchFileName;
    private Date dateSent;
    private String message;
    private Long sentTimeStamp;

    public String getBatchFileName() {
        return batchFileName;
    }

    public void setBatchFileName(String batchFileName) {
        this.batchFileName = batchFileName;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSentTimeStamp() {
        return sentTimeStamp;
    }

    public void setSentTimeStamp(Long sentTimeStamp) {
        this.sentTimeStamp = sentTimeStamp;
    }
}
