package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vote.topjava.model.User;
import ru.vote.topjava.repository.UserRepository;
import ru.vote.topjava.util.AuthorizedUser;

import java.util.List;

@Service("userService")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Достать всех пользователей (Доступно всем)
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // Достать только администраторов ресторанов (Доступно всем)
    public List<User> findAdministrators(){
        return userRepository.findAdministrators();
    }

    // Достать только обычных пользователей (Доступно всем)
    public List<User> findOtherUsers(){
        return userRepository.findOtherUsers();
    }

    // Достать одного любого пользователя (Доступно всем)
    public User getUserById(int id){
        return userRepository.findById(id).get();
    }

    // Удалить пользователя (Доступно только владельцу аккаунта)
    public void delete(long id){
        userRepository.deleteById((int) id);
    }

    // Сохранить или обновить пользователя (Сохранение доступно анонимным пользователям. Обновление доступно только владельцу аккаунта)
    public User createOrUpdate(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new BadCredentialsException("User " + email + " is not found");
        }

        return new AuthorizedUser(user);
    }
}
