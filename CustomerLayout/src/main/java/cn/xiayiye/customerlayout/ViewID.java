package cn.xiayiye.customerlayout;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生成View id 兼容sdk17(4.2.2)以下版本
 * Created by xlg on 2018/3/23.
 */
public class ViewID {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

}

