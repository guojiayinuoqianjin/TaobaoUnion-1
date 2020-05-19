package com.niyangup.taobaounion.presenter;

import com.niyangup.taobaounion.view.HomeCallback;

public interface HomePresenter {
    /**
     * 获取主页商品分类tab
     */
    void getCategory();

    void registerCallback(HomeCallback callback);

    void unregisterCallback(HomeCallback callback);
}
