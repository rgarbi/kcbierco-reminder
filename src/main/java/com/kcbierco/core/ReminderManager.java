package com.kcbierco.core;

import com.kcbierco.db.DBConnectionManager;
import com.kcbierco.db.EmailSentBroker;
import com.kcbierco.mailer.EmailService;
import com.kcbierco.models.EmailSentRecord;
import com.kcbierco.models.ExcelParsingConfig;
import com.kcbierco.parser.ExcelFileReader;
import com.kcbierco.parser.ExcelFormatNotSupportedException;
import com.kcbierco.parser.WorksheetNotFoundException;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
/**
 * Created by Richard on 1/31/16.
 */
public class ReminderManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReminderManager.class);

    private final EmailService emailService;

    @Inject
    public ReminderManager(EmailService emailService){
        this.emailService = emailService;
    }

    public void sendReminderIfNeeded(ExcelParsingConfig excelParsingConfig) throws IOException {

        List<String> allExcelFiles = new FileManager()
                .getAllExcelFilesInADirectory(excelParsingConfig.getActiveBatchDirectory());
        LOGGER.info("Found " + allExcelFiles.size() + " in " + excelParsingConfig.getActiveBatchDirectory());

        excelParsingConfig.getImportantDatesToWatch().stream().forEach(config -> allExcelFiles.stream().forEach(file -> {
            LOGGER.info("Parsing the file: " + file);
            ExcelFileReader excelFileReader = new ExcelFileReader(file, config);
            try {
                List<Date> checkDates = excelFileReader.parseDatesFromExcelFile();
                String tankInfo = excelFileReader.parseTankInfoFromExcelFile();
                sendEmailIfDateIsToday(checkDates,
                        tankInfo, config.getBaseEmailMessage(), file, excelParsingConfig);

            } catch (IOException | WorksheetNotFoundException | ExcelFormatNotSupportedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }));
    }

    protected void sendEmailIfDateIsToday(List<Date> allDates, String tankInfo, String baseMessage, String fileName, ExcelParsingConfig excelParsingConfig){
        //Is the date today?
        allDates.stream()
                .filter(date -> DateUtils.isSameDay(date, new Date()))
                .forEach(matchingDate -> {
                    if(!emailSent(matchingDate, tankInfo, baseMessage, fileName)){
                        LOGGER.info(String.format(
                                "An email has not been sent for for the file %s on this date %s with the message %s",
                                fileName, matchingDate, generateMessage(baseMessage, tankInfo)));
                        //Send the email!
                        try {
                            emailService.sendEmail(excelParsingConfig.getEmailRecipients(),
                                    generateMessage(baseMessage, tankInfo),
                                    generateMessage(baseMessage, tankInfo),
                                    excelParsingConfig.getSourceEmailAddress(),
                                    excelParsingConfig.getSourceEmailPassword());

                            LOGGER.info("Email sent, saving record");
                            saveRecord(matchingDate, generateMessage(baseMessage, tankInfo), fileName);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                });

        //Have we already sent an email for this date, message, and file name?
        //If not, send an email
    }


    protected boolean emailSent(Date date, String tankInfo, String baseMessage, String fileName){
        LOGGER.info("Checking to see if an email has been sent for this file (" + fileName + ") on this day -> " + date);
        EmailSentBroker emailSentBroker = new EmailSentBroker();
        Optional<EmailSentRecord> emailSentRecord = emailSentBroker.findRecordByDateFileAndMessage(date, generateMessage(baseMessage, tankInfo), fileName);
        return emailSentRecord.isPresent();
    }

    protected void saveRecord(Date date, String message, String fileName){
        EmailSentRecord emailSentRecord = new EmailSentRecord();
        emailSentRecord.setSentTimeStamp(System.currentTimeMillis());
        emailSentRecord.setDateSent(date);
        emailSentRecord.setMessage(message);
        emailSentRecord.setBatchFileName(fileName);
        new EmailSentBroker().save(emailSentRecord);
    }

    protected String generateMessage(String baseMessage, String tankInfo){
        return String.format("%s %s", baseMessage, tankInfo).toString();
    }

}
