package com.znz.compass.znzlibray.views.gallery.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.yalantis.ucrop.UCrop;
import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.base.BaseActivity;
import com.znz.compass.znzlibray.views.gallery.adapter.FolderAdapter;
import com.znz.compass.znzlibray.views.gallery.adapter.PhotoAdapter;
import com.znz.compass.znzlibray.views.gallery.bean.FolderInfo;
import com.znz.compass.znzlibray.views.gallery.bean.PhotoInfo;
import com.znz.compass.znzlibray.views.gallery.config.GalleryConfig;
import com.znz.compass.znzlibray.views.gallery.config.GalleryPick;
import com.znz.compass.znzlibray.views.gallery.inter.IPhotoSelectCallback;
import com.znz.compass.znzlibray.views.gallery.utils.FileUtils;
import com.znz.compass.znzlibray.views.gallery.utils.UCropUtils;
import com.znz.compass.znzlibray.views.gallery.widget.FolderListPopupWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 图片选择页面
 */
public class GalleryPickActivity extends BaseActivity {

    private Context mContext = null;
    private Activity mActivity = null;
    private final static String TAG = "GalleryPickActivity";

    private ArrayList<String> resultPhoto;

    private TextView tvFinish;                  // 完成按钮
    private TextView tvGalleryFolder;           // 文件夹按钮
    private LinearLayout btnGalleryPickBack;    // 返回按钮
    private RecyclerView rvGalleryImage;        // 图片列表

    private PhotoAdapter photoAdapter;              // 图片适配器
    private FolderAdapter folderAdapter;            // 文件夹适配器


    private List<FolderInfo> folderInfoList = new ArrayList<>();    // 本地文件夹信息List
    private List<PhotoInfo> photoInfoList = new ArrayList<>();      // 本地图片信息List

    private static final int LOADER_ALL = 0;         // 获取所有图片
    private static final int LOADER_CATEGORY = 1;    // 获取某个文件夹中的所有图片

    private boolean hasFolderScan = false;           // 是否扫描过

    private GalleryConfig galleryConfig;   // GalleryPick 配置器

    private static final int REQUEST_CAMERA = 100;   // 设置拍摄照片的 REQUEST_CODE

    private IPhotoSelectCallback mHandlerCallBack;   // GalleryPick 生命周期接口

