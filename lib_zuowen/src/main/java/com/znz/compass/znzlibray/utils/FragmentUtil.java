package com.znz.compass.znzlibray.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Administrator on 2016/3/16.
 */
public class FragmentUtil {
    public Fragment mContent;

    /**
     * @param from         从哪个界面
     * @param to           到哪个界面
     * @param id           替换的flayout的id
     * @param mFragmentMan
     */
    public void switchContent(Fragment to, int id, FragmentManager mFragmentMan) {

        FragmentTransaction transaction = mFragmentMan.beginTransaction();
        if (!to.isAdded()) {// 先判断是否被add过
            transaction.hide(mContent).add(id, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        if (mContent != to) {
            mContent = to;
        }
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
