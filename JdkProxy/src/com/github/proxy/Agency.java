package com.github.proxy;

import com.github.reflectionDemo.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Agency implements InvocationHandler {
    private Real sub;

    public Object bind(Real sub) {
        this.sub = sub;
        Object obj = Proxy.newProxyInstance(Test.class.getClassLoader(),sub.getClass().getInterfaces(),this);
        return obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 前置增强处理
        System.out.println("+++++++++Agency begin+++++++");
        method.invoke(sub, args);
        // 后置增强处理
        System.out.println("+++++++++Agency end+++++++");
        return null;
    }
}
