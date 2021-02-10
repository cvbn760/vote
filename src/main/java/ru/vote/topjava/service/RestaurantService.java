package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.vote.topjava.model.Restaurant;
import ru.vote.topjava.repository.RestaurantRepository;
import ru.vote.topjava.util.SecurityUtil;

import java.util.List;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // Достать все рестораны (Доступно всем)
    public List<Restaurant> getAllRestaurants(Integer id) {
        return (id == null) ? restaurantRepository.findAll() : restaurantRepository.findAllByIdOwnerRest(id);
    }

    // Достать ресторан по id (Доступно всем)
    public Restaurant getRestaurantById(int id){
        return restaurantRepository.findById(id).get();
    }

    // Удалить ресторан по id (Доступно только автору)
    public void delete(int id, long authUserId){
        Restaurant restaurant = restaurantRepository.findById(id).get();
        if (restaurant.getIdOwnerRest() != authUserId){
            throw new AccessDeniedException("You cannot edit restaurant because restaurant does not exist or you do not have access...");
        }
        restaurantRepository.deleteById(id);
    }

    // Получить заготовку ресторана (Доступно только администраторам)
    public Restaurant getNewRest(long authUserId) {
        Restaurant restaurant = new Restaurant((int) authUserId, "name rest", "address", SecurityUtil.safeGet().getUser());
        return restaurant;
    }

    // Сохранить или обновить ресторан (Доступно только автору)
    public Restaurant createOrUpdate(Restaurant restaurant, long authUserId){
        if (restaurant.getIdOwnerRest() == authUserId) {
            return restaurantRepository.save(restaurant);
        }
        throw new AccessDeniedException("You cannot edit restaurant because restaurant does not exist or you do not have access...");
    }
}
