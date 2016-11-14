package com.krava.dagger2test.presentation.view.behavoir;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.devspark.robototextview.widget.RobotoTextView;
import com.krava.dagger2test.presentation.Global;
import com.krava.dagger2test.R;


/**
 * Created by krava2008 on 24.10.16.
 */

public class MainWeatherTextBehavior extends CoordinatorLayout.Behavior<RobotoTextView> {
    private Context context;
    private float mCustomFinalYPosition;
    private float mCustomStartXPosition;
    private float mCustomStartToolbarPosition;
    private float mCustomStartHeight;
    private float mCustomFinalHeight;
    private float mStartToolbarPosition;
    private float mChangeBehaviorPoint;
    private int mStartYPosition = 0;
    private int mFinalYPosition = 0;
    private int mStartHeight = 0;
    private int mFinalHeight = 0;
    private int mStartWidth = 0;
    private int mStartXPosition = 0;
    private int mFinalXPosition = 0;


    public MainWeatherTextBehavior(Context context, AttributeSet attrs) {
        this.context = context;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WeatherMainIconBehavior);
            mCustomFinalYPosition = a.getDimension(R.styleable.WeatherMainIconBehavior_finalYPosition, 0);
            mCustomStartXPosition = a.getDimension(R.styleable.WeatherMainIconBehavior_startXPosition, 0);
            mCustomStartToolbarPosition = a.getDimension(R.styleable.WeatherMainIconBehavior_startToolbarPosition, 0);
            mCustomStartHeight = a.getDimension(R.styleable.WeatherMainIconBehavior_startHeight, 0);
            mCustomFinalHeight = a.getDimension(R.styleable.WeatherMainIconBehavior_finalHeight, 0);

            a.recycle();
        }
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RobotoTextView child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RobotoTextView child, View dependency) {
        maybeInitProperties(child, dependency);

        final int maxScrollDistance = (int) (mStartToolbarPosition);
        float expandedPercentageFactor = dependency.getY() / maxScrollDistance;

        child.setX(mFinalXPosition - (mFinalXPosition - mStartXPosition) * expandedPercentageFactor);
        if(child.getId() == R.id.main_city) {
            child.setTextSize(16f + 8f * expandedPercentageFactor);
        }else {
            child.setTextSize(14f + 4f * expandedPercentageFactor);
        }
        if (expandedPercentageFactor < mChangeBehaviorPoint) {
            float heightFactor = (mChangeBehaviorPoint - expandedPercentageFactor) / mChangeBehaviorPoint;
            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                    * (1f - expandedPercentageFactor)) + (child.getHeight()/2);
            child.setY(mStartYPosition - distanceYToSubtract);

            float heightToSubtract = ((mStartHeight - mFinalHeight) * heightFactor);

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.width = mStartWidth;
            lp.height = (int) (mStartHeight - heightToSubtract);
            child.setLayoutParams(lp);
        } else {
            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                    * (1f - expandedPercentageFactor)) + (mStartHeight/2);
            child.setY(mStartYPosition - distanceYToSubtract);

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.width = mStartWidth;
            lp.height = (int) (mStartHeight);
            child.setLayoutParams(lp);
        }
        return true;
    }

    private void maybeInitProperties(RobotoTextView child, View dependency) {
        int devWidth = Global.getDeviceWidth();
        float childX = child.getX();
        float dp16 = context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);

        if (mStartYPosition == 0) {
            if(child.getId() == R.id.main_city) {
                mStartYPosition = Global.dp(40);
            }else{
                mStartYPosition = Global.dp(56);
            }
        }
        if (mFinalYPosition == 0){
            if(child.getId() == R.id.main_city){
                mFinalYPosition = Global.dp(20);
            }else{
                mFinalYPosition = Global.dp(40);
            }
        }

        if (mStartHeight == 0 || mStartHeight < child.getHeight())
            mStartHeight = child.getHeight();

        if(mStartWidth == 0 || mStartWidth < child.getWidth())
            mStartWidth = child.getWidth();

        if(mFinalHeight == 0)
            mFinalHeight = Global.dp(24);

        if (mStartXPosition == 0 || childX < mStartXPosition){
            mStartXPosition = (int) childX;
        }

        if (mFinalXPosition == 0){
            mFinalXPosition = (int) (devWidth - mStartWidth - dp16);
        }

        if (mStartToolbarPosition == 0)
            mStartToolbarPosition = dependency.getY();

        if (mChangeBehaviorPoint == 0) {
            mChangeBehaviorPoint = (child.getHeight() - mCustomFinalHeight) / (2f * (mStartYPosition - mFinalYPosition));
        }
    }
}
