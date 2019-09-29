package com.crazy.crazylearn.manager.background.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/****
 * 一、Executor（执行者）
 *      ExecutorService
 *
 * 原因与目的：
 * 多线程异步执行-->发挥多核处理器能力--->线程乱用，不加控制--->大量线程占用系统资源（大量线程回收GC压力大）
 * 避免重复创建线程、减少资源浪费与系统开销-->使用线程池--->线程复用、合理管理、调度线程
 *
 * 二、Executor框架结构
 *    1、任务：两类任务：实现Runnable接口、实现Callback接口
 *    2、执行任务：
 *          Executor    执行者接口，执行提交Runnable的execute()方法
 *           ExecutorService    一个{Executor}，提供管理终止的方法，可以生成{Future}以跟踪一个或多个异步任务的进度的方法。
 *             AbstractExecutorService
 *               ThreadPoolExecutor
 *                   ScheduledThreadPoolExecutor
 *    3、异步执行结果:
 *          Future及FutureTask
 *
 * 三、Executor框架的主要类及接口
 *    1、Executor是一个接口，它是Executor框架的基础，它将任务的提交与任务的执行分离开来。
 *    2、ExecutorService：一个{Executor}，提供管理终止的方法，可以生成{Future}以跟踪一个或多个异步任务的进度的方法。
 *          shutdown()  不再接收新任务
 *          shutdownNow()
 *              尝试停止所有正在执行的任务，停止等待任务的处理，并返回等待执行的任务列表*
 *              不保证任务会被终止，例如，典型的实现将通过{Thread.interrupt}取消，因此任何未能响应中断的任务可能永远不会终止。
 *          isShutDown() 是否关闭
 *          isTerminated() 是否已终止，true:关闭后所有任务都已完成
 *          awaitTermination() 等待终止
 *          Future submit(Callback task) 提交任务   Future.get() 获取任务结果
 *          <T> Future<T> submit(Runnable task, T result);
 *    3、AbstractExecutorService
 *    4、ThreadPoolExecutor是线程池的核心实现类，用来执行被提交的任务。
 *    5、ScheduledThreadPoolExecutor是一个实现类，可以在给定的延迟后运行命令，或者定期执 行命令。ScheduledThreadPoolExecutor比Timer更灵活，功能更强大
 *    6、Future接口和实现Future接口的FutureTask类，代表异步计算的结果。
 *    7、Runnable接口和Callable接口的实现类
 *
 * 四、Executor框架的使用
 *    1、create Runnable、 Callback、 Executors.callable(Runable)
 *    2、Executor.execute(Runable)
 *      ExecutorService.submit(Runable)
 *      ExecutorService.submit(Callback)
 *    3、FutureTask.get()、FutureTask.cancel()
 *
 * 五、ThreadPoolExecutor
 *
 * 五一、创建线程池
 *
 *  public ThreadPoolExecutor(int corePoolSize,
 *                               int maximumPoolSize,
 *                               long keepAliveTime,
 *                               TimeUnit unit,
 *                               BlockingQueue<Runnable> workQueue,
 *                               ThreadFactory threadFactory,
 *                               RejectedExecutionHandler handler)
 *
 *  1、int corePoolSize：
 *      必需参数，规定了线程池的基本大小，当提交一个任务到线程池时，线程池会创建一个线程来执行任务，
 *      即使其他空闲的基本线程能够执行新任务也会创建线程，等到需要执行的任务数大于线程池基本大小时就不再创建。
 *      如果调用了线程池的prestartAllCoreThreads()方法， 线程池会提前创建并启动所有基本线程。
 *  2、 maximumPoolSize：
 *      必需参数，线程池中允许创建线程的最大数量，如果队列满了，并且已创建的线程数小于最大线程数，
 *      则线程池会再创建新的线程执行任务。值得注意的是，如果使用了无界的任务队列这个参数就没什么效果。
 *  3、long keepAliveTime：
 *      必需参数，线程活动保持时间，线程池的工作线程空闲后，保持存活的时间。
 *      所以，如果任务很多，并且每个任务执行的时间比较短，可以调大时间，提高线程的利用率。
 *  4、TimeUnit unit：
 *      必需参数，线程活动保持时间的单位，可选的单位有天（DAYS）、小时（HOURS）、分钟 （MINUTES）、
 *      毫秒（MILLISECONDS）、微秒（MICROSECONDS，千分之一毫秒）和纳秒 （NANOSECONDS，千分之一微秒）。
 *  5、BlockingQueue<Runnable> workQueue：
 *      必需参数，任务队列，用于保存等待执行的任务的阻塞队列，
 *      关于阻塞队列可以参考https://my.oschina.net/u/3352298/blog/1807780
 *  6、ThreadFactory threadFactory：
 *      非必须参数，不设置此参数会采用内置默认参数。用于设置创建线程的工厂，
 *      可以通过线程工厂给每个创建出来的线程设置更有意义的名字。
 *  7、RejectedExecutionHandler handler：
 *      非必须参数，不设置此参数会采用内置默认参数，设置饱和策略，当队列和线程池都满了，
 *      说明线程池处于饱和状态，那么必须采取一种策略处理提交的新任务。
 *
 *      饱和策略：
 *      AbortPolicy（直接抛出异常）
 *      CallerRunsPolicy（只用调用者所在线程来运行任务）
 *      DiscardOldestPolicy（丢弃队列里最近的一个任务，并执行当前任务）
 *      DiscardPolicy（不处理，丢弃掉）
 *      也可以根据应用场景需要来实现RejectedExecutionHandler接口自定义策略
 *
 * 五二、提交任务
 *  1、execute()方法用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功。
 *  2、submit()方法用于提交需要返回值的任务。线程池会返回一个future类型的对象，
 *      通过这个future对象可以判断任务是否执行成功，并且可以通过future的get()方法来获取返回值，
 *      get()方法会阻塞当前线程直到任务完成，而使用get（long timeout，TimeUnit unit）方法则会阻塞当前线
 *      程一段时间后立即返回，这时候有可能任务没有执行完。
 *
 *五三、关闭线程池
 *  1、 有两种方法：shutdown或shutdownNow方法，它们的原理是遍历线程池中的工作线程，
 *      然后逐个调用线程的interrupt方法来中断线程，所以无法响应中断的任务可能永远无法终止。
 *      且调用之后不能再向线程池提交任务。
 *  2、shutdownNow首先将线程池的状态设置成STOP，然后尝试停止所有的正在执行或暂停任务的线程，
 *      并返回等待执行任务的列表。会立即停止所有任务，无论是否执行完毕
 *  3、shutdown只是将线程池的状态设置成SHUTDOWN状态，然后中断所有没有正在执行任务的线程。
 *      直到所有任务执行完成或者退出才能关闭线程池。
 *  4、只要调用了这两个关闭方法中的任意一个，isShutdown方法就会返回true。当所有的任务都已关闭后，
 *      才表示线程池关闭成功，这时调用isTerminaed方法会返回true。至于应该调用哪 一种方法来关闭线程池，
 *      应该由提交到线程池的任务特性决定，通常调用shutdown方法来关闭线程池，如果任务不一定要执行完，
 *      则可以调用shutdownNow方法
 *
 * 五四、合理配置线程池
 *  1、任务的性质
 *      CPU密集型任务：尽可能小的线程，如配置N(cpu)+1个线程的线程池
 *      IO密集型任务：并不是一直在执行任务，可配置尽可能多的线程，如2*N(cpu)
 *      混合型的任务：两个任务执行时间相差太大，可以通过 Runtime.getRuntime().availableProcessors()方法获得当前设备的CPU个数
 *  2、任务的优先级
 *      高、中和低。优先级不同的任务可以使用优先级队列PriorityBlockingQueue来处理。它可以让优先级高 的任务先执行。
 *      如果一直有优先级高的任务提交到队列里，那么优先级低的任务可能永远不能执行。
 *  3、任务的执行时间
 *      长、中和短。执行时间不同的任务可以交给不同规模的线程池来处理，或者可以使用优先级队列，让执行时间短的任务先执行
 *  4、任务的依赖性
 *      是否依赖其他系统资源，如数据库连接。依赖数据库连接池的任务，因为线程提交SQL后需要等待数据库返回结果，
 *      等待的时间越 长，则CPU空闲时间就越长，那么线程数应该设置得越大，这样才能更好地利用CPU。
 *      建议使用有界队列。有界队列能增加系统的稳定性和预警能力，可以根据需要设大一点儿，比如几千。
 *      如果设置成无界队列，那么由于操作数据库的线程可能会阻塞，引起线程池的队列就会越来越多，
 *      有可能会撑满内存，导致整个系统不可用。
 *
 * 五五、线程池监控
 *      使用线程池，则有必要对线程池进行监控，方便在出现问题时，可以根据线程池的使用状况快速定位问题
 *  1、taskCount：线程池需要执行的任务数量。
 *  2、completedTaskCount：线程池在运行过程中已完成的任务数量，小于或等于taskCount。
 *  3、largestPoolSize：线程池里曾经创建过的最大线程数量。通过这个数据可以知道线程池是 否曾经满过。如该数值等于线程池的最大大小，则表示线程池曾经满过。
 *  4、getPoolSize：线程池的线程数量。如果线程池不销毁的话，线程池里的线程不会自动销 毁，所以这个大小只增不减。
 *  5、 getActiveCount：获取活动的线程数。
 *  6、扩展线程池进行监控，重写线程池的 beforeExecute、afterExecute和terminated方法
 *
 * 五六、工作队列
 *  1、直接交付队列    SynchronousQueue
 *  2、有界阻塞队列    LinkedBlockingQueue
 *  3、无界阻塞队列    ArrayBlockingQueue
 *
 * ————————————————
 *  总结：几个内置的线程池类，如newFixedThreadPool等，但是通常应该通过ThreadPoolExecutor类来写自己的线程池类，
 *      因为那些内置的线程池内总会不满足我们的各种需求且可能会引起性能问题，
 *      所以应认真分析任务特点后进行专门的配置线程池。
 * ————————————————
 *
 * 六、线程池状态、workerCount
 *
 *     线程池中通过高低位控制workerCount(低29位)和runState（高3位）状态运行状态，
 *     private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
 *          workerCount：线程池中当前活动的线程数量，占据ctl的低29位；
 *          runState：线程池运行状态，占据ctl的高3位
 *
 *     private static final int COUNT_BITS = Integer.SIZE - 3;         32 - 3 = 29
 *     private static final int CAPACITY   = (1 << COUNT_BITS) - 1;   1左移29位 =  536,870,911‬（ 0001 1111 1111 1111 111 1111 1111 1111）
 *
 *     // runState is stored in the high-order bits  （-1 负数第32为1，其他位也全为1， 1111 1111 1111 1111 , 111 1111 1111 1111）
 *     private static final int RUNNING    = -1 << COUNT_BITS;       -536,870,912 (1110 0000 0000 0000 , 0000 0000 0000 0000)
 *     private static final int SHUTDOWN   =  0 << COUNT_BITS;          0
 *     private static final int STOP       =  1 << COUNT_BITS;       536,870,912‬ (‭0010 0000 0000 0000 , 0000 0000 0000 0000‬)
 *     //整理状态
 *     private static final int TIDYING    =  2 << COUNT_BITS;       1073741824
 *     //终止状态
 *     private static final int TERMINATED =  3 << COUNT_BITS;       1610612736
 *
 *     // Packing and unpacking ctl
 *     private static int runStateOf(int c)     { return c & ~CAPACITY; }
 *     private static int workerCountOf(int c)  { return c & CAPACITY; }
 *     private static int ctlOf(int rs, int wc) { return rs | wc; }
 *
 *     运行状态的值按照RUNNING-->SHUTDOWN-->STOP-->TIDYING-->TERMINATED顺序值是递增的，
 *     这些值之间的数值顺序很重要。随着时间的推移，运行状态单调增加，但是不需要经过每个状态
 *
 *      （1）RUNNING -> SHUTDOWN：调用shutdownNow()方法后，或者线程池实现了finalize方法，在里面调用了shutdown方法，即隐式调用；
 *
 *    （2）(RUNNING or SHUTDOWN) -> STOP：调用shutdownNow()方法后；
 *
 *     （3）SHUTDOWN -> TIDYING：线程池和队列均为空时；
 *
 *    （4）STOP -> TIDYING：线程池为空时；
 *
 *    （5）TIDYING -> TERMINATED：terminated()钩子方法完成时。
 *
 * ————————————————
 *
 * 七、execute()执行过程
 *  1、if (workerCountOf(c) < corePoolSize)
 *      当前工作线程小于核心线程数，
 *      if (addWorker(command, true))
 *                 return;
 *      加入成功后返回
 *  2、 c = ctl.get() 加入不成功，获取当前状态，
 *  3、if (isRunning(c) && workQueue.offer(command))
 *      如果处于运行状态则将任务加入等待队列（offer,如果加入成功返回true,加入失败返回false,不抛出ill异常，add加入失败会抛出IllegalStateException异常）
 *  4、int recheck = ctl.get();
 *      获取状态
 *  5、 if (!isRunning(recheck) && remove(command))
 *            reject(command);
 *      刚加入，不在运行状态，则从等待队列移除，并调用拒绝策略处理
 *      else if (workerCountOf(recheck) == 0)
 *            addWorker(null, false);
 *      否则，工作线程数为0，加入
 *
 *
 *————————————————
 *
 *
 *  FixedThreadPool  可重用固定线程数的线程池
 *        corePoolSize = maximumPoolSize = fixedThreadSize(参数设置)
 *       当线程池中的线程数大于核心线程数时，
 *
 *    SingleThreadExecutor
 *    CacheThreadPool
 *
 *————————————————
 *
 *
 */
