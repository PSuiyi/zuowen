package com.znz.zuowen.ui.home.week;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/11/3 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleUploadFileFragment extends BaseAppFragment {

    @Bind(R.id.llUploadWord)
    LinearLayout llUploadWord;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    private String id;

    public static ArticleUploadImageFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
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

    @OnClick({R.id.llUploadWord, R.id.tvSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llUploadWord:
                gotoActivity(FileListAct.class);
                break;
            case R.id.tvSubmit:
                if (StringUtil.isBlank(ArticleUploadAct.teacher_id)) {
                    mDataManager.showToast("请选择批改老师");
                    return;
                }

                if (StringUtil.isBlank(ArticleUploadAct.title)) {
                    mDataManager.showToast("请输入作文题目");
                    return;
                }
                break;
        }
    }
}
