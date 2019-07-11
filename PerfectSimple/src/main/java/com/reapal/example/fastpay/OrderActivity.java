package com.reapal.example.fastpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.reapal.utils.PayTask;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class OrderActivity extends Activity implements OnClickListener {
    private EditText et_total_fee;
    private EditText et_title;
    private EditText et_body;
    private Button bt_reapal_pay;
    private EditText et_member_id;
    private TextView tv_result_data;
    private EditText et_merchant_id;
    private CheckBox cb_formal;
    private String member_id;
    private String title;
    private String body;
    private String merchant_id;

    private String private_key;
    private String key_store;
    private String key;
    private EditText un_credit_code;//店铺信用码
    private String trim_un_credit_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reapal_main);
        initView();
    }

    private void initView() {
        cb_formal = (CheckBox) findViewById(R.id.cb_formal);
        et_title = (EditText) findViewById(R.id.et_title);
        et_body = (EditText) findViewById(R.id.et_body);
        et_total_fee = (EditText) findViewById(R.id.et_total_fee);
        et_total_fee.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_member_id = (EditText) findViewById(R.id.et_member_id);
        et_merchant_id = (EditText) findViewById(R.id.et_merchant_id);
        un_credit_code = (EditText) findViewById(R.id.et_un_credit_code);
        bt_reapal_pay = (Button) findViewById(R.id.bt_reapal_pay);
        bt_reapal_pay.setOnClickListener(this);
        tv_result_data = (TextView) findViewById(R.id.tv_result);
        cb_formal.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton arg0, boolean checked) {
                et_merchant_id.getText().clear();
                if (checked) {
                    et_merchant_id.setText(MerchantConstant.MERCHANT_ID531);
                    un_credit_code.setText(MerchantConstant.SHOP_CODE531);
                    Toast.makeText(OrderActivity.this, "当前为正式环境", Toast.LENGTH_SHORT).show();
                } else {
                    un_credit_code.setText(MerchantConstant.SHOP_CODE147);
                    et_merchant_id.setText(MerchantConstant.MERCHANT_ID147);
                    Toast.makeText(OrderActivity.this, "当前为测试/联调环境", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_reapal_pay.setBackground(getResources().getDrawable(R.drawable.bg));
    }

    @Override
    public void onClick(View v) {
        title = et_title.getText().toString();
        body = et_body.getText().toString();
        member_id = et_member_id.getText().toString();
        merchant_id = et_merchant_id.getText().toString().trim();
        trim_un_credit_code = un_credit_code.getText().toString().trim();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(body) || TextUtils.isEmpty(trim_un_credit_code) || TextUtils.isEmpty(et_total_fee.getText().toString())
                || TextUtils.isEmpty(member_id) || TextUtils.isEmpty(merchant_id)) {
            Toast.makeText(this, "请输入上方信息!", Toast.LENGTH_SHORT).show();
        } else {
            Integer money = Integer.valueOf(et_total_fee.getText().toString().trim());
            if (money >= 1) {
                if (et_total_fee.getText().toString().trim().startsWith("0")) {
                    Toast.makeText(OrderActivity.this, "请输入金额不能已0开头", Toast.LENGTH_SHORT).show();
                } else {
                    // 配置商户号。 这里实际接入中,可以去掉输入框,单独配置商户号和安全校验码(ReapalConfig.merchant_id 和 ReapalConfig.key)
                    if (merchant_id.equals("100000000000147")) {
                        private_key = MerchantConstant.PRIVATE_KEY147;
                        key_store = MerchantConstant.KEY_STORE147;
                        key = MerchantConstant.KEY_147;
                    } else if (merchant_id.equals("100000000071531")) { // 正式环境
                        private_key = MerchantConstant.PRIVATE_KEY531;
                        key_store = MerchantConstant.KEY_STORE531;
                        key = MerchantConstant.KEY_531;
                    }
                    onReapalPay(); // 调用融宝支付的方法。
                }
            } else {
                Toast.makeText(OrderActivity.this, "请输入金额最小为1分", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * --- 通过融宝支付的方法: 准备初始化、加密数据 ---
     */
    private void onReapalPay() {
        /**
         * 以下是整合所需的参数
         * */
        Map<String, String> map = new HashMap<>();
        map.put("merchant_id", merchant_id);//商户号
        map.put("order_no", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));//自己生成的订单号
        map.put("total_fee", et_total_fee.getText().toString());//金额最小为整数1分
        map.put("transtime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//交易时间
        map.put("title", title);//商品名称
        map.put("body", body);//商品描述
        map.put("seller_email", MerchantConstant.SELLER_EMAIL);//签约融宝支付账号或卖家收款融宝支付帐户
        map.put("notify_url", MerchantConstant.NOTIFY_URL);//通知地址，由商户提供
        map.put("member_no", member_id);//融宝会员号
        map.put("un_credit_code", trim_un_credit_code);//店铺信用码
        //下面是调起完美账单的入口
        PayTask.getInstance(private_key, key_store, key).start(OrderActivity.this, map, cb_formal.isChecked());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tv_result_data.setText("");
        if (resultCode == PayTask.RESULT_SUCCESS && data != null) {
            String total = data.getStringExtra("total_fee");
            String total_fee = fen2Yuan(total);
            String order_no = data.getStringExtra("order_no");
            String result_code = data.getStringExtra("result_code");
            String status = data.getStringExtra("status");
            String merchant_id = data.getStringExtra("merchant_id");
            if (!TextUtils.isEmpty(total_fee) && !TextUtils.isEmpty(order_no)) {
                String return_message = getString(R.string.return_order) + getString(R.string.pay_money) + total_fee + getString(R.string.RMB) + getString(R.string.order) + order_no +
                        getString(R.string.return_code) + result_code +
                        getString(R.string.return_status) + status +
                        getString(R.string.return_merchant_id) + merchant_id;
                tv_result_data.setText(return_message);
            }
            if (status.equals("success")) {
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
            } else if (status.equals("processing")) {
                Toast.makeText(this, "支付处理中……", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "出错了", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 分-->元  转换-用于显示
     */
    public static String fen2Yuan(String total_fee) {
        DecimalFormat df = new DecimalFormat("#,##0.00 ");
        return df.format(new BigDecimal(Double.parseDouble(total_fee)).movePointLeft(2));
    }
}
