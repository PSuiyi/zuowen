package com.znz.compass.znzlibray.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.znz.compass.znzlibray.R;

/**
 * Date： 2017/2/10 2017
 * User： PSuiyi
 * Description：公共loding
 */

public class ZnzLodingDialog extends ProgressDialog {
    public ZnzLodingDialog(Context context) {
        super(context);
    }

    public ZnzLodingDialog(Context context, int theme, boolean canCancel) {
        super(context, theme);
        setCancelable(canCancel);
        setCanceledOnTouchOutside(canCancel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.view_znz_loding);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }
}
