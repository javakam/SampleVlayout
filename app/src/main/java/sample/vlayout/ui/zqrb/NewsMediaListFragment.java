package sample.vlayout.ui.zqrb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sample.vlayout.R;

/**
 * Title: NewsMediaListFragment
 * <p>
 * Description:
 * </p>
 *
 * @author Changbao
 * @date 2019/12/17  13:49
 */
public class NewsMediaListFragment extends Fragment {

    //https://www.jianshu.com/p/fde38f367019
    //https://github.com/18380438200/TablayoutUseCase
    //https://github.com/AndroidKun/XTabLayout
    /*
    app:tabIndicatorColor ：指示线的颜色
    app:tabIndicatorHeight ：指示线的高度
    app:tabSelectedTextColor ： tab选中时的字体颜色
    app:tabMode="scrollable" ： 默认是fixed，固定的；scrollable：可滚动的
     */

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] titles = {"视频", "音频"};
    private List<Fragment> fragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        mTabLayout = view.findViewById(R.id.tabLayout);
        mViewPager = view.findViewById(R.id.viewPager);

        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                IndicatorLineUtil.setIndicator(mTabLayout, dp2px(15D), dp2px(15D));
            }
        });

        fragments.add(NewsVideoListFragment.newInstance());
        fragments.add(NewsAudioListFragment.newInstance());

        BaseStatePagersAdapter pagerAdapter = new BaseStatePagersAdapter(getChildFragmentManager());
        pagerAdapter.setData(Arrays.asList(titles), fragments);
        mViewPager.setAdapter(pagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager, false);

        return view;
    }

    private int dp2px(double dpValue) {
        float density = getActivity().getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5);
    }


}