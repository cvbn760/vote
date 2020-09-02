package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vote.topjava.model.Restaurant;
import ru.vote.topjava.repository.RestaurantRepository;

import java.util.List;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // Достать все рестораны
    public List<Restaurant> getAllRestaurants(){
        return restaurantRepository.findAll();
    }

    // Достать ресторан по id
    public Restaurant getRestaurantById(int id){
        return restaurantRepository.findById(id).get();
    }


}
