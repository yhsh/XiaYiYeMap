package cn.xiayiye.custormtext;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创 建 者：下一页5（轻飞扬）
 * 创建时间：2018/2/24.14:37
 * 个人小站：http://wap.yhsh.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 空间名称：XiaYiYeMap
 * 项目包名：cn.xiayiye.custormtext
 */
public class xyyView extends View {
    public xyyView(Context context) {
        super(context);
    }

    public xyyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Text_name);
        int color = typedArray.getColor(R.styleable.Text_name_yhsh_color, 0xffff0000);
        setBackgroundColor(color);
        typedArray.recycle();
    }

    public xyyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
