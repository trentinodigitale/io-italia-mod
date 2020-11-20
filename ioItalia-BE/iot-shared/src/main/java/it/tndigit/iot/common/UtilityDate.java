package it.tndigit.iot.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class UtilityDate {
    public static LocalDate localDateOf(Date date){
        return  LocalDateTime.
                ofInstant(date.toInstant(),
                        ZoneId.of("Europe/Paris")).toLocalDate();
    }

}

