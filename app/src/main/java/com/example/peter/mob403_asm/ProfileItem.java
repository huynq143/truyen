package com.example.peter.mob403_asm;

public class ProfileItem {
    private String id, email, password;
    private GioTruyenItem gioTruyen;

    public ProfileItem(String id, String email, String password, GioTruyenItem gioTruyen) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.gioTruyen = gioTruyen;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public GioTruyenItem getGioTruyen() {
        return gioTruyen;
    }
}
