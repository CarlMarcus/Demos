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
                System.out.println("Total is: " + total+"  出现此值证明Counter线程运行完了");
                notify();// 唤醒在c对象锁上等待的线程，比如刚刚的main，从 等待队列 进入到 同步队列
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
                c.wait(); //main释放c的对象锁，使得子线程c能拿到自己的c对象锁，main线程被丢到counter阻塞队列(等待队列)
                System.out.println("main线程结束阻塞了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
