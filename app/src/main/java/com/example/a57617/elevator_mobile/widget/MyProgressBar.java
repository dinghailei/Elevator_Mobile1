package com.example.a57617.elevator_mobile.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.a57617.elevator_mobile.utils.DpUtil;

public class MyProgressBar extends View {
    private int radius = DpUtil.dip2px(getContext(), 5);//这里这个圆直径一定要比进度高度要
    private int progressHigher = DpUtil.dip2px(getContext(), 5);
    private int strokeWidth = radius / 2;//边线宽度
    private Paint backgroundPaint, progressPaint, linePaint;//背景和进度条画笔;
    private Paint circularBgPaint;
    private Paint circularSelectionPaint;
    private Paint circularNoSelectionPaint;
    private RectF backgroundRect = new RectF();//进度条;
    private RectF progressRects[];//背景矩形区域
    private RectF circularBgRects[];//圆球位置
    private float weights[];//每个区域的权重
    private int colors[];//颜色
    private float totalWeight;//总的权重
    public static final int DEF_COLORS[];//进度条颜色值
    public static final int BG_COLORS;//背景颜色值
    public static final float DEF_WEIGHTS[];//每段对应的权重
    private float progress = 0, maxProgress = 100;//进度和最大进度
    private OnProgressChangeListener listener;

    static {
        BG_COLORS = Color.parseColor("#eeeeee");//背景颜色值
        DEF_COLORS = new int[]{//进度条颜色值
                Color.parseColor("#ff0000"),
                Color.parseColor("#00ff00"),
                Color.parseColor("#0000ff")
        };
        DEF_WEIGHTS = new float[]{//进度条颜色值 显示占比
                1, 1, 1
        };

    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        if (progress >= maxProgress) {
            this.progress = maxProgress;
        } else {
            this.progress = progress;
        }
        invalidate();
        onProgressChange();
    }

    private void onProgressChange() {
        if (listener != null) {
            int position = 0;
            int currentWidth = (int) getWidthForWeight(getProgress(), getMaxProgress());
            int tmpWidth = 0;
            for (int i = 0; i < weights.length; i++) {
                tmpWidth += (int) getWidthForWeight(weights[i], totalWeight);
                if (tmpWidth >= currentWidth) {
                    position = i;
                    break;
                }
            }
            listener.onProgressChange(getProgress(), position);
        }
    }

    public float getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

    public OnProgressChangeListener getProgressChangeListener() {
        return listener;
    }

    public void setProgressChangeListener(OnProgressChangeListener listener) {
        this.listener = listener;
    }

