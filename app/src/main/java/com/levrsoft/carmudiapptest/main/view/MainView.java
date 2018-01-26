package com.levrsoft.carmudiapptest.main.view;

import com.levrsoft.carmudiapptest.main.model.CarmudiResponse;

/**
 * Created by joeyramirez on 1/25/18.
 */

public interface MainView {

     void onCarmudiDataRequest();


     void onCarmudiDataRequestFinished(boolean isSuccessful,
                                       String responseMessage,
                                       CarmudiResponse carmudiResponse);


}
