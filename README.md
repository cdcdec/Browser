####缓存模式

1.LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据

2.LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。

3.LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.

4.LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。


>注意： 每个 Application 只调用一次 WebSettings.setAppCachePath()，WebSettings.setAppCacheMaxSize().


####WebViewClient方法

```txt
WebViewClient mWebViewClient = new WebViewClient()
{
    shouldOverrideUrlLoading(WebView view, String url)  最常用的，比如上面的。
    //在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作。
    //比如获取url，查看url.contains(“add”)，进行添加操作

    shouldOverrideKeyEvent(WebView view, KeyEvent event)
    //重写此方法才能够处理在浏览器中的按键事件。

    onPageStarted(WebView view, String url, Bitmap favicon)
    //这个事件就是开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。

    onPageFinished(WebView view, String url)
    //在页面加载结束时调用。同样道理，我们可以关闭loading 条，切换程序动作。

    onLoadResource(WebView view, String url)
    // 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。

    shouldInterceptRequest(WebView view, String url)
    // 拦截替换网络请求数据,  API 11开始引入，API 21弃用
    shouldInterceptRequest (WebView view, WebResourceRequest request)
    // 拦截替换网络请求数据,  从API 21开始引入

    onReceivedError(WebView view, int errorCode, String description, String failingUrl)
    // (报告错误信息)

    doUpdateVisitedHistory(WebView view, String url, boolean isReload)
    //(更新历史记录)

    onFormResubmission(WebView view, Message dontResend, Message resend)
    //(应用程序重新请求网页数据)

    onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host,String realm)
    //（获取返回信息授权请求）

    onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
    //重写此方法可以让webview处理https请求。

    onScaleChanged(WebView view, float oldScale, float newScale)
    // (WebView发生改变时调用)

    onUnhandledKeyEvent(WebView view, KeyEvent event)
    //（Key事件未被加载时调用）
}


```

####WebChromeClient

WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等 .
```txt
WebChromeClient mWebChromeClient = new WebChromeClient() {

    //获得网页的加载进度，显示在右上角的TextView控件中
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress < 100) {
            String progress = newProgress + "%";
        } else {
        }
    }

    //获取Web页中的title用来设置自己界面中的title
    //当加载出错的时候，比如无网络，这时onReceiveTitle中获取的标题为 找不到该网页,
    //因此建议当触发onReceiveError时，不要使用获取到的title
    @Override
    public void onReceivedTitle(WebView view, String title) {
        MainActivity.this.setTitle(title);
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        //
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        //
        return true;
    }

    @Override
    public void onCloseWindow(WebView window) {
    }

    //处理alert弹出框，html 弹框的一种方式
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        //
        return true;
    }

    //处理confirm弹出框
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult
            result) {
        //
        return true;
    }

    //处理prompt弹出框
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        //
        return true;
    }
};

```

###样式
>http://keeganlee.me/post/android/20150830

>https://github.com/keeganlee/kstyle


###移动端html布局

>http://www.jianshu.com/p/985d26b40199

把这段 原生JS 放到 HTML 的 head 标签中即可（注:不要手动设置viewport，该方案自动帮你设置）。


###二维码识别
>https://github.com/bingoogolapple/BGAQRCode-Android

## 自定义属性说明

属性名 | 说明 | 默认值
:----------- | :----------- | :-----------
qrcv_topOffset         | 扫描框距离 toolbar 底部的距离        | 90dp
qrcv_cornerSize         | 扫描框边角线的宽度        | 3dp
qrcv_cornerLength         | 扫描框边角线的长度        | 20dp
qrcv_cornerColor         | 扫描框边角线的颜色        | @android:color/white
qrcv_rectWidth         | 扫描框的宽度        | 200dp
qrcv_barcodeRectHeight         | 条码扫样式描框的高度        | 140dp
qrcv_maskColor         | 除去扫描框，其余部分阴影颜色        | #33FFFFFF
qrcv_scanLineSize         | 扫描线的宽度        | 1dp
qrcv_scanLineColor         | 扫描线的颜色「扫描线和默认的扫描线图片的颜色」        | @android:color/white
qrcv_scanLineMargin         | 扫描线距离上下或者左右边框的间距        | 0dp
qrcv_isShowDefaultScanLineDrawable         | 是否显示默认的图片扫描线「设置该属性后 qrcv_scanLineSize 将失效，可以通过 qrcv_scanLineColor 设置扫描线的颜色，避免让你公司的UI单独给你出特定颜色的扫描线图片」        | false
qrcv_customScanLineDrawable         | 扫描线的图片资源「默认的扫描线图片样式不能满足你的需求时使用，设置该属性后 qrcv_isShowDefaultScanLineDrawable、qrcv_scanLineSize、qrcv_scanLineColor 将失效」        | null
qrcv_borderSize         | 扫描边框的宽度        | 1dp
qrcv_borderColor         | 扫描边框的颜色        | @android:color/white
qrcv_animTime         | 扫描线从顶部移动到底部的动画时间「单位为毫秒」        | 1000
qrcv_isCenterVertical         | 扫描框是否垂直居中，该属性为true时会忽略 qrcv_topOffset 属性        | false
qrcv_toolbarHeight         | Toolbar 的高度，通过该属性来修正由 Toolbar 导致扫描框在垂直方向上的偏差        | 0dp
qrcv_isBarcode         | 是否是扫条形码        | false
qrcv_tipText         | 提示文案        | null
qrcv_tipTextSize         | 提示文案字体大小        | 14sp
qrcv_tipTextColor         | 提示文案颜色        | @android:color/white
qrcv_isTipTextBelowRect         | 提示文案是否在扫描框的底部        | false
qrcv_tipTextMargin         | 提示文案与扫描框之间的间距        | 20dp
qrcv_isShowTipTextAsSingleLine         | 是否把提示文案作为单行显示        | false
qrcv_isShowTipBackground         | 是否显示提示文案的背景        | false
qrcv_tipBackgroundColor         | 提示文案的背景色        | #22000000
qrcv_isScanLineReverse         | 扫描线是否来回移动        | true
qrcv_isShowDefaultGridScanLineDrawable         | 是否显示默认的网格图片扫描线        | false
qrcv_customGridScanLineDrawable         | 扫描线的网格图片资源        | nulll
qrcv_isOnlyDecodeScanBoxArea         | 是否只识别扫描框区域的二维码        | false

