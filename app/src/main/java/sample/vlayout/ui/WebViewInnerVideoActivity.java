package sample.vlayout.ui;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import sample.vlayout.R;
import sample.vlayout.utils.WebViewUtils;


/**
 * Title: WebViewInnerVideoActivity
 * <p>
 * Description:
 * </p>
 *
 * @author Changbao
 * @date 2020/1/3  15:13
 */
public class WebViewInnerVideoActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_video);

        mWebView = findViewById(R.id.webView);
        WebViewUtils.initWebView(this, mWebView);

        // mWebView.loadUrl("http://www.zqrb.cn/video/gaoduanfangtan/2019-10-14/A1559655439503.html");
        // mWebView.loadUrl("http://www.zqrb.cn/video/caijingshipin/2019-12-27/A1577413800663.html");
        mWebView.loadUrl("http://m.zqrb.cn/video/weifangtan/2020-01-03/A1578021308639.html");
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
