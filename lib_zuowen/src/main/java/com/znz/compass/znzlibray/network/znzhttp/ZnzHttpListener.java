package com.znz.compass.znzlibray.network.znzhttp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.socks.library.KLog;

/**
 * 网络接口回调
 */
public abstract class ZnzHttpListener implements IZnzHttpListener {

    protected JSONObject responseObject;//json数据，已经解过一层的数据
    protected String message;

    /**
     * 调用接口成功
     *
     * @param responseOriginal
     */
    @Override
    public void onSuccess(JSONObject responseOriginal) {
        try {
            responseObject = JSON.parseObject(responseOriginal.getString("data"));
            message = responseOriginal.getString("msg");
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    /**
     * 调用接口失败
     *
     * @param error
     */
    @Override
    public void onFail(String error) {
        KLog.e(error);
    }
}
