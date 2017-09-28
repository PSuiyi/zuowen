
package com.znz.compass.znzlibray.views;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MagicTextView extends TextView {
    // 递减/递增 的变量值
    private double mRate;
    // view 设置的值
    private double mValue;
    // 当前显示的值
    private double mCurValue;
    // 当前变化后最终状态的目标值
    private double mGalValue;
    // 控制加减法
    private int rate = 1;
    // 当前变化状态(增/减/不变)
    private int mState = 0;
    DecimalFormat fnum = new DecimalFormat("0.0");

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 1:
                    if (rate * mCurValue < mGalValue) {
                        setText(fnum.format(mCurValue));
                        mCurValue += mRate * rate;
                        mHandler.sendEmptyMessageDelayed(1, 50);
                    } else {
                        setText(fnum.format(mGalValue));
                        if (callback != null)
                            callback.changeState(1);
                    }
                    break;

                default:
                    break;
            }
        }

        ;
    };
    private INumberChangeCallback callback;

    public MagicTextView(Context context) {
        super(context);
    }

    public MagicTextView(Context context, AttributeSet set) {
        super(context, set);
    }

    public MagicTextView(Context context, AttributeSet set, int defStyle) {
        super(context, set, defStyle);
    }

    public void setValue(double value) {
        setText("0.0");
        mCurValue = 0.0;
        mGalValue = value;
        mValue = value;
        mRate = (double) (mValue / 20.00);
//        BigDecimal b = new BigDecimal(mRate);
//        mRate = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        mHandler.sendEmptyMessage(1);
    }

    public interface INumberChangeCallback{
        /**
         *
         * @param state 1:数字递增结束
         */
        void changeState(int state);
    }
    public void setINumberChangeCallback(INumberChangeCallback callback){
        this.callback = callback;
    }
}
