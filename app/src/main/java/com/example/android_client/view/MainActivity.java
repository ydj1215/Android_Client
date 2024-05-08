package com.example.android_client.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast; // 사용자 화면 위에 살짝 나타났다 사라지는 팝업창
import androidx.appcompat.app.AppCompatActivity;
import com.example.android_client.R; // 안드로이드 프로젝트에서 자동으로 생성되는 특별한 자바 클래스로, 앱의 모든 Resource 에 접근 가능한 정적 변수들이 포함되어 있다.
import com.example.android_client.api.ApiService;
import com.example.android_client.api.RetrofitClient;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// MainActivity 클래스는 AppCompatActivity를 상속받아 안드로이드 앱의 주 화면을 구성
public class MainActivity extends AppCompatActivity {
    private ApiService apiService;  // API 인터페이스를 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // activity_main 레이아웃을 사용하여 UI를 구성 (setContentView() 메서드를 사용)

        // RetrofitClient를 사용하여 Retrofit 인스턴스를 호출
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class); // 싱글턴 패턴을 이용하여 인스턴스 생성

        // 저장 버튼에 대한 클릭 리스너 설정
        findViewById(R.id.saveButton).setOnClickListener(v -> {
            String memo = ((EditText) findViewById(R.id.memoEditText)).getText().toString(); // 메모 입력 필드에서 텍스트를 가져온다.
            apiService.saveMemo(memo).enqueue(new Callback<Void>() { // ApiService를 통해 서버에 메모 저장 요청, enqueue 메서드를 사용했기에 비동기적으로 처리된다.
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) { // 요청 성공 시 실행
                    if (response.isSuccessful()) {
                        // 요청이 성공적으로 처리되었을 때만 성공 메시지를 표시
                        Toast.makeText(MainActivity.this, "Memo saved", Toast.LENGTH_SHORT).show();
                        Log.e("Error", "ㅋㅋㅋㅋ_3 : " + memo);
                    } else {
                        // 서버는 응답을 반환했지만, 에러 코드를 포함하는 경우
                        Toast.makeText(MainActivity.this, "Failed to save memo: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.e("Error", "ㅋㅋㅋㅋ_1 : " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {  // 요청 실패 혹은 네트워크 문제 발생 시 실행
                    Toast.makeText(MainActivity.this, "Error saving memo", Toast.LENGTH_SHORT).show();
                    Log.e("Error", "ㅋㅋㅋㅋ_2 : " + t.getMessage()); // 로그에 에러 메시지와 스택 트레이스를 출력
                }
            });
        });
    }
}


