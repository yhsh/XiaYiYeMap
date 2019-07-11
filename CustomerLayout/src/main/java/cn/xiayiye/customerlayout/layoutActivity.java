package cn.xiayiye.customerlayout;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
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

import static cn.xiayiye.customerlayout.ScaleUtil.dip2px;

public class layoutActivity extends Activity implements View.OnClickListener {

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
    private EditText input_et_year;
    private ImageView iv_delete_number_year;
    private EditText input_et_cvv2;
    private ImageView iv_delete_number;
    private EditText input_et_sfz;
    private ImageView iv_delete_number_sfz;
    private TextView footerView;
    private ImageView iv_about_cvv2;
    private ImageView iv_about_yxq;
    private TextView conferm_ok;
    private AlertDialog dialog_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
                AbsListView.LayoutParams.MATCH_PARENT, dip2px(this, 50));
        LinearLayout linearLayout0 = new LinearLayout(this);// 1: 商户名称水平布局
        // 定义宽高和布局方向
        linearLayout0.setLayoutParams(para_top);
        linearLayout0.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout0.setBackgroundColor(Color.parseColor("#323236"));
        linearLayout0.setGravity(Gravity.CENTER_VERTICAL);


        LinearLayout.LayoutParams para_top_title = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        TextView tv_top = new TextView(this); // 创建TextView对象
        tv_top.setLayoutParams(para_top_title);
        tv_top.setText("融宝收银台"); // 设置文字 颜色 字体
        tv_top.setTextSize(20);
        tv_top.setTextColor(Color.WHITE);
        tv_top.setHeight(dip2px(this, 50));
        tv_top.setPadding(0, 0, dip2px(this, 45), 0);
        tv_top.setGravity(Gravity.CENTER);

        linearLayout0.addView(tv_top);
        mLL_ready_to_pay.addView(linearLayout0);

        // 条目1 商户名称
        linearLayout1 = new LinearLayout(this);
        LinearLayout.LayoutParams para_stripe = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        linearLayout1.setLayoutParams(para_stripe);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setBackgroundColor(Color.WHITE);
        linearLayout1.setPadding(dip2px(this, 10), 0, 0, 0);
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
        tv_name_merchant.setHeight(dip2px(this, 40));
        tv_name_merchant.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

        TextView tv_Merchant_name = new TextView(this);
        tv_Merchant_name.setLayoutParams(para_discendant);
        tv_Merchant_name.setText("nap测试"); // 设置文字
        tv_Merchant_name.setTextSize(14);
        tv_Merchant_name.setTextColor(Color.BLACK);
        tv_Merchant_name.setWidth(mWidth * 31 / 40);
        tv_Merchant_name.setHeight(dip2px(this, 40));
        tv_Merchant_name.setGravity(Gravity.CENTER_VERTICAL);
        // 更新商户名称
        tv_Merchant_name.setText("map标题");

        ll_div1 = new LinearLayout(this);
        LinearLayout.LayoutParams para_div = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        ll_div1.setLayoutParams(para_div);
        ll_div1.setOrientation(LinearLayout.HORIZONTAL);
        ll_div1.setBackgroundColor(Color.WHITE);
        ll_div1.setPadding(0, 0, 0, 0);
        ll_div1.setVisibility(View.GONE);

        TextView mDiv_Line1 = new TextView(this);
        mDiv_Line1.setHeight(dip2px(this, 0.75F));
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
        linearLayout2.setPadding(dip2px(this, 10), 0, 0, 0);
        linearLayout2.setVisibility(View.GONE);

        TextView tv_no_order = new TextView(this); // 创建TextView对象
        tv_no_order.setLayoutParams(para_discendant);
        tv_no_order.setText("订单号"); // 设置文字 颜色 字体
        tv_no_order.setTextSize(14);
        tv_no_order.setTextColor(Color.BLACK);
        tv_no_order.setWidth(mWidth * 9 / 40);
        tv_no_order.setHeight(dip2px(this, 40));
        tv_no_order.setGravity(Gravity.CENTER_VERTICAL);

        TextView tv_order_no = new TextView(this); // 创建TextView对象
        tv_order_no.setLayoutParams(para_discendant);
        tv_order_no.setText("订单信息");
        // 设置文字
        tv_order_no.setTextSize(14);
        tv_order_no.setTextColor(Color.BLACK);
        tv_order_no.setWidth(mWidth * 31 / 40);
        tv_order_no.setHeight(dip2px(this, 40));
        tv_order_no.setGravity(Gravity.CENTER_VERTICAL);

        mDiv_Line2 = new TextView(this);
        mDiv_Line2.setHeight(dip2px(this, 0.75f));
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
        linearLayout3.setPadding(dip2px(this, 10), 0, 0, 0);
        TextView tv_fee_total = new TextView(this); // 创建TextView对象
        tv_fee_total.setLayoutParams(para_discendant);
        tv_fee_total.setText("应付金额"); // 设置文字 颜色 字体
        tv_fee_total.setTextSize(14);
        tv_fee_total.setTextColor(Color.BLACK);
        tv_fee_total.setWidth(mWidth * 9 / 40);
        tv_fee_total.setHeight(dip2px(this, 50));
        tv_fee_total.setGravity(Gravity.CENTER_VERTICAL);

        TextView tv_total_fee = new TextView(this); // 创建TextView对象
        tv_total_fee.setLayoutParams(para_discendant);
        // 将map中的分转换成元,保留两位小数
        TextPaint tp = tv_total_fee.getPaint();
        tp.setFakeBoldText(true);
        tv_total_fee.setText("￥200 "); // 设置文字 ??
        tv_total_fee.setTextSize(16);
        tv_total_fee.setTextColor(Color.RED);
        tv_total_fee.setWidth(mWidth * 25 / 40);
        tv_total_fee.setHeight(dip2px(this, 50));
        tv_total_fee.setGravity(Gravity.CENTER_VERTICAL);


        // 添加创建分割线3
        TextView mDiv_Line3 = new TextView(this);
        mDiv_Line3.setHeight(dip2px(this, 0.75f));
        mDiv_Line3.setBackgroundColor(Color.parseColor("#D1D3D5"));

        linearLayout3.addView(tv_fee_total); // 在父类布局中添加第一行，及布局样式
        linearLayout3.addView(tv_total_fee);
        mLL_ready_to_pay.addView(linearLayout3);
        mLL_ready_to_pay.addView(mDiv_Line3);

        // 间隔带
        LinearLayout.LayoutParams para_chooseMethod = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, dip2px(this, 10));
        TextView chooseMethod = new TextView(this);
        chooseMethod.setPadding(dip2px(this, 10), 0, 0, 0);
        chooseMethod.setLayoutParams(para_chooseMethod);
        chooseMethod.setTextColor(Color.parseColor("#ABABAB"));
        chooseMethod.setBackgroundColor(Color.parseColor("#F3F3F3"));
        mLL_ready_to_pay.addView(chooseMethod);

        // 添加创建分割线4
        TextView mDiv_Line4 = new TextView(this);
        mDiv_Line4.setHeight(dip2px(this, 0.75f));
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
        linearLayout4.setPadding(dip2px(this, 10), 0,
                dip2px(this, 10), 0);
        // 添加银行标志 logo

        tv_chosen_card = new TextView(this);
        LinearLayout.LayoutParams para_tv = new LinearLayout.LayoutParams(
                mWidth * 27 / 40, AbsListView.LayoutParams.WRAP_CONTENT);
        tv_chosen_card.setLayoutParams(para_tv);
        // 设置文字 颜色 字体
        tv_chosen_card.setTextSize(14);
        tv_chosen_card.setTextColor(Color.BLACK);
        tv_chosen_card.setHeight(dip2px(this, 50));
        tv_chosen_card.setGravity(Gravity.CENTER_VERTICAL);

        ll_change = new LinearLayout(this);
        LinearLayout.LayoutParams para_change = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        para_change.gravity = Gravity.CENTER_VERTICAL;
        para_change.setMargins(dip2px(this, 10),
                dip2px(this, 6), 0, dip2px(this, 5));
        ll_change.setLayoutParams(para_change);
        ll_change.setOrientation(LinearLayout.HORIZONTAL);
        ll_change.setGravity(Gravity.CENTER);

        ImageView iv_change_icon = new ImageView(this);
        iv_change_icon.setLayoutParams(new AbsListView.LayoutParams(dip2px(this,
                20), dip2px(this, 18)));

        TextView tv_change_card = new TextView(this);
        LinearLayout.LayoutParams para_btn = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        para_btn.setMargins(dip2px(this, 5), 0, 0, 0);
        tv_change_card.setLayoutParams(para_btn);
        tv_change_card.setText("更换"); // 设置文字 颜色 字体
        tv_change_card.setTextSize(12);
        tv_change_card.setTextColor(Color.parseColor("#2575CE"));
        tv_change_card.setBackgroundColor(Color.WHITE);
        tv_change_card.setHeight(dip2px(this, 50));
        tv_change_card.setGravity(Gravity.CENTER);

        ll_change.addView(iv_change_icon);
        ll_change.addView(tv_change_card);
        linearLayout4.addView(tv_chosen_card);
        linearLayout4.addView(ll_change);
        mLL_ready_to_pay.addView(linearLayout4);
        // 添加创建分割线5
        TextView mDiv_Line5 = new TextView(this);
        mDiv_Line5.setHeight(dip2px(this, 0.75f));
        mDiv_Line5.setBackgroundColor(Color.parseColor("#D1D3D5"));
        mLL_ready_to_pay.addView(mDiv_Line5);

        // 第5--手机号 行: 商户名称水平布局
        // 定义宽高和布局方向____________________________________________________________
        LinearLayout linearLayout5 = new LinearLayout(this);// 1: 商户名称水平布局
        // 定义宽高和布局方向
        linearLayout5.setLayoutParams(para_stripe);
        linearLayout5.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout5.setBackgroundColor(Color.WHITE);
        linearLayout5.setPadding(dip2px(this, 7), 0,
                dip2px(this, 7), 0);

        TextView tv_phone = new TextView(this);
        LinearLayout.LayoutParams para_bt = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        tv_phone.setLayoutParams(para_bt);
        tv_phone.setText("手机号"); // 设置文字 颜色 字体
        tv_phone.setTextSize(14);
        tv_phone.setTextColor(Color.BLACK);
        tv_phone.setHeight(dip2px(this, 50));
        tv_phone.setGravity(Gravity.CENTER_VERTICAL);

        TextView tv_phone_no = new TextView(this);
        LinearLayout.LayoutParams para_tv_phone = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        para_tv_phone.setMargins(dip2px(this, 7), 0,
                dip2px(this, 35), 0);
        tv_phone_no.setPadding(20, 0, 0, 0);
        tv_phone_no.setLayoutParams(para_tv_phone);
        tv_phone_no.setText("卡号：4552825558855555"); // 设置文字 颜色 字体
        tv_phone_no.setTextSize(14);
        tv_phone_no.setTextColor(Color.BLACK);
        tv_phone_no.setHeight(dip2px(this, 50));
        tv_phone_no.setGravity(Gravity.CENTER_VERTICAL);

        // 添加创建分割线6
        TextView mDiv_Line6 = new TextView(this);
        mDiv_Line6.setHeight(dip2px(this, 0.75f));
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
        tv_captchas.setPadding(dip2px(this, 7), 0, 0, 0);
        tv_captchas.setTextSize(14);
        tv_captchas.setTextColor(Color.BLACK);
        tv_captchas.setGravity(Gravity.CENTER_VERTICAL);

        et_captchas = new EditText(this);
        LinearLayout.LayoutParams para_et = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        et_captchas.setLayoutParams(para_et);
        para_et.setMargins(dip2px(this, 7), 0,
                dip2px(this, 35), 0);
        et_captchas.setHint("输入验证码"); // 设置文字 颜色 字体
        et_captchas.setWidth(mWidth * 3 / 8);
        et_captchas.setTextSize(14);
        et_captchas
                .setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        et_captchas.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_captchas.setTextColor(Color.BLACK);
        et_captchas.setHeight(dip2px(this, 50));
        et_captchas.setBackgroundResource(0);
        et_captchas.setPadding(20, 0, 0, 0);
        et_captchas.setGravity(Gravity.CENTER_VERTICAL);
        et_captchas.addTextChangedListener(checkCodeWatcher);

        TextView tv_cap_div = new TextView(this);
        LinearLayout.LayoutParams para_divhor = new LinearLayout.LayoutParams(
                dip2px(this, 2), AbsListView.LayoutParams.WRAP_CONTENT);
        para_divhor.setMargins(dip2px(this, 10), 0,
                dip2px(this, 10), 0);
        tv_cap_div.setLayoutParams(para_divhor);
        tv_cap_div.setText("|"); // 设置文字 颜色 字体
        tv_cap_div.setTextSize(16);
        tv_cap_div.setTextColor(Color.GRAY);
        tv_cap_div.setGravity(Gravity.CENTER_VERTICAL);

        bt_get_captchas = new Button(this);
        LinearLayout.LayoutParams para_bt_get_captchas = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        bt_get_captchas.setLayoutParams(para_bt_get_captchas);
        bt_get_captchas.setText("获取验证码"); // 设置文字 颜色 字体
        bt_get_captchas.setWidth(mWidth * 3 / 8);
        bt_get_captchas.setTextColor(Color.parseColor("#2575CE"));
        bt_get_captchas.setTextSize(11);

        bt_get_captchas.setBackgroundColor(Color.WHITE);
        bt_get_captchas.setHeight(dip2px(this, 50));
        bt_get_captchas.setGravity(Gravity.CENTER);

        // 添加创建分割线7
        TextView mDiv_Line7 = new TextView(this);
        mDiv_Line7.setHeight(dip2px(this, 0.75f));
        mDiv_Line7.setBackgroundColor(Color.parseColor("#D1D3D5"));

        linearLayout6.addView(tv_captchas);
        linearLayout6.addView(et_captchas);
        linearLayout6.addView(tv_cap_div);
        linearLayout6.addView(bt_get_captchas);
        mLL_ready_to_pay.addView(linearLayout6);
        mLL_ready_to_pay.addView(mDiv_Line7);


        // 身份证
        LinearLayout ll_sfz = new LinearLayout(this);// 1: 商户名称水平布局 定义宽高和布局方向
        ll_sfz.setLayoutParams(para_stripe);
        ll_sfz.setOrientation(LinearLayout.HORIZONTAL);
        ll_sfz.setBackgroundColor(Color.WHITE);
        ll_sfz.setGravity(Gravity.CENTER_VERTICAL);
        ll_sfz.setPadding(dip2px(this, 10), 0, 0, 0);

        // 子嵌套控件的宽高参数
        TextView tv_sfz = new TextView(this); // 创建TextView对象
        tv_sfz.setLayoutParams(para_discendant);
        tv_sfz.setText("身份证"); // 设置文字 颜色 字体
        tv_sfz.setTextSize(14);
        tv_sfz.setTextColor(Color.BLACK);
        tv_sfz.setWidth(mWidth * 9 / 40);
        tv_sfz.setHeight(dip2px(this, 44));
        tv_sfz.setGravity(Gravity.CENTER_VERTICAL);

        input_et_sfz = new EditText(this);
        input_et_sfz.setLayoutParams(para_discendant);
        input_et_sfz.setHint("输入身份证");
        input_et_sfz.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
        // start--身份证号输入过程中限制数字和小写'x', 大写'X'自动转小写
        input_et_sfz.setKeyListener(new NumberKeyListener() {
            protected char[] getAcceptedChars() {
                char[] mychar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'x', 'X'};
                return mychar;
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_NUMBER;
            }
        });
        input_et_sfz.setInputType(InputType.TYPE_CLASS_PHONE);
        input_et_sfz.setBackgroundResource(0);
        input_et_sfz.addTextChangedListener(cvv2Watcher);

        iv_delete_number_sfz = new ImageView(this);
        iv_delete_number_sfz.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_delete_number_sfz.setImageDrawable(ReapalUtil.getResDrawable(layoutActivity.this, "deleteIcon.png"));
        iv_delete_number_sfz.setVisibility(View.INVISIBLE);
        iv_delete_number_sfz.setPadding(0, 0, dip2px(this, 10), 0);
        iv_delete_number_sfz.setOnClickListener(this);

        ImageView iv_about_sfz = new ImageView(this);
        iv_about_sfz.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_about_sfz.setImageDrawable(ReapalUtil.getResDrawable(layoutActivity.this, "aboutPhoneNo.png"));
        iv_about_sfz.setPadding(0, 0, dip2px(this, 20), 0);
        iv_about_sfz.setOnClickListener(this);

        input_et_sfz.setTextSize(14);
        input_et_sfz.setTextColor(Color.BLACK);
        input_et_sfz.setPadding(0, 0, 0, 0);
        input_et_sfz.setWidth(mWidth * 19 / 40); // ?
        input_et_sfz.setHeight(dip2px(this, 44));
        input_et_sfz.setGravity(Gravity.CENTER_VERTICAL);
        input_et_sfz.addTextChangedListener(cvv2Watcher);

        // 添加创建分割线6_____安全码下方
        LinearLayout ll_div6_sfz = new LinearLayout(this);
        ll_div6_sfz.setLayoutParams(para_div);
        ll_div6_sfz.setOrientation(LinearLayout.HORIZONTAL);
        ll_div6_sfz.setBackgroundColor(Color.WHITE);
        ll_div6_sfz.setPadding(dip2px(this, 10), 0, 0, 0);

        TextView mDiv_Line_sfz = new TextView(this);
        mDiv_Line_sfz.setWidth(mWidth - dip2px(this, 10));
        mDiv_Line_sfz.setHeight(dip2px(this, 0.75f));
        mDiv_Line_sfz.setBackgroundColor(Color.parseColor("#D1D3D5"));

        // 填充四行
        ll_sfz.addView(tv_sfz); // 在父类布局中添加第一行，及布局样式
        ll_sfz.addView(input_et_sfz);
        ll_sfz.addView(iv_delete_number_sfz);
