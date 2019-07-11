package cn.xiayiye.custormtext;

import android.app.Application;
import android.util.Log;

/**
 * 创 建 者：下一页5（轻飞扬）
 * 创建时间：2018/2/26.12:41
 * 个人小站：http://wap.yhsh.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 空间名称：XiaYiYeMap
 * 项目包名：cn.xiayiye.custormtext
 */
public class XiaYiYeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
