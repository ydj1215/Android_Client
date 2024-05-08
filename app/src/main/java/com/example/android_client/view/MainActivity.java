package com.example.android_client.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.android_client.R;
import com.example.android_client.api.ApiService;
import com.example.android_client.api.RetrofitClient;
import com.example.android_client.model.Memo;
import com.example.android_client.adapter.MemosAdapter;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Slf4j
public class MainActivity extends AppCompatActivity {
    private ApiService apiService;
    private RecyclerView recyclerView;
    private MemosAdapter adapter;
    private List<Memo> memoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrofit 클라이언트 인스턴스를 통해 ApiService 인터페이스 구현
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // RecyclerView 설정
        recyclerView = findViewById(R.id.memosRecyclerView); // RecyclerView 를 찾아서,
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // 세로 방향 리스트로 만들고,
        adapter = new MemosAdapter(memoList, this); // 사용자가 정의한 MemosAdapter 에 인자로 memoList 를 넘겨서,
        recyclerView.setAdapter(adapter); // 실제 화면에 표시한다.

        loadMemos();

        findViewById(R.id.saveButton).setOnClickListener(v -> {
            String memo = ((EditText) findViewById(R.id.memoEditText)).getText().toString();
            apiService.saveMemo(memo).enqueue(new Callback<Void>() { // enqueue : 비동기적으로 요청을 실행

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) { // 요청 성공 시
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Memo saved", Toast.LENGTH_SHORT).show();
                        loadMemos();
                    } else { // 서버에 요청이 도달했으나, 정상적인 응답을 받지 못했을 때
                        Toast.makeText(MainActivity.this, "Failed to save memo: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) { // 서버에 요청을 전송하지도 못했을 때
                    Toast.makeText(MainActivity.this, "Error saving memo", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void loadMemos() {
        apiService.getMemos().enqueue(new Callback<List<Memo>>() { // ApiService를 사용하여 서버로부터 메모 목록을 비동기적으로 불러온다.
            @Override
            public void onResponse(Call<List<Memo>> call, Response<List<Memo>> response) {
                if (response.isSuccessful()) { // 응답 성공 시, 받아온 메모 목록으로 RecyclerView의 데이터를 갱신한다.
                    List<Memo> memos = response.body(); // 서버로부터 받은 메모 목록
                    memoList.clear(); // 기존 목록을 지우고
                    memoList.addAll(memos); // 새로운 목록을 추가한다.
                    adapter.notifyDataSetChanged(); // 어댑터에 데이터가 변경되었음을 알린다.
                } else {
                    // 서버로부터 응답은 받았으나, 요청이 성공적이지 않은 경우
                    Toast.makeText(MainActivity.this, "Failed to load memos: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Memo>> call, Throwable t) {
                // 서버로부터 응답조차 받지 못한 경우 (네트워크 문제 등)
                Toast.makeText(MainActivity.this, "Error loading memos", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
