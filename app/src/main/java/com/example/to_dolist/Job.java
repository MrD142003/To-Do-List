package com.example.to_dolist;

import java.io.Serializable;

public class Job implements Comparable<Job>, Serializable {
    private String maCV;
    private String tenCV;
    private String tenNV;
    private String sdt;

    public Job(String maCV, String tenCV, String tenNV, String sdt) {
        this.maCV = maCV;
        this.tenCV = tenCV;
        this.tenNV = tenNV;
        this.sdt = sdt;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }


    @Override
    public int compareTo(Job o) {
        return 0;
    }
}
