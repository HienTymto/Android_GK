package com.Vu_Van_Hien.doan_gk.model;

import java.io.Serializable;

public class Pet implements Serializable {
    int Masp;
    String Tensp;
    String MoTa;
    int Gia;
    byte[] Hinh;
    PhanLoai phanLoai;

    public Pet(int masp, String tensp, String moTa, int gia, byte[] hinh, PhanLoai phanLoai) {
        Masp = masp;
        Tensp = tensp;
        MoTa = moTa;
        Gia = gia;
        Hinh = hinh;
        this.phanLoai = phanLoai;
    }

    public Pet() {

    }

    public int getMasp() {
        return Masp;
    }

    public void setMasp(int masp) {
        Masp = masp;
    }

    public String getTensp() {
        return Tensp;
    }

    public void setTensp(String tensp) {
        Tensp = tensp;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public byte[] getHinh() {
        return Hinh;
    }

    public void setHinh(byte[] hinh) {
        Hinh = hinh;
    }

    public PhanLoai getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(PhanLoai phanLoai) {
        this.phanLoai = phanLoai;
    }
}

