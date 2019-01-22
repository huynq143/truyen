package com.example.peter.mob403_asm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadActivity extends AppCompatActivity {

    TextView tvRead, tvTitle;
    String idTruyen;
    Button btnPre, btnNext;
    ImageButton img_btn_back;

    int indexChuong, lastIndex;

    ArrayList<ChuongItem> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        initViews();
        img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        idTruyen = getIntent().getStringExtra("idTruyen");
        //Toast.makeText(this, idTruyen+" haha", Toast.LENGTH_SHORT).show();
        loadJSON();

//        tvRead.setText("Giờ họa, cô giáo dạy các em học sinh lớp hai vẽ trái tim. Cô vẽ mẫu trên bảng xong rồi quay xuống:\n" +
//                "\n" +
//                "- Các em vẽ đi!\n" +
//                "\n" +
//                "Cả lớp bắt đầu vẽ. Riêng Vova không vẽ. Cô giáo hỏi:\n" +
//                "\n" +
//                "- Sao em không vẽ?\n" +
//                "\n" +
//                "- Thưa cô – Vova trả lời – Cô vẽ còn thiếu.\n" +
//                "\n" +
//                "- Thiếu cái gì?\n" +
//                "\n" +
//                "- Áo quần.\n" +
//                "\n" +
//                "- Sao vậy?\n" +
//                "\n" +
//                "- Ở nhà lúc ngủ dậy, em nghe ba em nói với mẹ: “Trái tim của anh ơi, anh mặc áo quần cho em nhé!”");
    }

    private void initViews(){
        tvRead = findViewById(R.id.tvNoi_dung_truyen);
        tvTitle = findViewById(R.id.tv_Title_truyen);
        btnPre = findViewById(R.id.btnPre);
        btnNext = findViewById(R.id.btnNext);
        img_btn_back = findViewById(R.id.img_btn_back);
    }

    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TruyenRequestInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TruyenRequestInterface request = retrofit.create(TruyenRequestInterface.class);
        Call<ArrayList<ChuongItem>> call = request.getChuongTheoId(idTruyen);
        call.enqueue(new Callback<ArrayList<ChuongItem>>() {
                         @Override
                         public void onResponse(Call<ArrayList<ChuongItem>> call, Response<ArrayList<ChuongItem>> response) {
                             Log.d("tagne", "dc roi");
                            list = response.body();
                            if(list.size() == 0){
                                Toast.makeText(ReadActivity.this, "Truyện không có chương", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            indexChuong = 0;
                            lastIndex = list.size() - 1;
                            tvTitle.setText("Chương " + list.get(indexChuong).getThuTuTap() + ": " + list.get(indexChuong).getTenChuong());
                            tvRead.setText(list.get(indexChuong).getNoidung());
                            btnNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    while (indexChuong < lastIndex){
                                        indexChuong++;
                                        tvTitle.setText("Chương " + list.get(indexChuong).getThuTuTap() + ": " + list.get(indexChuong).getTenChuong());
                                        tvRead.setText(list.get(indexChuong).getNoidung());
                                    }
                                }
                            });

                            btnPre.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    while (indexChuong > 0){
                                        indexChuong--;
                                        tvTitle.setText("Chương " + list.get(indexChuong).getThuTuTap() + ": " + list.get(indexChuong).getTenChuong());
                                        tvRead.setText(list.get(indexChuong).getNoidung());
                                    }
                                }
                            });
                         }

                         @Override
                         public void onFailure(Call<ArrayList<ChuongItem>> call, Throwable t) {
                             Log.d("tagne", "nhucc");
                         }
                     }

        );
    }
}
