package com.example.peter.mob403_asm;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.loading.newton.NewtonCradleLoading;
import com.victor.loading.rotate.RotateLoading;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ArrayList<TruyenItem> truyenData = new ArrayList<TruyenItem>();
    TruyenAdapter adapter;
    RecyclerView recyclerView;
    TextView tvSo_trang, tvTongSoTrang;
    Button btn_refresh, btn_search;
    EditText edtSearch;
    int loadedPages;
    String totalPages;

    RotateLoading rotateLoading;
    ACProgressFlower progressFlower;
    LinearLayoutManager linearLayoutManager;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //-------------test---------------
        recyclerView = view.findViewById(R.id.rc_trangchu);
        tvSo_trang = view.findViewById(R.id.tvSo_trang);
        tvTongSoTrang = view.findViewById(R.id.tvTongSoTrang);
        btn_refresh = view.findViewById(R.id.btn_refresh);
        btn_search = view.findViewById(R.id.btn_search);
        edtSearch = view.findViewById(R.id.edtSearch);
        rotateLoading = view.findViewById(R.id.rotateLoading);
        rotateLoading.start();
        progressFlower = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.TRANSPARENT)
                .fadeColor(getResources().getColor(R.color.colortitle)).build();
        progressFlower.show();
//        newtonCradleLoading = view.findViewById(R.id.newton_cradle_loading);
//        newtonCradleLoading.start();

        linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //rotate loading
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT, 5f);
//        rotateLoading.setLayoutParams(params);
//        rotateLoading.start();
        //----------
//        MyDatabase db = new MyDatabase(getActivity());
//        if(db.getTokenAndUserId() == null){
//            Toast.makeText(getActivity(), "null nha", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(getActivity(), "ko co nha", Toast.LENGTH_SHORT).show();
//        }

        //,.,.,.,.,.,.,
        loadJSON();
        loadSoLuongTrang();
        //------------------------------------------
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "reload fragment", Toast.LENGTH_SHORT).show();
//                HomeFragment homeFragment = new HomeFragment();
//                FragmentManager manager = getActivity().getSupportFragmentManager();
//                manager.beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
                progressFlower.show();
                loadJSON();
                loadSoLuongTrang();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtSearch.isShown()){
                    edtSearch.setVisibility(View.VISIBLE);
                    return;
                }
                String searchName = edtSearch.getText().toString().trim();
                if(searchName.equals("")){
                    Toast.makeText(getActivity(), "Bạn chưa nhập tên truyện", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putExtra("searchName", searchName);
                getActivity().startActivity(intent);
                edtSearch.setVisibility(View.INVISIBLE);
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
        Call<ArrayList<TruyenItem>> call = request.getTruyenIdNhoHon(999+"");
        call.enqueue(new Callback<ArrayList<TruyenItem>>() {
                         @Override
                         public void onResponse(Call<ArrayList<TruyenItem>> call, Response<ArrayList<TruyenItem>> response) {
                             truyenData = response.body();
//                             for(int i =0; i<truyenData.size(); i++){
//                                 Log.d("tagne", truyenData.get(i).getUrlHinh() + "");
//                             }

                             adapter = new TruyenAdapter(getActivity(), truyenData, recyclerView);
                             recyclerView.setAdapter(adapter);
                             //-----
                             recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
                                 @Override
                                 public void onLoadMore(int current_page) {
                                     // do something...

                                     //Toast.makeText(getActivity(), "load"+current_page, Toast.LENGTH_SHORT).show();
                                     if(loadedPages < Integer.parseInt(totalPages)){
                                         rotateLoading.setVisibility(View.VISIBLE);

                                     }
                                     loadEndless(truyenData.get(truyenData.size() - 1).getIdTruyen());


                                 }
                             });
                             //-----
                             progressFlower.dismiss();
                         }

                         @Override
                         public void onFailure(Call<ArrayList<TruyenItem>> call, Throwable t) {
                             Log.d("tagne", "fail");
                             progressFlower.dismiss();
                         }
                     }

        );
    }

    //test endless
    private void loadEndless(String idCuoi){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TruyenRequestInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TruyenRequestInterface request = retrofit.create(TruyenRequestInterface.class);
        Call<ArrayList<TruyenItem>> call = request.getTruyenIdNhoHon(idCuoi);
        call.enqueue(new Callback<ArrayList<TruyenItem>>() {
                         @Override
                         public void onResponse(Call<ArrayList<TruyenItem>> call, Response<ArrayList<TruyenItem>> response) {

                             ArrayList<TruyenItem> resData = response.body();
                             if(resData.size() > 0){
                                 try{
                                     for(int i =0; i<resData.size(); i++){
                                         truyenData.add(resData.get(i));
                                     }

                                     adapter.notifyDataSetChanged();
                                     loadedPages++;
                                     tvSo_trang.setText(loadedPages + "");
                                     rotateLoading.setVisibility(View.GONE);
                                     //Toast.makeText(getActivity(), truyenData.size()+"", Toast.LENGTH_SHORT).show();
                                 }catch (Exception e){

                                 }

                             }

                         }

                         @Override
                         public void onFailure(Call<ArrayList<TruyenItem>> call, Throwable t) {
                             Log.d("tagne", "nhucc");
                             rotateLoading.setVisibility(View.GONE);
                         }
                     }

        );
    }
    //-----------


    //----
    private void loadSoLuongTrang() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TruyenRequestInterface.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        TruyenRequestInterface request = retrofit.create(TruyenRequestInterface.class);
        Call<String> callString = request.getSoLuongTrang();
        callString.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Toast.makeText(getActivity(), response.body(), Toast.LENGTH_SHORT).show();
                totalPages = response.body();
                if(Integer.parseInt(totalPages) > 0){
                    loadedPages = 1;
                    tvTongSoTrang.setText("/" + totalPages);
                    tvSo_trang.setText(loadedPages + "");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //tvTongSoTrang.setText("kodc");
            }
        });
    }



}
