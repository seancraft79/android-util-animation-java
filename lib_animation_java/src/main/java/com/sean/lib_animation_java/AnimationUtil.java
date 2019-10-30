package com.sean.lib_animation_java;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;

public class AnimationUtil {
    public void colorAnimation(String fromColor, String toColor, float valueFrom, float valueTo, long animDuration, final View animView) {
        final float[] from = new float[3],
                to =   new float[3];

        if(isNullOrEmpty(fromColor)) fromColor = "#FFFFFFFF";
        if(isNullOrEmpty(toColor)) toColor = "#FFFF0000";

        Color.colorToHSV(Color.parseColor(fromColor), from);   // from color of
        Color.colorToHSV(Color.parseColor(toColor), to);     // to color

        ValueAnimator anim = ValueAnimator.ofFloat(valueFrom, valueTo);   // animate from value to value
        anim.setDuration(animDuration);

        //final float[] hsv  = new float[3];                  // transition color
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override public void onAnimationUpdate(ValueAnimator animation) {

                float currentValue = (float) animation.getAnimatedValue();
                int currentAlpha = (int)(currentValue * 100);
                if(currentAlpha > 255) currentAlpha = 255;
                else if(currentAlpha < 0) currentAlpha = 0;

                int color = Color.argb(currentAlpha, 255, 0, 0);

                animView.setBackgroundColor(color);

                /*
                // Transition along each axis of HSV (hue, saturation, value)
                hsv[0] = from[0] + (to[0] - from[0]) * currentValue;
                hsv[1] = from[1] + (to[1] - from[1]) * currentValue;
                hsv[2] = from[2] + (to[2] - from[2]) * currentValue;
                */
            }
        });

        anim.start();
    }

    public void valueAnimation(float valueFrom, float valueTo, long animDuration, ValueAnimator.AnimatorUpdateListener valueAnimator) {

        ValueAnimator anim = ValueAnimator.ofFloat(valueFrom, valueTo);   // animate from value to value
        anim.setDuration(animDuration);

        anim.addUpdateListener(valueAnimator);

        anim.start();
    }

    public static void scaleAnimation(final View target, float pivotX, float pivotY, float fromScale, float toScale, float endScale, long duration, Animator.AnimatorListener listener) {
        if(target == null) {
            throw new RuntimeException("Animation target is null");
        }

        target.setPivotX(pivotX);
        target.setPivotY(pivotY);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, "scaleX", fromScale, toScale, endScale);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, "scaleY", fromScale, toScale, endScale);

        AnimatorSet as = new AnimatorSet();

        as.setDuration(duration);

        as.addListener(listener);

        as.playTogether(scaleX, scaleY);
        as.start();
    }

    /**
     * Only Vertical Movement : Animation that move Y axis only from position by amount.
     * View 의 Y 좌표가 아래로 갈수록 감소한다고 설정.
     * @param target : target for this animation
     * @param from : position of from where in screen
     * @param duration
     * @param cb
     */
    public static void moveFromY(final View target, float from, long startDelay, long duration, final Animator.AnimatorListener cb) {
        if(target == null) {
            throw new RuntimeException("Animation target is null");
        }

        target.setY(target.getY() + (from*-1));

        ObjectAnimator animation = ObjectAnimator.ofFloat(target, "translationY", 0);
        animation.setDuration(duration);
        animation.setStartDelay(startDelay);
        animation.addListener(cb);

        animation.start();
    }

    // Only Horizontal Movement
    public static void moveFromX(final View target, float from, long startDelay, long duration, final Animator.AnimatorListener cb) {
        if(target == null) {
            throw new RuntimeException("Animation target is null");
        }

        target.setX(target.getX() + (from*-1));

        ObjectAnimator animation = ObjectAnimator.ofFloat(target, "translationX", 0);
        animation.setDuration(duration);
        animation.setStartDelay(startDelay);
        animation.addListener(cb);

        animation.start();
    }

    public static void moveToY(final View target, float moveTo, long startDelay, long duration, final Animator.AnimatorListener cb) {
        if(target == null) {
            throw new RuntimeException("Animation target is null");
        }

        ObjectAnimator animation = ObjectAnimator.ofFloat(target, "translationY", moveTo);
        animation.setDuration(duration);
        animation.setStartDelay(startDelay);
        animation.addListener(cb);

        animation.start();
    }

    public static void moveToX(final View target, float moveTo, long startDelay, long duration, final Animator.AnimatorListener cb) {
        if(target == null) {
            throw new RuntimeException("Animation target is null");
        }

        ObjectAnimator animation = ObjectAnimator.ofFloat(target, "translationX", moveTo);
        animation.setDuration(duration);
        animation.setStartDelay(startDelay);
        animation.addListener(cb);

        animation.start();
    }

    public static boolean isNullOrEmpty(String str){

        if(str == null || str.isEmpty() || str.length() < 1)
            return true;

        str = removeSpace(str);
        if(str.isEmpty() || str.length() < 1)
            return true;

        return false;
    }

    public static String removeSpace(String s) {

        String withoutspaces = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ')
                withoutspaces += s.charAt(i);

        }
        return withoutspaces;
    }
}
