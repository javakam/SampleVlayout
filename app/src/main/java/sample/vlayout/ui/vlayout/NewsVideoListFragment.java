package sample.vlayout.ui.vlayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import sample.vlayout.R;
import sample.vlayout.bean.VideoBean;
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

    //
    private VideoBean mVideoBean;
    private ListPlayHelper playHelper;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;

        mAdapters = new LinkedList<>();
        String data = AssetsUtils.getJson(activity, "temp.json");// temp.json    videolist.json
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
        //优先初始化
        playHelper = new ListPlayHelper(activity);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(null);
//        mRecyclerView.setHasFixedSize(true);

        final DelegateAdapter delegateAdapter = initRecyclerView();

        initAdapters(delegateAdapter);

        return view;
    }

    /**
     * 设定RecyclerView最大滑动速度
     *
     * @param recyclerView
     * @param velocity
     */
    private void setMaxFlingVelocity(RecyclerView recyclerView, int velocity) {
        try {
            Field field = recyclerView.getClass().getDeclaredField("mMaxFlingVelocity");
            field.setAccessible(true);
            field.set(recyclerView, velocity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DelegateAdapter initRecyclerView() {
        setMaxFlingVelocity(mRecyclerView, 6000);

        //初始化
//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                outRect.set(0, 0, 0, 0);
//            }
//        });

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(pool);

        pool.setMaxRecycledViews(0, 10);
        pool.setMaxRecycledViews(1, 10);
        pool.setMaxRecycledViews(2, 10);
        pool.setMaxRecycledViews(3, 10);
        pool.setMaxRecycledViews(4, 10);
        pool.setMaxRecycledViews(5, 10);
        pool.setMaxRecycledViews(6, 10);
        pool.setMaxRecycledViews(7, 10);
        pool.setMaxRecycledViews(8, 10);
        pool.setMaxRecycledViews(9, 10);
        pool.setMaxRecycledViews(10, 10);

        //创建VirtualLayoutManager对象
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(activity);
        //playHelper.initVirtualLayoutManager(layoutManager);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View itemView) {
                if (itemView.getTag() == null) {
                    return;
                }
                final BaseViewHolder holder = (BaseViewHolder) itemView.getTag();
                int position = holder.absolutePosition;
                if (position == playHelper.getCurrentPosition()) {
                    //playHelper.startPlay(position, false);
                    //mVideoBean = mVideos.get(holder.relativePosition);
                    playHelper.startPlay(position, holder, mVideoBean, false);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View itemView) {
                if (itemView.getTag() == null) {
                    return;
                }
                final BaseViewHolder holder = (BaseViewHolder) itemView.getTag();
                int position = holder.absolutePosition;
                playHelper.detachFromWindow(position);
            }
        });

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
//        StickyLayoutHelper stickyLayoutHelper = new StickyLayoutHelper();
        return new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.vlayout_title, 1, ViewType.TYPE_TITLE) {
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

        ImageBigDelegateAdapter adapter = new ImageBigDelegateAdapter(activity, new LinearLayoutHelper(),
                R.layout.vlayout_title_summary_image_big, bean.getShowNum(), ViewType.TYPE_TITLE_IMAGE_BIG);

        if (bean.getDataType() == DataType.VIDEO) {
            if (bean.getContent() != null) {
                Toast.makeText(activity, "视频数 : " + bean.getContent().size(), Toast.LENGTH_SHORT).show();
                VideoListEntity.DataBean.ContentBean c = bean.getContent().get(0);
                VideoBean videoBean = new VideoBean();
                videoBean.setTitle(c.getTitle());
                videoBean.setThumb(c.getCover());
                videoBean.setAudioUrl(c.getAudio());
                videoBean.setVideoUrl(c.getVideo().getSD());
                mVideoBean = videoBean;
            }
        }
        final int finalDataType = bean.getDataType();


        adapter.setCallBack(new ImageBigDelegateAdapter.CallBack() {
            @Override
            public void call(BaseViewHolder holder, VideoListEntity.DataBean bean, int position, int finalPosition) {
                if (bean.getDataType() == DataType.VIDEO) {
                    if (bean.getContent() != null) {
                        Toast.makeText(activity, "视频数 : " + bean.getContent().size(), Toast.LENGTH_SHORT).show();
                        VideoListEntity.DataBean.ContentBean c = bean.getContent().get(0);
                        VideoBean videoBean = new VideoBean();
                        videoBean.setTitle(c.getTitle());
                        videoBean.setThumb(c.getCover());
                        videoBean.setAudioUrl(c.getAudio());
                        videoBean.setVideoUrl(c.getVideo().getSD());
                        mVideoBean = videoBean;
                    }
                }
                playHelper.startPlay(finalPosition, holder, mVideoBean, true);
            }
        });

        adapter.setData(bean);
        return adapter;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //https://stackoverflow.com/questions/22552958/handling-back-press-when-using-fragments-in-android
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button
                    Toast.makeText(activity, "handle back button", Toast.LENGTH_SHORT).show();

                    playHelper.onBackPressed();
                    return true;

                }

                return false;
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        playHelper.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        playHelper.resume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        playHelper.destroy();

    }


}