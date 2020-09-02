package ru.vote.topjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vote.topjava.model.Meal;

@Repository
@Transactional
public interface MealRepository extends JpaRepository<Meal, Integer> {
//
//    @Modifying
//    @Transactional
//    @Query("INSERT INTO meals m (meal_id, menu_id, name, description, description, calories, price)")

    //
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
//    int delete(@Param("id") int id, @Param("userId") int userId);

}

