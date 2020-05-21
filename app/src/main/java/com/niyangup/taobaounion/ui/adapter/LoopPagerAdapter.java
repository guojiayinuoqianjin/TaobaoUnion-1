package com.niyangup.taobaounion.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.niyangup.taobaounion.model.domain.HomePageCentent;
import com.niyangup.taobaounion.utils.LogUtil;
import com.niyangup.taobaounion.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

public class LoopPagerAdapter extends PagerAdapter {
    private List<HomePageCentent.DataBean> mData = new ArrayList<>();

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    public int getDataSize() {
        return mData.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //处理越界问题
        int index = position % mData.size();
        LogUtil.d(this, "index:" + index);

        HomePageCentent.DataBean dataBean = mData.get(index);

        int width = container.getMeasuredWidth();
        int height = container.getMeasuredHeight();
        int finalSize = (width > height ? width : height) / 2;
        LogUtil.d(this, "size:" + finalSize);

        String coverPath = UrlUtil.getCoverPath(dataBean.getPict_url(), finalSize);

        ImageView imageView = new ImageView(container.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(container.getContext()).load(coverPath).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setData(List<HomePageCentent.DataBean> contents) {
        mData.clear();
        mData.addAll(contents);
        notifyDataSetChanged();
    }
}
