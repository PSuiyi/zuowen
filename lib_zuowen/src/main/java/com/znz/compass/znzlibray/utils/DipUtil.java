package com.znz.compass.znzlibray.utils;

import android.content.Context;

import com.znz.compass.znzlibray.ZnzApplication;

/**
 * @author zhouyong
 * @date 2014-5-27 下午4:16:27
 */
public class DipUtil {

    /**
     * dip 转 pixels
     */
    public static int dipToPixels(float dip) {
        return (int) (dip * ScreenUtils.getInstance().density + 0.5f);
    }

    /**
     * pixels 转 dip
     */
    public static int pixelsToDip(float pixels) {
        return (int) (pixels / ScreenUtils.getInstance().density + 0.5f);
    }

    /**
     * sp 转 pixels
     */
    public static int spToPixels(float sp) {
        return (int) (sp * ScreenUtils.getInstance().scaledDensity + 0.5f);
    }

    /**
     * pixels 转 sp
     */
    public static int pixelsToSp(float pixels) {
        return (int) (pixels / ScreenUtils.getInstance().scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = ZnzApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    @Deprecated
    public static int px2dip(float pxValue) {
        return (int) (pxValue / ScreenUtils.getInstance().scaledDensity + 0.5f);
    }

    //转换dip为px
    public static int convertDip2Px(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    //转换px为dip
    public static int convertPx2Dip(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    public static int convertSp2Px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int convertPx2Sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
