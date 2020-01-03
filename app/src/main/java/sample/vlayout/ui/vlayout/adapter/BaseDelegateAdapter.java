package sample.vlayout.ui.vlayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;

public class BaseDelegateAdapter extends DelegateAdapter.Adapter<BaseVirtualViewHolder> {

    protected Context mContext;
    protected VirtualLayoutManager mLayoutManager;

    private LayoutHelper mLayoutHelper;
    private int mCount;
    private int mLayoutId;
    private int mViewTypeItem;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mLayoutManager = (VirtualLayoutManager) recyclerView.getLayoutManager();
    }

    public BaseDelegateAdapter(Context context, LayoutHelper layoutHelper, int layoutId, int count, int viewTypeItem) {
        this.mContext = context;
        this.mCount = count;
        this.mLayoutHelper = layoutHelper;
        this.mLayoutId = layoutId;
        this.mViewTypeItem = viewTypeItem;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public BaseVirtualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == mViewTypeItem) {
            return new BaseVirtualViewHolder(LayoutInflater.from(mContext).inflate(mLayoutId, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseVirtualViewHolder holder, int position) {
    }

    /**
     * @param holder
     * @param position    相对位置
     * @param offsetTotal 绝对位置
     */
    @Override
    protected void onBindViewHolderWithOffset(BaseVirtualViewHolder holder, int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);
    }

    /**
     * 必须重写不然会出现滑动不流畅的情况
     */
    @Override
    public int getItemViewType(int position) {
        return mViewTypeItem;
    }


    /**
     * 条目数量
     *
     * @return 条目数量
     */
    @Override
    public int getItemCount() {
        return mCount;
    }

}