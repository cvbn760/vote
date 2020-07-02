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

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public User getUser(long id) {
       return userRepository.getOne(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
