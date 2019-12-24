package sample.vlayout.ui.vlayout.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.LayoutHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import sample.vlayout.R;
import sample.vlayout.ui.vlayout.entity.VideoListEntity;
import sample.vlayout.utils.image.ImageLoader;

/**
 * Title: MultiItemsHorAdapter
 * <p>
 * Description: 横向多图
 * </p>
 *
 * @author Changbao
 * @date 2019/12/24  16:16
 */
public class MultiItemsHorAdapter extends BaseDelegateAdapter {

    private List<VideoListEntity.DataBean.ContentBean> content;
    private int xOffset;//横向偏移
    private int prePosition;


    public MultiItemsHorAdapter(Context context, LayoutHelper layoutHelper, int layoutId, int count, int viewTypeItem) {
        super(context, layoutHelper, layoutId, count, viewTypeItem);
    }
    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
       // Toast.makeText(mContext, "MultiItemsHorAdapter 被回收 ", Toast.LENGTH_SHORT).show();
        //recyclerView.getRecycledViewPool().getRecycledViewCount(ViewType.TYPE_BANNER)
    }
    public void setData(List<VideoListEntity.DataBean.ContentBean> content) {
        this.content = content;
    }

    @Override
    public void onViewDetachedFromWindow(@NotNull BaseViewHolder holder) {
        RecyclerView recyclerH = holder.getView(R.id.recyclerViewHor);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerH.getLayoutManager();
        if (linearLayoutManager == null) {
            return;
        }
        final int position = linearLayoutManager.findFirstVisibleItemPosition();
        View view = linearLayoutManager.findViewByPosition(position);
        if (view != null) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            xOffset = view.getLeft() - lp.leftMargin; //如果你设置了margin则减去
            prePosition = position;
        }
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NotNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        RecyclerView recyclerH = holder.getView(R.id.recyclerViewHor);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerH.getLayoutManager();
        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(prePosition, xOffset);
        }
    }

    @Override
    protected void onBindViewHolderWithOffset(BaseViewHolder holder, int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);

//      final int num = safeShowNum(content, showNum);
//      final int finalShowNum = num > 5 ? 5 : num;

        // SmartRefreshHorizontal refreshLayout = holder.getView(R.id.refreshLayout);
        RecyclerView recyclerH = holder.getView(R.id.recyclerViewHor);
        recyclerH.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        recyclerH.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 5, 0);
            }
        });
        recyclerH.setAdapter(new RecyclerView.Adapter<HorViewHolder>() {
            @NonNull
            @Override
            public HorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new HorViewHolder(LayoutInflater.from(mContext).inflate(R.layout.vlayout_item_multi_images, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull HorViewHolder holder, int position) {
                ImageLoader.get().loadImage(holder.ivImage, content.get(position).getCover());
            }

            @Override
            public int getItemCount() {
                return content == null || content.isEmpty() ? 0 : content.size();
            }
        });

        //recyclerView.setRecycledViewPool(recyclerPool);
        recyclerH.getAdapter().notifyDataSetChanged();
    }

    private class HorViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivImage;

        public HorViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }
}
