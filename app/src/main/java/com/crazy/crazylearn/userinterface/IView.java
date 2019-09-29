package com.crazy.crazylearn.userinterface;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class IView extends View {

    public IView(Context context) {
        super(context);
    }

    public IView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
