package com.github.staticORdynamicProxy;

public class Client1 {
    public static void main(String[] args) {
        UserService userServiceImpl = new UserServiceImpl();
        UserService userServiceProxy = new UserServiceProxy(userServiceImpl);

        userServiceProxy.select();
        userServiceProxy.update();
    }
}
