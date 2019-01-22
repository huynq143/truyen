package com.example.peter.mob403_asm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultActivity extends AppCompatActivity {

    ArrayList<TruyenItem> truyenData = new ArrayList<TruyenItem>();
    TruyenAdapter adapter;
    RecyclerView recyclerView;
    TextView tvSo_trang, tvTongSoTrang, tvKhongTimThay, tvTitle, tvDetail;
    Button btn_refresh;
    String idTheLoai, tenTheLoai, searchName;
    int loadedPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initViews();

        idTheLoai = getIntent().getStringExtra("idTheLoai");
        tenTheLoai = getIntent().getStringExtra("tenTheLoai");
        searchName = getIntent().getStringExtra("searchName");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ResultActivity.this, OrientationHelper.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        if(!(idTheLoai == null)){//id thể loại != null -> truyện theo thể loại
            loadJSON();
            tvTitle.setText(tenTheLoai);
            recyclerView.setVisibility(View.VISIBLE);
        }else if(!(searchName == null)){//search name != null -> truyện theo tên
            loadSearch();
        }


    }

    private void initViews(){
        recyclerView = findViewById(R.id.rc_trangchu);
        tvSo_trang = findViewById(R.id.tvSo_trang);
        tvTongSoTrang = findViewById(R.id.tvTongSoTrang);
        btn_refresh = findViewById(R.id.btn_refresh);
        tvKhongTimThay = findViewById(R.id.tvKhongTimThay);
        tvTitle = findViewById(R.id.tvTitle);
        tvDetail = findViewById(R.id.tvDetail);
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TruyenRequestInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TruyenRequestInterface request = retrofit.create(TruyenRequestInterface.class);
        Call<ArrayList<TruyenItem>> call = request.getTruyenTheoTheLoai(idTheLoai);
        call.enqueue(new Callback<ArrayList<TruyenItem>>() {
                         @Override
                         public void onResponse(Call<ArrayList<TruyenItem>> call, Response<ArrayList<TruyenItem>> response) {
                             truyenData = response.body();
//                             for(int i =0; i<truyenData.size(); i++){
//                                 Log.d("tagne", truyenData.get(i).getUrlHinh() + "");
//                             }
                             //Log.d("tagne", "dcroi");

                             adapter = new TruyenAdapter(ResultActivity.this, truyenData, recyclerView);
                             recyclerView.setAdapter(adapter);
                         }

                         @Override
                         public void onFailure(Call<ArrayList<TruyenItem>> call, Throwable t) {
                             Log.d("tagne", "nhucc");
                         }
                     }

        );
    }

    private void loadSearch() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TruyenRequestInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TruyenRequestInterface request = retrofit.create(TruyenRequestInterface.class);
        Call<ArrayList<TruyenItem>> call = request.getTruyenTheoTen(searchName);
        call.enqueue(new Callback<ArrayList<TruyenItem>>() {
                         @Override
                         public void onResponse(Call<ArrayList<TruyenItem>> call, Response<ArrayList<TruyenItem>> response) {
                             truyenData = response.body();
                             tvTitle.setText("Kết quả của" + " \"" + searchName + "\"");
                             if (truyenData.size() == 0){
                                 //Toast.makeText(ResultActivity.this, "0 nha", Toast.LENGTH_SHORT).show();
                                 tvKhongTimThay.setVisibility(View.VISIBLE);
                             }else {
                                 recyclerView.setVisibility(View.VISIBLE);
//                                 for (int i = 0; i < truyenData.size(); i++) {
//                                     Log.d("tagne", truyenData.get(i).getUrlHinh() + "");
//                                 }
                                 //Log.d("tagne", "dcroi");

                                 adapter = new TruyenAdapter(ResultActivity.this, truyenData, recyclerView);
                                 recyclerView.setAdapter(adapter);
                             }
                         }

                         @Override
                         public void onFailure(Call<ArrayList<TruyenItem>> call, Throwable t) {
                             Log.d("tagne", "nhucc");
                         }
                     }

        );
    }
}
