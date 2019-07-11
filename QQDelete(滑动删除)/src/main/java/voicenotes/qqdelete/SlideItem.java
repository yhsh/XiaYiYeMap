package voicenotes.qqdelete;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;


/**
 * @author （轻飞扬）
 *         创建时间：2018/6/27.10:08
 */
public class SlideItem extends LinearLayout {
    private View contentView = null; //不滑动显示的view
    private View menuView = null; //左滑显示的view

    //计算滑动 动画效果
    private Scroller mOpenScroller;
    private Scroller mCloseScroller;

    private int downX; //开始按下的位置

    //记录状态
    private int state = STATE_CLOSE;
    private static final int STATE_CLOSE = 0;
    private static final int STATE_OPEN = 1;

    private int mBaseX;//在关闭滑动的时候计算与父布局的剩余距离


    public SlideItem(Context context) {
        super(context);
    }

    public SlideItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContentView(View contentView, View rightView) {

        this.contentView = contentView;
        this.menuView = rightView;

        //初始化mColoseScroller和mOpenScroller
        mCloseScroller = new Scroller(getContext());
        mOpenScroller = new Scroller(getContext());

        initView();
    }

    //child view的布局参数设定好后 添加到parent view里面
    private void initView() {
        //这是设置宽和高
        LayoutParams contentParams = new LayoutParams
                (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        LayoutParams rightParams = new LayoutParams
                (LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(contentParams);
        contentView.setPadding(10, 10, 10, 10);
        menuView.setLayoutParams(rightParams);
        this.addView(contentView);
        this.addView(menuView);
    }

    // 判断是否滑出的状态
    public boolean isOpen() {
        return state == STATE_OPEN;
    }

    /**
     * 供listView调用 进行视图的移动   listView判断状态 什么情况下左滑
     *
     * @param event
     * @return
     */
    public boolean onSwipe(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //按下位置减去移动位置 获取移动的距离
                int dis = (int) (downX - event.getX());
                if (state == STATE_OPEN) {
                    dis += menuView.getWidth();
                }
                //移动
                move(dis);
                break;
            case MotionEvent.ACTION_UP:
                //当滑到右边视图一半的距离 自动滑进滑出
                if ((downX - event.getX()) > (menuView.getWidth() / 2)) {
                    smoothOpenMenu();
                } else {
                    smoothCloseMenu();
                    return false;
                }
                break;
            default:
                break;
        }
        //消费掉事件
        return true;
    }

    /**
     * 视图重新绘制时调用
     */
    @Override
    public void computeScroll() {
        if (state == STATE_OPEN) {
            //computeScrollOffset滑动是否结束
            if (mOpenScroller.computeScrollOffset()) {
                move(mOpenScroller.getCurrX());
                postInvalidate();
            }
        } else {
            if (mCloseScroller.computeScrollOffset()) {
                move(mBaseX - mCloseScroller.getCurrX());
                postInvalidate();
            }
        }
    }

    /**
     * 移动视图
     *
     * @param dis
     */
    private void move(int dis) {
        //这两个判断是为了保证 不要把视图移动过多 导致视图偏移
        if (dis > menuView.getWidth()) {
            dis = menuView.getWidth();
        }
        if (dis < 0) {
            dis = 0;
        }
        //view.layout()控制view相对于其父布局的位置   在触发移动的时候调用不断改变位置 完成实际的滑动效果
        contentView.layout(-dis, contentView.getTop(), contentView.getWidth() - dis, getMeasuredHeight());
        menuView.layout(contentView.getWidth() - dis, menuView.getTop(), contentView.getWidth() + menuView.getWidth() - dis, menuView.getBottom());
    }

    /**
     * 滑动关闭
     * contentView.getLeft()  与其父视图的相对位置
     */
    public void smoothCloseMenu() {
        state = STATE_CLOSE;
        mBaseX = -contentView.getLeft();
        mCloseScroller.startScroll(0, 0, mBaseX, 0, 350);
        postInvalidate();
    }

    /**
     * 滑动打开
     */
    public void smoothOpenMenu() {
        state = STATE_OPEN;
        mOpenScroller.startScroll(-contentView.getLeft(), 0, menuView.getWidth(), 0, 350);
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (menuView != null)
            menuView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //确保centerView menuView的显示位置
        if (contentView != null)
            contentView.layout(0, 0, getMeasuredWidth(), contentView.getMeasuredHeight());
        if (menuView != null)
            menuView.layout(getMeasuredWidth(), 0, getMeasuredWidth() + menuView.getMeasuredWidth(), contentView.getMeasuredHeight());
    }
}
