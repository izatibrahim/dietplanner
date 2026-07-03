package com.dietplanner.utils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Menghitung streak (hari berturut-turut) user menyimpan menu hariannya.
 */
public class StreakUtil {

    public static int hitungStreak(List<String> daftarTanggal) {

        Set<LocalDate> tanggalSet = new HashSet<>();
        for (String t : daftarTanggal) {
            try {
                tanggalSet.add(LocalDate.parse(t));
            } catch (Exception ignored) {
                // lewati format tanggal yang tidak valid
            }
        }

        LocalDate cursor = LocalDate.now();

        // Kalau hari ini belum input, streak masih dianggap "hidup"
        // selama kemarin ada isinya (baru putus kalau kemarin juga kosong).
        if (!tanggalSet.contains(cursor)) {
            cursor = cursor.minusDays(1);
            if (!tanggalSet.contains(cursor)) {
                return 0;
            }
        }

        int streak = 0;
        while (tanggalSet.contains(cursor)) {
            streak++;
            cursor = cursor.minusDays(1);
        }

        return streak;
    }
}
