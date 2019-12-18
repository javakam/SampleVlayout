package sample.vlayout.utils.image.strategy.impl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import sample.vlayout.utils.image.strategy.IImageLoadStrategy;

/**
 * Glide图片加载策略
 *
 * @author Changbao
 * @since 2019-11-20 21:57:43
 */
public class GlideImageLoadStrategy implements IImageLoadStrategy {

    @Override
    public void loadImage(@NonNull ImageView imageView, Object path) {
        Glide.with(imageView.getContext())
                .load(path)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @Override
    public void loadGifImage(@NonNull ImageView imageView, Object path) {
        Glide.with(imageView.getContext())
                .asGif()
                .load(path)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @Override
    public void loadImage(@NonNull ImageView imageView, Object path, DiskCacheStrategy strategy) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(strategy);

        Glide.with(imageView.getContext())
                .load(path)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @Override
    public void loadGifImage(@NonNull ImageView imageView, Object path, DiskCacheStrategy strategy) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(strategy);

        Glide.with(imageView.getContext())
                .asGif()
                .load(path)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @Override
    public void loadImage(@NonNull ImageView imageView, Object path, Drawable placeholder, DiskCacheStrategy strategy) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholder)
                .diskCacheStrategy(strategy);

        Glide.with(imageView.getContext())
                .load(path)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @Override
    public void loadGifImage(@NonNull ImageView imageView, Object path, Drawable placeholder, DiskCacheStrategy strategy) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholder)
                .diskCacheStrategy(strategy);

        Glide.with(imageView.getContext())
                .asGif()
                .load(path)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @Override
    public void loadImage(@NonNull ImageView imageView, Object path, int width, int height, Drawable placeholder, DiskCacheStrategy strategy) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .override(width, height)
                .placeholder(placeholder)
                .diskCacheStrategy(strategy);

        Glide.with(imageView.getContext())
                .load(path)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

    }

    @Override
    public void clearCache(Context context) {
        Glide.get(context).clearMemory();
        Glide.get(context).clearDiskCache();
    }

    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

}