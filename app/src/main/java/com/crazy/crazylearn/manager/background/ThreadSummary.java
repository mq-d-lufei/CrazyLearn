package com.crazy.crazylearn.manager.background;

import com.crazy.crazylearn.manager.background.handler.Empty;

public class ThreadSummary {


    public static void main(String[] arg) {

        ThreadSummary threadSummary = new ThreadSummary();

        threadSummary.classTest();

        System.out.println(" --------------- ");

        classStatic();
    }

    public void classTest() {

        Class<?> class0 = null;

        try {
            class0 = Class.forName("com.crazy.crazylearn.manager.background.handler.Empty");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("class0 : " + (null != class0 ? class0.hashCode() : " null "));

        Class class1 = Empty.class;

        System.out.println("class1 : " + class1.hashCode());

        Empty class2 = new Empty();

        System.out.println("class2 : " + class2.getClass().hashCode());


        Empty class3 = new Empty();

        System.out.println("class3 : " + class3.getClass().hashCode());
    }

    public static void classStatic() {

        Class<?> class0 = null;

        try {
            class0 = Class.forName("com.crazy.crazylearn.manager.background.ThreadSummary.StaticClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("class0 : " + (null != class0 ? class0.hashCode() : " null "));

        Class class1 = StaticClass.class;

        System.out.println("class1 : " + class1.hashCode());

        StaticClass class2 = new StaticClass();

        System.out.println("class2 : " + class2.getClass().hashCode());


        StaticClass class3 = new StaticClass();

        System.out.println("class3 : " + class3.getClass().hashCode());
    }


    public static class StaticClass {

    }

}


/***
 *
 *  概念相关
 *
 *  1、同步、异步
 *
 *  2、并发：多个任务交替执行，而多个任务有可能还是串行的
 *     并行：正在意义上的“同时执行”，多核CPU
 *
 *     线程安全：在并发情况下，代码经过多线程使用，线程的调度顺序不会影响任何结果
 *
 *     同步：通过人为的控制和调度，保证共享资源在多线程情况下访问成为线程安全，来保证结果准确
 *
 *  3、临界区：共享资源（公共资源），可以被多个线程使用，但是每一次只能有一个线程使用它，
 *              一旦共享资源被占用，其他线程若想使用，必须等待
 *             受保护区，避免出现恩提
 *
 *  4、阻塞与非阻塞：多线程间的相互影响
 *
 *  5、死锁：十字路口堵车
 *
 *     饥饿：迟迟无法获得所需资源，导致一直无法执行，
 *              资源被高优先级线程抢占
 *              关键资源被另一个线程占着不放
 *
 *     活锁：线程间互相谦让，导致资源在现场见跳动，没有一个线程拿到资源而正常执行
 *
 *  6、并发级别
 *      阻塞      synchronized reentrantLock 获取锁时阻塞
 *      无饥饿    线程之间具有优先级，优先级低的线程总是获取不到锁
 *      无障碍    乐观锁、先无障碍执行，一旦发生冲突，则回滚，线程间可以同时访问共享区域，提供一致性标志位用于检查访问共享数据区是否产生冲突（线程操作前先访问并保存标记，操作完成后，再次读取，检查标记是否被更改过）
 *      无锁      所有线程都能尝试对临界区进行访问，无所并发保证必然有一个线程能在有限步内完成操作并离开临界区
 *                 while(!atomicVar.compareAndSet(localVar,localVar+1)){
 *                      localVar = atomicVar.get();
 *                 }
 *      无等待``  无所基础，所有线程都必须在有限步内完成，不会引发饥饿
 *
 *  JVM多线程
 *
 *  1、原子性：一个操作是不可中断的
 *
 *  2、可见性：一个线程修改了共享变量的值，其他线程是否能够立即知道这个修改
 *
 *  3、有序性：并发时，程序的执行可能出现乱序，可能会指令重排序，重排序后的指令与原指令未必一致
 *
 *
 *  Thread
 *
 *  1、NEW RUNNABLE BLOCKED WAITING TIMED_WAITING TERMINATED
 *
 *  2、Thread.stop()方法在结束线程时，会直接终止线程，并立即释放该线程持有的锁，可能会导致问题
 *
 *
 *  同步控制
 *
 *  1、Synchronized 线程间同步
 *
 *  2、ReentrantLock 重入锁
 *          中断响应
 *          锁申请等待限时
 *          公平锁
 *          基于AQS(AbstractQueueSynchronize)和LockSupport
 *          AQS主要利用硬件原语指令(CAS compare-and-swap)，来实现轻量级多线程同步机制，并且不会引起CPU上文切换和调度，
 *          同时提供内存可见性和原子化更新保证(线程安全的三要素：原子性、可见性、顺序性)。
 *
 *  3、condition 重入锁搭档，与Object.wait()、Object.notify()大致相同
 *
 *  4、Semaphore 信行量 对锁的扩展 一次可以指定多个线程访问某一共享资源
 *
 *  5、ReentrantReadWriteLock 读写锁
 *
 *  6、CountDownLatch 倒计时器
 *      Latch 门闩
 *      让某一线程等待直到倒计时结束，再开始执行（该线程等待知道其他线程执行完再执行）
 *
 *  7、LockSupport 线程阻塞工具类
 *          可以在线程任意位置让线程阻塞
 *          与Object.wait()相比，不需要先获得某个对象的锁，不会抛出中断异常
 *
 *
 * 详解：
 *
 *  1、synchronized
 *
 *     0、悲观锁策略
 *         假设每一次执行临界区代码时都会产生冲突，所以当前线程获得锁后同时会阻塞其他线程获取锁
 *
 *     1、Java对象头和monitor是实现synchronized的基础
 *
 *     a、monitor机制
 *        monitorenter与monitorexit指令
 *     b、synchronized的happens-before关系
 *        线程A在释放锁之前所有可见的共享变量，在线程B获取同一个锁之后，将立即变得对B线程可见
 *     c、锁获取和锁释放的内存语义
 *          语义:线程A加锁 --> 执行临界区代码  --> :线程A释放锁
 *               线程B加锁 --> 执行临界区代码(此时共享变量堆B可见)  --> :线程B释放锁
 *          线程A加锁 - 线程A将共享变量从主内存拷贝到本地内存 - 修改本地内存的值 -  线程A释放锁时将值写回主内存
 *          线程B获得锁 - 线程B强制从主内存获取最新值到本地内存 - 修改本地内存的值 -  线程B释放锁时将值写回主内存
 *
 *     d、synchronized优化
 *        特征：同一时刻只有一个线程能够获得对象的监视器（monitor），表现为互斥性（排他性）
 *        缺点：效率低，每次只能通过一个线程
 *        优化方式：排队方式不变，加快通过（锁中执行）速度
 *
 *  2、CAS操作（无锁操作）
 *      0、乐观锁策略
 *          假设所有线程访问共享资源的时候不会产生冲突，既然不出现冲突自然不会阻塞其他线程的操作
 *      a、CAS(compare and swap)又称为比较交换来鉴别线程是否出现冲突，出现冲突就重试当前操作直到没有冲突为止
 *      b、三个值：V 内存地址存放的实际值；O 预期的值（旧值）；N 更新的新值
 *      c、流程：当V和O相同时，也就是说旧值和内存中实际的值相同表明该值没有被其他线程更改过，即该旧值O就是目前来说最新的值了，自然而然可以将新值N赋值给V。
 *               反之，V和O不相同，表明该值已经被其他线程改过了则该旧值O不是最新版本的值了，所以不能将新值N赋给V，返回V即可。当多个线程使用CAS操作一个变量是，只有一个线程会成功，并成功更新，其余会失败。失败的线程会重新尝试，当然也可以选择挂起线程
 *      d、CAS的实现需要硬件指令集的支撑，在JDK1.5后虚拟机才可以使用处理器提供的CMPXCHG指令实现。
 *
 *  3、CAS问题
 *      a、 ABA问题
 *          AtomicStampedReference来解决ABA问题
 *      b、自旋时间过长
 *          如果JVM能支持处理器提供的pause指令，那么在效率上会有一定的提升。
 *      c、只能保证一个共享变量的原子操作
 *          可以使用将对象（对象中包含共享变量）做CAS操作就可以保证其原子性
 *          AtomicReference来保证引用对象之间的原子性
 *
 *  4、Java对象头
 *     a、对象的monitor,即获取到对象的锁，存放在Java对象头中
 *     b、锁的4种状态
 *         无锁状态、偏向锁状态、轻量级锁状态和重量级锁状态，
 *         这几个状态会随着竞争情况逐渐升级。锁可以升级但不能降级，意味着偏向锁升级成轻量级锁后不能降级成偏向锁
 *
 *  5、jdk1.6之后synchronized的优化
 *     自旋锁、适应性自旋锁、锁清除、锁粗化、偏向锁、轻量级锁等操作减少锁操作的开销
 *
 *    自旋：首先有多处理器，让阻塞的线程先运行会，等待所释放，会消耗CPU资源
 *          优化;限制自选时间，自选次数达到一定次数时，挂起线程
 *
 *
 *  6、4种锁状态（低-->高）
 *      无所状态、偏向锁状态、轻量级锁状态、重量级锁状态，（锁客升级不可降级）
 *
 *
 *
 *
 * JMM
 *
 * 1、线程间如何通信，如何同步
 *    通信：共享内存、消息传递（如Handler）
 *    同步：控制不同线程之间操作发生的相对顺序的机制
 *    Java采用共享内存的方式实现线程间通信
 *
 * 2、共享变量、存储在主内存中，线程本地内存
 *
 *
 * monitorenter
 * monitorexit
 *
 *
 *
 *
 */