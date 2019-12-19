package com.ando.ui.banner.widget.banner;

/**
 * 图片轮播条目
 *
 * @author Changbao
 * @since 2018/11/25 下午7:01
 */
public class BannerItem {

    public String title;
    public String imgUrl;

    public BannerItem() {
    }

    public BannerItem(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public BannerItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public BannerItem setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

}
