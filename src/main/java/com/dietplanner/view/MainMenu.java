package com.dietplanner.view;

import com.dietplanner.dao.UserDAO;
import com.dietplanner.model.User;
import com.dietplanner.dao.MakananDAO;
import com.dietplanner.model.Makanan;
import com.dietplanner.dao.MenuHarianDAO;
import com.dietplanner.model.MenuHarian;
import com.dietplanner.service.DietPlannerService;
import com.dietplanner.utils.KaloriUtil;
import com.dietplanner.utils.BMIUtil;

import java.util.List;
import java.util.Scanner;

public class MainMenu {

    Scanner input = new Scanner(System.in);
    private UserDAO userDAO = new UserDAO();
    private MakananDAO makananDAO = new MakananDAO();
    private MenuHarianDAO menuHarianDAO = new MenuHarianDAO();
    private DietPlannerService dietPlannerService = new DietPlannerService();

    public void tampilMenu() {

        int pilih;

        do {

            System.out.println("\n==============================");
            System.out.println("      DIET PLANNER");
            System.out.println("==============================");
            System.out.println("1. Tambah User");
            System.out.println("2. Lihat User");
            System.out.println("3. Tambah Makanan");
            System.out.println("4. Lihat Makanan");
            System.out.println("5. Generate Menu Diet");
            System.out.println("6. Edit User");
            System.out.println("7. Hapus User");
            System.out.println("8. Edit Makanan");
            System.out.println("9. Hapus Makanan");
            System.out.println("10. Lihat Riwayat Menu");
            System.out.println("0. Keluar");

            pilih = bacaInt("Pilih : ");

            switch (pilih) {

                case 1:
                    tambahUser();
                    break;

                case 2:
                    lihatUser();
                    break;

                case 3:
                    tambahMakanan();
                    break;

                case 4:
                    lihatMakanan();
                    break;

                case 5:
                    generateMenu();
                    break;

                case 6:
                    editUser();
                    break;

                case 7:
                    hapusUser();
                    break;

                case 8:
                    editMakanan();
                    break;

                case 9:
                    hapusMakanan();
                    break;

                case 10:
                    lihatRiwayat();
                    break;

                case 0:
                    System.out.println("Terima kasih...");
                    break;

                default:
                    System.out.println("Menu tidak tersedia.");

            }

        } while (pilih != 0);

    }

