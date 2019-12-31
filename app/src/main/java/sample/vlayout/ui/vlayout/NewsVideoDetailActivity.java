package sample.vlayout.ui.vlayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.SharedElementCallback;
import androidx.core.view.ViewCompat;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.AbstractPlayer;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;

import java.util.List;

import sample.vlayout.R;

import static sample.vlayout.player.PlayerConstant.IntentKeys;
import static sample.vlayout.player.PlayerConstant.Tag;
import static sample.vlayout.ui.vlayout.ListPlayHelper.removeViewFormParent;

/**
 * Title: NewsVideoListFragment
 * <p>
 * Description:
 * </p>
 *
 * @author Changbao
 * @date 2019/12/17  13:49
 */
public class NewsVideoDetailActivity extends AppCompatActivity {
    private boolean isStartTransition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            isStartTransition = true;
        }
        return true;
    }

    private VideoView<AbstractPlayer> mVideoView;

    protected void initView() {
        FrameLayout playerContainer = findViewById(R.id.player_container);
        ViewCompat.setTransitionName(playerContainer, "player_container");
        ActivityCompat.setEnterSharedElementCallback(this, new SharedElementCallback() {
            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                if (isStartTransition) {
                    return;
                }

                //注意以下过程需在共享元素动画结束后执行

                //拿到VideoView实例
                mVideoView = VideoViewManager.instance().get(Tag.SEAMLESS);
                //如果已经添加到某个父容器，就将其移除
                removeViewFormParent(mVideoView);
                //把播放器添加到页面的容器中
                playerContainer.addView(mVideoView);
                //设置新的控制器
                StandardVideoController controller = new StandardVideoController(NewsVideoDetailActivity.this);
                mVideoView.setVideoController(controller);

                Intent intent = getIntent();
                boolean seamlessPlay = intent.getBooleanExtra(IntentKeys.SEAMLESS_PLAY, false);
                String title = intent.getStringExtra(IntentKeys.TITLE);
                controller.addDefaultControlComponent(title, false);
                if (seamlessPlay) {
                    //无缝播放需还原Controller状态
                    controller.setPlayState(mVideoView.getCurrentPlayState());
                    controller.setPlayerState(mVideoView.getCurrentPlayerState());
                } else {
                    //不是无缝播放的情况
                    String url = intent.getStringExtra(IntentKeys.URL);
                    mVideoView.setUrl(url);
                    mVideoView.start();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.resume();
        }
    }

    @Override
    protected void onPause() {
        if (isFinishing()) {
            //移除Controller
            mVideoView.setVideoController(null);
            mVideoView = null;
        }
        if (mVideoView != null) {
            mVideoView.pause();
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (mVideoView == null || !mVideoView.onBackPressed()) {
            supportFinishAfterTransition();
            isStartTransition = true;
        }

        if (mVideoView != null && mVideoView.isFullScreen()) {
            //退出全屏,恢复列表播放状态
            mVideoView.stopFullScreen();
            if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } else {
            //从列表退出,直接页面退出
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.release();
        }
    }
}
