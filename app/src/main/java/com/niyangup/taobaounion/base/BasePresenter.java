package com.niyangup.taobaounion.base;


public interface BasePresenter<T> {

    void registerViewCallback(T callback);

    void unregisterViewCallback(T callback);
}
