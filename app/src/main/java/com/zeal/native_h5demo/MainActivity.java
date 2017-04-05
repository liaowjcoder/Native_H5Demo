package com.zeal.native_h5demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/first.html");

        //this 表示将当前类对象注入到 webview 中
        //android 随意起的一个名字，可以让 js 通过该名字访问本类中的方法。
        webView.addJavascriptInterface(this, "android");
    }

    //js 调用 native 代码
    @JavascriptInterface
    public void showToast(String msg) {
        Toast.makeText(this, "msg:"+msg, Toast.LENGTH_SHORT).show();
    }

    public void javaCallJs(View view) {
        String color = "#00ee00";
        //參數使用''包起來
        webView.loadUrl("javascript:changeColor('" + color + "');");
    }
}
