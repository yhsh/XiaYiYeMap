/*
package cn.xiayiye.customerlayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.reapal.json.JSON;
import com.reapal.bean.BankCardInfoEntitys;
import com.reapal.bean.OrderInfo;
import com.reapal.bean.SerializableMap;
import com.reapal.config.ReapalConfig;
import com.reapal.security.SecurityUtil;
import com.reapal.threadpool.ReapalThreadPoolFactory;
import com.reapal.utils.DialogUtil;
import com.reapal.utils.DisplaySizeUtil;
import com.reapal.utils.LogUtil;
import com.reapal.utils.NetworkTypeUtil;
import com.reapal.utils.PayTask;
import com.reapal.utils.ReapalSubmit;
import com.reapal.utils.ReapalUtil;
import com.reapal.utils.ScaleUtil;
import com.reapal.utils.ToastUtil;
import com.reapal.utils.ViewID;
import com.reapal.utils.WarningUtil;

public class BindCardListActivity extends BaseActivity implements OnClickListener {

    private String[] card_type;
    private String[] card_last;
    private String[] card_phone;
    private String[] card_bank_name;
    private int times_sent_sms = 0;
    private static List<BankCardInfoEntitys.BankCardInfoEntity> bind_card_list;
    private Dialog dialog;
    private Map<String, String> map;
    private TextView tv_chosen_card;
    private OrderInfo orderInfo;
    private EditText et_captchas;
    private Button bt_get_captchas;
    private Button btn_bind_card;
    private String[] card_bind_id;
    private int selected_position;
    private TimeCount time;
    private TextView tv_sms_not_received;
    private boolean captchas_got = false;
    private ImageView iv_backArrow;
    private ImageView bankLogo;
    private TextView footerView;
    private LinearLayout ll_change;
    private ImageView iv_downArrow;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private TextView headerView;
    private int mWidth;
    private LinearLayout ll_div1;
    private TextView mDiv_Line2;

    private static final String BIND_CARD_LIST_TAG = "BindCardListActivity ";
    private static final int PAY_SUCCESS_CODE = 1000;
    private static final int SEND_SMS_SUCCESS_CODE = 2000;
    private static final int FAIL_CODE = 3000;
    private ArrayList<String> bank_list_bank_card_no = new ArrayList<>();//银行卡号
    private ArrayList<String> bank_list_bank_code = new ArrayList<>();//银行简称 CBB
    private ArrayList<String> bank_list_bank_name = new ArrayList<>();//银行名称
    private ArrayList<String> bank_list_bind_id = new ArrayList<>();//bind的ID
    private ArrayList<String> bank_list_bind_type = new ArrayList<>();//bind类型
    private ArrayList<String> bank_list_card_type = new ArrayList<>();//银行卡类型
    private ArrayList<String> bank_list_phone_no = new ArrayList<>();//手机号

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == PAY_SUCCESS_CODE) { // 处理网络获取卡号之后 进行支付
                Intent intent = new Intent(BindCardListActivity.this, PaymentSuccessActivity.class);
                intent.putExtra("total_fee", orderInfo.getTotal_fee());
                intent.putExtra("order_no", orderInfo.getOrder_no()); // refine-
                startActivityForResult(intent, PayTask.REQUEST_CODE);
                ToastUtil.showMessage(BindCardListActivity.this, "支付成功");
                DialogUtil.hideProgressDialog();
            } else if (msg.what == SEND_SMS_SUCCESS_CODE) { // 短信发送成功
                Toast.makeText(BindCardListActivity.this, "短信发送成功",
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(BindCardListActivity.this,
                        "您还可发送短信" + (3 - times_sent_sms) + "次",
                        Toast.LENGTH_SHORT).show();
            } else if (msg.what == FAIL_CODE) {
                // 处理错误信息,弹窗提示
                String tips = msg.getData().getString("result_msg");
                DialogUtil.showHintDialog(BindCardListActivity.this, null, tips);
                DialogUtil.hideProgressDialog();
                et_captchas.setText("");
                bt_get_captchas.setClickable(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initData();
        initDatas();
//        initView();
        initViews();
//        tv_chosen_card.setText(setItemText(0, false));
    }

    private void initViews() {
        {
            mWidth = DisplaySizeUtil.getDisplaySizeInfo(this).x;
            // 根布局及其参数 定义宽高和布局方向等
            LinearLayout mLL_ready_to_pay = new LinearLayout(this);
            mLL_ready_to_pay.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            mLL_ready_to_pay.setOrientation(LinearLayout.VERTICAL);
            mLL_ready_to_pay.setBackgroundColor(Color.parseColor("#F1F3F5"));
            LinearLayout.LayoutParams para_top = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, ScaleUtil.dip2px(this, 50));
            LinearLayout linearLayout0 = new LinearLayout(this);// 1: 商户名称水平布局
            // 定义宽高和布局方向
            linearLayout0.setLayoutParams(para_top);
            linearLayout0.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout0.setBackgroundColor(Color.parseColor("#323236"));
            linearLayout0.setGravity(Gravity.CENTER_VERTICAL);

            iv_backArrow = new ImageView(this);
            iv_backArrow.setLayoutParams(new LayoutParams(ScaleUtil
                    .dip2px(this, 50), ScaleUtil.dip2px(this, 50)));
            iv_backArrow.setImageDrawable(ReapalUtil.getResDrawable(
                    BindCardListActivity.this, "backArrow.png"));
            iv_backArrow.setPadding(ScaleUtil.dip2px(this, 14), 0,
                    ScaleUtil.dip2px(this, 7), 0);
            iv_backArrow.setOnClickListener(this);
            LinearLayout.LayoutParams para_top_title = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            TextView tv_top = new TextView(this); // 创建TextView对象
            tv_top.setLayoutParams(para_top_title);
            tv_top.setText("融宝收银台"); // 设置文字 颜色 字体
            tv_top.setTextSize(20);
            tv_top.setTextColor(Color.WHITE);
            tv_top.setHeight(ScaleUtil.dip2px(this, 50));
            tv_top.setPadding(0, 0, ScaleUtil.dip2px(this, 45), 0);
            tv_top.setGravity(Gravity.CENTER);

            linearLayout0.addView(iv_backArrow);
            linearLayout0.addView(tv_top);
            mLL_ready_to_pay.addView(linearLayout0);

            // 条目1 商户名称
            linearLayout1 = new LinearLayout(this);
            LinearLayout.LayoutParams para_stripe = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            linearLayout1.setLayoutParams(para_stripe);
            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout1.setBackgroundColor(Color.WHITE);
            linearLayout1.setPadding(ScaleUtil.dip2px(this, 10), 0, 0, 0);
            linearLayout1.setVisibility(View.GONE);
            // 子嵌套控件的宽高参数
            LinearLayout.LayoutParams para_discendant = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            TextView tv_name_merchant = new TextView(this); // 创建TextView对象
            tv_name_merchant.setLayoutParams(para_discendant);
            tv_name_merchant.setText("商品名称"); // 设置文字 颜色 字体
            tv_name_merchant.setTextSize(14);
            tv_name_merchant.setTextColor(Color.BLACK);
            tv_name_merchant.setWidth(mWidth * 9 / 40);
            tv_name_merchant.setHeight(ScaleUtil.dip2px(this, 40));
            tv_name_merchant.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            TextView tv_Merchant_name = new TextView(this);
            tv_Merchant_name.setLayoutParams(para_discendant);
            tv_Merchant_name.setText(map.get("title")); // 设置文字
            tv_Merchant_name.setTextSize(14);
            tv_Merchant_name.setTextColor(Color.BLACK);
            tv_Merchant_name.setWidth(mWidth * 31 / 40);
            tv_Merchant_name.setHeight(ScaleUtil.dip2px(this, 40));
            tv_Merchant_name.setGravity(Gravity.CENTER_VERTICAL);
            // 更新商户名称
            tv_Merchant_name.setText(map.get("title"));

            ll_div1 = new LinearLayout(this);
            LinearLayout.LayoutParams para_div = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            ll_div1.setLayoutParams(para_div);
            ll_div1.setOrientation(LinearLayout.HORIZONTAL);
            ll_div1.setBackgroundColor(Color.WHITE);
            ll_div1.setPadding(0, 0, 0, 0);
            ll_div1.setVisibility(View.GONE);

            TextView mDiv_Line1 = new TextView(this);
            mDiv_Line1.setHeight(ScaleUtil.dip2px(this, 0.75F));
            mDiv_Line1.setWidth(mWidth);
            mDiv_Line1.setBackgroundColor(Color.parseColor("#D1D3D5"));

            // 填充第一行
            linearLayout1.addView(tv_name_merchant); // 在父类布局中添加第一行，及布局样式
            linearLayout1.addView(tv_Merchant_name);
            mLL_ready_to_pay.addView(linearLayout1);
            ll_div1.addView(mDiv_Line1);
            mLL_ready_to_pay.addView(ll_div1);

            linearLayout2 = new LinearLayout(this);
            linearLayout2.setLayoutParams(para_stripe);
            linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout2.setBackgroundColor(Color.WHITE);
            linearLayout2.setPadding(ScaleUtil.dip2px(this, 10), 0, 0, 0);
            linearLayout2.setVisibility(View.GONE);

            TextView tv_no_order = new TextView(this); // 创建TextView对象
            tv_no_order.setLayoutParams(para_discendant);
            tv_no_order.setText("订单号"); // 设置文字 颜色 字体
            tv_no_order.setTextSize(14);
            tv_no_order.setTextColor(Color.BLACK);
            tv_no_order.setWidth(mWidth * 9 / 40);
            tv_no_order.setHeight(ScaleUtil.dip2px(this, 40));
            tv_no_order.setGravity(Gravity.CENTER_VERTICAL);

            TextView tv_order_no = new TextView(this); // 创建TextView对象
            tv_order_no.setLayoutParams(para_discendant);
            tv_order_no.setText(map.get("order_no"));
            // 设置文字
            tv_order_no.setTextSize(14);
            tv_order_no.setTextColor(Color.BLACK);
            tv_order_no.setWidth(mWidth * 31 / 40);
            tv_order_no.setHeight(ScaleUtil.dip2px(this, 40));
            tv_order_no.setGravity(Gravity.CENTER_VERTICAL);

            mDiv_Line2 = new TextView(this);
            mDiv_Line2.setHeight(ScaleUtil.dip2px(this, 0.75f));
            mDiv_Line2.setBackgroundColor(Color.parseColor("#D1D3D5"));
            mDiv_Line2.setVisibility(View.GONE);

            linearLayout2.addView(tv_no_order); // 在父类布局中添加第一行，及布局样式
            linearLayout2.addView(tv_order_no);
            mLL_ready_to_pay.addView(linearLayout2);
            mLL_ready_to_pay.addView(mDiv_Line2);

            LinearLayout linearLayout3 = new LinearLayout(this);
            linearLayout3.setLayoutParams(para_stripe);
            linearLayout3.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout3.setBackgroundColor(Color.WHITE);
            linearLayout3.setPadding(ScaleUtil.dip2px(this, 10), 0, 0, 0);
            TextView tv_fee_total = new TextView(this); // 创建TextView对象
            tv_fee_total.setLayoutParams(para_discendant);
            tv_fee_total.setText("应付金额"); // 设置文字 颜色 字体
            tv_fee_total.setTextSize(14);
            tv_fee_total.setTextColor(Color.BLACK);
            tv_fee_total.setWidth(mWidth * 9 / 40);
            tv_fee_total.setHeight(ScaleUtil.dip2px(this, 50));
            tv_fee_total.setGravity(Gravity.CENTER_VERTICAL);

            TextView tv_total_fee = new TextView(this); // 创建TextView对象
            tv_total_fee.setLayoutParams(para_discendant);
            // 将map中的分转换成元,保留两位小数
            TextPaint tp = tv_total_fee.getPaint();
            tp.setFakeBoldText(true);
            tv_total_fee.setText("￥ " + map.get("total_fee")); // 设置文字 ??
            tv_total_fee.setTextSize(16);
            tv_total_fee.setTextColor(Color.RED);
            tv_total_fee.setWidth(mWidth * 25 / 40);
            tv_total_fee.setHeight(ScaleUtil.dip2px(this, 50));
            tv_total_fee.setGravity(Gravity.CENTER_VERTICAL);

            iv_downArrow = new ImageView(this);
            iv_downArrow.setPadding(ScaleUtil.dip2px(this, 7), 0,
                    ScaleUtil.dip2px(this, 20), 0);
            iv_downArrow.setLayoutParams(new LayoutParams(ScaleUtil
                    .dip2px(this, 44), ScaleUtil.dip2px(this, 44)));
            iv_downArrow.setImageDrawable(ReapalUtil.getResDrawable(
                    BindCardListActivity.this, "downArrow.png"));
            iv_downArrow.setOnClickListener(this);

            // 添加创建分割线3
            TextView mDiv_Line3 = new TextView(this);
            mDiv_Line3.setHeight(ScaleUtil.dip2px(this, 0.75f));
            mDiv_Line3.setBackgroundColor(Color.parseColor("#D1D3D5"));

            linearLayout3.addView(tv_fee_total); // 在父类布局中添加第一行，及布局样式
            linearLayout3.addView(tv_total_fee);
            linearLayout3.addView(iv_downArrow);
            mLL_ready_to_pay.addView(linearLayout3);
            mLL_ready_to_pay.addView(mDiv_Line3);

            // 间隔带
            LinearLayout.LayoutParams para_chooseMethod = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, ScaleUtil.dip2px(this, 10));
            TextView chooseMethod = new TextView(this);
            chooseMethod.setPadding(ScaleUtil.dip2px(this, 10), 0, 0, 0);
            chooseMethod.setLayoutParams(para_chooseMethod);
            chooseMethod.setTextColor(Color.parseColor("#ABABAB"));
            chooseMethod.setBackgroundColor(Color.parseColor("#F3F3F3"));
            mLL_ready_to_pay.addView(chooseMethod);

            // 添加创建分割线4
            TextView mDiv_Line4 = new TextView(this);
            mDiv_Line4.setHeight(ScaleUtil.dip2px(this, 0.75f));
            mDiv_Line4.setBackgroundColor(Color.parseColor("#D1D3D5"));
            mLL_ready_to_pay.addView(mDiv_Line4);
            // 第4--展示支付卡换卡按钮-行: 商户名称水平布局
            // 定义宽高和布局方向____________________________________________________________
            LinearLayout linearLayout4 = new LinearLayout(this);// 1: 商户名称水平布局
            // 定义宽高和布局方向
            linearLayout4.setLayoutParams(para_stripe);
            linearLayout4.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout4.setBackgroundColor(Color.WHITE);
            linearLayout4.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout4.setPadding(ScaleUtil.dip2px(this, 10), 0,
                    ScaleUtil.dip2px(this, 10), 0);
            // 添加银行标志 logo
            bankLogo = new ImageView(this);
            bankLogo.setLayoutParams(new LayoutParams(ScaleUtil.dip2px(this, 28), ScaleUtil.dip2px(this, 28)));
            bankLogo.setImageDrawable(ReapalUtil.getResBankDrawable(this, bank_list_bank_name.get(0)));
            bankLogo.setPadding(0, 0, 10, 0);
            tv_chosen_card = new TextView(this);
            LinearLayout.LayoutParams para_tv = new LinearLayout.LayoutParams(
                    mWidth * 27 / 40, LayoutParams.WRAP_CONTENT);
            tv_chosen_card.setLayoutParams(para_tv);
            // 设置文字 颜色 字体
            tv_chosen_card.setTextSize(14);
            tv_chosen_card.setTextColor(Color.BLACK);
            tv_chosen_card.setHeight(ScaleUtil.dip2px(this, 50));
            tv_chosen_card.setGravity(Gravity.CENTER_VERTICAL);
            String end_number = bank_list_bank_card_no.get(0).substring(bank_list_bank_card_no.get(0).length() - 4, bank_list_bank_card_no.get(0).length());
            tv_chosen_card.setText(bank_list_bank_name.get(0) + "(尾号" + end_number + ")");//默认第一张银行卡
            ll_change = new LinearLayout(this);
            LinearLayout.LayoutParams para_change = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            para_change.gravity = Gravity.CENTER_VERTICAL;
            para_change.setMargins(ScaleUtil.dip2px(this, 10),
                    ScaleUtil.dip2px(this, 6), 0, ScaleUtil.dip2px(this, 5));
            ll_change.setLayoutParams(para_change);
            ll_change.setOrientation(LinearLayout.HORIZONTAL);
            ll_change.setGravity(Gravity.CENTER);
            ll_change.setOnClickListener(this);

            ImageView iv_change_icon = new ImageView(this);
            iv_change_icon.setLayoutParams(new LayoutParams(ScaleUtil.dip2px(this,
                    20), ScaleUtil.dip2px(this, 18)));
            iv_change_icon.setImageDrawable(ReapalUtil.getResDrawable(
                    BindCardListActivity.this, "changeIcon.png"));

            TextView tv_change_card = new TextView(this);
            LinearLayout.LayoutParams para_btn = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            para_btn.setMargins(ScaleUtil.dip2px(this, 5), 0, 0, 0);
            tv_change_card.setLayoutParams(para_btn);
            tv_change_card.setText("更换"); // 设置文字 颜色 字体
            tv_change_card.setTextSize(12);
            tv_change_card.setTextColor(Color.parseColor("#2575CE"));
            tv_change_card.setBackgroundColor(Color.WHITE);
            tv_change_card.setHeight(ScaleUtil.dip2px(this, 50));
            tv_change_card.setGravity(Gravity.CENTER);

            ll_change.addView(iv_change_icon);
            ll_change.addView(tv_change_card);
            linearLayout4.addView(bankLogo);
            linearLayout4.addView(tv_chosen_card);
            linearLayout4.addView(ll_change);
            mLL_ready_to_pay.addView(linearLayout4);
            // 添加创建分割线5
            TextView mDiv_Line5 = new TextView(this);
            mDiv_Line5.setHeight(ScaleUtil.dip2px(this, 0.75f));
            mDiv_Line5.setBackgroundColor(Color.parseColor("#D1D3D5"));
            mLL_ready_to_pay.addView(mDiv_Line5);


            // 第5--手机号 行: 商户名称水平布局
            // 定义宽高和布局方向____________________________________________________________
            LinearLayout linearLayout5 = new LinearLayout(this);// 1: 商户名称水平布局
            // 定义宽高和布局方向
            linearLayout5.setLayoutParams(para_stripe);
            linearLayout5.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout5.setBackgroundColor(Color.WHITE);
            linearLayout5.setPadding(ScaleUtil.dip2px(this, 7), 0,
                    ScaleUtil.dip2px(this, 7), 0);

            TextView tv_phone = new TextView(this);
            LinearLayout.LayoutParams para_bt = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            tv_phone.setLayoutParams(para_bt);
            tv_phone.setText("手机号"); // 设置文字 颜色 字体
            tv_phone.setTextSize(14);
            tv_phone.setTextColor(Color.BLACK);
            tv_phone.setHeight(ScaleUtil.dip2px(this, 50));
            tv_phone.setGravity(Gravity.CENTER_VERTICAL);

            TextView tv_phone_no = new TextView(this);
            LinearLayout.LayoutParams para_tv_phone = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            para_tv_phone.setMargins(ScaleUtil.dip2px(this, 7), 0,
                    ScaleUtil.dip2px(this, 35), 0);
            tv_phone_no.setPadding(20, 0, 0, 0);
            tv_phone_no.setLayoutParams(para_tv_phone);
            tv_phone_no.setText(bank_list_phone_no.get(0)); // 设置文字 颜色 字体
            tv_phone_no.setTextSize(14);
            tv_phone_no.setTextColor(Color.BLACK);
            tv_phone_no.setHeight(ScaleUtil.dip2px(this, 50));
            tv_phone_no.setGravity(Gravity.CENTER_VERTICAL);

            // 添加创建分割线6
            TextView mDiv_Line6 = new TextView(this);
            mDiv_Line6.setHeight(ScaleUtil.dip2px(this, 0.75f));
            mDiv_Line6.setBackgroundColor(Color.parseColor("#D1D3D5"));

            linearLayout5.addView(tv_phone);
            linearLayout5.addView(tv_phone_no);
            mLL_ready_to_pay.addView(linearLayout5);
            mLL_ready_to_pay.addView(mDiv_Line6);


            // 身份证布局
            // 定义宽高和布局方向____________________________________________________________
            LinearLayout linearLayout61 = new LinearLayout(this);// 1: 商户名称水平布局
            // 定义宽高和布局方向
            linearLayout61.setLayoutParams(para_stripe);
            linearLayout61.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout61.setBackgroundColor(Color.WHITE);
            TextView tv_captchas5 = new TextView(this);
            tv_captchas5.setLayoutParams(para_bt);
            tv_captchas5.setText("身份证"); // 设置文字 颜色 字体
            tv_captchas5.setPadding(ScaleUtil.dip2px(this, 7), 0, 0, 0);
            tv_captchas5.setTextSize(14);
            tv_captchas5.setTextColor(Color.BLACK);
            tv_captchas5.setGravity(Gravity.CENTER_VERTICAL);

            et_captchas = new EditText(this);
            LinearLayout.LayoutParams para_et7 = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            et_captchas.setLayoutParams(para_et7);
            para_et7.setMargins(ScaleUtil.dip2px(this, 7), 0,
                    ScaleUtil.dip2px(this, 35), 0);
            et_captchas.setHint("输入身份证"); // 设置文字 颜色 字体
            et_captchas.setWidth(mWidth * 3 / 8);
            et_captchas.setTextSize(14);
            et_captchas
                    .setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            et_captchas.setInputType(InputType.TYPE_CLASS_NUMBER);
            et_captchas.setTextColor(Color.BLACK);
            et_captchas.setHeight(ScaleUtil.dip2px(this, 50));
            et_captchas.setBackgroundResource(0);
            et_captchas.setPadding(20, 0, 0, 0);
            et_captchas.setGravity(Gravity.CENTER_VERTICAL);
            et_captchas.addTextChangedListener(checkCodeWatcher);

            LinearLayout.LayoutParams para_divhor3 = new LinearLayout.LayoutParams(
                    ScaleUtil.dip2px(this, 2), LayoutParams.WRAP_CONTENT);
            para_divhor3.setMargins(ScaleUtil.dip2px(this, 10), 0,
                    ScaleUtil.dip2px(this, 10), 0);

            // 添加创建分割线7
            TextView mDiv_Line9 = new TextView(this);
            mDiv_Line9.setHeight(ScaleUtil.dip2px(this, 0.75f));
            mDiv_Line9.setBackgroundColor(Color.parseColor("#D1D3D5"));

            linearLayout61.addView(tv_captchas5);
            linearLayout61.addView(et_captchas);
            mLL_ready_to_pay.addView(linearLayout61);
            mLL_ready_to_pay.addView(mDiv_Line9);


            // 安全码布局
            // 定义宽高和布局方向____________________________________________________________
            LinearLayout linearLayout_aqm = new LinearLayout(this);// 1: 商户名称水平布局
            // 定义宽高和布局方向
            linearLayout_aqm.setLayoutParams(para_stripe);
            linearLayout_aqm.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout_aqm.setBackgroundColor(Color.WHITE);
            TextView tv_captchas_aqm = new TextView(this);
            tv_captchas_aqm.setLayoutParams(para_bt);
            tv_captchas_aqm.setText("安全码"); // 设置文字 颜色 字体
            tv_captchas_aqm.setPadding(ScaleUtil.dip2px(this, 7), 0, 0, 0);
            tv_captchas_aqm.setTextSize(14);
            tv_captchas_aqm.setTextColor(Color.BLACK);
            tv_captchas_aqm.setGravity(Gravity.CENTER_VERTICAL);

            et_captchas = new EditText(this);
            LinearLayout.LayoutParams para_et_aqm = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            et_captchas.setLayoutParams(para_et_aqm);
            para_et_aqm.setMargins(ScaleUtil.dip2px(this, 7), 0,
                    ScaleUtil.dip2px(this, 35), 0);
            et_captchas.setHint("输入安全码"); // 设置文字 颜色 字体
            et_captchas.setWidth(mWidth * 3 / 8);
            et_captchas.setTextSize(14);
            et_captchas
                    .setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            et_captchas.setInputType(InputType.TYPE_CLASS_NUMBER);
            et_captchas.setTextColor(Color.BLACK);
            et_captchas.setHeight(ScaleUtil.dip2px(this, 50));
            et_captchas.setBackgroundResource(0);
            et_captchas.setPadding(20, 0, 0, 0);
            et_captchas.setGravity(Gravity.CENTER_VERTICAL);
            et_captchas.addTextChangedListener(checkCodeWatcher);

            LinearLayout.LayoutParams para_divhor_aqm = new LinearLayout.LayoutParams(
                    ScaleUtil.dip2px(this, 2), LayoutParams.WRAP_CONTENT);
            para_divhor_aqm.setMargins(ScaleUtil.dip2px(this, 10), 0,
                    ScaleUtil.dip2px(this, 10), 0);

            // 添加创建分割线7
            TextView mDiv_Line_aqm = new TextView(this);
            mDiv_Line_aqm.setHeight(ScaleUtil.dip2px(this, 0.75f));
            mDiv_Line_aqm.setBackgroundColor(Color.parseColor("#D1D3D5"));

            linearLayout_aqm.addView(tv_captchas_aqm);
            linearLayout_aqm.addView(et_captchas);
            mLL_ready_to_pay.addView(linearLayout_aqm);
            mLL_ready_to_pay.addView(mDiv_Line_aqm);


            // 有效期布局
            // 定义宽高和布局方向____________________________________________________________
            LinearLayout linearLayout_yxq = new LinearLayout(this);// 1: 商户名称水平布局
            // 定义宽高和布局方向
            linearLayout_yxq.setLayoutParams(para_stripe);
            linearLayout_yxq.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout_yxq.setBackgroundColor(Color.WHITE);
            TextView tv_captchas_yxq = new TextView(this);
            tv_captchas_yxq.setLayoutParams(para_bt);
            tv_captchas_yxq.setText("有效期"); // 设置文字 颜色 字体
            tv_captchas_yxq.setPadding(ScaleUtil.dip2px(this, 7), 0, 0, 0);
            tv_captchas_yxq.setTextSize(14);
            tv_captchas_yxq.setTextColor(Color.BLACK);
            tv_captchas_yxq.setGravity(Gravity.CENTER_VERTICAL);

            et_captchas = new EditText(this);
            LinearLayout.LayoutParams para_et_yxq = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            et_captchas.setLayoutParams(para_et_yxq);
            para_et_yxq.setMargins(ScaleUtil.dip2px(this, 7), 0,
                    ScaleUtil.dip2px(this, 35), 0);
            et_captchas.setHint("输入有效期"); // 设置文字 颜色 字体
            et_captchas.setWidth(mWidth * 3 / 8);
            et_captchas.setTextSize(14);
            et_captchas
                    .setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            et_captchas.setInputType(InputType.TYPE_CLASS_NUMBER);
            et_captchas.setTextColor(Color.BLACK);
            et_captchas.setHeight(ScaleUtil.dip2px(this, 50));
            et_captchas.setBackgroundResource(0);
            et_captchas.setPadding(20, 0, 0, 0);
            et_captchas.setGravity(Gravity.CENTER_VERTICAL);
            et_captchas.addTextChangedListener(checkCodeWatcher);

            LinearLayout.LayoutParams para_divhor_yxq = new LinearLayout.LayoutParams(
                    ScaleUtil.dip2px(this, 2), LayoutParams.WRAP_CONTENT);
            para_divhor_yxq.setMargins(ScaleUtil.dip2px(this, 10), 0,
                    ScaleUtil.dip2px(this, 10), 0);

            // 添加创建分割线7
            TextView mDiv_Line_yxq = new TextView(this);
            mDiv_Line_yxq.setHeight(ScaleUtil.dip2px(this, 0.75f));
            mDiv_Line_yxq.setBackgroundColor(Color.parseColor("#D1D3D5"));

            linearLayout_yxq.addView(tv_captchas_yxq);
            linearLayout_yxq.addView(et_captchas);
            mLL_ready_to_pay.addView(linearLayout_yxq);
            mLL_ready_to_pay.addView(mDiv_Line_yxq);


            // 确认支付按钮
            btn_bind_card = new Button(this);
            LinearLayout.LayoutParams para_bt_bind_card = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, ScaleUtil.dip2px(this, 43));
            para_bt_bind_card.gravity = Gravity.CENTER_HORIZONTAL;
            para_bt_bind_card.setMargins(ScaleUtil.dip2px(this, 20),
                    ScaleUtil.dip2px(this, 20), ScaleUtil.dip2px(this, 20), 0);
            btn_bind_card.setLayoutParams(para_bt_bind_card);

            btn_bind_card.setBackgroundColor(Color.parseColor("#B3CDE9"));
            btn_bind_card.setText("确认支付");
            btn_bind_card.setTextSize(14);
            btn_bind_card.setTextColor(Color.WHITE);
            btn_bind_card.setOnClickListener(this);
            mLL_ready_to_pay.addView(btn_bind_card);

            tv_sms_not_received = new TextView(this);
            LinearLayout.LayoutParams para_tv_sms_not_received = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, ScaleUtil.dip2px(this, 30));
            para_tv_sms_not_received
                    .setMargins(0, ScaleUtil.dip2px(this, 10), 0, 0);
            para_tv_sms_not_received.gravity = Gravity.CENTER_HORIZONTAL;
            tv_sms_not_received.setOnClickListener(this);
            tv_sms_not_received.setLayoutParams(para_tv_sms_not_received);
            tv_sms_not_received.setText("收不到验证码？");
            tv_sms_not_received.setTextSize(10);
            tv_sms_not_received.setTextColor(Color.parseColor("#2575CE"));
            mLL_ready_to_pay.addView(tv_sms_not_received);

            // 下方布局
            RelativeLayout rl_bottom_info = new RelativeLayout(this);
            RelativeLayout.LayoutParams para_rl_bot_info = new RelativeLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            int info2_id = ViewID.generateViewId();
            int info3_id = ViewID.generateViewId();
            TextView tv_info1 = createTextView();
            RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp1.addRule(RelativeLayout.ABOVE, info2_id);
            lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            tv_info1.setText("融宝支付是央行颁发支付牌照的第三方支付公司。");

            TextView tv_info2 = createTextView();
            tv_info2.setId(info2_id);
            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp2.addRule(RelativeLayout.ABOVE, info3_id);
            lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            tv_info2.setText("融宝收银台通过短信验证码、指纹收集等多重手段确保支付安全。");

            TextView tv_info3 = createTextView();
            tv_info3.setId(info3_id);
            RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp3.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            tv_info3.setText("Copyright©2015融宝支付");
            tv_info3.setPadding(0, 0, 0, ScaleUtil.dip2px(this, 5));

            rl_bottom_info.addView(tv_info1, lp1);
            rl_bottom_info.addView(tv_info2, lp2);
            rl_bottom_info.addView(tv_info3, lp3);
            mLL_ready_to_pay.addView(rl_bottom_info, para_rl_bot_info);

            setContentView(mLL_ready_to_pay);
        }
    }

    private void initDatas() {
        Bundle bundle = getIntent().getExtras();
        SerializableMap myMap = (SerializableMap) bundle.get("map");
        if (null == myMap) {
            throw new NullPointerException("SerializableMap == null");
        }
        map = myMap.getMap();
        String resJson = getIntent().getStringExtra("str_resJson");
        LogUtil.e(" 打印json数据" + resJson);
        if (!resJson.equals("error")) {
            try {
                JSONObject jsonObject = new JSONObject(resJson);
                JSONArray card_List = jsonObject.getJSONArray("cardList");
                for (int i = 0; i < card_List.length(); i++) {
                    bank_list_bank_card_no.add(card_List.getJSONObject(i).getString("bank_card_no"));
                    bank_list_bank_code.add(card_List.getJSONObject(i).getString("bank_code"));
                    bank_list_bank_name.add(card_List.getJSONObject(i).getString("bank_name"));
                    bank_list_bind_id.add(card_List.getJSONObject(i).getString("bind_id"));
                    bank_list_bind_type.add(card_List.getJSONObject(i).getString("bind_type"));
                    bank_list_card_type.add(card_List.getJSONObject(i).getString("card_type"));
                    bank_list_phone_no.add(card_List.getJSONObject(i).getString("phone_no"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e(BIND_CARD_LIST_TAG + "onPause()");
        DialogUtil.hideProgressDialog();
    }

    */
