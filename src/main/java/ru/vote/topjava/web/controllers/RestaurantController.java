package ru.vote.topjava.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vote.topjava.model.Meal;
import ru.vote.topjava.model.Restaurant;
import ru.vote.topjava.model.Role;
import ru.vote.topjava.model.User;
import ru.vote.topjava.service.RestaurantService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.RESTAURANT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String RESTAURANT_URL = "/rest/rest";

    @Autowired
    private RestaurantService restaurantService;

    // Достать вообще все рестораны / достать все рестораны по id владельца ресторана
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Restaurant> getAll(@RequestParam(value = "id", required = false) Integer id){
        return restaurantService.getAllRestaurants(id);
    }

    // Достать ресторан по id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Restaurant get(@PathVariable int id) {
        return restaurantService.getRestaurantById(id);
    }

    // Удалить ресторан по id
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        restaurantService.delete(id);
    }

    // Получить заготовку ресторана
    @RequestMapping(value = "/new_rest", method = RequestMethod.GET)
    public Restaurant getNewRest(){
        return restaurantService.getNewRest();
    }

    // Сохранить ресторан / Обновить ресторан
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createOrUpdate(@RequestBody Restaurant restaurant){
        restaurantService.createOrUpdate(restaurant);
        return ResponseEntity.ok(restaurant);
    }
}