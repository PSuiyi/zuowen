package com.znz.compass.znzlibray.views.imageloder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.utils.GlideCircleTransform;
import com.znz.compass.znzlibray.utils.GlideRoundTransform;
import com.znz.compass.znzlibray.utils.StringUtil;


/**
 * 网络图片加载控件
 */
public class HttpImageView extends AppCompatImageView {
    private static final String TAG = "HttpImageView";

    private Context context;
    private int default_image = R.mipmap.default_image_square; // 还没加载时的图片
    private int error_image = R.mipmap.default_image_square; // 加载失败的图片

    public HttpImageView(Context context) {
        super(context);
        initialize(context);
    }

    public HttpImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public HttpImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    /**
     * 初始化加载中图片和加载失败图片
     *
     * @param context
     */
    private void initialize(Context context) {
        this.context = context;
//        this.setImageResource(default_image);
    }

    /**
     * 设置网络图片（开放）
     *
     * @param url_image
     */
    @Deprecated
    public void loadHttpImage(final String url_image) {
        if (StringUtil.isBlank(url_image)) {
            this.setImageResource(R.mipmap.ic_launcher);
            this.setScaleType(ScaleType.FIT_CENTER);
        } else {
            Glide.with(context)
                    .load(url_image)
                    .thumbnail(0.1f)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(error_image)
                    .placeholder(default_image)
                    .into(this);
        }
    }

    /**
     * 加载直播图片
     *
     * @param url
     */
    public void loadLiveImage(String url) {
        if (!StringUtil.isBlank(url)) {
            loadRectImage(url);
        } else {
            setImageResource(R.mipmap.default_live_icon);
        }
    }

    /**
     * 加载长方形图
     *
     * @param url_image
     */
    public void loadRectImage(final String url_image) {
        default_image = R.mipmap.default_image_rect;
        error_image = R.mipmap.default_image_rect;

        if (StringUtil.isBlank(url_image)) {
            this.setImageResource(default_image);
            this.setScaleType(ScaleType.CENTER_CROP);
        } else {
            Glide.with(context)
                    .load(url_image)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(error_image)
                    .placeholder(default_image)
                    .into(this);
        }
    }

    /**
     * 加载长方形图
     *
     * @param url_image
     */
    public void loadRectImageWithFitCenter(final String url_image) {
        default_image = R.mipmap.default_image_rect;
        error_image = R.mipmap.default_image_rect;

        if (StringUtil.isBlank(url_image)) {
            this.setImageResource(default_image);
            this.setScaleType(ScaleType.FIT_CENTER);
        } else {
            Glide.with(context)
                    .load(url_image)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(error_image)
                    .placeholder(default_image)
                    .into(this);
        }
    }

    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public void loadFullImage(final String imageUrl) {
        default_image = R.mipmap.default_image_rect;
        error_image = R.mipmap.default_image_rect;
        Glide.with(context)
                .load(imageUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (this == null) {
                            return false;
                        }
                        if (getScaleType() != ImageView.ScaleType.FIT_XY) {
                            setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = getLayoutParams();
                        int vw = getWidth() - getPaddingLeft() - getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + getPaddingTop() + getPaddingBottom();
                        setLayoutParams(params);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(3000, 3000)
                .error(error_image)
                .placeholder(default_image)
                .into(this);
    }

    /**
     * 加载长方形图
     *
     * @param url_image
     */
    public void loadCodeImage(final String url_image) {
        default_image = R.mipmap.default_image_rect;
        error_image = R.mipmap.default_image_rect;

        if (StringUtil.isBlank(url_image)) {
            this.setImageResource(default_image);
            this.setScaleType(ScaleType.CENTER_CROP);
        } else {
            Glide.with(context)
                    .load(url_image)
                    .centerCrop()
                    .skipMemoryCache(true)
                    .signature(new StringSignature((Math.random() * (100000000 - 1 + 1)) + ""))
                    .error(error_image)
                    .placeholder(default_image)
                    .into(this);
        }
    }

    /**
     * 加载正方形图
     *
     * @param url_image
     */
    public void loadSquareImage(final String url_image) {
        default_image = R.mipmap.default_image_square;
        error_image = R.mipmap.default_image_square;

        if (StringUtil.isBlank(url_image)) {
            this.setImageResource(default_image);
            this.setScaleType(ScaleType.CENTER_CROP);
        } else {
            Glide.with(context)
                    .load(url_image)
                    .thumbnail(0.1f)
                    .fitCenter()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(error_image)
                    .placeholder(default_image)
                    .into(this);
        }
    }

    /**
     * 设置头像
     *
     * @param url_image
     */
    public void loadHeaderImage(final String url_image) {
        loadHeaderImage(url_image, true);
    }

    /**
     * 设置头像
     *
     * @param url_image
     * @param canCache
     */
    public void loadHeaderImage(final String url_image, boolean canCache) {
        default_image = R.mipmap.dfthead2;
        error_image = R.mipmap.dfthead2;

        try {
            if (StringUtil.isBlank(url_image)) {
                if (canCache) {
                    Glide.with(context)
                            .load(default_image)
                            .centerCrop()
                            .error(error_image)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(default_image)
                            .transform(new GlideCircleTransform(context))
                            .into(this);
                } else {
                    Glide.with(context)
                            .load(default_image)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .error(error_image)
                            .placeholder(default_image)
                            .transform(new GlideCircleTransform(context))
                            .into(this);
                }

            } else {
                if (canCache) {
                    Glide.with(context)
                            .load(url_image)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(error_image)
                            .placeholder(default_image)
                            .transform(new GlideCircleTransform(context))
                            .into(this);
                } else {
                    Glide.with(context)
                            .load(url_image)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .error(error_image)
                            .placeholder(default_image)
                            .transform(new GlideCircleTransform(context))
                            .into(this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置圆角
     *
     * @param url_image
     */
    public void loadRoundImage(final String url_image) {
        default_image = R.mipmap.default_image_rect;
        error_image = R.mipmap.default_image_rect;

        try {
            if (StringUtil.isBlank(url_image)) {
                Glide.with(context)
                        .load(default_image)
                        .error(error_image)
                        .placeholder(default_image)
                        .transform(new GlideRoundTransform(context, 10))
                        .into(this);
            } else {
                Glide.with(context)
                        .load(url_image)
                        .fitCenter()
                        .error(error_image)
                        .placeholder(default_image)
                        .transform(new GlideRoundTransform(context, 20))
                        .into(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置圆角
     *
     * @param url_image
     */
    public void loadRoundImage(final int url_image) {
        default_image = R.mipmap.default_image_square;
        error_image = R.mipmap.default_image_square;

        try {
            Glide.with(context)
                    .load(url_image)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(error_image)
                    .placeholder(default_image)
                    .transform(new GlideRoundTransform(context, 6))
                    .into(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置大图预览
     *
     * @param url_image
     */
    public void loadHttpImagePreview(final String url_image) {
        default_image = R.mipmap.default_image_square;
        error_image = R.mipmap.default_image_square;
        Glide.with(getContext())
                .load(url_image)
                .error(error_image)
                .placeholder(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this);
    }

}
