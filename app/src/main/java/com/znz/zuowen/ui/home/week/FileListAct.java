package com.znz.zuowen.ui.home.week;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.znz.compass.znzlibray.eventbus.EventManager;
import com.znz.compass.znzlibray.views.ZnzRemind;
import com.znz.compass.znzlibray.views.ZnzToolBar;
import com.znz.zuowen.R;
import com.znz.zuowen.adapter.FileListAdapter;
import com.znz.zuowen.base.BaseAppActivity;
import com.znz.zuowen.event.EventGoto;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.utils.StaticValues;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

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
    @Bind(R.id.file_list)
    ListView fileListView;
    @Bind(R.id.msg)
    TextView msgTextView;

    private String homePath = Environment.getExternalStorageDirectory().getPath();
    private List fileListData;
    private File currentFile;
    public FileListAdapter fileListAdapter;
    private int currentListMode = StaticValues.MODE_LIST;

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
        load(homePath);
    }

    @Override
    protected void loadDataFromServer() {

    }


    public void load(String path) {
        currentFile = new File(path);
        File[] files = currentFile.listFiles();

        if (files.length > 0) {
            fileListView.setVisibility(View.VISIBLE);
            msgTextView.setVisibility(View.GONE);
        } else {
            fileListView.setVisibility(View.GONE);
            msgTextView.setVisibility(View.VISIBLE);
            msgTextView.setText("该目录下没有文件");
        }

        if (files != null) {
            fileListData = new ArrayList<Map<String, Object>>();
            for (int a = 0; a < files.length; a++) {
                File file = files[a];
                Map line = new HashMap();
                line.put("id", a);
                line.put("name", file.getName());
                line.put("path", file.getPath());
                if (file.isDirectory()) {
                    line.put("type", StaticValues.FILE_ITEM_TYPE_DIRECTORY);
                    fileListData.add(line);
                }
                if (file.isFile()) {
                    if (getFileType(file.getName()).equals("doc") || getFileType(file.getName()).equals("docx")) {
                        line.put("type", StaticValues.FILE_ITEM_TYPE_FILE);
                        line.put("sub", getFileType(file.getName()));
                        fileListData.add(line);
                    }
                }
            }

            fileListAdapter = new FileListAdapter(fileListData, this, currentListMode);
            fileListView.setAdapter(fileListAdapter);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventGoto event) {
        if (event.getFlag() == EventTags.GOTO_FILE_UPLOAD) {
            finish();
        }
    }
}
