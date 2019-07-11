package cn.xiayiye.forceoff_line;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 创 建 者：下一页5（轻飞扬）
 * 创建时间：2018/2/28.11:05
 * 个人小站：http://wap.yhsh.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 空间名称：XiaYiYeMap
 * 项目包名：cn.xiayiye.forceoff_line
 */
class ActivityCollector {
    private static List<Activity> activities = new ArrayList<>();//存放activity的集合

    //添加activity
    static void addActivity(Activity acy) {
        activities.add(acy);
    }

    //移除activity
    static void removeActivity(Activity acy) {
        activities.remove(acy);
    }

    //销毁所有activity
    static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();//关闭所有activity
            }
        }
    }
}
