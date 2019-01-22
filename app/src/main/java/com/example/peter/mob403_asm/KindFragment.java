package com.example.peter.mob403_asm;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
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
public class KindFragment extends Fragment {

    //String arr[]={"Ngôn tình","Kinh dị","Thiếu Nhi","Cổ tích","Tiên Hiệp","Kiếm Hiệp","Viễn Tưởng","Truyện Cười","Lịch Sử","Xuyên không"};
    public GridView gridView;
    ArrayList<TheLoaiItem> list = new ArrayList<TheLoaiItem>();
    ACProgressFlower progressFlower;

    public KindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kind, container, false);
        gridView = view.findViewById(R.id.Grid_view);
        progressFlower = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.TRANSPARENT)
                .fadeColor(getResources().getColor(R.color.colortitle)).build();
        progressFlower.show();

        loadJSON();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getContext(), list.get(position).getTheloai(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putExtra("idTheLoai", list.get(position).getIdTheloai());
                intent.putExtra("tenTheLoai", list.get(position).getTheloai());
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TruyenRequestInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TruyenRequestInterface request = retrofit.create(TruyenRequestInterface.class);
        Call<ArrayList<TheLoaiItem>> call = request.getTheLoai();
        call.enqueue(new Callback<ArrayList<TheLoaiItem>>() {
                         @Override
                         public void onResponse(Call<ArrayList<TheLoaiItem>> call, Response<ArrayList<TheLoaiItem>> response) {
                            list = response.body();

                             TheLoaiAdapter adapter = new TheLoaiAdapter(getActivity(), list);
                             gridView.setAdapter(adapter);
                             progressFlower.dismiss();
                         }

                         @Override
                         public void onFailure(Call<ArrayList<TheLoaiItem>> call, Throwable t) {
                            Log.d("tagne", "errors");
                            progressFlower.dismiss();
                         }
                     }

        );
    }

}
