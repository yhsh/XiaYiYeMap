package cn.xiayiye.custormtext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 创 建 者：下一页5（轻飞扬）
 * 创建时间：2018/2/24.13:59
 * 个人小站：http://wap.yhsh.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 空间名称：XiaYiYeMap
 * 项目包名：cn.xiayiye.custormtext
 */
public class YhshView extends View {

    private String draw_string;
    private Paint mPaint;

    public YhshView(Context context) {
        this(context, null);
    }

    public YhshView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public YhshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Text_name, 0, 0);
        boolean Text_name_yhsh_open = typedArray.getBoolean(R.styleable.Text_name_yhsh_open, false);
        int Text_name_yhsh_color = typedArray.getColor(R.styleable.Text_name_yhsh_color, getResources().getColor(R.color.colorAccent));
        int dimensionPixelSize = typedArray.getDimensionPixelSize(R.styleable.Text_name_yhsh_size, 16);
        draw_string = typedArray.getString(R.styleable.Text_name_yhsh_text);
        mPaint = new Paint();
        // 设置画笔为抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setColor(Text_name_yhsh_color);
        mPaint.setTextSize(dimensionPixelSize);
        //获取绘制文本的宽和高
        Rect mBoud = new Rect();
        mPaint.getTextBounds(draw_string, 0, draw_string.length(), mBoud);
        setBackgroundColor(Text_name_yhsh_color);
        typedArray.recycle();
        initView();
    }

    private void initView() {

    }

  /*  @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawText(text, x, y, paint); //第一个参数是文本，第四个参数是画笔，第二个参数x默认是字符串的左边在屏幕的位置， 第三个参数y是这个字符文本baseline基线在屏幕上的位置，不是这个字符的中心在屏幕的位置

        canvas.drawText("扬宏豕慧", 10f, 100f, mPaint);
    }*/
}
