package sample.vlayout.ui.vlayout;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.extend.LayoutManagerCanScrollListener;
import com.alibaba.android.vlayout.extend.PerformanceMonitor;
import com.alibaba.android.vlayout.extend.ViewLifeCycleListener;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.PrepareView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.VideoView;

import sample.vlayout.R;
import sample.vlayout.bean.VideoBean;
import sample.vlayout.ui.vlayout.adapter.BaseViewHolder;
import sample.vlayout.ui.vlayout.entity.VideoListEntity;

/**
 * Title: ListPlayHelper
 * <p>
 * Description: 列表播放辅助类
 * </p>
 *
 * @author Changbao
 * @date 2019/12/26  10:03
 */
public class ListPlayHelper {

    private Activity activity;

    private TitleView mTitleView;
    private VideoView mVideoView;
    private StandardVideoController mController;

//    private VideoBean mVideoBean;

    private VirtualLayoutManager layoutManager;
    private int mCurPos = -1;

    public int getCurrentPosition() {
        return mCurPos;
    }

    public ListPlayHelper(Activity activity) {
        this.activity = activity;
        initListVideoPlayer();
    }

    private void initListVideoPlayer() {
        mVideoView = new VideoView(activity);
        mVideoView.setOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
                    if (mVideoView.isTinyScreen()) {
                        mVideoView.stopTinyScreen();

                        releaseVideoView();
                    }
                }
            }
        });
        mController = new StandardVideoController(activity);
        addControlComponent();
    }

    private void addControlComponent() {
        CompleteView completeView = new CompleteView(activity);
        ErrorView errorView = new ErrorView(activity);
        mTitleView = new TitleView(activity);
        mController.addControlComponent(completeView, errorView, mTitleView);
        mController.addControlComponent(new VodControlView(activity));
        mController.addControlComponent(new GestureView(activity));
    }

    public void detachFromWindow(int position) {
        if (position == getCurrentPosition() && !mVideoView.isFullScreen()) {
            mVideoView.startTinyScreen();
            mVideoView.setVideoController(null);
            mController.setPlayState(VideoView.STATE_IDLE);
        }
    }


    /**
     * 将View从父控件中移除
     */
    private void removeViewFormParent(View v) {
        if (v == null) {
            return;
        }
        ViewParent parent = v.getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).removeView(v);
        }
    }

    public void startPlay(BaseViewHolder holder, VideoBean videoBean, boolean isRelease) {
//        this.mVideoBean = videoBean;

        if (mVideoView.isTinyScreen()) {
            mVideoView.stopTinyScreen();
        }
        if (mCurPos != -1 && isRelease) {
            releaseVideoView();
            mCurPos = -1;
        }

        mVideoView.setUrl(videoBean.getVideoUrl());
        mTitleView.setTitle(videoBean.getTitle());

        //注意：要先设置控制才能去设置控制器的状态
        mVideoView.setVideoController(mController);
        mController.setPlayState(mVideoView.getCurrentPlayState());


        FrameLayout mPlayerContainer = holder.getView(R.id.player_container);
        TextView mSummary = holder.getView(R.id.tv_summary);
        PrepareView mPrepareView = holder.getView(R.id.prepare_view);

        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(mPrepareView, true);//holder.mPrepareView
        removeViewFormParent(mVideoView);
        mPlayerContainer.addView(mVideoView, 0);
        //holder.mPlayerContainer.addView(mVideoView, 0);
        mVideoView.start();
        mCurPos = holder.absolutePosition;
    }

//    /**
//     * 开始播放
//     *
//     * @param position 列表位置
//     */
//    public void startPlay(int position, boolean isRelease) {
//        if (mVideoView.isTinyScreen()) {
//            mVideoView.stopTinyScreen();
//        }
//        if (mCurPos != -1 && isRelease) {
//            releaseVideoView();
//            mCurPos = -1;
//        }
////        final VirtualLayoutManager layoutManager = (VirtualLayoutManager) mRecyclerView.getLayoutManager();
////        if (layoutManager == null) {
////            return;
////        }
////        final View itemView = layoutManager.findViewByPosition(position);
////        if (itemView == null || itemView.getTag() == null) {
////            return;
////        }
//
//        BaseViewHolder holder = (BaseViewHolder) itemView.getTag();
//
////        VideoBean videoBean = mVideos.get(position);
//        VideoBean videoBean = mVideos.get(holder.relativePosition);
//
//        mVideoView.setUrl(videoBean.getVideoUrl());
//        mTitleView.setTitle(videoBean.getTitle());
//
//        //注意：要先设置控制才能去设置控制器的状态。
//        mVideoView.setVideoController(mController);
//        mController.setPlayState(mVideoView.getCurrentPlayState());
//
//
//        FrameLayout mPlayerContainer = holder.getView(R.id.player_container);
//        TextView mSummary = holder.getView(R.id.tv_summary);
//        PrepareView mPrepareView = holder.getView(R.id.prepare_view);
//
//        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
//        mController.addControlComponent(mPrepareView, true);//holder.mPrepareView
//        removeViewFormParent(mVideoView);
//        mPlayerContainer.addView(mVideoView, 0);
//        //holder.mPlayerContainer.addView(mVideoView, 0);
//        mVideoView.start();
//        mCurPos = position;
//    }

    public void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }

    public void resume() {
        if (mVideoView != null) {
            mVideoView.resume();

        }
    }

    public void pause() {
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    public void destroy() {
        if (mVideoView != null) {
            mVideoView.release();
            mVideoView = null;
        }

    }

    public void onBackPressed() {
        if (mVideoView != null && mVideoView.isFullScreen()) {
            //退出全屏,恢复列表播放状态
            mVideoView.stopFullScreen();
            if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } else {
            //从列表退出,直接页面退出
            activity.onBackPressed();
        }
    }

    /**
     * @param layoutManager
     * @see com.alibaba.android.vlayout.VirtualLayoutManager
     */
    public void initVirtualLayoutManager(VirtualLayoutManager layoutManager) {
        layoutManager.setPerformanceMonitor(new PerformanceMonitor() {
            long start;
            long end;

            @Override
            public void recordStart(String phase, View view) {
                start = System.currentTimeMillis();
            }

            @Override
            public void recordEnd(String phase, View view) {
                end = System.currentTimeMillis();
                Log.d("123", " recordEnd " + view.getClass().getName() + " " + (end - start));
            }
        });

        layoutManager.setRecycleOffset(300);

        //Notice: viewLifeCycleListener should be used with setRecycleOffset()
        layoutManager.setViewLifeCycleListener(new ViewLifeCycleListener() {
            @Override
            public void onAppearing(View view) {
                Log.e("123", "onAppearing: " + view);
            }

            @Override
            public void onDisappearing(View view) {
                Log.e("123", "onDisappearing: " + view);
            }

            @Override
            public void onAppeared(View view) {
                Log.e("123", "onAppeared: " + view);
            }

            @Override
            public void onDisappeared(View view) {
                Log.e("123", "onDisappeared: " + view);
            }
        });

        layoutManager.setLayoutManagerCanScrollListener(new LayoutManagerCanScrollListener() {
            @Override
            public boolean canScrollVertically() {
                Log.i("123", "canScrollVertically: ");
                return true;
            }

            @Override
            public boolean canScrollHorizontally() {
                //Log.i("123", "canScrollHorizontally: ");
                return true;
            }
        });
    }

}