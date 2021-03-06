package ru.vote.topjava.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vote.topjava.model.Restaurant;
import ru.vote.topjava.service.RestaurantService;
import ru.vote.topjava.util.SecurityUtil;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.RESTAURANT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String RESTAURANT_URL = "/rest";

    @Autowired
    private RestaurantService restaurantService;

    // Достать вообще все рестораны / достать все рестораны по id владельца ресторана (Доступно для всех)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Restaurant> getAll(@RequestParam(value = "id_owner", required = false) Integer id){
        return restaurantService.getAllRestaurants(id);
    }

    // Достать ресторан по id (Доступно для всех)
    @RequestMapping(value = "/all/{id}", method = RequestMethod.GET)
    public Restaurant get(@PathVariable int id) {
        return restaurantService.getRestaurantById(id);
    }

    // Удалить ресторан по id (Доступно только автору)
    @RequestMapping(value = "/admin/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        restaurantService.delete(id, SecurityUtil.authUserId());
    }

    // Получить заготовку ресторана (Доступно только администраторам ресторанов)
    @RequestMapping(value = "/admin/new_rest", method = RequestMethod.GET)
    public Restaurant getNewRest(){
        return restaurantService.getNewRest(SecurityUtil.authUserId());
    }

    // Сохранить ресторан / Обновить ресторан (Доступно только администраторам ресторанов)
    @RequestMapping(value = "/admin/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createOrUpdate(@RequestBody Restaurant restaurant){
        restaurantService.createOrUpdate(restaurant, SecurityUtil.authUserId());
        return ResponseEntity.ok(restaurant);
    }
}