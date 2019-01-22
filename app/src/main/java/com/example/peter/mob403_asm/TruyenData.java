package com.example.peter.mob403_asm;

public class TruyenData {
    private String idTruyen;
    private String tenTacgia;
    private String tenTheloai;
    private String trangThai;
    private String sochuong;
    private String ngayUpload;
    private String ngayUpdate;
    private String tomtatNoidung;
    private String urlHinh;

    public TruyenData(String idTruyen, String tenTacgia,  String tenTheloai, String trangThai, String sochuong, String ngayUpload, String ngayUpdate, String tomtatNoidung, String urlHinh) {
        this.idTruyen = idTruyen;
        this.tenTacgia = tenTacgia;
        this.tenTheloai = tenTheloai;
        this.trangThai = trangThai;
        this.sochuong = sochuong;
        this.ngayUpload = ngayUpload;
        this.ngayUpdate = ngayUpdate;
        this.tomtatNoidung = tomtatNoidung;
        this.urlHinh = urlHinh;
    }

    public String getIdTruyen() {
        return idTruyen;
    }

    public String getTenTacgia() {
        return tenTacgia;
    }



    public String getTenTheloai() {
        return tenTheloai;
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
}
