package com.znz.compass.znzlibray.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.common.DataManager;

import java.util.List;

/**
 * Created by panqiming on 2016/10/31.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {
    protected Context context;
    protected List<T> dataList;
    protected DataManager mDataManager;

    protected boolean isLoadMoreFooterShown; //刷新相关
    protected boolean isNoData;//没有数据
    protected View headerView; //头部
    protected static final int VIEW_TYPE_LOAD_MORE_FOOTER = 0x100;
    protected static final int VIEW_TYPE_LOAD_NO_DATA = 0x101;
    protected static final int VIEW_TYPE_HEADER = 0x102;

    public BaseRecyclerAdapter(Context context, List<T> dataList) {
        this.context = context;
        this.dataList = dataList;
        mDataManager = DataManager.getInstance(context);
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOAD_MORE_FOOTER) {
            return onCreateLoadMoreFooterViewHolder(parent);
        }
        if (viewType == VIEW_TYPE_HEADER) {
            return onCreateHeaderViewHolder();
        }
        if (viewType == VIEW_TYPE_LOAD_NO_DATA) {
            return onCreateNoDataViewHolder(parent);
        } else {
            return onZnzCreateViewHolder(parent, viewType);
        }
    }

    protected BaseRecyclerHolder onCreateLoadMoreFooterViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_pull_to_refresh_footer, parent, false);
        return new OtherViewHolder(context, view);
    }

    protected BaseRecyclerHolder onCreateNoDataViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_pull_to_refresh_no_data, parent, false);
        return new OtherViewHolder(context, view);
    }

    protected BaseRecyclerHolder onCreateHeaderViewHolder() {
        return new OtherViewHolder(context, headerView);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        if (onItemClickLitener != null && !isNoData && !isCustomeHeader(position)) {
            holder.itemView.setOnClickListener(v -> onItemClickLitener.onItemClick(holder.itemView, position));
        }
        holder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        if (hasCustomeHeader()) {
            if (isNoData) {
                return 2;
            } else {
                return dataList == null ? 1 : dataList.size() + 1;
            }
        } else {
            if (isNoData) {
                return 1;
            } else {
                return dataList == null ? 0 : dataList.size();
            }
        }
    }

    public boolean isSectionHeader(int position) {
        return false;
    }

    public boolean hasCustomeHeader() {
        return headerView != null;
    }

    public boolean isCustomeHeader(int postion) {
        return hasCustomeHeader() && postion == 0;
    }

    /**
     * 添加头部方法
     *
     * @param headerView
     */
    public void addHeaderView(View headerView) {
        this.headerView = headerView;
        headerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        notifyItemInserted(0);
    }


    /**
     * 获取对应position的对象
     *
     * @param position
     * @return
     */
    protected T getItemObject(int position) {
        if (dataList.isEmpty()) {
            return null;
        }
        if (hasCustomeHeader()) {
            return dataList.get(position - 1);
        } else {
            return dataList.get(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isCustomeHeader(position)) {
            return VIEW_TYPE_HEADER;
        }
        if (isLoadMoreFooterShown && position == getItemCount() - 1) {
            return VIEW_TYPE_LOAD_MORE_FOOTER;
        }
        if (isNoData && dataList.isEmpty()) {
            return VIEW_TYPE_LOAD_NO_DATA;
        }
        return getDataViewType(position);
    }

    public boolean isNoData() {
        return isNoData;
    }

    public void setNoData(boolean noData) {
        isNoData = noData;
        notifyDataSetChanged();
    }

    protected int getDataViewType(int position) {
        return 0;
    }

    public void onLoadMoreStateChanged(boolean isShown) {
        this.isLoadMoreFooterShown = isShown;
        if (isShown) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }

    public boolean isLoadMoreFooter(int position) {
        return isLoadMoreFooterShown && position == getItemCount() - 1;
    }

    private class OtherViewHolder extends BaseRecyclerHolder {

        public OtherViewHolder(Context context, View itemView) {
            super(context, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {

        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }

    //点击事件相关
    public abstract BaseRecyclerHolder onZnzCreateViewHolder(ViewGroup parent, int viewType);

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener onItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    /**
     * item内部点击事件
     */
    protected OnItemInnerClickListener onItemInnerClickListener;

    public void setOnItemInnerClickListener(OnItemInnerClickListener onItemInnerClickListener) {
        this.onItemInnerClickListener = onItemInnerClickListener;
    }

    public interface OnItemInnerClickListener {
        void onInnerClick(int position, String type);
    }
}