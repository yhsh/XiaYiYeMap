package yxscan.webviewpdf;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;

import yxscan.webviewpdf.viewonlinepdf.util.BASE64Encoder;

public class MainActivity extends Activity {

    private WebView pdfShowWebView;
//    private String docPath = "http://zx.xytxw.com.cn/enterPDF/20180629/3ba04816-7f2c-4495-af87-482eda57030d1530240436378.pdf";
//    private String docPath = "https://dl.google.com/dl/developers/android/pie/Android-9-Pie-Handbook.pdf?hl=zh-cn";
      private String docPath = "http://dev.10086.cn/resource/Android_5.6.6.1.pdf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pdfShowWebView = (WebView) findViewById(R.id.view_web);
        pdfShowWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = pdfShowWebView.getSettings();
        settings.setSavePassword(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setBuiltInZoomControls(true);
        pdfShowWebView.setWebChromeClient(new WebChromeClient());
        if (!"".equals(docPath)) {
            byte[] bytes = null;
            try {// 获取以字符编码为utf-8的字符
                bytes = docPath.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (bytes != null) {
                docPath = new BASE64Encoder().encode(bytes);// BASE64转码
            }
        }
        //下面是打开PDF的方法
        pdfShowWebView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + docPath);
    }
}