/**
     * 数据初始化
     *//*

    private void initData() {
        orderInfo = new OrderInfo();

        Bundle bundle = getIntent().getExtras();
        SerializableMap myMap = (SerializableMap) bundle.get("map");
        if (null == myMap) {
            throw new NullPointerException("SerializableMap == null");
        }
        map = myMap.getMap();

        // 释放传来的绑卡列表信息
        orderInfo.setMerchant_id(map.get("merchant_id"));
        orderInfo.setOrder_no(map.get("order_no"));
        orderInfo.setTranstime(map.get("transtime"));
        orderInfo.setCurrency(map.get("currency"));
        orderInfo.setTotal_fee(map.get("total_fee"));
        orderInfo.setTitle(map.get("title"));
        orderInfo.setBody(map.get("body"));
        orderInfo.setMember_id(map.get("member_id"));
        orderInfo.setTerminal_type(map.get("terminal_type"));
        orderInfo.setTerminal_info(map.get("terminal_info"));
        orderInfo.setMember_ip(map.get("member_ip"));
        orderInfo.setSeller_email(map.get("seller_email"));
        // 缺失 default_bank payment_type pay_method
        orderInfo.setNotify_url(map.get("notify_url"));
        // 缺失 returnUrl

        LogUtil.e(BIND_CARD_LIST_TAG + "initData() " + map.get("total_fee"));
        LogUtil.e(BIND_CARD_LIST_TAG + "initData() merchant_id = " + map.get("merchant_id"));

        // 获取绑卡列表数据
        String resJson = getIntent().getStringExtra("str_resJson");
        LogUtil.e(BIND_CARD_LIST_TAG + "initData() resJson = " + resJson);
        if (!resJson.equals("error")) {
            BankCardInfoEntitys BankCardInfoEntitys = JSON.parseObject(
                    resJson, BankCardInfoEntitys.class);
            bind_card_list = BankCardInfoEntitys.getBind_card_list();
            LogUtil.e(BIND_CARD_LIST_TAG + "initData() bind_card_list = " + bind_card_list);
            card_type = new String[bind_card_list.size()];
            card_last = new String[bind_card_list.size()];
            card_phone = new String[bind_card_list.size()];
            card_bank_name = new String[bind_card_list.size()];
            card_bind_id = new String[bind_card_list.size()];
        }

        for (int i = 0; i < bind_card_list.size(); i++) {
            LogUtil.e(BIND_CARD_LIST_TAG + "initData() " + bind_card_list.get(i).bank_name);
            LogUtil.e(BIND_CARD_LIST_TAG + "initData() bind_card_list.size() = " + bind_card_list.size());
            card_bank_name[i] = bind_card_list.get(i).bank_name;
            card_last[i] = bind_card_list.get(i).card_last;
            card_bind_id[i] = bind_card_list.get(i).bind_id;
            if (bind_card_list.get(i).bank_card_type.equals("0")) {
                card_type[i] = "储蓄卡";
            } else if (bind_card_list.get(i).bank_card_type.equals("1")) {
                card_type[i] = "信用卡";
            }
            card_phone[i] = bind_card_list.get(i).phone;
        }
        LogUtil.e(BIND_CARD_LIST_TAG + "initData() InitData结束!!!");
    }

    */
