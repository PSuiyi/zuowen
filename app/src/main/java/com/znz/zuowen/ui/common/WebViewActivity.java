package com.znz.zuowen.ui.common;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.utils.ZnzLog;
import com.znz.compass.znzlibray.views.WebViewWithProgress;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;

import butterknife.Bind;


/**
 * Created by panqiming on 2016/10/29.
 */

public class WebViewActivity extends BaseAppActivity {

    @Bind(R.id.wvHtml)
    WebViewWithProgress wvHtml;
    private String url;
    private String title;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_webview, 1};
    }


    @Override
    protected void initializeVariate() {
        if (getIntent().hasExtra("url")) {
            url = getIntent().getStringExtra("url");
        }
        if (getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");
        }

        ZnzLog.e("receive url:" + url);
    }

    @Override
    protected void initializeNavigation() {
        if (!StringUtil.isBlank(title)) {
            setTitleName(title);
        } else {
            setTitleName(getResources().getString(R.string.app_name));
        }
    }

    @Override
    protected void initializeView() {
        WebSettings settings = wvHtml.getSettings();
        settings.setSupportZoom(true);//是否支持缩放,默认为true
        settings.setBuiltInZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //版本号控制，使图片能够适配
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion <= 19) {
        } else {
            settings.setUseWideViewPort(true);
        }

        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        wvHtml.loadUrl(url);
        wvHtml.setWebViewClient(new WebViewClient() {
            // 点击网页里面的链接还是在当前的webView内部跳转，不跳转外部浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            // 可以让webView处理https请求
            @Override
            public void onReceivedSslError(WebView view, android.webkit.SslErrorHandler handler, android.net.http.SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
    }

    @Override
    protected void loadDataFromServer() {

    }
}
