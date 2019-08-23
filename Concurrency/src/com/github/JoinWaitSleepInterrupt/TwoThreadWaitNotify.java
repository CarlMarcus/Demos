package com.github.JoinWaitSleepInterrupt;

/**
 *  两个线程交替打印 1~100 奇偶数
 *  https://mp.weixin.qq.com/s?__biz=MzIyMzgyODkxMQ==&mid=2247483730&idx=1&sn=1c48f1bee4f2eea04b5dd2779645efac&chksm=e8190f92df6e86842d085cfee1e31b8e437f86d9fe29fc515cf32fdbf141729705dd35f0ce6d&scene=21#wechat_redirect
 */
public class TwoThreadWaitNotify {

    public static void main(String[] args) {
        TwoThreadWaitNotify twoThread = new TwoThreadWaitNotify();
        Thread t1 = new Thread(new OddNum(twoThread));
        t1.setName("A");
        Thread t2 = new Thread(new EvenNum(twoThread));
        t2.setName("B");
        t1.start();
        t2.start();
    }

    private int start = 1;
    private boolean flag = false;
    /**
     * 偶数线程
     */
    public static class OddNum implements Runnable {
        private TwoThreadWaitNotify number;
        public OddNum(TwoThreadWaitNotify number) {
            this.number = number;
        }
        @Override
        public void run() {
            while (number.start <= 100) {
                synchronized (TwoThreadWaitNotify.class) {
                    System.out.println("偶数线程抢到锁了");
                    if (number.flag) {
                        System.out.println(Thread.currentThread().getName() + " print 偶数 " + number.start);
                        number.start++;
                        number.flag = false;
                        TwoThreadWaitNotify.class.notify();
                    }else {
                        try {
                            TwoThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    /**
     * 奇数线程
     */
    public static class EvenNum implements Runnable {
        private TwoThreadWaitNotify number;
        public EvenNum(TwoThreadWaitNotify number) {
            this.number = number;
        }
        @Override
        public void run() {
            while (number.start <= 100) {
                synchronized (TwoThreadWaitNotify.class) {
                    System.out.println("奇数线程抢到锁了");
                    if (!number.flag) {
                        System.out.println(Thread.currentThread().getName() + " print 奇数 " + number.start);
                        number.start++;
                        number.flag = true;
                        TwoThreadWaitNotify.class.notify();
                    }else {
                        try {
                            TwoThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
