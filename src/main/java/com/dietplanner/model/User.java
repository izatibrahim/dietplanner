package com.dietplanner.model;

public class User {

    private int id;
    private String nama;
    private int umur;
    private String jenisKelamin;
    private double tinggiBadan;
    private double beratBadan;
    private String target;
    private String alergi;
    private int targetKalori;
    private String username;
    private String password;

    public User() {
    }

    public User(int id, String nama, int umur,
                String jenisKelamin,
                double tinggiBadan,
                double beratBadan,
                String target,
                String alergi,
                int targetKalori) {

        this.id = id;
        this.nama = nama;
        this.umur = umur;
        this.jenisKelamin = jenisKelamin;
        this.tinggiBadan = tinggiBadan;
        this.beratBadan = beratBadan;
        this.target = target;
        this.alergi = alergi;
        this.targetKalori = targetKalori;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getUmur() { return umur; }
    public void setUmur(int umur) { this.umur = umur; }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public double getTinggiBadan() { return tinggiBadan; }
    public void setTinggiBadan(double tinggiBadan) { this.tinggiBadan = tinggiBadan; }

    public double getBeratBadan() { return beratBadan; }
    public void setBeratBadan(double beratBadan) { this.beratBadan = beratBadan; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public String getAlergi() { return alergi; }
    public void setAlergi(String alergi) { this.alergi = alergi; }

    public int getTargetKalori() { return targetKalori; }
    public void setTargetKalori(int targetKalori) { this.targetKalori = targetKalori; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
