package com.niyangup.taobaounion.presenter;

import com.niyangup.taobaounion.base.BasePresenter;
import com.niyangup.taobaounion.view.HomeCallback;

public interface HomePresenter extends BasePresenter<HomeCallback> {
    /**
     * 获取主页商品分类tab
     */
    void getCategory();
}
