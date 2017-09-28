package com.znz.compass.znzlibray.views.advs.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.znz.compass.znzlibray.R;


/**
 * ImageView创建工厂
 */
public class ViewFactory {

    /**
     * 获取ImageView视图的同时加载显示url
     *
     * @param
     * @return
     */
    public static ImageView getImageView(Context context, String url) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
                R.layout.view_banner, null);
        Glide.with(context)
                .load(url)
                .error(R.mipmap.default_image_rect)
                .placeholder(R.mipmap.default_image_rect)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        return imageView;
    }
}
