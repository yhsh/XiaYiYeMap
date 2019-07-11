package cn.xiayiye.customerlayout;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static cn.xiayiye.customerlayout.ScaleUtil.dip2px;

/**
 * 创 建 者：下一页5（轻飞扬）
 * 创建时间：2018/3/29.15:28
 * 个人小站：http://wap.yhsh.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268(请用手机QQ添加)
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 空间名称：XiaYiYeMap
 * 项目包名：cn.xiayiye.customerlayout
 */
public class BigLayout extends Activity {

    private int mWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        // 获取屏幕宽度
        mWidth = DisplaySizeUtil.getDisplaySizeInfo(this).x;

        // 根布局及其参数 定义宽高和布局方向等
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        scrollView.setBackgroundColor(Color.parseColor("#F1F3F5"));
        LinearLayoutView mLL_bind_dibit = new LinearLayoutView(this);
//        mLL_bind_dibit.setKeyBordStateListener(this);
        mLL_bind_dibit
                .setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        mLL_bind_dibit.setOrientation(LinearLayout.VERTICAL);
        mLL_bind_dibit.setBackgroundColor(Color.parseColor("#F1F3F5"));

        // 第0行 标题行
        LinearLayout linearLayout0 = new LinearLayout(this);
        LinearLayout.LayoutParams para_top = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, dip2px(this, 50));
        linearLayout0.setLayoutParams(para_top);
        linearLayout0.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout0.setBackgroundColor(Color.parseColor("#323236"));
        linearLayout0.setGravity(Gravity.CENTER_VERTICAL);

        ImageView iv_backArrow = new ImageView(this);
        iv_backArrow.setLayoutParams(new ActionBar.LayoutParams(dip2px(this, 50), dip2px(this, 50)));
        iv_backArrow.setImageDrawable(ReapalUtil.getResDrawable(BigLayout.this, "backArrow.png"));
        iv_backArrow.setPadding(dip2px(this, 14), 0, dip2px(this, 7), 0);
