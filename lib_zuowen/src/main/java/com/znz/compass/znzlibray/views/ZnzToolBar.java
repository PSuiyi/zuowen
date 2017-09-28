package com.znz.compass.znzlibray.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.utils.ViewHolder;


public class ZnzToolBar extends LinearLayout {
    private Context context;

    //normal about
    protected Toolbar toolbar;
    protected LinearLayout llNavLeft;
    protected LinearLayout llNavRight;
    protected LinearLayout llIconRight;
    protected LinearLayout llIconRight2;
    protected LinearLayout llMulti;
    protected ImageView ivNavLeft;
    protected ImageView ivNavRight;
    protected ImageView ivNavRight2;
    protected ImageView ivMulti;
    protected TextView tvNavLeft;
    protected TextView tvNavRight;
    protected TextView tvMulti;
    protected TextView navTitle;

    //search about
    protected Toolbar toolbarSearch;
    protected TextView tvSearch;
    protected EditTextWithDel etSerach;
    protected LinearLayout llSearch;
    protected LinearLayout llSearchRight;
    protected LinearLayout llSearchRight2;
    protected ImageView ivSearchRight;
    protected ImageView ivSearchRight2;


    private LinearLayout llNavRuixi;
    private LinearLayout llNavSearch;
    private LinearLayout llNavCart;
    private TextView tvCartNum;


    protected boolean isModeWhite = false;
    protected int BACK_MODE = 1;//1、箭头 2、返回文字 3、箭头加文字
    protected int TOOL_MODE = 1;//1、普通 2、搜索 3、返回搜索
    private View view;

    public ZnzToolBar(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public ZnzToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public ZnzToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        view = inflate(context, R.layout.view_znz_toolbar, this);
        toolbar = ViewHolder.init(view, R.id.toolbar);
        toolbarSearch = ViewHolder.init(view, R.id.toolbarSearch);
    }

    /**
     * 设置导航栏样式
     *
     * @param mode 1、普通 2、搜索 3、返回搜索 4、普通透明 5、睿玺特殊
     */
    public void setToolMode(int mode) {
        switch (mode) {
            case 1:
                toolbar.setBackgroundColor(getResources().getColor(R.color.nav_backgroud));
                setModeNormal();
                switch (BACK_MODE) {
                    case 1:
                        if (isModeWhite) {
                            toolbar.setNavigationIcon(R.drawable.topback);
                        } else {
                            toolbar.setNavigationIcon(R.drawable.topback_white);
                        }

                        tvNavLeft.setVisibility(GONE);
                        break;
                    case 2:
                        tvNavLeft.setVisibility(View.VISIBLE);
                        break;
                }

                if (isModeWhite) {
                    tvNavRight.setTextColor(getResources().getColor(R.color.text_color));
                    navTitle.setTextColor(getResources().getColor(R.color.text_color));
                } else {
                    tvNavRight.setTextColor(getResources().getColor(R.color.white));
                    navTitle.setTextColor(getResources().getColor(R.color.white));
                }
                break;
            case 2:
                toolbar.setBackgroundColor(getResources().getColor(R.color.nav_backgroud));
                TOOL_MODE = 2;
                toolbar.setVisibility(View.GONE);
                toolbarSearch.setVisibility(View.VISIBLE);
//                if (isModeWhite) {
//                    toolbarSearch.setNavigationIcon(R.drawable.topback);
//                } else {
//                    toolbarSearch.setNavigationIcon(R.drawable.topback_white);
//                }
                llSearch = ViewHolder.init(view, R.id.llSearch);
                etSerach = ViewHolder.init(view, R.id.etSerach);
                tvSearch = ViewHolder.init(view, R.id.tvSearch);
                llSearchRight = ViewHolder.init(view, R.id.llSearchRight);
                llSearchRight2 = ViewHolder.init(view, R.id.llSearchRight2);
                ivSearchRight = ViewHolder.init(view, R.id.ivSearchRight);
                ivSearchRight2 = ViewHolder.init(view, R.id.ivSearchRight2);
                break;

            case 3:
                toolbar.setBackgroundColor(getResources().getColor(R.color.nav_backgroud));
                TOOL_MODE = 3;
                toolbar.setVisibility(View.GONE);
                toolbarSearch.setVisibility(View.VISIBLE);
                if (isModeWhite) {
                    toolbarSearch.setNavigationIcon(R.drawable.topback);
                } else {
                    toolbarSearch.setNavigationIcon(R.drawable.topback_white);
                }
                llSearch = ViewHolder.init(view, R.id.llSearch);
                etSerach = ViewHolder.init(view, R.id.etSerach);
                tvSearch = ViewHolder.init(view, R.id.tvSearch);
                break;
            case 4:
                setModeNormal();
                toolbar.setNavigationIcon(R.drawable.topback_white);
                tvNavLeft.setVisibility(GONE);
                tvNavRight.setTextColor(getResources().getColor(R.color.white));
                navTitle.setTextColor(getResources().getColor(R.color.white));
                break;
            case 5:
                setModeNormal();
                toolbar.setBackgroundColor(getResources().getColor(R.color.nav_backgroud));
                toolbar.setNavigationIcon(R.drawable.topback_white);
                navTitle.setTextColor(getResources().getColor(R.color.white));

                llNavRight.setVisibility(GONE);
                llNavRuixi.setVisibility(VISIBLE);
                break;
        }
    }

