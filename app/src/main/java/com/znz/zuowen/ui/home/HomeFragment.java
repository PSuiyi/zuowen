package com.znz.zuowen.ui.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONArray;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.views.advs.bean.AdvInfoBean;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.MultiAdapter;
import com.znz.zuowen.base.BaseAppListFragment;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.bean.MultiBean;
import com.znz.zuowen.common.Constants;
import com.znz.zuowen.event.EventList;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.ArticleModel;
import com.znz.zuowen.ui.common.HelpAct;
import com.znz.zuowen.ui.home.article.ArticleListAct;
import com.znz.zuowen.ui.home.message.MessageListAct;
import com.znz.zuowen.ui.home.teacher.TeacherListAct;
import com.znz.zuowen.ui.home.video.VideoListAct;
import com.znz.zuowen.ui.home.vote.VoteStageAct;
import com.znz.zuowen.ui.home.week.WeekArticleAct;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class HomeFragment extends BaseAppListFragment<ArticleModel, MultiBean> {

    private View header;
    private BGABanner mBanner;
    private List<AdvInfoBean> advList = new ArrayList<>();

    private LinearLayout llMenu1;
    private LinearLayout llMenu2;
    private LinearLayout llMenu3;
    private LinearLayout llMenu4;
    private LinearLayout llMenu5;
    private LinearLayout llMenu6;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout_withnav, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("特级教师改作文");
        setNavLeftGone();
        znzToolBar.setNavRightImg(R.mipmap.bangzhu);
        znzToolBar.setOnNavRightClickListener(v -> {
            gotoActivity(HelpAct.class);
        });
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new MultiAdapter(dataList);
        rvRefresh.setAdapter(adapter);

        header = View.inflate(activity, R.layout.header_home, null);
        adapter.addHeaderView(header);

        llMenu1 = bindViewById(header, R.id.llMenu1);
        llMenu2 = bindViewById(header, R.id.llMenu2);
        llMenu3 = bindViewById(header, R.id.llMenu3);
        llMenu4 = bindViewById(header, R.id.llMenu4);
        llMenu5 = bindViewById(header, R.id.llMenu5);
        llMenu6 = bindViewById(header, R.id.llMenu6);

        ViewTreeObserver vto = llMenu1.getViewTreeObserver();
        vto.addOnPreDrawListener(() -> {
            int width = llMenu1.getMeasuredWidth();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llMenu1.getLayoutParams();
            layoutParams.height = width;
            llMenu1.setLayoutParams(layoutParams);
            llMenu2.setLayoutParams(layoutParams);
            llMenu3.setLayoutParams(layoutParams);
            llMenu4.setLayoutParams(layoutParams);
            llMenu5.setLayoutParams(layoutParams);
            llMenu6.setLayoutParams(layoutParams);
            return true;
        });

        llMenu1.setOnClickListener(v -> {
            gotoActivity(WeekArticleAct.class);
        });
        llMenu2.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("page", "我的作文");
            gotoActivity(ArticleListAct.class, bundle);
        });
        llMenu3.setOnClickListener(v -> {
            gotoActivity(VoteStageAct.class);
        });
        llMenu4.setOnClickListener(v -> {
            gotoActivity(TeacherListAct.class);
        });
        llMenu5.setOnClickListener(v -> {
            gotoActivity(VideoListAct.class);
        });
        llMenu6.setOnClickListener(v -> {
            gotoActivity(MessageListAct.class);
        });
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        return mModel.requestHomeList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        dataList.add(new MultiBean(Constants.MultiType.Section, "国内面料"));
        for (ArticleBean articleBean : JSONArray.parseArray(response, ArticleBean.class)) {
            dataList.add(new MultiBean(Constants.MultiType.WeekStar, articleBean));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventList event) {
        if (event.getFlag() == EventTags.LIST_ARTICLE_FAV) {
            for (MultiBean multiBean : dataList) {
                if (multiBean.getItemType() == Constants.MultiType.WeekStar) {
                    if (multiBean.getArticleBean().equals(event.getBean())) {
                        multiBean.getArticleBean().setCollect_count(((ArticleBean) event.getBean()).getCollect_count());
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }

        if (event.getFlag() == EventTags.LIST_ARTICLE_LIKE) {
            for (MultiBean multiBean : dataList) {
                if (multiBean.getItemType() == Constants.MultiType.WeekStar) {
                    if (multiBean.getArticleBean().equals(event.getBean())) {
                        multiBean.getArticleBean().setLike_count(((ArticleBean) event.getBean()).getLike_count());
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }
}
