package com.ashini.everyday.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.ashini.everyday.R;

public class CycleProcessBar extends View {

    private float currentProcess = 0f;
    private float maxProcess = 100f;
    private float speed = 0.034f;

    private boolean isRunning = false;

    private Paint paint;
    private Paint textPaint;

    private float thickness;
    private float textSize;
    private int cycleColor;
    private int processColor;
    private int textColor;


    private float cx;
    private float cy;
    private float radius;
    private RectF rectF;//用于确定圆弧的矩形

    private ValueAnimator valueAnimator;

    public CycleProcessBar(Context context) {
        super(context);
        init(context, null);
    }

    public CycleProcessBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CycleProcessBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }//必须有这三个构造方法

    private void init(Context context, AttributeSet attrs) {
        //TypedArray typedArray = context.obtainStyledAttributes( R.styleable.CycleProcessBar);//就可以了
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CycleProcessBar);
        thickness = typedArray.getDimension(R.styleable.CycleProcessBar_thickness, 16);
        textSize = typedArray.getDimension(R.styleable.CycleProcessBar_textSize, 100);
        cycleColor = typedArray.getColor(R.styleable.CycleProcessBar_cycleColor, context.getResources().getColor(R.color.colorPrimary));
        processColor = typedArray.getColor(R.styleable.CycleProcessBar_processColor, context.getResources().getColor(R.color.colorPrimaryDark));
        textColor = typedArray.getColor(R.styleable.CycleProcessBar_textColor, context.getResources().getColor(R.color.colorAccent));
        typedArray.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿画笔
        paint.setStrokeCap(Paint.Cap.ROUND);//绘制的图形两边会有圆角效果
        paint.setStyle(Paint.Style.STROKE);//绘制图形的边界
        paint.setStrokeWidth(thickness);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextAlign(Paint.Align.CENTER);  // 设置文字位置在中间
        textPaint.setTextSize(textSize);  // 设置文字大小
        textPaint.setAntiAlias(true);
        initAnimator();
    }

    private void initAnimator() {
        valueAnimator = ValueAnimator.ofFloat(currentProcess, maxProcess);
        valueAnimator.setDuration((long) ((maxProcess - currentProcess) / speed));
        valueAnimator.setStartDelay(200);
        valueAnimator.setInterpolator(new LinearInterpolator());//线性插补器
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentProcess = (float) animation.getAnimatedValue();
                invalidate();//触发 onDraw()；
            }
        });

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        cx = w * 0.5f;
        cy = h * 0.5f;
        radius = Math.min(cx, cy) - thickness;
        rectF = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(cycleColor);
        canvas.drawCircle(cx, cy, radius, paint);
        paint.setColor(processColor);
        canvas.drawArc(rectF, 90, currentProcess * 0.01f * 360, false, paint);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int baseline = (int) ((rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        canvas.drawText((int) currentProcess + "%", rectF.centerX(), baseline, textPaint);
    }

    public void start() {
        if (!isRunning) {
            initAnimator();
            valueAnimator.start();
            isRunning = true;
        }
    }

    public void stop() {
        if (isRunning) {
            valueAnimator.cancel();
            isRunning = false;
        }
    }

    public void end() {
        valueAnimator.end();
        isRunning = false;
    }

    public float getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(float currentProcess) {
        this.currentProcess = currentProcess;
    }

    public float getMaxProcess() {
        return maxProcess;
    }

    public void setMaxProcess(float maxProcess) {
        this.maxProcess = maxProcess;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getProcessColor() {
        return processColor;
    }

    public void setProcessColor(int processColor) {
        this.processColor = processColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getThickness() {
        return thickness;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    public int getCycleColor() {
        return cycleColor;
    }

    public void setCycleColor(int cycleColor) {
        this.cycleColor = cycleColor;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
