package com.niyangup.taobaounion.model.domain;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {

    @GET("discovery/categories")
    Call<Category> getCategory();

    //("/discovery/{materialId}/{page}")
    @GET
    Call<HomePageCentent> getHomePageContent(@Url String url);
}
