package com.browser;

import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by cdc on 2016/12/22.
 */

public class CWebSettings{

    private WebView webView;

    private WebSettings  mWebSettings;

    public CWebSettings(WebView webView){
        this.webView=webView;
        mWebSettings=webView.getSettings();
    }


    public  void init(){
        //支持缩放，默认为true
        mWebSettings.setSupportZoom(true);
        //设置内置的缩放控件。
        mWebSettings.setBuiltInZoomControls(true);
        //若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
        //隐藏原生的缩放控件
        mWebSettings.setDisplayZoomControls(false);
        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        mWebSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        mWebSettings.setLoadWithOverviewMode(true);
        //设置编码格式
        mWebSettings.setDefaultTextEncodingName("utf-8");
        //支持自动加载图片
        mWebSettings.setLoadsImagesAutomatically(true);
        //支持内容重新布局
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);
        //多窗口
        mWebSettings.setSupportMultipleWindows(true);
        //设置可以访问文件
        mWebSettings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        mWebSettings.setNeedInitialFocus(true);
        //支持通过JS打开新窗口
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);


        mWebSettings.setAllowContentAccess(true);
    }

    /***设置缓存***/
    public  void  setCache(){
        if (NetStatusUtil.isConnected(webView.getContext().getApplicationContext())) {
            //根据cache-control决定是否从网络上取数据。
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
        mWebSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        mWebSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        mWebSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        if(!GlobalWebSettings.hasSet){
            String appCachePath = webView.getContext().getCacheDir().getAbsolutePath();
            String cacheDirPath = webView.getContext().getFilesDir().getAbsolutePath() + appCachePath;
            mWebSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
            GlobalWebSettings.hasSet=true;
        }

    }


}
