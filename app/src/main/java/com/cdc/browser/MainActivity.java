package com.cdc.browser;



import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.browser.CWebChromeClient;
import com.browser.CWebSettings;
import com.browser.CWebViewClient;
import com.browser.InJavaScriptLocalObj;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements View.OnClickListener{

    private WebView webView;

    private EditText edtTitle;

    private TextView txtRefresh;

    private ProgressBar pbar;

    public  final int REQUEST_CODE_SCAN = 0;

    private boolean isOnPause = false;
    //========================File=========================================
    public  final int REQUEST_CODE_LOLIPOP = 1;
    public final  int RESULT_CODE_ICE_CREAM = 2;

    public ValueCallback<Uri[]> mFilePathCallback;
    public String mCameraPhotoPath;

    public ValueCallback<Uri> mUploadMessage;

    //========================File==========================================







    public EditText getEdtTitle() {
        return edtTitle;
    }

    public void setEdtTitle(EditText edtTitle) {
        this.edtTitle = edtTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
        String   path="file:///android_asset/index.html";
        webView.loadUrl(path);
        edtTitle.setText(path);



    }

    private void initView(){
        webView= (WebView) findViewById(R.id.webView);
        pbar= (ProgressBar) findViewById(R.id.pbar);
        txtRefresh= (TextView) findViewById(R.id.txtRefresh);
        edtTitle= (EditText)findViewById(R.id.edtTitle);

        txtRefresh.setOnClickListener(this);
        edtTitle.setOnClickListener(this);
    }

    private void init(){
       CWebSettings  cWebSettings=new CWebSettings(webView);
        cWebSettings.init();
        CWebViewClient cWebViewClient=new CWebViewClient(webView,pbar);
        webView.setWebViewClient(cWebViewClient);
        CWebChromeClient cWebChromeClient=new CWebChromeClient(webView,pbar);
        webView.setWebChromeClient(cWebChromeClient);
        InJavaScriptLocalObj inJavaScriptLocalObj=new InJavaScriptLocalObj(webView);
        webView.addJavascriptInterface(inJavaScriptLocalObj,"myObj");
    }


    public void  load(String  html){
        webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }

    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            String  currentUrl=webView.getOriginalUrl();
            edtTitle.setText(currentUrl);
            webView.onResume();
            //Toast.makeText(this,currentUrl,Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }


    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch(id){
            case R.id.txtRefresh:
                //webView.reload();
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,ScanActivity.class);
                startActivityForResult(intent,REQUEST_CODE_SCAN);
                break;
            case R.id.edtTitle:
                String  currentUrl=edtTitle.getText().toString();
               enterUrl(currentUrl);
                break;
            default:
                break;

        }

    }

    /***输入网址***/
    private void enterUrl(String currentUrl){
        new MaterialDialog.Builder(MainActivity.this)
                .title("请输入网址")
                .inputType(InputType.TYPE_TEXT_VARIATION_URI)
                .positiveText("进入")
                .inputRange(11,180)
                .autoDismiss(false)
                //不点击确定按钮就会执行:alwaysCallInputCallback()
               //.alwaysCallInputCallback() // this forces the callback to be invoked with every input change
                .input("网址", currentUrl, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        String  tempInput=input.toString();
                        if(tempInput.equals("file:///android_asset/index.html")){
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                            dialog.dismiss();
                            webView.loadUrl(tempInput);
                            edtTitle.setText(tempInput);
                        }else if (!((tempInput.startsWith("http://"))||(tempInput.startsWith("https://")))) {
                            //dialog.setContent("网址不合法,请重新输入!");
                            Toast.makeText(MainActivity.this,"网址不合法,请重新输入!",Toast.LENGTH_SHORT).show();
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        }else {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                            dialog.dismiss();
                            webView.loadUrl(tempInput);
                            edtTitle.setText(tempInput);
                        }
                    }
                }).show();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SCAN:
                if(data!=null){
                    String scanUrl = data.getStringExtra("scan_url");
                    webView.removeAllViews();
                    webView.onResume();
                    edtTitle.setText(scanUrl);
                    webView.loadUrl(scanUrl);
                }

                break;
            case RESULT_CODE_ICE_CREAM:
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();
                }
                mUploadMessage.onReceiveValue(uri);
                mUploadMessage = null;
                break;
            case REQUEST_CODE_LOLIPOP:
                Uri[] results = null;
                // Check that the response is a good one
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        // If there is not data, then we may have taken a photo
                        if (mCameraPhotoPath != null) {
                            results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                        }
                    } else {
                        String dataString = data.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }

                mFilePathCallback.onReceiveValue(results);
                mFilePathCallback = null;
                break;
        }
    }


    /**
     * 当Activity执行onPause()时让WebView执行pause
     */
    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (webView != null) {
                webView.onPause();
                isOnPause = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 当Activity执行onResume()时让WebView执行resume
     */
    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (isOnPause) {
                if (webView != null) {
                    webView.onResume();
                }
                isOnPause = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.reload ();
            webView.getSettings().setBuiltInZoomControls(true);
            webView.setVisibility(View.GONE);
            webView.destroy();
            webView = null;
        }
        isOnPause = false;
    }




}
