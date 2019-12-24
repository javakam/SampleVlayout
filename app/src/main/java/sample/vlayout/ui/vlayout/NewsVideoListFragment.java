package sample.vlayout.ui.vlayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import sample.vlayout.R;
import sample.vlayout.ui.vlayout.adapter.BannerDelegateAdapter;
import sample.vlayout.ui.vlayout.adapter.BaseDelegateAdapter;
import sample.vlayout.ui.vlayout.adapter.BaseViewHolder;
import sample.vlayout.ui.vlayout.adapter.ImageBigDelegateAdapter;
import sample.vlayout.ui.vlayout.adapter.MultiItemsHorAdapter;
import sample.vlayout.ui.vlayout.entity.VideoListEntity;
import sample.vlayout.utils.AssetsUtils;
import sample.vlayout.utils.GsonUtils;
import sample.vlayout.utils.ScreenShotUtils;
import sample.vlayout.utils.image.ImageLoader;

import static sample.vlayout.ui.vlayout.adapter.ImageBigDelegateAdapter.VIDEO_LIST_TAG;

/**
 * Title: NewsVideoListFragment
 * <p>
 * Description:
 * </p>
 *
 * @author Changbao
 * @date 2019/12/17  13:49
 */
public class NewsVideoListFragment extends Fragment {

    public static NewsVideoListFragment newInstance() {
        Bundle args = new Bundle();
        NewsVideoListFragment fragment = new NewsVideoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Activity activity;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinkedList<DelegateAdapter.Adapter> mAdapters;
    //
    private List<VideoListEntity.DataBean> mData;
    private RecyclerView.RecycledViewPool recyclerPool;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;

        mAdapters = new LinkedList<>();
        String data = AssetsUtils.getJson(activity, "videolist.json");
        mData = GsonUtils.fromJson(data, VideoListEntity.class).getData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_list, container, false);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setColorSchemeColors(Color.RED, Color.CYAN, Color.MAGENTA);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        mRecyclerView = view.findViewById(R.id.recyclerView);
//        mRecyclerView.setItemAnimator(null);
//        mRecyclerView.setHasFixedSize(true);

        DelegateAdapter delegateAdapter = initRecyclerView();

//        initListDKVideoPlayer(mRecyclerView);
        initListVideoPlayer(mRecyclerView);

        initAdapters(delegateAdapter);


