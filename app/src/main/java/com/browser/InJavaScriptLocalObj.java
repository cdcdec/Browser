package com.browser;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.cdc.browser.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by cdc on 2016/12/26.
 */

public class InJavaScriptLocalObj {
    private WebView webView;

    private MainActivity  activity;

    public InJavaScriptLocalObj(WebView webView){

        this.webView=webView;
        activity=(MainActivity)webView.getContext();
    }


    /** 获取html网页内容 */
    @JavascriptInterface
    public void showSource(String html) {
        Document doc = Jsoup.parse(html);
        Elements links = doc.select("input[type='file']");
//        activity.load(links.html());
        Log.i("12345","li2456="+links.text());
        for (Element link : links) {
            String li2 = link.attr("style");
            Log.i("12345","li2="+li2);
        }

    }
}
