package com.niyangup.taobaounion.model;

import com.niyangup.taobaounion.model.domain.Category;
import com.niyangup.taobaounion.model.domain.HomePageCentent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {

    @GET("discovery/categories")
    Call<Category> getCategory();

    @GET
    Call<HomePageCentent> getHomePageContent(@Url String url);
}
