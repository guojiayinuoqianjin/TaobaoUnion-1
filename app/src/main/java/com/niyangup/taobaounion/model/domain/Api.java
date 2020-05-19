package com.niyangup.taobaounion.model.domain;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("discovery/categories")
    Call<Category> getCategory();
}
