package com.kcbierco.core;

import com.google.inject.Guice;
import com.kcbierco.db.DBConnectionManager;
import com.kcbierco.db.EmailSentBroker;
import com.kcbierco.fixture.GenerateObjects;
import com.kcbierco.fixture.MockEmailService;
import com.kcbierco.fixture.MockServiceInjector;
import com.kcbierco.fixture.ResourceFileFinder;

import com.kcbierco.models.ExcelParsingConfig;
import com.kcbierco.parser.ConfigLoader;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Richard on 2/6/16.
 */
public class ReminderManagerTest {

    protected ReminderManager reminderManager;

    @Before
    public void setUp() throws IOException {
        reminderManager = Guice.createInjector(new MockServiceInjector()).getInstance(ReminderManager.class);
        DBConnectionManager.init(ResourceFileFinder.findFileInResources("database.properties"));;
    }

    @After
    public void cleanUp() throws IOException {
        new EmailSentBroker().deleteAllFromDB();
        MockEmailService.clearCounter();
    }

    @Test
    public void saveRecordTest(){
        reminderManager.saveRecord(new Date(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        Assert.assertEquals(1, new EmailSentBroker().getAllEmailSentRecords().size());
    }

    @Test
    public void emailSentIsSentTest(){
        String baseMessage = "wat";
        String tankInfo = "Tank 34";
        String fileName = "DoppleAlt.xls";

        reminderManager.saveRecord(new Date(), reminderManager.generateMessage(baseMessage, tankInfo), fileName);

        Assert.assertTrue(reminderManager.emailSent(new Date(), tankInfo, baseMessage, fileName));
    }

    @Test
    public void emailSentIsNotSentTest(){
        String baseMessage = "wat";
        String tankInfo = "Tank 34";
        String fileName = "DoppleAlt.xls";

        reminderManager.saveRecord(new Date(), reminderManager.generateMessage(baseMessage, tankInfo), fileName + "nope");

        Assert.assertFalse(reminderManager.emailSent(new Date(), tankInfo, baseMessage, fileName));
    }

    @Test
    public void sendEmailIfDateIsTodayNoneSentTest() throws IOException {
        List<Date> dates = GenerateObjects.genDateListAllInTheFuture();
        String tankInfo = "Tank 123";
        String baseMessage = "Dude check some stuff";
        String fileName = "DoppleAltIsYummy.xls";
        ExcelParsingConfig excelParsingConfig = ConfigLoader.loadTheConfig(ResourceFileFinder.findFileInResources("config.json"));

        reminderManager.sendEmailIfDateIsToday(dates, tankInfo, baseMessage, fileName, excelParsingConfig);

        Assert.assertEquals(0, MockEmailService.getNumberOfEmailsSent());
    }

    @Test
    public void sendEmailIfDateIsTodayOneSentTest() throws IOException {
        List<Date> dates = GenerateObjects.genDateListSomeToday(1);
        String tankInfo = "Tank 123";
        String baseMessage = "Dude check some stuff";
        String fileName = "DoppleAltIsYummy.xls";
        ExcelParsingConfig excelParsingConfig = ConfigLoader.loadTheConfig(ResourceFileFinder.findFileInResources("config.json"));

        reminderManager.sendEmailIfDateIsToday(dates, tankInfo, baseMessage, fileName, excelParsingConfig);

        Assert.assertEquals(1, MockEmailService.getNumberOfEmailsSent());
    }

    @Test
    public void sendEmailIfDateIsTodayTwoSentDifferentFileNameTest() throws IOException {
        List<Date> dates = GenerateObjects.genDateListSomeToday(1);
        String tankInfo = "Tank 123";
        String baseMessage = "Dude check some stuff";
        String fileName = "DoppleAltIsYummy.xls";
        ExcelParsingConfig excelParsingConfig = ConfigLoader.loadTheConfig(ResourceFileFinder.findFileInResources("config.json"));

        reminderManager.sendEmailIfDateIsToday(dates, tankInfo, baseMessage, fileName, excelParsingConfig);
        reminderManager.sendEmailIfDateIsToday(dates, tankInfo, baseMessage, fileName + ".yum", excelParsingConfig);

        Assert.assertEquals(2, MockEmailService.getNumberOfEmailsSent());
    }

    @Test
    public void sendEmailIfDateIsTodayTwoSentDifferentBaseMessageTest() throws IOException {
        List<Date> dates = GenerateObjects.genDateListSomeToday(1);
        String tankInfo = "Tank 123";
        String baseMessage = "Dude check some stuff";
        String fileName = "DoppleAltIsYummy.xls";
        ExcelParsingConfig excelParsingConfig = ConfigLoader.loadTheConfig(ResourceFileFinder.findFileInResources("config.json"));

        reminderManager.sendEmailIfDateIsToday(dates, tankInfo, baseMessage, fileName, excelParsingConfig);
        reminderManager.sendEmailIfDateIsToday(dates, tankInfo, baseMessage + " hurry!", fileName, excelParsingConfig);

        Assert.assertEquals(2, MockEmailService.getNumberOfEmailsSent());
    }

    @Test
    public void sendEmailIfDateIsTodayTwoSentDifferentTankInfoTest() throws IOException {
        List<Date> dates = GenerateObjects.genDateListSomeToday(1);
        String tankInfo = "Tank 123";
        String baseMessage = "Dude check some stuff";
        String fileName = "DoppleAltIsYummy.xls";
        ExcelParsingConfig excelParsingConfig = ConfigLoader.loadTheConfig(ResourceFileFinder.findFileInResources("config.json"));

        reminderManager.sendEmailIfDateIsToday(dates, tankInfo, baseMessage, fileName, excelParsingConfig);
        reminderManager.sendEmailIfDateIsToday(dates, tankInfo + " Tank 2", baseMessage, fileName, excelParsingConfig);

        Assert.assertEquals(2, MockEmailService.getNumberOfEmailsSent());
    }


    @Test
    public void sendReminderIfNeededTest() throws IOException {
        String file = ResourceFileFinder.findFileInResources("config.json");
        String parentDir = new File(file).getParent();
        ExcelParsingConfig excelParsingConfig = ConfigLoader.loadTheConfig(file);
        excelParsingConfig.setActiveBatchDirectory(parentDir);

        reminderManager.sendReminderIfNeeded(excelParsingConfig);

        Assert.assertEquals(1, MockEmailService.getNumberOfEmailsSent());
    }






}
