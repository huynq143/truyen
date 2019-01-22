package com.example.peter.mob403_asm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoTaActivity extends AppCompatActivity {

    TruyenItem truyenItem;
    String idTruyen;
    TextView tvTen_tacgia, tvTheloai, tvTrangthai, tvSochuong, tvNgayup, tvTNgaycapnhat, tvTNoidung, tvTenTruyen;
    ImageView img_hinhsp_chitiet;
    Button btnDoc_truyen;
    CircleImageView civHinhTruyen;
    String idTheloai;
    ImageButton img_btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mo_ta);

        idTruyen = getIntent().getStringExtra("idTruyen");
        idTheloai = getIntent().getStringExtra("idTheloai");
        //Toast.makeText(this, idTruyen, Toast.LENGTH_SHORT).show();
        initViews();
        loadJSON();
        //them truyen vao gio
        MyDatabase db = new MyDatabase(MoTaActivity.this);
        TokenAndUserId tokenAndUserId = db.getTokenAndUserId();
        if( tokenAndUserId != null){
            themTruyenVaoGio(tokenAndUserId.getUserId(), idTruyen);
        }

        //Toast.makeText(getApplication(), idTheloai+"", Toast.LENGTH_SHORT).show();
        btnDoc_truyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MoTaActivity.this, idTruyen+" ok", Toast.LENGTH_SHORT).show();
                Intent intent;
                if(idTheloai.equals("5")){
                    //Toast.makeText(MoTaActivity.this, "truyen tranh", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MoTaActivity.this, TruyenTranhActivity.class);
                    intent.putExtra("idTruyen", idTruyen);
                    //intent.putExtra("idTheloai", idTheloai);
                }else{
                    intent = new Intent(MoTaActivity.this, ReadActivity.class);
                    intent.putExtra("idTruyen", idTruyen);
                    //intent.putExtra("idTheloai", idTheloai);
                }

                MoTaActivity.this.startActivity(intent);
            }
        });

        img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews(){
        img_hinhsp_chitiet = findViewById(R.id.img_hinhsp_chitiet);
        tvTen_tacgia = findViewById(R.id.tvTen_tacgia);
        tvTheloai = findViewById(R.id.tvTheloai);
        tvTrangthai = findViewById(R.id.tvTrangthai);
        tvSochuong = findViewById(R.id.tvSochuong);
        tvNgayup = findViewById(R.id.tvNgayup);
        tvTNgaycapnhat = findViewById(R.id.tvTNgaycapnhat);
        tvTNoidung = findViewById(R.id.tvTNoidung);
        btnDoc_truyen = findViewById(R.id.btnDoc_truyen);
        civHinhTruyen = findViewById(R.id.civHinhTruyen);
        tvTenTruyen = findViewById(R.id.tvTenTruyen);

        img_btn_back = findViewById(R.id.img_btn_back);
    }

    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TruyenRequestInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TruyenRequestInterface request = retrofit.create(TruyenRequestInterface.class);
        Call<TruyenItem> call = request.getTruyenTheoId(idTruyen);
        call.enqueue(new Callback<TruyenItem>() {
                         @Override
                         public void onResponse(Call<TruyenItem> call, Response<TruyenItem> response) {
                             truyenItem = response.body();
                             Log.d("dcroine", truyenItem.getTenTacgia() + "");
                             Picasso.get().load(truyenItem.getUrlHinh()).into(img_hinhsp_chitiet);
                             tvTen_tacgia.setText(truyenItem.getTenTacgia());
                             tvTheloai.setText(truyenItem.getTheloai().getTheloai());
                             tvTrangthai.setText(truyenItem.getTrangThai());
                             tvSochuong.setText(truyenItem.getSochuong());
                             tvNgayup.setText(truyenItem.getNgayUpload());
                             tvTNgaycapnhat.setText(truyenItem.getNgayUpdate());
                             tvTNoidung.setText(truyenItem.getTomtatNoidung());
                             tvTenTruyen.setText(truyenItem.getTenTruyen());
                             Picasso.get().load(truyenItem.getUrlHinh()).into(civHinhTruyen);
                         }

                         @Override
                         public void onFailure(Call<TruyenItem> call, Throwable t) {

                         }
                     }

        );
    }

    private void themTruyenVaoGio(String idUser, String idTruyen){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TruyenRequestInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TruyenRequestInterface request = retrofit.create(TruyenRequestInterface.class);
        Call<ArrayList<TruyenItem>> call = request.themTruyenVaoGio(idUser,idTruyen);
        call.enqueue(new Callback<ArrayList<TruyenItem>>() {
            @Override
            public void onResponse(Call<ArrayList<TruyenItem>> call, Response<ArrayList<TruyenItem>> response) {
                //Toast.makeText(MoTaActivity.this, "okkk", Toast.LENGTH_SHORT).show();
                MainActivity.reloadLibrary = true;
            }

            @Override
            public void onFailure(Call<ArrayList<TruyenItem>> call, Throwable t) {
                //Toast.makeText(MoTaActivity.this, "...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