//        iv_backArrow.setOnClickListener(this);

        LinearLayout.LayoutParams para_top_title = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        TextView tv_top = new TextView(this); // 创建TextView对象
        tv_top.setLayoutParams(para_top_title);
        tv_top.setText("融宝收银台"); // 设置文字 颜色 字体
        tv_top.setTextSize(20);
        tv_top.setTextColor(Color.WHITE);
        tv_top.setHeight(dip2px(this, 44));
        tv_top.setPadding(0, 0, dip2px(this, 45), 0);
        tv_top.setGravity(Gravity.CENTER);

        linearLayout0.addView(iv_backArrow);
        linearLayout0.addView(tv_top);
        mLL_bind_dibit.addView(linearLayout0);

        // 第1行: 商户名称水平布局
        // 定义宽高和布局方向
        LinearLayout.LayoutParams para_stripe = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams para_discendant = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        LinearLayout linearLayout1 = new LinearLayout(this);
        linearLayout1.setLayoutParams(para_discendant);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setBackgroundColor(Color.WHITE);
        linearLayout1.setPadding(dip2px(this, 10), 0, 0, 0);
        linearLayout1.setVisibility(View.GONE);

        // 子嵌套控件的宽高参数
        TextView tv_name_merchant = new TextView(this); // 创建TextView对象
        tv_name_merchant.setLayoutParams(para_stripe); // 条目
        tv_name_merchant.setText("商户名称"); // 设置文字 颜色 字体
        tv_name_merchant.setTextSize(14);
        tv_name_merchant.setTextColor(Color.BLACK);
        tv_name_merchant.setWidth(mWidth * 9 / 40);
        tv_name_merchant.setHeight(dip2px(this, 40));
        tv_name_merchant.setGravity(Gravity.CENTER_VERTICAL);

        TextView tv_Merchant_name = new TextView(this);
        tv_Merchant_name.setLayoutParams(para_discendant);
        tv_Merchant_name.setText("商户名字"); // 设置文字
        tv_Merchant_name.setTextSize(14);
        tv_Merchant_name.setTextColor(Color.BLACK);
        tv_Merchant_name.setWidth(mWidth * 31 / 40);
        tv_Merchant_name.setHeight(dip2px(this, 40));
        tv_Merchant_name.setGravity(Gravity.CENTER_VERTICAL);

        // 左缺分割线
        LinearLayout ll_div1 = new LinearLayout(this);
        LinearLayout.LayoutParams para_div = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        ll_div1.setLayoutParams(para_div);
        ll_div1.setVisibility(View.GONE);
        ll_div1.setOrientation(LinearLayout.HORIZONTAL);
        ll_div1.setBackgroundColor(Color.WHITE);
        ll_div1.setPadding(0, 0, 0, 0);

        TextView mDiv_Line1 = new TextView(this);
        mDiv_Line1.setHeight(dip2px(this, 0.75F));
        mDiv_Line1.setWidth(mWidth);
        mDiv_Line1.setBackgroundColor(Color.parseColor("#D1D3D5"));
        // 填充第一行
        linearLayout1.addView(tv_name_merchant); // 在父类布局中添加第一行，及布局样式
        linearLayout1.addView(tv_Merchant_name);
        ll_div1.addView(mDiv_Line1);
        mLL_bind_dibit.addView(linearLayout1);
        mLL_bind_dibit.addView(ll_div1);

        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setLayoutParams(para_discendant);
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
        tv_order_no.setText("订单号的显示");
        // 设置文字
        tv_order_no.setTextSize(14);
        tv_order_no.setTextColor(Color.BLACK);
        tv_order_no.setWidth(mWidth * 31 / 40);
        tv_order_no.setHeight(dip2px(this, 40));
        tv_order_no.setGravity(Gravity.CENTER_VERTICAL);

        TextView mDiv_Line1_2 = new TextView(this);
        mDiv_Line1_2.setHeight(dip2px(this, 0.75F));
        mDiv_Line1_2.setVisibility(View.GONE);
        mDiv_Line1_2.setBackgroundColor(Color.parseColor("#D1D3D5"));

        linearLayout2.addView(tv_no_order); // 在父类布局中添加第一行，及布局样式
        linearLayout2.addView(tv_order_no);
        mLL_bind_dibit.addView(linearLayout2);
        mLL_bind_dibit.addView(mDiv_Line1_2);

        LinearLayout linearLayout3 = new LinearLayout(this);
        linearLayout3.setLayoutParams(para_stripe);
        linearLayout3.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout3.setBackgroundColor(Color.WHITE);
        linearLayout3.setPadding(dip2px(this, 10), 0, 0, 0);

        // 子嵌套控件的宽高参数
        TextView tv_fee_to_pay = new TextView(this); // 创建TextView对象
        tv_fee_to_pay.setLayoutParams(para_discendant);
        tv_fee_to_pay.setText("应付金额"); // 设置文字 颜色 字体
        tv_fee_to_pay.setTextSize(14);
        tv_fee_to_pay.setTextColor(Color.BLACK);
        tv_fee_to_pay.setWidth(mWidth * 9 / 40);
        tv_fee_to_pay.setHeight(dip2px(this, 50));
        tv_fee_to_pay.setGravity(Gravity.CENTER_VERTICAL);

        TextView tv_pay_fee = new TextView(this); // 创建TextView对象
        tv_pay_fee.setLayoutParams(para_discendant);
        TextPaint tp = tv_pay_fee.getPaint();
        tp.setFakeBoldText(true);
        // 设置文字 需要修改 refine
        tv_pay_fee.setText("￥800 ");
        tv_pay_fee.setTextSize(16);
        tv_pay_fee.setTextColor(Color.RED);
        tv_pay_fee.setWidth(mWidth * 25 / 40);
        tv_pay_fee.setHeight(dip2px(this, 50));
        tv_pay_fee.setGravity(Gravity.CENTER_VERTICAL);

        ImageView iv_downArrow = new ImageView(this);
        iv_downArrow.setPadding(dip2px(this, 7), 0, dip2px(this, 20), 0);
        iv_downArrow.setLayoutParams(new ActionBar.LayoutParams(dip2px(this, 44), dip2px(this, 44)));
        iv_downArrow.setImageDrawable(ReapalUtil.getResDrawable(BigLayout.this, "downArrow.png"));
