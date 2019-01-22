package com.example.peter.mob403_asm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btn_Register, btn_SignIn;
    String email, password;
    ImageButton img_btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                if(email.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this, "Bạn chưa nhập Username hoặc Password", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!email.matches("^[a-zA-Z0-9]{6,}$") || !password.matches("^.{6,}$")){
                    Toast.makeText(LoginActivity.this, "Username hoặc Password không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                loadJSON();

            }
        });

        img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initViews(){
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btn_Register = findViewById(R.id.btn_Register);
        btn_SignIn = findViewById(R.id.btn_SignIn);
        img_btn_back = findViewById(R.id.img_btn_back);
    }

    private void loadJSON(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TruyenRequestInterface.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TruyenRequestInterface request = retrofit.create(TruyenRequestInterface.class);

        try {
            JSONObject paramObject = new JSONObject();
            //paramObject.put("id", "69");
            paramObject.put("email", email);
            paramObject.put("password", password);

            Call<ProfileItem> userCall = request.login(paramObject.toString());
            userCall.enqueue(new Callback<ProfileItem>() {
                @Override
                public void onResponse(Call<ProfileItem> call, Response<ProfileItem> response) {
                    ProfileItem profileItem = response.body();
                    //Toast.makeText(LoginActivity.this, profileItem.getId()+"", Toast.LENGTH_SHORT).show();

//                    GioTruyenItem gioTruyenItem = profileItem.getGioTruyen();
//                    ArrayList<TruyenItem> asd = gioTruyenItem.getDanhSachTruyenDaXem();
                    String token = response.headers().get("Authorization");
                    MyDatabase db = new MyDatabase(LoginActivity.this);
                    db.deleteToken();
                    db.setTokenAndUserId(token+"", profileItem.getId()+"", profileItem.getEmail());
                    //TokenAndUserId asd = db.getTokenAndUserId();
                    //Toast.makeText(LoginActivity.this, asd.getUserId()+ asd.getToken(), Toast.LENGTH_SHORT).show();
                    //Log.d("tagne", asd.getToken());
                    //Log.d("tagne", db.getToken());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    //Toast.makeText(LoginActivity.this, "token ne: " + asd.getUserId(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ProfileItem> call, Throwable t) {
                    Log.d("tagne", "fail nha");
                    Toast.makeText(LoginActivity.this, "Sai username hoặc password", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
