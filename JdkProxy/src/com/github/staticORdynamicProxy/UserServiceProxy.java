package com.github.staticORdynamicProxy;

import java.util.Date;

public class UserServiceProxy implements UserService {
    //静态代理：
    //
    private UserService target;
    public UserServiceProxy(UserService target) {
        this.target = target;
    }

    @Override
    public void select() {
        before();
        target.select();
        after();
    }
    @Override
    public void update() {
        before();
        target.update();
        after();
    }

    private void before() {
        System.out.println(String.format("log start time [%s] ", new Date()));
    }
    private void after() {
        System.out.println(String.format("log end time [%s]", new Date()));
    }
}
