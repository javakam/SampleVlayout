package sample.vlayout.ui.seamless;

import android.os.Bundle;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sample.vlayout.R;

/**
 * 可切换列表
 */
public class SwitchListVideoActivity extends AppCompatActivity {

    RecyclerView videoList;

    SwitchListVideoAdapter listNormalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video);

        videoList = (RecyclerView) findViewById(R.id.video_list);

        listNormalAdapter = new SwitchListVideoAdapter(this);
        videoList.setAdapter(listNormalAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        videoList.setLayoutManager(layoutManager);
        videoList.setHasFixedSize(true);
        videoList.setItemAnimator(null);

        //https://www.jianshu.com/p/ce347cf991db
        videoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(SwitchListVideoAdapter.TAG)
                            && (position < layoutManager.findFirstVisibleItemPosition()
                            || position > layoutManager.findLastVisibleItemPosition())) {

                        if (GSYVideoManager.isFullState(SwitchListVideoActivity.this)) {
                            return;
                        }
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        GSYVideoManager.releaseAllVideos();
                        listNormalAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }
}
