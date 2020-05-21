package com.niyangup.taobaounion.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.niyangup.taobaounion.model.domain.Category;
import com.niyangup.taobaounion.ui.fragment.home.HomePagerFragment;
import com.niyangup.taobaounion.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Category.DataBean> categoryList = new ArrayList<>();

    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryList.get(position).getTitle();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        LogUtil.d(this, "getItem:" + position);
        return HomePagerFragment.newInstance(categoryList.get(position));
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    public void setCategory(Category category) {
        categoryList.clear();
        categoryList.addAll(category.getData());
        notifyDataSetChanged();
    }
}







