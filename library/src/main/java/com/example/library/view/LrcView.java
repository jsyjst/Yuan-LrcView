package com.example.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

import com.example.library.R;
import com.example.library.entity.LrcBean;
import com.example.library.utils.LrcUtil;

import java.util.List;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2019/09/29
 *     desc   : 自定义歌词View
 * </pre>
 */

public class LrcView extends View {
    private List<LrcBean> lrcBeanList;//歌词集合
    private Paint dPaint;//歌词画笔
    private Paint hPaint;//当前歌词画笔
    private int lrcTextColor;//歌词颜色
    private int highLineTextColor;//当前歌词颜色
    private int width, height;//屏幕宽高
    private int lineSpacing;//行间距
    private int textSize;//字体大小
    private int currentPosition;//当前歌词的位置
    private MediaPlayer player;//当前的播放器
    private int lastPosition = 0;//上一句歌词的位置
//    private Scroller scroller;


    //将歌词集合传给到这个自定义View中
    public LrcView setLrc(String lrc) {
        lrcBeanList = LrcUtil.parseStr2List(lrc);
        return this;
    }

    //传递mediaPlayer给自定义View中
    public LrcView setPlayer(MediaPlayer player) {
        this.player = player;
        return this;
    }


    //当setLrcBeanList被调用时需要重新绘制
    public LrcView draw() {
        currentPosition = 0;
        lastPosition = 0;
        invalidate();
        return this;
    }

    public LrcView(Context context) {
        this(context, null);
    }

    public LrcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LrcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        scroller = new Scroller(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LrcView);
        lrcTextColor = ta.getColor(R.styleable.LrcView_lrcTextColor, Color.GRAY);
        highLineTextColor = ta.getColor(R.styleable.LrcView_highLineTextColor, Color.BLUE);
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        float scale = context.getResources().getDisplayMetrics().density;
        //默认字体大小为16sp
        textSize = ta.getDimensionPixelSize(R.styleable.LrcView_textSize, (int) (16 * fontScale));
        //默认行间距为30dp
        lineSpacing = ta.getDimensionPixelSize(R.styleable.LrcView_lineSpacing, (int) (30 * scale));
        //回收
        ta.recycle();
        //进行初始化
        init();
    }

    private void init() {
        //初始化歌词画笔
        dPaint = new Paint();
        dPaint.setStyle(Paint.Style.FILL);//填满
        dPaint.setAntiAlias(true);//抗锯齿
        dPaint.setColor(lrcTextColor);//画笔颜色
        dPaint.setTextSize(textSize);//歌词大小
        dPaint.setTextAlign(Paint.Align.CENTER);//文字居中

        //初始化当前歌词画笔
        hPaint = new Paint();
        hPaint.setStyle(Paint.Style.FILL);
        hPaint.setAntiAlias(true);
        hPaint.setColor(highLineTextColor);
        hPaint.setTextSize(textSize);
        hPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        getMeasuredWidthAndHeight();//得到测量后的宽高
        getCurrentPosition();//得到当前歌词的位置
        drawLrc(canvas);//画歌词
        scrollLrc();//歌词滑动
        postInvalidateDelayed(100);//延迟0.1s刷新
    }

    private void getMeasuredWidthAndHeight(){
        if (width == 0 || height == 0) {
            width = getMeasuredWidth();
            height = getMeasuredHeight();
        }
    }

    //得到当前歌词的位置
    private void getCurrentPosition() {
        int curTime = player.getCurrentPosition();
        //如果当前的时间大于10分钟，证明歌曲未播放，则当前位置应该为0
        if (curTime < lrcBeanList.get(0).getStart()||curTime>10*60*1000) {
            currentPosition = 0;
            return;
        } else if (curTime > lrcBeanList.get(lrcBeanList.size() - 1).getStart()) {
            currentPosition = lrcBeanList.size() - 1;
            return;
        }
        for (int i = 0; i < lrcBeanList.size(); i++) {
            if (curTime >= lrcBeanList.get(i).getStart() && curTime <= lrcBeanList.get(i).getEnd()) {
                currentPosition = i;
            }
        }
    }

    //画歌词，第一句从正中央开始，以后的歌词递增行间距开始画
    private void drawLrc(Canvas canvas) {
        for (int i = 0; i < lrcBeanList.size(); i++) {
            if (currentPosition == i) {//如果是当前的歌词就用高亮的画笔画
                canvas.drawText(lrcBeanList.get(i).getLrc(), width / 2, height / 2 + i * lineSpacing, hPaint);
            } else {
                canvas.drawText(lrcBeanList.get(i).getLrc(), width / 2, height / 2 + i * lineSpacing, dPaint);
            }
        }
    }

    //歌词滑动
    private void scrollLrc() {
        //下一句歌词的开始时间
        long startTime = lrcBeanList.get(currentPosition).getStart();
        long currentTime = player.getCurrentPosition();

       // 判断是否换行,在0.5内完成滑动，即实现弹性滑动
        float y = (currentTime - startTime) > 500
                ? currentPosition * lineSpacing
                : lastPosition * lineSpacing + (currentPosition - lastPosition) * lineSpacing * ((currentTime - startTime) / 500f);
        scrollTo(0,(int)y);
        if (getScrollY() == currentPosition * lineSpacing) {
            lastPosition = currentPosition;
        }
        //Scroller实现弹性滑动
//        if(currentTime - startTime<=400){
//            smoothScrollTo(currentPosition*lineSpacing);
//        }
    }

//    private void smoothScrollTo(int y){
//        int scrollY = getScrollY();
//        int delta = y-scrollY;
//        scroller.startScroll(0,scrollY,0,delta,400);
//        invalidate();
//    }
//
//    @Override
//    public void computeScroll() {
//        if(scroller.computeScrollOffset()){
//            scrollTo(scroller.getCurrX(),scroller.getCurrY());
//            postInvalidateDelayed(100);//延迟0.1s刷新
//        }
//    }
}
