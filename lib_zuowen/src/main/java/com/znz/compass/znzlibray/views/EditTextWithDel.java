package com.znz.compass.znzlibray.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.znz.compass.znzlibray.R;


/**
 * 带删除的edittext
 */
public class EditTextWithDel extends AppCompatEditText {
    private final static String TAG = "EditWithDelView";
    private Drawable imgDisable;
    private Drawable imgAble;
    private Context mContext;

    private boolean enableDelete = true;

    public EditTextWithDel(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextWithDel);
        enableDelete = typedArray.getBoolean(R.styleable.EditTextWithDel_edit_enable_delete, true);
        imgAble = ContextCompat.getDrawable(context, typedArray.getResourceId(R.styleable.EditTextWithDel_edit_delete_icon, R.drawable.textclose1));
        typedArray.recycle();

        init();
    }

    private void init() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });

        this.setOnFocusChangeListener((v, hasFocus) -> {
            if (enableDelete) {
                if (hasFocus && length() >= 1) {
                    setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, imgAble, null);
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, null, null);
                }
            }
        });
    }

    //根据字符长度加载提示Drawable
    private void setDrawable() {
        if (length() < 1) {
            setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, null, null);
        } else if (isFocused()) {
            setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, imgAble, null);
        }
    }

    //响应触摸点击事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 150;
            rect.top = rect.bottom - 150;
            if (rect.contains(eventX, eventY)) {
                setText("");
                setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, null, null);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    //将光标移动到末尾
    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        CharSequence text1 = getText();
        if (text1 instanceof Spannable) {
            Spannable spanText = (Spannable) text1;
            Selection.setSelection(spanText, text1.length());
        }
        //设置图片和文字的间距
        setCompoundDrawablePadding(15);
    }


    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

}