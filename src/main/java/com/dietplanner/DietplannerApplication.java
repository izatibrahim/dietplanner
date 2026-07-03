package com.dietplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DietplannerApplication {

    public static void main(String[] args) {
        // Sebelumnya: menu.tampilMenu() -> aplikasi console.
        // Sekarang dijalankan sebagai web app (Spring MVC + Thymeleaf).
        // MainMenu.java (console) tidak dipanggil lagi, tapi boleh tetap
        // disimpan di project sebagai referensi/lampiran laporan.
        SpringApplication.run(DietplannerApplication.class, args);
    }
}
