

package sample.vlayout.utils.image.strategy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * 图片加载策略
 *
 * @author Changbao
 * @since 2019-07-26 00:06
 */
public interface IImageLoadStrategy {

    /**
     * 加载图片【最常用】
     *
     * @param imageView 图片控件
     * @param path      图片资源的索引
     */
    void loadImage(@NonNull ImageView imageView, Object path);

    /**
     * 加载Gif图片【最常用】
     *
     * @param imageView 图片控件
     * @param path      图片资源的索引
     */
    void loadGifImage(@NonNull ImageView imageView, Object path);

    /**
     * 加载图片
     *
     * @param imageView 图片控件
     * @param path      图片资源的索引
     * @param strategy  磁盘缓存策略
     */
    void loadImage(@NonNull ImageView imageView, Object path, DiskCacheStrategy strategy);


    /**
     * 加载Gif图片
     *
     * @param imageView 图片控件
     * @param path      图片资源的索引
     * @param strategy  磁盘缓存策略
     */
    void loadGifImage(@NonNull ImageView imageView, Object path, DiskCacheStrategy strategy);

    /**
     * 加载图片
     *
     * @param imageView   图片控件
     * @param path        图片资源的索引
     * @param placeholder 占位图片
     * @param strategy    磁盘缓存策略
     */
    void loadImage(@NonNull ImageView imageView, Object path, Drawable placeholder, DiskCacheStrategy strategy);

    /**
     * 加载Gif图片
     *
     * @param imageView   图片控件
     * @param path        图片资源的索引
     * @param placeholder 占位图片
     * @param strategy    磁盘缓存策略
     */
    void loadGifImage(@NonNull ImageView imageView, Object path, Drawable placeholder, DiskCacheStrategy strategy);

    /**
     * 加载指定宽高的图片
     *
     * @param imageView   图片控件
     * @param path        图片资源的索引
     * @param width       宽
     * @param height      高
     * @param placeholder 占位图片
     * @param strategy    磁盘缓存策略
     */
    void loadImage(@NonNull ImageView imageView, Object path, int width, int height, Drawable placeholder, DiskCacheStrategy strategy);


    /**
     * 清除缓存【内存和磁盘缓存】
     *
     * @param context
     */
    void clearCache(Context context);

    /**
     * 清除内存缓存
     *
     * @param context
     */
    void clearMemoryCache(Context context);

    /**
     * 清除磁盘缓存
     *
     * @param context
     */
    void clearDiskCache(Context context);

}
