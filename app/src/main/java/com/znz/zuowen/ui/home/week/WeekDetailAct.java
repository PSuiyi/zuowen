package com.znz.zuowen.ui.home.week;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.ios.ActionSheetDialog.UIAlertDialog;
import com.znz.libvideo.listener.SampleListener;
import com.znz.libvideo.videoplayer.builder.GSYVideoOptionBuilder;
import com.znz.libvideo.videoplayer.listener.LockClickListener;
import com.znz.libvideo.videoplayer.utils.Debuger;
import com.znz.libvideo.videoplayer.utils.OrientationUtils;
import com.znz.libvideo.videoplayer.video.StandardGSYVideoPlayer;
import com.znz.libvideo.videoplayer.video.base.GSYVideoPlayer;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ImageAdapter;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.model.ArticleModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class WeekDetailAct extends BaseAppActivity<ArticleModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvTeacher)
    TextView tvTeacher;
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.rvArticle)
    RecyclerView rvArticle;
    @Bind(R.id.detailPlayer)
    StandardGSYVideoPlayer detailPlayer;
    private String id;
    private ArticleBean bean;

    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_article_subject, 1};
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
        setTitleName("作文要求");
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
        mModel.requestWeekDetail(params, new ZnzHttpListener() {
            @Override
            public void onSuccess(JSONObject responseOriginal) {
                super.onSuccess(responseOriginal);
                bean = JSONObject.parseObject(responseOriginal.getString("data"), ArticleBean.class);
                mDataManager.setValueToView(tvName, bean.getTitle());
                mDataManager.setValueToView(tvTeacher, "命题老师：" + bean.getTeacher_name());
                tvContent.setText(Html.fromHtml(bean.getContent()));
                mDataManager.setValueToView(tvTime, bean.getAddtime());
                if (!bean.getImgurl().isEmpty()) {
                    rvArticle.setVisibility(View.VISIBLE);
                    rvArticle.setLayoutManager(new LinearLayoutManager(activity));
                    rvArticle.setHasFixedSize(true);
                    rvArticle.setNestedScrollingEnabled(false);
                    ImageAdapter imageAdapter = new ImageAdapter(bean.getImgurl());
                    rvArticle.setAdapter(imageAdapter);
                } else {
                    rvArticle.setVisibility(View.GONE);
                }


                HttpImageView ivImage = new HttpImageView(activity);
                ivImage.loadRectImage("");

                GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
                gsyVideoOption.setThumbImageView(ivImage)
                        .setIsTouchWiget(true)
                        .setRotateViewAuto(false)
                        .setLockLand(false)
                        .setShowFullAnimation(false)
                        .setNeedLockFull(true)
                        .setEnlargeImageRes(R.mipmap.icon_qunpin)
                        .setShrinkImageRes(R.mipmap.icon_qunpin)
                        .setSeekRatio(1)
                        .setUrl("")
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

    @OnClick(R.id.tvSubmit)
    public void onViewClicked() {
        if (!StringUtil.isBlank(bean.getIs_my_week())) {
            if (bean.getIs_my_week().equals("1")) {
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                if (bean.getAssign_teacher_id_info() != null) {
                    bundle.putSerializable("bean", bean.getAssign_teacher_id_info());
                }
                gotoActivity(ArticleUploadAct.class, bundle);
            } else {
                buyArticle();
            }
        } else {
            buyArticle();
        }
    }

    private void buyArticle() {
        new UIAlertDialog(activity)
                .builder()
                .setMsg("确定花费5个积分挑战该作文？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v2 -> {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", id);
                    mModel.requestWeekBuy(params, new ZnzHttpListener() {
                        @Override
                        public void onSuccess(JSONObject responseOriginal) {
                            super.onSuccess(responseOriginal);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", id);
                            if (bean.getAssign_teacher_id_info() != null) {
                                bundle.putSerializable("bean", bean.getAssign_teacher_id_info());
                            }
                            gotoActivity(ArticleUploadAct.class, bundle);
                        }

                        @Override
                        public void onFail(String error) {
                            super.onFail(error);
                        }
                    });
                })
                .show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
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
            GSYVideoPlayer.releaseAllVideos();
        }
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
}
