package com.github.AQSexmaple;

import java.io.Serializable;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Mutex implements Serializable {
    //利用AQS实现一个mutex锁
    //静态内部类，继承AQS
    private static class Sync extends AbstractQueuedSynchronizer {
        protected boolean isHeldExclusively() { //是否处于占用状态
            return getState()==1;
        }

        //将会被acquire调用,trayAcquire必须确保线程安全
        public boolean tryAcquire(int acquires) {
            if (compareAndSetState(0, 1)) { //当状态为0的时候获取锁，CAS操作成功，则state状态为1，
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //释放锁，将同步状态置为0
        protected boolean tryRelease(int releases) {
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }

    //同步对象完成一系列复杂的操作，我们仅需指向它即可
    private final Sync sync = new Sync();
    //加锁操作，代理到acquire（模板方法）上就行，acquire会调用我们重写的tryAcquire方法
    public void lock() {
        sync.acquire(1);
    }

    public boolean tryLock() {
        return sync.tryAcquire(1);
    }
    //释放锁，代理到release（模板方法）上就行，release会调用我们重写的tryRelease方法。
    public void unlock() {
        sync.release(1);
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }
}
