package com.znz.compass.znzlibray.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.utils.ViewHolder;


public class ZnzRemind extends LinearLayout {
    private Context context;

    private LinearLayout llLoding;
    private LinearLayout llNoData;
    private LinearLayout llNoWifi;
    private TextView tvNoDataMessage;
    private LinearLayout llCommonRemind;

    public ZnzRemind(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public ZnzRemind(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public ZnzRemind(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = inflate(context, R.layout.view_znz_remind, this);

        llLoding = ViewHolder.init(view, R.id.llLoding);
        llNoData = ViewHolder.init(view, R.id.llNoData);
        llNoWifi = ViewHolder.init(view, R.id.llNoWifi);
        llCommonRemind = ViewHolder.init(view, R.id.llCommonRemind);
        tvNoDataMessage = ViewHolder.init(view, R.id.tvNoDataMessage);
    }


    /**
     * 显示没有数据提醒
     */
    public void showNoData() {
        llCommonRemind.setVisibility(View.VISIBLE);
        llLoding.setVisibility(View.GONE);
        llNoData.setVisibility(View.VISIBLE);
        tvNoDataMessage.setText(R.string.common_no_data);
        llNoWifi.setVisibility(View.GONE);
    }

    /**
     * 显示没有数据提醒
     */
    public void showNoData(String message) {
        llCommonRemind.setVisibility(View.VISIBLE);
        llLoding.setVisibility(View.GONE);
        llNoData.setVisibility(View.VISIBLE);
        tvNoDataMessage.setText(message);
        llNoWifi.setVisibility(View.GONE);
    }

    /**
     * 显示没有数据提醒,用在scrollview中
     */
    public void showNoDataWithHeight(String message, int minHeight) {
        llCommonRemind.setVisibility(View.VISIBLE);
        llLoding.setVisibility(View.GONE);
        llNoData.setVisibility(View.VISIBLE);
        tvNoDataMessage.setText(message);
        llNoData.setMinimumHeight(minHeight);
        llNoWifi.setVisibility(View.GONE);
    }

    /**
     * 显示没有数据提醒,用在scrollview中
     */
    public void showNoDataWithHeight(int minHeight) {
        llCommonRemind.setVisibility(View.VISIBLE);
        llLoding.setVisibility(View.GONE);
        llNoData.setVisibility(View.VISIBLE);
        llNoData.setMinimumHeight(minHeight);
        llNoWifi.setVisibility(View.GONE);
    }

    /**
     * 隐藏没有数据提醒
     */
    public void hideNoData() {
        llCommonRemind.setVisibility(View.GONE);
        llLoding.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        llNoWifi.setVisibility(View.GONE);
    }

    /**
     * 显示loding
     */
    public void showLoding() {
        llCommonRemind.setVisibility(View.VISIBLE);
        llLoding.setVisibility(View.VISIBLE);
        llNoData.setVisibility(View.GONE);
        llNoWifi.setVisibility(View.GONE);
    }

    /**
     * 隐藏loding
     */
    public void hideLoding() {
        llCommonRemind.setVisibility(View.GONE);
        llLoding.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        llNoWifi.setVisibility(View.GONE);
    }

    /**
     * 显示没有网络
     */
    public void showNoWifi() {
        llCommonRemind.setVisibility(View.VISIBLE);
        llLoding.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        llNoWifi.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏没有网络
     */
    public void hideNoWifi() {
        llCommonRemind.setVisibility(View.GONE);
        llLoding.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        llNoWifi.setVisibility(View.GONE);
    }

    /**
     * 设置网络点击事件
     *
     * @param onClickListener
     */
    public void setOnNoWifiClickLinstener(OnClickListener onClickListener) {
        llNoWifi.setOnClickListener(onClickListener);
    }
}
