package com.example.android_client.api;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;

public interface ApiService {
    @FormUrlEncoded
    @POST("/ANDROID_SERVER/addMemo") // http://10.0.2.2:8080/ANDROID_SERVER/saveMemo 로 요청을 전송
    Call<Void> saveMemo(@Field("memo") String memo);
}
