package ru.vote.topjava.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vote.topjava.model.Role;
import ru.vote.topjava.model.User;
import ru.vote.topjava.service.UserService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = UserController.USER_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    static final String USER_URL = "/rest/users/*";

    @Autowired
    private UserService userService;

    // Достать пользователя по id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User get(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // Достать всех пользователей
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    // Удалить пользователя по id
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        userService.delete(id);
    }

    // Получить пользователей
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

    // Получить заготовку пользователя
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

    // Сохранить или обновить пользователя
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createOrUpdate(@RequestBody User user, @RequestParam(name = "id", required = false) Integer id){
        userService.createOrUpdate(user, id);
        return ResponseEntity.ok(user);
    }
}
