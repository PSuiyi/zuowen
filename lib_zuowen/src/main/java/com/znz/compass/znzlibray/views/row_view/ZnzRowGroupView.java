package com.znz.compass.znzlibray.views.row_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by panqiming on 16/8/4.
 */
public class ZnzRowGroupView extends LinearLayout {
    private final Context context;
    private List<ZnzRowDescription> descriptions;

    public ZnzRowGroupView(Context context) {
        super(context);
        this.context = context;
        initializeView();
    }

    public ZnzRowGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeView();
    }


    public ZnzRowGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initializeView();
    }

    private void initializeView() {
        setOrientation(VERTICAL);
    }

    public void setRowDataList(List<ZnzRowDescription> descriptions) {
        this.descriptions = descriptions;
        removeAllViews();
        if (descriptions == null || descriptions.size() <= 0) {
            setVisibility(GONE);
        }
        ZnzRowView view;
        for (int i = 0; i < descriptions.size(); i++) {
            if (!descriptions.get(i).isEnableHide()) {
                view = new ZnzRowView(context);
                view.setRowData(descriptions.get(i));
                addView(view);
            }
        }
    }


    /**
     * 刷新方法，还以比较差分继续优化
     *
     * @param descriptions
     */
    public void notifyDataChanged(List<ZnzRowDescription> descriptions) {
        removeAllViews();
        ZnzRowView view;
        for (int i = 0; i < descriptions.size(); i++) {
            if (!descriptions.get(i).isEnableHide()) {
                view = new ZnzRowView(context);
                view.setRowData(descriptions.get(i));
                addView(view);
            }
        }
    }
}
