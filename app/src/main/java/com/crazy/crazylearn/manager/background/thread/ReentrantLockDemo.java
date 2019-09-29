package com.crazy.crazylearn.manager.background.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockDemo {

    /***
     *
     * 1、reentrantLock.lock(); 加锁
     * 2、reentrantLock.unlock(); 释放锁
     *
     * 3、reentrantLock.lockInterruptibly() 加锁可中断，可防止死锁
     * 4、reentrantLock.isHeldByCurrentThread() 是否已加锁
     *
     * 5、reentrantLock.tryLock() 限时等待申请锁
     *
     * 6、 new ReentrantLock(true) 公平锁
     *
     * 7、Condition 条件  重入锁搭档  （让线程等待或继续执行）
     *      await() 使线程等待
     *      awaitUninterruptibly() 使线程等待,但不响应中断
     *      signal() 唤醒一个在等待中的线程
     *      signalAll() 唤醒所有在等待中的线程 同Object.notifyAll()
     */

    //非公平重入锁
    private ReentrantLock reentrantLock = new ReentrantLock(false);
    //公平重入锁
    private ReentrantLock reentrantLockFair = new ReentrantLock(true);

    public static int i = 0;

    public static void main(String[] args) {

        try {

           /* ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
            reentrantLockDemo.test1();

            System.out.println("==============================");

            ReentrantLockDemo reentrantLockDemo2 = new ReentrantLockDemo();
            reentrantLockDemo2.test2();

            System.out.println("==============================");

//            Thread.currentThread().join();

            ReentrantLockDemo reentrantLockDemo3 = new ReentrantLockDemo();
            reentrantLockDemo3.test3();

            System.out.println("==============================");

            reentrantLockDemo3.test4();

            System.out.println("==============================");
            */
            ReentrantLockDemo queueLock = new ReentrantLockDemo();
            //queueLock.test5();

            queueLock.test6();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void test1() throws InterruptedException {

        ReenterLockRunble reenterLockR = new ReenterLockRunble();

        Thread t1 = new Thread(reenterLockR, "t1");
        Thread t2 = new Thread(reenterLockR, "t2");

        t1.start();
        t2.start();

        // t1.join();
        // t2.join();

        System.out.println("end....." + i);
    }

    public void test2() throws InterruptedException {

        ReenterLockRunble reenterLockR = new ReenterLockRunble();

        Thread t1 = new Thread(reenterLockR, "t3");
        Thread t2 = new Thread(reenterLockR, "t4");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("end....." + i);
    }


    public class ReenterLockRunble implements Runnable {

        public String name;

        @Override
        public void run() {

            for (int j = 0; j < 1000; j++) {
                //加锁
                reentrantLock.lock();
                //允许一个线程多次获得通一把锁
                reentrantLock.lock();
                try {
                    i++;
                    System.out.println("name = " + Thread.currentThread().getName());
                } finally {
                    //释放锁
                    reentrantLock.unlock();
                    //对应释放
                    reentrantLock.unlock();
                }
            }
        }
    }

    private ReentrantLock intLock1 = new ReentrantLock();
    private ReentrantLock intLock2 = new ReentrantLock();

    public class IntLock implements Runnable {

        public IntLock(int flag) {
            this.flag = flag;
        }

        public int flag;

        @Override
        public void run() {

            try {

                if (flag == 1) {
                    intLock1.lockInterruptibly();
                    System.out.println(flag + " intLock1 加锁");
                    Thread.sleep(500);
                    intLock2.lockInterruptibly();
                    System.out.println(flag + " intLock2 加锁");
                } else if (flag == 2) {
                    intLock2.lockInterruptibly();
                    System.out.println(flag + " intLock2 加锁");
                    Thread.sleep(500);
                    intLock1.lockInterruptibly();
                    System.out.println(flag + " intLock1 加锁");
                }

            } catch (InterruptedException e) {
                System.out.println(flag + " e: " + e.toString());
                // e.printStackTrace();
            } finally {
                if (intLock1.isHeldByCurrentThread())
                    intLock1.unlock();
                if (intLock2.isHeldByCurrentThread())
                    intLock2.unlock();

                System.out.println(flag + " 退出当前线程 ...........");
            }
        }
    }

    public void test3() throws InterruptedException {

        Thread.sleep(1500);

        Thread t3 = new Thread(new IntLock(1));
        Thread t4 = new Thread(new IntLock(2));

        t3.start();
        t4.start();

        Thread.sleep(1500);

        t4.interrupt();
    }

    private ReentrantLock condtionLock = new ReentrantLock();
    private Condition condition = condtionLock.newCondition();

    public class ConditionRunnable implements Runnable {

        @Override
        public void run() {

            int count = 20;

            for (int i = 0; i < count; i++) {
                if (i == count / 2) {
                    try {
                        System.out.println("*******等待*******");
                        condtionLock.lock();
                        condition.await();
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                        System.out.println(i + "----- e: " + e.toString());
                    } finally {
                        condtionLock.unlock();
                    }
                }
                System.out.println("current = " + i);
            }

        }
    }

    public void test4() throws InterruptedException {

        Thread.sleep(1500);

        Thread t = new Thread(new ConditionRunnable());
        t.start();

        Thread.sleep(1500);
        System.out.println("*******1.5s后唤醒*******");
        condtionLock.lock();
        condition.signal();
        condtionLock.unlock();
    }


    public class CustomArrayBlockQueueDemo<E> {

        private ReentrantLock queueLock = new ReentrantLock(true);
        //队列不为空
        private Condition notEmpty = queueLock.newCondition();
        //队列没有满
        private Condition notFull = queueLock.newCondition();

        private int capacity = 20;

        private int count = 0;

        public Object[] items = new Object[capacity];

        public CustomArrayBlockQueueDemo() {
        }

        public void put(E e) throws InterruptedException {
            if (e == null) throw new NullPointerException();

            final ReentrantLock lock = this.queueLock;

            lock.lockInterruptibly();
            System.out.println("+++++插入获得锁+++++");
            try {

                while (count == items.length) {
                    System.out.println("-------------------插入等待 count= " + count);
                    notFull.await();
                }
                System.out.println("-------------------插入等待結束 count= " + count);

                insert(e);

                notEmpty.signal();

            } catch (InterruptedException e1) {
                e1.printStackTrace();

                notFull.signal();
                throw e1;

            } finally {
                lock.unlock();
                System.out.println("+++++插入释放锁+++++");
            }
        }

        public E take() throws InterruptedException {

            E value;

            final ReentrantLock lock = this.queueLock;

            lock.lockInterruptibly();
            System.out.println("+++++读取获得锁+++++");

            try {

                while (count == 0) {
                    System.out.println("读取等待 count= " + count);
                    notEmpty.await();
                }
                System.out.println("读取等待結束 count= " + count);

                value = remove();

                System.out.println("读取等待結束得到 value= " + value);

                notFull.signal();

            } catch (InterruptedException e) {
                e.printStackTrace();

                notEmpty.signal();
                throw e;

            } finally {
                lock.unlock();
                System.out.println("+++++读取释放锁+++++");
            }

            return value;
        }

        @SuppressWarnings("unchecked")
        private void insert(E e) {
            final E[] items = (E[]) this.items;
            items[count] = e;
            count++;
        }

        @SuppressWarnings("unchecked")
        private E remove() {
            E e;
            final E[] items = (E[]) this.items;

            e = items[count - 1];
            items[count - 1] = null;
            count--;
            return e;
        }

    }

    public class ReadRunnable implements Runnable {

        CustomArrayBlockQueueDemo<Integer> queue;

        public ReadRunnable(CustomArrayBlockQueueDemo<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {

            Integer take;

            while (!Thread.interrupted()) {
                try {
                    take = queue.take();

                    System.out.println("获取到的值为： " + take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("read runnable end...");
        }
    }

    public class WriterRunnable implements Runnable {
        public WriterRunnable(CustomArrayBlockQueueDemo<Integer> queue, int from, int to) {
            this.queue = queue;
            this.from = from;
            this.to = to;
        }

        CustomArrayBlockQueueDemo<Integer> queue;

        int from, to;


        @Override
        public void run() {

            try {

                for (int i = from; i < to; i++) {
                    queue.put(i);
                    System.out.println("-------------------写入第[ " + i + " ]个值");

                    //Thread.sleep(100);
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("-------------------writer runnable end...");

        }
    }

    public void test5() throws InterruptedException {
        CustomArrayBlockQueueDemo<Integer> queue = new CustomArrayBlockQueueDemo<>();

        /*int count = 20;

        for (int i = 0; i < count; i++) {
            queue.put(i);
        }*/

        ReadRunnable readRunnable = new ReadRunnable(queue);
        WriterRunnable writerRunnable = new WriterRunnable(queue, 0, 50);
        WriterRunnable writerRunnable2 = new WriterRunnable(queue, 100, 150);

        Thread readThread = new Thread(readRunnable, "readThread");
        Thread writerThread = new Thread(writerRunnable, "writerThread");
        Thread writerThread2 = new Thread(writerRunnable2, "writerThread2");

        readThread.start();
        writerThread.start();
        writerThread2.start();

       /* readThread.join();
        writerThread.join();*/

        System.out.println("main end ...");
    }


    /**
     * 读写锁
     */
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    /**
     * 倒计时器
     */
    //等待其他（10个）线程全部不执行完毕,再执行
    private CountDownLatch countDownLatch = new CountDownLatch(10);

    public class LatchRunnable implements Runnable {
        @Override
        public void run() {
            //模拟一个耗时线程

            try {
                Thread.sleep(new Random().nextInt(10) * 1000);
                System.out.println("over...");
                //计数
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void test6() throws InterruptedException {

        LatchRunnable latchRunnable = new LatchRunnable();

      //  ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.execute(latchRunnable);
            // executorService.submit(latchRunnable);
        }

        //等待
        countDownLatch.await();
        System.out.println("All over...");

        executorService.shutdown();
    }

}