/**
     * 界面初始化
     *//*

    private void initView() {
        mWidth = DisplaySizeUtil.getDisplaySizeInfo(this).x;
        // 根布局及其参数 定义宽高和布局方向等
        LinearLayout mLL_ready_to_pay = new LinearLayout(this);
        mLL_ready_to_pay.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mLL_ready_to_pay.setOrientation(LinearLayout.VERTICAL);
        mLL_ready_to_pay.setBackgroundColor(Color.parseColor("#F1F3F5"));
        LinearLayout.LayoutParams para_top = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, ScaleUtil.dip2px(this, 50));
        LinearLayout linearLayout0 = new LinearLayout(this);// 1: 商户名称水平布局
        // 定义宽高和布局方向
        linearLayout0.setLayoutParams(para_top);
        linearLayout0.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout0.setBackgroundColor(Color.parseColor("#323236"));
        linearLayout0.setGravity(Gravity.CENTER_VERTICAL);

        iv_backArrow = new ImageView(this);
        iv_backArrow.setLayoutParams(new LayoutParams(ScaleUtil
                .dip2px(this, 50), ScaleUtil.dip2px(this, 50)));
        iv_backArrow.setImageDrawable(ReapalUtil.getResDrawable(
                BindCardListActivity.this, "backArrow.png"));
        iv_backArrow.setPadding(ScaleUtil.dip2px(this, 14), 0,
                ScaleUtil.dip2px(this, 7), 0);
        iv_backArrow.setOnClickListener(this);
        LinearLayout.LayoutParams para_top_title = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        TextView tv_top = new TextView(this); // 创建TextView对象
        tv_top.setLayoutParams(para_top_title);
        tv_top.setText("融宝收银台"); // 设置文字 颜色 字体
        tv_top.setTextSize(20);
        tv_top.setTextColor(Color.WHITE);
        tv_top.setHeight(ScaleUtil.dip2px(this, 50));
        tv_top.setPadding(0, 0, ScaleUtil.dip2px(this, 45), 0);
        tv_top.setGravity(Gravity.CENTER);

        linearLayout0.addView(iv_backArrow);
        linearLayout0.addView(tv_top);
        mLL_ready_to_pay.addView(linearLayout0);

        // 条目1 商户名称
        linearLayout1 = new LinearLayout(this);
        LinearLayout.LayoutParams para_stripe = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        linearLayout1.setLayoutParams(para_stripe);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setBackgroundColor(Color.WHITE);
        linearLayout1.setPadding(ScaleUtil.dip2px(this, 10), 0, 0, 0);
        linearLayout1.setVisibility(View.GONE);
        // 子嵌套控件的宽高参数
        LinearLayout.LayoutParams para_discendant = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView tv_name_merchant = new TextView(this); // 创建TextView对象
        tv_name_merchant.setLayoutParams(para_discendant);
        tv_name_merchant.setText("商品名称"); // 设置文字 颜色 字体
        tv_name_merchant.setTextSize(14);
        tv_name_merchant.setTextColor(Color.BLACK);
        tv_name_merchant.setWidth(mWidth * 9 / 40);
        tv_name_merchant.setHeight(ScaleUtil.dip2px(this, 40));
        tv_name_merchant.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

        TextView tv_Merchant_name = new TextView(this);
        tv_Merchant_name.setLayoutParams(para_discendant);
        tv_Merchant_name.setText(map.get("title")); // 设置文字
        tv_Merchant_name.setTextSize(14);
        tv_Merchant_name.setTextColor(Color.BLACK);
        tv_Merchant_name.setWidth(mWidth * 31 / 40);
        tv_Merchant_name.setHeight(ScaleUtil.dip2px(this, 40));
        tv_Merchant_name.setGravity(Gravity.CENTER_VERTICAL);
        // 更新商户名称
        tv_Merchant_name.setText(map.get("title"));

        ll_div1 = new LinearLayout(this);
        LinearLayout.LayoutParams para_div = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ll_div1.setLayoutParams(para_div);
        ll_div1.setOrientation(LinearLayout.HORIZONTAL);
        ll_div1.setBackgroundColor(Color.WHITE);
        ll_div1.setPadding(0, 0, 0, 0);
        ll_div1.setVisibility(View.GONE);

        TextView mDiv_Line1 = new TextView(this);
        mDiv_Line1.setHeight(ScaleUtil.dip2px(this, 0.75F));
        mDiv_Line1.setWidth(mWidth);
        mDiv_Line1.setBackgroundColor(Color.parseColor("#D1D3D5"));

        // 填充第一行
        linearLayout1.addView(tv_name_merchant); // 在父类布局中添加第一行，及布局样式
        linearLayout1.addView(tv_Merchant_name);
        mLL_ready_to_pay.addView(linearLayout1);
        ll_div1.addView(mDiv_Line1);
        mLL_ready_to_pay.addView(ll_div1);

        linearLayout2 = new LinearLayout(this);
        linearLayout2.setLayoutParams(para_stripe);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout2.setBackgroundColor(Color.WHITE);
        linearLayout2.setPadding(ScaleUtil.dip2px(this, 10), 0, 0, 0);
        linearLayout2.setVisibility(View.GONE);

        TextView tv_no_order = new TextView(this); // 创建TextView对象
        tv_no_order.setLayoutParams(para_discendant);
        tv_no_order.setText("订单号"); // 设置文字 颜色 字体
        tv_no_order.setTextSize(14);
        tv_no_order.setTextColor(Color.BLACK);
        tv_no_order.setWidth(mWidth * 9 / 40);
        tv_no_order.setHeight(ScaleUtil.dip2px(this, 40));
        tv_no_order.setGravity(Gravity.CENTER_VERTICAL);

        TextView tv_order_no = new TextView(this); // 创建TextView对象
        tv_order_no.setLayoutParams(para_discendant);
        tv_order_no.setText(map.get("order_no"));
        // 设置文字
        tv_order_no.setTextSize(14);
        tv_order_no.setTextColor(Color.BLACK);
        tv_order_no.setWidth(mWidth * 31 / 40);
        tv_order_no.setHeight(ScaleUtil.dip2px(this, 40));
        tv_order_no.setGravity(Gravity.CENTER_VERTICAL);

        mDiv_Line2 = new TextView(this);
        mDiv_Line2.setHeight(ScaleUtil.dip2px(this, 0.75f));
        mDiv_Line2.setBackgroundColor(Color.parseColor("#D1D3D5"));
        mDiv_Line2.setVisibility(View.GONE);

        linearLayout2.addView(tv_no_order); // 在父类布局中添加第一行，及布局样式
        linearLayout2.addView(tv_order_no);
        mLL_ready_to_pay.addView(linearLayout2);
        mLL_ready_to_pay.addView(mDiv_Line2);

        LinearLayout linearLayout3 = new LinearLayout(this);
        linearLayout3.setLayoutParams(para_stripe);
        linearLayout3.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout3.setBackgroundColor(Color.WHITE);
        linearLayout3.setPadding(ScaleUtil.dip2px(this, 10), 0, 0, 0);
        TextView tv_fee_total = new TextView(this); // 创建TextView对象
        tv_fee_total.setLayoutParams(para_discendant);
        tv_fee_total.setText("应付金额"); // 设置文字 颜色 字体
        tv_fee_total.setTextSize(14);
        tv_fee_total.setTextColor(Color.BLACK);
        tv_fee_total.setWidth(mWidth * 9 / 40);
        tv_fee_total.setHeight(ScaleUtil.dip2px(this, 50));
        tv_fee_total.setGravity(Gravity.CENTER_VERTICAL);

        TextView tv_total_fee = new TextView(this); // 创建TextView对象
        tv_total_fee.setLayoutParams(para_discendant);
        // 将map中的分转换成元,保留两位小数
        TextPaint tp = tv_total_fee.getPaint();
        tp.setFakeBoldText(true);
        tv_total_fee.setText("￥ " + orderInfo.getTotal_fee()); // 设置文字 ??
        tv_total_fee.setTextSize(16);
        tv_total_fee.setTextColor(Color.RED);
        tv_total_fee.setWidth(mWidth * 25 / 40);
        tv_total_fee.setHeight(ScaleUtil.dip2px(this, 50));
        tv_total_fee.setGravity(Gravity.CENTER_VERTICAL);

        iv_downArrow = new ImageView(this);
        iv_downArrow.setPadding(ScaleUtil.dip2px(this, 7), 0,
                ScaleUtil.dip2px(this, 20), 0);
        iv_downArrow.setLayoutParams(new LayoutParams(ScaleUtil
                .dip2px(this, 44), ScaleUtil.dip2px(this, 44)));
        iv_downArrow.setImageDrawable(ReapalUtil.getResDrawable(
                BindCardListActivity.this, "downArrow.png"));
        iv_downArrow.setOnClickListener(this);

        // 添加创建分割线3
        TextView mDiv_Line3 = new TextView(this);
        mDiv_Line3.setHeight(ScaleUtil.dip2px(this, 0.75f));
        mDiv_Line3.setBackgroundColor(Color.parseColor("#D1D3D5"));

        linearLayout3.addView(tv_fee_total); // 在父类布局中添加第一行，及布局样式
        linearLayout3.addView(tv_total_fee);
        linearLayout3.addView(iv_downArrow);
        mLL_ready_to_pay.addView(linearLayout3);
        mLL_ready_to_pay.addView(mDiv_Line3);

        // 间隔带
        LinearLayout.LayoutParams para_chooseMethod = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, ScaleUtil.dip2px(this, 10));
        TextView chooseMethod = new TextView(this);
        chooseMethod.setPadding(ScaleUtil.dip2px(this, 10), 0, 0, 0);
        chooseMethod.setLayoutParams(para_chooseMethod);
        chooseMethod.setTextColor(Color.parseColor("#ABABAB"));
        chooseMethod.setBackgroundColor(Color.parseColor("#F3F3F3"));
        mLL_ready_to_pay.addView(chooseMethod);

        // 添加创建分割线4
        TextView mDiv_Line4 = new TextView(this);
        mDiv_Line4.setHeight(ScaleUtil.dip2px(this, 0.75f));
        mDiv_Line4.setBackgroundColor(Color.parseColor("#D1D3D5"));
        mLL_ready_to_pay.addView(mDiv_Line4);
        // 第4--展示支付卡换卡按钮-行: 商户名称水平布局
        // 定义宽高和布局方向____________________________________________________________
        LinearLayout linearLayout4 = new LinearLayout(this);// 1: 商户名称水平布局
        // 定义宽高和布局方向
        linearLayout4.setLayoutParams(para_stripe);
        linearLayout4.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout4.setBackgroundColor(Color.WHITE);
        linearLayout4.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout4.setPadding(ScaleUtil.dip2px(this, 10), 0,
                ScaleUtil.dip2px(this, 10), 0);
        // 添加银行标志 logo
        bankLogo = new ImageView(this);
        bankLogo.setLayoutParams(new LayoutParams(ScaleUtil.dip2px(this, 28), ScaleUtil.dip2px(this, 28)));
        bankLogo.setImageDrawable(ReapalUtil.getResBankDrawable(this, card_bank_name[0]));
        bankLogo.setPadding(0, 0, 10, 0);
        tv_chosen_card = new TextView(this);
        LinearLayout.LayoutParams para_tv = new LinearLayout.LayoutParams(
                mWidth * 27 / 40, LayoutParams.WRAP_CONTENT);
        tv_chosen_card.setLayoutParams(para_tv);
        // 设置文字 颜色 字体
        tv_chosen_card.setTextSize(14);
        tv_chosen_card.setTextColor(Color.BLACK);
        tv_chosen_card.setHeight(ScaleUtil.dip2px(this, 50));
        tv_chosen_card.setGravity(Gravity.CENTER_VERTICAL);

        ll_change = new LinearLayout(this);
        LinearLayout.LayoutParams para_change = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        para_change.gravity = Gravity.CENTER_VERTICAL;
        para_change.setMargins(ScaleUtil.dip2px(this, 10),
                ScaleUtil.dip2px(this, 6), 0, ScaleUtil.dip2px(this, 5));
        ll_change.setLayoutParams(para_change);
        ll_change.setOrientation(LinearLayout.HORIZONTAL);
        ll_change.setGravity(Gravity.CENTER);
        ll_change.setOnClickListener(this);

        ImageView iv_change_icon = new ImageView(this);
        iv_change_icon.setLayoutParams(new LayoutParams(ScaleUtil.dip2px(this,
                20), ScaleUtil.dip2px(this, 18)));
        iv_change_icon.setImageDrawable(ReapalUtil.getResDrawable(
                BindCardListActivity.this, "changeIcon.png"));

        TextView tv_change_card = new TextView(this);
        LinearLayout.LayoutParams para_btn = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        para_btn.setMargins(ScaleUtil.dip2px(this, 5), 0, 0, 0);
        tv_change_card.setLayoutParams(para_btn);
        tv_change_card.setText("更换"); // 设置文字 颜色 字体
        tv_change_card.setTextSize(12);
        tv_change_card.setTextColor(Color.parseColor("#2575CE"));
        tv_change_card.setBackgroundColor(Color.WHITE);
        tv_change_card.setHeight(ScaleUtil.dip2px(this, 50));
        tv_change_card.setGravity(Gravity.CENTER);

        ll_change.addView(iv_change_icon);
        ll_change.addView(tv_change_card);
        linearLayout4.addView(bankLogo);
        linearLayout4.addView(tv_chosen_card);
        linearLayout4.addView(ll_change);
        mLL_ready_to_pay.addView(linearLayout4);
        // 添加创建分割线5
        TextView mDiv_Line5 = new TextView(this);
        mDiv_Line5.setHeight(ScaleUtil.dip2px(this, 0.75f));
        mDiv_Line5.setBackgroundColor(Color.parseColor("#D1D3D5"));
        mLL_ready_to_pay.addView(mDiv_Line5);

        // 第5--手机号 行: 商户名称水平布局
        // 定义宽高和布局方向____________________________________________________________
        LinearLayout linearLayout5 = new LinearLayout(this);// 1: 商户名称水平布局
        // 定义宽高和布局方向
        linearLayout5.setLayoutParams(para_stripe);
        linearLayout5.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout5.setBackgroundColor(Color.WHITE);
        linearLayout5.setPadding(ScaleUtil.dip2px(this, 7), 0,
                ScaleUtil.dip2px(this, 7), 0);

        TextView tv_phone = new TextView(this);
        LinearLayout.LayoutParams para_bt = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tv_phone.setLayoutParams(para_bt);
        tv_phone.setText("手机号"); // 设置文字 颜色 字体
        tv_phone.setTextSize(14);
        tv_phone.setTextColor(Color.BLACK);
        tv_phone.setHeight(ScaleUtil.dip2px(this, 50));
        tv_phone.setGravity(Gravity.CENTER_VERTICAL);

        TextView tv_phone_no = new TextView(this);
        LinearLayout.LayoutParams para_tv_phone = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        para_tv_phone.setMargins(ScaleUtil.dip2px(this, 7), 0,
                ScaleUtil.dip2px(this, 35), 0);
        tv_phone_no.setPadding(20, 0, 0, 0);
        tv_phone_no.setLayoutParams(para_tv_phone);
        tv_phone_no.setText(card_phone[0]); // 设置文字 颜色 字体
        tv_phone_no.setTextSize(14);
        tv_phone_no.setTextColor(Color.BLACK);
        tv_phone_no.setHeight(ScaleUtil.dip2px(this, 50));
        tv_phone_no.setGravity(Gravity.CENTER_VERTICAL);

        // 添加创建分割线6
        TextView mDiv_Line6 = new TextView(this);
        mDiv_Line6.setHeight(ScaleUtil.dip2px(this, 0.75f));
        mDiv_Line6.setBackgroundColor(Color.parseColor("#D1D3D5"));

        linearLayout5.addView(tv_phone);
        linearLayout5.addView(tv_phone_no);
        mLL_ready_to_pay.addView(linearLayout5);
        mLL_ready_to_pay.addView(mDiv_Line6);

        // 第6--输入验证码- 行: 水平布局
        // 定义宽高和布局方向____________________________________________________________
        LinearLayout linearLayout6 = new LinearLayout(this);// 1: 商户名称水平布局
        // 定义宽高和布局方向
        linearLayout6.setLayoutParams(para_stripe);
        linearLayout6.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout6.setBackgroundColor(Color.WHITE);
        TextView tv_captchas = new TextView(this);
        tv_captchas.setLayoutParams(para_bt);
        tv_captchas.setText("验证码"); // 设置文字 颜色 字体
        tv_captchas.setPadding(ScaleUtil.dip2px(this, 7), 0, 0, 0);
        tv_captchas.setTextSize(14);
        tv_captchas.setTextColor(Color.BLACK);
        tv_captchas.setGravity(Gravity.CENTER_VERTICAL);

        et_captchas = new EditText(this);
        LinearLayout.LayoutParams para_et = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        et_captchas.setLayoutParams(para_et);
        para_et.setMargins(ScaleUtil.dip2px(this, 7), 0,
                ScaleUtil.dip2px(this, 35), 0);
        et_captchas.setHint("输入验证码"); // 设置文字 颜色 字体
        et_captchas.setWidth(mWidth * 3 / 8);
        et_captchas.setTextSize(14);
        et_captchas
                .setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        et_captchas.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_captchas.setTextColor(Color.BLACK);
        et_captchas.setHeight(ScaleUtil.dip2px(this, 50));
        et_captchas.setBackgroundResource(0);
        et_captchas.setPadding(20, 0, 0, 0);
        et_captchas.setGravity(Gravity.CENTER_VERTICAL);
        et_captchas.addTextChangedListener(checkCodeWatcher);

        TextView tv_cap_div = new TextView(this);
        LinearLayout.LayoutParams para_divhor = new LinearLayout.LayoutParams(
                ScaleUtil.dip2px(this, 2), LayoutParams.WRAP_CONTENT);
        para_divhor.setMargins(ScaleUtil.dip2px(this, 10), 0,
                ScaleUtil.dip2px(this, 10), 0);
        tv_cap_div.setLayoutParams(para_divhor);
        tv_cap_div.setText("|"); // 设置文字 颜色 字体
        tv_cap_div.setTextSize(16);
        tv_cap_div.setTextColor(Color.GRAY);
        tv_cap_div.setGravity(Gravity.CENTER_VERTICAL);

        bt_get_captchas = new Button(this);
        LinearLayout.LayoutParams para_bt_get_captchas = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        bt_get_captchas.setLayoutParams(para_bt_get_captchas);
        bt_get_captchas.setText("获取验证码"); // 设置文字 颜色 字体
        bt_get_captchas.setWidth(mWidth * 3 / 8);
        bt_get_captchas.setTextColor(Color.parseColor("#2575CE"));
        bt_get_captchas.setTextSize(11);
        time = new TimeCount(60000, 1000);

        bt_get_captchas.setBackgroundColor(Color.WHITE);
        bt_get_captchas.setHeight(ScaleUtil.dip2px(this, 50));
        bt_get_captchas.setGravity(Gravity.CENTER);

        bt_get_captchas.setOnClickListener(this);
        // 添加创建分割线7
        TextView mDiv_Line7 = new TextView(this);
        mDiv_Line7.setHeight(ScaleUtil.dip2px(this, 0.75f));
        mDiv_Line7.setBackgroundColor(Color.parseColor("#D1D3D5"));

        linearLayout6.addView(tv_captchas);
        linearLayout6.addView(et_captchas);
        linearLayout6.addView(tv_cap_div);
        linearLayout6.addView(bt_get_captchas);
        mLL_ready_to_pay.addView(linearLayout6);
        mLL_ready_to_pay.addView(mDiv_Line7);

        // 确认支付按钮
        btn_bind_card = new Button(this);
        LinearLayout.LayoutParams para_bt_bind_card = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, ScaleUtil.dip2px(this, 43));
        para_bt_bind_card.gravity = Gravity.CENTER_HORIZONTAL;
        para_bt_bind_card.setMargins(ScaleUtil.dip2px(this, 20),
                ScaleUtil.dip2px(this, 20), ScaleUtil.dip2px(this, 20), 0);
        btn_bind_card.setLayoutParams(para_bt_bind_card);

        btn_bind_card.setBackgroundColor(Color.parseColor("#B3CDE9"));
        btn_bind_card.setText("确认支付");
        btn_bind_card.setTextSize(14);
        btn_bind_card.setTextColor(Color.WHITE);
        btn_bind_card.setOnClickListener(this);
        mLL_ready_to_pay.addView(btn_bind_card);

        tv_sms_not_received = new TextView(this);
        LinearLayout.LayoutParams para_tv_sms_not_received = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, ScaleUtil.dip2px(this, 30));
        para_tv_sms_not_received
                .setMargins(0, ScaleUtil.dip2px(this, 10), 0, 0);
        para_tv_sms_not_received.gravity = Gravity.CENTER_HORIZONTAL;
        tv_sms_not_received.setOnClickListener(this);
        tv_sms_not_received.setLayoutParams(para_tv_sms_not_received);
        tv_sms_not_received.setText("收不到验证码？");
        tv_sms_not_received.setTextSize(10);
        tv_sms_not_received.setTextColor(Color.parseColor("#2575CE"));
        mLL_ready_to_pay.addView(tv_sms_not_received);

        // 下方布局
        RelativeLayout rl_bottom_info = new RelativeLayout(this);
        RelativeLayout.LayoutParams para_rl_bot_info = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        int info2_id = ViewID.generateViewId();
        int info3_id = ViewID.generateViewId();
        TextView tv_info1 = createTextView();
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.ABOVE, info2_id);
        lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        tv_info1.setText("融宝支付是央行颁发支付牌照的第三方支付公司。");

        TextView tv_info2 = createTextView();
        tv_info2.setId(info2_id);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ABOVE, info3_id);
        lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        tv_info2.setText("融宝收银台通过短信验证码、指纹收集等多重手段确保支付安全。");

        TextView tv_info3 = createTextView();
        tv_info3.setId(info3_id);
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp3.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        tv_info3.setText("Copyright©2015融宝支付");
        tv_info3.setPadding(0, 0, 0, ScaleUtil.dip2px(this, 5));

        rl_bottom_info.addView(tv_info1, lp1);
        rl_bottom_info.addView(tv_info2, lp2);
        rl_bottom_info.addView(tv_info3, lp3);
        mLL_ready_to_pay.addView(rl_bottom_info, para_rl_bot_info);

        setContentView(mLL_ready_to_pay);
    }

    */
