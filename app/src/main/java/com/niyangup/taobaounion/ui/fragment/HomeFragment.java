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
        mHomePresenter.registerViewCallback(this);
    }

    @Override
    protected void loadData() {
        mHomePresenter.getCategory();
    }

    @Override
    public void onCategoryLoaded(Category category) {
        setUpState(State.SUCCESS);
        if (homePagerAdapter != null) {
            homePagerAdapter.setCategory(category);
        }
    }

    @Override
    public void onLoading() {
        setUpState(State.LOADING);
    }

    @Override
    public void onNetworkError() {
        setUpState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        setUpState(State.EMPTY);
    }

    @Override
    protected void release() {
        if (mHomePresenter != null) {
            mHomePresenter.unregisterViewCallback(this);
        }
    }

    @Override
    protected void onRetryClick() {
        //网络错误，重新加载
        if (mHomePresenter != null) {
            mHomePresenter.getCategory();
        }
    }
}
















