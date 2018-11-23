package com.qicaixiong.reader.book.v;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.qicaixiong.reader.R;
import com.qicaixiong.reader.book.c.PageTurnViewAdapter;
import com.yyx.commonsdk.log.MyLog;
import com.yyx.commonsdk.sound.SoundPlayManager;

import android.os.Handler;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * 左划 翻下一页
 * Created by admin on 2018/11/2.
 */

public class PageTurnView extends FrameLayout {
    private PointF downPoint;
    private PointF lastPoint;
    private boolean isTurning;
    private String direction;
    private int mCurrentPage;
    private int translationX;
    private Rect mTopViewRect = new Rect(), mBottomViewRect = new Rect(), shadowRect = new Rect(), backOfPageRect = new Rect(), backShadowRect = new Rect();
    private Paint mPaint = new Paint();
    private Handler mHandler = new Handler();
    private int shadowWidth = 200;

    private PageTurnViewAdapter adapter;
    private long toaskTime;
    private View preView, currentView, nextView, topView, bottomView;

    public PageTurnView(Context context) {
        super(context);
        init();
    }

    public PageTurnView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageTurnView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (downPoint == null)
                    downPoint = new PointF();
                downPoint.x = event.getX();
                downPoint.y = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                if (lastPoint == null)
                    lastPoint = new PointF();
                lastPoint.x = event.getX();
                lastPoint.y = event.getY();
                if (!isTurning && downPoint != null) {
                    direction = isStartTurning(downPoint, lastPoint);
                    if (!TextUtils.isEmpty(direction)) {
                        downPoint = null;
                        startTurnAnimation();
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (isTurning && !TextUtils.isEmpty(direction) && getChildCount() >= 2) {

            int height = getMeasuredHeight();
            int width = getMeasuredWidth();

            // The rect that represents the backofthepage
            backOfPageRect.set(translationX - shadowWidth, 0, translationX, height);

            // The along the crease of the turning page
            shadowRect.set(backOfPageRect.left - 100, 0, backOfPageRect.left, height);
            // The shadow cast onto the next page by teh turning page
            backShadowRect.set(backOfPageRect.right, 0, backOfPageRect.right + 100, height);

            // set the top view to be the current page to the crease of the
            // turning page
            mTopViewRect.set(0, 0, translationX, getMeasuredHeight());
            mBottomViewRect.set(translationX, 0, width, height);

            canvas.save();
            // clip and draw the top page to your touch
            canvas.clipRect(mTopViewRect);
            topView.draw(canvas);
            canvas.restore();

            // clip and draw the first shadow
            canvas.save();
            canvas.clipRect(shadowRect);
            mPaint.setShader(new LinearGradient(shadowRect.left, shadowRect.top, shadowRect.right, shadowRect.top, 0x00000000, 0x44000000,
                    Shader.TileMode.REPEAT));
            canvas.drawPaint(mPaint);
            canvas.restore();

            mPaint.setShader(null);

//            // clip and draw the gradient that makes the page look bent
            canvas.save();
            canvas.clipRect(backOfPageRect);
            mPaint.setShadowLayer(0, 0, 0, 0x00000000);
            mPaint.setShader(new LinearGradient(backOfPageRect.left, backOfPageRect.top, backOfPageRect.right, backOfPageRect.top, new int[]{0xFFEEEEEE,
                    0xFFDDDDDD, 0xFFEEEEEE, 0xFFD6D6D6}, new float[]{.35f, .73f, 9f, 1.0f}, Shader.TileMode.REPEAT));
            canvas.drawPaint(mPaint);
            canvas.restore();

            // draw the second page in the remaining space
            canvas.save();
            canvas.clipRect(mBottomViewRect);
            bottomView.draw(canvas);
            canvas.restore();

//            // now draw a shadow
            if (backShadowRect.left > 0) {
                canvas.save();
                canvas.clipRect(backShadowRect);
                mPaint.setShader(new LinearGradient(backShadowRect.left, backShadowRect.top, backShadowRect.right, backShadowRect.top
                        , 0x44000000, 0x00000000,
                        Shader.TileMode.REPEAT));
                canvas.drawPaint(mPaint);
//				// canvas.drawColor(0xFF000000);
                canvas.restore();
            }
        } else {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                int position = (int) childAt.getTag(R.id.viewName);
                if (position == mCurrentPage)
                    childAt.draw(canvas);
            }
        }
    }

