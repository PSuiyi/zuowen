package com.znz.compass.znzlibray.views.upload_image;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.socks.library.KLog;
import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.base.BaseRecyclerAdapter;
import com.znz.compass.znzlibray.base.BaseRecyclerHolder;
import com.znz.compass.znzlibray.utils.ViewHolder;
import com.znz.compass.znzlibray.views.gallery.inter.IPhotoSelectCallback;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;

import java.util.List;

/**
 * Date： 2017/2/13 2017
 * User： PSuiyi
 * Description：
 */

public class UploadImageAdapter extends BaseRecyclerAdapter<String> {

    private final int uploadMaxSize;

    public UploadImageAdapter(Context context, List dataList, int uploadMaxSize) {
        super(context, dataList);
        this.uploadMaxSize = uploadMaxSize;
    }

    @Override
    public BaseRecyclerHolder onZnzCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZnzViewHolder(context, View.inflate(context, R.layout.item_gv_upload_image, null));
    }

    public class ZnzViewHolder extends BaseRecyclerHolder<String> {
        HttpImageView ivImage;
        ImageView ivDelete;

        public ZnzViewHolder(Context context, View itemView) {
            super(context, itemView);
            ivImage = ViewHolder.init(itemView, R.id.ivImage);
            ivDelete = ViewHolder.init(itemView, R.id.ivDelete);
        }

        @Override
        public void onBindViewHolder(int position) {
            bean = getItemObject(position);
            if (!bean.equals("add")) {
                ivImage.loadSquareImage(bean);
                ivDelete.setVisibility(View.VISIBLE);
            } else {
                ivImage.setImageResource(R.mipmap.add_image);
                ivDelete.setVisibility(View.GONE);
            }

            ivDelete.setOnClickListener(v -> {
                dataList.remove(position);
                if (!dataList.contains("add")) {
                    dataList.add("add");
                }
                notifyDataSetChanged();
            });
        }

        @Override
        public void onItemClick(View view, int position) {
            if (dataList.get(position).equals("add")) {
                mDataManager.openPhotoSelectSingle((Activity) context, new IPhotoSelectCallback() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(List<String> photoList) {
                        if (photoList.isEmpty()) {
                            return;
                        }
                        KLog.e(photoList.get(0));
                        dataList.remove("add");
                        if (dataList.size() < uploadMaxSize - 1) {
                            dataList.add(photoList.get(0));
                            dataList.add("add");
                        } else {
                            dataList.add(photoList.get(0));
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onError() {

                    }
                }, false);
            } else {
//                List<String> previewList = dataList;
//                previewList.remove("add");
//                mDataManager.showImagePreviewMulti((Activity) context, previewList, position, view);
            }
        }
    }
}
