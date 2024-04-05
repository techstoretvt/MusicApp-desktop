/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.GetListLive;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import helpers.AppConstants;

/**
 *
 * @author tranv
 */
public interface ApiServiceV2 {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(300, TimeUnit.SECONDS) // Thời gian tối đa cho việc kết nối
            .readTimeout(300, TimeUnit.SECONDS) // Thời gian tối đa cho việc đọc dữ liệu
            .writeTimeout(30, TimeUnit.SECONDS) // Thời gian tối đa cho việc ghi dữ liệu
            .build();

    ApiServiceV2 apiServiceV2 = new Retrofit.Builder()
            .baseUrl(AppConstants.url_backend_live)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(ApiServiceV2.class);

    /*
    @Body BodyThemDSPhat name
    @Header("authorization") String authorization
    
     */
    
    @GET("/api/get-list-room")
    Call<GetListLive> getListRoom();

}