    //设置适配器
    public void setAdapter(PageTurnViewAdapter adapter) {
        this.adapter = adapter;
        this.adapter.turnPage(getId(),mCurrentPage);
    }

    //执行一段动画 实现翻页
    private void startTurnAnimation() {
        if (TextUtils.isEmpty(direction) || adapter == null)
            return;
        mCurrentPage = adapter.getPage(mCurrentPage);
        int next = adapter.getPage(mCurrentPage + 1);
        int previous = adapter.getPage(mCurrentPage - 1);

        switch (direction) {
            case "left":
                if (mCurrentPage == (adapter.getCount() - 1)) {
                    long now = System.currentTimeMillis() - 3000;
                    if (now > toaskTime) {
                        Toast.makeText(getContext(), "最后一页", Toast.LENGTH_SHORT).show();
                        toaskTime = now;
                    }
                    return;
                }
                getChildView(mCurrentPage, next);
                translationX = getMeasuredWidth();
                mCurrentPage = next;
                break;
            case "right":
                if (mCurrentPage == 0) {
                    long now = System.currentTimeMillis() - 3000;
                    if (now > toaskTime) {
                        Toast.makeText(getContext(), "第一页", Toast.LENGTH_SHORT).show();
                        toaskTime = now;
                    }
                    return;
                }
                getChildView(previous, mCurrentPage);
                translationX = 0;
                mCurrentPage = previous;
                break;
        }
        if (topView == null || bottomView == null)
            return;
        isTurning = true;
        SoundPlayManager.getInstance().play(getContext(),"pageTurn.mp3","pageTurn.mp3",1);
        mHandler.post(createTurningAction(direction));
    }

    //找出翻页时候的 2个view展示出来 其他的隐藏
    private void getChildView(int topPosition, int bottomPosition) {
        for (int i = 0; i < getChildCount(); i++) {
            int position = (int) getChildAt(i).getTag(R.id.viewName);
            if (position == topPosition) {
                topView = getChildAt(i);
                topView.setVisibility(View.VISIBLE);
            } else if (position == bottomPosition) {
                bottomView = getChildAt(i);
                bottomView.setVisibility(View.VISIBLE);
            } else {
                getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    //翻页时，循环执行函数
    private Runnable createTurningAction(final String direction) {
        return new Runnable() {
            @Override
            public void run() {
                boolean turning = false;
                if (direction.equals("left") && translationX > 0) {
                    translationX -= 100;
                    if (translationX < 0)
                        translationX = 0;
                    turning = true;
                }
                else if (direction.equals("right") && translationX < getMeasuredWidth()) {
                    translationX += 100;
                    if (translationX >= getMeasuredWidth())
                        translationX = getMeasuredWidth();
                    turning = true;
                }

                if (turning){
                    invalidate();
                    mHandler.postDelayed(this, 20);
                }else {
                    isTurning = false;
                    adapter.turnPage(getId(), mCurrentPage);
                }
            }
        };
    }

    //判断移动距离 是否超过控件宽度的一半
    private String isStartTurning(PointF start, PointF end) {
        if (!isTurning && start != null && end != null) {
            float moveX = start.x - end.x;
            float w = getMeasuredWidth() / 3f;
            if (moveX > w || moveX < -w) {
                if (moveX > 0) {
                    return "left";
                } else {
                    return "right";
                }
            }
        }
        return null;
    }
}