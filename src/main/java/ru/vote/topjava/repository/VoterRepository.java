package ru.vote.topjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vote.topjava.model.Voter;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface VoterRepository extends CrudRepository<Voter, Integer>, JpaRepository<Voter, Integer>{

    // Достать отчет по голосованиям для конкретного пользователя
    @Query("SELECT v FROM Voter v WHERE v.voterIdPk=:voterIdPk ORDER BY v.date DESC")
    public List<Voter> getReportForUser(@Param("voterIdPk") int voterIdPk);

    // Достать результат голосования для конкретного пользователя за конкретный день
    @Query("SELECT v FROM Voter v WHERE v.voterIdPk=:voterIdPk AND v.date=:date")
    public Voter getVoterPerDay(@Param("voterIdPk") int id,@Param("date") LocalDate date);

    // Достать результат по menu_id и date
    @Query("SELECT v FROM Voter v WHERE v.menuId=:menuId AND v.date=:date")
    public Voter getVoteByMenuIdAndDate(@Param("menuId") int menuId, @Param("date") LocalDate date);

    // Добавить или обновить запись о голосовании за меню
    @Modifying
    @Query(value = "MERGE INTO voters v USING (VALUES (?1, ?2, ?3, ?4, null))" +
            " AS vals(date, voice, menu_id, voter_id_pk, v_id)" +
            " ON v.date = vals.date AND v.menu_id = vals.menu_id" +
            " AND v.voter_id_pk = vals.voter_id_pk" +
            " WHEN MATCHED THEN UPDATE SET v.voice = vals.voice" +
            " WHEN NOT MATCHED THEN INSERT VALUES vals.v_id, vals.menu_id,  vals.voter_id_pk, vals.date, vals.voice;"
            , nativeQuery = true)
    int insertOrUpdate(LocalDate date, boolean voice, int menuId, int voterIdPk);


}
