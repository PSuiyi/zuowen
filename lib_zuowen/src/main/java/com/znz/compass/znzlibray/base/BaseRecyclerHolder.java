package com.znz.compass.znzlibray.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.imageloder.HttpImageView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * ZnzRecyclerHolder
 */
public abstract class BaseRecyclerHolder<T> extends RecyclerView.ViewHolder {

    protected T bean;
    protected List<T> dataListHolder;
    private SparseArray<View> mViews;
    private View mConvertView;
    protected Context mContext;

    public BaseRecyclerHolder(Context context, View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        mConvertView = itemView;
        mContext = context;
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(v -> onItemClick(v, getAdapterPosition()));
    }

    public BaseRecyclerHolder(Context context, View itemView, List<T> dataList) {
        super(itemView);
        mViews = new SparseArray<>();
        mConvertView = itemView;
        mContext = context;
        this.dataListHolder = dataList;
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(v -> onItemClick(v, getAdapterPosition()));
    }


    public abstract void onBindViewHolder(int position);

    public abstract void onItemClick(View view, int position);

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 绑定控件
     *
     * @param view
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T bindViewById(View view, @IdRes int resId) {
        View childView = view.findViewById(resId);
        return (T) childView;
    }


    /****跳转页面方法*****/

    /**
     * activity跳转
     *
     * @param cls
     */
    public void gotoActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        mContext.startActivity(intent);
    }

    /**
     * activity跳转
     *
     * @param cls
     * @param bundle
     */
    public void gotoActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }


    /****以下为辅助方法*****/

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public BaseRecyclerHolder setRecyclerText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public BaseRecyclerHolder setRecyclerImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public BaseRecyclerHolder setRecyclerHttpImage(int viewId, String url) {
        HttpImageView view = getView(viewId);
        view.loadHttpImage(url);
        return this;
    }

    public BaseRecyclerHolder setRecyclerImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public BaseRecyclerHolder setRecyclerBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public BaseRecyclerHolder setRecyclerBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public BaseRecyclerHolder setRecyclerTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public BaseRecyclerHolder setRecyclerVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public BaseRecyclerHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public BaseRecyclerHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public BaseRecyclerHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    /**
     * 关于事件的
     */
    public BaseRecyclerHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseRecyclerHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public BaseRecyclerHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }
}