        return view;
    }

    public DelegateAdapter initRecyclerView() {
        //初始化
//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                outRect.set(0, 0, 0, 0);
//            }
//        });

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        recyclerPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(recyclerPool);

        recyclerPool.setMaxRecycledViews(0, 10);
        recyclerPool.setMaxRecycledViews(1, 10);
        recyclerPool.setMaxRecycledViews(2, 10);
        recyclerPool.setMaxRecycledViews(3, 10);
        recyclerPool.setMaxRecycledViews(4, 10);
        recyclerPool.setMaxRecycledViews(5, 10);
        recyclerPool.setMaxRecycledViews(6, 10);
        recyclerPool.setMaxRecycledViews(7, 10);
        recyclerPool.setMaxRecycledViews(8, 10);
        recyclerPool.setMaxRecycledViews(9, 10);
        recyclerPool.setMaxRecycledViews(10, 10);

        //创建VirtualLayoutManager对象
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(activity);
//        layoutManager.setPerformanceMonitor(new PerformanceMonitor() {
//
//            long start;
//            long end;
//
//            @Override
//            public void recordStart(String phase, View view) {
//                start = System.currentTimeMillis();
//            }
//
//            @Override
//            public void recordEnd(String phase, View view) {
//                end = System.currentTimeMillis();
//                Log.d("123", " recordEnd " + view.getClass().getName() + " " + (end - start));
//            }
//        });

//        layoutManager.setRecycleOffset(300);
        // viewLifeCycleListener should be used with setRecycleOffset()
//        layoutManager.setViewLifeCycleListener(new ViewLifeCycleListener() {
//            @Override
//            public void onAppearing(View view) {
//                Log.e("123", "onAppearing: " + view);
//            }
//
//            @Override
//            public void onDisappearing(View view) {
//                Log.e("123", "onDisappearing: " + view);
//            }
//
//            @Override
//            public void onAppeared(View view) {
//                Log.e("123", "onAppeared: " + view);
//            }
//
//            @Override
//            public void onDisappeared(View view) {
//                Log.e("123", "onDisappeared: " + view);
//            }
//        });

//        layoutManager.setLayoutManagerCanScrollListener(new LayoutManagerCanScrollListener() {
//            @Override
//            public boolean canScrollVertically() {
//                Log.i("123", "canScrollVertically: ");
//                return true;
//            }
//
//            @Override
//            public boolean canScrollHorizontally() {
//                //Log.i("123", "canScrollHorizontally: ");
//                return true;
//            }
//        });

        mRecyclerView.setLayoutManager(layoutManager);


        //设置适配器
        //https://github.com/alibaba/vlayout/blob/master/docs/VLayoutFAQ.md
        //注意：当hasConsistItemType=true的时候，不论是不是属于同一个子adapter，相同类型的item都能复用。表示它们共享一个类型。 当hasConsistItemType=false的时候，不同子adapter之间的类型不共享
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, false);
        mRecyclerView.setAdapter(delegateAdapter);
        return delegateAdapter;
    }

    /*
    1. type      列表样式(用于前端辨识)
    2. mediaType 媒体类型(标明数据类型)   0默认类型;1视频;2音频

    标题+查询更多 		1		mediaType=0 default

    标题+摘要		 			2	  mediaType=0 default

    标题+小图     		3   mediaType=0 default
    标题+小图视频     3   mediaType=1
    标题+小图音频     3   mediaType=2

    标题+大图     	  4   mediaType=0 default
    标题+大图视频     4   mediaType=1
    标题+大图音频     4		mediaType=2

    标题+三个小图     5   mediaType=0 default
    标题+三个小图视频 5   mediaType=1
    标题+三个小图音频 5		mediaType=2
    */
    private void initAdapters(DelegateAdapter delegateAdapter) {
        final List<VideoListEntity.DataBean> data = mData;
        BaseDelegateAdapter adapter = null;

        for (VideoListEntity.DataBean bean : data) {
            final int type = bean.getViewType();
            switch (type) {
                case ViewType.TYPE_BANNER:
                    adapter = initBanner(bean.getContent());
                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_MARQUEUE:
                    adapter = initMarqueue(bean);
                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_TITLE://初始化标题
                    adapter = initTitle(bean.getTitle(), bean.isMore());
                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_TITLE_SUMMARY:
                    adapter = initList2(bean.getTitle(), bean.getSummary(), bean.getShowNum());
                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_TITLE_IMAGE_SMALL:
                    adapter = initList3(bean.getTitle(), bean.getSummary(), bean.getCover(), bean.getShowNum());
                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_TITLE_IMAGE_BIG:
                    adapter = initList4(bean);
                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_TITLE_IMAGE_THREE:
                    adapter = initList5(bean.getContent(), bean.getShowNum());
                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_TITLE_IMAGE_MANY:
                    adapter = initList6(bean.getContent(), bean.getShowNum());
                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_NINE_GRID:
                    adapter = initGrid(bean.getContent(), bean.getShowNum(), bean.getGridRowCount());
                    mAdapters.add(adapter);
                    break;
                default:
                    break;
            }
        }

        //设置适配器
        delegateAdapter.setAdapters(mAdapters);
        mRecyclerView.requestLayout();
    }


    //防止数组越界 safeShowNum(bean.getContent(), bean.getShowNum())
    private static int safeShowNum(List<?> list, int showNum) {
        if (list == null || list.isEmpty() || showNum <= 0) {
            return 0;
        }
        if (showNum > list.size()) {
            return list.size();
        }
        return showNum;
    }

    private BaseDelegateAdapter initMarqueue(VideoListEntity.DataBean bean) {
        if (bean.getContent() == null) {
            return null;
        }
        return new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.vlayout_marqueue, 1, ViewType.TYPE_BANNER) {

            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                ImageView ivIcon = holder.getView(R.id.iv_vlayout_marqueue);
                ImageLoader.get().loadImage(ivIcon, bean.getCover());

                //跑马灯
                TextView tvText = holder.getView(R.id.tv_vlayout_marqueue);
                tvText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                CountDownTimer timer = new CountDownTimer(Integer.MAX_VALUE, 3000) {

                    int index;

                    @Override
                    public void onTick(long millisUntilFinished) {
                        final VideoListEntity.DataBean.ContentBean contentBean = bean.getContent().get(index);
                        if (index == bean.getContent().size() - 1) {
                            index = 0;
                        }
                        tvText.setText(contentBean.getTitle());
                        index++;
                    }

                    @Override
                    public void onFinish() {

                    }
                };
                timer.start();

            }
        };
    }

    public BannerDelegateAdapter initBanner(List<VideoListEntity.DataBean.ContentBean> content) {
        // LinearLayoutHelper
        BannerDelegateAdapter adapter = new BannerDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.vlayout_banner, 1, ViewType.TYPE_BANNER);
        adapter.setData(content);
        return adapter;
    }

    public BaseDelegateAdapter initTitle(final String title, final boolean showMore) {
        // LinearLayoutHelper
        StickyLayoutHelper stickyLayoutHelper = new StickyLayoutHelper();
        return new BaseDelegateAdapter(activity, stickyLayoutHelper, R.layout.vlayout_title, 1, ViewType.TYPE_TITLE) {
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, title);
                TextView tvMore = holder.getView(R.id.tv_show_more);
                tvMore.setVisibility(showMore ? View.VISIBLE : View.GONE);
                if (showMore) {
                    tvMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ScreenShotUtils.showRecyclerViewShotDialog(mRecyclerView);

                            Toast.makeText(activity, title, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        };
    }


    public BaseDelegateAdapter initList2(String title, String summary, int showNum) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        //linearLayoutHelper.setAspectRatio(4.0f);
        linearLayoutHelper.setDividerHeight(1);
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 0);

        return new BaseDelegateAdapter(activity, linearLayoutHelper, R.layout.vlayout_title_summary, showNum, ViewType.TYPE_TITLE_SUMMARY) {
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, title);
                holder.setText(R.id.tv_summary, summary);

                Objects.requireNonNull(holder.itemView).setOnClickListener(v -> {
                    //点击事件
                    Toast.makeText(activity, "initList2  position : " + position, Toast.LENGTH_SHORT).show();
                });
            }
        };
    }

    public BaseDelegateAdapter initList3(String title, String summary, String cover, int showNum) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        //linearLayoutHelper.setAspectRatio(4.0f);
        //linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setMargin(0, 0, 0, 10);
        linearLayoutHelper.setPadding(0, 0, 0, 0);

        return new BaseDelegateAdapter(activity, linearLayoutHelper, R.layout.vlayout_title_summary_image_small, showNum, ViewType.TYPE_TITLE_IMAGE_SMALL) {
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, title);
                holder.setText(R.id.tv_summary, summary);
                holder.setText(R.id.tv_time, "2019年12月18日");

                ImageView imageView = holder.getView(R.id.iv_logo);
                ImageLoader.get().loadImage(imageView, cover);

                Objects.requireNonNull(holder.itemView).setOnClickListener(v -> {
                    //点击事件
                    Toast.makeText(activity, "position : " + position, Toast.LENGTH_SHORT).show();
                });
            }
        };
    }

    private ImageBigDelegateAdapter initList4(VideoListEntity.DataBean bean) {
//        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper();
//        //noinspection deprecation
//        onePlusNLayoutHelper.setBgColor(activity.getResources().getColor(android.R.color.white));
//        onePlusNLayoutHelper.setMargin(0, 0, 0, 0);
//        onePlusNLayoutHelper.setPadding(10, 20, 10, 10);

        ImageBigDelegateAdapter adapter = new ImageBigDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.vlayout_title_summary_image_big, bean.getShowNum(), ViewType.TYPE_TITLE_IMAGE_BIG);
        adapter.setData(bean, smallVideoHelper);
        return adapter;
    }

    private GSYVideoHelper smallVideoHelper;
    private GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;
    private int lastVisibleItem, firstVisibleItem;

