package cn.xiayiye.customerlayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.UUID;

public class ReapalUtil {

    private static final String REAPAL_UTIL_TAG = "ReapalUtil ";

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 获取资源文件--Drawable
     */
    public static Drawable getResDrawable(Context context, String res_name) {
        InputStream is = null;
        try {
            is = context.getAssets().open(res_name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(is);
        Drawable drawable = new BitmapDrawable(null, bitmap);
        return drawable;
    }

    /**
     * 分-->元  转换-用于显示
     */
    public static String fen2Yuan(String total_fee) {
        DecimalFormat df = new DecimalFormat("#,##0.00 ");
        return df.format(new BigDecimal(Double.parseDouble(total_fee)).movePointLeft(2));
    }

    public static String yuan2Fen(String total_fee_Yuan) {
        //		//String total_fee_Yuan_raw = total_fee_Yuan.replace(",", "");
        //		DecimalFormat df = new DecimalFormat("#,###");
        ////		String total_fee = new BigDecimal(total_fee_Yuan).movePointRight(2).toString();
        //		String total_fee = df.format(new BigDecimal(Long.parseLong(total_fee_Yuan)).movePointRight(2));
        //		return total_fee;
        return yuan2FenLong(total_fee_Yuan);
    }

    public static String yuan2FenLong(String total_fee_Yuan) {
        String yuan_raw = total_fee_Yuan.replace(",", "").trim();
        BigDecimal fenBd = new BigDecimal(yuan_raw).multiply(new BigDecimal(100));
        fenBd = fenBd.setScale(0, BigDecimal.ROUND_HALF_UP);
        return String.valueOf(fenBd.longValue());
    }




    private static String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }



    /**
     * 获取设备Ip地址
     * 规避小米手机无法获取数据连接状态下的Ip地址
     */
    public static String getLocalIPAddress() {
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                NetworkInterface networkInterface = enumeration.nextElement();

                if (networkInterface.isUp()) {
                    Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();
                    while (addressEnumeration.hasMoreElements()) {
                        String ip = addressEnumeration.nextElement().getHostAddress();
                        final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
                        if (ip.matches(REGX_IP) && !ip.equals("127.0.0.1")) {
                            //IP_Final
                            return ip;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取银行图标
     */
    public static Drawable getResBankDrawable(Context context, String bank_name) {
        InputStream is = null;
        String res_name = "unknown.png";
        try {
            if (bank_name != null) {
                switch (bank_name) {
                    case "?":
                        res_name = "unknown.png";
                        break;
                    case "北京银行":
                        res_name = "bank_bj.png";
                        break;
                    case "光大银行":
                        res_name = "bank_gd.png";
                        break;
                    case "广发银行":
                        res_name = "bank_gf.png";
                        break;
                    case "工商银行":
                        res_name = "bank_gs.png";
                        break;
                    case "华夏银行":
                        res_name = "bank_hx.png";
                        break;
                    case "建设银行":
                        res_name = "bank_js.png";
                        break;
                    case "交通银行":
                        res_name = "bank_jt.png";
                        break;
                    case "民生银行":
                        res_name = "bank_ms.png";
                        break;
                    case "农业银行":
                        res_name = "bank_ny.png";
                        break;
                    case "平安银行":
                        res_name = "bank_pa.png";
                        break;
                    case "浦发银行":
                        res_name = "bank_pf.png";
                        break;
                    case "上海银行":
                        res_name = "bank_sh.png";
                        break;
                    case "兴业银行":
                        res_name = "bank_xy.png";
                        break;
                    case "邮政储蓄":
                        res_name = "bank_yz.png";
                        break;
                    case "中国银行":
                        res_name = "bank_zg.png";
                        break;
                    case "招商银行":
                        res_name = "bank_zs.png";
                        break;
                    case "中信银行":
                        res_name = "bank_zx.png";
                        break;
                }
            }
            is = context.getAssets().open(res_name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }


}
