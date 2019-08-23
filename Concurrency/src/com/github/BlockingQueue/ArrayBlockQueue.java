package com.github.BlockingQueue;

/**自己实现阻塞队列：
 * 基本队列特性：先进先出；
 * 写入队列空间不可用时会阻塞；
 * 获取队列数据时当队列为空时将阻塞；
 * 写入过队列的数量大于队列大小时需要从第一个下标开始写；
 */
public class ArrayBlockQueue<T> {

    private int count = 0; //队列里数据数量，初始为0，不可大于size

    private Object[] items; //存储数组

    private Object full = new Object(); //队列满时的阻塞锁

    private Object empty = new Object(); //队列空时的阻塞锁

    private int putIndex; //写入数据时的下标，一直往后移

    private int getIndex; //获取数据时的下标，一直往后移

    public ArrayBlockQueue(int size) {
        this.items = new Object[size]; //队列最大容量
    }

    public int size() {
        return count;
    }

    public void put(T t) {
        synchronized (full) { //先获取full锁，满了先阻塞，等待get里get完后， full notify
            while (count == items.length) {
                try {
                    full.wait(); //put操作所在线程（生产者）将被扔到full锁的等待队列
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
        synchronized (empty) {
            items[putIndex] = t;
            count++;
            putIndex++;
            if (putIndex == items.length) {
                putIndex = 0;
            }
            empty.notify(); //放了东西进去说明队列绝对不是空的了，唤醒empty锁上等待队列里的消费者线程
        }
    }

    public T get() {
        synchronized (empty) { //先获取empty锁，空了先阻塞，等待put里put完后，empty notify
            while (count == 0) {
                try {
                    empty.wait(); // 队列是空的，必须把消费者线程阻塞，丢到empty锁的等待队列
                } catch (InterruptedException e) {
                    return null;
                }
            }
        }
        synchronized (full) {
            Object res = items[getIndex];
            items[getIndex] = null; // 把这个位置清空
            count--; //库存少一
            getIndex++; //由于是队列，先进的先出
            if (getIndex == items.length) {
                getIndex = 0;
            }
            full.notify(); //消费了一个东西，队列绝对不会是满的了，唤醒等待队列上的生产者线程
            return (T) res;
        }
    }
    /**
     * 总的来说：
     * * 写入队列满时会阻塞，直到消费者线程消费了队列数据后唤醒生产者线程。
     * * 消费队列空时会阻塞，直到生产者线程写入了队列数据后唤醒消费者线程。
     */
    // test
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockQueue<String> queue = new ArrayBlockQueue<>(250);
        Thread t1 = new Thread(() -> {
            for (int i=0; i<100; i++) {
                queue.put(i+" ");
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i=0; i<100; i++) {
                queue.put(i+" ");
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i=0; i<100; i++) {
                queue.put(i+" ");
            }
        });
        Thread t4 = new Thread(() -> {
            for (int i=0; i<50; i++) {
                System.out.println("===="+queue.get());
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        //t3.join();
        System.out.println(queue.size());
    }
}
