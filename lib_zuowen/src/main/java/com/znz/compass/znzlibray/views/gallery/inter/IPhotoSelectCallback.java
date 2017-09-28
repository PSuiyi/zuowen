package com.znz.compass.znzlibray.views.gallery.inter;

import java.util.List;

/**
 * IPhotoSelectCallback
 * Created by Yancy on 2016/10/26.
 */

public interface IPhotoSelectCallback {

    void onStart();

    void onSuccess(List<String> photoList);

    void onCancel();

    void onFinish();

    void onError();

}
