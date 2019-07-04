package com.github.JoinWaitSleepInterrupt;

public class JoinDemo implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" started");
        try {
            Thread.sleep(4*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ended "+Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(new JoinDemo(), "A");
        Thread t2 = new Thread(new JoinDemo(), "B");
        Thread t3 = new Thread(new JoinDemo(), "C");

        t1.start();
        //main暂停等待t1执行两秒
        //起到的是延迟两秒启动t2的效果
        try {
            t1.join(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.start();
        //main暂停等到t1执行结束
        //相当于t3等到t1执行完才开始
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t3.start();
        //暂停主线程等待所有子线程执行完
        try {
            t1.join(); t2.join(); t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads are dead, exiting main thread");
        System.out.println(System.currentTimeMillis()-start);
    }
}
