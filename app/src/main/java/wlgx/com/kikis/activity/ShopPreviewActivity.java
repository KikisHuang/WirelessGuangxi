package wlgx.com.kikis.activity;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;

import com.tencent.smtt.sdk.CacheManager;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.io.File;
import java.net.URL;

import wlgx.com.kikis.R;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.view.OutSideWebViewClient;

import static wlgx.com.kikis.utils.SynUtils.setTitles;
import static wlgx.com.kikis.view.CustomProgress.Cancle;
import static wlgx.com.kikis.view.CustomProgress.Show;

/**
 * Created by lian on 2017/9/18.
 */
public class ShopPreviewActivity extends InitActivity {

    private static final String TAG = "ShopPreviewActivity";
    public static WebView webView;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.shop_preview_layout);
        setTitles(this,"店铺预览");
        webView = f(R.id.webView);
        webinit();
//        syncCookie(this, MzFinal.SHOPURL);
        webView.loadUrl(MzFinal.SHOPURL);
        Log.i(TAG, "MzFinal.SHOPURL =====" + MzFinal.SHOPURL);
    }

    @Override
    protected void initData() {
        Show(this, "加载中", true, null);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new OutSideWebViewClient());

    }

    private void webinit() {
//        webView.setInitialScale(50);//这里一定要设置，数值可以根据各人的需求而定，我这里设置的是50%的缩放

        WebSettings webSettings = webView.getSettings();

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
//        webSettings.setBuiltInZoomControls(true);// support zoom
        webSettings.setUseWideViewPort(true);// 这个很关键
        webSettings.setLoadWithOverviewMode(true);
        webView.goBack();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
    }

    public void destroyWebView() {
        Cancle();
        File file = CacheManager.getCacheFileBaseDir();

        if (file != null && file.exists() && file.isDirectory()) {
            for (File item : file.listFiles()) {
                item.delete();
            }
            file.delete();
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        }

        if (webView != null) {
          webView.clearHistory();
            webView.clearCache(true);
            webView.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            webView.freeMemory();
//            webView.pauseTimers();
            webView.removeAllViews();
            webView.destroy();
            webView = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }
    }

    public void syncCookie(Context context, String url) {
        try {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();
            String oldCookie = cookieManager.getCookie(url);

            URL aURL = new URL(url);

            StringBuilder sbCookie = new StringBuilder();
            sbCookie.append(String.format("CookieName" + "=%s", "Your saved Cookie Value."));
            //webview在使用cookie前会前判断保存cookie的domain和当前要请求的domain是否相同，相同才会发送cookie
            sbCookie.append(String.format(";domain=%s", aURL.getHost())); //注意，是getHost()，不是getAuthority(),
            sbCookie.append(String.format(";path=%s", "/"));

            String cookieValue = sbCookie.toString();
            cookieManager.setCookie(url, cookieValue);
            CookieSyncManager.getInstance().sync();

            String newCookie = cookieManager.getCookie(url);
            Log.i(TAG,"newCookie ===="+newCookie);
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else
            finish();
        return super.onKeyDown(keyCode, event);
    }
}