//        iv_downArrow.setOnClickListener(this);

        // 添加创建分割线1-3
        TextView mDiv_Line1_3 = new TextView(this);
        mDiv_Line1_3.setHeight(dip2px(this, 0.75F));
        mDiv_Line1_3.setBackgroundColor(Color.parseColor("#D1D3D5"));
        // 填充第一行
        linearLayout3.addView(tv_fee_to_pay); // 在父类布局中添加第一行，及布局样式
        linearLayout3.addView(tv_pay_fee);
        linearLayout3.addView(iv_downArrow);
        mLL_bind_dibit.addView(linearLayout3);
        mLL_bind_dibit.addView(mDiv_Line1_3);

        // 订单信息 与下方 绑卡信息的间隔带
        RelativeLayout rl_card_no = new RelativeLayout(this);
        RelativeLayout.LayoutParams para_rl_caed_no = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                dip2px(this, 85));
        para_rl_caed_no.addRule(RelativeLayout.CENTER_VERTICAL);
        para_rl_caed_no.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        rl_card_no.setPadding(dip2px(this, 15), dip2px(this, 20), dip2px(this, 20), dip2px(this, 20));
        // 左侧子布局
        RelativeLayout rl_card_no_left = new RelativeLayout(this);
        RelativeLayout.LayoutParams para_rl_caed_no_left = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        int bank_id = ViewID.generateViewId();
        // 左侧银行logo
        ImageView iv_bank = new ImageView(this);
        RelativeLayout.LayoutParams para_rl_caed_no_bank = new RelativeLayout.LayoutParams(dip2px(this, 20),
                dip2px(this, 20));
        para_rl_caed_no_bank.addRule(RelativeLayout.ALIGN_PARENT_LEFT, -1);
        iv_bank.setId(bank_id);
        iv_bank.setImageDrawable(ReapalUtil.getResBankDrawable(BigLayout.this, "建设银行")); ////// refine-
        // 卡信息
        TextView tv_bank_name = new TextView(this);
        RelativeLayout.LayoutParams para_tv = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        para_tv.addRule(RelativeLayout.RIGHT_OF, bank_id);
        tv_bank_name.setPadding(dip2px(this, 4), dip2px(this, 0), dip2px(this, 0), dip2px(this, 0));
        tv_bank_name.setGravity(Gravity.CENTER_VERTICAL);
        tv_bank_name.setLayoutParams(para_tv);
        tv_bank_name.setTextSize(14);
        tv_bank_name.setText("建设银行" + "(信用卡)");
        // 下方银行卡号
        TextView tv_card_no = new TextView(this);
        RelativeLayout.LayoutParams para_tv_card_no = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        para_tv_card_no.addRule(RelativeLayout.BELOW, bank_id);
        para_tv_card_no.setMargins(dip2px(this, 0), dip2px(this, 4), dip2px(this, 0), dip2px(this, 0));

        tv_card_no.setTextSize(14);
        TextPaint tp_card_no = tv_card_no.getPaint();
        tp_card_no.setFakeBoldText(true);
        tv_card_no.setText("62270028***251");

        rl_card_no_left.addView(iv_bank, para_rl_caed_no_bank);
        rl_card_no_left.addView(tv_bank_name, para_tv);
        rl_card_no_left.addView(tv_card_no, para_tv_card_no);
        rl_card_no.addView(rl_card_no_left, para_rl_caed_no_left);

        RelativeLayout rl_card_no_right = new RelativeLayout(this);
        RelativeLayout.LayoutParams para_rl_caed_no_right = new RelativeLayout.LayoutParams(dip2px(this, 50),
                ActionBar.LayoutParams.WRAP_CONTENT);
        para_rl_caed_no_right.addRule(RelativeLayout.CENTER_VERTICAL);
        para_rl_caed_no_right.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        para_rl_caed_no_right.setMargins(dip2px(this, 10), dip2px(this, 10), dip2px(this, 0), dip2px(this, 10));
