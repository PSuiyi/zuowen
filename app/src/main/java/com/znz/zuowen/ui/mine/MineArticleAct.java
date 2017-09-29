package com.znz.zuowen.ui.mine;

import android.support.v4.app.Fragment;

import com.znz.compass.znzlibray.utils.FragmentUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.ui.home.article.ArticleListFragment;

/**
 * Date： 2017/9/29 2017
 * User： PSuiyi
 * Description：
 */

public class MineArticleAct extends BaseAppActivity {

    private Fragment fragment;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.common_activity_with_fragment, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("微课指导");
    }

    @Override
    protected void initializeView() {
        if (fragment == null) {
            fragment = new ArticleListFragment();
        }
        FragmentUtil.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.container);
    }

    @Override
    protected void loadDataFromServer() {

    }
}
