package com.znz.compass.znzlibray.views.row_view;

import android.view.View;
import android.widget.CompoundButton;

/**
 * Created by panqiming on 16/8/4.
 */
public class ZnzRowDescription {

    private int rowMode;
    private int iconResId;
    private String title;
    private int titleColor;
    private String value;
    private int valueColor;
    private String hint;
    private String dotNum;
    private String isSwitch = "";
    private int rowHeight;
    private int inputMode;
    private boolean enableArraw;
    private boolean enableSwitch;
    private boolean enableLongLine;
    private boolean enableHide;
    private View.OnClickListener onClickListener;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    private String header;

    private ZnzRowDescription(Builder builder) {
        setRowMode(builder.rowMode);
        setIconResId(builder.iconResId);
        setTitle(builder.title);
        setTitleColor(builder.titleColor);
        setValue(builder.value);
        setValueColor(builder.valueColor);
        setHint(builder.hint);
        setDotNum(builder.dotNum);
        setIsSwitch(builder.isSwitch);
        setRowHeight(builder.rowHeight);
        setInputMode(builder.inputMode);
        setEnableArraw(builder.enableArraw);
        setEnableSwitch(builder.enableSwitch);
        setEnableLongLine(builder.enableLongLine);
        setEnableHide(builder.enableHide);
        setOnClickListener(builder.onClickListener);
        setOnCheckedChangeListener(builder.onCheckedChangeListener);
        setHeader(builder.header);
    }


    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public boolean isEnableHide() {
        return enableHide;
    }

    public void setEnableHide(boolean enableHide) {
        this.enableHide = enableHide;
    }

    public String getDotNum() {
        return dotNum;
    }

    public void setDotNum(String dotNum) {
        this.dotNum = dotNum;
    }

    public String getIsSwitch() {
        return isSwitch;
    }

    public void setIsSwitch(String isSwitch) {
        this.isSwitch = isSwitch;
    }

    public int getInputMode() {
        return inputMode;
    }

    public void setInputMode(@ZnzRowView.INPUT_MODE int inputMode) {
        this.inputMode = inputMode;
    }

    public int getValueColor() {
        return valueColor;
    }

    public void setValueColor(int valueColor) {
        this.valueColor = valueColor;
    }

    public boolean isEnableLongLine() {
        return enableLongLine;
    }

    public void setEnableLongLine(boolean enableLongLine) {
        this.enableLongLine = enableLongLine;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public String getHint() {
        return hint;
    }

    public boolean isEnableSwitch() {
        return enableSwitch;
    }

    public void setEnableSwitch(boolean enableSwitch) {
        this.enableSwitch = enableSwitch;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getRowMode() {
        return rowMode;
    }

    public void setRowMode(@ZnzRowView.ROW_MODE int rowMode) {
        this.rowMode = rowMode;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public int getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

    public boolean isEnableArraw() {
        return enableArraw;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setEnableArraw(boolean enableArraw) {
        this.enableArraw = enableArraw;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public String isSwitch() {
        return isSwitch;
    }

    public void setSwitch(String aSwitch) {
        isSwitch = aSwitch;
    }

    public static final class Builder {
        private int iconResId;
        private String title;
        private int titleColor;
        private String value;
        private int valueColor;
        private String hint;
        private String dotNum;
        private String isSwitch;
        private int rowHeight;
        private int inputMode;
        private boolean enableArraw;
        private boolean enableSwitch;
        private boolean enableLongLine;
        private boolean enableHide;
        private View.OnClickListener onClickListener;
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
        private int rowMode;

        private String header;

        public Builder() {
        }

        public Builder withIconResId(int val) {
            iconResId = val;
            return this;
        }

        public Builder withTitle(String val) {
            title = val;
            return this;
        }

        public Builder withTitleColor(int val) {
            titleColor = val;
            return this;
        }

        public Builder withValue(String val) {
            value = val;
            return this;
        }

        public Builder withValueColor(int val) {
            valueColor = val;
            return this;
        }

        public Builder withHint(String val) {
            hint = val;
            return this;
        }

        public Builder withDotNum(String val) {
            dotNum = val;
            return this;
        }

        public Builder withRowHeight(int val) {
            rowHeight = val;
            return this;
        }

        public Builder withInputMode(int val) {
            inputMode = val;
            return this;
        }

        public Builder withEnableArraw(boolean val) {
            enableArraw = val;
            return this;
        }

        public Builder withEnableSwitch(boolean val) {
            enableSwitch = val;
            return this;
        }

        public Builder withEnableLongLine(boolean val) {
            enableLongLine = val;
            return this;
        }

        public Builder withEnableHide(boolean val) {
            enableHide = val;
            return this;
        }

        public Builder withOnClickListener(View.OnClickListener val) {
            onClickListener = val;
            return this;
        }

        public Builder withOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener val) {
            onCheckedChangeListener = val;
            return this;
        }

        public Builder withHeader(String val) {
            header = val;
            return this;
        }

        public Builder withIsSwitch(String val) {
            isSwitch = val;
            return this;
        }

        public ZnzRowDescription build() {
            return new ZnzRowDescription(this);
        }

        public Builder withRowMode(int val) {
            rowMode = val;
            return this;
        }
    }
}