package ru.vote.topjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vote.topjava.model.Meal;
import ru.vote.topjava.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Modifying
    List<Restaurant> findAllByIdOwnerRest(int idOwnerRest);

    // Достать ресторан по id меню и id текущего пользователя
    @Modifying
    @Query(value = "SELECT rest FROM Restaurant rest " +
                   "INNER JOIN Menu mn ON mn.idRest = rest.idRest " +
                   "AND rest.idOwnerRest=:authUserId AND mn.idMenu=:idMenu")
    List<Restaurant> findRestaurantByMenuId(@Param("idMenu") int menuId, @Param("authUserId") int authUserId);
}
