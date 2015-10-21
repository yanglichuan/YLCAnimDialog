package com.example.administrator.myanimdialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

public class MyAnimDialog extends Dialog {
    private Context context;

    public MyAnimDialog(Context context) {
        super(context);
        this.context = context;
    }

    private int getpx(int idp) {
        return (int) (context.getResources().getDisplayMetrics().density * idp + 0.5f);
    }

    private View rootview = null;
    private View childview = null;
    private Handler handler = new Handler();

    public MyAnimDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rootview = View.inflate(context, R.layout.item_dialog, null);
        childview = rootview.findViewById(R.id.content);

        setContentView(rootview, params);
        // 这句话起全屏的作用
        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                ObjectAnimator bg =  ObjectAnimator.ofArgb(
//                        rootview, "backgroundColor", Color.parseColor("#00000000"), Color.parseColor("#7f000000"));
//                bg.setTarget(rootview);
//                bg.setDuration(500);
//                bg.setInterpolator(new LinearInterpolator());

                ValueAnimator bg = ValueAnimator.ofInt(0, 127);
                bg.setTarget(rootview);
                bg.setDuration(500);
                bg.setInterpolator(new LinearInterpolator());
                bg.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        rootview.setBackgroundColor(Color.argb((Integer) animation.getAnimatedValue(), 0, 0, 0));
                    }
                });


                ObjectAnimator o = ObjectAnimator.ofFloat(childview, "alpha", 0, 1);
                o.setTarget(childview);
                o.setDuration(500);
                o.setInterpolator(new OvershootInterpolator(3));


                //开始动画
                ObjectAnimator obj = ObjectAnimator.ofFloat(childview, "translationY", rootview.getMeasuredHeight(), 0);
                obj.setTarget(childview);
                obj.setDuration(500);
                obj.setInterpolator(new OvershootInterpolator(2));
                obj.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        childview.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                AnimatorSet set = new AnimatorSet();
                set.playTogether(o, obj, bg);
                set.start();
            }
        }, 100);
    }

    @Override
    public void dismiss() {
//        ObjectAnimator bg =  ObjectAnimator.ofFloat(rootview, "alpha", 0.5f,0);
//        bg.setTarget(rootview);
//        bg.setDuration(500);
//        bg.setInterpolator(new LinearInterpolator());
        ValueAnimator bg = ValueAnimator.ofInt(127, 0);
        bg.setTarget(rootview);
        bg.setDuration(500);
        bg.setInterpolator(new LinearInterpolator());
        bg.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rootview.setBackgroundColor(Color.argb((Integer) animation.getAnimatedValue(), 0, 0, 0));
            }
        });

        ObjectAnimator o = ObjectAnimator.ofFloat(childview, "alpha", 1, 0);
        o.setTarget(childview);
        o.setDuration(500);
        o.setInterpolator(new OvershootInterpolator(3));

        //开始动画
        ObjectAnimator obj = ObjectAnimator.ofFloat(childview, "translationY", 0, -rootview.getMeasuredHeight());
        obj.setTarget(childview);
        obj.setDuration(500);
        obj.setInterpolator(new OvershootInterpolator(2));
        obj.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                childview.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                MyAnimDialog.super.dismiss();
                ;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(o, obj, bg);
        set.start();
    }

    protected MyAnimDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

}
