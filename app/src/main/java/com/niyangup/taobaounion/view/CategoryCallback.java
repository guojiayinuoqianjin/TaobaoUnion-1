package com.niyangup.taobaounion.view;

import com.niyangup.taobaounion.model.domain.HomePageCentent;

import java.util.List;

public interface CategoryCallback {

    void onContentLoaded(List<HomePageCentent.DataBean> contents, int categoryId);

    int getCategoryId();

    void onLoading(int categoryId);

    void onError(int categoryId);

    void onEmpty(int categoryId);

    void onLoadMoreError();

    void onLoadMoreEmpty();

    void onLoadMoreLoaded(List<HomePageCentent.DataBean> contents);

    void onLooperListLoaded(List<HomePageCentent.DataBean> contents);

}
