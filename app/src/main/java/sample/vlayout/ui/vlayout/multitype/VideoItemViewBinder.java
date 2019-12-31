package sample.vlayout.ui.vlayout.multitype;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drakeet.multitype.ItemViewBinder;
import com.dueeeke.videocontroller.component.PrepareView;

import org.jetbrains.annotations.NotNull;

import sample.vlayout.R;
import sample.vlayout.ui.vlayout.DataType;
import sample.vlayout.ui.vlayout.adapter.BaseViewHolder;
import sample.vlayout.ui.vlayout.entity.VideoListEntity;

/**
 * Title: VideoItemViewBinder
 * <p>
 * Description:
 * </p>
 *
 * @author Changbao
 * @date 2019/12/31  9:26
 */
public class VideoItemViewBinder extends ItemViewBinder<VideoListEntity.DataBean, VideoItemViewBinder.ViewHolder> {
    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onClick(BaseViewHolder holder, VideoListEntity.DataBean bean);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull LayoutInflater inflater, @NotNull ViewGroup viewGroup) {
        return new ViewHolder(inflater.inflate(R.layout.item_video, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, VideoListEntity.DataBean bean) {
        //绑定位置信息
        final int ps = getPosition(holder);
        Log.w("123", "VideoItemViewBinder  ps  " + ps);

        //holder.relativePosition = getPosition(holder);
        holder.absolutePosition = ps;
        holder.itemView.setTag(holder);

//        Glide.with(holder.mThumb.getContext())
//                .load(video.getCover())
//                .placeholder(android.R.color.white)
//                .into(holder.mThumb);

        holder.mTitle.setText(bean.getTitle());

        String summary, cover, videoUrl = "";
        int dataType;
        if (bean.getDataType() == DataType.DEFAULT) {
            summary = bean.getSummary();
            cover = bean.getCover();
            dataType = bean.getDataType();
        } else {
            if (bean.getContent() == null) {
                return;
            }
            final VideoListEntity.DataBean.ContentBean contentBean = bean.getContent().get(ps);
            if (contentBean == null) {
                return;
            }
            summary = contentBean.getSummary();
            cover = contentBean.getCover();
            dataType = contentBean.getDataType();
            videoUrl = contentBean.getVideo().getSD();
        }

        //动态添加播放器
//        final CoverImageLayout coverImageLayout = holder.getView(R.id.coverImageLayout);
//        coverImageLayout.setOnClickListener(null);
        final int finalDataType = dataType;
        final int finalPosition = ps;
        Log.w("123", "DataType.VIDEO " + ps + "  " + dataType + " " + videoUrl + "  offsetTotal : " + ps);


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
                            callBack.onClick(holder, bean);
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

//
//        TextView tvSummary = holder.getView(R.id.tv_summary);
//        holder.setText(R.id.tv_summary, summary);
//
//        tvSummary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(holder.itemView.getContext(), "摘要", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public class ViewHolder extends BaseViewHolder {
        FrameLayout mPlayerContainer;
        PrepareView mPrepareView;
        TextView mTitle;
        ImageView mThumb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPlayerContainer = itemView.findViewById(R.id.player_container);
            mPrepareView = itemView.findViewById(R.id.prepare_view);
            mTitle = itemView.findViewById(R.id.tv_title);
            mThumb = itemView.findViewById(R.id.thumb);
        }
    }
}
