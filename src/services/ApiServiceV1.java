/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gson.GetListBaiHat;
import gson.ResponseDefault;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * @author tranv
 */
public interface ApiServiceV1 {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(300, TimeUnit.SECONDS) // Thời gian tối đa cho việc kết nối
            .readTimeout(300, TimeUnit.SECONDS) // Thời gian tối đa cho việc đọc dữ liệu
            .writeTimeout(30, TimeUnit.SECONDS) // Thời gian tối đa cho việc ghi dữ liệu
            .build();

    ApiServiceV1 apiServiceV1 = new Retrofit.Builder()
            .baseUrl(AppConstants.url_backend)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(ApiServiceV1.class);

    /*
    @Body BodyThemDSPhat name
    @Header("authorization") String authorization
    
     */
    @GET("/api/v1/check-start-server")
    Call<ResponseDefault> checkStartServer();

    @GET("/api/v2/lay-ds-bai-hat-public")
    Call<GetListBaiHat> getTop10MusicKhamPha(@Query("maxCount") int maxCount);

    @GET("/api/v2/get-list-random-bai-hat")
    Call<GetListBaiHat> getListRandom(@Query("limit") int limit,
            @Query("minusId") String[] minusId);
}
