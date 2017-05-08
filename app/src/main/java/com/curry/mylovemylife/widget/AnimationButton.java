package com.curry.mylovemylife.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by curry on 2017/5/8.
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
     * 两圆圆心之间的距离
     */
    private int two_circle_distance;
    /**
     * 圆角半径
     */
    private int circleAngle;
    /**
     * 圆角矩形画笔
     */
    private Paint paint;
    /**
     * 背景颜色
     */
    private int bg_color = 0xffbc7d53;
    /**
     * 动画集
     */
    private AnimatorSet animatorSet = new AnimatorSet();
    /**
     * 点击事件及动画事件2完成回调
     */
    private AnimationButtonListener animationButtonListener;
    /**
     * 矩形到圆角矩形过度的动画
     */
    private ValueAnimator animator_rect_to_angle;
    /**
     * 动画执行时间
     */
    private int duration = 1000;
    /**
     * 矩形到正方形过度的动画
     */
    private ValueAnimator animator_rect_to_square;
    /**
     * 默认两圆圆心之间的距离=需要移动的距离
     */
    private int default_two_circle_distance;
    public void setAnimationButtonListener(AnimationButtonListener listener) {
        animationButtonListener = listener;
    }

    private RectF mRectF = new RectF();

    public AnimationButton(Context context) {
        this(context, null);
    }

    public AnimationButton(Context context, AttributeSet attrs) {
        this(context, null, 0);
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawRoundedRectangle(canvas);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
        default_two_circle_distance = (w - h) / 2;
        initAnimation();
    }

    /**
     * 设置矩形过度到圆角矩形的动画
     */
    private void set_rect_to_angle_animation() {
        animator_rect_to_angle = ValueAnimator.ofInt(0, height / 2);
        animator_rect_to_angle.setDuration(duration);
        animator_rect_to_angle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    /**
     * 初始化所有动画
     */
    private void initAnimation() {
        set_rect_to_angle_animation();
        set_rect_to_circle_animation();
        animatorSet.play(animator_rect_to_angle);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(bg_color);
    }

    private void drawRoundedRectangle(Canvas canvas) {
        mRectF.left = two_circle_distance;
        mRectF.top = 0;
        mRectF.right = width - two_circle_distance;
        mRectF.bottom = height;

        //画圆角矩形
        canvas.drawRoundRect(mRectF, circleAngle, circleAngle, paint);
    }
    /**
     * 设置圆角矩形过度到圆的动画
     */
    private void set_rect_to_circle_animation() {
        animator_rect_to_square = ValueAnimator.ofInt(0, default_two_circle_distance);
        animator_rect_to_square.setDuration(duration);
        animator_rect_to_square.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                two_circle_distance = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
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