//        rl_card_no_right.setOnClickListener(this);

        TextView tv_change = new TextView(this);
        RelativeLayout.LayoutParams para_tv_change = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        para_tv_change.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, -1);
        para_tv_change.addRule(RelativeLayout.CENTER_VERTICAL, -1);
        tv_change.setTextSize(12);
        tv_change.setText("更换");
        tv_change.setTextColor(Color.parseColor("#2575CE"));
        tv_change.setGravity(Gravity.CENTER);

        ImageView iv_change = new ImageView(this);
        RelativeLayout.LayoutParams para_iv_change = new RelativeLayout.LayoutParams(dip2px(this, 20),
                dip2px(this, 20));
        iv_change.setImageDrawable(ReapalUtil.getResDrawable(BigLayout.this, "changeIcon.png"));
        para_iv_change.addRule(RelativeLayout.ALIGN_PARENT_LEFT, -1);
        iv_change.setScaleType(ImageView.ScaleType.FIT_CENTER);

        rl_card_no_right.addView(tv_change, para_tv_change);
        rl_card_no_right.addView(iv_change, para_iv_change);
        rl_card_no.addView(rl_card_no_right, para_rl_caed_no_right);
        mLL_bind_dibit.addView(rl_card_no, para_rl_caed_no);

        // 添加创建分割线2_____绑卡间隔带下方
        TextView mDiv_Line2 = new TextView(this);
        mDiv_Line2.setHeight(dip2px(this, 0.75F));
        mDiv_Line2.setBackgroundColor(Color.parseColor("#D1D3D5"));
        mLL_bind_dibit.addView(mDiv_Line2);

        // 绑卡信息 四要素输入框 第二行
        LinearLayout ll_owner = new LinearLayout(this);// 1: 商户名称水平布局 定义宽高和布局方向
        ll_owner.setLayoutParams(para_stripe);
        ll_owner.setOrientation(LinearLayout.HORIZONTAL);
        ll_owner.setBackgroundColor(Color.WHITE);
        ll_owner.setGravity(Gravity.CENTER_VERTICAL);
        ll_owner.setPadding(dip2px(this, 10), 0, 0, 0);

        // 子嵌套控件的宽高参数
        TextView tv_name_owner = new TextView(this); // 创建TextView对象
        tv_name_owner.setLayoutParams(para_discendant);
        tv_name_owner.setText("姓名"); // 设置文字 颜色 字体
        tv_name_owner.setTextSize(14);
        tv_name_owner.setTextColor(Color.BLACK);
        tv_name_owner.setWidth(mWidth * 9 / 40);
        tv_name_owner.setHeight(dip2px(this, 45));
        tv_name_owner.setGravity(Gravity.CENTER_VERTICAL);

        TextView et_owner_name = new TextView(this);
        et_owner_name.setLayoutParams(para_discendant);
        et_owner_name.setText("银行展示信息");

        // 设置文字
        et_owner_name.setTextSize(14);
        et_owner_name.setTextColor(Color.BLACK);
        et_owner_name.setPadding(0, 0, 0, 0);
        et_owner_name.setWidth(mWidth * 25 / 40);
        et_owner_name.setHeight(dip2px(this, 45));
        et_owner_name.setBackgroundResource(0);
        et_owner_name.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

        // 添加创建分割线4_____持卡人姓名下方
        LinearLayout ll_div4 = new LinearLayout(this);
        ll_div4.setLayoutParams(para_div);
        ll_div4.setOrientation(LinearLayout.HORIZONTAL);
        ll_div4.setBackgroundColor(Color.WHITE);
        ll_div4.setPadding(dip2px(this, 10), 0, 0, 0);

        TextView mDiv_Line_4 = new TextView(this);
        mDiv_Line_4.setWidth(mWidth - dip2px(this, 10));
        mDiv_Line_4.setHeight(dip2px(this, 0.75f));
        mDiv_Line_4.setBackgroundColor(Color.parseColor("#D1D3D5"));

        // 填充第一行
        ll_owner.addView(tv_name_owner); // 在父类布局中添加第一行，及布局样式
        ll_owner.addView(et_owner_name);
        ll_div4.addView(mDiv_Line_4);
        mLL_bind_dibit.addView(ll_owner); // 放到了卡号之后
        mLL_bind_dibit.addView(ll_div4);

        // 绑卡信息 四要素输入框 第三行
        LinearLayout ll_id_info = new LinearLayout(this);// 1: 商户名称水平布局
        // 定义宽高和布局方向
        ll_id_info.setLayoutParams(para_stripe);
        ll_id_info.setOrientation(LinearLayout.HORIZONTAL);
        ll_id_info.setBackgroundColor(Color.WHITE);
        ll_id_info.setGravity(Gravity.CENTER_VERTICAL);
        ll_id_info.setPadding(dip2px(this, 10), 0, 0, 0);

        // 子嵌套控件的宽高参数
        TextView tv_id_no = new TextView(this); // 创建TextView对象
        tv_id_no.setLayoutParams(para_discendant);
        tv_id_no.setText("身份证"); // 设置文字 颜色 字体
        tv_id_no.setTextSize(14);
        tv_id_no.setTextColor(Color.BLACK);
        tv_id_no.setWidth(mWidth * 9 / 40);
        tv_id_no.setHeight(dip2px(this, 45));
        tv_id_no.setGravity(Gravity.CENTER_VERTICAL);

        EditText et_id_no_input = new EditText(this);
        et_id_no_input.setText("用户ID 的显示");
        et_id_no_input.setBackgroundResource(0);
        // 设置文字
        et_id_no_input.setTextSize(14);
        et_id_no_input.setPadding(0, 0, 0, 0);
        et_id_no_input.setWidth(mWidth * 25 / 40);
        et_id_no_input.setHeight(dip2px(this, 45));
        et_id_no_input.setTextColor(Color.BLACK);
        et_id_no_input.setGravity(Gravity.CENTER_VERTICAL);

        // 添加创建分割线5_____持卡人身份证号下方
        LinearLayout ll_div5 = new LinearLayout(this);
        ll_div5.setLayoutParams(para_div);
        ll_div5.setOrientation(LinearLayout.HORIZONTAL);
        ll_div5.setBackgroundColor(Color.WHITE);
        ll_div5.setPadding(dip2px(this, 10), 0, 0, 0);

        TextView mDiv_Line_5 = new TextView(this);
        mDiv_Line_5.setWidth(mWidth - dip2px(this, 10));
        mDiv_Line_5.setHeight(dip2px(this, 0.75f));
        mDiv_Line_5.setBackgroundColor(Color.parseColor("#D1D3D5"));

        // 填充第三行---持卡人身份证号码
        ll_id_info.addView(tv_id_no); // 在父类布局中添加第一行，及布局样式
        ll_id_info.addView(et_id_no_input);
        ll_div5.addView(mDiv_Line_5);
        mLL_bind_dibit.addView(ll_id_info);
        mLL_bind_dibit.addView(ll_div5);

        // 绑卡信息 输入框 第四 行 安全码
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

        EditText et_cvv2 = new EditText(this);
        et_cvv2.setLayoutParams(para_discendant);
        et_cvv2.setHint("该卡背面后三位数字");
        et_cvv2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        et_cvv2.setInputType(InputType.TYPE_CLASS_PHONE);
        et_cvv2.setBackgroundResource(0);
