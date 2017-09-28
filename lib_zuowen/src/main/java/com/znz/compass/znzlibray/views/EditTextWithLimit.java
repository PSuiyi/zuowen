package com.znz.compass.znzlibray.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.utils.ViewHolder;

/**
 * Date： 2017/3/15 2017
 * User： PSuiyi
 * Description：
 */

public class EditTextWithLimit extends LinearLayout implements TextWatcher {
    private Context context;
    private DataManager mDataManager;
    private EditText etContent;
    private TextView tvLimit;
    private LinearLayout llContainer;
    private int limitSize;
    private String limitHint;
    private int limitMinLine;

    public EditTextWithLimit(Context context) {
        super(context);
        init(context, null);
    }

    public EditTextWithLimit(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        mDataManager = DataManager.getInstance(context);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextWithLimit);
            limitSize = typedArray.getInteger(R.styleable.EditTextWithLimit_limit_size, 100);
            limitMinLine = typedArray.getInteger(R.styleable.EditTextWithLimit_limit_min_line, 3);
            limitHint = typedArray.getString(R.styleable.EditTextWithLimit_limit_hint);
            typedArray.recycle();
        }

        LayoutInflater.from(context).inflate(R.layout.view_edit_with_limit, this);
        etContent = ViewHolder.init(this, R.id.etContent);
        tvLimit = ViewHolder.init(this, R.id.tvLimit);
        llContainer = ViewHolder.init(this, R.id.llContainer);

        etContent.setHint(limitHint);
        etContent.setMinLines(limitMinLine);
        etContent.addTextChangedListener(this);
        tvLimit.setText("0/" + limitSize);
    }

    public String getText() {
        return etContent.getText().toString();
    }

    public EditText getEditText() {
        return etContent;
    }

    public void setText(String content) {
        etContent.setText(content);
    }

    public void setHint(String content) {
        etContent.setHint(content);
    }

    public void setLimitBackgroundColor(@ColorRes int resId) {
        llContainer.setBackgroundColor(getResources().getColor(resId));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > limitSize) {
            mDataManager.showToast("超出输入限制,请重新输入");
            etContent.setText(etContent.getText().toString().substring(0, limitSize));
            //光标移到末尾
            CharSequence text1 = etContent.getText();
            if (text1 instanceof Spannable) {
                Spannable spanText = (Spannable) text1;
                Selection.setSelection(spanText, text1.length());
            }
        } else {
            tvLimit.setText(s.length() + "/" + limitSize);
        }
    }
}
