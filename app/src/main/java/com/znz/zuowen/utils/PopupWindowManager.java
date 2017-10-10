package com.znz.zuowen.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.znz.compass.znzlibray.base.BaseZnzBean;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.PopTeactherAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.znz.compass.znzlibray.utils.ViewHolder.init;


public class PopupWindowManager {
    private static PopupWindowManager instance;
    private Context mContext;
    private DataManager mDataManager;
    private PopupWindow popupWindow;

    public PopupWindowManager(Context context) {
        mContext = context.getApplicationContext();
        mDataManager = DataManager.getInstance(context);
    }

    public static synchronized PopupWindowManager getInstance(Context mContext) {
        if (instance == null) {
            instance = new PopupWindowManager(mContext);
        }
        return instance;
    }

    public boolean isPopupExist() {
        if (popupWindow != null) {
            return popupWindow.isShowing();
        }
        return false;
    }

    public void hidePopupWindow() {
        try {
            if (null != popupWindow && popupWindow.isShowing()) {
                popupWindow.dismiss();
                popupWindow = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View initPopupWindow(int resId) {
        popupWindow = new PopupWindow(mContext);
        View view = LayoutInflater.from(mContext).inflate(resId, null);
        popupWindow.setContentView(view);
        ColorDrawable dw = new ColorDrawable(mDataManager.getColor(R.color.trans));
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setFocusable(true);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        // 重写onKeyListener,返回只关闭pop
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                hidePopupWindow();
                return true;
            }
            return false;
        });
        return view;
    }

    public View initPopupWindowNoBack(Activity mActivity, int resId) {
        popupWindow = new PopupWindow(mActivity);
        View view = LayoutInflater.from(mActivity).inflate(resId, null);
        popupWindow.setContentView(view);
        ColorDrawable dw = new ColorDrawable(mDataManager.getColor(R.color.trans));
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        return view;
    }


    /**
     * 选择老师弹框
     *
     * @param parent
     */
    public void showSelectTeacher(View parent) {
        hidePopupWindow();
        View view = initPopupWindow(R.layout.popup_select_teacher);
        init(view, R.id.darkView).setOnClickListener(v -> hidePopupWindow());
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        RecyclerView rvTeacher = init(view, R.id.rvTeacher);
        List dataList = new ArrayList();
        dataList.add(new BaseZnzBean());
        dataList.add(new BaseZnzBean());
        dataList.add(new BaseZnzBean());
        dataList.add(new BaseZnzBean());
        dataList.add(new BaseZnzBean());
        PopTeactherAdapter adapter = new PopTeactherAdapter(dataList);
        rvTeacher.setLayoutManager(new LinearLayoutManager(mContext));
        rvTeacher.setAdapter(adapter);
        popupWindow.showAsDropDown(parent);
    }

    /**
     * 选择老师弹框
     *
     * @param parent
     */
    public void showVerifyCode(View parent) {
        hidePopupWindow();
        View view = initPopupWindow(R.layout.popup_verify_code);
        init(view, R.id.llParent).setOnClickListener(v -> hidePopupWindow());
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }
}
