/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package api;

import bodyapi.BodyThemBHVaoDS;
import bodyapi.BodyThemDSPhat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.GetBaiHatById;
import model.GetCaSiByID;
import model.GetDSPhatById;
import model.GetListBHYeuThich;
import model.GetListBaiHat;
import model.GetListCSQuanTam;
import model.GetListPlaylist;
import model.GetLoiBaiHat;
import model.GetTaiKhoan;
import model.KeywordGoogle;
import model.ResponseDefault;
import model.ThemBHVaoDS;
import model.ThemDSPhat;
import model.TimKiemBaiHat;
import model.TimKiemCaSi;
import model.TimKiemMV;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import helpers.AppConstants;
import okhttp3.RequestBody;

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

    @GET("/api/v2/get-list-loi-bai-hat")
    Call<GetLoiBaiHat> getLoiBaiHatById(@Query("idBaiHat") String idBaiHat);

    @GET("/api/v2/tim-kiem-bai-hat")
    Call<TimKiemBaiHat> timKiemBaiHat(@Query("tenBaiHat") String tenBH,
            @Query("offset") String offset,
            @Query("limit") String limit);

    @GET("/api/v2/tim-kiem-ca-si")
    Call<TimKiemCaSi> timKiemCaSi(@Query("tenCaSi") String tenCS,
            @Query("offset") String offset,
            @Query("limit") String limit);

    @GET("/api/v2/tim-kiem-mv")
    Call<TimKiemMV> timKiemMV(
            @Query("value") String value
    );

    @GET("/api/v2/lay-ca-si-by-id")
    Call<GetCaSiByID> layCaSiById(@Query("idCaSi") String idCaSi);

    @GET("/api/v2/lay-bai-hat-cua-ca-si")
    Call<GetListBaiHat> layBaiHatCuaCaSi(@Query("idCaSi") String idCaSi);

    @GET("/api/v2/tim-kiem-bai-hat-by-id")
    Call<GetBaiHatById> getBaiHatById(@Query("idBaiHat") String idBaiHat);

    @GET("/api/v2/get-goi-y-mv-bai-hat")
    Call<GetListBaiHat> getGoiYMVBaiHat(
            @Query("listIdBaiHat") String listIdBaiHat
    );

    @GET("/api/v2/lay-ds-ca-si-quan-tam")
    Call<GetListCSQuanTam> getListCaSiQuanTam(
            @Header("authorization") String authorization);

    @GET("/api/v2/lay-danh-sach-phat")
    Call<GetListPlaylist> getDanhSachPhat(@Header("authorization") String authorization);

    @POST("/api/v2/them-danh-sach-phat")
    Call<ThemDSPhat> themDanhSachPhat(@Body BodyThemDSPhat name, @Header("authorization") String authorization);

    @GET("/api/v2/lay-bai-hat-trong-danh-sach")
    Call<GetDSPhatById> getDanhSachPhatById(@Query("idDanhSachPhat") String idDanhSachPhat,
            @Header("authorization") String authorization);

    @PUT("/api/v2/doi-ten-danh-sach")
    Call<ResponseDefault> doiTenDanhSach(@Query("idDanhSach") String idDS,
            @Query("tenDanhSach") String tenDS,
            @Header("authorization") String authorization
    );

    @DELETE("/api/v2/xoa-danh-sach-phat")
    Call<ResponseDefault> xoaDanhSachPhatById(@Query("idDanhSachPhat") String idDanhSachPhat,
            @Header("authorization") String authorization);

    @POST("/api/v2/them-bai-hat-vao-danh-sach")
    Call<ThemBHVaoDS> themBaiHatVaoDS(@Body BodyThemBHVaoDS body,
            @Header("authorization") String authorization);

    @DELETE("/api/v2/xoa-bai-hat-khoi-danh-sach")
    Call<ResponseDefault> xoaBaiHatKhoiDS(@Query("idDanhSachPhat") String idDanhSachPhat,
            @Query("idBaiHat") String idBaiHat,
            @Header("authorization") String authorization);

    @GET("/api/v2/kiem-tra-yeu-thich-bai-hat")
    Call<ResponseDefault> kiemTraYeuThichBaiHat(@Query("idBaiHat") String idBaiHat,
            @Header("authorization") String authorization);

    @POST("/api/v2/toggle-yeu-thich-bai-hat")
    Call<ResponseDefault> toggleLikeBaiHat(@Query("idBaiHat") String idBaiHat,
            @Header("authorization") String authorization);

    @GET("/api/v2/check-login-user")
    Call<ResponseDefault> checkLogin(@Header("authorization") String authorization);
    
    @GET("/api/get-user-login")
    Call<GetTaiKhoan> getTaiKhoan(@Header("authorization") String authorization);
    
    @GET("/api/v2/lay-danh-sach-bai-hat-yeu-thich")
    Call<GetListBHYeuThich> getListBHYeuThich(@Query("order_by") String order_by,
                                              @Query("order_type") String order_type,
                                              @Header("authorization") String authorization);
    
    @POST("/api/v2/toggle-quan-tam-ca-si")
    Call<ResponseDefault> toggleQuanTamCasi(@Query("idCaSi") String idCaSi,
                                            @Header("authorization") String authorization);
    
    @GET("/api/v2/kiem-tra-quan-tam-ca-si")
    Call<ResponseDefault> kiemTraQuanTamCaSi(@Query("idCaSi") String idCaSi,
                                             @Header("authorization") String authorization);
    
    @GET("/api/v2/get-list-keyword-search-mobile")
    Call<KeywordGoogle> getGoiYTuKhoa(@Query("value") String value);
    
    
    @POST("/api/v2/them-danh-sach-phat")
    Call<ThemDSPhat> themDanhSachPhat2(@Body RequestBody postData, @Header("authorization") String authorization);

}
