package com.starostinvlad.professional_1c.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.starostinvlad.professional_1c.Models.Section;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static final RestClient ourInstance = new RestClient();
    private final Retrofit mRetrofit;
    private final ApiService service;

    private RestClient() {


        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator((route, response) -> {
                    String credential = Credentials.basic("vlad", "vlad1488");
                    return response.request().newBuilder()
                            .header("Authorization", credential)
                            .build();
                })
                .build();
        RxJava2CallAdapterFactory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
        Gson gson = new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .registerTypeAdapter(Section.class, new AllDataDeserializer())
                .create();

        mRetrofit = new Retrofit.Builder()
//                .baseUrl("http://10.81.16.60:5000/api/v1/")
//                .baseUrl("http://bbdd77.skladtfk.ru/")
                .baseUrl("https://starostinvlad.github.io/")
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        service = mRetrofit.create(ApiService.class);
    }

    public static ApiService instance() {
        return ourInstance.service;
    }
}
