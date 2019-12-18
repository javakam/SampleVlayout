package sample.vlayout.ui.zqrb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class BaseStatePagersAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;
    private List<Fragment> mFragments;

    public BaseStatePagersAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_SET_USER_VISIBLE_HINT);//BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    }

    public void setData(List<String> titles, List<Fragment> fragments) {
        this.mTitles = titles;
        this.mFragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}