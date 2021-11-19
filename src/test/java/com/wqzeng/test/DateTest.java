package com.wqzeng.test;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTest {

    @Test
    public void dateTest() {
        Date date = DateUtils.addDays(new Date(),1);
        date=DateUtils.setHours(date,8);
        date=DateUtils.setMinutes(date,0);
        date=DateUtils.setSeconds(date,0);
        date=DateUtils.setMilliseconds(date,0);

        String d=LocalDateTime.of(LocalDate.now(), LocalTime.of(8,0)).format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println(d);
        Date date2=new Date(1634525940000L);
        System.out.println(date2);
    }

}