//    private StandardVideoController mController;
//    private VideoView mVideoView;
//    private TitleView mTitleView;
//    private int mCurPos = -1;

//    private void initListDKVideoPlayer(@NonNull final RecyclerView recyclerView) {
//        mVideoView = new VideoView(activity);
//        mVideoView.setOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
//            @Override
//            public void onPlayStateChanged(int playState) {
//                if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
//                    if (mVideoView.isTinyScreen()) {
//                        mVideoView.stopTinyScreen();
//                        releaseVideoView();
//                    }
//                }
//            }
//        });
//        mController = new StandardVideoController(activity);
//
//        //addControlComponent();
//        CompleteView completeView = new CompleteView(activity);
//        ErrorView errorView = new ErrorView(activity);
//        mTitleView = new TitleView(activity);
//        mController.addControlComponent(completeView, errorView, mTitleView);
//        mController.addControlComponent(new VodControlView(activity));
//        mController.addControlComponent(new GestureView(activity));
//
//
//        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
//            @Override
//            public void onChildViewAttachedToWindow(@NonNull View view) {
//                if (view.getTag() == null) {
//                    return;
//                }
//                int position = (int) view.getTag();
//                if (position == mCurPos) {
//                    startPlay(position, false);
//                }
//            }
//
//            @Override
//            public void onChildViewDetachedFromWindow(@NonNull View view) {
//                if (view.getTag() == null) {
//                    return;
//                }
//                int position = (int) view.getTag();
//                if (position == mCurPos && !mVideoView.isFullScreen()) {
//                    mVideoView.startTinyScreen();
//                    mVideoView.setVideoController(null);
//                    mController.setPlayState(VideoView.STATE_IDLE);
//                }
//            }
//        });
//    }

    /**
     * 开始播放
     *
     * @param position 列表位置
     */
    protected void startPlay(int position, boolean isRelease) {
//        if (mVideoView.isTinyScreen()) {
//            mVideoView.stopTinyScreen();
//        }
//        if (mCurPos != -1 && isRelease) {
//            releaseVideoView();
//        }
//        mVideoView.setUrl(videoBean.getUrl());
//        mTitleView.setTitle(videoBean.getTitle());
//
//        //注意：要先设置控制才能去设置控制器的状态。
//        mVideoView.setVideoController(mController);
//        mController.setPlayState(mVideoView.getCurrentPlayState());
//
//        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
//        mController.addControlComponent(viewHolder.mPrepareView, true);
//        removeViewFormParent(mVideoView);
//        viewHolder.mPlayerContainer.addView(mVideoView, 0);
//        mVideoView.start();
//        mCurPos = position;
    }

