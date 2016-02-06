package com.kcbierco.db;

import com.kcbierco.models.EmailSentRecord;
import com.kcbierco.util.CollectionUtil;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Richard on 2/2/16.
 */
public class EmailSentBroker {
    private OObjectDatabaseTx db;

    public EmailSentBroker(){
        db = DBConnectionManager.getConnection();
    }

    public List<EmailSentRecord> getAllEmailSentRecords(){
        return CollectionUtil.iterableToCollection(db.browseClass(EmailSentRecord.class));
    }

    public Optional<EmailSentRecord> findRecordByDateFileAndMessage(Date date, String message, String fileName){
        return this.getAllEmailSentRecords().stream().filter(record ->
                record.getBatchFileName().equals(fileName) &&
                        record.getMessage().equals(message) &&
                        DateUtils.isSameDay(record.getDateSent(), date)).findAny();
    }

    public EmailSentRecord save(EmailSentRecord emailSentRecord){
        return db.save(emailSentRecord);
    }

    public void deleteAllFromDB(){
        getAllEmailSentRecords().stream().forEach(db::delete);
        db.commit();
    }

}
