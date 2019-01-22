package com.example.peter.mob403_asm;

import android.graphics.Bitmap;

public class TruyenItem {
    //private Bitmap hinhTruyen;
    private String idTruyen, tenTacgia, tenTruyen, trangThai, sochuong, ngayUpload, ngayUpdate, tomtatNoidung, urlHinh;
    TheLoaiItem theloai;

    public TruyenItem(String idTruyen, String tenTacgia, String tenTruyen, String trangThai, String sochuong, String ngayUpload, String ngayUpdate, String tomtatNoidung, String urlHinh, TheLoaiItem theloai) {
        this.idTruyen = idTruyen;
        this.tenTacgia = tenTacgia;
        this.tenTruyen = tenTruyen;
        this.trangThai = trangThai;
        this.sochuong = sochuong;
        this.ngayUpload = ngayUpload;
        this.ngayUpdate = ngayUpdate;
        this.tomtatNoidung = tomtatNoidung;
        this.urlHinh = urlHinh;
        this.theloai = theloai;
    }

    public String getIdTruyen() {
        return idTruyen;
    }

    public String getTenTacgia() {
        return tenTacgia;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getSochuong() {
        return sochuong;
    }

    public String getNgayUpload() {
        return ngayUpload;
    }

    public String getNgayUpdate() {
        return ngayUpdate;
    }

    public String getTomtatNoidung() {
        return tomtatNoidung;
    }

    public String getUrlHinh() {
        return urlHinh;
    }

    public TheLoaiItem getTheloai() {
        return theloai;
    }
}
