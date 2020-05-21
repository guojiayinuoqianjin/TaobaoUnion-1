package com.niyangup.taobaounion.ui.fragment.home;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.niyangup.taobaounion.R;
import com.niyangup.taobaounion.base.BaseFragment;
import com.niyangup.taobaounion.model.domain.Category;
import com.niyangup.taobaounion.model.domain.HomePageCentent;
import com.niyangup.taobaounion.presenter.CategoryPagePresenter;
import com.niyangup.taobaounion.presenter.impl.CategoryPagePresenterImpl;
import com.niyangup.taobaounion.ui.adapter.HomePageContentAdapter;
import com.niyangup.taobaounion.utils.Constants;
import com.niyangup.taobaounion.utils.LogUtil;
import com.niyangup.taobaounion.view.CategoryCallback;

import java.util.List;

import butterknife.BindView;

public class HomePagerFragment extends BaseFragment implements CategoryCallback {

    private CategoryPagePresenter mPresenter;
    private int materialId;

    @BindView(R.id.recycle_view)
    public RecyclerView mContentList;
    private HomePageContentAdapter mHomePageContentAdapter;

    public static HomePagerFragment newInstance(Category.DataBean category) {
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGE_TITLE, category.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGE_MATERIAL_ID, category.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @Override
    protected void initPresent() {
        mPresenter = CategoryPagePresenterImpl.getInstance();
        mPresenter.registerViewCallback(this);
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void initView(View rootView) {
        mContentList.setLayoutManager(new LinearLayoutManager(getContext()));
        mHomePageContentAdapter = new HomePageContentAdapter();
        mContentList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 5;
                outRect.bottom = 5;
            }
        });
        mContentList.setAdapter(mHomePageContentAdapter);

//        setUpState(State.SUCCESS);
    }

    @Override
    protected void loadData() {
        Bundle bundle = getArguments();
        String title = bundle.getString(Constants.KEY_HOME_PAGE_TITLE);
        materialId = bundle.getInt(Constants.KEY_HOME_PAGE_MATERIAL_ID);
        LogUtil.d(this, "title:" + title);
        LogUtil.d(this, "id:" + materialId);

        if (mPresenter != null) {
            mPresenter.getContentCategoryId(materialId);
        }
    }

    @Override
    protected void release() {
        super.release();
        if (mPresenter != null) {
            mPresenter.unregisterViewCallback(this);
        }
    }

    @Override
    public void onContentLoaded(List<HomePageCentent.DataBean> contents, int categoryId) {
        if (materialId != categoryId) {
            return;
        }

        mHomePageContentAdapter.setData(contents);
        setUpState(State.SUCCESS);

    }

    @Override
    public int getCategoryId() {
        return materialId;
    }

    @Override
    public void onLoading(int categoryId) {
        setUpState(State.LOADING);
    }

    @Override
    public void onError(int categoryId) {
        setUpState(State.ERROR);
    }

    @Override
    public void onEmpty(int categoryId) {
        setUpState(State.EMPTY);
    }

    @Override
    public void onLoadMoreError() {

    }

    @Override
    public void onLoadMoreEmpty() {

    }

    @Override
    public void onLoadMoreLoaded(List<HomePageCentent.DataBean> contents) {

    }

    @Override
    public void onLooperListLoaded(List<HomePageCentent.DataBean> contents) {

    }
}



















