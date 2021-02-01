package ru.vote.topjava.util;


import ru.vote.topjava.model.User;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoles());
        this.user = user;
    }

    public int getId(){
        return user.getId();
    }
}
