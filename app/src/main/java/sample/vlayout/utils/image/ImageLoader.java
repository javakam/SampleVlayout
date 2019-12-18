package sample.vlayout.utils.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import sample.vlayout.utils.image.strategy.IImageLoadStrategy;
import sample.vlayout.utils.image.strategy.impl.GlideImageLoadStrategy;

/**
 * 图片加载策略管理
 *
 * @author Changbao
 * @since 2019-07-26 00:27
 */
public class ImageLoader implements IImageLoadStrategy {


    private static volatile ImageLoader sInstance = null;

    /**
     * 图片加载策略
     */
    private IImageLoadStrategy mStrategy;


    private ImageLoader() {
        mStrategy = new GlideImageLoadStrategy();
    }

    /**
     * 设置图片加载的策略
     *
     * @param strategy
     */
    public ImageLoader setImageLoadStrategy(@NonNull IImageLoadStrategy strategy) {
        mStrategy = strategy;
        return this;
    }

    public IImageLoadStrategy getStrategy() {
        return mStrategy;
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static ImageLoader get() {
        if (sInstance == null) {
            synchronized (ImageLoader.class) {
                if (sInstance == null) {
                    sInstance = new ImageLoader();
                }
            }
        }
        return sInstance;
    }


    @Override
    public void loadImage(@NonNull ImageView imageView, Object path) {
        mStrategy.loadImage(imageView, path);
    }

    @Override
    public void loadGifImage(@NonNull ImageView imageView, Object path) {
        mStrategy.loadGifImage(imageView, path);
    }

    @Override
    public void loadImage(@NonNull ImageView imageView, Object path, DiskCacheStrategy strategy) {
        mStrategy.loadImage(imageView, path, strategy);
    }

    @Override
    public void loadGifImage(@NonNull ImageView imageView, Object path, DiskCacheStrategy strategy) {
        mStrategy.loadGifImage(imageView, path, strategy);
    }

    @Override
    public void loadImage(@NonNull ImageView imageView, Object path, Drawable placeholder, DiskCacheStrategy strategy) {
        mStrategy.loadImage(imageView, path, placeholder, strategy);
    }

    @Override
    public void loadGifImage(@NonNull ImageView imageView, Object path, Drawable placeholder, DiskCacheStrategy strategy) {
        mStrategy.loadGifImage(imageView, path, placeholder, strategy);
    }

    @Override
    public void loadImage(@NonNull ImageView imageView, Object path, int width, int height, Drawable placeholder, DiskCacheStrategy strategy) {
        mStrategy.loadImage(imageView, path, width, height, placeholder, strategy);
    }

    @Override
    public void clearCache(Context context) {
        mStrategy.clearCache(context);
    }

    @Override
    public void clearMemoryCache(Context context) {
        mStrategy.clearMemoryCache(context);
    }

    @Override
    public void clearDiskCache(Context context) {
        mStrategy.clearDiskCache(context);
    }
}
