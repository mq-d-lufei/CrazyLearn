package com.crazy.crazylearn.manager.background.thread;

/**
 * Created by feaoes on 2018/4/19.
 */

public class Summary implements Runnable {



    /**
     * 倒数第九：
     * 1、原子性：指一个操作是不可中断的，不可干扰的，即时在多个线程一起执行的时候，一个操作一旦开始，就不会被其他线程干扰
     * 2、可见性：当一个线程修改了某个共享变量时，其他线程是否可以立即知道这个修改，如缓存优化、硬件优化、指令重排等
     * 3、有序性：对单个线程而言，代码的执行顺序从先往后、依次执行；但是，并发时、程序的执行可能会出现乱序，可能会指令重排
     * 4、指令不重排原则：单个线程保证语义的串行性；volatile写先与读；锁规则；传递性；线程start先与其他动作；线程的所有操作咸鱼终止；线程的构造方法先与finalize();
     */

    /**
     * 倒数第八
     * 线程中断
     * Thread.interrupt();  中断线程，设置中断标记为
     * Thread.isInterrupt();    判断是否被中断
     * static Thread.interrupted();    判断是否被中断,并清除当前中断状态
     */

    /**
     * 倒数七
     * Object类中对象：
     * 1、等待：wait()  ，当在一个对象实例上调用object.wait(),当前线程就会在该对象上等待，并进入该对象的等待队列
     * 2、通知：notify(), 当object.notify()被调用，系统会从该对象的等待队列中随机选择一个线程并唤醒，该选择是不公平的。
     * 3、通知全部：notifAll(), 当object.notifyAll()被调用，系统或唤醒该等待队列中所有等待的线程，而不是随机选一个
     * 4、以上方法调用必须在synchronized()代码快中被调用。
     */

    private static Object waitObject = new Object();

    static int waitCount = 0;

    static class FirstWaitThread extends Thread {

        @Override
        public void run() {
            synchronized (waitObject) {
                while (true) {
                    if (waitCount == 100) {
                        System.out.println("wait...");
                        try {
                            waitObject.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("FirstWaitThread: " + waitCount++);

                    if (waitCount >= 500) {
                        break;
                    }
                }
            }
        }
    }

    static class SecondWaitThread extends Thread {
        @Override
        public void run() {
            synchronized (waitObject) {
                while (true) {
                    if (waitCount >= 350) {
                        waitObject.notify();
                        break;
                    }
                    System.out.println("SecondWaitThread: " + waitCount++);
                }
            }
        }
    }


    /**
     * 倒数第六
     * 1、等待线程结束：join
     * 2、线程谦让： yield,  让出cpu后还会竞争资源，
     */
    /**
     * 倒数第五：
     * volatile 与 Java内存模型（JMM）
     * 1、Java内存模型围绕着：原子性、可见性、有序性
     * 2、volatile:可见性，确保变量被某些程序或线程修改后，应用程序内的所有线程都能“看到”这个改动，
     * 3、volatile int count = 0;
     */


    /**
     * 倒数第四：
     * 线程组
     *  ThreadGroup tg = new ThreadGroup("FristGroup");
     *  Thread t1 = new Thread(tg,"t1");
     *  Thread t2 = new Thread(tg, new Runnable(),"t2");
     */

    /**
     * 倒数第三:
     * 守护线程
     * new Thread().setDaemon()true;
     */

    public static class DaemonThread extends Thread {

        public DaemonThread() {
            this.setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {
                System.out.println("i am alive...");

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 倒数第二:
     * 线程优先级
     * 1、线程都有自己的优先级、优先级高的在竞争资源是更有优势，更可能抢占资源，属于概率事件，也有可能抢占失败
     * 2、优先级从1到10，从低到高
     */


    public static class LowPriorityThread extends Thread {
        int lowPriCount = 0;

        @Override
        public void run() {
            //资源竞争
            synchronized (Summary.class) {
                while (true) {
                    lowPriCount++;
                    if (lowPriCount > 1000000) {
                        System.out.println("lowPriorityThread is complete...");
                        break;
                    }
                }
            }
        }
    }

    public static class HeightPriorityThread extends Thread {
        int heightPriCount = 0;

        @Override
        public void run() {
            //资源竞争
            synchronized (Summary.class) {
                while (true) {
                    heightPriCount++;
                    if (heightPriCount > 1000000) {
                        System.out.println("heightPriorityThread is complete...");
                        break;
                    }
                }
            }
        }
    }


    public static void threadPriorityTest() {
        LowPriorityThread lowPriorityThread = new LowPriorityThread();
        lowPriorityThread.setPriority(Thread.MIN_PRIORITY);
        HeightPriorityThread heightPriorityThread = new HeightPriorityThread();
        heightPriorityThread.setPriority(Thread.MAX_PRIORITY);

        lowPriorityThread.start();
        heightPriorityThread.start();
    }


    static Summary instance = new Summary();

    public final Object object = new Object();

    private final int[] lockObject = new int[1];

    public int count = 0;

    public static int value = 0;

    /***
     *  倒数第一:
     *  synchronized：进入同步块时需要先获得锁，（获得锁的过程会资源竞争），确保线程同步、线程安全，保存线程间可见性、有序性
     *  1、指定加锁对象：   锁为给定对象
     *  2、作用于实例方法： 锁为当前示实例对象    Object.Class();
     *  3、作用于静态方法： 锁为当前类
     */

    //指定加锁对象：   锁为给定对象
    public void synchronizedFirst() {
        synchronized (object) {
            count++;
        }
    }

    //、作用于实例方法： 锁为当前示实例对象
    public synchronized void synchronizedSecond() {
        count++;
    }

    //作用于静态方法： 锁为当前类
    public static synchronized void synchronizedThird() {
        value++;
    }


    private static boolean isSynchronizedTest = false;
    private static boolean isThreaPriorityTest = false;
    private static boolean isThreadGroupTest = false;
    private static boolean isWaitNotifyTest = true;

    public static void main(String[] args) throws InterruptedException {

        if (isWaitNotifyTest) {
            new FirstWaitThread().start();
            new SecondWaitThread().start();
        }

        //线程组
        if (isThreadGroupTest) {
            ThreadGroup fristTG = new ThreadGroup("FirstGroup");
            Thread t1Group = new Thread(fristTG, "t1Group");
            Thread t2Group = new Thread(fristTG, "t2Group");
            t1Group.start();
            t2Group.start();
            System.out.print("activeCount: " + fristTG.activeCount());
            fristTG.list();
        }

        //守护进程
        new DaemonThread().start();

        if (isThreaPriorityTest) {
            threadPriorityTest();
        }


        if (isSynchronizedTest) {
            Thread t1 = new Thread(instance);
            Thread t2 = new Thread(instance);
            t1.start();
            t2.start();

            t1.join();
            t2.join();
        }
    }


    @Override
    public void run() {
        synchronizedFirst();
    }
}