//    private void releaseVideoView() {
//        mVideoView.release();
//        if (mVideoView.isFullScreen()) {
//            mVideoView.stopFullScreen();
//        }
//        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
//            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
//        mCurPos = -1;
//    }

    /**
     * 将View从父控件中移除
     */
    public static void removeViewFormParent(View v) {
        if (v == null) {
            return;
        }
        ViewParent parent = v.getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).removeView(v);
        }
    }


    private void initListVideoPlayer(@NonNull final RecyclerView recyclerView) {

        final VirtualLayoutManager layoutManager = (VirtualLayoutManager) recyclerView.getLayoutManager();

        //创建小窗口帮助类
        smallVideoHelper = new GSYVideoHelper(activity);
        //smallVideoHelper.setFullViewContainer(videoFullContainer);


        gsySmallVideoHelperBuilder = new GSYVideoHelper.GSYVideoHelperBuilder();
        gsySmallVideoHelperBuilder
                .setHideActionBar(true)
                .setHideStatusBar(true)
                .setNeedLockFull(true)
                .setCacheWithPlay(true)
                .setAutoFullWithSize(false)
                .setShowFullAnimation(false)
                .setRotateViewAuto(false)
                .setLockLand(true)
                .setVideoAllCallBack(new GSYSampleCallBack() {

                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        Debuger.printfLog("Duration " + smallVideoHelper.getGsyVideoPlayer().getDuration() + " CurrentPosition " + smallVideoHelper.getGsyVideoPlayer().getCurrentPositionWhenPlaying());
                    }

                    @Override
                    public void onQuitSmallWidget(String url, Object... objects) {
                        super.onQuitSmallWidget(url, objects);
                        if (layoutManager == null) {
                            return;
                        }

                        //大于0说明有播放,//对应的播放列表TAG
                        if (smallVideoHelper.getPlayPosition() >= 0 && smallVideoHelper.getPlayTAG().equals(VIDEO_LIST_TAG)) {
                            //当前播放的位置
                            int position = smallVideoHelper.getPlayPosition();
                            Log.e("123", "onQuitSmallWidget position " + position);
                            //不可视的是时候
                            if ((position < firstVisibleItem || position > lastVisibleItem)) {
                                //释放掉视频
                                smallVideoHelper.releaseVideoPlayer();
                                if (recyclerView.getAdapter() != null) {
                                    //recyclerView.getAdapter().notifyDataSetChanged();//
                                    recyclerView.getAdapter().notifyItemChanged(position);
                                }
                            }
                        }

//                        //大于0说明有播放,//对应的播放列表TAG
//                        if (smallVideoHelper.getPlayPosition() >= 0 && smallVideoHelper.getPlayTAG().equals(VIDEO_LIST_TAG)) {
//                            //当前播放的位置
//                            int position = smallVideoHelper.getPlayPosition();
//                            Log.e("123", "onQuitSmallWidget position " + position);
//
//                            //不可视的是时候
//                            if ((position < layoutManager.findFirstVisibleItemPosition() || position > layoutManager.findLastVisibleItemPosition())) {
//                                //释放掉视频
//                                smallVideoHelper.releaseVideoPlayer();
//                                if (recyclerView.getAdapter() != null) {
//                                    recyclerView.getAdapter().notifyDataSetChanged();
//                                }
//                            }
//                        }
                    }
                });

        smallVideoHelper.setGsyVideoOptionBuilder(gsySmallVideoHelperBuilder);