    private int bacaInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String teks = input.nextLine();
            try {
                return Integer.parseInt(teks.trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka bulat. Coba lagi.");
            }
        }
    }

    private double bacaDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String teks = input.nextLine();
            try {
                return Double.parseDouble(teks.trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka. Coba lagi.");
            }
        }
    }

    private String bacaKategori(String prompt) {
        String[] valid = {"SARAPAN", "MAKAN_SIANG", "MAKAN_MALAM", "SNACK"};
        while (true) {
            System.out.print(prompt);
            String teks = input.nextLine().trim();
            for (String v : valid) {
                if (v.equalsIgnoreCase(teks)) {
                    return v;
                }
            }
            System.out.println("Kategori harus salah satu dari: SARAPAN, MAKAN_SIANG, MAKAN_MALAM, SNACK. Coba lagi.");
        }
    }

    private void tambahUser() {

        User user = new User();

        System.out.print("Nama : ");
        user.setNama(input.nextLine());

        user.setUmur(bacaInt("Umur : "));

        System.out.print("Jenis Kelamin (L/P) : ");
        user.setJenisKelamin(input.nextLine());

        user.setTinggiBadan(bacaDouble("Tinggi Badan : "));
        user.setBeratBadan(bacaDouble("Berat Badan : "));

        System.out.print("Target (Bulking/Cutting) : ");
        user.setTarget(input.nextLine());

        System.out.print("Alergi : ");
        user.setAlergi(input.nextLine());

        user.setTargetKalori(bacaInt("Target Kalori : "));

        if (userDAO.tambahUser(user)) {
            System.out.println("User berhasil ditambahkan.");
        } else {
            System.out.println("Gagal menambahkan user.");
        }

    }

    private void lihatUser() {

        var daftar = userDAO.getSemuaUser();

        for (User u : daftar) {

            System.out.println("--------------------------------");
            System.out.println("ID : " + u.getId());
            System.out.println("Nama : " + u.getNama());
            System.out.println("Umur : " + u.getUmur());
            System.out.println("JK : " + u.getJenisKelamin());
            System.out.println("TB : " + u.getTinggiBadan());
            System.out.println("BB : " + u.getBeratBadan());
            System.out.println("Target : " + u.getTarget());
            System.out.println("Alergi : " + u.getAlergi());
            System.out.println("Kalori : " + u.getTargetKalori());

        }

    }

    private void tambahMakanan() {

        Makanan makanan = new Makanan();

        System.out.print("Nama Makanan : ");
        makanan.setNama(input.nextLine());

        makanan.setKategori(bacaKategori("Kategori (SARAPAN/MAKAN_SIANG/MAKAN_MALAM/SNACK) : "));

        makanan.setKalori(bacaInt("Kalori : "));
        makanan.setProtein(bacaDouble("Protein (gram) : "));
        makanan.setKarbohidrat(bacaDouble("Karbohidrat (gram) : "));
        makanan.setLemak(bacaDouble("Lemak (gram) : "));

        System.out.print("Alergen (pisahkan koma jika lebih dari satu, kosongkan jika tidak ada) : ");
        makanan.setAlergen(input.nextLine());

        if (makananDAO.tambahMakanan(makanan)) {
            System.out.println("Makanan berhasil ditambahkan.");
        } else {
            System.out.println("Gagal menambahkan makanan.");
        }

    }

    private void lihatMakanan() {

        var daftar = makananDAO.getSemuaMakanan();

        for (Makanan m : daftar) {

            System.out.println("--------------------------------");
            System.out.println("ID : " + m.getId());
            System.out.println("Nama : " + m.getNama());
            System.out.println("Kategori : " + m.getKategori());
            System.out.println("Kalori : " + m.getKalori());
            System.out.println("Protein : " + m.getProtein());
            System.out.println("Karbo : " + m.getKarbohidrat());
            System.out.println("Lemak : " + m.getLemak());
            System.out.println("Alergen : " + m.getAlergen());

        }

    }

    private void generateMenu() {

        var daftarUser = userDAO.getSemuaUser();

        if (daftarUser.isEmpty()) {
            System.out.println("Belum ada user. Tambahkan user terlebih dahulu.");
            return;
        }

        System.out.println("\nPilih User:");
        for (User u : daftarUser) {
            System.out.println(u.getId() + ". " + u.getNama());
        }

        int idUser = bacaInt("Masukkan ID User : ");

        User user = null;
        for (User u : daftarUser) {
            if (u.getId() == idUser) {
                user = u;
                break;
            }
        }

        if (user == null) {
            System.out.println("User tidak ditemukan.");
            return;
        }

        int bmr = KaloriUtil.hitungBMR(user.getJenisKelamin(), user.getBeratBadan(), user.getTinggiBadan(), user.getUmur());
        int tdee = KaloriUtil.hitungTDEE(bmr);
        int targetKalori = KaloriUtil.hitungTargetKalori(tdee, user.getTarget());

        double bmi = BMIUtil.hitungBMI(user.getBeratBadan(), user.getTinggiBadan());
        String kategoriBMI = BMIUtil.kategori(bmi);

        System.out.println("\n--------------------------------");
        System.out.println("BMI  : " + String.format("%.1f", bmi) + " (" + kategoriBMI + ")");
        System.out.println("BMR  : " + bmr + " kkal");
        System.out.println("TDEE : " + tdee + " kkal");
        System.out.println("Target Kalori Harian (" + user.getTarget() + ") : " + targetKalori + " kkal");
        System.out.println("--------------------------------");

        List<Makanan> hasil = dietPlannerService.generateMenuUser(targetKalori, user.getAlergi());

        if (hasil.isEmpty()) {
            System.out.println("Tidak ditemukan kombinasi menu. Pastikan ada makanan untuk tiap kategori (SARAPAN/MAKAN_SIANG/MAKAN_MALAM) yang aman dari alergi user.");
            return;
        }

        int totalKalori = 0;

        System.out.println("\nMenu Rekomendasi Hari Ini:");
        for (Makanan m : hasil) {
            System.out.println("- " + m.getKategori() + " : " + m.getNama() + " (" + m.getKalori() + " kkal)");
            totalKalori += m.getKalori();
        }
        System.out.println("Total Kalori : " + totalKalori + " kkal (target: " + targetKalori + " kkal)");

        System.out.print("\nSimpan menu ini ke riwayat? (y/n) : ");
        String simpan = input.nextLine();

        if (simpan.trim().equalsIgnoreCase("y")) {

            MenuHarian menu = new MenuHarian();
            menu.setUserId(user.getId());
            menu.setTanggal(java.time.LocalDate.now().toString());
            menu.setSarapan(hasil.get(0).getNama());
            menu.setMakanSiang(hasil.get(1).getNama());
            menu.setMakanMalam(hasil.get(2).getNama());
            menu.setTotalKalori(totalKalori);

            if (menuHarianDAO.simpan(menu)) {
                System.out.println("Menu berhasil disimpan ke riwayat.");
            } else {
                System.out.println("Gagal menyimpan menu.");
            }

        }

    }

    private void editUser() {

        var daftar = userDAO.getSemuaUser();

        if (daftar.isEmpty()) {
            System.out.println("Belum ada user.");
            return;
        }

        System.out.println("\nDaftar User:");
        for (User u : daftar) {
            System.out.println(u.getId() + ". " + u.getNama());
        }

        int id = bacaInt("Masukkan ID User yang akan diedit : ");

        User user = null;
        for (User u : daftar) {
            if (u.getId() == id) {
                user = u;
                break;
            }
        }

        if (user == null) {
            System.out.println("User tidak ditemukan.");
            return;
        }

        System.out.println("Kosongkan input (tekan Enter) jika tidak ingin mengubah data tersebut.");

        System.out.print("Nama (" + user.getNama() + ") : ");
        String nama = input.nextLine();
        if (!nama.isBlank()) user.setNama(nama);

        System.out.print("Umur (" + user.getUmur() + ") : ");
        String umur = input.nextLine();
        if (!umur.isBlank()) {
            try {
                user.setUmur(Integer.parseInt(umur.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Umur diabaikan karena bukan angka.");
            }
        }

        System.out.print("Jenis Kelamin (" + user.getJenisKelamin() + ") : ");
        String jk = input.nextLine();
        if (!jk.isBlank()) user.setJenisKelamin(jk);

        System.out.print("Tinggi Badan (" + user.getTinggiBadan() + ") : ");
        String tb = input.nextLine();
        if (!tb.isBlank()) {
            try {
                user.setTinggiBadan(Double.parseDouble(tb.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Tinggi badan diabaikan karena bukan angka.");
            }
        }

        System.out.print("Berat Badan (" + user.getBeratBadan() + ") : ");
        String bb = input.nextLine();
        if (!bb.isBlank()) {
            try {
                user.setBeratBadan(Double.parseDouble(bb.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Berat badan diabaikan karena bukan angka.");
            }
        }

        System.out.print("Target (" + user.getTarget() + ") : ");
        String target = input.nextLine();
        if (!target.isBlank()) user.setTarget(target);

        System.out.print("Alergi (" + user.getAlergi() + ") : ");
        String alergi = input.nextLine();
        if (!alergi.isBlank()) user.setAlergi(alergi);

        if (userDAO.updateUser(user)) {
            System.out.println("User berhasil diupdate.");
        } else {
            System.out.println("Gagal update user.");
        }

    }

    private void hapusUser() {

        var daftar = userDAO.getSemuaUser();

        if (daftar.isEmpty()) {
            System.out.println("Belum ada user.");
            return;
        }

        System.out.println("\nDaftar User:");
        for (User u : daftar) {
            System.out.println(u.getId() + ". " + u.getNama());
        }

        int id = bacaInt("Masukkan ID User yang akan dihapus : ");

        if (userDAO.hapusUser(id)) {
            System.out.println("User berhasil dihapus.");
        } else {
            System.out.println("Gagal menghapus user. Kemungkinan user ini masih punya riwayat menu tersimpan.");
        }

    }

    private void editMakanan() {

        var daftar = makananDAO.getSemuaMakanan();

        if (daftar.isEmpty()) {
            System.out.println("Belum ada makanan.");
            return;
        }

        System.out.println("\nDaftar Makanan:");
        for (Makanan m : daftar) {
            System.out.println(m.getId() + ". " + m.getNama() + " (" + m.getKategori() + ")");
        }

        int id = bacaInt("Masukkan ID Makanan yang akan diedit : ");

        Makanan makanan = null;
        for (Makanan m : daftar) {
            if (m.getId() == id) {
                makanan = m;
                break;
            }
        }

        if (makanan == null) {
            System.out.println("Makanan tidak ditemukan.");
            return;
        }

        System.out.println("Kosongkan input (tekan Enter) jika tidak ingin mengubah data tersebut.");

        System.out.print("Nama (" + makanan.getNama() + ") : ");
        String nama = input.nextLine();
        if (!nama.isBlank()) makanan.setNama(nama);

        System.out.print("Kategori (" + makanan.getKategori() + ") - isi SARAPAN/MAKAN_SIANG/MAKAN_MALAM/SNACK atau kosongkan : ");
        String kategori = input.nextLine();
        if (!kategori.isBlank()) {
            String[] valid = {"SARAPAN", "MAKAN_SIANG", "MAKAN_MALAM", "SNACK"};
            boolean cocok = false;
            for (String v : valid) {
                if (v.equalsIgnoreCase(kategori.trim())) {
                    makanan.setKategori(v);
                    cocok = true;
                    break;
                }
            }
            if (!cocok) {
                System.out.println("Kategori tidak valid, diabaikan.");
            }
        }

        System.out.print("Kalori (" + makanan.getKalori() + ") : ");
        String kalori = input.nextLine();
        if (!kalori.isBlank()) {
            try {
                makanan.setKalori(Integer.parseInt(kalori.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Kalori diabaikan karena bukan angka.");
            }
        }

        System.out.print("Protein (" + makanan.getProtein() + ") : ");
        String protein = input.nextLine();
        if (!protein.isBlank()) {
            try {
                makanan.setProtein(Double.parseDouble(protein.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Protein diabaikan karena bukan angka.");
            }
        }

        System.out.print("Karbohidrat (" + makanan.getKarbohidrat() + ") : ");
        String karbo = input.nextLine();
        if (!karbo.isBlank()) {
            try {
                makanan.setKarbohidrat(Double.parseDouble(karbo.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Karbohidrat diabaikan karena bukan angka.");
            }
        }

        System.out.print("Lemak (" + makanan.getLemak() + ") : ");
        String lemak = input.nextLine();
        if (!lemak.isBlank()) {
            try {
                makanan.setLemak(Double.parseDouble(lemak.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Lemak diabaikan karena bukan angka.");
            }
        }

        System.out.print("Alergen (" + makanan.getAlergen() + ") : ");
        String alergen = input.nextLine();
        if (!alergen.isBlank()) makanan.setAlergen(alergen);

        if (makananDAO.updateMakanan(makanan)) {
            System.out.println("Makanan berhasil diupdate.");
        } else {
            System.out.println("Gagal update makanan.");
        }

    }

    private void hapusMakanan() {

        var daftar = makananDAO.getSemuaMakanan();

        if (daftar.isEmpty()) {
            System.out.println("Belum ada makanan.");
            return;
        }

        System.out.println("\nDaftar Makanan:");
        for (Makanan m : daftar) {
            System.out.println(m.getId() + ". " + m.getNama() + " (" + m.getKategori() + ")");
        }

        int id = bacaInt("Masukkan ID Makanan yang akan dihapus : ");

        if (makananDAO.hapusMakanan(id)) {
            System.out.println("Makanan berhasil dihapus.");
        } else {
            System.out.println("Gagal menghapus makanan.");
        }

    }

    private void lihatRiwayat() {

        var daftar = menuHarianDAO.getSemuaRiwayat();

        if (daftar.isEmpty()) {
            System.out.println("Belum ada riwayat menu.");
            return;
        }

        for (MenuHarian m : daftar) {

            System.out.println("--------------------------------");
            System.out.println("Tanggal : " + m.getTanggal());
            System.out.println("User ID : " + m.getUserId());
            System.out.println("Sarapan : " + m.getSarapan());
            System.out.println("Makan Siang : " + m.getMakanSiang());
            System.out.println("Makan Malam : " + m.getMakanMalam());
            System.out.println("Total Kalori : " + m.getTotalKalori());

        }

    }

}