package com.dietplanner.utils;

public class BMIUtil {

    public static double hitungBMI(double berat, double tinggi) {

        tinggi = tinggi / 100;

        return berat / (tinggi * tinggi);

    }

    public static String kategori(double bmi) {

        if (bmi < 18.5)
            return "Kurus";

        if (bmi < 25)
            return "Normal";

        if (bmi < 30)
            return "Gemuk";

        return "Obesitas";

    }

}