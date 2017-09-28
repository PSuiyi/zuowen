package com.znz.compass.znzlibray.views.nine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.common.DataManager;
import com.znz.compass.znzlibray.utils.DipUtil;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;
import com.znz.compass.znzlibray.views.preview.PreviewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 九宫格图片
 */

public class NineGridView extends LinearLayout {
    public static int MAX_WIDTH = 0;

    // 照片的Url列表
    private List<String> imagesList;
    private Context context;

    /**
     * 长度 单位为Pixel
     **/
    private int pxOneMaxWandH;  // 单张图最大允许宽高
    private int pxMoreWandH = 0;// 多张图的宽高
    private int pxImagePadding = DipUtil.dip2px(getContext(), 3);// 图片间的间距

    private int MAX_PER_ROW_COUNT = 3;// 每行显示最大数

    private LayoutParams onePicPara;
    private LayoutParams morePara, moreParaColumnFirst;
    private LayoutParams rowPara;
    private boolean enablePreview;
    private boolean enableOneMinWidth;

    public NineGridView(Context context) {
        super(context);
        this.context = context;
    }

    public NineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NineGridView);
        enablePreview = typedArray.getBoolean(R.styleable.NineGridView_nine_enable_preview, true);
        enableOneMinWidth = typedArray.getBoolean(R.styleable.NineGridView_nine_enable_one_min_width, false);
        typedArray.recycle();
    }

    public void setList(List<String> lists) throws IllegalArgumentException {
        if (lists == null) {
            throw new IllegalArgumentException("imageList is null...");
        }
        imagesList = lists;

        pxMoreWandH = (MAX_WIDTH - pxImagePadding * 2) / 3; //解决右侧图片和内容对不齐问题
        pxOneMaxWandH = MAX_WIDTH * 2 / 3;
        initImageLayoutParams();

        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        MAX_WIDTH = width;
        if (imagesList != null && imagesList.size() > 0) {
            setList(imagesList);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            // result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
            // + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private void initImageLayoutParams() {
        int wrap = LayoutParams.WRAP_CONTENT;
        int match = LayoutParams.MATCH_PARENT;

        onePicPara = new LayoutParams(wrap, wrap);

        moreParaColumnFirst = new LayoutParams(pxMoreWandH, pxMoreWandH);
        morePara = new LayoutParams(pxMoreWandH, pxMoreWandH);
        morePara.setMargins(pxImagePadding, 0, 0, 0);

        rowPara = new LayoutParams(match, wrap);
    }

    // 根据imageView的数量初始化不同的View布局,还要为每一个View作点击效果
    private void initView() {
        this.setOrientation(VERTICAL);
        this.removeAllViews();
        if (MAX_WIDTH == 0) {
            //为了触发onMeasure()来测量MultiImageView的最大宽度，MultiImageView的宽设置为match_parent
            addView(new View(getContext()));
            return;
        }

        if (imagesList == null || imagesList.size() == 0) {
            return;
        }

        if (imagesList.size() == 1) {
            addView(createImageView(0, enableOneMinWidth));
        } else {
            int allCount = imagesList.size();
            if (allCount == 4) {
                MAX_PER_ROW_COUNT = 2;
            } else {
                MAX_PER_ROW_COUNT = 3;
            }
            int rowCount = allCount / MAX_PER_ROW_COUNT
                    + (allCount % MAX_PER_ROW_COUNT > 0 ? 1 : 0);// 行数
            for (int rowCursor = 0; rowCursor < rowCount; rowCursor++) {
                LinearLayout rowLayout = new LinearLayout(getContext());
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);

                rowLayout.setLayoutParams(rowPara);
                if (rowCursor != 0) {
                    rowLayout.setPadding(0, pxImagePadding, 0, 0);
                }

                int columnCount = allCount % MAX_PER_ROW_COUNT == 0 ? MAX_PER_ROW_COUNT
                        : allCount % MAX_PER_ROW_COUNT;//每行的列数
                if (rowCursor != rowCount - 1) {
                    columnCount = MAX_PER_ROW_COUNT;
                }
                addView(rowLayout);

                int rowOffset = rowCursor * MAX_PER_ROW_COUNT;// 行偏移
                for (int columnCursor = 0; columnCursor < columnCount; columnCursor++) {
                    int position = columnCursor + rowOffset;
                    rowLayout.addView(createImageView(position, true));
                }
            }
        }
    }

    private ImageView createImageView(int position, final boolean isMultiImage) {
        String url = imagesList.get(position);
        HttpImageView imageView;
        if (enablePreview) {
            imageView = new ColorFilterImageView(getContext());
        } else {
            imageView = new HttpImageView(getContext());
        }

        if (isMultiImage) {
            imageView.setScaleType(ScaleType.CENTER_CROP);
            imageView.setLayoutParams(position % MAX_PER_ROW_COUNT == 0 ? moreParaColumnFirst : morePara);
        } else {
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ScaleType.CENTER_INSIDE);
            //imageView.setMaxHeight(pxOneMaxWandH);

            int expectW = 0;
            int expectH = 0;

            if (expectW == 0 || expectH == 0) {
                imageView.setLayoutParams(onePicPara);
            } else {
                int actualW = 0;
                int actualH = 0;
                float scale = ((float) expectH) / ((float) expectW);
                if (expectW > pxOneMaxWandH) {
                    actualW = pxOneMaxWandH;
                    actualH = (int) (actualW * scale);
                } else if (expectW < pxMoreWandH) {
                    actualW = pxMoreWandH;
                    actualH = (int) (actualW * scale);
                } else {
                    actualW = expectW;
                    actualH = expectH;
                }
                imageView.setLayoutParams(new LayoutParams(actualW, actualH));
            }
        }

        imageView.setId(url.hashCode());

        if (enablePreview) {
            //跳转至浏览界面
            imageView.setOnClickListener(v -> {
                if (imagesList.size() == 1) {
                    DataManager.getInstance(context).showImagePreviewSingle(context, imagesList.get(position), v);
                } else {
                    //这是是九宫格图片才会出现的情况,有双层父布局
                    ViewGroup root = (ViewGroup) v.getParent().getParent();
                    int rootCount = root.getChildCount();
                    ArrayList<Rect> rects = new ArrayList<>();
                    for (int i = 0; i < rootCount; i++) {
                        ViewGroup parent = (ViewGroup) root.getChildAt(i);
                        int childCount = parent.getChildCount();
                        for (int j = 0; j < childCount; j++) {
                            Rect rect = new Rect();
                            View child = parent.getChildAt(j);
                            child.getGlobalVisibleRect(rect);
                            rects.add(rect);
                        }
                    }

                    Intent intent = new Intent(context, PreviewActivity.class);
                    String imageArray[] = new String[imagesList.size()];
                    for (int i = 0; i < imageArray.length; i++) {
                        imageArray[i] = imagesList.get(i);
                    }
                    intent.putExtra("imgUrls", imageArray);
                    intent.putExtra("index", position);
                    intent.putExtra("bounds", rects);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(0, 0);
                }
            });
        }

        imageView.setBackgroundColor(getResources().getColor(R.color.text_gray));
        imageView.loadHttpImagePreview(url);
        return imageView;
    }
}