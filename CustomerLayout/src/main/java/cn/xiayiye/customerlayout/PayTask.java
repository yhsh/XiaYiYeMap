/*
package cn.xiayiye.customerlayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.reapal.BindCardListActivity;
import com.reapal.CardNoCheckActivity;
import com.reapal.CryptoTool;
import com.reapal.bean.BankCardInfoEntitys;
import com.reapal.bean.SerializableMap;
import com.reapal.config.ReapalConfig;
import com.reapal.json.JSON;
import com.reapal.security.SecurityUtil;
import com.reapal.threadpool.ReapalThreadPoolFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayTask {

    private static final String TAG = "PayTask";

    private static Map<String, String> map_org_data;
    private static Activity context_order_activity;
    public static final int REQUEST_CODE = 1001;
    public static final int RESULT_SUCCESS = 1002;
    public static final int RESULT_FAILURE = 1003;
    private static final String PAY_TASK_TAG = "PayTask ";

    private PayTask() {

    }

    private static class PayTaskHolder {
        private static final PayTask INSTANCE = new PayTask();
    }

    // 创建单例
    public static PayTask getInstance(String privateKey, String keystore, String key) {
        PayTask mPayTaskPar = PayTaskHolder.INSTANCE;
        mPayTaskPar.saveSecretKey(privateKey, keystore, key);
        return mPayTaskPar;
    }

    private void saveSecretKey(String privateKey, String keystore, String key) {
        // String decryptPrivateKey = CryptoTool.aesDecryptStringWithBase64(privateKey, CryptoTool.DECRYPT_KEY, null);
        // LogUtil.e(PAY_TASK_TAG + "PayTask() key = " + decryptPrivateKey);
        Log.e(TAG, "saveSecretKey: " + privateKey + " pwd" + keystore);
        ReapalConfig.privateKey = privateKey;
        ReapalConfig.privateKeyStore = keystore;
        ReapalConfig.key = key;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        private Intent intent_next;

        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                intent_next = new Intent();
                Bundle bundle_res = msg.getData();
                String resJson = bundle_res.get("resJson").toString();
                LogUtil.e(PAY_TASK_TAG + "handleMessage() resJson = " + resJson);

                if (StringUtils.isNotEmpty(resJson)) {
                    JSONObject object = null;
                    String result_code = null;
                    String result_msg = null;
                    JSONArray cardList = null;
                    try {
                        object = new JSONObject(resJson);
                        result_code = object.getString("result_code");
                        result_msg = object.getString("result_msg");
                        cardList = object.getJSONArray("cardList");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    LogUtil.e(PAY_TASK_TAG + "handleMessage() result_msg = " + result_code);

					*/
/*{"bind_card_list":[],"member_id":"1","merchant_id":"100000000000147"}  联调环境*//*

                    */
/*if (resJson.contains("bind_card_list") && resJson.contains("merchant_id")) { // 已绑卡，但联调环境例外
                        List<BankCardInfoEntitys.BankCardInfoEntity> bind_card_list;
                        BankCardInfoEntitys BankCardInfoEntitys = JSON.parseObject(resJson, BankCardInfoEntitys.class);
                        bind_card_list = BankCardInfoEntitys.getBind_card_list();
                        LogUtil.e(PAY_TASK_TAG + "handleMessage() bind_card_list" + bind_card_list.size());
                        if (bind_card_list != null && bind_card_list.size() > 0) {
                            intent_next.putExtra("str_resJson", resJson);
                            intent_next.setClass(context_order_activity, BindCardListActivity.class); // 跳转已绑卡支付页面
                        } else {
                            intent_next.setClass(context_order_activity, CardNoCheckActivity.class); // 跳转进行绑卡页面
                        }
                    }*//*



                    if (result_code != null && "0000".equals(result_code)) {
                        if (cardList != null && cardList.length() > 0) {
                            intent_next.putExtra("str_resJson", resJson);
                            intent_next.setClass(context_order_activity, BindCardListActivity.class); // 跳转已绑卡支付页面
                        } else {
                            intent_next.setClass(context_order_activity, CardNoCheckActivity.class); // 跳转进行绑卡页面
                        }
                    } else if ("1007".contains(result_code)) {  //联调
                        Toast.makeText(context_order_activity, result_msg, Toast.LENGTH_SHORT).show();
                        DialogUtil.hideProgressDialog();
                        return;
                    } else */
/*if (resJson.contains("bind_card_list\":[]") || resJson.contains("用户于指定的银行卡无绑卡关系")) *//*
 { // 未绑卡 result_code-5037:
                        intent_next.setClass(context_order_activity, CardNoCheckActivity.class); // 跳转进行绑卡页面
                    }*/
/*else {
                        ToastUtil.showMessage(context_order_activity, "网络或配置异常,请稍后重试");
						mDialog.dismiss();
						return;
					}*//*

                    DialogUtil.hideProgressDialog();
                    Bundle bundle = new Bundle();
                    SerializableMap myMap = new SerializableMap();
                    myMap.setMap(map_org_data);
                    bundle.putSerializable("map", myMap);
                    intent_next.putExtras(bundle);
                    context_order_activity.startActivityForResult(intent_next, REQUEST_CODE);
                } else {
                    DialogUtil.hideProgressDialog();
                }
            }
        }
    };

    */
/**
     * 卡信息查询的方法
     *//*

    public String cardQuery(final Map<String, String> map, Context mContext) {

        ReapalConfig.tail_url = "/fast/bankcard/list";

        Map<String, String> response = ReapalSubmit.buildSubmitForRSAAndMD5More(mContext, map, "/fast/bindcard/list");

        return beginRequest(response);
    }

    */
