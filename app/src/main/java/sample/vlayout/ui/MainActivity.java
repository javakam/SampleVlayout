package sample.vlayout.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import sample.vlayout.R;
import sample.vlayout.ui.vlayout.FragmentContainerActivity;
import sample.vlayout.ui.vlayout.NewsMediaListFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListeners();
    }

    private void initListeners() {
        findViewById(R.id.tv_zqrb).setOnClickListener(this);
        findViewById(R.id.tv_web_video).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
//            case R.id.tv_detail_exo:
//                break;
//            case R.id.tv_detail_exo_list:
//                break;
            case R.id.tv_zqrb:
                Intent intent = new Intent(this, FragmentContainerActivity.class);
                intent.putExtra(FragmentContainerActivity.FRAGMENT_NAME, NewsMediaListFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.tv_web_video:
                startActivity(new Intent(this, WebViewInnerVideoActivity.class));
                break;
            default:
        }
    }

}