//        et_cvv2.addTextChangedListener(cvv2Watcher);

        ImageView iv_delete2 = new ImageView(this);
        iv_delete2.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_delete2.setImageDrawable(ReapalUtil.getResDrawable(BigLayout.this, "deleteIcon.png"));
        iv_delete2.setVisibility(View.INVISIBLE);
        iv_delete2.setPadding(0, 0, dip2px(this, 10), 0);
//        iv_delete2.setOnClickListener(this);

        ImageView iv_about_cvv2 = new ImageView(this);
        iv_about_cvv2.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_about_cvv2.setImageDrawable(ReapalUtil.getResDrawable(BigLayout.this, "aboutPhoneNo.png"));
        iv_about_cvv2.setPadding(0, 0, dip2px(this, 20), 0);
//        iv_about_cvv2.setOnClickListener(this);

        et_cvv2.setTextSize(14);
        et_cvv2.setTextColor(Color.BLACK);
        et_cvv2.setPadding(0, 0, 0, 0);
        et_cvv2.setWidth(mWidth * 19 / 40); // ?
        et_cvv2.setHeight(dip2px(this, 44));
        et_cvv2.setGravity(Gravity.CENTER_VERTICAL);

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
        ll_cvv2.addView(et_cvv2);
        ll_cvv2.addView(iv_delete2);
        ll_cvv2.addView(iv_about_cvv2);
        mLL_bind_dibit.addView(ll_cvv2);
        ll_div6.addView(mDiv_Line_6);
        mLL_bind_dibit.addView(ll_div6);

        // 绑卡信息 四要素输入框 第五行-----有效期
        LinearLayout ll_valid = new LinearLayout(this);// 1: 商户名称水平布局 定义宽高和布局方向
        ll_valid.setLayoutParams(para_stripe);
        ll_valid.setOrientation(LinearLayout.HORIZONTAL);
        ll_valid.setBackgroundColor(Color.WHITE);
        ll_valid.setGravity(Gravity.CENTER_VERTICAL);
        ll_valid.setPadding(dip2px(this, 10), 0, 0, 0);

        // 子嵌套控件的宽高参数
        TextView tv_valid = new TextView(this); // 创建TextView对象
        tv_valid.setLayoutParams(para_discendant);
        tv_valid.setText("有效期"); // 设置文字 颜色 字体
        tv_valid.setTextSize(14);
        tv_valid.setTextColor(Color.BLACK);
        tv_valid.setWidth(mWidth * 9 / 40);
        tv_valid.setHeight(dip2px(this, 44));
        tv_valid.setGravity(Gravity.CENTER_VERTICAL);

        EditText et_valid = new EditText(this);
        et_valid.setLayoutParams(para_discendant);
        et_valid.setHint("月/年");
        et_valid.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        et_valid.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_valid.setBackgroundResource(0);
        // 设置文字
