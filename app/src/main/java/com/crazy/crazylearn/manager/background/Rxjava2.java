package com.crazy.crazylearn.manager.background;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class Rxjava2 {

    /***
     *
     * https://juejin.im/post/5b17560e6fb9a01e2862246f#heading-101
     *
     *  Observable
     *
     *  Flowable
     *
     *  Single
     *      单个值响应，实现响应模式。
     *
     *  Completable
     *
     *  Maybe
     *
     *  1、create()
     *      创建可观察者
     *  2、just(T item) .. just(T item1,T item2,...,T item10)
     *      创建可观察者，并发送数据，最多发送10个数据     *
     *  3、fromArray(T... items)
     *      创建可观察者，发送数据，数据来源于数组
     *  4、fromCallable(Callable call)
     *      发送Callable的结果值
     *  5、fromFuture()(Future future)
     *
     *  6、map() 可以将被观察者发送的数据类型转变成其他的类型
     *  7、flatMap() 可以将事件序列中的元素进行整合加工，返回一个新的被观察者。
     *      执行(发射)次序是一定的，完成次序是不确定的（flatmap转换为被观察者时，多个转换的过程耗时不同,会出现谁先转换完成谁先发射）
     *  8、concatMap() 和 flatMap()
     *      基本上是一样的，只不过 concatMap() 转发出来的事件是有序的，而 flatMap() 是无序的。
     *  9、concat()
     *      将多个观察者组合在一起，然后按照之前发送顺序发送事件。需要注意的是，concat() 最多只可以发送4个事件。
     *  10、concatArray()
     *      与 concat() 作用一样，不过 concatArray() 可以发送多于 4 个被观察者。
     *      merge()
     *      与concat() 作用基本一样，不过 concat() 是串行发送事件，而 merge() 并行发送事件
     *      zip()
     *      将多个被观察者合并，根据各个被观察者发送事件的顺序一个个结合起来，最终发送的事件数量会与源 Observable 中最少事件的数量一样
     */

    /**
     * Observable
     */
    public void onObservable() {
        Observable.just(1, 2, 3, 4).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * Flowable
     */
    public void onFlowable() {
        Flowable.just(1, 2, 3, 4, 5, 6).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * Single只能发送单个消息，不能发送消息流，而且观察者接收到消息也只有两种情况，
     * onSuccess() 要么接收成功，
     * onError()   要么接收失败
     * <p>
     * Single: SingleSource
     * subscribe(SingleObserver observer)
     */
    public void onSingle() {

        Disposable subscribe0 = Single.just(1).subscribe();

        Disposable subscribe1 = Single.just(1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

        Disposable subscribe2 = Single.just(1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        });

        Single.just(1).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                return integer;
            }
        }).flatMap(new Function<Integer, SingleSource<?>>() {
            @Override
            public SingleSource<Integer> apply(Integer integer) throws Exception {
                return Single.error(new Throwable("single error"));
            }
        }).subscribe(new SingleObserver<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Object integer) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });


        Single.error(new Throwable("error")).subscribe(new SingleObserver<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    /***
     *
     * Completable  表示延迟计算，没有任何值，只表示完成或异常。
     *      onComplete()    一旦延迟计算正常完成将会被调用
     *      onError()       一旦延迟计算抛出异常将会被调用
     *
     * Completable：CompletableSource
     *      subscribe(CompletableObserver observer)
     *
     */

    public void onCompletable() {
        Completable.complete().subscribe();
        Completable.error(new Throwable("Completable error")).subscribe();
        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {

            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    /***
     *
     * Maybe  表示延迟计算和单个值的发射，这个值可能根本没有或异常。
     *      onSuccess
     *      onError
     *      onComplete
     */
    public void onMaybe() {
        Maybe.just(1).subscribe();
        Maybe.error(new Throwable("error")).subscribe();
        Maybe.just(1).subscribe(new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }
}
