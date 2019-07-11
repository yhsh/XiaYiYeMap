package cn.xiayiye.customerlayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private LinearLayout linearLayout1;
    private int mWidth;
    private LinearLayout ll_div1;
    private LinearLayout linearLayout2;
    private TextView mDiv_Line2;
    private TextView tv_chosen_card;
    private LinearLayout ll_change;
    private EditText et_captchas;
    private Button btn_bind_card;
    private Button bt_get_captchas;
    private TextView tv_sms_not_received;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mWidth = DisplaySizeUtil.getDisplaySizeInfo(this).x;
        // 根布局及其参数 定义宽高和布局方向等
        LinearLayout mLL_ready_to_pay = new LinearLayout(this);
        mLL_ready_to_pay.setLayoutParams(new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
        mLL_ready_to_pay.setOrientation(LinearLayout.VERTICAL);
        mLL_ready_to_pay.setBackgroundColor(Color.parseColor("#F1F3F5"));
        LinearLayout.LayoutParams para_top = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, ScaleUtil.dip2px(this, 50));
        LinearLayout linearLayout0 = new LinearLayout(this);// 1: 商户名称水平布局
        // 定义宽高和布局方向
        linearLayout0.setLayoutParams(para_top);
        linearLayout0.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout0.setBackgroundColor(Color.parseColor("#323236"));
        linearLayout0.setGravity(Gravity.CENTER_VERTICAL);


        // 条目1 商户名称
        linearLayout1 = new LinearLayout(this);
        LinearLayout.LayoutParams para_stripe = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        linearLayout1.setLayoutParams(para_stripe);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setBackgroundColor(Color.WHITE);
        linearLayout1.setPadding(ScaleUtil.dip2px(this, 10), 0, 0, 0);
        linearLayout1.setVisibility(View.GONE);
        // 子嵌套控件的宽高参数
        LinearLayout.LayoutParams para_discendant = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
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
        tv_Merchant_name.setText("测试数据"); // 设置文字
        tv_Merchant_name.setTextSize(14);
        tv_Merchant_name.setTextColor(Color.BLACK);
        tv_Merchant_name.setWidth(mWidth * 31 / 40);
        tv_Merchant_name.setHeight(ScaleUtil.dip2px(this, 40));
        tv_Merchant_name.setGravity(Gravity.CENTER_VERTICAL);
        // 更新商户名称
        tv_Merchant_name.setText("更新商户名称");

        ll_div1 = new LinearLayout(this);
        LinearLayout.LayoutParams para_div = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
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
        tv_order_no.setText("订单号：2018年3月25日11:01:11");
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
        tv_total_fee.setText("￥ 200"); // 设置文字 ??
        tv_total_fee.setTextSize(16);
        tv_total_fee.setTextColor(Color.RED);
        tv_total_fee.setWidth(mWidth * 25 / 40);
        tv_total_fee.setHeight(ScaleUtil.dip2px(this, 50));
        tv_total_fee.setGravity(Gravity.CENTER_VERTICAL);

        // 添加创建分割线3
        TextView mDiv_Line3 = new TextView(this);
        mDiv_Line3.setHeight(ScaleUtil.dip2px(this, 0.75f));
        mDiv_Line3.setBackgroundColor(Color.parseColor("#D1D3D5"));

        linearLayout3.addView(tv_fee_total); // 在父类布局中添加第一行，及布局样式
        linearLayout3.addView(tv_total_fee);
        mLL_ready_to_pay.addView(linearLayout3);
        mLL_ready_to_pay.addView(mDiv_Line3);

        // 间隔带
        LinearLayout.LayoutParams para_chooseMethod = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, ScaleUtil.dip2px(this, 10));
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
        tv_chosen_card = new TextView(this);
        LinearLayout.LayoutParams para_tv = new LinearLayout.LayoutParams(
                mWidth * 27 / 40, AbsListView.LayoutParams.WRAP_CONTENT);
        tv_chosen_card.setLayoutParams(para_tv);
        // 设置文字 颜色 字体
        tv_chosen_card.setTextSize(14);
        tv_chosen_card.setTextColor(Color.BLACK);
        tv_chosen_card.setHeight(ScaleUtil.dip2px(this, 50));
        tv_chosen_card.setGravity(Gravity.CENTER_VERTICAL);

        ll_change = new LinearLayout(this);
        LinearLayout.LayoutParams para_change = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        para_change.gravity = Gravity.CENTER_VERTICAL;
        para_change.setMargins(ScaleUtil.dip2px(this, 10),
                ScaleUtil.dip2px(this, 6), 0, ScaleUtil.dip2px(this, 5));
        ll_change.setLayoutParams(para_change);
        ll_change.setOrientation(LinearLayout.HORIZONTAL);
        ll_change.setGravity(Gravity.CENTER);

        TextView tv_change_card = new TextView(this);
        LinearLayout.LayoutParams para_btn = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        para_btn.setMargins(ScaleUtil.dip2px(this, 5), 0, 0, 0);
        tv_change_card.setLayoutParams(para_btn);
        tv_change_card.setText("更换"); // 设置文字 颜色 字体
        tv_change_card.setTextSize(12);
        tv_change_card.setTextColor(Color.parseColor("#2575CE"));
        tv_change_card.setBackgroundColor(Color.WHITE);
        tv_change_card.setHeight(ScaleUtil.dip2px(this, 50));
        tv_change_card.setGravity(Gravity.CENTER);

        ll_change.addView(tv_change_card);
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
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        tv_phone.setLayoutParams(para_bt);
        tv_phone.setText("手机号"); // 设置文字 颜色 字体
        tv_phone.setTextSize(14);
        tv_phone.setTextColor(Color.BLACK);
        tv_phone.setHeight(ScaleUtil.dip2px(this, 50));
        tv_phone.setGravity(Gravity.CENTER_VERTICAL);

        TextView tv_phone_no = new TextView(this);
        LinearLayout.LayoutParams para_tv_phone = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        para_tv_phone.setMargins(ScaleUtil.dip2px(this, 7), 0,
                ScaleUtil.dip2px(this, 35), 0);
        tv_phone_no.setPadding(20, 0, 0, 0);
        tv_phone_no.setLayoutParams(para_tv_phone);
        tv_phone_no.setText("13343401268"); // 设置文字 颜色 字体
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
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
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
                ScaleUtil.dip2px(this, 2), AbsListView.LayoutParams.WRAP_CONTENT);
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
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
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
                ScaleUtil.dip2px(this, 2), AbsListView.LayoutParams.WRAP_CONTENT);
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
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
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
                ScaleUtil.dip2px(this, 2), AbsListView.LayoutParams.WRAP_CONTENT);
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
                AbsListView.LayoutParams.MATCH_PARENT, ScaleUtil.dip2px(this, 43));
        para_bt_bind_card.gravity = Gravity.CENTER_HORIZONTAL;
        para_bt_bind_card.setMargins(ScaleUtil.dip2px(this, 20),
                ScaleUtil.dip2px(this, 20), ScaleUtil.dip2px(this, 20), 0);
        btn_bind_card.setLayoutParams(para_bt_bind_card);

        btn_bind_card.setBackgroundColor(Color.parseColor("#B3CDE9"));
        btn_bind_card.setText("确认支付");
        btn_bind_card.setTextSize(14);
        btn_bind_card.setTextColor(Color.WHITE);
        mLL_ready_to_pay.addView(btn_bind_card);
        btn_bind_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转页面
