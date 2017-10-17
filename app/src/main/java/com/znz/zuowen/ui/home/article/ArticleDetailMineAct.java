package com.znz.zuowen.ui.home.article;

import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.model.ArticleModel;

/**
 * Date： 2017/10/9 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleDetailMineAct extends BaseAppActivity<ArticleModel> {

    private String id;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_article_detail_mine, 1};
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
        setTitleName("我的作文");
    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void loadDataFromServer() {

    }
}
