package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vote.topjava.model.Menu;
import ru.vote.topjava.model.Voter;
import ru.vote.topjava.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class MenuService {

    private MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    // Достать меню по id
    public Menu getMenuById(int id){
        return menuRepository.findById(id).get();
    }

    // Получить меню и итоги голосования за конкретный день
    public Menu getMenuByDate(Voter voter){
        return menuRepository.getMenuByDate(voter.getDate());
    }

    // Получить любое меню из тех, в голосовании за которое пользователь принимал участие
    public Menu getMenuForUserByVote(int userId, int menuId){
        return menuRepository.getMenuForUserByVote(userId, menuId);
    }

    // Получить меню с наибольшим(наименьшим) количеством очков в данный день
    public Menu getTypeMenuByDate(LocalDate dateTime, int type){
        return (type == 1) ? menuRepository.getBestMenuByDate(dateTime) : menuRepository.getMenuLoserByDate(dateTime);
    }

    // Получить все меню за конкретный день
    public List<Menu> getAllMenuForDay(LocalDate date){
        return menuRepository.getMenusByDate(date);
    }

    // Достать вообще все меню
    public  List<Menu> getAllMenus(){
        return menuRepository.findAll();
    }

    // Сохранить или обновить меню
    public Menu createOrUpdate(Menu menu){
        if (menu.getIdMenu() == null) {
            return menuRepository.save(menu);
        }
        return (menuRepository.update(menu.getDate(), menu.getIdMenu(), menu.getIdRest()) == 1) ? menu : null;
    }
}
