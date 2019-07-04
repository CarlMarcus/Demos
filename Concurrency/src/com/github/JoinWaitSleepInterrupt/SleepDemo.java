package com.github.JoinWaitSleepInterrupt;

public class SleepDemo extends Thread {
    @Override
    public void run() {
        for(int i=1;i<5;i++){
            try{
                Thread.sleep(500);//当前线程sleep，让出cpu资源，另一个线程就会由runnable变成running
            }catch(InterruptedException e) {
                System.out.println(e);
            }
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
    }

    public static void main(String[] args) {
        SleepDemo s1 = new SleepDemo();
        SleepDemo s2 = new SleepDemo();
        s1.start();s2.start();
    }
}
