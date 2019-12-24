package sample.vlayout.ui.vlayout.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.LayoutHelper;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;

import org.jetbrains.annotations.NotNull;

import sample.vlayout.R;
import sample.vlayout.ui.vlayout.CoverImageLayout;
import sample.vlayout.ui.vlayout.DataType;
import sample.vlayout.ui.vlayout.entity.VideoListEntity;
import sample.vlayout.utils.image.ImageLoader;

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
    private GSYVideoHelper smallVideoHelper;

    public ImageBigDelegateAdapter(Context context, LayoutHelper layoutHelper, int layoutId, int count, int viewTypeItem) {
        super(context, layoutHelper, layoutId, count, viewTypeItem);
    }

    public void setData(VideoListEntity.DataBean bean, GSYVideoHelper smallVideoHelper) {
        this.bean = bean;
        this.smallVideoHelper = smallVideoHelper;
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        Toast.makeText(mContext, "ImageBigDelegateAdapter 被回收 ", Toast.LENGTH_SHORT).show();
        //recyclerView.getRecycledViewPool().getRecycledViewCount(ViewType.TYPE_BANNER)
    }

    @Override
    public void onViewDetachedFromWindow(@NotNull BaseViewHolder holder) {

        smallVideoHelper.releaseVideoPlayer();
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NotNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (smallVideoHelper.isSmall()) {
            smallVideoHelper.smallVideoToNormal();
        }
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
        coverImageLayout.setOnClickListener(null);
        final int finalDataType = dataType;
        final int finalPosition = offsetTotal;

        //DKPlayer
        //保存位置
        //holder.itemView.setTag(finalPosition);

        switch (finalDataType) {
            case DataType.VIDEO:
                Log.w("123", "DataType.VIDEO " + position + "  " + dataType + " " + videoUrl + "  offsetTotal : " + offsetTotal);

                coverImageLayout.setCoverImg(R.drawable.selector_cover_video);
                //Log.w("123", "video  videoUrl " + videoUrl);

                if (TextUtils.isEmpty(videoUrl)) {
                    break;
                }
                final String finalVideoUrl = videoUrl;


                //DKPlayer
//                        PrepareView mPrepareView = holder.getView(R.id.prepare_view);
//                        coverImageLayout.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (finalDataType == DataType.VIDEO) {
//                                    //startPlay(finalPosition, true);
//
//                                    if (mVideoView.isTinyScreen()) {
//                                        mVideoView.stopTinyScreen();
//                                    }
//                                    if (mCurPos != -1) {
//                                        releaseVideoView();
//                                    }
//                                    mVideoView.setUrl(finalVideoUrl);
//                                    mTitleView.setTitle(summary);
//
//                                    //注意：要先设置控制才能去设置控制器的状态。
//                                    mVideoView.setVideoController(mController);
//                                    mController.setPlayState(mVideoView.getCurrentPlayState());
//
//                                    //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
//                                    mController.addControlComponent(mPrepareView, true);
//                                    removeViewFormParent(mVideoView);
//
//                                    coverImageLayout.removeAllViews();
//                                    coverImageLayout.addView(mVideoView, 0);
//                                    mVideoView.start();
//                                    mCurPos = finalPosition;
//
//                                }
//                            }
//                        });


                //GSYPlayer
                smallVideoHelper.addVideoPlayer(finalPosition, coverImageLayout.getBackImg(), VIDEO_LIST_TAG, coverImageLayout, coverImageLayout.getCoverImg());

                smallVideoHelper.setPlayPositionAndTag(finalPosition, VIDEO_LIST_TAG);

                coverImageLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "coverImageLayout: " + finalDataType + " finalPosition: " + finalPosition, Toast.LENGTH_SHORT).show();

                        //使用此方法添加数据，使用notifyDataSetChanged会导致正在播放的视频中断
                        //notifyItemRangeChanged(size, videos.size());

                        //listVideoUtil.setLoop(true);
                        //listVideoUtil.setCachePath(new File(FileUtils.getPath()));

                        // final String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
                        smallVideoHelper.getGsyVideoOptionBuilder()
                                .setVideoTitle("title " + finalPosition)
                                .setUrl(finalVideoUrl);
                        smallVideoHelper.startPlay();

                        //必须在startPlay之后设置才能生效
                        //listVideoUtil.getGsyVideoPlayer().getTitleTextView().setVisibility(View.VISIBLE);
                    }
                });

                break;
            case DataType.AUDIO:
                coverImageLayout.setCoverImg(R.drawable.ic_cover_audio);
                break;
            default:
                Log.e("123", "hideCover finalPosition " + finalPosition + "  " + dataType);
                coverImageLayout.hideCover();
                break;
        }
        if (finalDataType != DataType.VIDEO) {
            //加载预览图
            ImageLoader.get().loadImage(coverImageLayout.getBackImg(), cover);
        }


        TextView tvSummary = holder.getView(R.id.tv_summary);
        holder.setText(R.id.tv_summary, summary);

        tvSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "摘要", Toast.LENGTH_SHORT).show();

            }
        });

//                Objects.requireNonNull(holder.itemView).setOnClickListener(v -> {
//                    //点击事件
//                    Toast.makeText(activity, "initList4 finalPosition : " + finalPosition, Toast.LENGTH_SHORT).show();
//                });

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        super.onBindViewHolder(holder, position);

    }
}
