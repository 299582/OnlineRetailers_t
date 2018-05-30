package com.example.mathtest.presenter;

import android.animation.ObjectAnimator;
import android.widget.TextView;

import static android.animation.ValueAnimator.REVERSE;

public class AnimationPresenter implements AnimationPresenterInterface {



    public void setAnimation(TextView textView){
        if(animationinterface != null){

            ObjectAnimator translationX = ObjectAnimator.ofFloat(textView, "translationX", new float[]{-360f,0f});
            translationX.setDuration(2000);
            translationX.setRepeatCount(0);
            translationX.setRepeatMode(REVERSE);
            translationX.start();
        }

    }


    private final AnimationPresenterInterface animationinterface;

    public AnimationPresenter (AnimationPresenterInterface animationinterface){
        this.animationinterface = animationinterface;
    }




}