    public MyProgressBar(Context context) {
        super(context);
        init();
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init() {
        backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        backgroundPaint.setColor(BG_COLORS);
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        progressPaint.setColor(Color.parseColor("#d9d9d9"));
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setColor(Color.parseColor("#e7eaf0"));
        linePaint.setStrokeWidth(2);

        circularBgPaint = new Paint();
        circularBgPaint.setAntiAlias(true);
        circularBgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        circularBgPaint.setColor(Color.parseColor("#ffffff"));

        circularSelectionPaint = new Paint();
        circularSelectionPaint.setAntiAlias(true);
        circularSelectionPaint.setStyle(Paint.Style.STROKE);
        circularSelectionPaint.setStrokeWidth(strokeWidth);

        circularNoSelectionPaint = new Paint();
        circularNoSelectionPaint.setAntiAlias(true);
        circularNoSelectionPaint.setStyle(Paint.Style.STROKE);
        circularNoSelectionPaint.setColor(BG_COLORS);
        circularNoSelectionPaint.setStrokeWidth(strokeWidth);
        setColors(DEF_WEIGHTS);
        this.colors = DEF_COLORS;
        circularBgRects = new RectF[DEF_WEIGHTS.length + 1];


    }


    /**
     * 设置每一段的颜色以及对应的权重
     *
     * @param weights
     */
    public void setColors(float weights[]) {
        if (weights == null) {
            throw new NullPointerException("colors And weights must be not null");
        }
        progressRects = new RectF[weights.length];
        this.weights = weights;
        totalWeight = 0;
        for (int i = 0; i < weights.length; i++) {
            totalWeight += weights[i];
            progressRects[i] = new RectF();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (progressRects == null) {
            return;
        }
        if (maxProgress <= 0) {
            maxProgress = getWidth() - 2 * (strokeWidth + radius);
        }

        for (int i = 0; i < circularBgRects.length; i++) {
            circularBgRects[i] = new RectF();
            if (i == 0) {
                circularBgRects[i].set(strokeWidth, strokeWidth, strokeWidth + 2 * radius, strokeWidth + 2 * radius);
            } else {
                circularBgRects[i].set((int) getWidthForWeight((maxProgress / (circularBgRects.length - 1)) * i, maxProgress) + strokeWidth, strokeWidth, strokeWidth + 2 * radius + (int) getWidthForWeight((maxProgress / (circularBgRects.length - 1)) * i, maxProgress), strokeWidth + 2 * radius);
            }

        }


        //绘制背景颜色块
        backgroundRect.set((strokeWidth + radius), (strokeWidth + radius) - (progressHigher / 2), (int) getWidthForWeight(maxProgress, maxProgress) + (strokeWidth + radius), (strokeWidth + radius) + (progressHigher / 2));
        canvas.drawRoundRect(backgroundRect, progressHigher / 2, progressHigher / 2, backgroundPaint);//绘制矩形
        // 绘制进度条
        int progressX = (int) getWidthForWeight(progress, maxProgress) + (strokeWidth + radius);
        RectF rect = new RectF();

//        获取应当显示的颜色
        for (int i = 0; i < colors.length; i++) {
            if ((maxProgress / colors.length) * i <= progress) {
                progressPaint.setColor(colors[i]);
                circularSelectionPaint.setColor(colors[i]);
            } else {
                break;
            }
        }
        rect.set((strokeWidth + radius), (strokeWidth + radius) - (progressHigher / 2), progressX, (strokeWidth + radius) + (progressHigher / 2));
        canvas.drawRoundRect(rect, progressHigher / 2, progressHigher / 2, progressPaint);


//        先画一圈白底
        for (int i = 0; i < circularBgRects.length; i++) {
            canvas.drawArc(circularBgRects[i], 0, 360, true, circularBgPaint);
        }

        for (int i = 0; i < circularBgRects.length; i++) {
            if (progress >= i * (maxProgress / (circularBgRects.length - 1))) {
                canvas.drawArc(circularBgRects[i], 0, 360, true, circularSelectionPaint);
            } else {
                canvas.drawArc(circularBgRects[i], 0, 360, true, circularNoSelectionPaint);
            }

        }

    }

    /**
     * 根据权重获或进度取对应的宽度
     *
     * @param weight
     * @param totalWeight
     * @return
     */
    public float getWidthForWeight(float weight, float totalWeight) {
        return (getWidth() - (2 * (radius + strokeWidth))) * (weight / totalWeight) + 0.5f;
    }

    /**
     * 根据根据权重在数组中的索引获取对应的位置
     *
     * @param position
     * @return
     */
    public float getXForWeightPosition(int position) {
        float xPosition = 0;
        for (int i = 0; i < position; i++) {
            xPosition += getWidthForWeightPosition(i);
        }
        return xPosition;
    }

    /**
     * 根据根据权重在数组中的索引获取对应的宽度
     *
     * @param position
     * @return
     */
    public float getWidthForWeightPosition(int position) {
        return (getWidth() - (2 * (strokeWidth + radius))) * (weights[position] / totalWeight) + 0.5f;
    }

    ObjectAnimator valueAnimator;

    public void autoChange(float startProgress, float endProgress, long changeTime) {
        if (valueAnimator != null && valueAnimator.isRunning()) return;
        setProgress((int) startProgress);
        setMaxProgress((int) endProgress);
        valueAnimator = ObjectAnimator.ofFloat(this, "progress", startProgress, endProgress);
        valueAnimator.setDuration(changeTime);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
//                setProgress((int) value);
                Log.d(getClass().getName(), "进度值 " + value);
            }
        });
        valueAnimator.start();
    }

    public void stopChange() {
        if (valueAnimator != null && valueAnimator.isRunning()) valueAnimator.cancel();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
        super.onDetachedFromWindow();
    }

    public interface OnProgressChangeListener {
        /**
         * 进度改变时触发
         *
         * @param progress 进度
         * @param position 所在区间段
         */
        void onProgressChange(float progress, int position);
    }
}
