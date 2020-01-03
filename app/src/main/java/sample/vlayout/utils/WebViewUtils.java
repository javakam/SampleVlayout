package sample.vlayout.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY;

/**
 * WebView简单配置
 *
 * @author machangbao
 * @date 2019-05-20 16:48:25
 */
public class WebViewUtils {

    public static void initWebView(Context context, WebView webview) {
        initWebView((Activity) context, webview);
    }

    public static void initWebView(Activity activity, WebView webview) {
        if (activity == null || webview == null) {
            return;
        }
        WebSettings webSettings = webview.getSettings();

        webSettings.setAllowUniversalAccessFromFileURLs(true);
        //webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        // url callback
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                Intent intent = new Intent();
                intent.setData(uri);
                // 分析协议 是否打开分享 or 登录
//                if (IntentUtil.diggingIntentData(intent, activity)) {
//                    return true;
//                }

                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
                view.clearHistory();
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
            }

            @Override
            public void onReceivedTitle(WebView view, String title1) {
            }
        });
    }

    public static void loadContent(WebView webView, String source) {
        if (webView != null) {
            final String newSource = TextUtils.isEmpty(source) ? "" : source;
            String url = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                url = Html.fromHtml(newSource, FROM_HTML_MODE_LEGACY).toString();// for 24 api and more
            } else {
                url = Html.fromHtml(newSource).toString();// or for older api
            }

            //检查路径对否合法
            if (Patterns.WEB_URL.matcher(url).matches()) {
                webView.loadUrl(url);
            } else {
                //webView.loadData(newSource, "text/html", "UTF-8");  //乱码
                webView.loadDataWithBaseURL(null, newSource, "text/html", "UTF-8", null);
            }
        }
    }

}