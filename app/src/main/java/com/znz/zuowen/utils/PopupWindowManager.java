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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.znz.compass.znzlibray.base.BaseZnzBean;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.OptionAdapter;
import com.znz.zuowen.bean.OptionBean;

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
        OptionAdapter adapter = new OptionAdapter(dataList, "老师");
        rvTeacher.setLayoutManager(new LinearLayoutManager(mContext));
        rvTeacher.setAdapter(adapter);
        popupWindow.showAsDropDown(parent);
    }

    public void showOptionLeft(View parent, List<OptionBean> dataList, OnPopupWindowClickListener onPopupWindowClickListener) {
        hidePopupWindow();
        View view = initPopupWindow(R.layout.popup_option1);
        init(view, R.id.darkView).setOnClickListener(v -> hidePopupWindow());
        init(view, R.id.darkView2).setOnClickListener(v -> hidePopupWindow());
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        RecyclerView rvOption = init(view, R.id.rvOption);
        OptionAdapter adapter = new OptionAdapter(dataList, "体裁");
        adapter.setOnItemChildClickListener((adapter1, view1, position) -> {
            for (OptionBean optionBean : dataList) {
                optionBean.setChecked(false);
            }
            dataList.get(position).setChecked(true);

            if (onPopupWindowClickListener != null) {
                onPopupWindowClickListener.onPopupWindowClick("体裁", new String[]{dataList.get(position).getStyle_type()});
            }

            adapter.notifyDataSetChanged();
            hidePopupWindow();
        });
        rvOption.setLayoutManager(new LinearLayoutManager(mContext));
        rvOption.setAdapter(adapter);
        popupWindow.showAsDropDown(parent);
    }

    public void showOptionRight(View parent, List<OptionBean> dataList, OnPopupWindowClickListener onPopupWindowClickListener) {
        hidePopupWindow();
        View view = initPopupWindow(R.layout.popup_option2);
        init(view, R.id.darkView).setOnClickListener(v -> hidePopupWindow());
        init(view, R.id.darkView2).setOnClickListener(v -> hidePopupWindow());
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        RecyclerView rvOption = init(view, R.id.rvOption);
        OptionAdapter adapter = new OptionAdapter(dataList, "字数");
        adapter.setOnItemChildClickListener((adapter1, view1, position) -> {
            for (OptionBean optionBean : dataList) {
                optionBean.setChecked(false);
            }
            dataList.get(position).setChecked(true);

            if (onPopupWindowClickListener != null) {
                onPopupWindowClickListener.onPopupWindowClick("字数", new String[]{position + ""});
            }

            adapter.notifyDataSetChanged();
            hidePopupWindow();
        });
        rvOption.setLayoutManager(new LinearLayoutManager(mContext));
        rvOption.setAdapter(adapter);
        popupWindow.showAsDropDown(parent);
    }

    /**
     * 验证码弹框
     *
     * @param parent
     */
    public void showVerifyCode(View parent, String url, OnPopupWindowClickListener onPopupWindowClickListener) {
        hidePopupWindow();
        View view = initPopupWindow(R.layout.popup_verify_code);

        ImageView ivImage = init(view, R.id.ivImage);
        Glide.with(mContext)
                .load(url)
                .skipMemoryCache(true)
                .centerCrop()
                .signature(new StringSignature((Math.random() * (100000000 - 1 + 1)) + ""))
                .into(ivImage);

        init(view, R.id.tvCancel).setOnClickListener(v -> {
            hidePopupWindow();
        });

        EditText etCode = init(view, R.id.etCode);
        init(view, R.id.tvSubmit).setOnClickListener(v -> {
            if (onPopupWindowClickListener != null) {
                if (StringUtil.isBlank(mDataManager.getValueFromView(etCode))) {
                    mDataManager.showToast("请输入图中的验证码");
                    return;
                }
                onPopupWindowClickListener.onPopupWindowClick("", new String[]{mDataManager.getValueFromView(etCode)});
            }
            hidePopupWindow();
        });

        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    public interface OnPopupWindowClickListener {
        void onPopupWindowClick(String type, String[] values);
    }
}
