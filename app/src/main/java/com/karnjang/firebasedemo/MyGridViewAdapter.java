package com.karnjang.firebasedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridViewAdapter extends GridView{
    private android.view.ViewGroup.LayoutParams params;
    private int oldCount = 0;

    public MyGridViewAdapter(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (getCount() != oldCount)
        {
            int height = getChildAt(0).getHeight() + 1 ;
            oldCount = getCount();
            params = getLayoutParams();
            if(getCount()%2==0){
                params.height = ((getCount() * height)/2);
            }else {
                params.height = (((getCount()+1) * height)/2);
            }

            setLayoutParams(params);
        }

        super.onDraw(canvas);
    }

}
