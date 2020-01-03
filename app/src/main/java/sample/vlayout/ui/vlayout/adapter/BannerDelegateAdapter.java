package sample.vlayout.ui.vlayout.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.vlayout.LayoutHelper;

import java.util.ArrayList;
import java.util.List;

import sample.vlayout.R;
import sample.vlayout.ui.vlayout.DataType;
import sample.vlayout.ui.vlayout.entity.VideoListEntity;
import sample.vlayout.utils.image.ImageLoader;

/**
 * Title: BannerDelegateAdapter
 * <p>
 * Description:
 * </p>
 *
 * @author Changbao
 * @date 2019/12/24  15:35
 */
public class BannerDelegateAdapter extends BaseDelegateAdapter{

    private List<VideoListEntity.DataBean.ContentBean> content;
    private int prePosition = -1;

    public BannerDelegateAdapter(Context context, LayoutHelper layoutHelper, int layoutId, int count, int viewTypeItem) {
        super(context, layoutHelper, layoutId, count, viewTypeItem);
    }

    @Override
    public void onViewRecycled(@NonNull BaseVirtualViewHolder holder) {
        super.onViewRecycled(holder);
        //     Toast.makeText(mContext, "Banner 被回收 ", Toast.LENGTH_SHORT).show();
        //recyclerView.getRecycledViewPool().getRecycledViewCount(ViewType.TYPE_BANNER)
    }

    public void setData(List<VideoListEntity.DataBean.ContentBean> content) {
        this.content = content;
    }

    @Override
    public void onViewDetachedFromWindow(BaseVirtualViewHolder holder) {
//        if (holder.itemView instanceof ViewPager) {
//            RecyclerView recyclerView = ((RecyclerView) holder.itemView);
//            int position = layoutManager.findFirstVisibleItemPosition();
//            View view = layoutManager.findViewByPosition(position);
//            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
//            if (view != null) {
//                xOffset = view.getLeft() - lp.leftMargin; //如果你设置了margin则减去
//            }
//        }
        ViewPager banner = holder.getView(R.id.banner);
        prePosition = banner.getCurrentItem();
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewAttachedToWindow(BaseVirtualViewHolder holder) {
        super.onViewAttachedToWindow(holder);
//        if (holder.itemView instanceof ViewPager) {
//            layoutManager.scrollToPositionWithOffset(position, xOffset);
//        }
        ViewPager banner = holder.getView(R.id.banner);
        banner.setCurrentItem(prePosition, false);
    }

    @Override
    protected void onBindViewHolderWithOffset(BaseVirtualViewHolder holder, int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);

        List<ImageView> viewList = new ArrayList<>();
        List<String> imgUrls = new ArrayList<>();
        for (VideoListEntity.DataBean.ContentBean bean : content) {
            imgUrls.add(bean.getCover());
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String action = "";
                    int dataType = bean.getDataType();
                    switch (dataType) {
                        case DataType.WEBVIEW:
                            action = "WebView展示";
                            break;
                        case DataType.OTHER_APP:
                            action = "跳转到其他APP";
                            break;
                        case DataType.OTHER_PAGE:
                            action = "跳转到其他页面";
                            break;
                        default:
                    }
                    Toast.makeText(mContext, action + "  \n  " + bean.getUrl(), Toast.LENGTH_SHORT).show();
                }
            });
            viewList.add(imageView);
        }
        final ViewPager banner = holder.getView(R.id.banner);
        banner.setOffscreenPageLimit(viewList.size());
        banner.setAdapter(new PagerAdapter() {
            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public int getCount() {
                return viewList == null || viewList.isEmpty() ? 0 : viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = viewList.get(position);
                container.addView(imageView);
                ImageLoader.get().loadImage(imageView, imgUrls.get(position));

                return viewList.get(position);
            }
        });
    }
}