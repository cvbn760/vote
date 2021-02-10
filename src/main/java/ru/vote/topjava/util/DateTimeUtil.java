package ru.vote.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeUtil {

    public static boolean canEdit(LocalDate  dateMenu) {
        LocalDateTime now = LocalDateTime.now();
        if ((now.toLocalDate().isBefore(dateMenu)) | (now.toLocalDate().isEqual(dateMenu) && now.getHour() <= 11)) {
            return true;
        }
        else {
            return false;
        }
    }
}
