package com.znz.compass.znzlibray.views.gallery.config;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.znz.compass.znzlibray.views.gallery.activity.GalleryPickActivity;
import com.znz.compass.znzlibray.views.gallery.utils.FileUtils;


/**
 * GalleryPick 启动类 (单例)
 * Created by Yancy on 2016/1/27.
 */
public class GalleryPick {

    private final static String TAG = "GalleryPick";

    private static GalleryPick galleryPick;

    private GalleryConfig galleryConfig;

    public static GalleryPick getInstance() {
        if (galleryPick == null) {
            galleryPick = new GalleryPick();
        }
        return galleryPick;
    }


    public void open(Activity mActivity) {
        if (galleryPick.galleryConfig == null) {
            Log.e(TAG, "请配置 GalleryConfig");
            return;
        }
        if (galleryPick.galleryConfig.getImageLoader() == null) {
            Log.e(TAG, "请配置 ImageLoader");
            return;
        }
        if (galleryPick.galleryConfig.getIHandlerCallBack() == null) {
            Log.e(TAG, "请配置 IPhotoSelectCallback");
            return;
        }

        FileUtils.createFile(galleryPick.galleryConfig.getFilePath());

        Intent intent = new Intent(mActivity, GalleryPickActivity.class);
        mActivity.startActivity(intent);
    }

    public void openCamera(Activity mActivity) {
        if (galleryPick.galleryConfig == null) {
            Log.e(TAG, "请配置 GalleryConfig");
            return;
        }
        if (galleryPick.galleryConfig.getIHandlerCallBack() == null) {
            Log.e(TAG, "请配置 IPhotoSelectCallback");
            return;
        }

        FileUtils.createFile(galleryPick.galleryConfig.getFilePath());

        Intent intent = new Intent(mActivity, GalleryPickActivity.class);
        intent.putExtra("isOpenCamera", true);
        mActivity.startActivity(intent);
    }


    public GalleryPick setGalleryConfig(GalleryConfig galleryConfig) {
        this.galleryConfig = galleryConfig;
        return this;
    }

    public GalleryConfig getGalleryConfig() {
        return galleryConfig;
    }

}