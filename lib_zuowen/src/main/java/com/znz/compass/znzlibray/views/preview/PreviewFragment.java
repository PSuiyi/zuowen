package com.znz.compass.znzlibray.views.preview;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.views.CircularProgress;

/**
 * 图片预览类
 */
public class PreviewFragment extends Fragment {

    private SmoothImageView ivPhoto;
    private CircularProgress progressBar;
    private Rect startBounds;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.preview_fragment_photo, null);
        activity = getActivity();
        ivPhoto = (SmoothImageView) rootView.findViewById(R.id.iv_photo);
        ivPhoto.setMinimumScale(0.5f);
        ivPhoto.setOnViewTapListener((view, x, y) -> ((PreviewActivity) activity).transformOut());
        progressBar = (CircularProgress) rootView.findViewById(R.id.progress);
        Bundle args = getArguments();
        if (args != null && args.containsKey("img")) {
            String imgUrl = args.getString("img");
            startBounds = args.getParcelable("startBounds");
            Glide.with(getContext()).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivPhoto);
        }
        return rootView;
    }


    public void transformIn(SmoothImageView.onTransformListener listener) {
        ivPhoto.transformIn(startBounds, listener);
    }

    public void transformOut(SmoothImageView.onTransformListener listener) {
        ivPhoto.transformOut(startBounds, listener);
        progressBar.setVisibility(View.GONE);
    }
}
