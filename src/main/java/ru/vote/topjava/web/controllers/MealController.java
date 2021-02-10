package ru.vote.topjava.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vote.topjava.model.Meal;
import ru.vote.topjava.service.MealService;
import ru.vote.topjava.util.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MealController.MEAL_URL)
public class MealController {
    static final String MEAL_URL = "/meals/*";

    @Autowired
    private MealService mealService;

    // Достать еду по id (Доступно всем)
    @RequestMapping(value = "/all/{id}", method = RequestMethod.GET)
    public Meal get(@PathVariable int id) {
        return mealService.getMealById(id);
    }

    // Достать вообще всю еду / Достать еду за конкретную дату (Доступно всем)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Meal> getAllMeal(@RequestParam(name = "date", required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return mealService.getAllMeal(date);
    }

    // Удалить еду по id (Доступно только автору)
    @RequestMapping(value = "/admin/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        long authUserId = SecurityUtil.authUserId();
        mealService.deleteMealById(id, authUserId);
    }

    // Добавить новую еду / Обновить существующую еду (Доступно только автору)
    @RequestMapping(value = "/admin/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createOrUpdate(@RequestBody Meal meal){
        long authUserId = SecurityUtil.authUserId();
        mealService.addMealInMenu(meal, authUserId);
        return ResponseEntity.ok(meal);
    }

    // Получить заготовку еды
    @RequestMapping(value = "/admin/new_meal/{id_menu}", method = RequestMethod.GET)
    public Meal getNewMeal(@PathVariable(name = "id_menu", required = true) Integer id){
        return mealService.getNewMenu(id, SecurityUtil.authUserId());
    }
}
