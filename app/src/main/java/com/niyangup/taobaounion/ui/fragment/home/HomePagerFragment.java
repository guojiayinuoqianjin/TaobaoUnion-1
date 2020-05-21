package com.niyangup.taobaounion.ui.fragment.home;

import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.niyangup.taobaounion.R;
import com.niyangup.taobaounion.base.BaseFragment;
import com.niyangup.taobaounion.model.domain.Category;
import com.niyangup.taobaounion.model.domain.HomePageCentent;
import com.niyangup.taobaounion.presenter.CategoryPagePresenter;
import com.niyangup.taobaounion.presenter.impl.CategoryPagePresenterImpl;
import com.niyangup.taobaounion.ui.adapter.HomePageContentAdapter;
import com.niyangup.taobaounion.ui.adapter.LoopPagerAdapter;
import com.niyangup.taobaounion.utils.Constants;
import com.niyangup.taobaounion.utils.LogUtil;
import com.niyangup.taobaounion.utils.SizeUtil;
import com.niyangup.taobaounion.utils.ToastUtil;
import com.niyangup.taobaounion.view.CategoryCallback;

import java.util.List;

import butterknife.BindView;

public class HomePagerFragment extends BaseFragment implements CategoryCallback {

    private CategoryPagePresenter mPresenter;
    private int materialId;

    @BindView(R.id.recycle_view)
    public RecyclerView mContentList;

    private HomePageContentAdapter mHomePageContentAdapter;

    @BindView(R.id.banner_page)
    public ViewPager mViewPager;

    @BindView(R.id.ll_loop_indicator)
    public LinearLayout mLoopIndicator;

    @BindView(R.id.home_page_refresh)
    public TwinklingRefreshLayout twinklingRefreshLayout;

    @BindView(R.id.tv_home_pager_title)
    public TextView tvCategoryTitle;

    //轮播图适配器
    private LoopPagerAdapter loopPagerAdapter;

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
    protected void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (loopPagerAdapter.getDataSize() == 0) {
                    return;
                }

                int targetPostition = position % loopPagerAdapter.getDataSize();
                LogUtil.d(this, "targetPostition" + targetPostition);
                //切换指示器
                updateLooperIndicater(targetPostition);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                LogUtil.d(this, "loadMore触发");
                if (mPresenter != null) {
                    mPresenter.loadMore(materialId);
                }
            }
        });

    }

    private void updateLooperIndicater(int targetPostition) {
        for (int i = 0; i < mLoopIndicator.getChildCount(); i++) {
            View point = mLoopIndicator.getChildAt(i);

            if (i == targetPostition) {
                point.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            } else {
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }
        }

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

        loopPagerAdapter = new LoopPagerAdapter();
        mViewPager.setAdapter(loopPagerAdapter);

        twinklingRefreshLayout.setEnableRefresh(false);
        twinklingRefreshLayout.setEnableLoadmore(true);
        twinklingRefreshLayout.setBottomView(new LoadingView(getContext()));
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
        if (tvCategoryTitle != null) {
            tvCategoryTitle.setText(title);
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
        if (twinklingRefreshLayout != null) {
            twinklingRefreshLayout.finishLoadmore();
        }
        ToastUtil.showToast("网络异常,请稍后重试");
    }

    @Override
    public void onLoadMoreEmpty() {
        if (twinklingRefreshLayout != null) {
            twinklingRefreshLayout.finishLoadmore();
        }
        ToastUtil.showToast("没有更多数据了");
    }

    @Override
    public void onLoadMoreLoaded(List<HomePageCentent.DataBean> contents) {
        mHomePageContentAdapter.addData(contents);
        if (twinklingRefreshLayout != null) {
            twinklingRefreshLayout.finishLoadmore();
        }
        ToastUtil.showToast("新增了" + contents.size() + "条数据");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onLooperListLoaded(List<HomePageCentent.DataBean> contents) {
        LogUtil.d(this, "data:" + contents);
        if (loopPagerAdapter != null) {
            loopPagerAdapter.setData(contents);
        }

        int dx = (Integer.MAX_VALUE / 2) % contents.size();
        int targetPosition = (Integer.MAX_VALUE / 2) - dx;
        mViewPager.setCurrentItem(targetPosition);
        mLoopIndicator.removeAllViews();
        for (int i = 0; i < contents.size(); i++) {
            View point = new View(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(SizeUtil.dip2px(getContext(), 8), SizeUtil.dip2px(getContext(), 8));
            params.leftMargin = SizeUtil.dip2px(getContext(), 5);
            params.rightMargin = SizeUtil.dip2px(getContext(), 5);
            point.setLayoutParams(params);
            if (i == 0) {
                point.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            } else {
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }

            mLoopIndicator.addView(point);
        }

    }
}



















