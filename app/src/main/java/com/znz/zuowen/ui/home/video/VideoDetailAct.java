package com.znz.zuowen.ui.home.video;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.libvideo.listener.SampleListener;
import com.znz.libvideo.videoplayer.builder.GSYVideoOptionBuilder;
import com.znz.libvideo.videoplayer.listener.LockClickListener;
import com.znz.libvideo.videoplayer.utils.Debuger;
import com.znz.libvideo.videoplayer.utils.OrientationUtils;
import com.znz.libvideo.videoplayer.video.StandardGSYVideoPlayer;
import com.znz.libvideo.videoplayer.video.base.GSYVideoPlayer;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.bean.VideoBean;
import com.znz.zuowen.common.Constants;
import com.znz.zuowen.model.ArticleModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class VideoDetailAct extends BaseAppActivity<ArticleModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvTeacher)
    TextView tvTeacher;
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.detailPlayer)
    StandardGSYVideoPlayer detailPlayer;
    private String id;

    private VideoBean bean;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_video_detail, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("微课详情");
        znzToolBar.setNavRightImg(R.mipmap.icon_shoucanghui);
        znzToolBar.setOnNavRightClickListener(v -> {
            Map<String, String> params = new HashMap<>();
            params.put("id", id);
            mModel.requestVideoFav(params, new ZnzHttpListener() {
                @Override
                public void onSuccess(JSONObject responseOriginal) {
                    super.onSuccess(responseOriginal);
                    if (!bean.getIs_collect().equals("1")) {
                        znzToolBar.setNavRightImg(R.mipmap.icon_shoucang);
                        bean.setIs_collect("1");
                    } else {
                        znzToolBar.setNavRightImg(R.mipmap.icon_shoucanghui);
                        bean.setIs_collect("0");
                    }
                }

                @Override
                public void onFail(String error) {
                    super.onFail(error);
                }
            });
        });
    }

    @Override
    protected void initializeView() {

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

//        resolveNormalVideoUI();

        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(activity, true, true);
            }
        });
    }

    @Override
    protected void loadDataFromServer() {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mModel.requestVideoDetail(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                bean = JSONObject.parseObject(responseOriginal.getString("data"), VideoBean.class);
                mDataManager.setValueToView(tvName, bean.getTitle());
                mDataManager.setValueToView(tvContent, bean.getContent());
                mDataManager.setValueToView(tvTeacher, bean.getTeacher_name());
                mDataManager.setValueToView(tvTime, bean.getAddtime());

                if (bean.getIs_collect().equals("1")) {
                    znzToolBar.setNavRightImg(R.mipmap.icon_shoucang);
                } else {
                    znzToolBar.setNavRightImg(R.mipmap.icon_shoucanghui);
                }

                HttpImageView ivImage = new HttpImageView(activity);
                ivImage.loadRectImage(bean.getImage());

                GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
                gsyVideoOption.setThumbImageView(ivImage)
                        .setIsTouchWiget(true)
                        .setRotateViewAuto(false)
                        .setLockLand(false)
                        .setShowFullAnimation(false)
                        .setNeedLockFull(true)
                        .setSeekRatio(1)
                        .setUrl(Constants.IMG_URL + bean.getVideo_url())
                        .setCacheWithPlay(false)
                        .setStandardVideoAllCallBack(new SampleListener() {
                            @Override
                            public void onPrepared(String url, Object... objects) {
                                Debuger.printfError("***** onPrepared **** " + objects[0]);
                                Debuger.printfError("***** onPrepared **** " + objects[1]);
                                super.onPrepared(url, objects);
                                //开始播放了才能旋转和全屏
                                orientationUtils.setEnable(true);
                                isPlay = true;
                            }

                            @Override
                            public void onEnterFullscreen(String url, Object... objects) {
                                super.onEnterFullscreen(url, objects);
                                Debuger.printfError("***** onEnterFullscreen **** " + objects[0]);//title
                                Debuger.printfError("***** onEnterFullscreen **** " + objects[1]);//当前全屏player
                            }

                            @Override
                            public void onAutoComplete(String url, Object... objects) {
                                super.onAutoComplete(url, objects);
                            }

                            @Override
                            public void onClickStartError(String url, Object... objects) {
                                super.onClickStartError(url, objects);
                            }

                            @Override
                            public void onQuitFullscreen(String url, Object... objects) {
                                super.onQuitFullscreen(url, objects);
                                Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                                Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                                if (orientationUtils != null) {
                                    orientationUtils.backToProtVideo();
                                }
                            }
                        })
                        .setLockClickListener(new LockClickListener() {
                            @Override
                            public void onClick(View view, boolean lock) {
                                if (orientationUtils != null) {
                                    //配合下方的onConfigurationChanged
                                    orientationUtils.setEnable(!lock);
                                }
                            }
                        }).build(detailPlayer);
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
//        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
//        getCurPlay().onVideoResume();
        super.onResume();
        isPause = false;
    }

    @Override
    public void onBackPressed() {

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
//            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils);
        }
    }

    private GSYVideoPlayer getCurPlay() {
        if (detailPlayer.getFullWindowPlayer() != null) {
            return detailPlayer.getFullWindowPlayer();
        }
        return detailPlayer;
    }
}
