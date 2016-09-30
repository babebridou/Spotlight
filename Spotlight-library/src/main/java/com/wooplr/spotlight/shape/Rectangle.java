package com.wooplr.spotlight.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.wooplr.spotlight.target.Target;

/**
 * Created by PTPS12161 on 30/09/2016.
 */

public class Rectangle extends Circle{

    public Rectangle(Target target, int padding){
        super(target, padding);
    }

    @Override
    public void draw(Canvas canvas, Paint eraser, int padding) {
        Rect rect = this.target.getRect();
        float left = rect.left-padding;
        float right = rect.right+padding;
        float top = rect.top-padding;
        float bottom = rect.bottom+padding;
        float rx = padding;
        float ry = padding;

        canvas.drawRoundRect(left, top, right, bottom, rx, ry, eraser);
    }

    @Override
    public int getRadius() {
        return this.target.getRect().height()/2+this.padding;
    }
}
