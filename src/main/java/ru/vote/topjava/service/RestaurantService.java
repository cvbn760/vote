package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vote.topjava.model.Restaurant;
import ru.vote.topjava.model.User;
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
    public List<Restaurant> getAllRestaurants(Integer id) {
        return (id == null) ? restaurantRepository.findAll() : restaurantRepository.findAllByIdOwnerRest(id);
    }

    // Достать ресторан по id
    public Restaurant getRestaurantById(int id){
        return restaurantRepository.findById(id).get();
    }

    // Удалить ресторан по id
    public void delete(int id){
        restaurantRepository.deleteById(id);
    }

    // Получить заготовку ресторана (использовать Spring Security)
    public Restaurant getNewRest(){
        User admin = new User();
        Restaurant restaurant = new Restaurant(1,"name", "address", admin);
        return restaurant;
    }

    // Сохранить или обновить ресторан
    public Restaurant createOrUpdate(Restaurant restaurant){
        return restaurantRepository.save(restaurant);
    }
}
