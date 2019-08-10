package com.github.staticORdynamicProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class Client3 {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 动态代理第二种方式
        /**
         * 使用JDK动态代理的五大步骤:
         * 1.通过实现InvocationHandler接口来自定义自己的InvocationHandler;
         * 2.通过Proxy.getProxyClass获得动态代理类
         * 3.通过反射机制获得代理类的构造方法，方法签名为getConstructor(InvocationHandler.class)
         * 4.通过构造函数获得代理对象并将自定义的InvocationHandler实例对象传为参数传入
         * 5.通过代理对象调用目标方法
         * */
        // 1、生成$Proxy0的class文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // 2、获取动态代理类
        Class proxyClazz = Proxy.getProxyClass(UserService.class.getClassLoader(),UserService.class);
        // 3、获得代理类的构造函数，并传入参数类型InvocationHandler.class (这里不能传自己实现的Handler！)
        Constructor constructor = proxyClazz.getConstructor(InvocationHandler.class);
        // 4、通过构造函数来创建动态代理对象，将自定义的LogHandler实例传入
        UserService proxy = (UserService) constructor.newInstance(new LogHandler(new UserServiceImpl()));

        proxy.select();
        proxy.update();
    }
}
