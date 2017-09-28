package com.znz.compass.znzlibray.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.utils.ViewHolder;


/**
 * Created by panqiming on 2015/5/6.
 */
public class ZnzStarView extends LinearLayout implements View.OnClickListener {

    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;
    private TextView tvScore;

    private int star;
    private onStarClickListener onStarClickListener;

    private boolean clickable;//是否可点击
    private boolean dispalyScrore = true;//是否显示分数

    private int starWidth;
    private int starHeight;
    private int starSpace;

    @Override
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public ZnzStarView(Context context) {
        super(context);
        init(context);
    }

    public ZnzStarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZnzStarView);
        clickable = typedArray.getBoolean(R.styleable.ZnzStarView_star_clickable, false);
        dispalyScrore = typedArray.getBoolean(R.styleable.ZnzStarView_star_display_score, true);
        starWidth = typedArray.getDimensionPixelSize(R.styleable.ZnzStarView_star_width, 0);
        starHeight = typedArray.getDimensionPixelSize(R.styleable.ZnzStarView_star_height, 0);
        starSpace = typedArray.getDimensionPixelSize(R.styleable.ZnzStarView_star_space, 0);
        typedArray.recycle();

        init(context);
    }

    public int getStar() {
        return star;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_star, this);
        star1 = ViewHolder.init(this, R.id.star1);
        star2 = ViewHolder.init(this, R.id.star2);
        star3 = ViewHolder.init(this, R.id.star3);
        star4 = ViewHolder.init(this, R.id.star4);
        star5 = ViewHolder.init(this, R.id.star5);
        tvScore = ViewHolder.init(this, R.id.tvScore);

        if (clickable) {
            star1.setOnClickListener(this);
            star2.setOnClickListener(this);
            star3.setOnClickListener(this);
            star4.setOnClickListener(this);
            star5.setOnClickListener(this);
        }

        if (dispalyScrore) {
            tvScore.setVisibility(VISIBLE);
        } else {
            tvScore.setVisibility(View.GONE);
        }

        if (starHeight != 0) {
            LayoutParams layoutParams = new LayoutParams(starWidth, starHeight);
            if (starSpace != 0) {
                layoutParams.setMargins(0, 0, starSpace, 0);
            }
            star1.setLayoutParams(layoutParams);
            star2.setLayoutParams(layoutParams);
            star3.setLayoutParams(layoutParams);
            star4.setLayoutParams(layoutParams);
            star5.setLayoutParams(layoutParams);
        }
    }

    @SuppressWarnings("deprecation")
    public void setStarNum(float num) {
        tvScore.setText(num + "");
        star = (int) num;
        if (num < 0.5) {
            star1.setImageResource(R.drawable.star_gry);
            star2.setImageResource(R.drawable.star_gry);
            star3.setImageResource(R.drawable.star_gry);
            star4.setImageResource(R.drawable.star_gry);
            star5.setImageResource(R.drawable.star_gry);
        } else if (num < 1 && num >= 0.5) {
            star1.setImageResource(R.drawable.halfstar);
            star2.setImageResource(R.drawable.star_gry);
            star3.setImageResource(R.drawable.star_gry);
            star4.setImageResource(R.drawable.star_gry);
            star5.setImageResource(R.drawable.star_gry);
        } else if (num < 1.5 && num >= 1) {
            star1.setImageResource(R.drawable.saveyel);
            star2.setImageResource(R.drawable.star_gry);
            star3.setImageResource(R.drawable.star_gry);
            star4.setImageResource(R.drawable.star_gry);
            star5.setImageResource(R.drawable.star_gry);
        } else if (num < 2 && num >= 1.5) {
            star1.setImageResource(R.drawable.saveyel);
            star2.setImageResource(R.drawable.halfstar);
            star3.setImageResource(R.drawable.star_gry);
            star4.setImageResource(R.drawable.star_gry);
            star5.setImageResource(R.drawable.star_gry);
        } else if (num < 2.5 && num >= 2) {
            star1.setImageResource(R.drawable.saveyel);
            star2.setImageResource(R.drawable.saveyel);
            star3.setImageResource(R.drawable.star_gry);
            star4.setImageResource(R.drawable.star_gry);
            star5.setImageResource(R.drawable.star_gry);
        } else if (num < 3 && num >= 2.5) {
            star1.setImageResource(R.drawable.saveyel);
            star2.setImageResource(R.drawable.saveyel);
            star3.setImageResource(R.drawable.halfstar);
            star4.setImageResource(R.drawable.star_gry);
            star5.setImageResource(R.drawable.star_gry);
        } else if (num < 3.5 && num >= 3) {
            star1.setImageResource(R.drawable.saveyel);
            star2.setImageResource(R.drawable.saveyel);
            star3.setImageResource(R.drawable.saveyel);
            star4.setImageResource(R.drawable.star_gry);
            star5.setImageResource(R.drawable.star_gry);
        } else if (num < 4 && num >= 3.5) {
            star1.setImageResource(R.drawable.saveyel);
            star2.setImageResource(R.drawable.saveyel);
            star3.setImageResource(R.drawable.saveyel);
            star4.setImageResource(R.drawable.halfstar);
            star5.setImageResource(R.drawable.star_gry);
        } else if (num < 4.5 && num >= 4) {
            star1.setImageResource(R.drawable.saveyel);
            star2.setImageResource(R.drawable.saveyel);
            star3.setImageResource(R.drawable.saveyel);
            star4.setImageResource(R.drawable.saveyel);
            star5.setImageResource(R.drawable.star_gry);
        } else if (num < 5 && num >= 4.5) {
            star1.setImageResource(R.drawable.saveyel);
            star2.setImageResource(R.drawable.saveyel);
            star3.setImageResource(R.drawable.saveyel);
            star4.setImageResource(R.drawable.saveyel);
            star5.setImageResource(R.drawable.halfstar);
        } else if (num >= 5) {
            star1.setImageResource(R.drawable.saveyel);
            star2.setImageResource(R.drawable.saveyel);
            star3.setImageResource(R.drawable.saveyel);
            star4.setImageResource(R.drawable.saveyel);
            star5.setImageResource(R.drawable.saveyel);
        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.star1) {
            setStarNum(1);
            if (onStarClickListener != null) {
                onStarClickListener.onStarClick(1);
            }
        } else if (i == R.id.star2) {
            setStarNum(2);
            if (onStarClickListener != null) {
                onStarClickListener.onStarClick(2);
            }
        } else if (i == R.id.star3) {
            setStarNum(3);
            if (onStarClickListener != null) {
                onStarClickListener.onStarClick(3);
            }
        } else if (i == R.id.star4) {
            setStarNum(4);
            if (onStarClickListener != null) {
                onStarClickListener.onStarClick(4);
            }
        } else if (i == R.id.star5) {
            setStarNum(5);
            if (onStarClickListener != null) {
                onStarClickListener.onStarClick(5);
            }
        }
    }

    public interface onStarClickListener {
        void onStarClick(int score);
    }

    public void setOnStarClickListener(ZnzStarView.onStarClickListener onStarClickListener) {
        this.onStarClickListener = onStarClickListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
