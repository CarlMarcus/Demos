package com.github.staticORdynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

public class LogHandler implements InvocationHandler {
    Object target; // 被代理的对象，实际的方法执行者
    public LogHandler(Object target) { //
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    private void before() {
        System.out.println(String.format("log start time [%s]", new Date()));
    }
    private void after() {
        System.out.println(String.format("log end time [%s]", new Date()));
    }
}
