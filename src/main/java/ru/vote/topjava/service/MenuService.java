package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.vote.topjava.model.Menu;
import ru.vote.topjava.model.Restaurant;
import ru.vote.topjava.model.Voter;
import ru.vote.topjava.repository.MenuRepository;
import ru.vote.topjava.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    // Достать меню по id (доступно для всех)
    public Menu getMenuById(int id){
        return menuRepository.findById(id).get();
    }

    // Получить меню и итоги голосования за конкретный день (Доступно для всех)
    public Menu getMenuByDate(Voter voter){
        return menuRepository.getMenuByDate(voter.getDate());
    }

    // Получить меню с наибольшим(наименьшим) количеством очков в данный день (Доступно для всех)
    public List<Menu> getTypeMenuByDate(LocalDate dateTime, int type){
        return (type == 1) ? menuRepository.getBestMenuByDate(dateTime) : menuRepository.getMenuLoserByDate(dateTime);
    }

    // Получить все меню за конкретный день (Доступно для всех)
    public List<Menu> getAllMenuForDay(LocalDate date){
        return menuRepository.getMenusByDate(date);
    }

    // Достать вообще все меню (Доступно для всех)
    public  List<Menu> getAllMenus(){
        return menuRepository.findAll();
    }

    // Сохранить или обновить меню (Доступно только автору)
    public Menu createOrUpdate(Menu menu, long authUserId){
        if (!canEdit(menu.getIdRest(), authUserId)){
            return null;
        }
        if (menu.getIdMenu() == null) {
            return menuRepository.save(menu);
        }
        return (menuRepository.update(menu.getDate(), menu.getIdMenu(), menu.getIdRest()) == 1) ? menu : null;
    }

    // Можно ли редактировать меню текущему пользователю
    private boolean canEdit(int restId, long authUserId){
        List<Restaurant> restaurant = Collections.singletonList(restaurantRepository.findById(restId).get());
        if (restaurant.isEmpty() || restaurant.get(0).getAdminRest().getId() != authUserId){
            throw new AccessDeniedException("You cannot edit menu because the menu or restaurant does not exist or you do not have access...");
        }
        return true;
    }

    // Удалить меню по id меню и id текущего пользователя (Доступно только автору)
    public void delete(int menuId, long authUserId) {
        Menu menu = menuRepository.getOne(menuId);
        if (menu != null && canEdit(menu.getIdRest(), authUserId)){
            menuRepository.deleteById(menuId);
        }
    }

    // Получить заготовку меню
    public Menu getNewMenu(int restId, long authUserId){
        if (canEdit(restId, authUserId)){
            Menu menu = new Menu(restId, LocalDate.now());
            menu.setMealList(Collections.emptyList());
            return menu;
        }
        return null;
    }
}