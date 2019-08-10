package com.github.proxy;

public class Test {
    public static void main(String[] args) {
        // 设置系统参数，输出动态生成的代理类
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Agency agency = new Agency();
        Real real = new Real();
        // 绑定被代理对象，返回代理对象
        Object obj = agency.bind(real);
        Subject sub = (Subject) (obj);
        sub.doSomething();
        DupSubject dubSub = (DupSubject) (obj);
        dubSub.doSomethingAgain();
    }
}