/**
     * 生成TextView
     *//*

    public TextView createTextView() {
        TextView tv_info = new TextView(this);
        tv_info.setTextColor(Color.parseColor("#919395"));
        tv_info.setTextSize(8);
        return tv_info;
    }


    private SpannableStringBuilder setItemText(int i, boolean same) {
        SpannableStringBuilder ss = null;
        if (same) {
            ss = new SpannableStringBuilder(card_bank_name[i] + "（尾号"
                    + card_last[i] + "）" + card_type[i]);
        } else {
            if (card_bank_name[i] == null) {
                card_bank_name[i] = " ";
            }
            ss = new SpannableStringBuilder(card_bank_name[i]);
            SpannableStringBuilder ss1 = new SpannableStringBuilder("（尾号"
                    + card_last[i] + "）" + card_type[i]);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ss.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss1.setSpan(new ForegroundColorSpan(Color.GRAY), 0, ss1.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.append(ss1);
        }
        return ss;
    }

    @Override
    public void onClick(View v) {
        if (v == iv_backArrow) {
            finish();
        } else if (v == ll_change) {
            showCardListDialog();
        } else if (v == bt_get_captchas) { // 重新获取验证码
            if (NetworkTypeUtil.getNetworkType(this) == NetworkTypeUtil.NO_NETWORK) {
                WarningUtil.networkAnomaly(this);
                return;
            }

            if (times_sent_sms == 0) {
                captchas_got = true;
                time.start();
                sendSms();
            } else if (times_sent_sms < 3) { // 判断短信发送次数
                time.start();
                reSendSms();
            } else {
                Toast.makeText(this, "短信发送次数超限", Toast.LENGTH_SHORT).show();
                et_captchas.setText("");
            }
        } else if (v == btn_bind_card) {

            if (!captchas_got) {
                Toast.makeText(this, "请点击获取验证码", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(et_captchas.getText())
                    || et_captchas.getText().length() < 6) {
                Toast.makeText(this, "请输入短信验证码", Toast.LENGTH_SHORT).show();
            } else {
                DialogUtil.showProgressDialog(this, false);
                // 确认支付方法
                confermPay();
            }
        } else if (v == footerView) {
            Intent intent_org = getIntent();
            intent_org.setClass(BindCardListActivity.this,
                    CardNoCheckActivity.class);
            LogUtil.e(BIND_CARD_LIST_TAG + "onClick() 跳转绑卡界面|" + orderInfo.getMember_id());
            startActivityForResult(intent_org, PayTask.REQUEST_CODE);
        } else if (v == iv_downArrow) {
            if (linearLayout1.getVisibility() == View.VISIBLE) {
                linearLayout1.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.GONE);
                ll_div1.setVisibility(View.GONE);
                mDiv_Line2.setVisibility(View.GONE);
                iv_downArrow.setImageDrawable(ReapalUtil.getResDrawable(
                        BindCardListActivity.this, "downArrow.png"));
            } else {
                linearLayout1.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
                ll_div1.setVisibility(View.VISIBLE);
                mDiv_Line2.setVisibility(View.VISIBLE);
                iv_downArrow.setImageDrawable(ReapalUtil.getResDrawable(
                        BindCardListActivity.this, "upArrow.png"));

            }
        } else if (v == headerView) {
            dialog.dismiss();
        } else if (v == tv_sms_not_received) {
            String title = "收不到验证码?";
            String tips = "请按下面的步骤依次确认解决问题 \n①请确认该预留手机号是否为当前使用手机号；\n②请查看短信是否被安全软件拦截；\n③"
                    + "由于运营商网路原因，可能存在短信延迟，请耐心等待或稍后再试；\n④若银行预留手机号已停用，请联系银行客服处理；\n⑤获取更多帮助，可联系融宝支付客服400-022-6816。";

            DialogUtil.showHintDialog(BindCardListActivity.this, title, tips);
        }

    }

    private void showCardListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                BindCardListActivity.this);
        LinearLayout mLL_change_card = new LinearLayout(this);
        mLL_change_card.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mLL_change_card.setOrientation(LinearLayout.VERTICAL);
        mLL_change_card.setBackgroundColor(Color.parseColor("#ffffff"));
        ListView listview = new ListView(this);

        footerView = new TextView(BindCardListActivity.this);
        footerView.setTextSize(14);
        footerView.setTextColor(Color.parseColor("#2575CE"));
        footerView.setPadding(20, 0, 20, 0);
        footerView.setHeight(ScaleUtil.dip2px(BindCardListActivity.this, 50));
        footerView.setWidth(LayoutParams.MATCH_PARENT);
        footerView.setGravity(Gravity.CENTER_VERTICAL);// |Gravity.LEFT);
        footerView.setText("╋  其他银行卡支付");
        footerView.setOnClickListener(this);

        LinearLayout ll_header = new LinearLayout(this);
        LayoutParams para_stripe = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ll_header.setLayoutParams(para_stripe);
        ll_header.setOrientation(LinearLayout.HORIZONTAL);
        ll_header.setBackgroundColor(Color.WHITE);
        ll_header.setPadding(ScaleUtil.dip2px(this, 7), 0, 0, 0);

        TextView headerView0 = new TextView(this);
        headerView0.setTextSize(15);
        headerView0.setTextColor(Color.parseColor("#2575CE"));
        headerView0.setHeight(ScaleUtil.dip2px(this, 50));
        headerView0.setWidth(mWidth * 3 / 4);
        headerView0.setGravity(Gravity.CENTER);// |Gravity.LEFT);
        headerView0.setText("请选择支付卡");
        headerView0.setOnClickListener(this);

        headerView = new TextView(this);
        headerView.setTextSize(12);
        headerView.setTextColor(Color.parseColor("#2575CE"));
        headerView.setHeight(ScaleUtil.dip2px(this, 40));
        headerView.setWidth(mWidth / 10);
        headerView.setGravity(Gravity.CENTER_VERTICAL);// |Gravity.LEFT);
        headerView.setText("╳");
        TextPaint tp = headerView.getPaint();
        tp.setFakeBoldText(true);
        headerView.setOnClickListener(this);

        ll_header.addView(headerView0);
        ll_header.addView(headerView);
        listview.addFooterView(footerView);
        listview.addHeaderView(ll_header);
        mLL_change_card.addView(listview);
        builder.setView(mLL_change_card);
        listview.setAdapter(new MyAdapter());

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selected_position = position - 1;
                tv_chosen_card.setText(setItemText(position - 1, false)); // 显示更换后的卡号信息
                // 设置银行logo
                bankLogo.setImageDrawable(ReapalUtil
                        .getResBankDrawable(BindCardListActivity.this,
                                card_bank_name[position - 1]));
                if (dialog != null) {
                    dialog.dismiss();
                }
                time.cancel();
                bt_get_captchas.setClickable(true);
                bt_get_captchas.setText("获取验证码");
                times_sent_sms = 0;
            }
        });
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show(); // 选择完毕
    }

    class MyAdapter extends BaseAdapter {
        private String s_item;

        @Override
        public int getCount() {
            return bind_card_list.size();
        }

        @Override
        public Object getItem(int position) {
            LogUtil.e(BIND_CARD_LIST_TAG + "getItem() position = " + position);
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout linearLayout = new LinearLayout(
                    BindCardListActivity.this);// 1: 水平布局 定义条目宽高和布局方向

            LayoutParams para_item = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(para_item);
            linearLayout.setBackgroundColor(Color.WHITE);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setPadding(
                    ScaleUtil.dip2px(BindCardListActivity.this, 10), 0,
                    ScaleUtil.dip2px(BindCardListActivity.this, 10), 0);
            // 银行图标
            ImageView bankLogo = new ImageView(BindCardListActivity.this);
            bankLogo.setLayoutParams(new LayoutParams(ScaleUtil
                    .dip2px(BindCardListActivity.this, 23), ScaleUtil.dip2px(
                    BindCardListActivity.this, 23)));
            bankLogo.setImageDrawable(ReapalUtil.getResBankDrawable(
                    BindCardListActivity.this, card_bank_name[position]));
            bankLogo.setPadding(0, 0, 10, 0);
            // 银行卡信息
            TextView textView = new TextView(BindCardListActivity.this);
            textView.setTextSize(14);
            textView.setTextColor(Color.BLACK);
            textView.setHeight(ScaleUtil.dip2px(BindCardListActivity.this, 50));
            textView.setWidth(mWidth * 13 / 20);
            textView.setGravity(Gravity.CENTER_VERTICAL);

            s_item = setItemText(position, true).toString();
            LogUtil.e(BIND_CARD_LIST_TAG + "getView() 初始化listview条目 设置卡");
            textView.setText(s_item);
            // 右侧箭头
            ImageView iv_pay_arrow = new ImageView(BindCardListActivity.this);
            iv_pay_arrow.setLayoutParams(new LayoutParams(ScaleUtil
                    .dip2px(BindCardListActivity.this, 20), ScaleUtil.dip2px(
                    BindCardListActivity.this, 18)));
            iv_pay_arrow.setImageDrawable(ReapalUtil.getResDrawable(
                    BindCardListActivity.this, "payarrow.png"));

            linearLayout.addView(bankLogo);
            linearLayout.addView(textView);
            linearLayout.addView(iv_pay_arrow);

            return linearLayout;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // 创建计时器类
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            bt_get_captchas.setText("获取验证码");
            bt_get_captchas.setTextSize(12);
            bt_get_captchas.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            bt_get_captchas.setClickable(false);
            bt_get_captchas.setText("(" + millisUntilFinished / 1000 + ")秒后重发");
            bt_get_captchas.setTextSize(12);
        }
    }

    */
