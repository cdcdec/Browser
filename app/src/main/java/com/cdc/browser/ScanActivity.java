package com.cdc.browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by cdc on 2016/12/27.
 */

public class ScanActivity extends Activity implements QRCodeView.Delegate{


    private QRCodeView mQRCodeView;

    private int resultCode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);

        mQRCodeView.setDelegate(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
       // Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        mQRCodeView.stopSpot();
        Intent intent=new Intent();
        intent.putExtra("scan_url",result);
        setResult(resultCode,intent);
        finish();

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }


}
