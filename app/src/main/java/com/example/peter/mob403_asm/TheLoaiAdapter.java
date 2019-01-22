package com.example.peter.mob403_asm;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TheLoaiAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TheLoaiItem> list;

    public TheLoaiAdapter(Context context, ArrayList<TheLoaiItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i).getTheloai();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        NewHolder holder = null;

        if(convertView==null){//
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(R.layout.view_1o_grid,viewGroup,false);

            holder = new NewHolder();

            //

            holder.textView = convertView.findViewById(R.id.tvTheLoaiItem);

            convertView.setTag(holder);
        }
        else {
            holder = (NewHolder) convertView.getTag();
        }


        holder.textView.setText(list.get(position).getTheloai());

        return convertView;
    }

    public class NewHolder {

        public TextView textView;
    }
}
