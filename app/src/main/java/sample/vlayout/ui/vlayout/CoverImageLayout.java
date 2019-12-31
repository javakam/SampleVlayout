package sample.vlayout.ui.vlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageView;

import sample.vlayout.R;

/**
 * Title: CoverImageView
 * <p>
 * Description: 带有遮盖图的 ImageView
 *
 * <pre>
 *     <FrameLayout
 *         android:id="@+id/fl_img_container"
 *         android:layout_width="match_parent"
 *         android:layout_height="210dp"
 *         android:layout_gravity="center"
 *         android:descendantFocusability="blocksDescendants">
 *
 *         <ImageView
 *             android:id="@+id/iv_big"
 *             android:layout_width="match_parent"
 *             android:layout_height="200dp"
 *             android:layout_gravity="center"
 *             android:scaleType="fitXY"
 *             tools:background="@color/gray3" />
 *
 *         <ImageView
 *             android:id="@+id/iv_cover_big"
 *             android:layout_width="45dp"
 *             android:layout_height="45dp"
 *             android:layout_gravity="center"
 *             android:scaleType="centerCrop"
 *             android:visibility="gone"
 *             tools:src="@mipmap/ic_launcher_round"
 *             tools:visibility="visible" />
 *
 *     </FrameLayout>
 * </pre>
 * </p>
 *
 * @author Changbao
 * @date 2019/12/19  13:27
 */
public class CoverImageLayout extends FrameLayout {

    private AppCompatImageView backImg, coverImg;
    private ViewGroup controlView;
    private boolean isCoverShow;//注:默认不显示 Cover图片
    private Drawable coverDrawable;

    public CoverImageLayout(Context context) {
        this(context, null, 0);
    }

    public CoverImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoverImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CoverImageLayout);
        coverDrawable = ta.getDrawable(R.styleable.CoverImageLayout_cover);
        ta.recycle();

        backImg = new AppCompatImageView(context, attrs, defStyleAttr);
        backImg.setScaleType(ImageView.ScaleType.FIT_XY);
        backImg.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(context, 200), Gravity.CENTER));

        coverImg = new AppCompatImageView(context, attrs, defStyleAttr);
        coverImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        int px = dp2px(context, 45);
        coverImg.setLayoutParams(new LayoutParams(px, px, Gravity.CENTER));
        if (coverDrawable != null) {
            coverImg.setImageDrawable(coverDrawable);
        }
        coverImg.setVisibility(isCoverShow ? VISIBLE : GONE);

        addView(backImg);
        addView(coverImg);
    }

    public void showCover() {
        showOrHideCover(true);
    }

    public void hideCover() {
        showOrHideCover(false);
    }

    private void showOrHideCover(boolean show) {
        if (coverImg != null) {
            if (show) {
                if (coverImg.getVisibility() == GONE) {
                    coverImg.setVisibility(VISIBLE);
                }
            } else {
                if (coverImg.getVisibility() == VISIBLE) {
                    coverImg.setVisibility(GONE);
                }
            }
        }
    }

    public void hideBackImg() {
        if (backImg != null) {
            backImg.setVisibility(GONE);
        }
    }

    public AppCompatImageView getBackImg() {
        return backImg;
    }

    public AppCompatImageView getCoverImg() {
        return coverImg;
    }

    public void setBackImg(@DrawableRes int resId) {
        if (this.backImg != null) {
            this.backImg.setImageResource(resId);
        }
    }

    public void setCoverImg(@DrawableRes int resId) {
        if (this.coverImg != null) {
            showCover();
            this.coverImg.setImageResource(resId);
        }
    }

    public void setVideoControlView(ViewGroup controlView) {
        this.controlView = controlView;
        hideCover();
        hideBackImg();
        controlView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(controlView);
    }

    private int dp2px(Context context, double dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5);
    }

}