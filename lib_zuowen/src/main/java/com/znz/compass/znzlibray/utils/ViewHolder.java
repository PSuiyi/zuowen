package com.znz.compass.znzlibray.utils;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;

/**
 * viewholder工具类
 *
 * @author zyn
 */
public class ViewHolder {
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    /**
     * activity初始化控件
     *
     * @param activity
     * @param resId
     * @param <T>
     * @return
     */
    public static <T extends View> T init(Activity activity, int resId) {
        View childView = activity.findViewById(resId);
        return (T) childView;
    }

    /**
     * fragment初始化控件
     *
     * @param view
     * @param resId
     * @param <T>
     * @return
     */
    public static <T extends View> T init(View view, int resId) {
        View childView = view.findViewById(resId);
        return (T) childView;
    }
}
