package com.jt.util;

import com.jt.pojo.User;

public class UserLocalThread {

    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void addUser(User user){
        threadLocal.set(user);
    }

    public static User getUser(){
        return threadLocal.get();
    }

    public static void removeUser(){
        threadLocal.remove();
    }

}
