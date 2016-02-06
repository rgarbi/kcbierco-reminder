package com.kcbierco.fixture;

import com.kcbierco.models.EmailSentRecord;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * Created by Richard on 2/6/16.
 */
public class GenerateObjects {

    public static EmailSentRecord generateEmailSentRecord(){
        EmailSentRecord emailSentRecord = new EmailSentRecord();
        emailSentRecord.setDateSent(new Date());
        emailSentRecord.setBatchFileName(UUID.randomUUID().toString());
        emailSentRecord.setMessage(UUID.randomUUID().toString());
        emailSentRecord.setSentTimeStamp(System.currentTimeMillis());
        return emailSentRecord;
    }

    public static EmailSentRecord generateEmailSentRecord(Date date){
        EmailSentRecord emailSentRecord = generateEmailSentRecord();
        emailSentRecord.setDateSent(date);
        return emailSentRecord;
    }

    public static List<Date> genDateListAllInTheFuture(){
        List<Date> allDates = new ArrayList<>();
        IntStream.range(0, 100).forEach(item -> {
            LocalDateTime localDate = LocalDateTime.now();
            localDate = localDate.plusDays(RandomUtils.nextLong(3, 3000));
            allDates.add(Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant()));
        });

        return allDates;
    }

    public static List<Date> genDateListSomeToday(int numberToday){
        List<Date> allDates = genDateListAllInTheFuture();
        IntStream.range(0, numberToday).forEach(item -> {
            allDates.add(new Date());
        });

        return allDates;
    }
}
