package com.dietplanner.model;

public class Makanan {

    private int id;
    private String nama;
    private String kategori;
    private int kalori;
    private double protein;
    private double karbohidrat;
    private double lemak;
    private String alergen;

    public Makanan() {
    }

    public Makanan(int id, String nama, String kategori,
                   int kalori, double protein,
                   double karbohidrat,
                   double lemak,
                   String alergen) {

        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.kalori = kalori;
        this.protein = protein;
        this.karbohidrat = karbohidrat;
        this.lemak = lemak;
        this.alergen = alergen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getKalori() {
        return kalori;
    }

    public void setKalori(int kalori) {
        this.kalori = kalori;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getKarbohidrat() {
        return karbohidrat;
    }

    public void setKarbohidrat(double karbohidrat) {
        this.karbohidrat = karbohidrat;
    }

    public double getLemak() {
        return lemak;
    }

    public void setLemak(double lemak) {
        this.lemak = lemak;
    }

    public String getAlergen() {
        return alergen;
    }

    public void setAlergen(String alergen) {
        this.alergen = alergen;
    }
}