    /**
     * 设置普通模式
     */
    private void setModeNormal() {
        TOOL_MODE = 1;
        toolbar.setVisibility(View.VISIBLE);
        toolbarSearch.setVisibility(View.GONE);
        llNavLeft = ViewHolder.init(view, R.id.llNavLeft);
        llNavRight = ViewHolder.init(view, R.id.llNavRight);
        ivNavLeft = ViewHolder.init(view, R.id.ivNavLeft);
        ivNavRight = ViewHolder.init(view, R.id.ivNavRight);
        ivNavRight2 = ViewHolder.init(view, R.id.ivNavRight2);
        ivMulti = ViewHolder.init(view, R.id.ivMulti);
        tvNavRight = ViewHolder.init(view, R.id.tvNavRight);
        tvNavLeft = ViewHolder.init(view, R.id.tvNavLeft);
        tvMulti = ViewHolder.init(view, R.id.tvMulti);
        navTitle = ViewHolder.init(view, R.id.navTitle);
        llIconRight = ViewHolder.init(view, R.id.llIconRight);
        llIconRight2 = ViewHolder.init(view, R.id.llIconRight2);
        llMulti = ViewHolder.init(view, R.id.llMulti);

        llNavRuixi = ViewHolder.init(view, R.id.llNavRuixi);
        llNavSearch = ViewHolder.init(view, R.id.llNavSearch);
        llNavCart = ViewHolder.init(view, R.id.llNavCart);
        tvCartNum = ViewHolder.init(view, R.id.tvCartNum);
    }


    public Toolbar getToolbar() {
        switch (TOOL_MODE) {
            case 1:
                return toolbar;
            case 2:
            case 3:
                return toolbarSearch;
        }
        return null;
    }


    /**
     * 设置标题名称
     */
    public void setTitleName(String name) {
        navTitle.setText(name);
    }


    /**
     * hide right all
     */
    public void setNavRightGone() {
        llNavRight.setVisibility(GONE);
    }

    /**
     * set nav right img
     */
    public void setNavRightImg(int resourceId) {
        llNavRight.setVisibility(VISIBLE);
        llIconRight.setVisibility(VISIBLE);
        tvNavRight.setVisibility(GONE);
        ivNavRight.setVisibility(VISIBLE);
        ivNavRight.setImageResource(resourceId);
    }

    /**
     * set nav right img2
     */
    public void setNavRightImg2(int resourceId) {
        llNavRight.setVisibility(VISIBLE);
        llIconRight2.setVisibility(VISIBLE);
        tvNavRight.setVisibility(GONE);
        ivNavRight2.setVisibility(VISIBLE);
        ivNavRight2.setImageResource(resourceId);
    }

    /**
     * set nav right txt
     */
    public void setNavRightText(String text) {
        llNavRight.setVisibility(VISIBLE);
        tvNavRight.setVisibility(VISIBLE);
        ivNavRight.setVisibility(GONE);
        llIconRight.setVisibility(GONE);
        tvNavRight.setText(text);
    }

    /**
     * set nav right txt
     */
    public void setNavRight(String text, int resourceId) {
        llNavRight.setVisibility(VISIBLE);
        tvNavRight.setVisibility(GONE);
        ivNavRight.setVisibility(GONE);
        llIconRight.setVisibility(GONE);
        llMulti.setVisibility(VISIBLE);
        tvMulti.setText(text);
        ivMulti.setImageResource(resourceId);
    }

