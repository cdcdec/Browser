package com.browser;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cdc.browser.MainActivity;
import com.cdc.browser.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by cdc on 2016/12/22.
 */

public class CWebChromeClient  extends WebChromeClient {

    private String title = "";

    private WebView webView;

    private ProgressBar pbar;





    private View myView = null;
    private CustomViewCallback myCallback = null;

    private MainActivity activity;


    public CWebChromeClient(WebView webView,ProgressBar pbar){
        this.webView=webView;
        this.pbar=pbar;
        activity=(MainActivity) webView.getContext();

    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (myCallback != null) {
            myCallback.onCustomViewHidden();
            myCallback = null;
            return;
        }

        ViewGroup parent = (ViewGroup) webView.getParent();
        parent.removeView(webView);
        parent.addView(view);
        myView = view;
        myCallback = callback;
    }

    @Override
    public void onHideCustomView() {
        if (myView != null) {
            if (myCallback != null) {
                myCallback.onCustomViewHidden();
                myCallback = null;
            }

            ViewGroup parent = (ViewGroup) myView.getParent();
            parent.removeView(myView);
            parent.addView(webView);
            myView = null;
        }
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message,
                             JsResult result) {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                view.getContext());
        builder.setTitle("Ifaboo提示").setMessage(message)
                .setPositiveButton("确定", null);
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher);
        AlertDialog dialog = builder.create();
        dialog.show();
        result.confirm();
        return true;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        // TODO Auto-generated method stub
        this.title = title;
        super.onReceivedTitle(view, title);
    }

    public String getTitle() {

        return title;
    }





    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        // TODO Auto-generated method stub
        super.onProgressChanged(view, newProgress);
        pbar.setProgress(newProgress);
    }


  //The undocumented magic method override
    //Eclipse will swear at you if you try to put @Override here
   // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {

        activity.mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image*//*");
        activity.startActivityForResult(Intent.createChooser(i, "File Chooser"),
                activity.RESULT_CODE_ICE_CREAM);

    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        activity.mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("**/*//*");
        activity.startActivityForResult(Intent.createChooser(i, "File Browser"),
                activity.RESULT_CODE_ICE_CREAM);
    }

    //For Android 4.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        activity.mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image*//*");
        activity.startActivityForResult(Intent.createChooser(i, "File Chooser"),
                activity.RESULT_CODE_ICE_CREAM);

    }

    //For Android5.0+
    public boolean onShowFileChooser(
            WebView webView, ValueCallback<Uri[]> filePathCallback,
            FileChooserParams fileChooserParams) {
        if (activity.mFilePathCallback != null) {
            activity.mFilePathCallback.onReceiveValue(null);
        }
        activity.mFilePathCallback = filePathCallback;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                takePictureIntent.putExtra("PhotoPath", activity.mCameraPhotoPath);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                activity.mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
            } else {
                takePictureIntent = null;
            }
        }

        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image*//*");

        Intent[] intentArray;
        if (takePictureIntent != null) {
            intentArray = new Intent[]{takePictureIntent};
        } else {
            intentArray = new Intent[0];
        }

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

        activity.startActivityForResult(chooserIntent, activity.REQUEST_CODE_LOLIPOP);

        return true;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }





}
