package sample.vlayout.ui.vlayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.LiveControlView;
import com.dueeeke.videocontroller.component.PrepareView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.AbstractPlayer;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.util.L;

import sample.vlayout.R;

import static sample.vlayout.player.PlayerConstant.IntentKeys;

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
//    private boolean isStartTransition;

//    private VideoListEntity.DataBean bean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        initPlayer();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            supportFinishAfterTransition();
//            isStartTransition = true;
//        }
//        return true;
//    }

    private VideoView<AbstractPlayer> mVideoView;
    private static final String THUMB = "https://cms-bucket.nosdn.127.net/eb411c2810f04ffa8aaafc42052b233820180418095416.jpeg";

    protected void initPlayer() {
        mVideoView = findViewById(R.id.player);

//        FrameLayout playerContainer = findViewById(R.id.player_container);
//        ViewCompat.setTransitionName(playerContainer, "player_container");
//        ActivityCompat.setEnterSharedElementCallback(this, new SharedElementCallback() {
//            @Override
//            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
//                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
//                if (isStartTransition) {
//                    return;
//                }
//
//                //注意以下过程需在共享元素动画结束后执行
//
//                //拿到VideoView实例
//                mVideoView = VideoViewManager.instance().get(Tag.SEAMLESS);
//                //如果已经添加到某个父容器，就将其移除
//                removeViewFormParent(mVideoView);
//                //把播放器添加到页面的容器中
//                playerContainer.addView(mVideoView);
//                //设置新的控制器
//                StandardVideoController controller = new StandardVideoController(NewsVideoDetailActivity.this);
//                mVideoView.setVideoController(controller);
//
//                Intent intent = getIntent();
//                boolean seamlessPlay = intent.getBooleanExtra(IntentKeys.SEAMLESS_PLAY, false);
//                String title = intent.getStringExtra(IntentKeys.TITLE);
//                controller.addDefaultControlComponent(title, false);
//                if (seamlessPlay) {
//                    //无缝播放需还原Controller状态
//                    controller.setPlayState(mVideoView.getCurrentPlayState());
//                    controller.setPlayerState(mVideoView.getCurrentPlayerState());
//                } else {
//                    //不是无缝播放的情况
//                    String url = intent.getStringExtra(IntentKeys.URL);
//                    mVideoView.setUrl(url);
//                    mVideoView.start();
//                }
//            }
//        });

        Intent intent = getIntent();
        if (intent != null) {
//            bean= (VideoListEntity.DataBean) intent.getSerializableExtra(IntentKeys.VIDEO_BEAN);

            StandardVideoController controller = new StandardVideoController(this);
            //根据屏幕方向自动进入/退出全屏
            controller.setEnableOrientation(true);

            PrepareView prepareView = new PrepareView(this);//准备播放界面

            String cover = intent.getStringExtra(IntentKeys.COVER);
            ImageView thumb = prepareView.findViewById(R.id.thumb);//封面图
            Glide.with(this).load(cover).into(thumb);
            controller.addControlComponent(prepareView);

            controller.addControlComponent(new CompleteView(this));//自动完成播放界面

            controller.addControlComponent(new ErrorView(this));//错误界面

            //标题栏
            TitleView titleView = new TitleView(this);
            String title = intent.getStringExtra(IntentKeys.TITLE);
            titleView.setTitle(title);
            controller.addControlComponent(titleView);

            //根据是否为直播设置不同的底部控制条
            boolean isLive = intent.getBooleanExtra(IntentKeys.IS_LIVE, false);
            if (isLive) {
                controller.addControlComponent(new LiveControlView(this));//直播控制条
            } else {
                VodControlView vodControlView = new VodControlView(this);//点播控制条
                //是否显示底部进度条。默认显示
//                vodControlView.showBottomProgress(false);
                controller.addControlComponent(vodControlView);
            }

            GestureView gestureControlView = new GestureView(this);//滑动控制视图
            controller.addControlComponent(gestureControlView);
            //根据是否为直播决定是否需要滑动调节进度
            controller.setCanChangePosition(!isLive);

            //注意：以上组件如果你想单独定制，我推荐你把源码复制一份出来，然后改成你想要的样子。
            //改完之后再通过addControlComponent添加上去
            //你也可以通过addControlComponent添加一些你自己的组件，具体实现方式参考现有组件的实现。
            //这个组件不一定是View，请发挥你的想象力😃

            //如果你不需要单独配置各个组件，可以直接调用此方法快速添加以上组件
//            controller.addDefaultControlComponent(title, isLive);

            //竖屏也开启手势操作，默认关闭
//            controller.setEnableInNormal(true);
            //滑动调节亮度，音量，进度，默认开启
//            controller.setGestureEnabled(false);
            //适配刘海屏，默认开启
//            controller.setAdaptCutout(false);

            //如果你不想要UI，不要设置控制器即可
            mVideoView.setVideoController(controller);


            String videoUrl = intent.getStringExtra(IntentKeys.URL);
            mVideoView.setUrl(videoUrl);

            //保存播放进度
//            mVideoView.setProgressManager(new ProgressManagerImpl());
            //播放状态监听
//            mVideoView.addOnVideoViewStateChangeListener(mOnVideoViewStateChangeListener);

            //临时切换播放核心，如需全局请通过VideoConfig配置，详见MyApplication
            //使用IjkPlayer解码
//            mVideoView.setPlayerFactory(IjkPlayerFactory.create());
            //使用ExoPlayer解码
//            mVideoView.setPlayerFactory(ExoMediaPlayerFactory.create());
            //使用MediaPlayer解码
//            mVideoView.setPlayerFactory(AndroidMediaPlayerFactory.create());


            mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT);
//            mVideoView.setOnStateChangeListener(mOnStateChangeListener);


            mVideoView.start();
        }

    }

    private VideoView.OnStateChangeListener mOnStateChangeListener = new VideoView.SimpleOnStateChangeListener() {
        @Override
        public void onPlayerStateChanged(int playerState) {
            switch (playerState) {
                case VideoView.PLAYER_NORMAL://小屏
                    break;
                case VideoView.PLAYER_FULL_SCREEN://全屏
                    break;
                default:
            }
        }

        @Override
        public void onPlayStateChanged(int playState) {
            switch (playState) {
                case VideoView.STATE_IDLE:
                    break;
                case VideoView.STATE_PREPARING:
                    break;
                case VideoView.STATE_PREPARED:
                    break;
                case VideoView.STATE_PLAYING:
                    //需在此时获取视频宽高
                    int[] videoSize = mVideoView.getVideoSize();
                    L.d("视频宽：" + videoSize[0]);
                    L.d("视频高：" + videoSize[1]);
                    break;
                case VideoView.STATE_PAUSED:
                    break;
                case VideoView.STATE_BUFFERING:
                    break;
                case VideoView.STATE_BUFFERED:
                    break;
                case VideoView.STATE_PLAYBACK_COMPLETED:
                    break;
                case VideoView.STATE_ERROR:
                    break;
                default:
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
            mVideoView.release();
            mVideoView = null;
        }
        if (mVideoView != null) {
            mVideoView.pause();
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
//        if (mVideoView == null || !mVideoView.onBackPressed()) {
//            supportFinishAfterTransition();
//            isStartTransition = true;
//        }

        if (mVideoView != null && mVideoView.isFullScreen()) {
            //退出全屏,恢复列表播放状态
            mVideoView.stopFullScreen();
            if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } else {
            //从列表退出,直接页面退出
            if (mVideoView!=null) {
                mVideoView.release();
            }
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
