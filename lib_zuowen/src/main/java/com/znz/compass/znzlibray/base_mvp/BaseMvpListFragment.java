package com.znz.compass.znzlibray.base_mvp;

import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.base.BaseRecyclerAdapter;
import com.znz.compass.znzlibray.base.BaseZnzBean;
import com.znz.compass.znzlibray.base_znz.BaseZnzFragment;
import com.znz.compass.znzlibray.base_znz.IModel;
import com.znz.compass.znzlibray.utils.ViewHolder;
import com.znz.compass.znzlibray.views.refresh.recycler.PullRecycler;
import com.znz.compass.znzlibray.views.refresh.recycler.layoutmanager.ILayoutManager;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by panqiming on 2016/10/31.
 */

public abstract class BaseMvpListFragment<M extends IModel, T extends BaseZnzBean> extends BaseZnzFragment implements PullRecycler.OnRecyclerRefreshListener {
    protected ArrayList<T> dataList = new ArrayList<>();
    protected int currentPageIndex = 1; // 当前页码
    protected PullRecycler rvRefresh;
    protected BaseRecyclerAdapter<T> adapter;
    protected M mModel;
    protected boolean isCustomeParams;

    @Override
    protected void initializeAdvance() {
        initializeMVP();
        initializeVariate();
        initializeRefreshView();
        initializeNavigation();
        initializeView();
        loadDataFromServer();
    }

    protected void initializeRefreshView() {
        //刷新控件初始化
        rvRefresh = ViewHolder.init(rootView, R.id.rvRefresh);
        rvRefresh.setLayoutManager(getLayoutManager());
        rvRefresh.setOnRefreshListener(this);
        rvRefresh.setRefreshing();
    }

    /**
     * 使用自定义参数
     */
    protected void setCustomeParamsMode() {
        isCustomeParams = true;
    }

    /**
     * 加载layout，fragment中需要返回int数组，int[0]是控件id，int[1]控制是否使用znzToolbar
     *
     * @return
     */
    protected abstract int[] getLayoutResource();

    /**
     * 初始化MVP相关
     */
    protected abstract void initializeMVP();

    /**
     * 初始化数据
     */
    protected abstract void initializeVariate();

    /**
     * 导航栏初始化
     */
    protected abstract void initializeNavigation();

    /**
     * recyclerview加载方式
     *
     * @return
     */
    protected abstract ILayoutManager getLayoutManager();

    /**
     * 视图初始化
     */
    protected abstract void initializeView();

    /**
     * 调用接口
     */
    protected abstract void loadDataFromServer();


    /**
     * 自定义请求
     *
     * @return
     */
    protected Observable<ResponseBody> requestCustomeRefreshObservable() {
        return null;
    }


    /**
     * 接口请求成功
     *
     * @param response 返回值str
     */
    protected abstract void onRefreshSuccess(String response);

    /**
     * 接口请求失败
     *
     * @param error
     */
    protected abstract void onRefreshFail(String error);


    /**
     * 手动刷新
     */
    protected void resetRefresh() {
        currentPageIndex = 1;
        rvRefresh.setRefreshing();
    }

    @Override
    public void onRefresh(int action) {
        if (isCustomeParams) {
            customeRefreshRequest(action);
        } else {
            defaultRefreshRequest(action);
        }
    }

    private void defaultRefreshRequest(final int action) {
    }

    private void customeRefreshRequest(final int action) {
        if (mModel == null) {
            setTempDataList();
            return;
        }
        if (requestCustomeRefreshObservable() == null) {
            setTempDataList();
        } else {
            if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
                currentPageIndex = 1;
            }

            requestCustomeRefreshObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            rvRefresh.onRefreshCompleted();
                            onRefreshFail(e.toString());
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                //写在这数据是加载完才删除
                                if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
                                    dataList.clear();
                                }

                                onRefreshSuccess(responseBody.string());

                                //页码自增
                                currentPageIndex++;

                                if (dataList.isEmpty()) {
                                    rvRefresh.enableLoadMore(false);
                                    adapter.setNoData(true);
                                } else {
                                    adapter.setNoData(false);

                                }
                                rvRefresh.onRefreshCompleted();

                                if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
                                    rvRefresh.getRecyclerView().smoothScrollToPosition(0);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    /**
     * 临时数据
     */
    private void setTempDataList() {
//        List<BaseZnzBean> tempList = new ArrayList<>();
//        tempList.add(new BaseZnzBean());
//        tempList.add(new BaseZnzBean());
//        tempList.add(new BaseZnzBean());
//        tempList.add(new BaseZnzBean());
//        tempList.add(new BaseZnzBean());
//        tempList.add(new BaseZnzBean());
//        tempList.add(new BaseZnzBean());
//        tempList.add(new BaseZnzBean());
//
//        dataList.clear();
//        dataList.addAll((Collection<? extends T>) tempList);
//        rvRefresh.onRefreshCompleted();

        rvRefresh.enableLoadMore(false);
        rvRefresh.onRefreshCompleted();
        adapter.setNoData(true);
        adapter.notifyDataSetChanged();
    }
}
