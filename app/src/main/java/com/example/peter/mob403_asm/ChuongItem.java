package com.example.peter.mob403_asm;

public class ChuongItem {
    private String idChuong, thuTuTap, tenChuong, noidung;

    public ChuongItem(String idChuong, String thuTuTap, String tenChuong, String noidung) {
        this.idChuong = idChuong;
        this.thuTuTap = thuTuTap;
        this.tenChuong = tenChuong;
        this.noidung = noidung;
    }

    public String getIdChuong() {
        return idChuong;
    }

    public String getThuTuTap() {
        return thuTuTap;
    }

    public String getTenChuong() {
        return tenChuong;
    }

    public String getNoidung() {
        return noidung;
    }
}
