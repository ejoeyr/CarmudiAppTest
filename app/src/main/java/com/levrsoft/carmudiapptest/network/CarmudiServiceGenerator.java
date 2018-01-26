package com.levrsoft.carmudiapptest.network;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by joeyramirez on 1/25/18.
 */

public class CarmudiServiceGenerator {



    //Base URL
    public static final String API_BASE_URL = "https://www.carmudi.co.id/api/";

    //OKHttp
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    //Retrofit
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    //.addCallAdapterFactory(rxAdapter)
                    .addConverterFactory(GsonConverterFactory.create());


    //Service generator
    public static <S> S createService(Class<S> serviceClass) {

        return createService(serviceClass, null, null);
    }

    /*Create service with Auth header*/
    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        if (username != null && password != null) {

            final String basic = Credentials.basic(username, password);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basic)
                            .header("Accept", "application/json")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }


}