/**
     * 通过验证码快捷 确认支付的方法
     *//*

    private void confermPay() {
        if (NetworkTypeUtil.getNetworkType(this) == NetworkTypeUtil.NO_NETWORK) {
            DialogUtil.hideProgressDialog();
            WarningUtil.networkAnomaly(this);
            return;
        }
        ReapalThreadPoolFactory.getInstance().executeRequest(new Runnable() {
            private Map<String, Object> resultMap;

            @Override
            public void run() {
                Map<String, String> map = new HashMap<>();
                map.put("merchant_id", ReapalConfig.merchant_id);
                map.put("version", ReapalConfig.newVersion);
                map.put("order_no", orderInfo.getOrder_no());
                map.put("check_code", et_captchas.getText().toString().trim()); // 6位
                // 请求接口
                String url = "/fast/pay";
                // 返回结果
                String post;
                try {
                    LogUtil.e(BIND_CARD_LIST_TAG + "confermPay() 确认支付 " + map.toString());
                    post = ReapalSubmit.buildSubmit(BindCardListActivity.this, map, url);
                    LogUtil.e(BIND_CARD_LIST_TAG + "confermPay() 返回结果post = " + post);
                    // 解密返回的数据
                    JSONObject json1 = new JSONObject(post);
                    String encrypt_key = json1.getString("encryptkey");
                    String data = json1.getString("data");
                    String res = SecurityUtil.decryptData(data, encrypt_key, ReapalConfig.privateKey);
                    LogUtil.e(BIND_CARD_LIST_TAG + "confermPay() 解密返回的数据: " + res);
                    resultMap = JSON.parseObject(res);

                    Message msg = Message.obtain();
                    String resultCode = resultMap.get("result_code").toString();
                    if ("0000".equals(resultCode)) {
                        msg.what = PAY_SUCCESS_CODE;
                    } else if ("3083".equals(resultCode)) {
                        Map<String, String> queryMaps = new HashMap<>();
                        queryMaps.put("merchant_id", ReapalConfig.merchant_id);
                        queryMaps.put("version", ReapalConfig.version);
                        queryMaps.put("order_no", orderInfo.getOrder_no());
                        queryMaps.put("sign_type", ReapalConfig.signType);
                        // 请求接口
                        String queryUrl = "/fast/search";
                        for (int i = 0; i < 5; i++) {
                            String queryResponse = null;
                            try {
                                queryResponse = ReapalSubmit.buildSubmit(BindCardListActivity.this, queryMaps, queryUrl);
                                LogUtil.e(BIND_CARD_LIST_TAG + "confermPay() 支付处理中查询返回:post=" + queryResponse);
                                // 解密返回的数据
                                JSONObject jsonObject = new JSONObject(queryResponse);
                                String decryptResponse = SecurityUtil.decryptData(jsonObject.getString("data"), jsonObject.getString("encryptkey"),
                                        ReapalConfig.privateKey);
                                LogUtil.e(BIND_CARD_LIST_TAG + "confermPay() 解密返回的数据 = " + decryptResponse);
                                resultMap = JSON.parseObject(decryptResponse);
                                String payResultCode = resultMap.get("result_code").toString();

                                if (i != 4) {
                                    String status = resultMap.get("status")
                                            .toString();
                                    if (!"0000".equals(payResultCode) || !"completed".equals(status)) {
                                        Thread.sleep(2000);
                                    } else {
                                        msg.what = PAY_SUCCESS_CODE;
                                        break;
                                    }
                                } else {
                                    //查询结束
                                    if (!"0000".equals(payResultCode)) {
                                        String result_msg = resultMap.get("result_msg")
                                                .toString();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("result_msg", result_msg);
                                        bundle.putString("result_code", resultCode);
                                        msg.setData(bundle);
                                        msg.what = FAIL_CODE;
                                    } else {
                                        String status = resultMap.get("status")
                                                .toString();
                                        if ("completed".equals(status)) {
                                            msg.what = PAY_SUCCESS_CODE;
                                        } else if ("processing".equals(status)) {
                                            msg.what = FAIL_CODE;
                                        } else {
                                            msg.what = FAIL_CODE;
                                        }
                                    }
                                }

                            } catch (Exception e) {
                                if (i != 4) {
                                    Thread.sleep(2000);
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("result_msg", "查询支付异常");
                                    bundle.putString("result_code", "3333");
                                    msg.setData(bundle);
                                    msg.what = FAIL_CODE;
                                }
                            }

                        }
                    } else {
                        String result_msg = resultMap.get("result_msg")
                                .toString();
                        Bundle bundle = new Bundle();
                        bundle.putString("result_msg", result_msg);
                        bundle.putString("result_code", resultCode);
                        msg.setData(bundle);
                        msg.what = FAIL_CODE;
                    }
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    DialogUtil.hideProgressDialog();
                }
            }
        });
    }

    */
