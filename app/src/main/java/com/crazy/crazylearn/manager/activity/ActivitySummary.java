package com.crazy.crazylearn.manager.activity;

public class ActivitySummary {


    /**
     * 一、Activity介绍
     *     1、配置清单
     *        声明活动
     *        声明意图过滤器：显示/隐式启动Activity
     *        声明权限
     *
     *     2、生命周期
     *      onCreate -  系统首次创建活动时触发，活动进入创建状态
     *      onStart -   Activity对用户可见
     *      onResume -  与用户交互状态
     *      onPause -   它表示活动不再在前台（尽管如果用户处于多窗口模式，它仍然可见）
     *          1、某些事件会中断应用程序执行，如onResume（）部分所述。这是最常见的情况
     *          2、在Android 7.0（API级别24）或更高版本中，多个应用程序以多窗口模式运行。由于只有一个应用程序（窗口）可以随时关注，系统会暂停所有其他应用程序
     *          3、将打开一个新的半透明活动（如对话框）。只要活动仍然部分可见但不是焦点，它仍然暂停
     *          ---->释放系统资源
     *          ---->onPause()执行非常简短，并且不一定要有足够的时间来执行保存操作。
     *      onStop -    当您的活动不再对用户可见时，它已进入 Stopped状态
     *          1、当新启动的活动覆盖整个屏幕时，可能会发生这种情况
     *      onDestroy  -  活动被销毁之前调用
     *          1、活动正在结束（由于用户完全解雇活动或因活动 finish()被召唤）
     *          2、由于配置更改（例如设备旋转或多窗口模式），系统暂时销毁活动
     *
     *     3、保存和恢复瞬态UI状态
     *          使用onSaveInstanceState（）保存简单，轻量级的UI状态
     *          使用已保存的实例状态还原活动UI状态
     *
     *     4、协调活动
     *          A启动B的过程（B不透明）
     *          ActivityA   onCreate onStart onResume onPause                               onStop                              onRestart onStart  onResume
     *          ActivityB                                       onCreate onStart onResume           onPause onStop onDestroy
     *
     *           A启动B的过程（B透明）
     *           ActivityA   onCreate onStart onResume onPause                                                         onResume
     *           ActivityB                                       onCreate onStart onResume | onPause onStop onDestroy
     *
     *     5、处理活动状态更改
     *          不同的事件，一些用户触发的和一些系统触发的，可能导致Activity从一个状态转换到另一个状态。
     *
     *     6、发生配置更改
     *          纵向和横向之间的变化、更改语言或输入设备
     *
     *     7、处理多窗口案例
     *          当应用程序进入多窗口模式（Android 7.0（API级别24）及更高级别）时，系统会通知当前正在运行的配置更改活动，从而完成上述生命周期转换。如果已经处于多窗口模式的应用程序调整大小，也会发生此行为。
     *          在多窗口模式中，尽管有两个应用程序对用户可见，但只有用户与之交互的应用程序位于前台并具有焦点。该活动处于“已恢复”状态，而另一个窗口中的应用程序处于“暂停”状态。
     *          当用户从应用程序A切换到应用程序B时，系统会调用 onPause()应用程序A和onResume()应用程序B.每次用户在应用程序之间切换时，它都会在这两种方法之间切换。
     *
     *     8、活动或对话框出现在前台
     *
     *
     *     9、管理任务
     *          FLAG_ACTIVITY_NEW_TASK
     *          FLAG_ACTIVITY_SINGLE_TOP
     *          FLAG_ACTIVITY_CLEAR_TOP
     *
     *     10、清理回退栈
     *          alwaysRetainTaskState
     *          clearTaskOnLaunch
     *          finishOnTaskLaunch
     *
     *
     *     11、流程和应用程序生命周期
     *          为了确定在内存不足时应该杀死哪些进程，Android会根据其中运行的组件以及这些组件的状态将每个进程置于“重要性层次结构”中。这些流程类型（按重要性顺序）
     *
     *          前台进程
     *              它正在Activity 用户正在与之交互的屏幕顶部运行（其 onResume()方法已被调用）。
     *              它有一个BroadcastReceiver当前正在运行的（它的BroadcastReceiver.onReceive()方法正在执行）。
     *              它有一个Service当前在其回调之一执行代码（Service.onCreate()， Service.onStart()，或 Service.onDestroy()）。
     *
     *          可见进程
     *              它运行的Activity 是屏幕上的用户可见但不在前台（其 onPause()方法已被调用）。例如，如果前景活动显示为允许在其后面看到前一个活动的对话框，则可能会发生这种情况。
     *              它有一个Service作为前台服务运行的Service.startForeground()（通过要求系统将服务视为用户知道的或者对他们基本可见的东西）。
     *              它正在托管系统用于用户知道的特定功能的服务，例如动态壁纸，输入法服务等。
     *
     *          服务进程
     *              一个拿着Service 已经开始与 startService()方法。虽然用户无法直接看到这些进程，但它们通常是用户关心的事情（例如后台网络数据上传或下载），因此系统将始终保持此类进程运行，除非没有足够的内存来保留所有进程前景和可见过程。
     *              已经运行了很长时间（例如30分钟或更长时间）的服务可能会降级，以允许其进程下降到下面描述的缓存LRU列表。这有助于避免出现内存泄漏或其他问题的长时间运行服务占用大量RAM而导致系统无法有效使用缓存进程的情况。
     *
     *          缓存进程
     *              是一个当前没有必要，因此系统是免费时别处需要存储器根据需要将其杀死。在正常运行的系统中，这些是内存管理中涉及的唯一过程：运行良好的系统将始终具有多个缓存进程（用于在应用程序之间进行更有效的切换），并根据需要定期终止最旧的进程。只有在非常关键（且不可取）的情况下，系统才会到达所有缓存进程被杀死的点，并且必须开始终止服务进程。
     *
     *
     *     12、Parcelables和Bundles
     *          在进程之间发送时，我们建议您不要使用自定义parcelables。如果您将自定义 Parcelable对象从一个应用程序发送到另一个应用程序，则需要确保发送和接收应用程序上都存在完全相同的自定义类版本。通常，这可能是跨两个应用程序使用的公共库。如果您的应用尝试向系统发送自定义parcelable，则可能会发生错误，因为系统无法解组它不知道的类。
     *          Binder事务缓冲区具有有限的固定大小，当前为1MB，
     *          程正在进行的所有事务共享。由于此限制是在进程级别而不是在每个活动级别，因此这些事务包括应用程序中的所有绑定事务，例如onSaveInstanceState，startActivity以及与系统的任何交互。超出大小限制时，将抛出TransactionTooLargeException。
     *          我们建议您将保存的状态保存在少于50k的数据中。
     *
     *          注意：
     *          在Android 7.0（API级别24）及更高版本中，系统会将TransactionTooLargeException作为运行时异常抛出。在较低版本的Android中，系统仅在logcat中显示警告。
     *
     *
     *
     *
     */


}