//        et_valid.addTextChangedListener(validWatcher);

        ImageView iv_delete3 = new ImageView(this);
        iv_delete3.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_delete3.setImageDrawable(ReapalUtil.getResDrawable(BigLayout.this, "deleteIcon.png"));
        iv_delete3.setVisibility(View.INVISIBLE);
        iv_delete3.setPadding(0, 0, dip2px(this, 10), 0);
//        iv_delete3.setOnClickListener(this);

        ImageView iv_about_valid = new ImageView(this);
        iv_about_valid.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_about_valid.setImageDrawable(ReapalUtil.getResDrawable(BigLayout.this, "aboutPhoneNo.png"));
        iv_about_valid.setPadding(0, 0, dip2px(this, 20), 0);
//        iv_about_valid.setOnClickListener(this);

        et_valid.setTextSize(14);
        et_valid.setTextColor(Color.BLACK);
        et_valid.setPadding(0, 0, 0, 0);
        et_valid.setWidth(mWidth * 19 / 40); // ?
        et_valid.setHeight(dip2px(this, 44));
        et_valid.setGravity(Gravity.CENTER_VERTICAL);
//        et_valid.addTextChangedListener(validWatcher);

        // 添加创建分割线7_____有效期下方
        LinearLayout ll_div7 = new LinearLayout(this);
        ll_div7.setLayoutParams(para_div);
        ll_div7.setOrientation(LinearLayout.HORIZONTAL);
        ll_div7.setBackgroundColor(Color.WHITE);
        ll_div7.setPadding(dip2px(this, 10), 0, 0, 0);

        TextView mDiv_Line_7 = new TextView(this);
        mDiv_Line_7.setWidth(mWidth - dip2px(this, 10));
        mDiv_Line_7.setHeight(dip2px(this, 0.75f));
        mDiv_Line_7.setBackgroundColor(Color.parseColor("#D1D3D5"));

        // 填充五行
        ll_valid.addView(tv_valid); // 在父类布局中添加第一行，及布局样式
        ll_valid.addView(et_valid);
        ll_valid.addView(iv_delete3);
        ll_valid.addView(iv_about_valid);
        ll_div7.addView(mDiv_Line_7);
        mLL_bind_dibit.addView(ll_valid);
        mLL_bind_dibit.addView(ll_div7);

        // 绑卡信息 输入框 第六 行-----------手机号
        LinearLayout ll_phone_no = new LinearLayout(this);// 1: 商户名称水平布局
        // 定义宽高和布局方向
        ll_phone_no.setLayoutParams(para_stripe);
        ll_phone_no.setOrientation(LinearLayout.HORIZONTAL);
        ll_phone_no.setBackgroundColor(Color.WHITE);
        ll_phone_no.setGravity(Gravity.CENTER_VERTICAL);
        ll_phone_no.setPadding(dip2px(this, 10), 0, 0, 0);

        // 子嵌套控件的宽高参数
        TextView tv_phone_no = new TextView(this); // 创建TextView对象
        tv_phone_no.setLayoutParams(para_discendant);
        tv_phone_no.setText("手机号"); // 设置文字 颜色 字体
        tv_phone_no.setTextSize(14);
        tv_phone_no.setTextColor(Color.BLACK);
        tv_phone_no.setWidth(mWidth * 9 / 40);
        tv_phone_no.setHeight(dip2px(this, 44));
        tv_phone_no.setGravity(Gravity.CENTER_VERTICAL);

        EditText et_phone_no = new EditText(this);
        et_phone_no.requestFocus();
        et_phone_no.setLayoutParams(para_discendant);
        et_phone_no.setHint("该卡银行预留的手机号");
        et_phone_no.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        et_phone_no.setInputType(InputType.TYPE_CLASS_PHONE);
        et_phone_no.setBackgroundResource(0);
        // 设置文字
//        et_phone_no.addTextChangedListener(phoneNoWatcher);

        ImageView iv_delete4 = new ImageView(this);
        iv_delete4.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_delete4.setImageDrawable(ReapalUtil.getResDrawable(BigLayout.this, "deleteIcon.png"));
        iv_delete4.setVisibility(View.INVISIBLE);
        iv_delete4.setPadding(0, 0, dip2px(this, 10), 0);
//        iv_delete4.setOnClickListener(this);

        ImageView iv_about_phone_no = new ImageView(this);
        iv_about_phone_no.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_about_phone_no.setImageDrawable(ReapalUtil.getResDrawable(BigLayout.this, "aboutPhoneNo.png"));
        iv_about_phone_no.setPadding(0, 0, dip2px(this, 20), 0);
