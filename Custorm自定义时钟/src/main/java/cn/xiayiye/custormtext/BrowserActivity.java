package cn.xiayiye.custormtext;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView wv = new WebView(this);
        Uri data = getIntent().getData();
        Log.e("打印路径", data.toString());
        wv.loadUrl(data.toString());
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                //在本页中打开，不调用系统浏览器
                return true;
            }
        });
        setContentView(wv);
    }
}
