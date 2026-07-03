package com.dietplanner.utils;

public class KaloriUtil {

public static int hitungBMR(String jk, double bb, double tb, int umur) {

    double bmr;

    if (jk != null && jk.trim().toUpperCase().startsWith("L")) {

        bmr = 88.362 + (13.397 * bb) + (4.799 * tb) - (5.677 * umur);

    } else {

        bmr = 447.593 + (9.247 * bb) + (3.098 * tb) - (4.330 * umur);

    }

    return (int) bmr;

}

    public static int hitungTDEE(int bmr) {

        // Faktor aktivitas ringan-sedang.
        // Nanti bisa dikembangkan jadi input user (sedentary/ringan/sedang/berat).
        double faktorAktivitas = 1.375;

        return (int) (bmr * faktorAktivitas);

    }

    public static int hitungTargetKalori(int tdee, String target) {

        if (target == null) return tdee;

        switch (target.trim().toLowerCase()) {

            case "bulking":
                return tdee + 500;

            case "cutting":
                return tdee - 500;

            default:
                return tdee;

        }

    }

}