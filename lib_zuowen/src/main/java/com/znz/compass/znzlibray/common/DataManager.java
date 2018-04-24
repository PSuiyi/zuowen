package com.znz.compass.znzlibray.common;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.znz.compass.znzlibray.BuildConfig;
import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.ZnzApplication;
import com.znz.compass.znzlibray.eventbus.BaseEvent;
import com.znz.compass.znzlibray.utils.AESUtil;
import com.znz.compass.znzlibray.utils.ActivityStackManager;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.utils.ZnzLog;
import com.znz.compass.znzlibray.views.EditTextWithLimit;
import com.znz.compass.znzlibray.views.ZnzToast;
import com.znz.compass.znzlibray.views.gallery.config.GalleryConfig;
import com.znz.compass.znzlibray.views.gallery.config.GalleryPick;
import com.znz.compass.znzlibray.views.gallery.config.GlideImageLoader;
import com.znz.compass.znzlibray.views.gallery.inter.IPhotoSelectCallback;
import com.znz.compass.znzlibray.views.ios.ActionSheetDialog.UIAlertDialog;
import com.znz.compass.znzlibray.views.preview.PreviewActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class DataManager implements ZnzConst {

    public static final String TAG = "DataManager";

    public static DataManager instance;

    private File mLogDir, mDataDir, mImageDir;

    private Context context;

    private static final String CONFIG_NAME = "config";// 配置文件名字
    private ZnzToast mToast;

    private SharedPreferences.Editor editor;

    private SharedPreferences settings;

//    private PushAgent mPushAgent;

    /**
     * Perform root directory.
     */
    private String path = Environment.getExternalStorageDirectory()
            + File.separator + FILENAMEDIR + File.separator;

    private boolean isTokenOut;//token是否失效

    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context.getApplicationContext());
        }
        return instance;
    }

    public DataManager(Context context) {
        this.context = context;
//        mPushAgent = PushAgent.getInstance(context);
        init();
    }

    /**
     * 初始化.
     */
    private void init() {
        if (existSDCard()) {
            if (isFolderExists(path)) {
                String logPath = path + LOG_FILE_DIR;
                String dataPath = path + DATA_FILE_DIR;
                String imagePath = path + IMAGE_FILE_DIR;
                mLogDir = openDir(mLogDir, logPath);
                mDataDir = openDir(mDataDir, dataPath);
                mImageDir = openDir(mImageDir, imagePath);
                File file = new File(mImageDir, ".nomedia");
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 保存布尔值数据
     *
     * @param key
     * @param value
     */
    public void saveBooleanTempData(String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 读取布尔值数据.
     */
    public boolean readBooleanTempData(String key) {
        SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME,
                Context.MODE_PRIVATE);
        return settings.getBoolean(key, false);
    }

    /**
     * 保存数据
     *
     * @param key
     * @param value
     */
    public void saveTempData(String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, AESUtil.encrypt(value, ZnzConstants.AES_KEY));
        editor.commit();
    }

    /**
     * 读取数据.
     */
    public String readTempData(String key) {
        settings = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        if (settings.getString(key, "").equals("")) {
            return "";
        }
        return AESUtil.decrypt(settings.getString(key, ""), ZnzConstants.AES_KEY);
    }


    public void removeTempData(String key) {
        SharedPreferences settings = context.getSharedPreferences(CONFIG_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 读取数据.
     */
    public String readTempDataWithoutAes(String key) {
        settings = context.getSharedPreferences(CONFIG_NAME,
                Context.MODE_PRIVATE);
        return settings.getString(key, "");
    }

    /**
     * 清除数据
     */
    public void clearData() {
        settings.edit().clear().commit();
    }

    /**
     * 获取屏幕宽度.
     */
    public int getDeviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度.
     */
    public int getDeviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕密度.
     */
    public int getDeviceDensity(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 提示框显示内容.
     */
    public void showToast(int message) {
        showToast(context.getString(message));
    }

    /**
     * 提示框显示内容.
     */
    public void showToast(String message) {
        if (mToast == null) {
            mToast = ZnzToast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        Observable.empty()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(() -> mToast.show())
                .subscribe();
    }

    /**
     * 判断文件夹是否存在.
     */
    private boolean isFolderExists(String strFolder) {
        File file = new File(strFolder);
        if (!file.exists()) {
            if (file.mkdir()) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断SD卡是否存在?.
     */
    public boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 打开或创建目录.
     */
    private File openDir(File dir, String path) {
        if (isFolderExists(path)) {
            dir = new File(path);
        }
        return dir;
    }


    /**
     * ****************** 用户数据缓存 *******************
     */


    public void gotoActivity(Class<?> cls) {
        Intent intent = new Intent(context, cls);
        if (context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * activity跳转
     *
     * @param cls
     * @param bundle
     */
    public void gotoActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public String getAccessToken() {
        return readTempData(ZnzConstants.ACCESS_TOKEN);
    }


    /**
     * 判断是否登录
     *
     * @return
     */
    public boolean isLogin() {
        return readBooleanTempData(ZnzConstants.IS_LOGIN);
    }

    /**
     * 判断是否是debug模式
     *
     * @return
     */
    public boolean isAppDebug() {
        return ZnzConstants.APP_DEBUG;
    }

    /**
     * 判断是否是分析师
     *
     * @return
     */
    public boolean isAnalyst() {
        if (!StringUtil.isBlank(readTempData(ZnzConstants.ACCOUNT_TYPE))) {
            if (readTempData(ZnzConstants.ACCOUNT_TYPE).equals("3")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * token 过期
     *
     * @param context
     */
    public void tokenTimeOut(Context context) {
        if (!isTokenOut) {
            isTokenOut = true;
            new AlertDialog.Builder(context)
                    .setTitle("登录令牌失效")
                    .setMessage("您的账户在另外一个设备上登录或者登录令牌已过期，请重新登录")
                    .setCancelable(false)
                    .setPositiveButton("确定", (dialog, which) -> {
                        EventBus.getDefault().postSticky(new BaseEvent(0x90000));
                        isTokenOut = false;
                    })
                    .show();
        }
    }

    /**
     * 注销
     */
    public void logout(Activity activity, Class<?> cls) {
        String username = readTempData(ZnzConstants.ACCOUNT);
        String device_token = readTempData(ZnzConstants.DEVICE_TOKEN);
        String service_ip = readTempData(ZnzConstants.SERVICE_IP);

        clearData();

        saveTempData(ZnzConstants.ACCOUNT, username);
        saveTempData(ZnzConstants.DEVICE_TOKEN, device_token);
        saveTempData(ZnzConstants.SERVICE_IP, service_ip);

        saveBooleanTempData(ZnzConstants.IS_APP_OPEND, true);
        saveBooleanTempData(ZnzConstants.IS_LOGIN, false);
        ActivityStackManager.getInstance().AppExit(activity);
        activity.finish();
        gotoActivity(cls);
    }

    /**
     * 修改密码成功
     */
    public void updatePsd(Activity activity, Class<?> cls) {
        String username = readTempData(ZnzConstants.ACCOUNT);
        clearData();
        saveTempData(ZnzConstants.ACCOUNT, username);
        saveBooleanTempData(ZnzConstants.IS_APP_OPEND, true);
        ActivityStackManager.getInstance().AppExit(activity);
        gotoActivity(cls);
        activity.finish();
    }


    //--------------------- 控件相关 ----------------------

    /**
     * 获取随机boolean值，用于测试
     *
     * @return
     */
    public boolean getRandomBoolean() {
        int temp = (int) (1 + Math.random() * (10 - 1 + 1));
        if (temp % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取颜色
     *
     * @param colorRes
     * @return
     */
    public int getColor(@ColorRes int colorRes) {
        return ContextCompat.getColor(context, colorRes);
    }


    /**
     * 设置view的margin
     *
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * 通过分隔符获取多id
     *
     * @param stringList
     * @param separator
     * @return
     */
    public String getValueBySeparator(List<String> stringList, String separator) {
        String temp = "";
        if (stringList.isEmpty()) {
            return temp;
        } else {
            for (String s : stringList) {
                temp += s + separator;
            }
            return temp.substring(0, temp.length() - 1);
        }
    }

    /**
     * 获取view中的字段
     *
     * @param view
     * @return
     */
    public String getValueFromView(@NonNull View view) {
        if (view instanceof EditTextWithLimit) {
            return ((EditTextWithLimit) view).getText();
        }
        if (view instanceof EditText) {
            return ((EditText) view).getText().toString().trim();
        }
        if (view instanceof TextView) {
            return ((TextView) view).getText().toString().trim();
        }
        return null;
    }

    /**
     * 获取TextView中的字段
     *
     * @param textView
     * @return
     */
    @Deprecated
    public String getValueFromTextView(@NonNull TextView textView) {
        return textView.getText().toString().trim();
    }


    /**
     * 获取edit中的字段
     *
     * @param editText
     * @return
     */
    @Deprecated
    public String getValueFromEditText(@NonNull View editText) {
        if (editText instanceof EditTextWithLimit) {
            return ((EditTextWithLimit) editText).getText();
        } else {
            return ((EditText) editText).getText().toString().trim();
        }
    }


    /**
     * 设置文本文字
     *
     * @param view
     * @param str
     */
    public void setValueToView(@NonNull View view, @NonNull String str) {
        setValueToView(view, str, context.getResources().getString(R.string.common_no_data), true);
    }

    /**
     * 设置文本文字
     *
     * @param view
     * @param str
     */
    public void setValueToView(@NonNull View view, @NonNull String str, boolean visiable) {
        setValueToView(view, str, context.getResources().getString(R.string.common_no_data), visiable);
    }

    /**
     * 设置文本文字
     *
     * @param view
     * @param str
     */
    public void setValueToView(@NonNull View view, @NonNull String str, @NonNull String descripte) {
        setValueToView(view, str, descripte, true);
    }

    /**
     * 设置文本文字
     *
     * @param view
     * @param str
     * @param descripte 为空的描述
     */
    public void setValueToView(@NonNull View view, @NonNull String str, @NonNull String descripte, boolean visiable) {
        if (view instanceof TextView) {
            if (str == null) {
                ((TextView) view).setText(descripte);
                setViewVisibility(view, visiable);
            } else {
                if (!StringUtil.isBlank(str)) {
                    ((TextView) view).setText(str);
                } else {
                    ((TextView) view).setText(descripte);
                    setViewVisibility(view, visiable);
                }
            }
        }
        if (view instanceof EditText) {
            if (str == null) {
                ((EditText) view).setText(descripte);
                setViewVisibility(view, visiable);
            } else {
                if (!StringUtil.isBlank(str)) {
                    ((EditText) view).setText(str);
                } else {
                    ((EditText) view).setText(descripte);
                    setViewVisibility(view, visiable);
                }
            }
        }
        if (view instanceof EditTextWithLimit) {
            if (str == null) {
                ((EditTextWithLimit) view).setText(descripte);
                setViewVisibility(view, visiable);
            } else {
                if (!StringUtil.isBlank(str)) {
                    ((EditTextWithLimit) view).setText(str);
                } else {
                    ((EditTextWithLimit) view).setText(descripte);
                    setViewVisibility(view, visiable);
                }
            }
        }
    }

    /**
     * 设置文本文字
     *
     * @param textView
     * @param str
     */
    @Deprecated
    public void setValueToTextView(@NonNull TextView textView, @NonNull String str) {
        if (str == null) {
            textView.setText(context.getResources().getString(R.string.common_no_data));
        } else {
            if (!StringUtil.isBlank(str)) {
                textView.setText(str);
            } else {
                textView.setText(context.getResources().getString(R.string.common_no_data));
            }
        }
    }

    /**
     * 设置文本文字
     *
     * @param textView
     * @param str
     */
    public void setValueHtmlToTextView(@NonNull TextView textView, @NonNull String str) {
        if (str == null) {
            textView.setText(context.getResources().getString(R.string.common_no_data));
        } else {
            if (!StringUtil.isBlank(str)) {
                textView.setText(Html.fromHtml(str));
            } else {
                textView.setText(context.getResources().getString(R.string.common_no_data));
            }
        }
    }

    /**
     * 设置文本文字
     *
     * @param textView
     * @param str
     */
    @Deprecated
    public void setValueToTextView(@NonNull TextView textView, @NonNull String description, @NonNull String str) {
        if (str == null) {
            textView.setText(description + context.getResources().getString(R.string.common_no_data));
        } else {
            if (!StringUtil.isBlank(str)) {
                textView.setText(description + str);
            } else {
                textView.setText(description + context.getResources().getString(R.string.common_no_data));
            }
        }
    }

    /**
     * 设置控件是否隐藏
     *
     * @param view
     * @param visiable
     */
    public void setViewVisibility(View view, boolean visiable) {
        if (visiable) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte == 0) {
            return size + "MB";
        }
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }


    /**
     * 设置左边图片
     *
     * @param text
     * @param resId
     */
    public void setTextDrawableLeft(TextView text, @DrawableRes int resId) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        text.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 设置右边图片
     *
     * @param text
     * @param resId
     */
    public void setTextDrawableRight(TextView text, @DrawableRes int resId) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        text.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 控制焦点
     *
     * @param focusable
     */
    public void toggleEditTextFocus(EditText editText, boolean focusable) {
        editText.setFocusable(focusable);
        editText.setFocusableInTouchMode(focusable);
        editText.requestFocus();
        editText.requestFocusFromTouch();
        if (focusable) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(editText, 0);
                }
            }, 300);
        }
    }

    /**
     * 强制显示键盘
     */
    public void toggleInputSoft() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 300);
    }

    /**
     * 强制关闭
     */
    public void toggleOffInputSoft(View v) {
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe(aLong -> {
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }
                });
    }

    /**
     * 设置文本颜色
     *
     * @param textView
     * @param str1
     * @param color1   字符串str的颜色
     * @param str2
     * @param color2   字符串str2的颜色
     */
    public void setTextViewColor(TextView textView, String str1, int color1, String str2, int color2) {
        SpannableStringBuilder style = new SpannableStringBuilder(str1 + str2);
        style.setSpan(new ForegroundColorSpan(color1), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(color2), str1.length(), str1.length() + str2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(style);
    }

    /**
     * 设置文本颜色
     *
     * @param textView
     * @param str1
     * @param color1   字符串str的颜色
     * @param str2
     * @param color2   字符串str2的颜色
     * @param str3
     * @param color3   字符串str3的颜色
     */
    public void setTextViewColor(TextView textView, String str1, int color1, String str2, int color2, String str3, int color3) {
        SpannableStringBuilder style = new SpannableStringBuilder(str1 + str2 + str3);
        style.setSpan(new ForegroundColorSpan(color1), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(color2), str1.length(), str1.length() + str2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(color3), str1.length() + str2.length(), str1.length() + str2.length() + str3.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(style);
    }


    /**
     * 获取非空值
     *
     * @param str
     * @return
     */
    public String getValueString(@NonNull String str) {
        if (!StringUtil.isBlank(str)) {
            return str;
        } else {
            return context.getResources().getString(R.string.common_no_data);
        }
    }

    /**
     * 获取非空值
     *
     * @param str
     * @return
     */
    public String getValueString(@NonNull String str, String description) {
        if (!StringUtil.isBlank(str)) {
            return str;
        } else {
            return description;
        }
    }


    /**
     * 移动光标到末尾
     *
     * @param editText
     */
    public void moveCursorEnd(View editText) {
        //光标移到末尾
        CharSequence text1 = null;
        if (editText instanceof EditTextWithLimit) {
            text1 = ((EditTextWithLimit) editText).getText();
        } else if (editText instanceof EditText) {
            text1 = ((EditText) editText).getText();
        }

        if (text1 instanceof Spannable) {
            Spannable spanText = (Spannable) text1;
            Selection.setSelection(spanText, text1.length());
        }

    }


    /********************设备信息相关**************************/


    /**
     * 打开链接
     *
     * @param url
     */
    public void openUrl(String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送邮件
     *
     * @param content
     */
    public void openMail(String content) {
        try {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("plain/text");
            email.putExtra(Intent.EXTRA_SUBJECT, "22");
            email.putExtra(Intent.EXTRA_TEXT, content);
            email.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 父子到剪贴板
     *
     * @param content
     */
    public void copyToClipboard(String content) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(content);
        showToast("复制成功");
    }

    /**
     * 打开应用市场
     */
    public void openMarket() {
        try {
            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            Intent intentGrade = new Intent(Intent.ACTION_VIEW, uri);
            intentGrade.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentGrade);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放视频
     */
    public void openVideo(Activity activity, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "video/mp4");
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开网络设置界面
     */
    public void openSettingWifi(Activity activity) {
        activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    /**
     * 打开权限设置界面
     */
    public void openSettingPermissions(Activity activity) {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", activity.getPackageName());
        try {
            activity.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();

            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            try {
                activity.startActivity(intent);
            } catch (Exception e1) {
                e1.printStackTrace();

                try {
                    Intent intentHuawei = new Intent();
                    intentHuawei.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
                    intent.setComponent(comp);
                    activity.startActivity(intentHuawei);
                } catch (Exception e2) {
                    e2.printStackTrace();

                    Intent localIntent = new Intent();
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= 9) {
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                    } else if (Build.VERSION.SDK_INT <= 8) {
                        localIntent.setAction(Intent.ACTION_VIEW);
                        localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                        localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
                    }
                    activity.startActivity(localIntent);
                }
            }
        }
    }


    /**
     * 推荐给好友（系统自带的分享方式）
     *
     * @param url
     * @param shareTitle
     */
    public void recommandToFriend(String url, String shareTitle) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareTitle + "   " + url);

        Intent itn = Intent.createChooser(intent, "分享");
        itn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(itn);
    }

    /**
     * 拨打电话
     */
    public void callPhone(Activity activity, String number) {
        new RxPermissions(activity)
                .request(Manifest.permission.CALL_PHONE)
                .subscribe(granted -> {
                    if (granted) {
                        new UIAlertDialog(activity)
                                .builder()
                                .setMsg("确定拨打" + number)
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", v2 -> {
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    Uri data = Uri.parse("tel:" + number);
                                    intent.setData(data);
                                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    }
                                    activity.startActivity(intent);
                                })
                                .show();
                    } else {
                        new AlertDialog.Builder(activity)
                                .setTitle("权限申请")
                                .setMessage("该操作需要相机权限，请在设置中打开该应用的相机权限")
                                .setCancelable(false)
                                .setNegativeButton("取消", null)
                                .setPositiveButton("去设置", (dialog, which) -> {
                                    openSettingPermissions(activity);
                                })
                                .show();
                    }
                });
    }

    /**
     * 创建快捷方式
     *
     * @param cls
     * @param name
     * @param resId
     */
    public void createShortcut(Class<?> cls, String name, int resId) {
        if (!isExistShortCut(name)) {
            Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
            shortcut.putExtra("duplicate", false);//设置是否重复创建

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setClass(context, cls);//设置第一个页面
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);

            Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(context, resId);
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

            context.sendBroadcast(shortcut);
        }
    }

    /**
     * 判读是否已经存在快捷方式
     *
     * @param name
     * @return
     */
    public boolean isExistShortCut(String name) {
        boolean isInstallShortcut = false;
        final ContentResolver cr = context.getContentResolver();
        final String AUTHORITY = "com.android.launcher2.settings";
        final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
        Cursor c = cr.query(CONTENT_URI, new String[]{"title", "iconResource"}, "title=?", new String[]{name}, null);
        if (c != null && c.getCount() > 0) {
            isInstallShortcut = true;
            ZnzLog.e("已经存在快捷方式");
        }
        return isInstallShortcut;
    }

    /**
     * app是否是debug
     *
     * @return
     */
    public boolean isAppDebugable() {
        try {
            ApplicationInfo info = ZnzApplication.getContext().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 打开图片选择器
     */
    public void openPhotoSelectMulti(Activity activity, IPhotoSelectCallback iPhotoSelectCallback) {
        GalleryConfig galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iPhotoSelectCallback)     // 监听接口（必填）
                .multiSelect(true)                      // 是否多选   默认：false
                .multiSelect(true, 8)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(8)                             // 配置多选时 的多选数量。    默认：9
                .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(true, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath(ZnzConstants.IMAGE_DIR)          // 图片存放路径
                .build();
        GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(activity);
    }

    /**
     * 打开单个图片，并且设置是否需要裁切
     *
     * @param activity
     * @param iPhotoSelectCallback
     * @param needCrop
     */
    public void openPhotoSelectSingle(Activity activity, IPhotoSelectCallback iPhotoSelectCallback, boolean needCrop) {
        new RxPermissions(activity)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(granted -> {
                    if (granted) {
                        GalleryConfig galleryConfig = new GalleryConfig.Builder()
                                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                                .iHandlerCallBack(iPhotoSelectCallback)     // 监听接口（必填）
                                .multiSelect(false)                      // 是否多选   默认：false
                                .crop(needCrop)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                                .crop(needCrop, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                                .filePath(ZnzConstants.IMAGE_DIR)          // 图片存放路径
                                .build();
                        GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(activity);
                    } else {
                        new AlertDialog.Builder(activity)
                                .setTitle("权限申请")
                                .setMessage("该操作需要相机权限，请在设置中打开该应用的相机权限")
                                .setCancelable(false)
                                .setNegativeButton("取消", null)
                                .setPositiveButton("去设置", (dialog, which) -> {
                                    openSettingPermissions(activity);
                                })
                                .show();
                    }
                });
    }

    /**
     * 单张图片预览
     *
     * @param url
     */
    public void showImagePreviewSingle(Context context, String url, View view) {
        Activity activity;
        if (context instanceof Activity) {
            activity = (Activity) context;
        } else {
            KLog.e("请传入类型activity的context");
            return;
        }

        ArrayList<Rect> rects = new ArrayList<>();
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        rects.add(rect);

        String[] imgs = {url};

        Intent intent = new Intent(activity, PreviewActivity.class);
        intent.putExtra("imgUrls", imgs);
        intent.putExtra("index", 0);
        intent.putExtra("bounds", rects);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    /**
     * 多张图片预览
     *
     * @param imageUrls
     * @param position
     * @param view
     */
    public void showImagePreviewMulti(Context context, List<String> imageUrls, int position, View view) {
        Activity activity;
        if (context instanceof Activity) {
            activity = (Activity) context;
        } else {
            KLog.e("请传入类型activity的context");
            return;
        }

        ViewGroup parent = (ViewGroup) view.getParent();
        int childCount = parent.getChildCount();
        ArrayList<Rect> rects = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            Rect rect = new Rect();
            View child = parent.getChildAt(i);
            child.getGlobalVisibleRect(rect);
            rects.add(rect);
        }
        Intent intent = new Intent(context, PreviewActivity.class);
        String imageArray[] = new String[imageUrls.size()];
        for (int i = 0; i < imageArray.length; i++) {
            imageArray[i] = imageUrls.get(i);
        }
        intent.putExtra("imgUrls", imageArray);
        intent.putExtra("index", position);
        intent.putExtra("bounds", rects);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }


    /**
     * 返回缓存文件夹
     */
    public static File getCacheFile(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = null;
            file = context.getExternalCacheDir();//获取系统管理的sd卡缓存文件
            if (file == null) {//如果获取的为空,就是用自己定义的缓存文件夹做缓存路径
                file = new File(getCacheFilePath(context));
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            return file;
        } else {
            return context.getCacheDir();
        }
    }

    /**
     * 获取自定义缓存文件地址
     *
     * @param context
     * @return
     */
    public static String getCacheFilePath(Context context) {
        String packageName = context.getPackageName();
        return "/mnt/sdcard/" + packageName;
    }


    public void addAlias(String id, String apiKey) {
//        mPushAgent.addExclusiveAlias(id, apiKey, new UTrack.ICallBack() {
//            @Override
//            public void onMessage(boolean isSuccess, String message) {
//                KLog.e("add_alias_result:" + message.toString());
//                if (Boolean.TRUE.equals(isSuccess)) {
//                    KLog.e("alias was set successfully.");
//                } else {
//                    KLog.e("fail");
//                }
//            }
//        });
    }

    public void removeAlias(String id, String apiKey) {
//        mPushAgent.removeAlias(id, apiKey, new UTrack.ICallBack() {
//            @Override
//            public void onMessage(boolean isSuccess, String message) {
//                KLog.e("add_alias_result:" + message.toString());
//                if (Boolean.TRUE.equals(isSuccess)) {
//                    KLog.e("alias was remove successfully.");
//                } else {
//                    KLog.e("fail");
//                }
//            }
//        });
    }

    public void addTag(String message) {
//        mPushAgent.getTagManager().add(new TagManager.TCallBack() {
//            @Override
//            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
//                //isSuccess表示操作是否成功
//            }
//        }, "movie", "sport");
    }

    public void removeTag(String message) {
//        mPushAgent.getTagManager().add(new TagManager.TCallBack() {
//            @Override
//            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
//
//            }
//        }, String... tags);
    }
}