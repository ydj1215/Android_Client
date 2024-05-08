package com.example.android_client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.model.Memo;

import java.util.List;

// main_item 에 정의된 디자인을 사용하여, activity_main 안의 recycleview 에 메모 데이터를 표시하는 역할을 수행
public class MemosAdapter extends RecyclerView.Adapter<MemosAdapter.ViewHolder> {

    private final  List<Memo> memoList;
    private final Context context;

    public MemosAdapter(List<Memo> memoList, Context context) {
        this.memoList = memoList;
        this.context = context;
    }

    // 새로운 뷰 홀더를 생성, memo_item.xml 레이아웃을 인플레이트(= 정의된 뷰,레이아웃을 메모리 상의 객체로 변환, 이를 통해 개발자는 미리 정의된 레이아웃을 앱 내에서 실제로 다룰 수 있게 된다.)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.memo_item, parent, false); // (레이아웃 리소스, 이 레이아웃 리소스를 포함할 뷰 그룹, 생성된 뷰를 바로 부모에 추가할지 여부 = 일반적으로는 false)
        return new ViewHolder(view);
    }

    // 뷰 홀더에 데이터를 바인딩하는 메소드, 각 메모의 내용을 텍스트 뷰에 설정
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Memo memo = memoList.get(position);
        holder.memoText.setText(memo.getMemo());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이 곳에서 메모 삭제 로직을 구현해야 한다.
                // : 메모 목록에서 해당 항목을 제거하고, 변경사항을 알린다.
                memoList.remove(position);
                notifyItemRemoved(position);
                // 필요하다면 서버에도 삭제 요청을 보내는 로직을 추가할 수 있다.
            }
        });
    }

    // 메모 리스트의 아이템 개수를 반환하는 메소드
    @Override
    public int getItemCount() {
        return memoList.size();
    }

    // 뷰 홀더 클래스를 정의, 여기서 뷰 홀더는 메모 아이템의 뷰를 관리한다.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView memoText;
        public Button deleteButton;

        // 생성자에서 뷰 홀더와 관련된 뷰를 초기화
        public ViewHolder(View itemView) {
            super(itemView);
            memoText = itemView.findViewById(R.id.memoText);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