//        iv_about_phone_no.setOnClickListener(this);

        et_phone_no.setTextSize(14);
        et_phone_no.setTextColor(Color.BLACK);
        et_phone_no.setPadding(0, 0, 0, 0);
        et_phone_no.setWidth(mWidth * 19 / 40);
        et_phone_no.setHeight(dip2px(this, 44));
        et_phone_no.setGravity(Gravity.CENTER_VERTICAL);

        // 添加创建分割线_____手机号下方
        LinearLayout ll_div8 = new LinearLayout(this);
        ll_div8.setLayoutParams(para_div);
        ll_div8.setOrientation(LinearLayout.HORIZONTAL);
        ll_div8.setBackgroundColor(Color.WHITE);
        ll_div8.setPadding(dip2px(this, 10), 0, 0, 0);

        TextView mDiv_Line_8 = new TextView(this);
        mDiv_Line_8.setHeight(dip2px(this, 0.75F));
        mDiv_Line_8.setWidth(mWidth - dip2px(this, 10));
        mDiv_Line_8.setBackgroundColor(Color.parseColor("#D1D3D5"));

        // 填充四行
        ll_phone_no.addView(tv_phone_no); // 在父类布局中添加第一行，及布局样式
        ll_phone_no.addView(et_phone_no);
        ll_phone_no.addView(iv_delete4);
        ll_phone_no.addView(iv_about_phone_no);
        mLL_bind_dibit.addView(ll_phone_no);
        ll_div8.addView(mDiv_Line_8);
        mLL_bind_dibit.addView(ll_div8);

        //添加短信验证码
        LinearLayout ll_vcode = new LinearLayout(this);
        // 定义宽高和布局方向
        LinearLayout.LayoutParams ll_para_vcode = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        ll_vcode.setLayoutParams(ll_para_vcode);
        ll_vcode.setOrientation(LinearLayout.HORIZONTAL);
        ll_vcode.setBackgroundColor(Color.WHITE);

        TextView tv_captchas = new TextView(this);
        tv_captchas.setLayoutParams(para_tv);
        tv_captchas.setText("验证码"); // 设置文字 颜色 字体
        tv_captchas.setTextSize(13);
        tv_captchas.setTextColor(Color.BLACK);
        tv_captchas.setWidth(mWidth * 3 / 20);
        tv_captchas.setGravity(Gravity.CENTER_VERTICAL);
        tv_captchas.setPadding(dip2px(this, 10), 0, 0, 0);
        ll_vcode.addView(tv_captchas);

        LinearLayout.LayoutParams para_et = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        EditText et_captchas = new EditText(this);
        et_captchas.setLayoutParams(para_et);
        para_et.setMargins(dip2px(this, 7), 0, dip2px(this, 40), 0);
        et_captchas.setHint("输入验证码"); // 设置文字 颜色 字体
        et_captchas.setTextSize(13);
        et_captchas.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        et_captchas.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_captchas.setTextColor(Color.BLACK);
        et_captchas.setWidth(mWidth * 3 / 8);
        et_captchas.setHeight(dip2px(this, 50));
        et_captchas.setBackgroundResource(0);
        et_captchas.setGravity(Gravity.CENTER_VERTICAL);
//        et_captchas.addTextChangedListener(checkCodeWatcher);
        et_captchas.requestFocus();
        ll_vcode.addView(et_captchas);

        TextView tv_cap_div = new TextView(this);
        LinearLayout.LayoutParams para_divhor = new LinearLayout.LayoutParams(
                dip2px(this, 0.75f), dip2px(this, 24));
        para_divhor.setMargins(dip2px(this, 10), dip2px(this, 3),
                dip2px(this, 10), 0);
        tv_cap_div.setLayoutParams(para_divhor);
        // 设置文字 颜色 字体
        tv_cap_div.setTextSize(16);
        tv_cap_div.setBackgroundColor(Color.parseColor("#DADADA"));
        tv_cap_div.setGravity(Gravity.CENTER_VERTICAL);
        ll_vcode.addView(tv_cap_div);

        Button bt_get_captchas = new Button(this);
        LinearLayout.LayoutParams para_bt_send = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        bt_get_captchas.setLayoutParams(para_bt_send);
        bt_get_captchas.setText("获取验证码"); // 设置文字 颜色 字体
        bt_get_captchas.setTextColor(Color.parseColor("#2575CE"));
        bt_get_captchas.setWidth(mWidth * 3 / 8);
        bt_get_captchas.setTextSize(11);
        bt_get_captchas.setPadding(0, 0, 0, 0);