//        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
//            @Override
//            public void onChildViewAttachedToWindow(@NonNull View view) {
//                if (view.getTag() == null) {
//                    return;
//                }
//                int position = (int) view.getTag();
//                if (position == mCurPos) {
//                    startPlay(position, false);
//                }
//            }
//
//            @Override
//            public void onChildViewDetachedFromWindow(@NonNull View view) {
//                if (view.getTag() == null) {
//                    return;
//                }
//                int position = (int) view.getTag();
//                if (position == mCurPos && !mVideoView.isFullScreen()) {
//                    mVideoView.startTinyScreen();
//                    mVideoView.setVideoController(null);
//                    mController.setPlayState(VideoView.STATE_IDLE);
//                }
//            }
//        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (layoutManager == null) {
                    return;
                }

                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                Log.i("123", "firstVisibleItem " + firstVisibleItem + " lastVisibleItem " + lastVisibleItem);
                //大于0说明有播放,//对应的播放列表TAG
                if (smallVideoHelper.getPlayPosition() >= 0 && smallVideoHelper.getPlayTAG().equals(VIDEO_LIST_TAG)) {
                    //当前播放的位置
                    int position = smallVideoHelper.getPlayPosition();
                    Log.e("123", "onScrolled position " + position);
                    //不可视的是时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果是小窗口就不需要处理
                        if (!smallVideoHelper.isSmall() && !smallVideoHelper.isFull()) {
                            //小窗口
                            int size = CommonUtil.dip2px(activity, 150);
                            //actionbar为true才不会掉下面去
                            smallVideoHelper.showSmallVideo(new Point(size + 30, size), true, true);
                        }
                    } else {
                        if (smallVideoHelper.isSmall()) {
                            smallVideoHelper.smallVideoToNormal();
                        }
                    }
                }

            }

        });

//        //增加title
//        videoPlayer.getTitleTextView().setVisibility(View.GONE);
//
//        //设置返回键
//        videoPlayer.getBackButton().setVisibility(View.GONE);
//
//        //设置全屏按键功能
//        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resolveFullBtn(videoPlayer);
//            }
//        });
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(activity, true, true);
    }


    public BaseDelegateAdapter initList5(List<VideoListEntity.DataBean.ContentBean> contentList, int showNum) {

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        gridLayoutHelper.setMargin(0, 0, 0, 0);
        gridLayoutHelper.setPadding(0, 10, 0, 10);
        gridLayoutHelper.setBgColor(Color.WHITE);
        //gridLayoutHelper.setAspectRatio(1.5f);  // 设置设置布局内每行布局的宽与高的比
        // gridLayoutHelper 特有属性（下面会详细说明）
        //设置每行中 每个网格宽度 占 每行总宽度 的比例 ， 会和 setAutoExpand 冲突
        //gridLayoutHelper.setWeights(new float[]{30, 30, 30});

        // 控制子元素之间的垂直间距
        gridLayoutHelper.setVGap(0);
        // 控制子元素之间的水平间距
        gridLayoutHelper.setHGap(5);
        //是否自动填充空白区域
        gridLayoutHelper.setAutoExpand(true);
        // 设置每行多少个网格
        //gridLayoutHelper.setSpanCount(6);
        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.vlayout_title_image_three, safeShowNum(contentList, showNum), ViewType.TYPE_TITLE_IMAGE_THREE) {
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);

                // if (contentList != null && !contentList.isEmpty() && position < contentList.size()) {}
                final VideoListEntity.DataBean.ContentBean bean = contentList.get(position);

                final int dataType = bean.getDataType();
                final ImageView coverIcon = holder.getView(R.id.iv_image_cover);
                switch (dataType) {
                    case DataType.VIDEO:
                        coverIcon.setVisibility(View.VISIBLE);
                        coverIcon.setImageResource(R.drawable.selector_cover_video);
                        break;
                    case DataType.AUDIO:
                        coverIcon.setVisibility(View.VISIBLE);
                        coverIcon.setImageResource(R.drawable.ic_cover_audio);
                        break;
                    default:
                        coverIcon.setVisibility(View.GONE);
                        break;
                }

                holder.setText(R.id.tv_title, bean.getTitle());
                ImageView iv = holder.getView(R.id.iv_image);
                ImageLoader.get().loadImage(iv, bean.getCover());

                Objects.requireNonNull(holder.itemView).setOnClickListener(v -> {
                    //点击事件
                    Toast.makeText(activity, "initList5  position : " + position, Toast.LENGTH_SHORT).show();
                });

            }
        };

