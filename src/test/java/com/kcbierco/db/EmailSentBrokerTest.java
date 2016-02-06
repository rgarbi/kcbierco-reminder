package com.kcbierco.db;

import com.kcbierco.fixture.GenerateObjects;
import com.kcbierco.fixture.ResourceFileFinder;
import com.kcbierco.models.EmailSentRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * Created by Richard on 2/6/16.
 */
public class EmailSentBrokerTest {
    protected EmailSentBroker emailSentBroker;

    @Before
    public void setUp() throws IOException {
        DBConnectionManager.init(ResourceFileFinder.findFileInResources("database.properties"));
        emailSentBroker = new EmailSentBroker();
    }

    @Test
    public void saveTest(){
        emailSentBroker.save(GenerateObjects.generateEmailSentRecord());
        Assert.assertEquals(1, emailSentBroker.getAllEmailSentRecords().size());
    }

    @Test
    public void findRecordByDateFileAndMessageNoneFoundTest(){
        Optional<EmailSentRecord> emailSentRecordOptional = emailSentBroker.findRecordByDateFileAndMessage(new Date(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString());

        Assert.assertFalse(emailSentRecordOptional.isPresent());
    }

    @Test
    public void findRecordByDateFileAndMessageOneFoundTest(){
        EmailSentRecord emailSentRecord = emailSentBroker.save(GenerateObjects.generateEmailSentRecord());

        Optional<EmailSentRecord> emailSentRecordOptional = emailSentBroker.findRecordByDateFileAndMessage(emailSentRecord.getDateSent(),
                emailSentRecord.getMessage(),
                emailSentRecord.getBatchFileName());

        Assert.assertTrue(emailSentRecordOptional.isPresent());
    }

    @Test
    public void findRecordByDateFileAndMessageOneFoundOutOfManyTest(){
        IntStream.range(0, 1000).forEach(item -> emailSentBroker.save(GenerateObjects.generateEmailSentRecord()));
        EmailSentRecord emailSentRecord = emailSentBroker.save(GenerateObjects.generateEmailSentRecord());

        Optional<EmailSentRecord> emailSentRecordOptional = emailSentBroker.findRecordByDateFileAndMessage(emailSentRecord.getDateSent(),
                emailSentRecord.getMessage(),
                emailSentRecord.getBatchFileName());

        Assert.assertTrue(emailSentRecordOptional.isPresent());
    }

    @Test
    public void findRecordByDateFileAndMessageNoneFoundOutOfManyTest(){
        IntStream.range(0, 1000).forEach(item -> emailSentBroker.save(GenerateObjects.generateEmailSentRecord()));
        EmailSentRecord emailSentRecord = GenerateObjects.generateEmailSentRecord();

        Optional<EmailSentRecord> emailSentRecordOptional = emailSentBroker.findRecordByDateFileAndMessage(emailSentRecord.getDateSent(),
                emailSentRecord.getMessage(),
                emailSentRecord.getBatchFileName());

        Assert.assertFalse(emailSentRecordOptional.isPresent());
    }


}
