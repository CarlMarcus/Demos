package com.github.JoinWaitSleepInterrupt;

public class InterruptDemo implements Runnable {

    Thread t;
    public InterruptDemo() {
        t = new Thread(this);
        System.out.println("Executing " + t.getName());
        t.start();

        if (!t.isInterrupted()) {
            t.interrupt();
        } // interrupt the threads

        try {
            t.join();
        } catch (InterruptedException e) { } // block until other threads finish
    }

    @Override
    public void run() {
        try {
            while (true)
                Thread.sleep(1);
        } catch (InterruptedException e) {
            System.out.print(t.getName() + " interrupted: ");
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        new InterruptDemo();
        new InterruptDemo();
    }

}
