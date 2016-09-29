package com.wooplr.spotlight.target;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

/**
 * Created by jitender on 10/06/16.
 */
public class SmallCircleViewTarget implements Target {

    private View view;
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public enum Mode{
        HorizontalLeader,
        Normal
    }


    public SmallCircleViewTarget(View view, Mode mode){
        this.view = view;
        switch(mode){
            case HorizontalLeader:
                this.minX = 0d;
                this.minY = 0d;
                this.maxX = 0.5d;
                this.maxY = 1d;
                break;
            default:
                this.minX = 0d;
                this.minY = 0d;
                this.maxX = 1d;
                this.maxY = 1d;
                break;
        }
    }

    public SmallCircleViewTarget(View view, double minX, double minY, double maxX, double maxY) {
        this.view = view;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    @Override
    public Point getPoint() {

        int[] location = new int[2];
        view.getLocationInWindow(location);
        double xLeft = location[0];
        double yUp = location[1];
        double viewWidth = view.getWidth();
        double viewHeight = view.getHeight();
        double realXmin = xLeft+minX*viewWidth;
        double realYmin = yUp +minY*viewHeight;
        double realXmax = xLeft+maxX*viewWidth;
        double realYmax = yUp + maxY*viewHeight;
        double x = (realXmin+realXmax)/2.0d;
        double y = (realYmin+realYmax)/2.0d;
        return new Point((int)x,(int)y);
    }

    @Override
    public Rect getRect() {
        int[] location = new int[2];
        view.getLocationInWindow(location);

        double xLeft = location[0];
        double yUp = location[1];
        double viewWidth = view.getWidth();
        double viewHeight = view.getHeight();
        double realXmin = xLeft+minX*viewWidth;
        double realYmin = yUp +minY*viewHeight;
        double realXmax = xLeft+maxX*viewWidth;
        double realYmax = yUp + maxY*viewHeight;

        Log.d("SmallCircleViewTarget", "minX "+minX);
        Log.d("SmallCircleViewTarget", "minY "+minY);
        Log.d("SmallCircleViewTarget", "maxX "+maxX);
        Log.d("SmallCircleViewTarget", "maxY "+maxY);
        Log.d("SmallCircleViewTarget", "viewWidth "+viewWidth);
        Log.d("SmallCircleViewTarget", "viewHeight "+viewHeight);
        Log.d("SmallCircleViewTarget", "xLeft "+xLeft);
        Log.d("SmallCircleViewTarget", "yUp "+yUp);
        Log.d("SmallCircleViewTarget", "realXmin "+realXmin);
        Log.d("SmallCircleViewTarget", "realYmin "+realYmin);
        Log.d("SmallCircleViewTarget", "realXmax "+realXmax);
        Log.d("SmallCircleViewTarget", "realYmax "+realYmax);


        return new Rect(
                (int)realXmin,
                (int)realYmin,
                (int)realXmax,
                (int)realYmax
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
        return getRect().width();
    }

    @Override
    public int getViewHeight() {
        return getRect().height();
    }

}