    private FolderListPopupWindow folderListPopupWindow;   // 文件夹选择弹出框

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.gallery_main, 1};
    }

    @Override
    protected void initializeVariate() {
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("");
    }

    @Override
    protected void initializeAppBusiness() {

    }

    @Override
    protected void initializeView() {
        mContext = this;
        mActivity = this;

        galleryConfig = GalleryPick.getInstance().getGalleryConfig();
        Intent intent = getIntent();
        boolean isOpenCamera = intent.getBooleanExtra("isOpenCamera", false);
        if (isOpenCamera || galleryConfig.isOpenCamera()) {
            galleryConfig.getBuilder().isOpenCamera(true).build();
            showCameraAction();
        }

        initView();
        init();
        initPhoto();
    }

    @Override
    protected void loadDataFromServer() {

    }

    /**
     * 初始化视图
     */
    private void initView() {
        tvFinish = (TextView) super.findViewById(R.id.tvFinish);
        tvGalleryFolder = (TextView) super.findViewById(R.id.tvGalleryFolder);
        btnGalleryPickBack = (LinearLayout) super.findViewById(R.id.btnGalleryPickBack);
        rvGalleryImage = (RecyclerView) super.findViewById(R.id.rvGalleryImage);
    }

    /**
     * 初始化
     */
    private void init() {
        mHandlerCallBack = galleryConfig.getIHandlerCallBack();
        mHandlerCallBack.onStart();

        resultPhoto = galleryConfig.getPathList();

        tvFinish.setText(getString(R.string.gallery_finish, resultPhoto.size(), galleryConfig.getMaxSize()));

        btnGalleryPickBack.setOnClickListener(v -> {
            mHandlerCallBack.onCancel();
            exit();
        });

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        rvGalleryImage.setLayoutManager(gridLayoutManager);
        photoAdapter = new PhotoAdapter(mActivity, mContext, photoInfoList);
        photoAdapter.setOnCallBack(new PhotoAdapter.OnCallBack() {
            @Override
            public void OnClickCamera(List<String> selectPhotoList) {
                resultPhoto.clear();
                resultPhoto.addAll(selectPhotoList);

                new RxPermissions(activity)
                        .request(Manifest.permission.CAMERA,
                                Manifest.permission.READ_PHONE_STATE)
                        .subscribe(granted -> {
                            if (granted) {
                                showCameraAction();
                            } else {
                                new AlertDialog.Builder(activity)
                                        .setTitle("权限申请")
                                        .setMessage("该操作需要相机权限，请在设置中打开该应用的相机权限")
                                        .setCancelable(false)
                                        .setNegativeButton("取消", null)
                                        .setPositiveButton("去设置", (dialog, which) -> {
                                            mDataManager.openSettingPermissions(activity);
                                        })
                                        .show();
                            }
                        });
            }

            @Override
            public void OnClickPhoto(List<String> selectPhotoList) {
                tvFinish.setText(getString(R.string.gallery_finish, selectPhotoList.size(), galleryConfig.getMaxSize()));

                resultPhoto.clear();
                resultPhoto.addAll(selectPhotoList);

                if (!galleryConfig.isMultiSelect() && resultPhoto != null && resultPhoto.size() > 0) {
                    if (galleryConfig.isCrop()) {
                        cameraTempFile = new File(resultPhoto.get(0));
                        cropTempFile = FileUtils.getCorpFile(galleryConfig.getFilePath());
                        UCropUtils.start(mActivity, cameraTempFile, cropTempFile, galleryConfig.getAspectRatioX(), galleryConfig.getAspectRatioY(), galleryConfig.getMaxWidth(), galleryConfig.getMaxHeight());
                        return;
                    }
                    mHandlerCallBack.onSuccess(resultPhoto);
                    exit();
                }

            }

        });
        photoAdapter.setSelectPhoto(resultPhoto);
        rvGalleryImage.setAdapter(photoAdapter);


        if (!galleryConfig.isMultiSelect()) {
            tvFinish.setVisibility(View.GONE);
        }


        tvFinish.setOnClickListener(v -> {
            if (resultPhoto != null && resultPhoto.size() > 0) {
                mHandlerCallBack.onSuccess(resultPhoto);
                exit();
            }

        });

        tvGalleryFolder.setOnClickListener(v -> {
            if (folderListPopupWindow != null && folderListPopupWindow.isShowing()) {
                folderListPopupWindow.dismiss();
                return;
            }
            folderListPopupWindow = new FolderListPopupWindow(mActivity, mContext, folderAdapter);
            folderListPopupWindow.showAsDropDown(tvGalleryFolder);
        });

        folderAdapter = new FolderAdapter(mActivity, mContext, folderInfoList);
        folderAdapter.setOnClickListener(folderInfo -> {
            if (folderInfo == null) {
                getSupportLoaderManager().restartLoader(LOADER_ALL, null, mLoaderCallback);
                tvGalleryFolder.setText(R.string.gallery_all_folder);
            } else {
                photoInfoList.clear();
                photoInfoList.addAll(folderInfo.photoInfoList);
                photoAdapter.notifyDataSetChanged();
                tvGalleryFolder.setText(folderInfo.name);
            }
            folderListPopupWindow.dismiss();
            gridLayoutManager.scrollToPosition(0);
        });


    }


    /**
     * 初始化配置
     */
    private void initPhoto() {

        mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

            private final String[] IMAGE_PROJECTION = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_ADDED,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.SIZE
            };

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                if (id == LOADER_ALL) {
                    return new CursorLoader(mActivity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, null, null, IMAGE_PROJECTION[2] + " DESC");
                } else if (id == LOADER_CATEGORY) {
                    return new CursorLoader(mActivity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, IMAGE_PROJECTION[0] + " like '%" + args.getString("path") + "%'", null, IMAGE_PROJECTION[2] + " DESC");
                }

                return null;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                if (data != null) {
                    int count = data.getCount();
                    if (count > 0) {
                        List<PhotoInfo> tempPhotoList = new ArrayList<>();
                        data.moveToFirst();
                        do {
                            String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                            String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                            long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                            int size = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4]));
                            boolean showFlag = size > 1024 * 5;                           //是否大于5K
                            PhotoInfo photoInfo = new PhotoInfo(path, name, dateTime);
                            if (showFlag) {
                                tempPhotoList.add(photoInfo);
                            }
                            if (!hasFolderScan && showFlag) {
                                File photoFile = new File(path);                  // 获取图片文件
                                File folderFile = photoFile.getParentFile();      // 获取图片上一级文件夹

                                FolderInfo folderInfo = new FolderInfo();
                                folderInfo.name = folderFile.getName();
                                folderInfo.path = folderFile.getAbsolutePath();
                                folderInfo.photoInfo = photoInfo;
                                if (!folderInfoList.contains(folderInfo)) {      // 判断是否是已经扫描到的图片文件夹
                                    List<PhotoInfo> photoInfoList = new ArrayList<>();
                                    photoInfoList.add(photoInfo);
                                    folderInfo.photoInfoList = photoInfoList;
                                    folderInfoList.add(folderInfo);
                                } else {
                                    FolderInfo f = folderInfoList.get(folderInfoList.indexOf(folderInfo));
                                    f.photoInfoList.add(photoInfo);
                                }
                            }

                        } while (data.moveToNext());

                        photoInfoList.clear();
                        photoInfoList.addAll(tempPhotoList);

                        List<String> tempPhotoPathList = new ArrayList<>();
                        for (PhotoInfo photoInfo : photoInfoList) {
                            tempPhotoPathList.add(photoInfo.path);
                        }
                        for (String mPhotoPath : galleryConfig.getPathList()) {
                            if (!tempPhotoPathList.contains(mPhotoPath)) {
                                PhotoInfo photoInfo = new PhotoInfo(mPhotoPath, null, 0L);
                                photoInfoList.add(0, photoInfo);
                            }
                        }


                        photoAdapter.notifyDataSetChanged();

                        hasFolderScan = true;
                    }
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        };
        getSupportLoaderManager().restartLoader(LOADER_ALL, null, mLoaderCallback);   // 扫描手机中的图片
    }


    private File cameraTempFile;
    private File cropTempFile;

    /**
     * 选择相机
     */
    private void showCameraAction() {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(mActivity.getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            cameraTempFile = FileUtils.createTmpFile(mActivity, galleryConfig.getFilePath());
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraTempFile));
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {
            Toast.makeText(mContext, R.string.gallery_msg_no_camera, Toast.LENGTH_SHORT).show();
            galleryConfig.getIHandlerCallBack().onError();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            Log.i(TAG, "onActivityResult: " + resultCode);
            if (resultCode == RESULT_OK) {
                if (cameraTempFile != null) {
                    if (!galleryConfig.isMultiSelect()) {
                        resultPhoto.clear();
                        if (galleryConfig.isCrop()) {
                            cropTempFile = FileUtils.getCorpFile(galleryConfig.getFilePath());
                            UCropUtils.start(mActivity, cameraTempFile, cropTempFile, galleryConfig.getAspectRatioX(), galleryConfig.getAspectRatioY(), galleryConfig.getMaxWidth(), galleryConfig.getMaxHeight());
                            return;
                        }
                    }
                    resultPhoto.add(cameraTempFile.getAbsolutePath());
                    mHandlerCallBack.onSuccess(resultPhoto);
                    exit();
                }
            } else {
                if (cameraTempFile != null && cameraTempFile.exists()) {
                    cameraTempFile.delete();
                }
                if (galleryConfig.isOpenCamera()) {
                    exit();
                }
            }
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
//            final Uri resultUri = UCrop.getOutput(data);
//            if (cameraTempFile != null && cameraTempFile.exists()) {
//                cameraTempFile.delete();
//            }
            resultPhoto.clear();
            resultPhoto.add(cropTempFile.getAbsolutePath());
            mHandlerCallBack.onSuccess(resultPhoto);
            exit();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            galleryConfig.getIHandlerCallBack().onError();
//            final Throwable cropError = UCrop.getError(data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 退出
     */
    private void exit() {
        mHandlerCallBack.onFinish();
        finish();
    }

    /**
     * 回退键监听
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (folderListPopupWindow != null && folderListPopupWindow.isShowing()) {
                folderListPopupWindow.dismiss();
                return true;
            }
            mHandlerCallBack.onCancel();
            exit();
        }
        return true;
    }
}