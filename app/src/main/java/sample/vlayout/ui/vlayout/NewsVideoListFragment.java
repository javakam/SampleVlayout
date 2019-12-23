package sample.vlayout.ui.vlayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import sample.vlayout.R;
import sample.vlayout.bean.VideoBean;
import sample.vlayout.ui.vlayout.adapter.BaseDelegateAdapter;
import sample.vlayout.ui.vlayout.adapter.BaseViewHolder;
import sample.vlayout.ui.vlayout.adapter.RecyclerBaseAdapter;
import sample.vlayout.ui.vlayout.adapter.holder.RecyclerItemNormalHolder;
import sample.vlayout.ui.vlayout.entity.VideoListEntity;
import sample.vlayout.utils.AssetsUtils;
import sample.vlayout.utils.GsonUtils;
import sample.vlayout.utils.ScreenShotUtils;
import sample.vlayout.utils.image.ImageLoader;

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
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setHasFixedSize(true);

        DelegateAdapter delegateAdapter = initRecyclerView(mRecyclerView);

        initListVideoPlayer(mRecyclerView);

        initAdapters(delegateAdapter);


        return view;
    }

    public DelegateAdapter initRecyclerView(RecyclerView recyclerView) {
        //初始化
//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                outRect.set(0, 0, 0, 0);
//            }
//        });

        //创建VirtualLayoutManager对象
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        //设置适配器
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);
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
                CountDownTimer timer = new CountDownTimer(Integer.MAX_VALUE, 2000) {

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

    public BaseDelegateAdapter initBanner(List<VideoListEntity.DataBean.ContentBean> content) {
        // LinearLayoutHelper
        return new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.vlayout_banner, 1, ViewType.TYPE_BANNER) {
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                List<ImageView> viewList = new ArrayList<>();
                List<String> imgUrls = new ArrayList<>();
                for (VideoListEntity.DataBean.ContentBean bean : content) {
                    imgUrls.add(bean.getCover());
                    ImageView imageView = new ImageView(activity);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String action = "";
                            int dataType = bean.getDataType();
                            switch (dataType) {
                                case DataType.WEBVIEW:
                                    action = "WebView展示";
                                    break;
                                case DataType.OTHER_APP:
                                    action = "跳转到其他APP";
                                    break;
                                case DataType.OTHER_PAGE:
                                    action = "跳转到其他页面";
                                    break;
                                default:
                            }
                            Toast.makeText(activity, action + "  \n  " + bean.getUrl(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    viewList.add(imageView);
                }
                final ViewPager banner = holder.getView(R.id.banner);
                banner.setOffscreenPageLimit(viewList.size());
                banner.setAdapter(new PagerAdapter() {
                    @Override
                    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                        return view == object;
                    }

                    @Override
                    public int getCount() {
                        return viewList == null || viewList.isEmpty() ? 0 : viewList.size();
                    }

                    @Override
                    public void destroyItem(ViewGroup container, int position, Object object) {
                        container.removeView(viewList.get(position));
                    }

                    @Override
                    public Object instantiateItem(ViewGroup container, int position) {
                        ImageView imageView = viewList.get(position);
                        container.addView(imageView);
                        ImageLoader.get().loadImage(imageView, imgUrls.get(position));

                        return viewList.get(position);
                    }
                });
            }
        };
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

    private BaseDelegateAdapter initList4(VideoListEntity.DataBean bean) {
//        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper();
//        //noinspection deprecation
//        onePlusNLayoutHelper.setBgColor(activity.getResources().getColor(android.R.color.white));
//        onePlusNLayoutHelper.setMargin(0, 0, 0, 0);
//        onePlusNLayoutHelper.setPadding(10, 20, 10, 10);


        return new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.vlayout_title_summary_image_big, bean.getShowNum(), ViewType.TYPE_TITLE_IMAGE_BIG) {
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                String summary, cover, videoUrl = "";
                int dataType;
                if (bean.getDataType() == DataType.DEFAULT) {
                    summary = bean.getSummary();
                    cover = bean.getCover();
                    dataType = bean.getDataType();
                } else {
                    final VideoListEntity.DataBean.ContentBean contentBean = bean.getContent().get(position);
                    summary = contentBean.getSummary();
                    cover = contentBean.getCover();
                    dataType = contentBean.getDataType();
                    videoUrl = contentBean.getVideo().getSD();
                }

                //动态添加播放器
                final CoverImageLayout coverImageLayout = holder.getView(R.id.coverImageLayout);


                switch (dataType) {
                    case DataType.VIDEO:
                        coverImageLayout.setCoverImg(R.drawable.selector_cover_video);
                        //Log.w("123", "video  videoUrl " + videoUrl);

                        if (TextUtils.isEmpty(videoUrl)) {
                            break;
                        }
                        final String finalvideoUrl = videoUrl;



                        smallVideoHelper.addVideoPlayer(position, coverImageLayout.getBackImg(), VIDEO_LIST_TAG, coverImageLayout, coverImageLayout.getCoverImg());


                        coverImageLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(activity, "coverImageLayout", Toast.LENGTH_SHORT).show();


                                notifyDataSetChanged();
                                smallVideoHelper.setPlayPositionAndTag(position, VIDEO_LIST_TAG);

                                // final String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
                                gsySmallVideoHelperBuilder
                                        .setVideoTitle("title " + position)
                                        .setUrl(finalvideoUrl);
                                smallVideoHelper.startPlay();
                            }
                        });

                        break;
                    case DataType.AUDIO:
                        coverImageLayout.setCoverImg(R.drawable.ic_cover_audio);
                        break;
                    default:
                        coverImageLayout.hideCover();
                        break;
                }
                //加载预览图
                ImageLoader.get().loadImage(coverImageLayout.getBackImg(), cover);


                TextView tvSummary = holder.getView(R.id.tv_summary);
                holder.setText(R.id.tv_summary, summary);

                tvSummary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity, "tvSummary", Toast.LENGTH_SHORT).show();


                    }
                });


