package ru.vote.topjava.util;

import ru.vote.topjava.model.Role;
import ru.vote.topjava.model.User;

import java.util.Set;

public class SecurityUtil {

    private static User user;

    public static void setUser(User user){
        SecurityUtil.user = user;
    }

    public static Set<Role> roleAuthUser() {
        return user.getRoles();
    }

    public static boolean isAdmin(){
        return roleAuthUser().contains(Role.ADMIN);
    }

    public static long authUserId() {
        return user.getId();
    }

    public static User getUser(){
        return user;
    }
}
