package ru.vote.topjava.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vote.topjava.model.Menu;
import ru.vote.topjava.service.MenuService;
import ru.vote.topjava.util.SecurityUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = MenuController.MENU_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {
    static final String MENU_URL = "/rest/menus";

    @Autowired
    private MenuService menuService;

    // Достать вообще все меню / Достать все меню за конкретную дату / Достать меню конкретного ресторана (Доступно всем)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Menu> getAllMenus(@RequestParam(name = "date", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                  @RequestParam(name = "id", required = false) Integer id){
        List<Menu> menuList = Collections.emptyList();
        menuList = (date == null) ? menuService.getAllMenus() : menuService.getAllMenuForDay(date);
        if (id != null) {
            menuList = menuList.stream().filter(menu -> menu.getIdRest() == id).collect(Collectors.toList());
        }
        return menuList;
    }

    // Достать меню с максимальным(минимальным) количеством голосов в требуемый день (Доступно всем)
    @RequestMapping(value = "/get_win/{type}", method = RequestMethod.GET)
    public Menu getVotingWinner(@RequestParam(name = "date", required = false)
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                @PathVariable(name = "type", required = true) Integer type){
        return menuService.getTypeMenuByDate(date, type);
    }

    // Получить заготовку меню (Доступно только администратору ресторана)
    @RequestMapping(value = "/new_menu/{id_rest}", method = RequestMethod.GET)
    public Menu getNewMenu(@PathVariable(name = "id_rest", required = true) Integer id){
        return menuService.getNewMenu(id, SecurityUtil.authUserId());
    }

    // Сохранить или обновить меню (Доступно только автору)
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createOrUpdate(@RequestBody Menu menu){
        menuService.createOrUpdate(menu, SecurityUtil.authUserId());
        return ResponseEntity.ok(menu);
    }

    // Удалить меню (Доступно только автору)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        menuService.delete(id, SecurityUtil.authUserId());
    }
}
