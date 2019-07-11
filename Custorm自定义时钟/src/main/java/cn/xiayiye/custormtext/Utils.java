package cn.xiayiye.custormtext;

/**
 * 创 建 者：下一页5（轻飞扬）
 * 创建时间：2018/2/26.12:30
 * 个人小站：http://wap.yhsh.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 空间名称：XiaYiYeMap
 * 项目包名：cn.xiayiye.custormtext
 */
import android.annotation.SuppressLint;
import android.content.Context;
//import android.support.annotation.NonNull;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
   /* public static void init(@NonNull Context context) {
        Utils.context = context.getApplicationContext();
    } */
    public static void init( Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}
