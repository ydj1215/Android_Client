package com.example.android_client.api;

import com.example.android_client.model.Memo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("/ANDROID_SERVER/addMemo") // http://10.0.2.2:8080/ANDROID_SERVER/saveMemo 로 요청을 전송
    Call<Void> saveMemo(@Field("memo") String memo); // Call : Retrofit 라이브러리에서 비동기적으로 http 요청을 보내고 응답을 처리하는데 사용

    @GET("/ANDROID_SERVER/loadMemo")
    Call<List<Memo>> getMemos();

    @DELETE("/ANDROID_SERVER/deleteMemo/{id}")
    Call<Void> deleteMemo(@Path("id") int id);

    @FormUrlEncoded
    @PUT("/ANDROID_SERVER/updateMemo/{id}")
    Call<Void> updateMemo(@Path("id") int id, @Field("memo") String memo);
}

// get 과 delete 요청은 어차피 요청을 url 을 통해서 보내기 때문에 @FormUrlEncoded 가 필요 X
