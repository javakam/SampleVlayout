package sample.vlayout;

import android.app.Application;

import com.dueeeke.videoplayer.BuildConfig;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;

/**
 * Title: MyApplication
 * <p>
 * Description:
 * </p>
 *
 * @author Changbao
 * @date 2019/12/13  16:53
 */
public class MyApplication extends Application {
    private static MyApplication INSTANCE;

    public static MyApplication get() {
        return INSTANCE;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;

        initMediaPlayer();

    }

    private void initMediaPlayer() {
        //播放器配置，注意：此为全局配置，按需开启
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setLogEnabled(BuildConfig.DEBUG)//调试的时候请打开日志，方便排错
                .setPlayerFactory(IjkPlayerFactory.create())
//                .setPlayerFactory(ExoMediaPlayerFactory.create())
//                .setRenderViewFactory(SurfaceRenderViewFactory.create())
//                .setEnableOrientation(true)
//                .setEnableAudioFocus(false)
//                .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
//                .setAdaptCutout(false)
//                .setPlayOnMobileNetwork(true)
//                .setProgressManager(new ProgressManagerImpl())
                .build());

//        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
//        }
    }
}
