package com.znz.zuowen.ui.home.article;

import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;

/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleDetailAct extends BaseAppActivity {
    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_article_detail, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("作文详情");
        znzToolBar.setNavRightImg(R.mipmap.icon_dianzanhui);
        znzToolBar.setNavRightImg2(R.mipmap.icon_shoucanghui);
    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void loadDataFromServer() {

    }
}
