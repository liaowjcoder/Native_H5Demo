package com.zeal.native_h5demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String TAG = "zeal";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);

        webView.setWebViewClient(new MyWebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.imooc.com");//file:///android_asset/first.html

        //this 表示将当前类对象注入到 webview 中
        //android 随意起的一个名字，可以让 js 通过该名字访问本类中的方法。
        webView.addJavascriptInterface(this, "android");
    }

    //js 调用 native 代码
    @JavascriptInterface
    public void showToast(String msg) {
        Toast.makeText(this, "msg:" + msg, Toast.LENGTH_SHORT).show();
    }

    public void javaCallJs(View view) {
        String color = "#00ee00";
        //參數使用''包起來
        webView.loadUrl("javascript:changeColor('" + color + "');");
    }


//    private class MyWebViewClient extends WebChromeClient {
//        @Override
//        public void onReceivedTitle(WebView view, String title) {
//            super.onReceivedTitle(view, title);
//            Log.e("zeal","url:"+view.getOriginalUrl());
//        }
//    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {


            if (url != null && Uri.parse(url).getHost().startsWith("m.imooc.com")) {
                Log.e(TAG, "shouldOverrideUrlLoading: " + false);
                return false;
            }
            Log.e(TAG, "shouldOverrideUrlLoading: " + true);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e(TAG, "onPageStarted");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e(TAG, "onPageFinished");
        }
    }

    //向前和后退导航
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
