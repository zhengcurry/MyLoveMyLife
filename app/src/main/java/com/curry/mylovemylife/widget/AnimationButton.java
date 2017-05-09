package com.curry.mylovemylife.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by allen on 2017/4/26.
 * 带动画效果的按钮
 */

public class AnimationButton extends View {

    /**
     * view的宽度
     */
    private int width;
    /**
     * view的高度
     */
    private int height;
    /**
     * 圆角半径
     */
    private int circleAngle;
    /**
     * 默认两圆圆心之间的距离=需要移动的距离
     */
    private int default_two_circle_distance;
    /**
     * 两圆圆心之间的距离
     */
    private int two_circle_distance;
    /**
     * 背景颜色
     */
    private int bg_color = 0xffbc7d53;
    /**
     * 动画执行时间
     */
    private int duration = 1000;

    /**
     * 圆角矩形画笔
     */
    private Paint paint;

    /**
     * 动画集
     */
    private AnimatorSet animatorSet = new AnimatorSet();

    /**
     * 矩形到圆角矩形过度的动画
     */
    private ValueAnimator animator_rect_to_angle;

    /**
     * 根据view的大小设置成矩形
     */
    private RectF rectf = new RectF();

    /**
     * 路径--用来获取对勾的路径
     */
    private Path path = new Path();

    /**
     * 点击事件及动画事件2完成回调
     */
    private AnimationButtonListener animationButtonListener;

    public void setAnimationButtonListener(AnimationButtonListener listener) {
        animationButtonListener = listener;
    }

    public AnimationButton(Context context) {
        this(context, null);
    }

    public AnimationButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationButtonListener != null) {
                    animationButtonListener.onClickListener();
                }
            }
        });

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationButtonListener != null) {
                    animationButtonListener.animationFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 初始化所有动画
     */
    private void initAnimation() {
        set_rect_to_angle_animation();

        animatorSet.play(animator_rect_to_angle);
    }


    /**
     * 设置矩形过度到圆角矩形的动画
     */
    private void set_rect_to_angle_animation() {
        animator_rect_to_angle = ValueAnimator.ofInt(0, height / 2);//
        animator_rect_to_angle.setDuration(duration);
        animator_rect_to_angle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    private void initPaint() {

        paint = new Paint();
        paint.setStrokeWidth(10);//设置线宽
        paint.setStyle(Paint.Style.FILL);//设置样式,FILL较为圆滑
        paint.setAntiAlias(true);
        paint.setColor(bg_color);

    }

    /**
     * 1.先经过这里
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        default_two_circle_distance = (w - h) / 2;

        initAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

         draw_oval_to_circle(canvas);
    }

    /**
     * 绘制长方形变成圆形
     *
     * @param canvas 画布
     */
    private void draw_oval_to_circle(Canvas canvas) {

        rectf.left = two_circle_distance;
        rectf.top = 0;
        rectf.right = width - two_circle_distance;
        rectf.bottom = height;

        //画圆角矩形
        canvas.drawRoundRect(rectf, circleAngle, circleAngle, paint);

    }

    /**
     * 启动动画
     */
    public void start() {
        animatorSet.start();
    }

    /**
     * 借口回调
     */
    public interface AnimationButtonListener {
        /**
         * 按钮点击事件
         */
        void onClickListener();

        /**
         * 动画完成回调
         */
        void animationFinish();
    }
}
