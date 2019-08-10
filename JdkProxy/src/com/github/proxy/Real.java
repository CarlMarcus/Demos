package com.github.proxy;

public class Real implements Subject, DupSubject {
    @Override
    public void doSomething() {
        System.out.println("=========Real doSomething=========");
    }

    @Override
    public void doSomethingAgain() {
        System.out.println("=========Real doSomethingAgain=========");
    }
}
