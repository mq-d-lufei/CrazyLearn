package com.crazy.crazylearn.manager.background.handler;

import android.os.Handler;
import android.os.Message;

/***
 *
 * 1、Handler允许您发送和处理与线程的 MessageQueue 相关联的{@link Message}和Runnable对象。
 *      每个Handler实例都与单个线程和该线程的消息队列相关联。
 *      当您创建一个新的Handler时，它被绑定到创建它的线程/消息队列-从那时起，
 *      它将把消息和运行程序传递到消息队列中，并在它们从消息队列中出来时执行它们。
 *
 *2、一个Handler有两个主要用途：
 *      (1)调度消息和runnables，以便在将来的某个点执行；
 *      (2)将要在与您自己的线程不同的线程上执行的操作排队。
 *
 *3、调度消息由{@link#POST}、{@link#postAtTime(runnable，long)}、{@link#postDelayed}、
 *      {@link#sendemptyMessage}、{@link#sendMessage}、{@link#sendMessageAtTime}和
 *      {@link#sendMessageDelayed}方法完成。
 *      <em>POST</em>版本允许您在收到消息队列时对可运行的对象进行排队；
 *      <em>sendMessage</em>版本允许您对包含由Handler的{@link#handleMessage}方法
 *      处理的数据束的{@link消息}对象进行排队(要求您实现Handler的子类)。
 *
 *4、当投递或发送到Handler时，您可以允许在消息队列准备就绪时立即处理该项，
 *      或者在消息队列被处理之前指定一个延迟，或者指定处理它的绝对时间。
 *      后两者允许您实现超时、滴答和其他基于时间的行为。
 *
 *5、当为应用程序创建进程时，其主线程专用于运行消息队列，负责管理顶级应用程序对象(活动、
 *      广播接收器等)及其创建的任何窗口。您可以创建自己的线程，并通过Handler与主应用程序线程进行通信。
 *      这是通过调用与前面相同的<em>post</em>或<em>sendMessage</em>方法来完成的，
 *      而是从新线程调用的。然后在Handler的消息队列中对给定的可运行或消息进行调度，
 *      并在适当时进行处理。
 */

public class MyHandler extends Handler {


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);


    }

    public void hello() {
    }
}


