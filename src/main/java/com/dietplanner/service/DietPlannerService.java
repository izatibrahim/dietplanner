package com.dietplanner.service;

import com.dietplanner.model.Makanan;
import com.dietplanner.dao.MakananDAO;

import java.util.ArrayList;
import java.util.List;

public class DietPlannerService {

    private List<Makanan> hasilTerbaik = new ArrayList<>();
    private int selisihTerbaik = Integer.MAX_VALUE;

    private MakananDAO makananDAO = new MakananDAO();

    public List<Makanan> generateMenu(
            List<Makanan> daftarMakanan,
            int targetKalori,
            String alergi) {

        hasilTerbaik.clear();
        selisihTerbaik = Integer.MAX_VALUE;
 
            List<Makanan> sarapan = filterKategori(daftarMakanan, "SARAPAN", alergi);
            List<Makanan> siang = filterKategori(daftarMakanan, "MAKAN_SIANG", alergi);
            List<Makanan> malam = filterKategori(daftarMakanan, "MAKAN_MALAM", alergi);

        backtracking(sarapan, siang, malam, targetKalori, 0, new ArrayList<>(), 0);

        return hasilTerbaik;
    }

    private void backtracking(
            List<Makanan> sarapan,
            List<Makanan> siang,
            List<Makanan> malam,
            int targetKalori,
            int slot,
            List<Makanan> sementara,
            int totalKalori) {

        if (slot == 3) {

            int selisih = Math.abs(targetKalori - totalKalori);

            if (selisih < selisihTerbaik) {
                selisihTerbaik = selisih;
                hasilTerbaik = new ArrayList<>(sementara);
            }

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
                    targetKalori,
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

}