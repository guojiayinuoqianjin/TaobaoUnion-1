package com.niyangup.taobaounion.presenter.impl;

import com.niyangup.taobaounion.model.domain.Api;
import com.niyangup.taobaounion.model.domain.Category;
import com.niyangup.taobaounion.presenter.HomePresenter;
import com.niyangup.taobaounion.utils.LogUtil;
import com.niyangup.taobaounion.utils.RetrofitManager;
import com.niyangup.taobaounion.view.HomeCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePresenterImpl implements HomePresenter {
    private HomeCallback mCallback;

    @Override
    public void getCategory() {
        Retrofit retrofit = RetrofitManager.getInstance().getmRetrofit();
        Call<Category> task = retrofit.create(Api.class).getCategory();
        task.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                //成功
                LogUtil.i(this, "状态码:" + response.code());
                if (response.code() == 200) {
                    Category category = response.body();
//                    LogUtil.i(this, category.toString());
                    if (mCallback != null) {
                        mCallback.onCategoryLoaded(category);
                    }
                } else {
                    LogUtil.i(this, "请求失败..." + response.message());
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                //失败
                LogUtil.d(HomePresenterImpl.class, "error" + t.getMessage());
            }
        });


    }

    @Override
    public void registerCallback(HomeCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void unregisterCallback(HomeCallback callback) {
        mCallback = null;
    }
}
