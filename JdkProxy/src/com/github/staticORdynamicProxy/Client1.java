package com.github.staticORdynamicProxy;

public class Client1 {
    /*
     * 典型的静态代理
     */
    public static void main(String[] args) {
        UserService userServiceImpl = new UserServiceImpl();
        UserService userServiceProxy = new UserServiceProxy(userServiceImpl);

        userServiceProxy.select();
        userServiceProxy.update();
    }
}
