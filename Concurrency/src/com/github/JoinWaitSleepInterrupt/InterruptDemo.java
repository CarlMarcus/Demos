package com.github.JoinWaitSleepInterrupt;

public class InterruptDemo implements Runnable {

    Thread t = new Thread(this);

    @Override
    public void run() {
        try {
            while (true)
                Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.print(t.getName() + " interrupted:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }

}
