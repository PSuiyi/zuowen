package com.znz.compass.znzlibray.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.znz.compass.znzlibray.R;

/**
 * Date： 2017/3/17 2017
 * User： PSuiyi
 * Description：
 */

public class ZnzToast {

    private Toast mToast;
    private final View v;

    private ZnzToast(Context context, CharSequence text, int duration) {
        v = LayoutInflater.from(context).inflate(R.layout.view_znz_toast, null);
        TextView textView = (TextView) v.findViewById(R.id.tvContent);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
//        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setView(v);
    }

    public static ZnzToast makeText(Context context, CharSequence text, int duration) {
        return new ZnzToast(context, text, duration);
    }

    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }

    public void setText(String text) {
        TextView textView = (TextView) v.findViewById(R.id.tvContent);
        textView.setText(text);
    }
}
