package com.znz.compass.znzlibray.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * Date： 2017/6/28 2017
 * User： PSuiyi
 * Description：居中drawableleft
 */

public class TextViewDrawableCenter extends android.support.v7.widget.AppCompatTextView {
    public TextViewDrawableCenter(Context context) {
        super(context);
    }

    public TextViewDrawableCenter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewDrawableCenter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }
        }
        super.onDraw(canvas);
    }
}
