package sample.vlayout;

import android.app.Application;
import android.net.Uri;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;

import java.io.File;

import sample.vlayout.source.CustomSourceTag;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;
import tv.danmaku.ijk.media.exo2.ExoMediaSourceInterceptListener;
import tv.danmaku.ijk.media.exo2.ExoPlayerCacheManager;
import tv.danmaku.ijk.media.exo2.ExoSourceManager;

import static com.google.android.exoplayer2.util.Util.inferContentType;

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

        initDKPlayer();

        //GSYVideoType.enableMediaCodec();
        //GSYVideoType.enableMediaCodecTexture();

        PlayerFactory.setPlayManager(Exo2PlayerManager.class);//EXO模式
        ExoSourceManager.setSkipSSLChain(true);

        CacheFactory.setCacheManager(ExoPlayerCacheManager.class);//exo缓存模式，支持m3u8，只支持exo
        //CacheFactory.setCacheManager(ProxyCacheManager.class);//代理缓存模式，支持所有模式，不支持m3u8等

        //16:9
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL);
        //自定义
//        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_CUSTOM);
//        GSYVideoType.setScreenScaleRatio(9.0f / 16);


        //GSYVideoType.setRenderType(GSYVideoType.SUFRACE);
        //GSYVideoType.setRenderType(GSYVideoType.GLSURFACE);


        ExoSourceManager.setExoMediaSourceInterceptListener(new ExoMediaSourceInterceptListener() {
            @Override
            public MediaSource getMediaSource(String dataSource, boolean preview, boolean cacheEnable, boolean isLooping, File cacheDir) {
                Uri contentUri = Uri.parse(dataSource);
                int contentType = inferContentType(dataSource);
                if (contentType == C.TYPE_HLS) {
                    return new HlsMediaSource.Factory(CustomSourceTag.getDataSourceFactory(MyApplication.this.getApplicationContext(), preview)).createMediaSource(contentUri);
                }
                return null;
            }
        });


    }

    private void initDKPlayer() {
//        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
//                //使用使用IjkPlayer解码
//                //.setPlayerFactory(IjkPlayerFactory.create())
//                //使用ExoPlayer解码
//                .setPlayerFactory(ExoMediaPlayerFactory.create())
//                //使用MediaPlayer解码
//                //.setPlayerFactory(AndroidMediaPlayerFactory.create())
//                .build());

        //如果您想使用默认的网络状态事件生产者，请添加此行配置。
        //并需要添加权限 android.permission.ACCESS_NETWORK_STATE
//        PlayerConfig.setUseDefaultNetworkEventProducer(true);
//        //初始化库
//        PlayerLibrary.init(this);
//
//        //-------------------------------------------
//
//        //如果添加了'cn.jiajunhui:exoplayer:xxxx'该依赖
////        ExoMediaPlayer.init(this);
//
//        //如果添加了'cn.jiajunhui:ijkplayer:xxxx'该依赖
//        IjkPlayer.init(this);
//
//
//        //播放记录的配置
//        //开启播放记录
//        PlayerConfig.playRecord(true);
//        PlayRecordManager.setRecordConfig(
//                new PlayRecordManager.RecordConfig.Builder()
//                        .setMaxRecordCount(100)
//                        //.setRecordKeyProvider()
//                        //.setOnRecordCallBack()
//                        .build());
    }
}
