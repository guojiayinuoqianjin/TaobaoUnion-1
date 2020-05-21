package com.niyangup.taobaounion.presenter;

import com.niyangup.taobaounion.base.BasePresenter;
import com.niyangup.taobaounion.view.CategoryCallback;

public interface CategoryPagePresenter extends BasePresenter<CategoryCallback> {

    void getContentCategoryId(int categoryId);

    void loadMore(int categoryId, int page);

    void reload(int categoryId);
}
