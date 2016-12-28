package com.browser;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cdc.browser.MainActivity;

/**
 * Created by cdc on 2016/12/22.
 */

public class CWebViewClient extends WebViewClient{

    private WebView webView;

    private ProgressBar pbar;

    private boolean isLoadUrl = false;

    public CWebViewClient(WebView webView,ProgressBar pbar){
        this.webView=webView;
        this.pbar=pbar;
    }
    @TargetApi(Build.VERSION_CODES.N)
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url=request.getUrl().toString();
        view.loadUrl(url);
        MainActivity  mainActivity= (MainActivity) webView.getContext();
       // Toast.makeText(mainActivity,"地址2="+url,Toast.LENGTH_SHORT).show();
        Log.i("12345","url2="+url);
        mainActivity.getEdtTitle().setText(url);
        return true;
    }

    @SuppressWarnings("deprecation")
    //打开网页时不调用系统浏览器， 而是在本WebView中显示
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        MainActivity  mainActivity= (MainActivity) webView.getContext();
        //Toast.makeText(mainActivity,"地址="+url,Toast.LENGTH_SHORT).show();
        Log.i("12345","url="+url);
        mainActivity.getEdtTitle().setText(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        pbar.setVisibility(View.VISIBLE);
        pbar.setProgress(0);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        view.loadUrl("javascript:window.myObj.showSource(document.getElementsByTagName('html')[0].innerHTML);");
        super.onPageFinished(view, url);
        pbar.setVisibility(View.GONE);
    }
}
