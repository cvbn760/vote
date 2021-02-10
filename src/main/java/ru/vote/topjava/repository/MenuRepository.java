package ru.vote.topjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vote.topjava.model.Menu;

import java.time.LocalDate;
import java.util.List;

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
    List<Menu> getBestMenuByDate(@Param("date") LocalDate date);

    // Получить меню с наименьшим количеством очков в данный день
    @Query("SELECT m FROM Menu m WHERE m.counterVoice = (SELECT min (men.counterVoice) FROM Menu men WHERE men.date=:date)")
    List<Menu> getMenuLoserByDate(@Param("date") LocalDate date);

    List<Menu> getMenusByDate(LocalDate date);

    @Modifying
    @Query(value = "UPDATE menus mn SET mn.date=:date WHERE mn.id_menu=:idMenu AND mn.id_rest=:idRest", nativeQuery = true)
    int update(@Param("date") LocalDate date, @Param("idMenu") int idMenu, @Param("idRest") int idRest);
}
