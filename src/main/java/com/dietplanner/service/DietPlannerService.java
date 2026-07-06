package com.dietplanner.service;

import com.dietplanner.model.Makanan;
import com.dietplanner.dao.MakananDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DietPlannerService {

    // Toleransi selisih kalori (kkal) supaya ada lebih dari satu kombinasi
    // "cukup bagus" untuk dipilih secara acak, bukan cuma satu yang paling pas.
    private static final int TOLERANSI_KALORI = 150;

    private final MakananDAO makananDAO = new MakananDAO();
    private final Random random = new Random();

    // Menyimpan setiap kombinasi menu lengkap (3 slot) yang ditemukan backtracking,
    // beserta total kalorinya, supaya bisa dipilah & dipilih secara acak nanti.
    private List<KombinasiHasil> semuaKombinasi = new ArrayList<>();

    public List<Makanan> generateMenu(
            List<Makanan> daftarMakanan,
            int targetKalori,
            String alergi) {

        List<Makanan> sarapan = filterKategori(daftarMakanan, "SARAPAN", alergi);
        List<Makanan> siang = filterKategori(daftarMakanan, "MAKAN_SIANG", alergi);
        List<Makanan> malam = filterKategori(daftarMakanan, "MAKAN_MALAM", alergi);

        semuaKombinasi = new ArrayList<>();
        backtracking(sarapan, siang, malam, 0, new ArrayList<>(), 0);

        if (semuaKombinasi.isEmpty()) {
            return new ArrayList<>();
        }

        // Cari selisih kalori terkecil yang mungkin dicapai dari seluruh kombinasi
        int selisihTerbaik = Integer.MAX_VALUE;
        for (KombinasiHasil k : semuaKombinasi) {
            int selisih = Math.abs(targetKalori - k.totalKalori);
            if (selisih < selisihTerbaik) {
                selisihTerbaik = selisih;
            }
        }

        // Kumpulkan semua kombinasi yang selisihnya masih dalam toleransi dari yang terbaik
        List<List<Makanan>> kandidatTerbaik = new ArrayList<>();
        for (KombinasiHasil k : semuaKombinasi) {
            int selisih = Math.abs(targetKalori - k.totalKalori);
            if (selisih <= selisihTerbaik + TOLERANSI_KALORI) {
                kandidatTerbaik.add(k.menu);
            }
        }

        // Pilih salah satu kandidat secara acak, supaya rekomendasi tidak itu-itu saja
        return kandidatTerbaik.get(random.nextInt(kandidatTerbaik.size()));
    }

    private void backtracking(
            List<Makanan> sarapan,
            List<Makanan> siang,
            List<Makanan> malam,
            int slot,
            List<Makanan> sementara,
            int totalKalori) {

        if (slot == 3) {
            semuaKombinasi.add(new KombinasiHasil(new ArrayList<>(sementara), totalKalori));
            return;
        }

        List<Makanan> pilihanSlotIni;

        if (slot == 0) {
            pilihanSlotIni = sarapan;
        } else if (slot == 1) {
            pilihanSlotIni = siang;
        } else {
            pilihanSlotIni = malam;
        }

        for (Makanan makanan : pilihanSlotIni) {

            sementara.add(makanan);

            backtracking(
                    sarapan, siang, malam,
                    slot + 1,
                    sementara,
                    totalKalori + makanan.getKalori()
            );

            sementara.remove(sementara.size() - 1);

        }

    }

    private List<Makanan> filterKategori(List<Makanan> daftar, String kategori, String alergi) {

        List<Makanan> hasil = new ArrayList<>();

        for (Makanan m : daftar) {
            if (m.getKategori().equalsIgnoreCase(kategori) && amanDimakan(m, alergi)) {
                hasil.add(m);
            }
        }

        return hasil;
    }

    private boolean amanDimakan(Makanan makanan, String alergiUser) {

        if (alergiUser == null || alergiUser.trim().isEmpty())
            return true;

        if (makanan.getAlergen() == null || makanan.getAlergen().trim().isEmpty())
            return true;

        String[] daftarAlergiUser = alergiUser.toLowerCase().split(",");
        String[] daftarAlergenMakanan = makanan.getAlergen().toLowerCase().split(",");

        for (String a : daftarAlergiUser) {
            for (String b : daftarAlergenMakanan) {
                if (a.trim().equals(b.trim())) {
                    return false;
                }
            }
        }

        return true;
    }

    public List<Makanan> generateMenuUser(int targetKalori, String alergi) {

        List<Makanan> daftar = makananDAO.getSemuaMakanan();

        return generateMenu(daftar, targetKalori, alergi);

    }

    // Kelas kecil untuk menyimpan satu kombinasi hasil backtracking beserta total kalorinya.
    private static class KombinasiHasil {
        List<Makanan> menu;
        int totalKalori;

        KombinasiHasil(List<Makanan> menu, int totalKalori) {
            this.menu = menu;
            this.totalKalori = totalKalori;
        }
    }

}
