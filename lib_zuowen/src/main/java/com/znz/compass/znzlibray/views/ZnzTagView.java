package com.znz.compass.znzlibray.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.bean.TagBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义标签View
 * Created by Xugang on 2017/2/3.
 */
public class ZnzTagView extends LinearLayout {
    private List<int[]> children;
    private Context context;
    private boolean isMuch = true;
    private int[] backgrounds = new int[]{
            R.drawable.tag_blue,
            R.drawable.tag_green,
            R.drawable.tag_yellow,
            R.drawable.tag_red,
            R.drawable.tag_cyan,
            R.drawable.tag_orange,
    };
    private OnTagClickListener onTagClickListener;

    public ZnzTagView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ZnzTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        children = new ArrayList<>();
        setOrientation(VERTICAL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            int[] position = children.get(i);
            child.layout(position[0], position[1], position[2], position[3]);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        final int count = getChildCount(); // tag的数量
        int left = 0; // 当前的左边距离
        int top = 0; // 当前的上边距离
        int totalHeight = 0; // WRAP_CONTENT时控件总高度
        int totalWidth = 0; // WRAP_CONTENT时控件总宽度

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();

            if (i == 0) { // 第一行的高度
                totalHeight = params.topMargin + child.getMeasuredHeight() + params.bottomMargin;
            }

            if (left + params.leftMargin + child.getMeasuredWidth() + params.rightMargin > getMeasuredWidth()) { // 换行
                left = 0;
                top += params.topMargin + child.getMeasuredHeight() + params.bottomMargin; // 每个TextView的高度都一样，随便取一个都行
                totalHeight += params.topMargin + child.getMeasuredHeight() + params.bottomMargin;
            }

            children.add(new int[]{left + params.leftMargin, top + params.topMargin, left + params.leftMargin + child.getMeasuredWidth(), top + params.topMargin + child.getMeasuredHeight()});

            left += params.leftMargin + child.getMeasuredWidth() + params.rightMargin;

            if (left > totalWidth) { // 当宽度为WRAP_CONTENT时，取宽度最大的一行
                totalWidth = left;
            }
        }

        int height = 0;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            height = totalHeight;
        }

        int width = 0;
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            width = totalWidth;
        }

        setMeasuredDimension(width, height);
    }

    public void isMuch(boolean isMuch) {
        this.isMuch = isMuch;
    }

    public void setDataList(List<TagBean> tags) {
        removeAllViews();
        for (int i = 0; i < tags.size(); i++) {
            TextView tv = new TextView(context);
            tv.setText(tags.get(i).getTitle());

            int finalI = i;
            tv.setOnClickListener(v -> {
                if (isMuch) {
                    tags.get(finalI).setSelect(!tags.get(finalI).isSelect());
                } else {
                    for (TagBean tag : tags) {
                        tag.setSelect(false);
                    }
                    tags.get(finalI).setSelect(true);
                }

                setDataList(tags);
                if (onTagClickListener != null) {
                    onTagClickListener.onTagClick(tags.get(finalI));
                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(30, 20, 30, 20);
            tv.setPadding(60, 20, 60, 20);
            tv.setLayoutParams(params);

            if (tags.get(i).isSelect()) {
                tv.setBackgroundResource(R.drawable.bg_tag_focus);
                tv.setTextColor(getResources().getColor(R.color.red));
            } else {
                tv.setBackgroundResource(R.drawable.bg_tag);
                tv.setTextColor(getResources().getColor(R.color.text_gray));
            }
            addView(tv);
        }
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public interface OnTagClickListener {
        void onTagClick(TagBean tagBean);
    }
}
