package com.znz.zuowen.ui.home.article;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.gallery.inter.IPhotoSelectCallback;
import com.znz.zuowen.R;
import com.znz.zuowen.base.BaseAppActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date： 2017/10/9 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleUploadAct extends BaseAppActivity {
    @Bind(R.id.llAdd)
    LinearLayout llAdd;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_article_upload, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("上传作文");
    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void loadDataFromServer() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.llAdd, R.id.tvSubmit, R.id.llSelect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llAdd:
                mDataManager.openPhotoSelectSingle(activity, new IPhotoSelectCallback() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(List<String> photoList) {
                        if (!photoList.isEmpty()) {
                        }
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onError() {

                    }
                }, false);
                break;
            case R.id.tvSubmit:
                finish();
                break;
            case R.id.llSelect:
//                PopupWindowManager.getInstance(activity).showSelectTeacher(view);
                break;
        }
    }
}
