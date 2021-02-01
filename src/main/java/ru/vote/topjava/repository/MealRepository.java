package ru.vote.topjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vote.topjava.model.Meal;
import ru.vote.topjava.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface MealRepository extends JpaRepository<Meal, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM meals WHERE meal_id IN(\n" +
                   "SELECT meal_id FROM meals ml \n" +
                   "INNER JOIN menus ms ON ms.id_menu=ml.menu_id \n" +
                   "INNER JOIN restaurants rest ON rest.id_rest=ms.id_rest\n" +
                   "INNER JOIN users us ON us.id=rest.id_owner_rest " +
                   "AND ml.meal_id=:id AND rest.id_owner_rest=:authUserId)", nativeQuery = true)
    void delete(@Param("id") int id, @Param("authUserId") long authUserId);

    @Query(value = "SELECT ml FROM Meal ml INNER JOIN Menu mn ON ml.menuId=mn.idMenu AND mn.date=:date")
    List<Meal> getMealByDate(@Param("date") LocalDate date);


}

