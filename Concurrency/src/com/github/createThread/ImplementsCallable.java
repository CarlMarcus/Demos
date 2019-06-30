package com.github.createThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ImplementsCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int i=0;
        for(;i<50;i++) {
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
        return i;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ImplementsCallable callable = new ImplementsCallable();
        FutureTask<Integer> ft0 = new FutureTask<>(callable); // FutureTask包装Callable实现类实例
        FutureTask<Integer> ft1 = new FutureTask<>(callable);
        for (int i=0;i<50;i++) {
            System.out.println(Thread.currentThread().getName()+" "+i);
            if (i==15) {
                Thread th1 = new Thread(ft0,"subThread 0");
                Thread th2 = new Thread(ft1,"subThread 1");
                th1.start();th2.start();
                Thread.sleep(10);
            }
        }
        System.out.println("subThread 0 的返回值： "+ft0.get());
        System.out.println("subThread 1 的返回值： "+ft1.get());
    }
}
