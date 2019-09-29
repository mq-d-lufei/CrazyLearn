package com.crazy.crazylearn.manager.background;

import android.content.Context;
import android.location.LocationManager;

import static android.content.Context.LOCATION_SERVICE;

public class BackgroundManager {

    public void initService(Context context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);


    }

    /**
     *
     * 1、线程
     *
     * 2、线程池
     *
     * 3、Handler
     *
     * 4、IntentService
     *
     * 5、Binder
     *
     *
     *
     *
     *
     *
     *
     */

    /***
     *
     *  一、unix中常见IPC :
     *      信号、管道、信号量、消息队列、共享存储、Unix域套接字
     *
     *  二、Android应用程序服务
     *
     *  三、Android 服务概要
     *      1、应用服务（Application）
     *          应用中通过继承Service后开发出来的
     *
     *      2、系统服务（Framework）
     *         Java系统服务（核心平台服务、硬件服务）------ Framework层提供
     *              在Android启动时，Java系统服务由SystemServer系统进程启动
     *              核心平台服务
     *                  ActivityManagerService
     *                  WindowManagerService
     *                  PacketManagerService
     *              硬件服务（用于控制底层硬件）
     *                  AlertManagerService
     *                  Location Manager Service
     *                  。。。。。。
     *
     *         本地系统服务 ------ Libraries层提供
     *             使用C++编写
     *
     *  四、运行系统服务
     *
     *      init --> 运行MediaServer进程  --> 启动本地系统服务（除了SurfaceFlinger）
     *           --> 运行Zygote进程 --> 运行SystemServer进程 --> 运行Java系统服务
     *
     *  五、媒体服务器（MediaServer）
     *      是系统进程、运行本地系统服务、有init进程启动运行
     *      创建各个本地服务实例对象，并注册服务到ContextManager
     *
     *  六、系統服務（SystemServer）
     *      是Java进程，有Zygote进程生成，运行在Dalvik虚拟机中的Java进程，运行多种Java系统服务，还有SufaceFlinger本地系统服务
     *
     *
     *
     *
     */

    /***
     *
     * Handler
     *
     * handler用于同一个进程中不同线程的通信
     * Binder用于不同进程之间通信，由一个进程的Binder客户端向另一个进程的服务端发送事务
     *
     *
     * 主线程为什么不会卡死?
     * 1，epoll模型
     * 当没有消息的时候会epoll.wait（主线程没有消息处理时阻塞在管道的读端），等待句柄写的时候再唤醒，这个时候其实是阻塞的。
     * 2，所有的ui操作都通过handler来发消息操作。
     * 比如屏幕刷新16ms一个消息，你的各种点击事件，所以就会有句柄写操作，唤醒上文的wait操作，所以不会被卡死了。
     *
     *
     * 1、创建：
     * ActivityThread -> main() -> Looper.prepareMainLooper() ... Looper.loop() -->主线程消息队列创建成功
     *
     * 2、获取主线程handler
     * ActivityThread -> getHandler():mH ->
     *      handleBindApplication()
     *      Looper.myLooper().quit();
     *      handleReceiver((ReceiverData)msg.obj);
     *      handleCreateService((CreateServiceData)msg.obj);
     *      handleBindService((BindServiceData)msg.obj);
     *      handleUnbindService((BindServiceData)msg.obj);
     *      handleStopService((IBinder)msg.obj);
     *      handleConfigurationChanged((Configuration) msg.obj);
     *      Process.killProcess(Process.myPid());
     *      通过回调处理消息
     *
     * 3、发送消息：
     * ActivityThread -> ApplicationThread extends IApplicationThread.Stub 其它进程可以发送消息
     *    mH.sendMessage() 其他线程发送消息
     *    Message ->  boolean enqueueMessage(Message msg, long when)
     *    //如果唤醒epoll，写句柄唤醒阻塞
     *    if (needWake) {
     *        nativeWake(mPtr);
     *    }
     *
     * 4、获取消息
     * Looper-> loop() -> for(;;) queue.next()(might block)
     *
     *     当主线程没有消息可处理的时候，该方法会阻塞主线程
     *     epoll.wait（主线程没有消息处理时阻塞在管道的读端），等待句柄写的时候再唤醒，这个时候其实是阻塞的。
     *     Message : mBlocked: 指示next（）是否在pollOnce（）中以非零超时等待被阻止。
     *     nativePollOnce(ptr, nextPollTimeoutMillis);
     *
     *     比如屏幕刷新16ms一个消息，你的各种点击事件，就会有句柄写操作，唤醒上文的wait操作，所以不会被卡死了。
     *
     *     轮询到消息：
     *        msg.target.dispatchMessage(msg);
     *
     * 5、处理消息
     * ActivityThread -> H
     *      handleBindApplication()
     *      Looper.myLooper().quit();
     *      handleReceiver((ReceiverData)msg.obj);
     *      handleCreateService((CreateServiceData)msg.obj);
     *      handleBindService((BindServiceData)msg.obj);
     *      handleUnbindService((BindServiceData)msg.obj);
     *      handleStopService((IBinder)msg.obj);
     *      handleConfigurationChanged((Configuration) msg.obj);
     *      Process.killProcess(Process.myPid());
     *      通过回调处理消息
     *
     * 6、主线程Handler回调执行耗时操作，导致后续消息处理不及时 -> ANR
     *
     *
     */


    /**
     *
     * 内核剖析
     *
     * 零、 Binder
     *
     * 问题：
     *  1、服务端、客户端、驱动端通讯过程
     *  2、设计方式
     *  3、数据封装传递解析过程
     *  4、调用过程
     *
     * 一、Binder 开始
     *
     * 1、客户端想要访问远程服务，必须获得远程服务在Binder对象中对应的onRemote引用，
     * 2、先挂起客户端线程-->等待服务端线程执行，完成，通知客户端线程-->客户端线程继续执行
     * 3、通过Binder驱动进行中转，故，存在两个Binder对象，一个是服务端Binder对象，
     *      另一个是Binder驱动中的Binder对象，Binder驱动中的对象不会在额外产生一个线程
     *
     *
     * 二、设计Server端
     *
     * 1、继续Binder类，
     * 2、public boolean onTransact(int code, android.os.Parcel element, android.os.Parcel reply, int flags) throws android.os.RemoteException
     *    重载onTrasact()方法，并从data变量中取出客户端传递的参数
     * 3、code变量：用于标识客户端期望调用服务端的哪个函数，所以双发需要约定一组int值，
     *      不同值代表不同服务端函数，客户顿transact()函数中使用
     * 4、enforceInterface():为了某种校验，与客户端的writeInterfaceToken()对应
     * 5、readString():用于重包裹中读取一个字符串
     * 6、reply： 该IPC调用的客户端期望返回的结果,可写入返回数据
     *
     * 三、设计Client端
     *
     * 1、要想使用服务端，首先需要获取服务端在Binder驱动中对mRemote变量的引用
     * 2、parcel 数据包裹类，常用原子类型，继承与Parcel类的数据，
     *      包裹中添加的内容是有序的，客户端与服务端约定好的
     * 3、服务端onTransact()、客户端transact()
     * 4、客戶端調用transact方法后客戶端進入Binder驱动，Binder驱动挂起当前线程，并向远程服务发起消息（Parcel包裹）
     *      服务端拿到包裹数据后会拆解，然后执行指定的服务函数，执行完毕后，再把执行结果繁荣reply包裹中，然后服务端
     *      向Binder驱动发送notify消息，从而使客户端线程从Binder驱动代码区返回到客户端代码区
     * 5、客户端解析reply包裹数据，完成一次调用
     *
     * (Binder ? 全局服务，系统中的任何应用程序都可以访问)
     *
     * 四、使用Service类
     *
     * 1、手工写Binder服务端和客户端存在两个重要问题
     *      a、客户端如何获取服务端Binder对象的引用
     *      b、客户端与服务端必须事先约定两件事
     *          data包裹Parcel中个数据的顺序
     *          服务端不同函数的int标识
     *
     * 2、获取Binder对象
     *     0、系统服务：仅用Binder扩展
     *        客户端服务：用Service编写
     *        AMS中提供了startService()函数用于启动客户服务
     *     a、通过ContextImpl类中的startService(Intent)或 bindService(intent,ServiceConnect,flags)
     *          与一个服务建立连接
     *     b、当客户端请求AMS启动Service时，该Service如果正常启动，那么AMS就会远程调用ActivityThread类
     *          中的ApplicationThread对象，调用的参数中会包含Service的Binder引用，然后会在ApplicationThread
     *          中回调bindService中的conn接口，因此，客户端就可以在onServiceConnected()回调方法中将
     *          Service保存为全局变量，从而在客户端任何地方都可以随时调用远程服务
     *     c、此时服务绑定成功
     *
     * 3、AIDL
     *     a、定义Interface,实现对应的服务函数
     *       b、Stub： 由服务端使用，onBind() 方法返回Stub的具体实现类，并实现服务端具体的方法
     *          abstract类，基于Binder类，实现对应的服务接口，具体的实现函数有服务端具体实现，
     *          重载了OnTransact()根据约定取出数据
     *          asInterface() 检查获取本地Binder还是远程Bind
     *         c、Proxy:
     *              transtract() 客户端对发送数据的约定
     *
     *
     */

    /***
     *
     * Android 进场间通信方式：
     *  Intent & Bundle
     *  Messenger
     *  AIDL
     *  ContentProvider
     *  Socket
     *  文件共享
     *
     *
     *
     * IPC （inter Process Communication）
     *  Binder本是 IPC ( Inter Process Communication 工具，在Android中主要用
     *  于支持 ( Remote Procedure Cal1 使得当前进程调用另一个进程的函数时就像调
     *  用自身的函数一样轻松、简单
     *
     * 1、Linux现有的所有进程间IPC方式：
     *    1. 管道：在创建时分配一个page大小的内存，缓存区大小比较有限；
     *    2. 消息队列：信息复制两次，额外的CPU消耗；不合适频繁或信息量大的通信；
     *    3. 共享内存：无须复制，共享缓冲区直接付附加到进程虚拟地址空间，速度快；但进程间的同步问题操作系统无法实现，必须各进程利用同步工具解决；
     *    4. 套接字：作为更通用的接口，传输效率低，主要用于不通机器或跨网络的通信；
     *    5. 信号量：常作为一种锁机制，防止某进程正在访问共享资源时，其他进程也访问该资源。因此，主要作为进程间以及同一进程内不同线程之间的同步手段。
     *    6. 信号: 不适用于信息交换，更适用于进程中断控制，比如非法内存访问，杀死某个进程等；
     *
     * 2、从5个角度来展开对Binder的分析：
     *    a、从性能的角度 数据拷贝次数：
     *      Binder数据拷贝只需要一次，而管道、消息队列、Socket都需要2次，但共享内存方式一次内存拷贝都不需要；
     *      从性能角度看，Binder性能仅次于共享内存。
     *    b、从稳定性的角度：
     *      Binder是基于C/S架构的，简单解释下C/S架构，是指客户端(Client)和服务端(Server)组成的架构，
     *      Client端有什么需求，直接发送给Server端去完成，架构清晰明朗，Server端与Client端相对独立，稳定性较好；
     *      而共享内存实现方式复杂，没有客户与服务端之别， 需要充分考虑到访问临界资源的并发同步问题，否则可能会出现死锁等问题；
     *      从这稳定性角度看，Binder架构优越于共享内存
     *    c、从安全的角度（更重要的原因）：
     *      传统Linux IPC的接收方无法获得对方进程可靠的UID/PID，从而无法鉴别对方身份；
     *      Android为每个安装好的应用程序分配了自己的UID，故进程的UID是鉴别进程身份的重要标志，
     *      前面提到C/S架构，Android系统中对外只暴露Client端，Client端将任务发送给Server端，
     *      Server端会根据权限控制策略，判断UID/PID是否满足访问权限，目前权限控制很多时候是通过弹出权限询问对话框，
     *      让用户选择是否运行。
     *    d、从语言层面的角度
     *
     *
     * 3、IPC原理
     *      Android进程虚拟地址空间
     *      用户空间（进程间不可共享）
     *      内核空间（进程间可共享、客户端与服务端进程通过内核态中的驱动进行交互）
     *
     * 4、Binder原理
     *      Binder通信采用C/S架构，从组件视角来说，包含Client、Server、ServiceManager以及binder驱动，其中ServiceManager用于管理系统中的各种服务
     *
     *      a、注册服务（addService）
     *      b、获取服务（getService）
     *      c、使用服务
     *
     * 5、C/S模式
     *      client: transact()    发送事务请求
     *      server: onTransact()  接收相应事务
     *
     *      AIDL 中间通信过程约定封装
     *          code调用什么函数的约定、data数据分包解包约定、...
     *
     *          Client进程1   |    Client进程        |      Server进程
     *                        |                     |                          Application
     *                        |                     |
     *  -----------------------------------------------------------------------------------------       用户空间
     *                        |                     |       ServiceManager
     *                        |                     |       AMS、WMS、PMS      Framework
     *                        |                     |
     * ------------------------------------------------------------------------------------------
     *
     *
     *                              Binder Driver                              Kernel                   内核空间
     *
     * 6、ServiceManager
     *  a、ServiceManager是Binder IPC通信过程中的守护进程,自行编写了binder.c直接和Binder驱动来通信，简单而高效。
     *  b、功能：查询和注册服务
     *  c、启动过程主要以下几个阶段
     *      打开Binder驱动：binder_open;
     *      注册成为Binder服务的大管家：binder_become_context_manager;
     *      进入无限循环，处理客户端发来的请求：binder_loop;
     *  d、由初始化进程通过解析init.rc文件而创建的
     *  e、对应的可执行程序：/system/bin/ServiceManager中的service_manager.c
     *  f、进程名为/system/bin/ServiceManager
     *  g、入口函数是service_manager.c中的main()函数
     *  总结:
     *      ServiceManger集中管理系统内的所有服务，通过权限控制进程是否有权注册服务，通过字符串名称来查找对应的服务;
     *      由于ServiceManger进程建立跟所有向其注册服务的死亡通知，那么当服务所在进程死亡后，会只需告知ServiceManager。
     *      每个客户端通过查询的ServiceManager可获取服务器进程的情况，降低所有客户端进程直接检测会导致负载过重。
     *
     *  注册服务：记录服务名和处理信息，保存到svclist列表;
     *  查询服务：根据服务名查询相应的的处理信息。
     *
     * 7、获取ServiceManager
     *   对于gDefaultServiceManager对象，如果存在则直接返回；如果不存在则创建该对象，
     *    创建过程包括调用open()打开binder驱动设备，利用mmap()映射内核的地址空间
     *
     *   ProcessState::self()主要工作：
     *      调用open()，打开/dev/binder驱动设备；
     *      再利用mmap()，创建大小为1M-8K的内存地址空间；
     *      设定当前进程最大的最大并发Binder线程个数为16。
     *
     *  binder 驱动会调用 mmap() 在内核空间分配一块内存，这块内存同时映射进了内核空间和接收进程的用户空间，
     *  所以数据只需要从发送方进程的用户空间 copy 到内核空间，就相当于把数据发送到了接收进程的用户空间，
     *  因此整体只需要一次 copy
     *
     */

    /***
     * https://yq.aliyun.com/articles/567593?spm=5176.10695662.1996646101.searchclickresult.15bf729d0w1i9f
     * https://zhuanlan.zhihu.com/p/24414378
     * https://zhuanlan.zhihu.com/p/68849297
     * https://zhuanlan.zhihu.com/p/53723652
     *
     * 1、IPC
     * 2、Dalvik、ART、JVM 结构、区别、优缺点、GC算法
     * 3、Android应用启动流程
     * 4、activity、service启动流程
     *
     *
     *  Java编译器将Java文件编译为class文件
     *  dx工具将编译输出的类文件转换为dex文件(Android虚拟机不支持class文件)
     *
     *
     *  Dalvik
     *      负责将dex翻译为机器码交由系统调用
     *      缺陷：每次执行代码，都需要Dalvik将操作码代码翻译为机器对应的微处理器指令，然后交给底层系统处理，运行效率很低
     *
     *      JIT意思是Just In Time Compiler，就是即时编译技术
     *        动态编译
     *        当App运行时，每当遇到一个新类，JIT编译器就会对这个类进行即时编译，经过编译后的代码，会被优化成相当精简的原生型指令码（即native code），这样在下次执行到相同逻辑的时候，速度就会更快
     *        JIT编译器可以对执行次数频繁的 dex/odex 代码进行编译与优化，将 dex/odex 中的 Dalvik Code（Smali 指令集）翻译成相当精简的 Native Code 去执行，JIT 的引入使得 Dalvik 的性能提升了 3~6 倍
     *        JIT缺陷
     *           每次启动应用都需要重新编译（没有缓存）
     *           运行时比较耗电，耗电量大
     *
     *  AOT是指"Ahead Of Time"，与"Just In Time"不同，从字面来看是说提前编译。
     *
     */

    /***
     * APK程序的运行过程
     *
     *  ActivityThread从main()方法开会执行，调用prepareMainLooper(),创建UI线程消息队列，
     *  然后创建ActivityThread对象，创建H(Handler)和ApplicationThread(Binder->Stub),
     *      Binder负责IPC调用，收到远程消息后，通过H负责分发消息，UI主线程会异步地从消息队列取消息，
     *      并执行相应的操作，如onCreate、onStart、onStop等
     *  然后调用Looper.loop()开始消息轮询。
     *
     *  之后WindowManagerService显示界面范畴......
     *
     */

    /**
     *
     * Android启动 (https://blog.csdn.net/u012267215/article/details/91406211)
     * Service启动 （https://www.jianshu.com/p/77fe505e2287）
     *
     *  1、init进程是Linux系统中用户空间的第一个进程，进程号固定为1。
     *      Kernel启动后，在用户空间启动init进程，并调用init中的main()方法执行init进程的职责
     *
     *
     *
     *  Launcher进程
     *      ActivityManagerProxy ----1、发送startActivity---->
     *      serviceManager AIDL获取AMS
     *  system_server进程（）
     *      ActivityManagerService -->2、发送送创建进程的请求--->
     *  zygote进程
     *      3、fork()新进程 -->
     *  App进程
     *      4、创建成功，发送attachApplication -->
     *  ActivityManagerService
     *      ApplicationThreadProxy --5、发送scheduleLaunchActivity-->
     *  App进程
     *      ApplicationThread----6、发送消息H.LAUNCHACTIVITY-->
     *      ActivityThread----7、handleLaunchActivity---->
     *      Activity.onCreate ....
     *
     */


    /**
     *
     * Context  場景（应用中不同场景的切换）  上下文
     *      Activity基于Context，Service也基于Context
     *      但也都实现了一些其他的接口，接口实现功能，继承才是类的本质
     *      Context总数 = Activity + Service + 1（Application）
     *
     *
     *              abstract  class  Context
     *               |                       |
     *         ContextImpl               ContextWrapper
     *           具体实现      ContextThemeWrapper       |
     *                                 Activity          Service
     */

    /**
     *
     * ActivityManagerService
     *
     *  1、統一调度各应用程序的Activity
     *  2、内存管理，Activity推出后合适杀死等内存问题
     *  3、进程管理，向外提供了查询系统正在运行的进程信息API
     *
     *  Activity调度
     *      各应用进程启动新的Activity或停止当前Activity，都要首先报告给AMS
     *
     *
     */



    /***
     *
     * 创建窗口过程
     *  WindowManager
     *      应用窗口（值该窗口对应一个Activity，创建在Activity内部完成）
     *      子窗口（该窗口必须有一个父窗口）
     *      系统窗口
     *
     *
     */

    /**
     * Framework运行环境
     *  Zygote
     *  SystemServer
     *
     *  启动第一个Activity
     *
     *
     */


    /***
     *
     *  多线程
     *
     *  并行：多个CPU或多台机器同时执行一段处理逻辑，真的同时
     *  并发：通过CPU调度算法，看上去同时执行，实际上从CPU层面上不是真正同时
     *
     *
     *
     *
     *
     *
     *
     */
}


