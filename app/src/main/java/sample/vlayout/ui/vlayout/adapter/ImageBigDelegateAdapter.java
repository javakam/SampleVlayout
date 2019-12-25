package sample.vlayout.ui.vlayout.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.LayoutHelper;
import com.dueeeke.videocontroller.component.PrepareView;

import sample.vlayout.R;
import sample.vlayout.ui.vlayout.DataType;
import sample.vlayout.ui.vlayout.entity.VideoListEntity;

/**
 * Title: ImageBigDelegateAdapter
 * <p>
 * Description:
 * </p>
 *
 * @author Changbao
 * @date 2019/12/24  16:29
 */
public class ImageBigDelegateAdapter extends BaseDelegateAdapter {

    public static final String VIDEO_LIST_TAG = "VIDEO_LIST_TAG";

    private VideoListEntity.DataBean bean;

    public ImageBigDelegateAdapter(Context context, LayoutHelper layoutHelper, int layoutId, int count, int viewTypeItem) {
        super(context, layoutHelper, layoutId, count, viewTypeItem);
    }

    public void setData(VideoListEntity.DataBean bean) {
        this.bean = bean;
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        Toast.makeText(mContext, "ImageBigDelegateAdapter 被回收 ", Toast.LENGTH_SHORT).show();
        //recyclerView.getRecycledViewPool().getRecycledViewCount(ViewType.TYPE_BANNER)
    }

    @Override
    protected void onBindViewHolderWithOffset(BaseViewHolder holder, final int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);
                /*
                获取 DelegateAdapter 里数据的相对位置

                在 DelegateAdapter 里有 findOffsetPosition(int absolutePosition) 方法，传入整个页面的绝对位置，获取相对位置。
                   eg :  int offsetPosition = delegateAdapter.findOffsetPosition(1);

                或者用
                  public static abstract class Adapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
                      public abstract LayoutHelper onCreateLayoutHelper();

                      protected void onBindViewHolderWithOffset(VH holder, int position, int offsetTotal) {
                      }
                  }
                中的 onBindViewHolderWithOffset() 方法代替传统的 onBindViewHolder() 方法，其中的 position 参数也是相对位置,offsetTotal 为绝对位置。
                 */

        //保存位置
        holder.relativePosition = position;
        holder.absolutePosition = offsetTotal;
        holder.itemView.setTag(holder);
        //holder.position = offsetTotal;

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
//        final CoverImageLayout coverImageLayout = holder.getView(R.id.coverImageLayout);
//        coverImageLayout.setOnClickListener(null);
        final int finalDataType = dataType;
        final int finalPosition = offsetTotal;
        Log.w("123", "DataType.VIDEO " + position + "  " + dataType + " " + videoUrl + "  offsetTotal : " + offsetTotal);


        switch (finalDataType) {
            case DataType.VIDEO:

//                coverImageLayout.setCoverImg(R.drawable.selector_cover_video);
                if (TextUtils.isEmpty(videoUrl)) {
                    break;
                }
                final String finalVideoUrl = videoUrl;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callBack != null) {
                            callBack.call(position, finalPosition);
                        }
                    }
                });

                break;
            case DataType.AUDIO:
//                coverImageLayout.setCoverImg(R.drawable.ic_cover_audio);
                break;
            default:
                Log.e("123", "hideCover finalPosition " + finalPosition + "  " + dataType);
//                coverImageLayout.hideCover();
                break;
        }
        if (finalDataType != DataType.VIDEO) {
            //加载预览图
            // ImageLoader.get().loadImage(coverImageLayout.getBackImg(), cover);
        }


        TextView tvSummary = holder.getView(R.id.tv_summary);
        holder.setText(R.id.tv_summary, summary);

        tvSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "摘要", Toast.LENGTH_SHORT).show();
            }
        });

//     Objects.requireNonNull(holder.itemView).setOnClickListener(v -> {
//         //点击事件
//         Toast.makeText(activity, "initList4 finalPosition : " + finalPosition, Toast.LENGTH_SHORT).show();
//     });

    }

//    public class MediaBaseViewHolder extends BaseViewHolder {
//        public int position = -1;
//        public FrameLayout mPlayerContainer;
//        public TextView mSummary;
//        public ImageView mThumb;
//        public PrepareView mPrepareView;
//
//        public MediaBaseViewHolder(View view) {
//            super(view);
//            mPlayerContainer = itemView.findViewById(R.id.player_container);
//            mSummary = itemView.findViewById(R.id.tv_summary);
//            mPrepareView = itemView.findViewById(R.id.prepare_view);
//            mThumb = mPrepareView.findViewById(R.id.thumb);
//        }
//    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void call(int position, int finalPosition);
    }
}