    /**
     * set left text and img
     *
     * @param text
     * @param drawable
     */
    public void setNavLeft(String text, Drawable drawable) {
        llNavLeft.setVisibility(VISIBLE);
        tvNavLeft.setVisibility(VISIBLE);
        tvNavLeft.setText(text);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvNavLeft.setCompoundDrawables(drawable, null, null, null);
        tvNavLeft.setCompoundDrawablePadding(10);
    }

    /**
     * 设置左侧文字
     *
     * @param text
     */
    public void setNavLeft(String text) {
        llNavLeft.setVisibility(VISIBLE);
        tvNavLeft.setVisibility(VISIBLE);
        tvNavLeft.setText(text);
        if (isModeWhite) {
            tvNavLeft.setTextColor(ContextCompat.getColor(context, R.color.text_color));
        } else {
            tvNavLeft.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    public void setNavLeft(int resId) {
        llNavLeft.setVisibility(VISIBLE);
        tvNavLeft.setVisibility(GONE);
        ivNavLeft.setVisibility(VISIBLE);
        ivNavLeft.setImageResource(resId);
    }

    /**
     * 设置右边点击事件
     *
     * @param onNavRightClickListener
     */
    public void setOnNavRightClickListener(OnClickListener onNavRightClickListener) {
        llNavRight.setOnClickListener(onNavRightClickListener);
    }

    /**
     * 设置右边图片1点击事件
     *
     * @param onNavRightClickListener
     */
    public void setOnIconRightClickListener(OnClickListener onNavRightClickListener) {
        llIconRight.setOnClickListener(onNavRightClickListener);
    }

    /**
     * 设置右边图片2点击事件
     *
     * @param onNavRightClickListener
     */
    public void setOnIconRightClickListener2(OnClickListener onNavRightClickListener) {
        llIconRight2.setOnClickListener(onNavRightClickListener);
    }

    /**
     * 设置左边点击事件
     *
     * @param onNavRightClickListener
     */
    public void setOnNavLeftClickListener(OnClickListener onNavRightClickListener) {
        llNavLeft.setOnClickListener(onNavRightClickListener);
    }


    //-------------------Search about----------------------

    /**
     * 获取控件
     *
     * @return
     */
    public EditTextWithDel getSerachEditText() {
        return etSerach;
    }


    /**
     * 设置输入框是否可编辑，默认不可编辑
     *
     * @param enable
     */
    public void setEnableEdit(boolean enable) {
        if (enable) {
            etSerach.setVisibility(VISIBLE);
            tvSearch.setVisibility(GONE);
        } else {
            etSerach.setVisibility(GONE);
            tvSearch.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置搜索框提示信息
     *
     * @param hint
     */
    public void setSearchHint(String hint) {
        etSerach.setHint(hint);
        tvSearch.setHint(hint);
    }

    /**
     * 设置图片
     *
     * @param resId
     */
    public void setSearchRightImage(@DrawableRes int resId) {
        ivSearchRight.setImageResource(resId);
        llSearchRight.setVisibility(VISIBLE);
    }

    public void setSearchRightImage2(@DrawableRes int resId) {
        ivSearchRight2.setImageResource(resId);
        llSearchRight2.setVisibility(VISIBLE);
    }


    /**
     * 设置搜索框点击事件
     *
     * @param onNavSearchClickListener
     */
    public void setOnSearchClickListener(OnClickListener onNavSearchClickListener) {
        llSearch.setOnClickListener(onNavSearchClickListener);
    }

    public void setOnSearchRightClickListener(OnClickListener onNavSearchClickListener) {
        llSearchRight.setOnClickListener(onNavSearchClickListener);
    }

    public void setOnSearchRightClickListener2(OnClickListener onNavSearchClickListener) {
        llSearchRight2.setOnClickListener(onNavSearchClickListener);
    }


    //-------------------ruixi----------------------

    /**
     * 设置搜索点击事件
     *
     * @param onNavSearchClickListener
     */
    public void setOnRuixiSearchClickListener(OnClickListener onNavSearchClickListener) {
        llNavSearch.setOnClickListener(onNavSearchClickListener);
    }

    /**
     * 设置购物袋点击事件
     *
     * @param onRuixiCartClickListener
     */
    public void setOnRuixiCartClickListener(OnClickListener onRuixiCartClickListener) {
        llNavCart.setOnClickListener(onRuixiCartClickListener);
    }

    /**
     * 设置购物车数量
     *
     * @param num
     */
    public void setRuixiCatNum(String num) {
        tvCartNum.setText(num);
    }
}
