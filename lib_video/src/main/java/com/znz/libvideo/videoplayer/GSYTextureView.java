package com.znz.libvideo.videoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

import com.znz.libvideo.videoplayer.utils.MeasureHelper;


/**
 * 用于显示video的，做了横屏与竖屏的匹配，还有需要rotation需求的
 * Created by shuyu on 2016/11/11.
 */

public class GSYTextureView extends TextureView {

    private MeasureHelper measureHelper;

    public GSYTextureView(Context context) {
        super(context);
        init();
    }

    public GSYTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        measureHelper = new MeasureHelper(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (GSYVideoManager.instance().getMediaPlayer() != null) {
            try {
                int videoWidth = GSYVideoManager.instance().getCurrentVideoWidth();
                int videoHeight = GSYVideoManager.instance().getCurrentVideoHeight();

                int videoSarNum = GSYVideoManager.instance().getMediaPlayer().getVideoSarNum();
                int videoSarDen = GSYVideoManager.instance().getMediaPlayer().getVideoSarDen();

                if (videoWidth > 0 && videoHeight > 0) {
                    measureHelper.setVideoSampleAspectRatio(videoSarNum, videoSarDen);
                    measureHelper.setVideoSize(videoWidth, videoHeight);
                }
                measureHelper.setVideoRotation((int) getRotation());
                measureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setMeasuredDimension(measureHelper.getMeasuredWidth(), measureHelper.getMeasuredHeight());
    }

    public int getSizeH() {
        return measureHelper.getMeasuredHeight();
    }

    public int getSizeW() {
        return measureHelper.getMeasuredWidth();
    }
}