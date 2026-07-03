package com.dietplanner.model;

public class MenuHarian {

    private int id;
    private int userId;
    private String tanggal;
    private String sarapan;
    private String makanSiang;
    private String makanMalam;
    private int totalKalori;

    public MenuHarian() {
    }

    public MenuHarian(int id,
                      int userId,
                      String tanggal,
                      String sarapan,
                      String makanSiang,
                      String makanMalam,
                      int totalKalori) {

        this.id = id;
        this.userId = userId;
        this.tanggal = tanggal;
        this.sarapan = sarapan;
        this.makanSiang = makanSiang;
        this.makanMalam = makanMalam;
        this.totalKalori = totalKalori;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getSarapan() {
        return sarapan;
    }

    public void setSarapan(String sarapan) {
        this.sarapan = sarapan;
    }

    public String getMakanSiang() {
        return makanSiang;
    }

    public void setMakanSiang(String makanSiang) {
        this.makanSiang = makanSiang;
    }

    public String getMakanMalam() {
        return makanMalam;
    }

    public void setMakanMalam(String makanMalam) {
        this.makanMalam = makanMalam;
    }

    public int getTotalKalori() {
        return totalKalori;
    }

    public void setTotalKalori(int totalKalori) {
        this.totalKalori = totalKalori;
    }
}