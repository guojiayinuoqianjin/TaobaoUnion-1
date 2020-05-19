package com.niyangup.taobaounion.ui.fragment;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.niyangup.taobaounion.R;
import com.niyangup.taobaounion.base.BaseFragment;
import com.niyangup.taobaounion.model.domain.Category;
import com.niyangup.taobaounion.presenter.HomePresenter;
import com.niyangup.taobaounion.presenter.impl.HomePresenterImpl;
import com.niyangup.taobaounion.ui.adapter.HomePagerAdapter;
import com.niyangup.taobaounion.view.HomeCallback;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements HomeCallback {


    @BindView(R.id.home_tab_layout)
    public TabLayout mTabLayout;

    @BindView(R.id.home_pager)
    public ViewPager mViewPager;


    private HomePresenter mHomePresenter;
    HomePagerAdapter homePagerAdapter;

    @Override
    protected int getResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mTabLayout.setupWithViewPager(mViewPager);
        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(homePagerAdapter);
    }

    @Override
    protected void initPresent() {
        mHomePresenter = new HomePresenterImpl();
        mHomePresenter.registerCallback(this);
    }

    @Override
    protected void loadData() {
        mHomePresenter.getCategory();
    }

    @Override
    public void onCategoryLoaded(Category category) {
        if (homePagerAdapter != null) {
            homePagerAdapter.setCategory(category);
        }
    }

    @Override
    protected void release() {
        if (mHomePresenter != null) {
            mHomePresenter.unregisterCallback(this);
        }
    }


}
















