package com.example.peter.mob403_asm;

import java.util.ArrayList;

public class GioTruyenItem {
    private int id;
    private ArrayList<TruyenItem> danhSachTruyenDaXem;

    public GioTruyenItem(int id, ArrayList<TruyenItem> danhSachTruyenDaXem) {
        this.id = id;
        this.danhSachTruyenDaXem = danhSachTruyenDaXem;
    }

    public int getId() {
        return id;
    }

    public ArrayList<TruyenItem> getDanhSachTruyenDaXem() {
        return danhSachTruyenDaXem;
    }
}