//        time = new TimeCount(60000, 1000);

        bt_get_captchas.setBackgroundColor(Color.WHITE);
        bt_get_captchas.setHeight(dip2px(this, 50));
        bt_get_captchas.setGravity(Gravity.CENTER_VERTICAL);
//        bt_get_captchas.setOnClickListener(this);
        ll_vcode.addView(bt_get_captchas);

        // 添加创建验证码分割线
        TextView vcode_line = new TextView(this);
        vcode_line.setWidth(mWidth - dip2px(this, 10));
        vcode_line.setHeight(dip2px(this, 0.75f));
        vcode_line.setBackgroundColor(Color.parseColor("#D1D3D5"));

        mLL_bind_dibit.addView(ll_vcode);
        mLL_bind_dibit.addView(vcode_line);

        // 单选框 的 父容器
        LinearLayout ll_accept_protocol = new LinearLayout(this);
        ll_accept_protocol.setLayoutParams(para_stripe);
        ll_accept_protocol.setOrientation(LinearLayout.HORIZONTAL);
        ll_accept_protocol.setPadding(dip2px(this, 10), dip2px(this, 5), 0, 0);
        // 单选框
        CheckBox cb_accept = new CheckBox(this);
        cb_accept.setLayoutParams(para_discendant);
        cb_accept.setChecked(true);
        cb_accept.setText("同意");
        cb_accept.setTextSize(10);
        cb_accept.setTextColor(Color.parseColor("#A4A5A5"));

        TextView tv_protocol = new TextView(this);
        tv_protocol.setLayoutParams(para_discendant);
        tv_protocol.setText(" 《融宝快捷支付协议》	");
        tv_protocol.setTextColor(Color.parseColor("#2575CE"));
        tv_protocol.setTextSize(10);
//        tv_protocol.setOnClickListener(this);

        ll_accept_protocol.addView(cb_accept);
        ll_accept_protocol.addView(tv_protocol);
        mLL_bind_dibit.addView(ll_accept_protocol);

        Button btn_bind_card = new Button(this);
        LinearLayout.LayoutParams para_bt_bind_card = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                dip2px(this, 45));
        para_bt_bind_card.gravity = Gravity.CENTER_HORIZONTAL;
        para_bt_bind_card.setMargins(dip2px(this, 20), dip2px(this, 10), dip2px(this, 20), 0);
        btn_bind_card.setLayoutParams(para_bt_bind_card);
        btn_bind_card.setBackgroundColor(Color.parseColor("#B3CDE9")); // B3CDE9
        // 灰蓝
        // 398DEE
        // 蓝色
        // 2575CD
        btn_bind_card.setText("下一步");
        btn_bind_card.setTextSize(14);
        btn_bind_card.setTextColor(Color.WHITE);
        mLL_bind_dibit.addView(btn_bind_card);

        // 下方布局
        RelativeLayout rl_bottom_info = new RelativeLayout(this);
        RelativeLayout.LayoutParams para_rl_bot_info = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        int info2_id = ViewID.generateViewId();
        int info3_id = ViewID.generateViewId();
        TextView tv_info1 = createTextView();
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.ABOVE, info2_id);
        lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        tv_info1.setText("融宝支付是央行颁发支付牌照的第三方支付公司。");

        TextView tv_info2 = createTextView();
        tv_info2.setId(info2_id);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ABOVE, info3_id);
        lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        tv_info2.setText("融宝收银台通过短信验证码、指纹收集等多重手段确保支付安全。");

        TextView tv_info3 = createTextView();
        tv_info3.setId(info3_id);
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp3.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        tv_info3.setText("Copyright©2015融宝支付");
        tv_info3.setPadding(0, 0, 0, dip2px(this, 5));

        rl_bottom_info.addView(tv_info1, lp1);
        rl_bottom_info.addView(tv_info2, lp2);
        rl_bottom_info.addView(tv_info3, lp3);
        mLL_bind_dibit.addView(rl_bottom_info, para_rl_bot_info);
        scrollView.addView(mLL_bind_dibit);
        setContentView(scrollView);

    }

    /**
     * 生成TextView
     */
    private TextView createTextView() {
        TextView tv_info = new TextView(this);
        tv_info.setTextColor(Color.parseColor("#919395"));
        tv_info.setTextSize(8);
        return tv_info;
    }
}
