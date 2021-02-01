package ru.vote.topjava.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
    static final String USER_URL = "/rest/users/*";

    @Autowired
    private UserService userService;

    // Достать пользователя по id (Доступно всем)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User get(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // Достать всех пользователей (Доступно всем)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    // Удалить пользователя (Доступно только самому пользователю)
    @RequestMapping(value = "/delete_user", method = RequestMethod.DELETE)
    public void delete(){
        userService.delete(SecurityUtil.authUserId());
    }

    // Получить пользователей (Доступно всем)
    @RequestMapping(value = "/get_us", method = RequestMethod.GET)
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
    @RequestMapping(value = "/new_user/{type}", method = RequestMethod.GET)
    public User getNewUser(@PathVariable int type){
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
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createOrUpdate(@RequestBody User user){
        if ((SecurityUtil.getUser() != null && user.getId() != SecurityUtil.authUserId())  // Проанрка на владельца аккаунта
                || (user.getId() != null && SecurityUtil.getUser() == null)){ // Пытается ли аноним обновить чужую учетную запись
            throw new BadCredentialsException("You are trying to change an account that does not belong to you...");
        }
        userService.createOrUpdate(user);
        return ResponseEntity.ok(user);
    }
}
