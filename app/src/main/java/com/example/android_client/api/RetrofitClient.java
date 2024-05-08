package com.example.android_client.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// 해당 클래스는 Retrofit 라이브러리를 사용하여 HTTP 요청을 관리한다.
public class RetrofitClient {
    /*
    서버의 기본 URL (=BASE_URL) 은, 항상 / 로 끝나야 한다. (어길시 오류 발생)
    즉, 뒤에 /ANDROID_SERVER 를 붙이지 말자, 이 때문에 서버에 요청이 가지 않았었다.
    만약 붙이게 된다면, BASE_URL 이 / 로 끝나야 하는데,
    ApiService 에 설정된 경로도 / 로 시작하기 때문에 전체 경로가 ~//~ 가 되어서 오류가 발생하는 것 같다.
  */
    private static final String BASE_URL = "http://10.0.2.2:8080/";
    private static Retrofit retrofit = null; // Retrofit 객체를 저장할 변수

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) { // Retrofit 객체가 없는 경우 새로 생성
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // 서버 URL 설정
                    .addConverterFactory(GsonConverterFactory.create()) // JSON 파서로 Gson 사용
                    .build();  // Retrofit 객체 생성
        }
        return retrofit;  // 생성된 Retrofit 객체 반환
    }
}
