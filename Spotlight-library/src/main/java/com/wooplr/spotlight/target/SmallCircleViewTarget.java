package com.wooplr.spotlight.target;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by jitender on 10/06/16.
 */
public class SmallCircleViewTarget implements Target {

    private View view;
    private double width;
    private double height;
    private double offsetX;
    private double offsetY;


    public SmallCircleViewTarget(View view, double width, double height, double offsetX, double offsetY) {
        this.view = view;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public Point getPoint() {

        int[] location = new int[2];
        view.getLocationInWindow(location);
        int minX = location[0];
        int maxX = location[0]+view.getWidth();
        int minY = location[1];
        int maxY = location[1]+view.getHeight();
        int x = (int)((double)minX+(double)(maxX-minX)*offsetX);
        int y = (int)((double)minY+(double)(maxY-minY)*offsetY);
        return new Point(x,y);
    }

    @Override
    public Rect getRect() {
        int[] location = new int[2];
        view.getLocationInWindow(location);

        int minX = location[0];
        int maxX = location[0]+view.getWidth();
        int minY = location[1];
        int maxY = location[1]+view.getHeight();
        int x = (int)((double)minX+(double)(maxX-minX)*offsetX);
        int y = (int)((double)minY+(double)(maxY-minY)*offsetY);

        int halfRectWidth = (int)((double)view.getWidth()*width/2d);
        int halfRectHeight = (int)((double)view.getHeight()*height/2d);

        return new Rect(
                x-halfRectWidth,
                y-halfRectHeight,
                x+halfRectWidth,
                x+halfRectHeight
        );
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public int getViewLeft() {
        return getRect().left;
    }

    @Override
    public int getViewRight() {
        return getRect().right;
    }

    @Override
    public int getViewBottom() {
        return getRect().bottom;
    }

    @Override
    public int getViewTop() {
        return getRect().top;
    }

    @Override
    public int getViewWidth() {
        return view.getWidth();
    }

    @Override
    public int getViewHeight() {
        return view.getHeight();
    }

}
