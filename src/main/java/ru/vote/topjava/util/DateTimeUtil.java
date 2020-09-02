package ru.vote.topjava.util;

import java.time.LocalDate;

public class DateTimeUtil {

    // Проверка даты голосования
    // - Изменять голос для меню на сегодня можно не позднее 11:00 текущего дня
    // - Изменять голос для меню на любой следующий день можно не позднее 11:00 того дня, на который это меню запланировано
    // - Изменять голос для меню за прошедшие дни нельзя
    public static boolean canVote(LocalDate  dateOne, LocalDate dateTwo){
        //if (dateOne.isEqual(dateTwo) && dateOne.isAfter(LocalDate.now())){ // Проверка текущего дня
        if (dateOne.isEqual(dateTwo)){ // Без проверки текущего дня
            return true;
        }
        return false;
    }
}
