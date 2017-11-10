package com.znz.zuowen.ui.home.week;

import android.os.Bundle;

import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppFragment;

/**
 * Date： 2017/11/3 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleUploadFileFragment extends BaseAppFragment {

    private String id;

    public static ArticleUploadImageFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id",id);
        ArticleUploadImageFragment fragment = new ArticleUploadImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.frag_upload_file};
    }

    @Override
    protected void initializeVariate() {
        if (getArguments() != null) {
            id = getArguments().getString("id");
        }
    }

    @Override
    protected void initializeNavigation() {

    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void loadDataFromServer() {

    }
}
