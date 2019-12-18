package sample.vlayout.ui.seamless;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sample.vlayout.R;

public class SwitchListVideoAdapter extends RecyclerView.Adapter<SwitchListVideoAdapter.ViewHolder> {

    public static final String TAG = "SwitchListVideoAdapter";

    private List<VideoBean> list = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    private StandardGSYVideoPlayer curPlayer;

    protected OrientationUtils orientationUtils;

    protected boolean isPlay;

    protected boolean isFull;

    public SwitchListVideoAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < 40; i++) {
            list.add(new VideoBean());
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View convertView = inflater.inflate(R.layout.switch_list_video_item, null);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final String urlH = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        final String urlV = "http://player.alicdn.com/video/aliyunmedia.mp4";
        final String url = (position % 2 == 0) ? urlH : urlV;
        final int coverId = (position % 2 == 0) ? R.mipmap.xxx1 : R.mipmap.xxx2;


        //防止错位设置
        holder.gsyVideoPlayer.setPlayTag(TAG);
        holder.gsyVideoPlayer.setPlayPosition(position);
        SwitchUtil.instance().optionPlayer(holder.gsyVideoPlayer, url, true, "这是title");
        holder.gsyVideoPlayer.setUpLazy(url, true, null, null, "这是title");

        //增加封面
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.imageView.setImageResource(coverId);
        if (holder.imageView.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) holder.imageView.getParent();
            viewGroup.removeView(holder.imageView);
        }
        holder.gsyVideoPlayer.setThumbImageView(holder.imageView);

        if (GSYVideoManager.instance().getPlayTag().equals(SwitchListVideoAdapter.TAG)
                && (position == GSYVideoManager.instance().getPlayPosition())) {
            holder.gsyVideoPlayer.getThumbImageViewLayout().setVisibility(View.GONE);
        } else {
            holder.gsyVideoPlayer.getThumbImageViewLayout().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.isEmpty() ? 0 : list.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        SwitchVideo gsyVideoPlayer;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gsyVideoPlayer = itemView.findViewById(R.id.video_item_player);
            imageView = new ImageView(context);
        }
    }

}
