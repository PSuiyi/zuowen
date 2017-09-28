package com.znz.compass.znzlibray.views.preview;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.znz.compass.znzlibray.R;

import java.util.ArrayList;

/**
 * 图片预览
 */
public class PreviewActivity extends FragmentActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    private PhotoViewPager viewPager;
    private String imgUrls[];
    private ArrayList<Rect> rects;
    private ArrayList<PreviewFragment> fragments;
    private int index;
    private PhotoFragmentAdapter adapter;
    private ViewPagerIndicator indicator;
    private View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.preview_layout_photo);
        root = findViewById(R.id.root);
        viewPager = (PhotoViewPager) findViewById(R.id.viewpager);
        indicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        imgUrls = getIntent().getStringArrayExtra("imgUrls");
        rects = getIntent().getParcelableArrayListExtra("bounds");
        index = getIntent().getIntExtra("index", 0);
        if (rects == null || imgUrls == null) {
            finish();
        } else {
            adapter = new PhotoFragmentAdapter(getSupportFragmentManager());
            fragments = new ArrayList<>();
            int rectSize = rects.size();
            for (int i = 0; i < imgUrls.length; i++) {
                PreviewFragment fragment = new PreviewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("img", imgUrls[i]);
                if (i < rectSize) {
                    bundle.putParcelable("startBounds", rects.get(i));
                }
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }
            viewPager.setAdapter(adapter);
            viewPager.getViewTreeObserver().addOnGlobalLayoutListener(this);
            indicator.setViewPager(viewPager);
            indicator.refreshIndicator(fragments.size());
            viewPager.setCurrentItem(index);
        }
    }


    @Override
    public void onGlobalLayout() {
        PreviewFragment fragment = fragments.get(index);
        fragment.transformIn(status -> root.setBackgroundColor(Color.BLACK));
        viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    private class PhotoFragmentAdapter extends FragmentPagerAdapter {

        public PhotoFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            PreviewFragment fragment = fragments.get(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            transformOut();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void transformOut() {
        int currentIndex = viewPager.getCurrentItem();
        if (currentIndex < rects.size()) {
            PreviewFragment fragment = fragments.get(currentIndex);
            indicator.setVisibility(View.GONE);
            root.setBackgroundColor(Color.TRANSPARENT);
            fragment.transformOut(status -> {
                finish();
                PreviewActivity.this.overridePendingTransition(0, 0);
            });
        } else {
            finish();
            this.overridePendingTransition(0, 0);
        }
    }
}