//            ll_sfz.addView(iv_about_sfz);
        mLL_ready_to_pay.addView(ll_sfz);
        mLL_ready_to_pay.addView(mDiv_Line_sfz);


        // 安全码
        LinearLayout ll_cvv2 = new LinearLayout(this);// 1: 商户名称水平布局 定义宽高和布局方向
        ll_cvv2.setLayoutParams(para_stripe);
        ll_cvv2.setOrientation(LinearLayout.HORIZONTAL);
        ll_cvv2.setBackgroundColor(Color.WHITE);
        ll_cvv2.setGravity(Gravity.CENTER_VERTICAL);
        ll_cvv2.setPadding(dip2px(this, 10), 0, 0, 0);

        // 子嵌套控件的宽高参数
        TextView tv_cvv2 = new TextView(this); // 创建TextView对象
        tv_cvv2.setLayoutParams(para_discendant);
        tv_cvv2.setText("安全码"); // 设置文字 颜色 字体
        tv_cvv2.setTextSize(14);
        tv_cvv2.setTextColor(Color.BLACK);
        tv_cvv2.setWidth(mWidth * 9 / 40);
        tv_cvv2.setHeight(dip2px(this, 44));
        tv_cvv2.setGravity(Gravity.CENTER_VERTICAL);

        input_et_cvv2 = new EditText(this);
        input_et_cvv2.setLayoutParams(para_discendant);
        input_et_cvv2.setHint("该卡背面后三位数字");
        input_et_cvv2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        input_et_cvv2.setInputType(InputType.TYPE_CLASS_PHONE);
        input_et_cvv2.setBackgroundResource(0);
        input_et_cvv2.addTextChangedListener(cvv2Watcher);

        iv_delete_number = new ImageView(this);
        iv_delete_number.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_delete_number.setImageDrawable(ReapalUtil.getResDrawable(layoutActivity.this, "deleteIcon.png"));
        iv_delete_number.setVisibility(View.INVISIBLE);
        iv_delete_number.setPadding(0, 0, dip2px(this, 10), 0);
        iv_delete_number.setOnClickListener(this);

        iv_about_cvv2 = new ImageView(this);
        iv_about_cvv2.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_about_cvv2.setImageDrawable(ReapalUtil.getResDrawable(layoutActivity.this, "aboutPhoneNo.png"));
        iv_about_cvv2.setPadding(0, 0, dip2px(this, 20), 0);
        iv_about_cvv2.setOnClickListener(this);

        input_et_cvv2.setTextSize(14);
        input_et_cvv2.setTextColor(Color.BLACK);
        input_et_cvv2.setPadding(0, 0, 0, 0);
        input_et_cvv2.setWidth(mWidth * 19 / 40); // ?
        input_et_cvv2.setHeight(dip2px(this, 44));
        input_et_cvv2.setGravity(Gravity.CENTER_VERTICAL);

        // 添加创建分割线6_____安全码下方
        LinearLayout ll_div6 = new LinearLayout(this);
        ll_div6.setLayoutParams(para_div);
        ll_div6.setOrientation(LinearLayout.HORIZONTAL);
        ll_div6.setBackgroundColor(Color.WHITE);
        ll_div6.setPadding(dip2px(this, 10), 0, 0, 0);

        TextView mDiv_Line_6 = new TextView(this);
        mDiv_Line_6.setWidth(mWidth - dip2px(this, 10));
        mDiv_Line_6.setHeight(dip2px(this, 0.75f));
        mDiv_Line_6.setBackgroundColor(Color.parseColor("#D1D3D5"));

        // 填充四行
        ll_cvv2.addView(tv_cvv2); // 在父类布局中添加第一行，及布局样式
        ll_cvv2.addView(input_et_cvv2);
        ll_cvv2.addView(iv_delete_number);
        ll_cvv2.addView(iv_about_cvv2);
        mLL_ready_to_pay.addView(ll_cvv2);
        mLL_ready_to_pay.addView(mDiv_Line_6);


        // 有效期
        LinearLayout ll_yxq = new LinearLayout(this);// 1: 商户名称水平布局 定义宽高和布局方向
        ll_yxq.setLayoutParams(para_stripe);
        ll_yxq.setOrientation(LinearLayout.HORIZONTAL);
        ll_yxq.setBackgroundColor(Color.WHITE);
        ll_yxq.setGravity(Gravity.CENTER_VERTICAL);
        ll_yxq.setPadding(dip2px(this, 10), 0, 0, 0);

        // 子嵌套控件的宽高参数
        TextView tv_yxq = new TextView(this); // 创建TextView对象
        tv_yxq.setLayoutParams(para_discendant);
        tv_yxq.setText("有效期"); // 设置文字 颜色 字体
        tv_yxq.setTextSize(14);
        tv_yxq.setTextColor(Color.BLACK);
        tv_yxq.setWidth(mWidth * 9 / 40);
        tv_yxq.setHeight(dip2px(this, 44));
        tv_yxq.setGravity(Gravity.CENTER_VERTICAL);

        input_et_year = new EditText(this);
        input_et_year.setLayoutParams(para_discendant);
        input_et_year.setHint("月/年");
        input_et_year.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        input_et_year.setInputType(InputType.TYPE_CLASS_PHONE);
        input_et_year.setBackgroundResource(0);
        input_et_year.addTextChangedListener(cvv2Watcher);

        iv_delete_number_year = new ImageView(this);
        iv_delete_number_year.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_delete_number_year.setImageDrawable(ReapalUtil.getResDrawable(layoutActivity.this, "deleteIcon.png"));
        iv_delete_number_year.setVisibility(View.INVISIBLE);
        iv_delete_number_year.setPadding(0, 0, dip2px(this, 10), 0);
        iv_delete_number_year.setOnClickListener(this);

        iv_about_yxq = new ImageView(this);
        iv_about_yxq.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_about_yxq.setImageDrawable(ReapalUtil.getResDrawable(layoutActivity.this, "aboutPhoneNo.png"));
        iv_about_yxq.setPadding(0, 0, dip2px(this, 20), 0);
        iv_about_yxq.setOnClickListener(this);

        input_et_year.setTextSize(14);
        input_et_year.setTextColor(Color.BLACK);
        input_et_year.setPadding(0, 0, 0, 0);
        input_et_year.setWidth(mWidth * 19 / 40); // ?
        input_et_year.setHeight(dip2px(this, 44));
        input_et_year.setGravity(Gravity.CENTER_VERTICAL);

        // 添加创建分割线6_____安全码下方
        LinearLayout ll_div_yxq = new LinearLayout(this);
        ll_div_yxq.setLayoutParams(para_div);
        ll_div_yxq.setOrientation(LinearLayout.HORIZONTAL);
        ll_div_yxq.setBackgroundColor(Color.WHITE);
        ll_div_yxq.setPadding(dip2px(this, 10), 0, 0, 0);

        TextView mDiv_Line_yxq = new TextView(this);
        mDiv_Line_yxq.setWidth(mWidth - dip2px(this, 10));
        mDiv_Line_yxq.setHeight(dip2px(this, 0.75f));
        mDiv_Line_yxq.setBackgroundColor(Color.parseColor("#D1D3D5"));

        // 填充四行
        ll_yxq.addView(tv_yxq); // 在父类布局中添加第一行，及布局样式
        ll_yxq.addView(input_et_year);
        ll_yxq.addView(iv_delete_number_year);
        ll_yxq.addView(iv_about_yxq);
        mLL_ready_to_pay.addView(ll_yxq);
        mLL_ready_to_pay.addView(mDiv_Line_yxq);


        // 确认支付按钮
        btn_bind_card = new Button(this);
        LinearLayout.LayoutParams para_bt_bind_card = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, dip2px(this, 43));
        para_bt_bind_card.gravity = Gravity.CENTER_HORIZONTAL;
        para_bt_bind_card.setMargins(dip2px(this, 20),
                dip2px(this, 20), dip2px(this, 20), 0);
        btn_bind_card.setLayoutParams(para_bt_bind_card);

        btn_bind_card.setBackgroundColor(Color.parseColor("#B3CDE9"));
        btn_bind_card.setText("确认支付");
        btn_bind_card.setTextSize(14);
        btn_bind_card.setTextColor(Color.WHITE);
        mLL_ready_to_pay.addView(btn_bind_card);

        tv_sms_not_received = new TextView(this);
        LinearLayout.LayoutParams para_tv_sms_not_received = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, dip2px(this, 30));
        para_tv_sms_not_received
                .setMargins(0, dip2px(this, 10), 0, 0);
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
        tv_info3.setPadding(0, 0, 0, dip2px(this, 5));

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

    private TextWatcher cvv2Watcher = new TextWatcher() {// 监听输入的内容
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (input_et_year.hasFocus()) {
                iv_delete_number_year.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);// 是否展示清除图片
            } else if (input_et_cvv2.hasFocus()) {
                iv_delete_number.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);// 是否展示清除图片
            } else if (input_et_sfz.hasFocus()) {
                iv_delete_number_sfz.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);// 是否展示清除图片
            }
            // 监听输入框控制"下一步" 按钮颜色
            /*btn_bind_card.setBackgroundColor(TextUtils.isEmpty(s) || TextUtils.isEmpty(et_phone_no.getText())
                    || TextUtils.isEmpty(et_id_no_input.getText()) ? Color.parseColor("#B3CDE9")
                    : Color.parseColor("#398DEE"));*/
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void onClick(View v) {
        if (v == btn_bind_card) {
            Toast.makeText(this, "点击了确认按钮", Toast.LENGTH_SHORT).show();

        } else if (v == tv_sms_not_received) {
            String title = "收不到验证码?";
            String tips = "请按下面的步骤依次确认解决问题 \n①请确认该预留手机号是否为当前使用手机号；\n②请查看短信是否被安全软件拦截；\n③"
                    + "由于运营商网路原因，可能存在短信延迟，请耐心等待或稍后再试；\n④若银行预留手机号已停用，请联系银行客服处理；\n⑤获取更多帮助，可联系融宝支付客服400-022-6816。";

        } else if (v == iv_delete_number) {
            input_et_cvv2.getText().clear();//清空安全码信息
        } else if (v == iv_delete_number_year) {
            input_et_year.getText().clear();//清空有效期
        } else if (v == iv_delete_number_sfz) {
            input_et_sfz.getText().clear();//清空身份证信息
        } else if (v == conferm_ok) {
            dialog_ok.dismiss();//关闭弹框
        } else if (v == iv_about_cvv2) {
            //弹出说明框
            String title = "安全码说明";
            String tips = "CVN2是打印在信用卡背面签名处一串数字的末三位。";
            showDialog_explain(title, tips, "cvv2.png");
        } else if (v == iv_about_yxq) {
            //弹出说明框
            String title = "安全码说明";
            String tips = "CVN2是打印在信用卡背面签名处一串数字的末三位。";
            showDialog_explain(title, tips, "valid.png");
        }
    }

    private void showDialog_explain(String title, String tips, String pic_name) {
        // 弹窗提示
        LinearLayout layout = new LinearLayout(layoutActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        // 标题
        TextView tv_title = new TextView(layoutActivity.this);
        LinearLayout.LayoutParams para_tv = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        tv_title.setLayoutParams(para_tv);
        tv_title.setText(title);
        tv_title.setGravity(Gravity.CENTER);
        tv_title.setTextSize(15);
        tv_title.setPadding(dip2px(this, 10), dip2px(this, 10), dip2px(this, 10), dip2px(this, 10));
        layout.setGravity(Gravity.CENTER_VERTICAL);
        // 间隔线
        TextView mDiv_Line_tip = new TextView(this);
        mDiv_Line_tip.setWidth(mWidth - dip2px(this, 10));
        mDiv_Line_tip.setHeight(dip2px(this, 0.75f));
        mDiv_Line_tip.setBackgroundColor(Color.parseColor("#D1D3D5"));
        // 提示信息
        TextView tv = new TextView(layoutActivity.this);
        tv.setLayoutParams(para_tv);
        tv.setText(tips);
        tv.setTextSize(13);
        tv.setPadding(dip2px(this, 10), dip2px(this, 10), dip2px(this, 10), dip2px(this, 10));
        // 提示图片
        ImageView iv_pic = new ImageView(this);
        LinearLayout.LayoutParams para_pic = new LinearLayout.LayoutParams(mWidth * 3 / 5, mWidth * 9 / 20);
        para_pic.gravity = Gravity.CENTER;
        iv_pic.setLayoutParams(para_pic);
        if (pic_name != null) {
            iv_pic.setImageDrawable(ReapalUtil.getResDrawable(layoutActivity.this, pic_name));
        }
        iv_pic.setPadding(0, 0, 0, dip2px(this, 10));
        // 确定按钮
        conferm_ok = new TextView(layoutActivity.this);
        LinearLayout.LayoutParams para_tv1 = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                dip2px(layoutActivity.this, 50));
        conferm_ok.setText("确定");
        conferm_ok.setLayoutParams(para_tv1);
        conferm_ok.setTextSize(17);
        conferm_ok.setTextColor(Color.WHITE);
        conferm_ok.setBackgroundColor(Color.parseColor("#398DEE"));
        conferm_ok.setGravity(Gravity.CENTER);
        conferm_ok.setOnClickListener(layoutActivity.this);

        if (title != null) {
            layout.addView(tv_title);
            layout.addView(mDiv_Line_tip);
        }
        layout.addView(tv);
        if (pic_name != null) {
            layout.addView(iv_pic);
            conferm_ok.setText("知道了");
        }
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.addView(conferm_ok);

        dialog_ok = new AlertDialog.Builder(layoutActivity.this).setView(layout).setCancelable(false).show();
    }
}
