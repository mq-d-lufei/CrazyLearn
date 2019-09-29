package com.crazy.crazylearn.Picture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.crazy.crazylearn.R;

import java.security.MessageDigest;

public class Glide4 {


    /***
     *
     *  Glide
     *
     *  加载图片
     *  基本流程、Glide.with.load、into、target
     *
     *  占位图 placeholder
     *  异常占位图 error
     *  指定图片大小
     *      overrider（int w,int h）
     *      override(int size)
     *
     *  缓存
     *      内存缓存
     *          跳过内存缓存   new RequestOptions().skipMemoryCache(boolean)
     *      磁盘缓存
     *          new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
     *          DiskCacheStrategy.NONE      不缓存
     *          DiskCacheStrategy.Data      原始数据
     *          DiskCacheStrategy.Resource  裁切转换后数据
     *          DiskCacheStrategy.All       所有（原始、转换）
     *          DiskCacheStrategy.AUTOMATIC 智能选择
     *
     *      缓存实现原理
     *
     *  指定加载格式
     *      asBitmap asFile asDrawable asGif
     *
     *  回调与监听
     *      into(Terget)
     *      preload() 预加载
     *      FutureTarget submit()
     *      FutureTarget submit(int width, int height)
     *          target阻塞
     *          结合asFile使用，可以得到图片地址
     *      load().listener(new RequestListener(){
     *          onLoadFailed() 图片加载失败回调
     *          onResourceReady()   加载完成后回调
     *      })
     *
     *  图片变换
     *      new RequestOptions().transform()
     *
     *
     *
     *
     *  自定义模块
     *
     *
     */

    public void glide(Context context, String url, ImageView imageView) {

        Transformation<Bitmap> transformation = new Transformation<Bitmap>() {
            @NonNull
            @Override
            public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
                return null;
            }

            @Override
            public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

            }
        };

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
//                .error()
//                .centerCrop()
//                .override(w,h)
                .transform(transformation);

        Glide.with(context).asFile()
                .load(url)
                .into(imageView);

        Target target = new Target() {
            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {

            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {

            }

            @Override
            public void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {

            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }

            @Override
            public void getSize(@NonNull SizeReadyCallback cb) {

            }

            @Override
            public void removeCallback(@NonNull SizeReadyCallback cb) {

            }

            @Override
            public void setRequest(@Nullable Request request) {

            }

            @Nullable
            @Override
            public Request getRequest() {
                return null;
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onDestroy() {

            }
        };


        ImageViewTarget imageViewTarget = new ImageViewTarget(imageView) {
            @Override
            protected void setResource(@Nullable Object resource) {

            }
        };


        Glide.with(context).load(url)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                    }
                });
    }


    public void bitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = true;
    }

}
