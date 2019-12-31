package sample.vlayout.ui.vlayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.drakeet.multitype.MultiTypeAdapter;

import java.util.List;

import sample.vlayout.R;
import sample.vlayout.bean.VideoBean;
import sample.vlayout.ui.vlayout.adapter.BaseViewHolder;
import sample.vlayout.ui.vlayout.entity.VideoListEntity;
import sample.vlayout.ui.vlayout.multitype.VideoItemViewBinder;
import sample.vlayout.utils.AssetsUtils;
import sample.vlayout.utils.GsonUtils;

/**
 * Title: NewsAudioListFragment
 * <p>
 * Description:
 * </p>
 *
 * @author Changbao
 * @date 2019/12/17  13:49
 */
public class NewsAudioListFragment extends Fragment {

    private Activity activity;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MultiTypeAdapter adapter;
    private ListPlayHelper playHelper;
    private List<VideoListEntity.DataBean> mData;

    public static NewsAudioListFragment newInstance() {

        Bundle args = new Bundle();

        NewsAudioListFragment fragment = new NewsAudioListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
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

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(5, 5, 5, 5);
            }
        });

//        VirtualLayoutManager layoutManager = new VirtualLayoutManager(activity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        pool.setMaxRecycledViews(0, 10);
        pool.setMaxRecycledViews(1, 10);

        mRecyclerView.setLayoutManager(layoutManager);


        //设置适配器
        //https://github.com/alibaba/vlayout/blob/master/docs/VLayoutFAQ.md
        //注意：当hasConsistItemType=true的时候，不论是不是属于同一个子adapter，相同类型的item都能复用。表示它们共享一个类型。 当hasConsistItemType=false的时候，不同子adapter之间的类型不共享
//        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, false);
//        mRecyclerView.setAdapter(delegateAdapter);
//
//        BannerDelegateAdapter adapter = new BannerDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.vlayout_banner, 1, ViewType.TYPE_BANNER);
////        adapter.setData(content);
////        delegateAdapter.setAdapters();
//

        initViews(view);
        return view;
    }

    private VideoBean mVideoBean;

    private void initViews(View view) {
        final String json = AssetsUtils.getJson(activity, "videolist.json");// temp.json    videolist.json
        mData = GsonUtils.fromJson(json, VideoListEntity.class).getData();

        final List<VideoListEntity.DataBean> data = mData;

        for (VideoListEntity.DataBean bean : data) {
            final int type = bean.getViewType();
            switch (type) {
                case ViewType.TYPE_BANNER:
//                    adapter = initBanner(bean.getContent());
//                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_MARQUEUE:
//                    adapter = initMarqueue(bean);
//                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_TITLE://初始化标题
//                    adapter = initTitle(bean.getTitle(), bean.isMore());
//                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_TITLE_SUMMARY:
//                    adapter = initList2(bean.getTitle(), bean.getSummary(), bean.getShowNum());
//                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_TITLE_IMAGE_SMALL:
//                    adapter = initList3(bean.getTitle(), bean.getSummary(), bean.getCover(), bean.getShowNum());
//                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_TITLE_IMAGE_BIG:

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
//                    adapter = initList4(bean);
//                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_TITLE_IMAGE_THREE:
//                    adapter = initList5(bean.getContent(), bean.getShowNum());
//                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_TITLE_IMAGE_MANY:
//                    adapter = initList6(bean.getContent(), bean.getShowNum());
//                    mAdapters.add(adapter);
                    break;
                case ViewType.TYPE_NINE_GRID:
//                    adapter = initGrid(bean.getContent(), bean.getShowNum(), bean.getGridRowCount());
//                    mAdapters.add(adapter);
                    break;
                default:
                    break;
            }
        }

        playHelper = new ListPlayHelper(activity,mRecyclerView.getLayoutManager());

        adapter = new MultiTypeAdapter();

        VideoItemViewBinder videoItemViewBinder = new VideoItemViewBinder();
        videoItemViewBinder.setCallBack(new VideoItemViewBinder.CallBack() {
            @Override
            public void onClick(BaseViewHolder holder, VideoListEntity.DataBean bean) {

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
                playHelper.startPlay(holder, mVideoBean, true);
            }
        });

        adapter.register(VideoListEntity.DataBean.class, videoItemViewBinder);

        mRecyclerView.setAdapter(adapter);

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
                    playHelper.startPlay(holder, mVideoBean, false);
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
        adapter.setItems(mData);
    }

}