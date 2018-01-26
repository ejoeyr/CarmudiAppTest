package com.levrsoft.carmudiapptest.main.presenter;

import android.util.Log;

import com.levrsoft.carmudiapptest.main.model.CarmudiResponse;
import com.levrsoft.carmudiapptest.main.view.MainView;
import com.levrsoft.carmudiapptest.network.CarmudiInterface;
import com.levrsoft.carmudiapptest.network.CarmudiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by joeyramirez on 1/25/18.
 */

public class MainPresenter {


    private MainView mMainView;
    private CarmudiInterface mCarmudiInterface;

    public MainPresenter() {
    }


    public MainPresenter(MainView mainView) {
        this.mMainView = mainView;

        //initialize carmudi network interface

        mCarmudiInterface =
                CarmudiServiceGenerator.createService(CarmudiInterface.class);
    }


    //Loads data from the web service and call's
    public void requestCarmudiData(int page, int items, String sortBy) {

        //Display the loading bar
        mMainView.onCarmudiDataRequest();

        //Asynchronous request
        mCarmudiInterface.getCarmudiDataSorted(page, items,sortBy).enqueue(new Callback<CarmudiResponse>() {
            @Override
            public void onResponse(Call<CarmudiResponse> call, Response<CarmudiResponse> response) {


                CarmudiResponse carmudiResponse = null;

                try {
                    Log.v("Response",response.raw().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    carmudiResponse = response.body();

                } catch (Exception e) {

                    Log.v("Carmudi Response Eror", e.getMessage());
                }

                mMainView.onCarmudiDataRequestFinished(response.isSuccessful(),
                        response.message(),
                        carmudiResponse);


            }

            @Override
            public void onFailure(Call<CarmudiResponse> call, Throwable t) {


                mMainView.onCarmudiDataRequestFinished(false,
                        t.getMessage(),
                        null);
            }
        });


    }




}
