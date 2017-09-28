package com.znz.compass.znzlibray.network.znzhttp;

import com.alibaba.fastjson.JSONObject;

/**
 * 网络接口回调方法
 */
public interface IZnzHttpListener {

    void onSuccess(JSONObject response);

    void onFail(String error);
}