public class ThreadPoolSummary {

    public ThreadPoolSummary() {
    }

    public static void main(String[] args) {

        ThreadPoolSummary threadPoolSummary = new ThreadPoolSummary();

        threadPoolSummary.test1();
    }

    /**
     * 固定大小线程池
     */
    public Executor executor = Executors.newFixedThreadPool(5);
    public Executor singleExecutor = Executors.newSingleThreadExecutor();

    private Executor cacheExecutor = Executors.newCachedThreadPool();


    public class ExecutorRunnable implements Runnable {

        public String name;

        @Override
        public void run() {

            long time = System.currentTimeMillis();
            long id = Thread.currentThread().getId();

            System.out.println("time = " + time + " thread-id = " + id);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void test1() {

        ExecutorRunnable executorRunnable = new ExecutorRunnable();

        for (int i = 0; i < 10; i++) {
            cacheExecutor.execute(executorRunnable);
        }

    }

    public Executor iexecutor;
    private ExecutorService executorService;
    private ThreadPoolExecutor threadPoolExecutor;

    public class MyThreadPool extends ThreadPoolExecutor {

        public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);
            System.out.println("准备执行： " + ((ExecutorRunnable) r).name);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            System.out.println("准备完成： ");
        }

        @Override
        protected void terminated() {
            super.terminated();
            System.out.println("线程池退出");
        }
    }


    private ExecutorService futrueExecutor = Executors.newFixedThreadPool(5);

    public void test2() {

        futrueExecutor.submit(new Runnable() {
            @Override
            public void run() {

            }
        });

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "result = ";
            }
        };

        FutureTask<String> future = new FutureTask<String>(callable);

        futrueExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "";
            }
        });
    }


}
