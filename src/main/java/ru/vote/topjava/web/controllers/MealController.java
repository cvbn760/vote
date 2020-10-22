package ru.vote.topjava.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vote.topjava.model.Meal;
import ru.vote.topjava.service.MealService;
import ru.vote.topjava.util.SecurityUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = MealController.MEAL_URL)
public class MealController {
    static final String MEAL_URL = "/rest/meals/*";

    @Autowired
    private MealService mealService;

    // Достать еду по id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Meal get(@PathVariable int id) {
        return mealService.getMealById(id);
    }

    // Достать вообще всю еду / Достать еду за конкретную дату
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Meal> getAllMeal(@RequestParam(name = "date", required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return mealService.getAllMeal(date);
    }

    // Удалить еду по id
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        mealService.deleteMealById(id);
    }

    // Добавить новую еду / Обновить существующую еду
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createOrUpdate(@RequestBody Meal meal){
        mealService.addMealInMenu(meal);
        return ResponseEntity.ok(meal);
    }
}
