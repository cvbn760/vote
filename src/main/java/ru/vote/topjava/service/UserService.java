package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vote.topjava.model.User;
import ru.vote.topjava.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Достать всех пользователей
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // Достать только администраторов ресторанов
    public List<User> findAdministrators(){
        return userRepository.findAdministrators();
    }

    // Достать только обычных пользователей
    public List<User> findOtherUsers(){
        return userRepository.findOtherUsers();
    }

    // Достать одного любого пользователя
    public User getUserById(int id){
        return userRepository.findById(id);
    }
}
