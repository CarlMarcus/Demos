package com.github.JoinWaitSleepInterrupt;

public class WaitNotifyDemo {
    static class Counter extends Thread {
        int total;
        @Override
        public void run() {
            //子线程拿到自己的对象锁才能for循环
            synchronized (this) {
                for (int i=0; i<10; i++) {
                    total += i;
                }
                notify();// 唤醒在c对象锁上等待的线程，比如刚刚的main，从等待队列到同步队列
                // 都开始竞争c对象锁，但要等当前线程结束才有可能抢到
            }
        }
    }

    public static void main(String[] args) {
        Counter c = new Counter();
        c.start();

        synchronized (c) {
            try {
                System.out.println("Waiting for counter to complete...");
                c.wait(); //main释放c的对象锁，使得子线程c能拿到自己的c对象锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Total is: " + c.total);
        }
    }

}
