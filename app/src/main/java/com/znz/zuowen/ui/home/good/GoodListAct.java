package com.znz.zuowen.ui.home.good;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ArticleAdapter;
import com.znz.zuowen.base.BaseAppListActivity;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.bean.OptionBean;
import com.znz.zuowen.model.ArticleModel;
import com.znz.zuowen.utils.PopupWindowManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class GoodListAct extends BaseAppListActivity<ArticleModel, ArticleBean> {

    @Bind(R.id.tvLeft)
    TextView tvLeft;
    @Bind(R.id.llLeft)
    LinearLayout llLeft;
    @Bind(R.id.tvRight)
    TextView tvRight;
    @Bind(R.id.llRight)
    LinearLayout llRight;

    private List<OptionBean> leftList = new ArrayList<>();
    private List<OptionBean> rightList = new ArrayList<>();

    private String style_type;
    private String counts_id;
    private boolean isLoaded;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_good, 2};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
    }

    @Override
    protected void initializeNavigation() {
        znzToolBar.setSearchHint("请输入作文题目");
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    protected void initializeView() {
        adapter = new ArticleAdapter(dataList);
        rvRefresh.setAdapter(adapter);
        ((ArticleAdapter) adapter).setPage("优秀作文");
    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        if (!StringUtil.isBlank(style_type)) {
            params.put("style_type", style_type);
        }
        if (!StringUtil.isBlank(counts_id)) {
            params.put("counts_id", counts_id);
        }
        return mModel.requestGoodList(params);
    }

    @Override
    protected void onRefreshSuccess(String response) {
        dataList.addAll(JSONArray.parseArray(responseJson.getString("list"), ArticleBean.class));
        adapter.notifyDataSetChanged();

        //判断是否加载过，如果加载过条件就不加载了
        if (!isLoaded) {
            leftList.clear();
            rightList.clear();
            OptionBean optionBean = new OptionBean();
            optionBean.setChecked(true);
            optionBean.setStyle_type("全部");
            leftList.add(optionBean);
            leftList.addAll(JSONArray.parseArray(responseJson.getString("style_type"), OptionBean.class));
            if (!leftList.isEmpty()) {
                leftList.get(0).setChecked(true);
            }
            rightList.addAll(JSONArray.parseArray(responseJson.getString("counts_list"), OptionBean.class));
            if (!rightList.isEmpty()) {
                rightList.get(0).setChecked(true);
            }
        }

        isLoaded = true;
    }

    @Override
    protected void onRefreshFail(String error) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.llLeft, R.id.llRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llLeft:
                PopupWindowManager.getInstance(activity).showOptionLeft(view, leftList, (type, values) -> {
                    if (values[0].equals("全部")) {
                        style_type = "";
                    } else {
                        style_type = values[0];
                    }
                    resetRefresh();
                });
                break;
            case R.id.llRight:
                PopupWindowManager.getInstance(activity).showOptionRight(view, rightList, (type, values) -> {
                    if (values[0].equals("全部")) {
                        counts_id = "";
                    } else {
                        counts_id = values[0];
                    }
                    resetRefresh();
                });
                break;
        }
    }
}
