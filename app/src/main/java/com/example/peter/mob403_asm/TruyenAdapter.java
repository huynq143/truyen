package com.example.peter.mob403_asm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TruyenAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<TruyenItem> list;
    private RecyclerView recyclerView;

    public TruyenAdapter(Context context, ArrayList<TruyenItem> list, RecyclerView recyclerView) {
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;
    }

    public static class TruyenViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgStory;
        public TextView tv_ten_truyen, tv_mo_ta, tv_thoi_gian;

        public TruyenViewHolder(View itemView) {
            super(itemView);

            imgStory = itemView.findViewById(R.id.imgStory);
            tv_ten_truyen = itemView.findViewById(R.id.tv_ten_truyen);
            tv_mo_ta = itemView.findViewById(R.id.tv_mo_ta);
            tv_thoi_gian = itemView.findViewById(R.id.tv_thoi_gian);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_1_o, parent, false);

        return new TruyenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((TruyenViewHolder)holder).tv_ten_truyen.setText(list.get(position).getTenTruyen());
        ((TruyenViewHolder)holder).tv_mo_ta.setText("Chương " + list.get(position).getSochuong()+" - T/g: "+ list.get(position).getTenTacgia());
        ((TruyenViewHolder)holder).tv_thoi_gian.setText("Ngày cập nhật: " + list.get(position).getNgayUpdate());
        Picasso.get().load(list.get(position).getUrlHinh()).into(((TruyenViewHolder)holder).imgStory);

        //--
        ((TruyenViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, ""+list.get(position).getIdTruyen(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MoTaActivity.class);
                intent.putExtra("idTruyen", list.get(position).getIdTruyen());
                intent.putExtra("idTheloai", list.get(position).getTheloai().getIdTheloai());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