## 接口说明

>QRCodeView

```java
/**
 * 设置扫描二维码的代理
 *
 * @param delegate 扫描二维码的代理
 */
public void setDelegate(Delegate delegate)

/**
 * 显示扫描框
 */
public void showScanRect()

/**
 * 隐藏扫描框
 */
public void hiddenScanRect()

/**
 * 打开后置摄像头开始预览，但是并未开始识别
 */
public void startCamera()

/**
 * 打开指定摄像头开始预览，但是并未开始识别
 *
 * @param cameraFacing  Camera.CameraInfo.CAMERA_FACING_BACK or Camera.CameraInfo.CAMERA_FACING_FRONT
 */
public void startCamera(int cameraFacing)

/**
 * 关闭摄像头预览，并且隐藏扫描框
 */
public void stopCamera()

/**
 * 延迟1.5秒后开始识别
 */
public void startSpot()

/**
 * 延迟delay毫秒后开始识别
 *
 * @param delay
 */
public void startSpotDelay(int delay)

/**
 * 停止识别
 */
public void stopSpot()

/**
 * 停止识别，并且隐藏扫描框
 */
public void stopSpotAndHiddenRect()

/**
 * 显示扫描框，并且延迟1.5秒后开始识别
 */
public void startSpotAndShowRect()

/**
 * 打开闪光灯
 */
public void openFlashlight()

/**
 * 关闭散光灯
 */
public void closeFlashlight()
```

>QRCodeView.Delegate   扫描二维码的代理

```java
/**
 * 处理扫描结果
 *
 * @param result
 */
void onScanQRCodeSuccess(String result)

/**
 * 处理打开相机出错
 */
void onScanQRCodeOpenCameraError()
```

>QRCodeDecoder  解析二维码图片。几个重载方法都是耗时操作，请在子线程中调用。

```java
/**
 * 同步解析本地图片二维码。该方法是耗时操作，请在子线程中调用。
 *
 * @param picturePath 要解析的二维码图片本地路径
 * @return 返回二维码图片里的内容 或 null
 */
public static String syncDecodeQRCode(String picturePath)

/**
 * 同步解析bitmap二维码。该方法是耗时操作，请在子线程中调用。
 *
 * @param bitmap 要解析的二维码图片
 * @return 返回二维码图片里的内容 或 null
 */
public static String syncDecodeQRCode(Bitmap bitmap)
```

>QRCodeEncoder  创建二维码图片。几个重载方法都是耗时操作，请在子线程中调用。

```java
/**
 * 同步创建黑色前景色、白色背景色的二维码图片。该方法是耗时操作，请在子线程中调用。
 *
 * @param content 要生成的二维码图片内容
 * @param size    图片宽高，单位为px
 */
public static Bitmap syncEncodeQRCode(String content, int size)

/**
 * 同步创建指定前景色、白色背景色的二维码图片。该方法是耗时操作，请在子线程中调用。
 *
 * @param content         要生成的二维码图片内容
 * @param size            图片宽高，单位为px
 * @param foregroundColor 二维码图片的前景色
 */
public static Bitmap syncEncodeQRCode(String content, int size, int foregroundColor)

/**
 * 同步创建指定前景色、白色背景色、带logo的二维码图片。该方法是耗时操作，请在子线程中调用。
 *
 * @param content         要生成的二维码图片内容
 * @param size            图片宽高，单位为px
 * @param foregroundColor 二维码图片的前景色
 * @param logo            二维码图片的logo
 */
public static Bitmap syncEncodeQRCode(String content, int size, int foregroundColor, Bitmap logo)

/**
 * 同步创建指定前景色、指定背景色、带logo的二维码图片。该方法是耗时操作，请在子线程中调用。
 *
 * @param content         要生成的二维码图片内容
 * @param size            图片宽高，单位为px
 * @param foregroundColor 二维码图片的前景色
 * @param backgroundColor 二维码图片的背景色
 * @param logo            二维码图片的logo
 */
public static Bitmap syncEncodeQRCode(String content, int size, int foregroundColor, int backgroundColor, Bitmap logo)
```


####http://blog.csdn.net/sk719887916/article/details/44780669