//                Objects.requireNonNull(holder.itemView).setOnClickListener(v -> {
//                    //点击事件
//                    Toast.makeText(activity, "initList4 position : " + position, Toast.LENGTH_SHORT).show();
//                });
            }
        };
    }

    public static final String VIDEO_LIST_TAG = "VIDEO_LIST_TAG";
    private GSYVideoHelper smallVideoHelper;
    private GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;


    private void initListVideoPlayer(@NonNull final RecyclerView recyclerView) {

        final VirtualLayoutManager layoutManager = (VirtualLayoutManager) recyclerView.getLayoutManager();

        //创建小窗口帮助类
        smallVideoHelper = new GSYVideoHelper(activity);

        gsySmallVideoHelperBuilder = new GSYVideoHelper.GSYVideoHelperBuilder();
        gsySmallVideoHelperBuilder
                .setHideStatusBar(true)
                .setNeedLockFull(true)
                .setCacheWithPlay(true)
                .setShowFullAnimation(false)
                .setRotateViewAuto(false)
                .setLockLand(true)
                .setVideoAllCallBack(new GSYSampleCallBack() {
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
                            if ((position < layoutManager.findFirstVisibleItemPosition() || position > layoutManager.findLastVisibleItemPosition())) {
                                //释放掉视频
                                smallVideoHelper.releaseVideoPlayer();
                                if (recyclerView.getAdapter() != null) {
                                    recyclerView.getAdapter().notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });

        smallVideoHelper.setGsyVideoOptionBuilder(gsySmallVideoHelperBuilder);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                SimpleListVideoActivityMode2.this.firstVisibleItem = firstVisibleItem;
//                lastVisibleItem = firstVisibleItem + visibleItemCount;

                if (layoutManager == null) {
                    return;
                }

                //大于0说明有播放,//对应的播放列表TAG
                if (smallVideoHelper.getPlayPosition() >= 0 && smallVideoHelper.getPlayTAG().equals(VIDEO_LIST_TAG)) {
                    //当前播放的位置
                    int position = smallVideoHelper.getPlayPosition();
                    Log.e("123", "onScrolled position " + position);
                    //不可视的是时候
                    if ((position < layoutManager.findFirstVisibleItemPosition() || position > layoutManager.findLastCompletelyVisibleItemPosition())) {
                        //如果是小窗口就不需要处理
                        if (!smallVideoHelper.isSmall()) {
                            //小窗口
                            int size = CommonUtil.dip2px(activity, 160);
                            smallVideoHelper.showSmallVideo(new Point(size + 50, size), false, true);
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

                //todo 2019年12月19日 22:17:29 去掉这层判断试一试

                if (contentList != null && !contentList.isEmpty() && position < contentList.size()) {
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

    private class HorViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_image;

        public HorViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
        }
    }

    private BaseDelegateAdapter initList6(List<VideoListEntity.DataBean.ContentBean> content, final int showNum) {

        return new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.vlayout_multi_images, 1, ViewType.TYPE_TITLE_IMAGE_MANY) {

            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder,
                                         @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
//                final int num = safeShowNum(content, showNum);
//                final int finalShowNum = num > 5 ? 5 : num;

                // SmartRefreshHorizontal refreshLayout = holder.getView(R.id.refreshLayout);
                RecyclerView recyclerView = holder.getView(R.id.recyclerViewHor);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
                recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        outRect.set(0, 0, 5, 0);
                    }
                });
                recyclerView.setAdapter(new RecyclerView.Adapter<HorViewHolder>() {
                    @NonNull
                    @Override
                    public HorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        return new HorViewHolder(getLayoutInflater().inflate(R.layout.vlayout_item_multi_images, parent, false));
                    }

                    @Override
                    public void onBindViewHolder(@NonNull HorViewHolder holder, int position) {
                        ImageLoader.get().loadImage(holder.iv_image, content.get(position).getCover());
                    }

                    @Override
                    public int getItemCount() {
                        return content == null || content.isEmpty() ? 0 : content.size();
                    }
                });

                recyclerView.getAdapter().notifyDataSetChanged();

            }
        };
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

    List<VideoBean> dataList = new ArrayList<>();
    RecyclerBaseAdapter recyclerBaseAdapter;

    public void bindVideoPlayer(@NotNull RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final VirtualLayoutManager layoutManager = (VirtualLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager == null) {
                    return;
                }

                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(RecyclerItemNormalHolder.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        if (!GSYVideoManager.isFullState(activity)) {
                            GSYVideoManager.releaseAllVideos();
                            if (mRecyclerView.getAdapter() != null) {
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                            }
                        }
                    }
                }
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
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
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