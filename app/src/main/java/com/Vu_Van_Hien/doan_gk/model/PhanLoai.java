package com.Vu_Van_Hien.doan_gk.model;

import java.io.Serializable;

public class PhanLoai implements Serializable {
    int MaLoai;
    String TenPhanLoai;
    // ArrayList<Sach> DsSach;

    public PhanLoai(int maLoai, String tenPhanLoai) {
        MaLoai = maLoai;
        TenPhanLoai = tenPhanLoai;
        // DsSach = dsSach;
    }

    public PhanLoai() {
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }

    public String getTenPhanLoai() {
        return TenPhanLoai;
    }

    public void setTenPhanLoai(String tenPhanLoai) {
        TenPhanLoai = tenPhanLoai;
    }


    public String toString() {
        return
                "Mã thể loại: " + MaLoai + '\n' +
                        "Tên thể loại: " + TenPhanLoai + '\n';
    }
}