/**
     * 完美账单绑卡签约接口
     *
     * @return
     *//*

    public String creditCardContract(Map<String, String> map) {
        ReapalConfig.tail_url = "/member/custmem/cashier/ledger/sign";
        return executeRequest(map);
    }

    */
/**
     * 完美账单绑卡信息查询列表接口
     *//*

    public String cardListQuery(Map<String, String> map) {
        ReapalConfig.tail_url = "/member/bindCard/queryBindCard";
        return executeRequest(map);
    }

    */
/**
     * 完美账单确认支付接口
     *//*

    public String confirmPay(Map<String, String> map) {
        ReapalConfig.tail_url = "/member/custmem/cashier/comfirm";
        return executeRequest(map);
    }

    */
/**
     * 完美账单支付结果查询接口
     *//*

    public String payResultQuery(Map<String, String> map) {
        ReapalConfig.tail_url = "/fast/search";
        return executeRequest(map);
    }

    */
/**
     * 完美账单发送短信验证码接口
     *//*

    public String sendSms(Map<String, String> map) {
        ReapalConfig.tail_url = "/member/comm/sendPhoneVCodeCommon";
        return executeRequest(map);
    }

    */
/**
     * 完美账单绑卡接口
     *//*

    public String bindCard(Map<String, String> map) {
        ReapalConfig.tail_url = "/member/bindCard/bind";
        return executeRequest(map);
    }

    */
/**
     * 完美账单卡BIN信息接口
     *//*

    public String cardBINInfo(Map<String, String> map) {
        ReapalConfig.tail_url = "/fast/bankcard/list";
        return executeRequest(map);
    }

    */
/**
     * 完美账单个人会员查询接口
     *//*

    public String personalQuery(Map<String, String> map) {
        ReapalConfig.tail_url = "/member/pmember/queryPersonAccount";
        return executeRequest(map);
    }

    public void start(final Activity activity, final Map<String, String> map_data, boolean isFormal) {
        context_order_activity = activity;
        map_org_data = map_data;
        ReapalConfig.merchant_id = map_data.get("merchant_id");
        ReapalConfig.member_no = map_data.get("member_no");
        ReapalConfig.rongpay_api = isFormal ? ReapalConfig.API_FORMAL : ReapalConfig.API_TEST;

        DialogUtil.showProgressDialog(activity, false);
        if (!validatePackage()) {
            Toast.makeText(activity, "包名校验失败", Toast.LENGTH_SHORT);
            DialogUtil.hideProgressDialog();
            return;
        }

        checkBindListInfo();
    }

    */
/**
     * 校验包名
     *
     * @return
     *//*

    private boolean validatePackage() {
        return true;
    }

    */
/**
     * 查询绑卡列表信息,判断是否绑卡
     **//*

    private void checkBindListInfo() {
        if (NetworkTypeUtil.getNetworkType(context_order_activity) == NetworkTypeUtil.NO_NETWORK) {
            DialogUtil.hideProgressDialog();
            WarningUtil.networkAnomaly(context_order_activity);
            return;
        }
        ReapalThreadPoolFactory.getInstance().executeRequest(new Runnable() {
            @Override
            public void run() {
                final Map<String, String> map = new HashMap<>();
                map.put("member_no", ReapalConfig.member_no);
                map.put("sign_type", "RSA");
                map.put("charset", "UTF-8");
                LogUtil.e(PAY_TASK_TAG + "checkBindListInfo() map = " + map);
                String resJson = null;
                // 查询绑卡列表
                try {
                    String response = ReapalSubmit.buildSubmit(context_order_activity, map, "/bindCard/queryBindCard");
                    Log.e(TAG, "run: " + response);
                    JSONObject json = new JSONObject(response);
                    String encrypt_key = json.getString("encryptkey");
                    String data = json.getString("data");
                    LogUtil.e(PAY_TASK_TAG + "checkBindListInfo() response未解密 = \n" + response);
                    resJson = SecurityUtil.decryptData(data, encrypt_key, ReapalConfig.privateKey);
                    LogUtil.e(PAY_TASK_TAG + "checkBindListInfo() resJson = " + resJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (resJson != null) {
                    Bundle bundle_res_json = new Bundle();
                    bundle_res_json.putString("resJson", resJson);
                    Message msg = new Message();
                    msg.setData(bundle_res_json);
                    msg.what = 100;
                    handler.sendMessage(msg);
                } else {
                    DialogUtil.hideProgressDialog();
                }
            }
        });
    }

    */
/**
     * 开启网络请求
     *//*

    private String beginRequest(final Map<String, String> map) {
        // 请求接口地址
        String url = ReapalConfig.tail_url;
        String post = null;
        // 返回结果
        try {
            post = WebUtils.doPost(ReapalConfig.rongpay_api + url, map, 10 * 1000, 10 * 1000);
        } catch (IOException e) {
            post = "error";
            e.printStackTrace();
        }
        return post;
    }

    */
/**
     * 完美账单执行网络请求
     *//*

    private String executeRequest(Map<String, String> map) {
        Map<String, String> signParam = ReapalSubmit.perfectBillMD5Sign(map);
        if (signParam.size() == 0) {
            return null;
        }
        // 请求接口地址
        String url = ReapalConfig.tail_url;
        String post = null;
        // 返回结果
        try {
            post = WebUtils.doPost(ReapalConfig.rongpay_api + url, signParam, 10 * 1000, 10 * 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

}
*/
