package com.example.peter.mob403_asm;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends Fragment {

    ArrayList<TruyenItem> danhSachTruyenDaXem;
    RecyclerView recyclerView;
    ACProgressFlower progressFlower;
    public LibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        progressFlower = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.TRANSPARENT)
                .fadeColor(getResources().getColor(R.color.colortitle)).build();

        MyDatabase db = new MyDatabase(getContext());
        TokenAndUserId tokenAndUserId = db.getTokenAndUserId();
        if( tokenAndUserId == null){// chưa login
            view = inflater.inflate(R.layout.fragment_library, container, false);
        }else{
            view = inflater.inflate(R.layout.truyendaxem, container, false);

            recyclerView = view.findViewById(R.id.rc_trangchu);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL,false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            loadJSON(tokenAndUserId.getToken());
            //

        }
        return view;
    }

    public void loadJSON(String token){
        progressFlower.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TruyenRequestInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TruyenRequestInterface request = retrofit.create(TruyenRequestInterface.class);
        Call<ProfileItem> call = request.getLoginResponse(token);
        call.enqueue(new Callback<ProfileItem>() {
            @Override
            public void onResponse(Call<ProfileItem> call, Response<ProfileItem> response) {
                ProfileItem profileItem = response.body();

                Log.d("tagne", profileItem.getEmail());
                //Toast.makeText(getActivity(), profileItem.getEmail(), Toast.LENGTH_SHORT).show();
                GioTruyenItem gioTruyenItem = profileItem.getGioTruyen();
                danhSachTruyenDaXem = gioTruyenItem.getDanhSachTruyenDaXem();
                //
                //Toast.makeText(getActivity(), danhSachTruyenDaXem.size()+"", Toast.LENGTH_SHORT).show();
                if(danhSachTruyenDaXem.size() > 0){//SỐ TRUYỆN ĐÃ XEM > 0
                    TruyenAdapter adapter = new TruyenAdapter(getActivity(), danhSachTruyenDaXem, recyclerView);
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(getActivity(), "Bạn chưa xem truyện", Toast.LENGTH_SHORT).show();
                }

                progressFlower.dismiss();
                MainActivity.reloadLibrary = false;
            }

            @Override
            public void onFailure(Call<ProfileItem> call, Throwable t) {
                progressFlower.dismiss();
            }
        });
    }
}
