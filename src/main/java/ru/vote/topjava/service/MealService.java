package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vote.topjava.model.Meal;
import ru.vote.topjava.model.Restaurant;
import ru.vote.topjava.repository.MealRepository;
import ru.vote.topjava.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MealService {

    private final MealRepository mealRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MealService(MealRepository mealRepository, RestaurantRepository restaurantRepository) {
        this.mealRepository = mealRepository;
        this.restaurantRepository = restaurantRepository;
    }

    // Добавить новую еду в меню (Доступно только автору)
    public Meal addMealInMenu(Meal meal, long authUserId){
        if (canEdit(meal.getMenuId(), authUserId)) {
            return mealRepository.save(meal);
        }
        return null;
    }

    // Можно ли редактировать еду текущему пользователю
    private boolean canEdit(int menuId, long authUserId){
        List<Restaurant> restaurant = restaurantRepository.findRestaurantByMenuId(menuId, (int) authUserId);
        if (restaurant.isEmpty()){
            throw new AccessDeniedException("You cannot edit meal because the menu or restaurant does not exist or you do not have access...");
        }
        return true;
    }

    // Достать еду по id (Доступно всем)
    public Meal getMealById(int id) {
        return mealRepository.findById(id).get();
    }

    // Удалить еду (Доступно только автору)
    public void deleteMealById(int id, long authUserId){
        Meal meal = mealRepository.getOne(id);
        if (meal != null && canEdit(meal.getMenuId(), authUserId)) {
            mealRepository.delete(id, authUserId);
        }
    }

    // Достать всю еду / Достать еду за конкретную дату (Доступно всем)
    public List<Meal> getAllMeal(LocalDate date) {
        if (date == null){
            return mealRepository.findAll();
        }
        return mealRepository.getMealByDate(date);
    }

    // Достать заготовку еды (Доступно только администраторам)
    public Meal getNewMenu(int menuId, long authUserId){
        if (canEdit(menuId, authUserId)){
            return new Meal(menuId, "new meal", "description", 0, 0.00f);
        }
        return null;
    }
}
