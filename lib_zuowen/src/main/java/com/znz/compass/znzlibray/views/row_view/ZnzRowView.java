package com.znz.compass.znzlibray.views.row_view;

import android.content.Context;
import android.support.annotation.IntDef;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.utils.StringUtil;
import com.znz.compass.znzlibray.utils.ViewHolder;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.ios.UISwitchButton;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by panqiming on 16/8/4.
 */
public class ZnzRowView extends LinearLayout {
    private Context context;
    private ImageView ivIcon;
    private ImageView ivArrow;
    private TextView tvTitle;
    private TextView tvValue;
    private TextView tvDot;
    private EditText etValue;
    private UISwitchButton iosSwitch;
    private View line;
    private View lineLong;

    private LinearLayout llMulti;
    private LinearLayout llRowView;
    private TextView tvValueMulti;

    private HttpImageView ivRowHeader;

    public static final int MODE_DISPLAY = 0x01;
    public static final int MODE_EDIT = 0x02;
    public static final int MODE_MULTI_DISPLAY = 0x03;

    public static final int INPUT_MODE_PHONE = 0x21;
    public static final int INPUT_MODE_NUMBER = 0x22;

    @IntDef({MODE_DISPLAY, MODE_EDIT, MODE_MULTI_DISPLAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ROW_MODE {
    }

    @IntDef({INPUT_MODE_PHONE, INPUT_MODE_NUMBER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface INPUT_MODE {
    }

    public ZnzRowView(Context context) {
        super(context);
        this.context = context;
        initializeView();
    }

    public ZnzRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeView();
    }

    public ZnzRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initializeView();
    }

    private void initializeView() {
        View.inflate(context, R.layout.view_znz_row, this);
        ivIcon = ViewHolder.init(this, R.id.ivIcon);
        ivArrow = ViewHolder.init(this, R.id.ivArrow);
        tvTitle = ViewHolder.init(this, R.id.tvTitle);
        tvValue = ViewHolder.init(this, R.id.tvValue);
        tvDot = ViewHolder.init(this, R.id.tvDot);
        etValue = ViewHolder.init(this, R.id.etValue);
        line = ViewHolder.init(this, R.id.line);
        lineLong = ViewHolder.init(this, R.id.lineLong);
        iosSwitch = ViewHolder.init(this, R.id.iosSwitch);

        llRowView = ViewHolder.init(this, R.id.llRowView);
        ivRowHeader = ViewHolder.init(this, R.id.ivRowHeader);

        llMulti = ViewHolder.init(this, R.id.llMulti);
        tvValueMulti = ViewHolder.init(this, R.id.tvValueMulti);
    }

    public void setRowData(ZnzRowDescription znzRowDescription) {
        //设置row模式
        switch (znzRowDescription.getRowMode()) {
            case MODE_EDIT:
                llMulti.setVisibility(GONE);
                etValue.setVisibility(VISIBLE);
                tvValue.setVisibility(GONE);

                //设置输入样式
                if (znzRowDescription.getInputMode() != 0) {
                    switch (znzRowDescription.getInputMode()) {
                        case INPUT_MODE_PHONE:
                            etValue.setInputType(InputType.TYPE_CLASS_PHONE);
                            etValue.setMaxWidth(11);
                            break;
                        case INPUT_MODE_NUMBER:
                            etValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                            break;
                    }
                }

                //设置值提示
                if (znzRowDescription.getHint() != null) {
                    etValue.setHint(znzRowDescription.getHint());
                }

                if (znzRowDescription.getValue() != null) {
                    etValue.setText(znzRowDescription.getValue());
                }

                etValue.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        znzRowDescription.setValue(s.toString());
                    }
                });
                break;
            case MODE_MULTI_DISPLAY:
                llMulti.setVisibility(VISIBLE);
                ivArrow.setVisibility(GONE);
                tvValue.setVisibility(GONE);
                if (znzRowDescription.getValue() != null) {
                    tvValueMulti.setText(znzRowDescription.getValue());
                }
                //设置值提示
                if (znzRowDescription.getHint() != null) {
                    tvValueMulti.setHint(znzRowDescription.getHint());
                }
                break;
            default:
                llMulti.setVisibility(GONE);
                etValue.setVisibility(GONE);

                //如果有小红点数量，不显示值等
                if (!StringUtil.isBlank(znzRowDescription.getDotNum())) {
                    if (!znzRowDescription.getDotNum().equals("0")) {
                        tvDot.setVisibility(VISIBLE);
                        tvValue.setVisibility(GONE);
                        tvDot.setText(znzRowDescription.getDotNum());
                    }
                } else {
                    if (znzRowDescription.getHint() == null && znzRowDescription.getValue() == null) {
                        tvValue.setVisibility(GONE);
                    } else {
                        tvValue.setVisibility(VISIBLE);
                    }

                    //设置值提示
                    if (znzRowDescription.getHint() != null) {
                        tvValue.setHint(znzRowDescription.getHint());
                    }

                    if (znzRowDescription.getValue() != null) {
                        tvValue.setText(znzRowDescription.getValue());
                        if (znzRowDescription.getValueColor() != 0) {
                            tvValue.setTextColor(znzRowDescription.getValueColor());
                        }
                    }
                }
                break;
        }

