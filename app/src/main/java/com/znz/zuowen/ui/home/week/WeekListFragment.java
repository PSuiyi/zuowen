package com.znz.zuowen.ui.home.week;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.views.ios.ActionSheetDialog.UIAlertDialog;
import com.znz.compass.znzlibray.views.recyclerview.BaseQuickAdapter;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.WeekAdapter;
import com.znz.zuowen.base.BaseAppListFragment;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.model.ArticleModel;
import com.znz.zuowen.ui.home.article.ArticleDetailMineAct;
import com.znz.zuowen.ui.mine.MineClassAct;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class WeekListFragment extends BaseAppListFragment<ArticleModel, ArticleBean> {

    private String page;

    public static WeekListFragment newInstance(String page) {
        Bundle args = new Bundle();
        args.putString("page", page);
        WeekListFragment fragment = new WeekListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_list_layout};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
        if (getArguments() != null) {
            page = getArguments().getString("page");
        }
    }

    @Override
    protected void initializeNavigation() {

    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new WeekAdapter(dataList);
        rvRefresh.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean bean = dataList.get(position);
                if (bean.getIs_model().equals("1")) {
                    gotoWeekDetail(bean);
                    return;
                }

                if (bean.getIs_my_week().equals("1")) {
                    gotoWeekDetail(bean);
                    return;
                }

                new UIAlertDialog(activity)
                        .builder()
                        .setMsg("确定花费50积分练习该作文？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", v2 -> {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", bean.getId());
                            mModel.requestWeekBuy(params, new ZnzHttpListener() {
                                @Override
                                public void onSuccess(JSONObject responseOriginal) {
                                    super.onSuccess(responseOriginal);
                                    gotoWeekDetail(bean);
                                    bean.setIs_my_week("1");
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFail(String error) {
                                    new UIAlertDialog(activity)
                                            .builder()
                                            .setMsg("积分不够，是否现在购买？")
                                            .setNegativeButton("取消", null)
                                            .setPositiveButton("确定", v2 -> {
                                                gotoActivity(MineClassAct.class);
                                            })
                                            .show();
                                }
                            });
                        })
                        .show();
            }
        });
    }

    /**
     * 跳转至详情
     *
     * @param bean
     */
    private void gotoWeekDetail(ArticleBean bean) {
        if (!StringUtil.isBlank(bean.getFirst_status())) {
            if (bean.getFirst_status().equals("1") || bean.getFirst_status().equals("2")) {
                Bundle bundle = new Bundle();
                bundle.putString("id", bean.getId());
                bundle.putString("title", "作文要求");
                gotoActivity(ArticleDetailMineAct.class, bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("id", bean.getId());
                gotoActivity(WeekDetailAct.class, bundle);
            }
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("id", bean.getId());
            gotoActivity(WeekDetailAct.class, bundle);
        }
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        if (!StringUtil.isBlank(page)) {
            switch (page) {
                case "小学组":
                    params.put("cate_type", "1");
                    return mModel.requestWeekList(params);
                case "初中组":
                    params.put("cate_type", "2");
                    return mModel.requestWeekList(params);
                case "高中组":
                    params.put("cate_type", "3");
                    return mModel.requestWeekList(params);
                default:
                    return mModel.requestHomeList(params);
            }
        } else {
            return mModel.requestHomeList(params);
        }
    }

    @Override
    protected void onRefreshSuccess(String response) {
        dataList.addAll(JSONArray.parseArray(response, ArticleBean.class));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRefreshFail(String error) {

    }
}
