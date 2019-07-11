package voicenotes.qqdelete;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;


/**
 * @author （轻飞扬）
 *         创建时间：2018/6/27.10:06
 */
public class SlideListView extends ListView {
    //记录当前点击的item View
    private SlideItem mTouchView = null;
    //x轴坐标
    private float mDownX;
    //y轴坐标
    private float mDownY;
    //记录点击状态
    private int mTouchState;
    //记录点击位置
    private int mTouchPosition;
    //按下状态
    private static final int TOUCH_STATE_NONE = 0;
    //横滑状态
    private static final int TOUCH_STATE_X = 1;
    //竖滑状态
    private static final int TOUCH_STATE_Y = 2;
    //判断横竖滑动的最小值
    private static final int MAX_Y = 5;
    private static final int MAX_X = 3;

    public SlideListView(Context context) {
        super(context);
    }

    public SlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() != MotionEvent.ACTION_DOWN && mTouchView == null) {
            return super.onTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按住的item的position
                int oldPosition = mTouchPosition;
                //记录位置
                mDownX = ev.getX();
                mDownY = ev.getY();
                mTouchState = TOUCH_STATE_NONE;
                //根据当前横纵坐标点获取点击的item的position
                mTouchPosition = this.pointToPosition((int) ev.getX(), (int) ev.getY());

                //判断当前点击的是否和上次点击的item是同一个，如果是同一个，并且状态是打开了的就记录状态和坐标
                //记录坐标通过Item中的downX属性
                if (mTouchPosition == oldPosition && mTouchView != null && mTouchView.isOpen()) {
                    mTouchState = TOUCH_STATE_X;
                    mTouchView.onSwipe(ev);
                    return true;
                }
                //获取当前的item的View
                View currentView = getChildAt(mTouchPosition - getFirstVisiblePosition());
                //如果不是同一个item 那么点击的话就关闭掉之前打开的item
                if (mTouchView != null && mTouchView.isOpen()) {
                    mTouchView.smoothCloseMenu();
                    mTouchView = null;
                    return super.onTouchEvent(ev);
                }
                //判断该view的类型
                if (currentView instanceof SlideItem) {
                    mTouchView = (SlideItem) currentView;
                }
                if (mTouchView != null) {
                    mTouchView.onSwipe(ev);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = Math.abs((ev.getY() - mDownY));
                float dx = Math.abs((ev.getX() - mDownX));
                if (mTouchState == TOUCH_STATE_X) {
                    if (mTouchView != null) {
                        //执行滑动
                        mTouchView.onSwipe(ev);
                    }
                    return true;
                } else if (mTouchState == TOUCH_STATE_NONE) {
                    //判断滑动方向，x方向执行滑动，Y方向执行滚动
                    if (Math.abs(dy) > MAX_Y) {
                        mTouchState = TOUCH_STATE_Y;
                    } else if (dx > MAX_X) {
                        mTouchState = TOUCH_STATE_X;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //判断状态
                if (mTouchState == TOUCH_STATE_X) {
                    if (mTouchView != null) {
                        mTouchView.onSwipe(ev);
                        //如过最后状态是打开 那么就重新初始化
                        if (!mTouchView.isOpen()) {
                            mTouchPosition = -1;
                            mTouchView = null;
                        }
                    }
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(ev);
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
