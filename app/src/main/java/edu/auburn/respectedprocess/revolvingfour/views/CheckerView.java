package edu.auburn.respectedprocess.revolvingfour.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mvb0005 on 4/29/18.
 */

public class CheckerView extends View {
    int color;
    Paint p;
    public CheckerView(Context context) {
        super(context);
        init();
    }

    public CheckerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CheckerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        p = new Paint();
        color = Color.BLACK;
        p.setColor(color);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setStrokeWidth(1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(100,100, 100, p);
    }

    public void setColor(int c){
        color = c;
        p.setColor(c);
        invalidate();
    }
}