/**
     * 点击发送短信按钮的发短信方法
     *//*

    private void sendSms() {
        times_sent_sms++;
        et_captchas.setText("");
        ReapalThreadPoolFactory.getInstance().executeRequest(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map_bindcard = new HashMap<>();
                map_bindcard.put("merchant_id", ReapalConfig.merchant_id);
                map_bindcard.put("version", ReapalConfig.version);
                map_bindcard.put("member_id", orderInfo.getMember_id());
                LogUtil.e(BIND_CARD_LIST_TAG + "sendSms() " + orderInfo.getMember_id());
                map_bindcard.put("bind_id", card_bind_id[selected_position]);
                map_bindcard.put("order_no", orderInfo.getOrder_no());
                map_bindcard.put("transtime", new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss").format(new Date()));
                map_bindcard.put("currency", orderInfo.getCurrency()); // 默认
                map_bindcard.put("title", orderInfo.getTitle());
                map_bindcard.put("body", orderInfo.getBody());
                map_bindcard.put("terminal_type", orderInfo.getTerminal_type());// 终端类型
                map_bindcard.put("terminal_info", orderInfo.getTerminal_info()); // 终端标识
                map_bindcard.put("notify_url", orderInfo.getNotify_url());
                map_bindcard.put("member_ip", orderInfo.getMember_ip());// orderInfo.getMember_ip());//用户IP
                map_bindcard.put("seller_email", orderInfo.getSeller_email());
                // 金额
                map_bindcard.put("total_fee",
                        ReapalUtil.yuan2Fen(orderInfo.getTotal_fee()));
                map_bindcard.put("token_id", ReapalUtil.getUUID());
                // 请求接口
                String url = "/fast/bindcard/portal";
                // 返回的数据
                String post;
                LogUtil.e(BIND_CARD_LIST_TAG + "sendSms() 请求参数 = " + map_bindcard.toString());
                try {
                    post = ReapalSubmit.buildSubmit(BindCardListActivity.this, map_bindcard, url);
                    // 解密返回的数据
                    JSONObject json = new JSONObject(post);
                    String encrypt_key = json.getString("encryptkey");
                    String data = json.getString("data");
                    String res = SecurityUtil.decryptData(data, encrypt_key, ReapalConfig.privateKey);
                    LogUtil.e(BIND_CARD_LIST_TAG + "sendSms() 绑卡签约解密返回的数据 = " + res);

                    Map<String, Object> sms_sent_resultMap = JSON
                            .parseObject(res);
                    Message msg = Message.obtain();
                    if (sms_sent_resultMap.get("result_code")
                            .equals("0000")) {
                        msg.what = SEND_SMS_SUCCESS_CODE;// 短信发送成功
                    } else {
                        String result_msg = sms_sent_resultMap
                                .get("result_msg").toString();
                        Bundle bundle = new Bundle();
                        bundle.putString("result_msg", result_msg);
                        msg.setData(bundle);
                        msg.what = FAIL_CODE;
                    }
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    */
/**
     * 点击发送短信按钮的发短信方法
     *//*

    private void reSendSms() {
        times_sent_sms++;
        et_captchas.setText("");
        ReapalThreadPoolFactory.getInstance().executeRequest(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map_bindcard = new HashMap<>();
                map_bindcard.put("merchant_id", ReapalConfig.merchant_id);
                map_bindcard.put("order_no", orderInfo.getOrder_no());
                map_bindcard.put("version", ReapalConfig.newVersion);
                // 请求接口
                String url = "/fast/sms";
                // 返回的数据
                String post;
                LogUtil.e(BIND_CARD_LIST_TAG + "reSendSms() 重发短信请求参数：" + map_bindcard.toString());
                try {
                    post = ReapalSubmit.buildSubmit(BindCardListActivity.this, map_bindcard, url);
                    // 解密返回的数据
                    JSONObject json1 = new JSONObject(post);
                    String encrypt_key = json1.getString("encryptkey");
                    String data = json1.getString("data");
                    String res = SecurityUtil.decryptData(data, encrypt_key, ReapalConfig.privateKey);
                    LogUtil.e(BIND_CARD_LIST_TAG + "reSendSms() 重发解密返回的数据 = " + res);

                    Map<String, Object> sms_sent_resultMap = JSON
                            .parseObject(res);
                    Message msg = Message.obtain();
                    if (sms_sent_resultMap.get("result_code")
                            .equals("0000")) {
                        msg.what = SEND_SMS_SUCCESS_CODE;// 短信发送成功--跳转
                    } else {
                        String result_msg = sms_sent_resultMap
                                .get("result_msg").toString();
                        Bundle bundle = new Bundle();
                        bundle.putString("result_msg", result_msg);
                        msg.setData(bundle);
                        msg.what = FAIL_CODE;
                    }
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 验证码输入监听
    private TextWatcher checkCodeWatcher = new TextWatcher() {// 监听输入的内容
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            btn_bind_card.setBackgroundColor(TextUtils.isEmpty(s) ? Color
                    .parseColor("#B3CDE9") : Color.parseColor("#398DEE"));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PayTask.RESULT_SUCCESS) {
            setResult(resultCode, data);
            finish();
        }
    }

}
*/
