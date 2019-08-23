package com.github.staticORdynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Client2 {
    public static void main(String[] args) {
        // 动态代理第一种方式
        /*
         * Proxy类中还有个将2~4步骤封装好的简便方法来创建动态代理对象，
         *其方法签名为：newProxyInstance(ClassLoader classloader,Class<?>[] interfaces, InvocationHandler h)
         */
        // 设置变量可以保存动态代理类，默认名称以 $Proxy0 格式命名
        //System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // 1. 创建被代理的对象，UserService接口的实现类
        UserService target = new UserServiceImpl();
        // 2. 获取对应的 ClassLoader
        ClassLoader classLoader = target.getClass().getClassLoader();
        // 3. 获取被代理对象所实现的所有接口。这里只有一个UserService
        Class[] interfaces = target.getClass().getInterfaces();
        // 4. 创建一个将传给代理类的调用请求处理器，处理所有的代理对象上的方法调用
        InvocationHandler logHandler = new LogHandler(target);
        /*
		   5.根据上面提供的信息，创建代理对象 在这个过程中，
               a.JDK会通过根据传入的参数信息动态地在内存中创建和.class 文件等同的字节码
               b.然后根据相应的字节码转换成对应的class，
               c.然后调用newInstance()创建代理实例
		 */
        UserService proxy = (UserService) Proxy.newProxyInstance(classLoader,interfaces,logHandler);
        proxy.select();
        proxy.update();
        ProxyUtils.generateClassFile(target.getClass(), "UserServiceProxy");
    }
}
