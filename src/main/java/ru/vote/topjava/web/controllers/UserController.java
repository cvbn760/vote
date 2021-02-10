package ru.vote.topjava.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import ru.vote.topjava.model.Role;
import ru.vote.topjava.model.User;
import ru.vote.topjava.service.UserService;
import ru.vote.topjava.util.SecurityUtil;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = UserController.USER_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    static final String USER_URL = "/users/*";

    @Autowired
    private UserService userService;

    // Достать пользователя по id (Доступно всем авторизированным пользователям)
    @RequestMapping(value = "/auth/{id}", method = RequestMethod.GET)
    public User get(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // Достать всех пользователей (Доступно всем)
    @RequestMapping(value = "/auth/all", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    // Удалить пользователя (Доступно только самому пользователю)
    @RequestMapping(value = "/auth/delete_user", method = RequestMethod.DELETE)
    public void delete(){
        userService.delete(SecurityUtil.authUserId());
    }

    // Получить пользователей (Доступно всем)
    @RequestMapping(value = "/auth/get_us", method = RequestMethod.GET)
    public List<User> getUsers(@RequestParam(name = "option", required = true) String option){
        List<User> userList = Collections.emptyList();
        switch (option) {
            case "user":
                userList = userService.findOtherUsers();
                break;
            case "admin":
                userList = userService.findAdministrators();
                break;
            default:
                break;
        }
        return userList;
    }

    // Получить заготовку пользователя (Доступно только при регистрации для анонимных пользователей)
    @RequestMapping(value = "/anon/new_user", method = RequestMethod.GET)
    public User getNewUser(@RequestParam(name = "type", required = true) int type ){
        User user = new User(); 
        user.setName("name");
        user.setEmail("email");
        user.setPassword("password");
        switch (type){
            case 1:
                user.setRoles(Collections.singleton(Role.ADMIN));
                break;
            default:
                user.setRoles(Collections.singleton(Role.USER));
                break;
        }
        return user;
    }

    // Сохранить или обновить пользователя (Сохранение доступно только анонимным пользователям. Обновление доступно только владельцу аккаунта)
    @RequestMapping(value = "/all/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus createOrUpdate(@RequestBody User user) {
        if (SecurityUtil.canEdit(user)) {
            userService.createOrUpdate(user);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
