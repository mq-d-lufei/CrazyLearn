package com.crazy.crazylearn.userinterface;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class IViewGroup extends ViewGroup {

    public IViewGroup(Context context) {
        super(context);
    }

    public IViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        requestDisallowInterceptTouchEvent(false);//让ViewGroup本身不拦截触摸事件
        return super.onTouchEvent(event);
    }
}