        if (znzRowDescription.getIconResId() == 0) {
            ivIcon.setVisibility(GONE);
        } else {
            ivIcon.setVisibility(VISIBLE);
            ivIcon.setImageResource(znzRowDescription.getIconResId());
        }

        if (znzRowDescription.isEnableArraw()) {
            ivArrow.setVisibility(VISIBLE);
            //如果箭头存在，去掉padding，因为箭头有marginleft
            tvValue.setPadding(0, 0, 0, 0);
            etValue.setPadding(0, 0, 0, 0);
            tvValueMulti.setPadding(0, 0, 0, 0);
        } else {
            ivArrow.setVisibility(GONE);
        }

        if (znzRowDescription.getTitle() == null) {
            tvTitle.setVisibility(GONE);
        } else {
            tvTitle.setVisibility(VISIBLE);
            tvTitle.setText(znzRowDescription.getTitle());
        }

        //设置值颜色
        if (tvValue.getVisibility() == VISIBLE) {
            if (znzRowDescription.getValueColor() != 0) {
                tvValue.setTextColor(znzRowDescription.getValueColor());
            }
        }

        //设置头像
        if (znzRowDescription.getHeader() == null) {
            ivRowHeader.setVisibility(View.GONE);
        } else {
            ivRowHeader.setVisibility(View.VISIBLE);
            ivRowHeader.loadHeaderImage(znzRowDescription.getHeader());
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, znzRowDescription.getRowHeight());
            llRowView.setLayoutParams(params);
        }

        //设置是否显示switch
        if (znzRowDescription.isEnableSwitch()) {
            iosSwitch.setVisibility(View.VISIBLE);
        } else {
            iosSwitch.setVisibility(View.GONE);
        }
        if (!StringUtil.isBlank(znzRowDescription.isSwitch())) {
            //设置switch的状态
            if (znzRowDescription.isSwitch().equals("1")) {
                iosSwitch.setChecked(true);
            } else {
                iosSwitch.setChecked(false);
            }
        }

        //设置是否显示长间隔
        if (znzRowDescription.isEnableLongLine()) {
            lineLong.setVisibility(VISIBLE);
            line.setVisibility(GONE);
        } else {
            lineLong.setVisibility(GONE);
            line.setVisibility(VISIBLE);
        }

        //设置标题颜色
        if (tvTitle.getVisibility() == VISIBLE) {
            if (znzRowDescription.getTitleColor() != 0) {
                tvTitle.setTextColor(znzRowDescription.getTitleColor());
            }
        }

        if (znzRowDescription.getOnClickListener() != null) {
            this.setOnClickListener(znzRowDescription.getOnClickListener());
        }

        //设置开关点击事件
        if (iosSwitch.getVisibility() == VISIBLE) {
            if (znzRowDescription.getOnCheckedChangeListener() != null) {
                iosSwitch.setOnCheckedChangeListener(znzRowDescription.getOnCheckedChangeListener());
            }
        }
    }
}