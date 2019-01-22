package com.example.peter.mob403_asm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TruyenTranhActivity extends AppCompatActivity {
    String idTruyen;
    String[] urlList;
    ImageButton img_btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truyen_tranh);
        img_btn_back = findViewById(R.id.img_btn_back);

        img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        idTruyen = getIntent().getStringExtra("idTruyen");
        //Toast.makeText(this, idTruyen, Toast.LENGTH_SHORT).show();
        loadJSON();
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
                             String noiDung = response.body().get(0).getNoidung();
//                             String httpString = "(.*)(\\\\d+)(.*)";
//                             Pattern pattern = Pattern.compile(httpString);
                             urlList = noiDung.split("http");
                             int totalImg = urlList.length;
                             //Toast.makeText(TruyenTranhActivity.this, urlList.length + "", Toast.LENGTH_SHORT).show();


                             LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout);
                             for(int i = 0; i < totalImg; i++)
                             {
                                 try {
                                     ImageView img = new ImageView(TruyenTranhActivity.this);
                                     Picasso.get().load("http" + urlList[i]).into(img);
                                     img.setBackgroundColor(Color.rgb(100, 100, 50));
                                     img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                     img.setAdjustViewBounds(true);

                                     ll.addView(img);
//                                     img.invalidate();
//                                     ll.invalidate();
                                 }catch (Exception e){
                                     Toast.makeText(TruyenTranhActivity.this, "URL Sai", Toast.LENGTH_SHORT).show();
                                 }

                             }

                         }

                         @Override
                         public void onFailure(Call<ArrayList<ChuongItem>> call, Throwable t) {
                             Log.d("tagne", "nhucc");
                         }
                     }

        );
    }
}
