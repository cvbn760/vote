package ru.vote.topjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vote.topjava.model.Menu;

import java.time.LocalDate;

@Repository
@Transactional
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    // Получить меню и итоги голосования за конкретный день
    @Query("SELECT m FROM Menu m WHERE m.date=:dateTime")
    Menu getMenuByDate(@Param("dateTime") LocalDate date);

    // Получить меню и итоги голосования за конкретный день для конкретного ресторана
    @Query("SELECT m FROM Menu m INNER JOIN Voter v ON v.menuId = m.idMenu AND v.voterIdPk=:uId AND m.idMenu=:mId")
    Menu getMenuForUserByVote(@Param("uId") int uId, @Param("mId") int mId);

    // Получить меню с наибольшим количеством очков в данный день
    @Query("SELECT m FROM Menu m WHERE m.counterVoice = (SELECT max (men.counterVoice) FROM Menu men WHERE men.date=:date)")
    Menu getBestMenuByDate(@Param("date") LocalDate date);

    // Получить меню с наименьшим количеством очков в данный день
    @Query("SELECT m FROM Menu m WHERE m.counterVoice = (SELECT min (men.counterVoice) FROM Menu men WHERE men.date=:date)")
    Menu getMenuLoserByDate(@Param("date") LocalDate date);
}
