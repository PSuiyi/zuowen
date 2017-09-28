package com.znz.compass.znzlibray.views.gallery.activity;

import android.animation.Animator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.base.BaseActivity;
import com.znz.compass.znzlibray.utils.ViewHolder;
import com.znz.compass.znzlibray.views.HackyViewPager;
import com.znz.compass.znzlibray.views.imageloder.HttpImageView;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片放大展示
 */
public class ImageShowActivity extends BaseActivity implements View.OnClickListener {
    private HackyViewPager mViewPager;
    private ImageViewPagerAdapter adapter;
    private TextView tv_page_number;

    private LinearLayout llParent;
    // 持有一个当前animator的引用,
    // 以后以便于中途取消动画.
    private Animator mCurrentAnimator;
    //这个系统内的“短”动画时长是以毫秒为单位的。
    //这个时长对于精确控制的动画或频繁激发的动画是非常理想的。
    private int mShortAnimationDuration;

    private List<HttpImageView> httpImageViewList = new ArrayList<HttpImageView>();
    private float startScale;

    /**
     * 图片承载容器
     */
    private View container;
    private int current_position = 0; // 当前viewpager索引(不管初始化进来是第几张页面，都是0，之后在这基础上加减)

    /**
     * 上个页面传递过来的
     */
    private List<String> imageList = new ArrayList<String>();// 大图片url
    private int position = 0; // 当前应该显示第几张图片

    /**
     * 开源框架photoview
     */
    private List<PhotoViewAttacher> attacherList = new ArrayList<PhotoViewAttacher>();

    private long current_click_time;  // 用来判断单机事件
    private TextView tv_save;

    @Override
    protected int[] getLayoutResource() {
        return new int[]{R.layout.image_show_layout, 1};
    }

    @Override
    protected void initializeVariate() {
        imageList = (List<String>) getIntent().getSerializableExtra("image_urls");
        position = getIntent().getIntExtra("position", 0);
    }

    @Override
    protected void initializeNavigation() {
        setTitleName("图片预览");
    }

    @Override
    protected void initializeAppBusiness() {

    }

    @Override
    protected void initializeView() {
        //获取并缓存系统默认定义的“短”动画时长
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        container = ViewHolder.init(this, R.id.container);
        mViewPager = ViewHolder.init(this, R.id.mViewPager);
        tv_page_number = ViewHolder.init(this, R.id.tv_page_number);
        mViewPager.setOffscreenPageLimit(8);
        adapter = new ImageViewPagerAdapter(httpImageViewList);
        mViewPager.setAdapter(adapter);

        tv_save = ViewHolder.init(this, R.id.tv_save);
        tv_save.setOnClickListener(this);
        llParent = ViewHolder.init(this, R.id.llParent);
        llParent.setOnClickListener(this);

        initializeViewPager();
    }

    @Override
    protected void loadDataFromServer() {

    }

    /**
     * 初始化大图列表滑动块
     */
    private void initializeViewPager() {
        for (int i = 0; i < imageList.size(); i++) {
            HttpImageView httpImageView = new HttpImageView(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            httpImageView.setLayoutParams(layoutParams);
            httpImageView.loadHttpImagePreview(imageList.get(i));

            // 设置锚点，以放大后的View左上角坐标为准来准备 SCALE_X 和 SCALE_Y 变换
            // (默认为View的中心)
            httpImageView.setPivotX(0f);
            httpImageView.setPivotY(0f);

            httpImageViewList.add(httpImageView);
        }
        adapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(position);
        mViewPager.setClipChildren(false);
        current_position = 0;

        tv_page_number.setText(position + 1 + "/" + imageList.size());
//        setTitleName(position + 1 + "/" + imageList.size());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                if (i < position) {
                    current_position++;
                } else if (i > position) {
                    current_position--;
                }
                position = i;
                tv_page_number.setText((i + 1) + "/" + imageList.size());
//                setTitleName((i + 1) + "/" + imageList.size());
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }


    @Override
    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.tv_save) {
//            Bitmap bitmap = ((BitmapDrawable) httpImageViewList.get(position).getDrawable()).getBitmap();
//            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "description");
//            ZnzLog.d("保存图片到本地 " + position);
//            DataManager.getInstance(this).showToast("保存成功");
//
//        } else if (i == R.id.llParent) {
//            finish();
//
//        } else {
//        }
        finish();
    }

    /**
     * *********************  Image ViewPager 适配器  *****************************************
     */
    public class ImageViewPagerAdapter extends PagerAdapter {
        private List<HttpImageView> httpImageViewList;

        public ImageViewPagerAdapter(List<HttpImageView> httpImageViewList) {
            this.httpImageViewList = httpImageViewList;
        }

        @Override
        public int getCount() {
            return httpImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(httpImageViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(httpImageViewList.get(position));
            httpImageViewList.get(position).setTag(position);
            PhotoViewAttacher attacher2 = new PhotoViewAttacher(httpImageViewList.get(position));
            attacher2.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }
            });
            attacher2.update();
            httpImageViewList.get(position).setOnTouchListener((v, event) -> {
                Integer index = (Integer) v.getTag();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        current_click_time = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - current_click_time < 300) {
                        } else {
                            httpImageViewList.get(index).setScaleType(ImageView.ScaleType.MATRIX);
                            PhotoViewAttacher attacher = new PhotoViewAttacher(httpImageViewList.get(index));
                            attacher.setOnPhotoTapListener((view, x, y) -> {
                            });
                            attacher.update();
                        }
                        break;
                    default:
                        httpImageViewList.get(index).setScaleType(ImageView.ScaleType.MATRIX);
                        PhotoViewAttacher attacher = new PhotoViewAttacher(httpImageViewList.get(index));
                        attacher.setOnPhotoTapListener((view, x, y) -> {
                        });
                        attacher.update();
                        break;
                }
                return true;
            });
            return httpImageViewList.get(position);
        }
    }
}
