package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vote.topjava.model.Menu;
import ru.vote.topjava.model.Voter;
import ru.vote.topjava.repository.MenuRepository;

import java.time.LocalDate;

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

    // Получить меню с наибольшим количеством очков в данный день
    public Menu getBestMenuByDate(LocalDate dateTime){
        return menuRepository.getBestMenuByDate(dateTime);
    }

    // Получить меню с наименьшим количеством очков в данный день
    public Menu geMenuLoserByDate(LocalDate dateTime){
        return menuRepository.getMenuLoserByDate(dateTime);
    }
}
