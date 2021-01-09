package com.library.nnamdi_azikiwe.util;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class TimeUtils {

    public Timestamp currentDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        long dateTime = zdt.toInstant().toEpochMilli();
        return new Timestamp(dateTime);
    }
}
