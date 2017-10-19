package com.znz.zuowen.ui.home.good;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.ArticleAdapter;
import com.znz.zuowen.base.BaseAppListActivity;
import com.znz.zuowen.bean.ArticleBean;
import com.znz.zuowen.bean.OptionBean;
import com.znz.zuowen.event.EventList;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.model.ArticleModel;
import com.znz.zuowen.utils.PopupWindowManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
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
    private String search_name;
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
        znzToolBar.getSerachEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search_name = s.toString();
                resetRefresh();
            }
        });

        znzToolBar.getSerachEditText().setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        znzToolBar.getSerachEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    resetRefresh();
                    //收起软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });
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
        if (!StringUtil.isBlank(search_name)) {
            params.put("search_name", search_name);
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
                    tvLeft.setText(values[0]);
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
                    tvRight.setText(values[1]);
                    resetRefresh();
                });
                break;
        }
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
            int position = dataList.indexOf(event.getBean());
            dataList.get(position).setCollect_count(((ArticleBean) event.getBean()).getCollect_count());
            adapter.notifyDataSetChanged();
        }
        if (event.getFlag() == EventTags.LIST_ARTICLE_LIKE) {
            int position = dataList.indexOf(event.getBean());
            dataList.get(position).setLike_count(((ArticleBean) event.getBean()).getLike_count());
            adapter.notifyDataSetChanged();
        }
    }
}
