package com.github.createThread;

import com.sun.jmx.snmp.Timestamp;

import java.sql.Time;

public class ExtendsThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Long t = System.currentTimeMillis();
        for(int i = 0;i< 30;i++) {
            System.out.println(Thread.currentThread().getName()+" "+i);
            /*
            可以观察到，第20次循环main主线程时，将开启两个新的子线程，等到这两个子线程结束，继续执行main主线程。
            run和start不一样，run会完全按照先后顺序执行完毕，start则是使线程就绪，需要得到cpu时间片才会run。
            如果直接调用run方法，并不会启动新线程,run执行的时候线程1和2打印的线程名都是main，程序中依然只有主线程这一个线程，
            其程序执行路径还是只有一条，还是要顺序执行，还是要等待run方法体执行完毕后才可继续执行下面的代码
             */
            if(i==20) {
                ExtendsThread th1 = new ExtendsThread();
                ExtendsThread th2 = new ExtendsThread();
                //th1.start();th2.start();
                th1.run();th2.run();
                Thread.sleep(3);
            }
        }
        System.out.println(System.currentTimeMillis()-t);
    }
}
