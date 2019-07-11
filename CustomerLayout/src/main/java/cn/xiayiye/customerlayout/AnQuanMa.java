package cn.xiayiye.customerlayout;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static cn.xiayiye.customerlayout.ScaleUtil.dip2px;

/**
 * 创 建 者：下一页5（轻飞扬）
 * 创建时间：2018/3/26.10:44
 * 个人小站：http://wap.yhsh.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268(请用手机QQ添加)
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 空间名称：XiaYiYeMap
 * 项目包名：cn.xiayiye.customerlayout
 */
public class AnQuanMa extends Activity implements View.OnClickListener, LinearLayoutView.KeyBordStateListener {

    private EditText et_cvv2;
    private int mWidth;
    private ImageView iv_delete2;
    private ImageView iv_about_cvv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        // 获取屏幕宽度
        mWidth = DisplaySizeUtil.getDisplaySizeInfo(this).x;

        // 根布局及其参数 定义宽高和布局方向等
        LinearLayoutView mLL_bind_dibit = new LinearLayoutView(this);
        mLL_bind_dibit.setKeyBordStateListener(this);
        mLL_bind_dibit
                .setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        mLL_bind_dibit.setOrientation(LinearLayout.VERTICAL);
        mLL_bind_dibit.setBackgroundColor(Color.parseColor("#F1F3F5"));

        LinearLayout.LayoutParams para_discendant = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams para_stripe = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
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

        et_cvv2 = new EditText(this);
        et_cvv2.setLayoutParams(para_discendant);
        et_cvv2.setHint("该卡背面后三位数字");
        et_cvv2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        et_cvv2.setInputType(InputType.TYPE_CLASS_PHONE);
        et_cvv2.setBackgroundResource(0);
        et_cvv2.addTextChangedListener(cvv2Watcher);

        iv_delete2 = new ImageView(this);
        iv_delete2.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_delete2.setImageDrawable(ReapalUtil.getResDrawable(AnQuanMa.this, "deleteIcon.png"));
        iv_delete2.setVisibility(View.INVISIBLE);
        iv_delete2.setPadding(0, 0, dip2px(this, 10), 0);
        iv_delete2.setOnClickListener(this);

        iv_about_cvv2 = new ImageView(this);
        iv_about_cvv2.setLayoutParams(new ActionBar.LayoutParams(mWidth * 6 / 40, dip2px(this, 20)));
        iv_about_cvv2.setImageDrawable(ReapalUtil.getResDrawable(AnQuanMa.this, "aboutPhoneNo.png"));
        iv_about_cvv2.setPadding(0, 0, dip2px(this, 20), 0);
        iv_about_cvv2.setOnClickListener(this);

        et_cvv2.setTextSize(14);
        et_cvv2.setTextColor(Color.BLACK);
        et_cvv2.setPadding(0, 0, 0, 0);
        et_cvv2.setWidth(mWidth * 19 / 40); // ?
        et_cvv2.setHeight(dip2px(this, 44));
        et_cvv2.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout.LayoutParams para_div = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
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
        mLL_bind_dibit.addView(mDiv_Line_6);
        setContentView(mLL_bind_dibit);
    }

    private TextWatcher cvv2Watcher = new TextWatcher() {// 监听输入的内容
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            iv_delete2.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);// 是否展示清除图片
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
        if (v ==iv_delete2){
            et_cvv2.getText().clear();
        }
    }

    @Override
    public void stateChange(int state) {
        switch (state) {
            case LinearLayoutView.KEYBORAD_HIDE:
//                linearLayout3.setVisibility(View.VISIBLE);
                break;
            case LinearLayoutView.KEYBORAD_SHOW:
//                linearLayout3.setVisibility(View.GONE);
                break;
        }
    }
}
