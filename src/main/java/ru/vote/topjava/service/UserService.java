package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vote.topjava.model.User;
import ru.vote.topjava.repository.UserRepository;
import ru.vote.topjava.util.AuthorizedUser;
import ru.vote.topjava.util.SecurityUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Set<GrantedAuthority> roles = new HashSet<>();
        user.getRoles().forEach(role -> roles.add(new SimpleGrantedAuthority(role.getAuthority())));

        SecurityUtil.setUser(user);
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), roles);
    }
}
