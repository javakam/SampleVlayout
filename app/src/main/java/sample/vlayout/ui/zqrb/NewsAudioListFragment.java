package sample.vlayout.ui.zqrb;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.DefaultLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import sample.vlayout.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.alibaba.android.vlayout.layout.FixLayoutHelper.TOP_RIGHT;

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

    public static NewsAudioListFragment newInstance() {

        Bundle args = new Bundle();

        NewsAudioListFragment fragment = new NewsAudioListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private Activity activity;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

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
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setHasFixedSize(true);


//        if (true) {
//            test2();
//            return view ;
//        }


        VirtualLayoutManager layoutManager = new VirtualLayoutManager(activity);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(10, 10, 10, 10);
            }
        });

        final List<LayoutHelper> helpers = new LinkedList<>();

        final GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(6);
        gridLayoutHelper.setItemCount(25);


        final int specialPosition = 7;
        final ScrollFixLayoutHelper scrollFixLayoutHelper = new ScrollFixLayoutHelper(TOP_RIGHT, 100, 100);

        helpers.add(DefaultLayoutHelper.newHelper(specialPosition));
        helpers.add(scrollFixLayoutHelper);
        helpers.add(DefaultLayoutHelper.newHelper(2));
        helpers.add(gridLayoutHelper);

        layoutManager.setLayoutHelpers(helpers);

        mRecyclerView.setAdapter(
                new VirtualLayoutAdapter(layoutManager) {
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        return new MainViewHolder(new TextView(activity));
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
                        holder.itemView.setLayoutParams(layoutParams);

                        ((TextView) holder.itemView).setText(Integer.toString(position));

                        if (position == specialPosition) {
                            layoutParams.height = 60;
                            layoutParams.width = 60;
                        } else if (position > 35) {
                            layoutParams.height = 200 + (position - 30) * 100;
                        }

                        if (position > 35) {
                            holder.itemView.setBackgroundColor(0x66cc0000 + (position - 30) * 128);
                        } else if (position % 2 == 0) {
                            holder.itemView.setBackgroundColor(0xaa00ff00);
                        } else {
                            holder.itemView.setBackgroundColor(0xccff00ff);
                        }
                    }

                    @Override
                    public int getItemCount() {
                        List<LayoutHelper> helpers = getLayoutHelpers();
                        if (helpers == null) {
                            return 0;
                        }
                        int count = 0;
                        for (int i = 0, size = helpers.size(); i < size; i++) {
                            count += helpers.get(i).getItemCount();
                        }
                        return count;
                    }
                });

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollToPosition(specialPosition);
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }, 3000);
        return view;
    }

    private void test2() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(activity);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        List<DelegateAdapter.Adapter> adapterList = new ArrayList<>();
        adapterList.add(new SubAdapter(new LinearLayoutHelper(20), 20));
        adapterList.add(new SubAdapter(new StickyLayoutHelper(true), 1));
        adapterList.add(new SubAdapter(new LinearLayoutHelper(20), 20));
        adapterList.add(new SubAdapter(new StickyLayoutHelper(true), 1));
        adapterList.add(new SubAdapter(new GridLayoutHelper(4), 80));
        // adapterList.add(new SubAdapter(new FixLayoutHelper(0, 0), 1));
        adapterList.add(new SubAdapter(new FixLayoutHelper(TOP_RIGHT, 0, 0), 1));
        delegateAdapter.addAdapters(adapterList);
        mRecyclerView.setLayoutManager(virtualLayoutManager);
        mRecyclerView.setAdapter(delegateAdapter);
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class SubAdapter extends DelegateAdapter.Adapter<SubViewHolder> {

        private LayoutHelper mLayoutHelper;
        private int mItemCount;

        private SubAdapter(LayoutHelper layoutHelper, int itemCount) {
            mLayoutHelper = layoutHelper;
            mItemCount = itemCount;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return mLayoutHelper;
        }

        @Override
        public SubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new SubViewHolder(inflater.inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(SubViewHolder holder, int position) {
            // do nothing
        }

        @Override
        protected void onBindViewHolderWithOffset(SubViewHolder holder, int position, int offsetTotal) {
            super.onBindViewHolderWithOffset(holder, position, offsetTotal);
            holder.setText(String.valueOf(offsetTotal));
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        @Override
        public int getItemCount() {
            return mItemCount;
        }

    }

    static class SubViewHolder extends RecyclerView.ViewHolder {

        public static volatile int existing = 0;
        public static int createdTimes = 0;

        public SubViewHolder(View itemView) {
            super(itemView);
            createdTimes++;
            existing++;
        }

        public void setText(String title) {
            ((TextView) itemView.findViewById(R.id.title)).setText(title);
        }

        @Override
        protected void finalize() throws Throwable {
            existing--;
            super.finalize();
        }
    }

}