//                startActivity(new Intent(MainActivity.this, layoutActivity.class));
                startActivity(new Intent(MainActivity.this, BigLayout.class));
            }
        });
        tv_sms_not_received = new TextView(this);
        LinearLayout.LayoutParams para_tv_sms_not_received = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, ScaleUtil.dip2px(this, 30));
        para_tv_sms_not_received
                .setMargins(0, ScaleUtil.dip2px(this, 10), 0, 0);
        para_tv_sms_not_received.gravity = Gravity.CENTER_HORIZONTAL;
        tv_sms_not_received.setLayoutParams(para_tv_sms_not_received);
        tv_sms_not_received.setText("收不到验证码？");
        tv_sms_not_received.setTextSize(10);
        tv_sms_not_received.setTextColor(Color.parseColor("#2575CE"));
        mLL_ready_to_pay.addView(tv_sms_not_received);

        // 下方布局
        RelativeLayout rl_bottom_info = new RelativeLayout(this);
        RelativeLayout.LayoutParams para_rl_bot_info = new RelativeLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);

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

    /**
     * 生成TextView
     */
    public TextView createTextView() {
        TextView tv_info = new TextView(this);
        tv_info.setTextColor(Color.parseColor("#919395"));
        tv_info.setTextSize(8);
        return tv_info;
    }
}
