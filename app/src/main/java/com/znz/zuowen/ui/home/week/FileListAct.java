package com.znz.zuowen.ui.home.week;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.FileAdapter;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.bean.FileBean;
import com.znz.zuowen.event.EventGoto;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.utils.StaticValues;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date： 2017/11/13 2017
 * User： PSuiyi
 * Description：
 */

public class FileListAct extends BaseAppActivity {

    @Bind(R.id.znzToolBar)
    ZnzToolBar znzToolBar;
    @Bind(R.id.znzRemind)
    ZnzRemind znzRemind;
    @Bind(R.id.llNetworkStatus)
    LinearLayout llNetworkStatus;
    @Bind(R.id.rvFile)
    RecyclerView rvFile;
    @Bind(R.id.tvNoData)
    TextView tvNoData;

    private String homePath = Environment.getExternalStorageDirectory().getPath();
    private File currentFile;
    private FileAdapter adapter;
    private List<FileBean> fileBeanList = new ArrayList<>();

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.act_file_list, 1};
    }

    @Override
    protected void initializeVariate() {

    }

    @Override
    protected void initializeNavigation() {
        setTitleName("文件列表");
        setSwipeBackEnable(false);
    }

    @Override
    protected void initializeView() {
        adapter = new FileAdapter(fileBeanList);
        rvFile.setLayoutManager(new LinearLayoutManager(activity));
        rvFile.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            FileBean bean = fileBeanList.get(position);
            switch (bean.getType()) {
                case StaticValues.FILE_ITEM_TYPE_DIRECTORY:
                    load(bean.getPath());
                    break;

                case StaticValues.FILE_ITEM_TYPE_FILE:
                    EventBus.getDefault().post(new EventGoto(EventTags.GOTO_FILE_UPLOAD, bean.getPath()));
                    finish();
                    break;
            }
        });

        load(homePath);
    }

    @Override
    protected void loadDataFromServer() {

    }


    public void load(String path) {
        currentFile = new File(path);
        File[] files = currentFile.listFiles();
        if (files.length > 0) {
            tvNoData.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }

        if (files != null) {
            fileBeanList.clear();
            for (int a = 0; a < files.length; a++) {
                File file = files[a];
                FileBean bean = new FileBean();
                bean.setId(a + "");
                bean.setName(file.getName());
                bean.setPath(file.getPath());
                if (file.isDirectory()) {
                    bean.setType(StaticValues.FILE_ITEM_TYPE_DIRECTORY);
                    fileBeanList.add(bean);
                }
                if (file.isFile()) {
                    if (getFileType(file.getName()).equals("doc") || getFileType(file.getName()).equals("docx")) {
                        bean.setType(StaticValues.FILE_ITEM_TYPE_FILE);
                        fileBeanList.add(bean);
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (!currentFile.getPath().equals(homePath) && currentFile.isDirectory()) {
            load(currentFile.getParent());
        } else {
            finish();
        }
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    public String getFileType(String fileName) {
        String[] strArray = fileName.split("\\.");
        int suffixIndex = strArray.length - 1;
        return strArray[suffixIndex];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
