package com.niyangup.taobaounion.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.niyangup.taobaounion.R;
import com.niyangup.taobaounion.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private State currentState = null;
    private View emptyView;
    private View errorView;
    private View loadingView;
    private View successView;

    public enum State {
        NONE, LOADING, SUCCESS, ERROR, EMPTY
    }

    private Unbinder mBind;
    private FrameLayout mBaseContainer;

    @OnClick(R.id.network_reload)
    public void retry() {
        //重新加载内容
        LogUtil.d(this, "重新加载");

        onRetryClick();
    }

    protected void onRetryClick() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View baseView = inflater.inflate(R.layout.base_fragment_layout, container, false);
        mBaseContainer = baseView.findViewById(R.id.base_container);
        loadStatesView(inflater, container);

        mBind = ButterKnife.bind(this, baseView);
        initView(baseView);
        initPresent();
        initListener();
        loadData();
        return baseView;
    }

    protected void initListener() {
    }

    /**
     * 加载各种布局
     *
     * @param inflater
     * @param container
     */
    private void loadStatesView(LayoutInflater inflater, ViewGroup container) {
        //成功的view
        successView = inflater.inflate(getResId(), container, false);
        mBaseContainer.addView(successView);
        LogUtil.d(this, "--success--");

        //加载loading
        loadingView = inflater.inflate(R.layout.fragment_loading_view, container, false);
        mBaseContainer.addView(loadingView);

        //错误
        errorView = inflater.inflate(R.layout.fragment_error_view, container, false);
        mBaseContainer.addView(errorView);

        //空页面
        emptyView = inflater.inflate(R.layout.fragment_empty_view, container, false);
        mBaseContainer.addView(emptyView);

        setUpState(State.NONE);

    }

    public void setUpState(State state) {
        this.currentState = state;

        errorView.setVisibility(currentState == State.ERROR ? View.VISIBLE : View.GONE);
        successView.setVisibility(currentState == State.SUCCESS ? View.VISIBLE : View.GONE);
        loadingView.setVisibility(currentState == State.LOADING ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(currentState == State.EMPTY ? View.VISIBLE : View.GONE);
    }


    protected void initView(View rootView) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        release();
        if (mBind != null) {
            mBind.unbind();
        }
    }

    /**
     * 释放资源
     */
    protected void release() {

    }

    protected void initPresent() {
    }

    protected void loadData() {
    }

    protected abstract int getResId();

}
