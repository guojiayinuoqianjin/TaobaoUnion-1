package com.niyangup.taobaounion.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.niyangup.taobaounion.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private Unbinder mBind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getResId(), container, false);
        mBind = ButterKnife.bind(this, view);
        initView(view);
        initPresent();
        loadData();
        return view;
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
