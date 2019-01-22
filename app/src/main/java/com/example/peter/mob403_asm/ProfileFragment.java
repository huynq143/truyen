package com.example.peter.mob403_asm;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView tvemail_nguoi_dung;
    MyDatabase db;
    Button btnDang_Nhap;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnDang_Nhap = view.findViewById(R.id.btnDang_Nhap);
        tvemail_nguoi_dung = view.findViewById(R.id.tvemail_nguoi_dung);

        db = new MyDatabase(getActivity());
        TokenAndUserId tokenAndUserId = db.getTokenAndUserId();
        if(tokenAndUserId == null){
            btnDang_Nhap.setText("Đăng nhập");
            btnDang_Nhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }
            });
        }else{
            tvemail_nguoi_dung.setText(tokenAndUserId.getUserName());
            btnDang_Nhap.setText("Đăng xuất");
            btnDang_Nhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deleteToken();
                    //db.setTokenAndUserId(null, null);
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }



        return view;
    }

}
