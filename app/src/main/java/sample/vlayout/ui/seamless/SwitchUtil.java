package sample.vlayout.ui.seamless;

import android.view.View;

import com.shuyu.gsyvideoplayer.listener.GSYMediaPlayerListener;

public class SwitchUtil {

    private static SwitchUtil SWITCH_UTIL;

    private SwitchUtil() {
    }

    /**
     * 单例管理器
     */
    public static synchronized SwitchUtil instance() {
        if (SWITCH_UTIL == null) {
            SWITCH_UTIL = new SwitchUtil();
        }
        return SWITCH_UTIL;
    }

    private SwitchVideo sSwitchVideo;
    private GSYMediaPlayerListener sMediaPlayerListener;

    public void optionPlayer(final SwitchVideo switchVideo, String url, boolean cache, String title) {
        //增加title
        switchVideo.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        switchVideo.getBackButton().setVisibility(View.GONE);
        //设置全屏按键功能
        switchVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchVideo.startWindowFullscreen(switchVideo.getContext(), false, true);
            }
        });
        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
        switchVideo.setAutoFullWithSize(true);
        //音频焦点冲突时是否释放
        switchVideo.setReleaseWhenLossAudio(true);
        //全屏动画
        switchVideo.setShowFullAnimation(false);
        //小屏时不触摸滑动
        switchVideo.setIsTouchWiget(false);

        switchVideo.setSwitchUrl(url);

        switchVideo.setSwitchCache(cache);

        switchVideo.setSwitchTitle(title);
    }


    public void savePlayState(SwitchVideo switchVideo) {
        sSwitchVideo = switchVideo.saveState();
        sMediaPlayerListener = switchVideo;
    }

    public void clonePlayState(SwitchVideo switchVideo) {
        switchVideo.cloneState(sSwitchVideo);
    }

    public void release() {
        if (sMediaPlayerListener != null) {
            sMediaPlayerListener.onAutoCompletion();
        }
        sSwitchVideo = null;
        sMediaPlayerListener = null;
    }
}