//        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
//        linearLayoutHelper.setAspectRatio(4.0f);
//        linearLayoutHelper.setDividerHeight(5);
//        linearLayoutHelper.setMargin(0, 0, 0, 0);
//        linearLayoutHelper.setPadding(0, 0, 0, 10);
//        return new BaseDelegateAdapter(activity,
//                linearLayoutHelper, R.layout.item_news_base_view, 10, Constant.viewType.typeFooter) {
//            @Override
//            public void onBindViewHolder(@NonNull BaseViewHolder holder,
//                                         @SuppressLint("RecyclerView") final int position) {
//                super.onBindViewHolder(holder, position);
//                if (Constant.findBottomNews != null && Constant.findBottomNews.size() > 0) {
//                    HomeBlogEntity model = Constant.findBottomNews.get(position);
//                    holder.setText(R.id.tv_title, model.getTitle());
//                    ImageView imageView = holder.getView(R.id.iv_logo);
//                    ImageUtils.loadImgByPicasso(activity, model.getImageUrl()
//                            , R.drawable.image_default, imageView);
//                    holder.setText(R.id.tv_time, model.getTime());
//                    Objects.requireNonNull(holder.getItemView()).setOnClickListener(v -> {
//                        //点击事件
//                        mView.setNewsList5Click(position,
//                                Constant.findBottomNews.get(position).getVideoUrl());
//                    });
//                } else {
//                    ImageView imageView = holder.getView(R.id.iv_logo);
//                    holder.setText(R.id.tv_title, "新闻标题 ");
//                    imageView.setImageResource(R.drawable.image_default);
//                    holder.setText(R.id.tv_time, "新闻时间");
//                }
//            }
//        };
    }

    /**
     * 横向多图
     *
     * @param content
     * @param showNum
     * @return
     */
    private MultiItemsHorAdapter initList6(List<VideoListEntity.DataBean.ContentBean> content, final int showNum) {
        MultiItemsHorAdapter adapter = new MultiItemsHorAdapter(activity, new LinearLayoutHelper(), R.layout.vlayout_multi_images, 1, ViewType.TYPE_TITLE_IMAGE_MANY);
        adapter.setData(content);
        return adapter;
    }


    private BaseDelegateAdapter initGrid(List<VideoListEntity.DataBean.ContentBean> content, int showNum, int gridRowCount) {
        final int num = safeShowNum(content, showNum);

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(gridRowCount);// 2 4
        gridLayoutHelper.setBgColor(Color.WHITE);

        gridLayoutHelper.setPadding(0, 12, 0, 12);
        gridLayoutHelper.setVGap(10);
        //gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setAutoExpand(true);

        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.vlayout_item_grid_title_image, num, ViewType.TYPE_NINE_GRID) {
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);

                final VideoListEntity.DataBean.ContentBean bean = content.get(position);

                holder.setText(R.id.tv_new_seed_title, bean.getTitle());
                ImageLoader.get().loadImage((ImageView) holder.getView(R.id.iv_new_seed_ic), content.get(position).getCover());

                holder.getView(R.id.ll_new_seed_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(activity, "initGrid position " + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        //GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();

        //https://stackoverflow.com/questions/22552958/handling-back-press-when-using-fragments-in-android
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button
                    Toast.makeText(activity, "handle back button", Toast.LENGTH_SHORT).show();
                    onBackPressed();

                    activity.onBackPressed();
                    return true;

                }

                return false;
            }
        });
    }


    public void onBackPressed() {
        if (smallVideoHelper != null && smallVideoHelper.backFromFull()) {
            return;
        }
        if (GSYVideoManager.backFromWindowFull(getActivity())) {
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (smallVideoHelper != null) {
            smallVideoHelper.releaseVideoPlayer();
        }
        GSYVideoManager.releaseAllVideos();
    }


}