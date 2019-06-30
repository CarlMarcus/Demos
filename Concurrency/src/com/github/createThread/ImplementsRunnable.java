package com.github.createThread;

public class ImplementsRunnable implements Runnable {
    @Override
    public void run() {
        for (int i=0;i<20;i++) {
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<30;i++) {
            System.out.println(Thread.currentThread().getName()+" "+i);
            if (i==15) {
                ImplementsRunnable runnable = new ImplementsRunnable();
                new Thread(runnable,"thread 0").start(); //run不会开启新线程，顺序执行，两个都打印主线程名main
                new Thread(runnable,"thread 1").start(); //start会开启新线程，线程0和1都打印自己的线程名
                Thread.sleep(3);
            }
        }
    }
}
