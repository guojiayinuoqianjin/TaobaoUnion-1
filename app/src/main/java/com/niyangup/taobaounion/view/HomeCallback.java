package com.niyangup.taobaounion.view;

import com.niyangup.taobaounion.base.BaseCallback;
import com.niyangup.taobaounion.model.domain.Category;

public interface HomeCallback extends BaseCallback {

    void onCategoryLoaded(Category category);
}
