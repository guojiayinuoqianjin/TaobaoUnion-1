package com.niyangup.taobaounion.presenter.impl;

import com.niyangup.taobaounion.model.domain.Api;
import com.niyangup.taobaounion.model.domain.HomePageCentent;
import com.niyangup.taobaounion.presenter.CategoryPagePresenter;
import com.niyangup.taobaounion.utils.LogUtil;
import com.niyangup.taobaounion.utils.RetrofitManager;
import com.niyangup.taobaounion.utils.UrlUtil;
import com.niyangup.taobaounion.view.CategoryCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryPagePresenterImpl implements CategoryPagePresenter {

    private static final Integer DEFAULT_PAGE = 1;
    private Map<Integer, Integer> pageInfo = new HashMap<>();

    private CategoryPagePresenterImpl() {
    }

    private static CategoryPagePresenter sInstance = null;

    public static CategoryPagePresenter getInstance() {
        if (sInstance == null) {
            sInstance = new CategoryPagePresenterImpl();
        }
        return sInstance;
    }


    @Override
    public void getContentCategoryId(int categoryId) {

        for (CategoryCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {
                callback.onLoading(categoryId);
            }
        }

        Retrofit retrofit = RetrofitManager.getInstance().getmRetrofit();
        Api api = retrofit.create(Api.class);

        Integer targetPage = pageInfo.get(categoryId);
        if (targetPage == null) {
            targetPage = DEFAULT_PAGE;
            pageInfo.put(categoryId, targetPage);
        }

        Call<HomePageCentent> task = api.getHomePageContent(UrlUtil.createHomePageUrl(categoryId, targetPage));
        task.enqueue(new Callback<HomePageCentent>() {
            @Override
            public void onResponse(Call<HomePageCentent> call, Response<HomePageCentent> response) {
                LogUtil.d(this, "状态码:" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    handleHomePageContentResult(response.body(), categoryId);
                } else {
                    handleNetworkError(categoryId);
                }
            }


            @Override
            public void onFailure(Call<HomePageCentent> call, Throwable t) {
                LogUtil.d(this, "失败:" + t.toString());
                handleNetworkError(categoryId);
            }
        });

    }

    private void handleNetworkError(int categoryId) {
        for (CategoryCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {
                callback.onError(categoryId);
            }
        }
    }

    private void handleHomePageContentResult(HomePageCentent body, int categoryId) {
        for (CategoryCallback callback : callbacks) {

            if (callback.getCategoryId() == categoryId) {
                if (body == null || body.getData().size() <= 0) {
                    callback.onEmpty(categoryId);
                } else {
                    callback.onContentLoaded(body.getData(), categoryId);
                }
            }
        }
    }

    @Override
    public void loadMore(int categoryId, int page) {

    }

    @Override
    public void reload(int categoryId) {

    }

    private List<CategoryCallback> callbacks = new ArrayList<>();

    @Override
    public void registerViewCallback(CategoryCallback callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);
        }

    }

    @Override
    public void unregisterViewCallback(CategoryCallback callback) {
        callbacks.remove(callback);
    }
}
