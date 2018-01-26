package com.levrsoft.carmudiapptest.network;

import com.levrsoft.carmudiapptest.main.model.CarmudiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by joeyramirez on 1/25/18.
 */

public interface CarmudiInterface {



    @GET("cars")
    Call<CarmudiResponse> getCarmudiData(@Query("page") int page,
                                         @Query("maxitems") int maxItems);

    @GET("cars")
    Call<CarmudiResponse> getCarmudiDataSorted(@Query("page") int page,
                                               @Query("maxitems") int maxItems,
                                               @Query("sort") String sortBy);



}
