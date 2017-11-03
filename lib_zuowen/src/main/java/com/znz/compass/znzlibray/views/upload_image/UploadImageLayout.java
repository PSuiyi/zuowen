package com.znz.compass.znzlibray.views.upload_image;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Date： 2017/3/15 2017
 * User： PSuiyi
 * Description：
 */

public class UploadImageLayout extends LinearLayout {
    private Context context;
    private DataManager mDataManager;
    private RecyclerView rvUplodeImage;
    private List<String> dataList = new ArrayList<>();
    private UploadImageAdapter uploadImageAdapter;
    private int uploadMaxSize = 16;

    public UploadImageLayout(Context context) {
        super(context);
        init(context, null);
    }

    public UploadImageLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UploadImageLayout);
            uploadMaxSize = typedArray.getInteger(R.styleable.UploadImageLayout_upload_max_size, 8);
            typedArray.recycle();
        }

        mDataManager = DataManager.getInstance(context);
        LayoutInflater.from(context).inflate(R.layout.view_upload_image, this);
        rvUplodeImage = ViewHolder.init(this, R.id.rvUplodeImage);
        rvUplodeImage.setLayoutManager(new LinearLayoutManager(context));
        uploadImageAdapter = new UploadImageAdapter(context, dataList, uploadMaxSize);
        rvUplodeImage.setAdapter(uploadImageAdapter);
        dataList.add("add");
    }

    public List<String> getImageList() {
        List<String> result = new ArrayList<>();
        result.addAll(dataList);
        result.remove("add");
        return result;
    }
}
