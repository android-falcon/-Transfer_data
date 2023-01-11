package com.hiaryabeer.transferapp.Models;



import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitInstance {
    public static OkHttpClient client = new OkHttpClient().newBuilder()
            .readTimeout(
                    30, TimeUnit.MINUTES)
            .connectTimeout(30, TimeUnit.MINUTES).build();
    private static Retrofit ourInstance;
//    static String BASEURL="http://46.185.161.254:8085/Falcons/VAN.dll/";

    public static Retrofit getInstance(String BASE_URL) {

        if (ourInstance == null)
            ourInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();

        Log.e("ourInstance",""+ourInstance.toString());
        return ourInstance;
    }

    //
    private RetrofitInstance() {

    }
}
