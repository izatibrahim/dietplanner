package com.dietplanner.model;

public class Progress {

    private int id;
    private int userId;
    private String tanggal;
    private double beratBadan;

    public Progress() {
    }

    public Progress(int id, int userId, String tanggal, double beratBadan) {
        this.id = id;
        this.userId = userId;
        this.tanggal = tanggal;
        this.beratBadan = beratBadan;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public double getBeratBadan() { return beratBadan; }
    public void setBeratBadan(double beratBadan) { this.beratBadan = beratBadan; }
}
