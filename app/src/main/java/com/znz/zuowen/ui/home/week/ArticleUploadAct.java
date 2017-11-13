package com.znz.zuowen.ui.home.week;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znz.compass.znzlibray.network.znzhttp.ZnzHttpListener;
import com.znz.compass.znzlibray.views.EditTextWithDel;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ViewPageAdapter;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.bean.OptionBean;
import com.znz.zuowen.bean.TeacherBean;
import com.znz.zuowen.model.ArticleModel;
import com.znz.zuowen.utils.PopupWindowManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Date： 2017/10/9 2017
 * User： PSuiyi
 * Description：
 */

public class ArticleUploadAct extends BaseAppActivity<ArticleModel> {
    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.tvTeacher)
    TextView tvTeacher;
    @Bind(R.id.llSelect)
    LinearLayout llSelect;
    @Bind(R.id.etTitle)
    EditTextWithDel etTitle;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvTeacherZhiding)
    TextView tvTeacherZhiding;
    @Bind(R.id.commonTabLayout)
    TabLayout commonTabLayout;
    @Bind(R.id.commonViewPager)
    ViewPager commonViewPager;

    private List<OptionBean> teacherList = new ArrayList<>();
    private String id;
    public static String teacher_id;
    public static String title;
    private List<TeacherBean> beanList;

    private List<String> tabNames = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_article_upload, 1};
    }

    @Override
    protected void initializeVariate() {
        mModel = new ArticleModel(activity, this);
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
        if (getIntent().hasExtra("bean")) {
            beanList = (List<TeacherBean>) getIntent().getSerializableExtra("bean");
            for (TeacherBean teacherBean : beanList) {
                OptionBean bean = new OptionBean();
                bean.setTeacher_name(teacherBean.getReal_name());
                bean.setId(teacherBean.getId());
                teacherList.add(bean);
            }
        }
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("上传作文");
    }

    @Override
    protected void initializeView() {
        if (!beanList.isEmpty()) {
            tvTitle.setText("指定老师");
        }

        tabNames.add("上传图片");
        tabNames.add("上传文档");

        fragmentList.add(ArticleUploadImageFragment.newInstance("1", id));
        fragmentList.add(ArticleUploadFileFragment.newInstance("1", id));

        commonViewPager.setAdapter(new ViewPageAdapter(getSupportFragmentManager(), tabNames, fragmentList));
        commonTabLayout.setupWithViewPager(commonViewPager);
        commonViewPager.setOffscreenPageLimit(fragmentList.size());


        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                title = s.toString();
            }
        });
    }

    @Override
    protected void loadDataFromServer() {
        if (beanList.isEmpty()) {
            Map<String, String> params2 = new HashMap<>();
            mModel.requestTeacherList(params2, new ZnzHttpListener() {
                @Override
                public void onSuccess(JSONObject responseOriginal) {
                    super.onSuccess(responseOriginal);
                    teacherList.clear();
                    teacherList.addAll(JSONArray.parseArray(responseOriginal.getString("data"), OptionBean.class));
                    if (!teacherList.isEmpty()) {
                        teacherList.get(0).setChecked(true);
                    }
                }

                @Override
                public void onFail(String error) {
                    super.onFail(error);
                }
            });
        }
    }

    @OnClick({R.id.llSelect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llSelect:
                PopupWindowManager.getInstance(activity).showSelectTeacher(view, teacherList, (type, values) -> {
                    tvTeacher.setText(values[1]);
                    teacher_id = values[0];
                });
                break;
        }
    }
}
