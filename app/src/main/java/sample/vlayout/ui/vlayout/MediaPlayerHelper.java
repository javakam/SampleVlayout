package sample.vlayout.ui.vlayout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;

import sample.vlayout.R;
import sample.vlayout.ui.seamless.SwitchListVideoAdapter;
import sample.vlayout.ui.seamless.SwitchUtil;

public class MediaPlayerHelper {

    public static void initItemPlayer() {

//        final String urlH = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
//        final String urlV = "http://player.alicdn.com/video/aliyunmedia.mp4";
//        final String url = (position % 2 == 0) ? urlH : urlV;
//        final int coverId = (position % 2 == 0) ? R.mipmap.xxx1 : R.mipmap.xxx2;
//
//
//        //防止错位设置
//        holder.gsyVideoPlayer.setPlayTag(TAG);
//        holder.gsyVideoPlayer.setPlayPosition(position);
//        SwitchUtil.instance().optionPlayer(holder.gsyVideoPlayer, url, true, "这是title");
//        holder.gsyVideoPlayer.setUpLazy(url, true, null, null, "这是title");
//
//        //增加封面
//        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        holder.imageView.setImageResource(coverId);
//        if (holder.imageView.getParent() != null) {
//            ViewGroup viewGroup = (ViewGroup) holder.imageView.getParent();
//            viewGroup.removeView(holder.imageView);
//        }
//        holder.gsyVideoPlayer.setThumbImageView(holder.imageView);
//
//        if (GSYVideoManager.instance().getPlayTag().equals(SwitchListVideoAdapter.TAG)
//                && (position == GSYVideoManager.instance().getPlayPosition())) {
//            holder.gsyVideoPlayer.getThumbImageViewLayout().setVisibility(View.GONE);
//        } else {
//            holder.gsyVideoPlayer.getThumbImageViewLayout().setVisibility(View.VISIBLE);
//        }
    }
}
