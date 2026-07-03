# 🥗 Diet Planner

Aplikasi web perencana diet berbasis **Java Spring Boot** yang membantu pengguna
menghitung kebutuhan kalori harian (BMI, BMR, TDEE), mendapatkan rekomendasi
menu makanan otomatis (algoritma _backtracking_) sesuai target kalori dan
alergi, serta memantau progres berat badan dari waktu ke waktu lengkap dengan
sistem _streak_ harian.

---

## ✨ Fitur

- **Login & Register** — autentikasi sederhana berbasis session, password di-hash (SHA-256).
- **Dashboard** — ringkasan BMI, BMR, TDEE, target kalori harian, dan streak konsistensi.
- **Generate Menu Otomatis** — algoritma backtracking mencari kombinasi sarapan, makan siang, dan makan malam yang paling mendekati target kalori, sekaligus memfilter makanan yang mengandung alergen pengguna.
- **Riwayat Menu** — daftar menu yang pernah disimpan per hari.
- **Progress Berat Badan** — catat berat badan berkala, tampil sebagai grafik tren (naik/turun) memakai Chart.js.
- **Streak** 🔥 — menghitung hari berturut-turut pengguna generate & simpan menu, mendorong konsistensi seperti pada aplikasi habit-tracking.
- **Profil** — lihat & edit data diri (tinggi, berat, target, alergi).
- **CRUD Data Makanan** — kelola daftar makanan beserta info gizi (kalori, protein, karbohidrat, lemak) dan kategori (sarapan/siang/malam/snack).
- **Responsif** — tampilan menyesuaikan otomatis untuk HP (bottom navigation), tablet, dan laptop/desktop (sidebar).

---

## 🛠️ Teknologi

| Layer           | Teknologi                                   |
| --------------- | ------------------------------------------- |
| Bahasa          | Java 21                                     |
| Framework       | Spring Boot (Spring MVC)                    |
| Template Engine | Thymeleaf                                   |
| Styling         | Bootstrap 5 + CSS custom                    |
| Grafik          | Chart.js                                    |
| Database        | MySQL                                       |
| Akses Data      | JDBC murni (`PreparedStatement`, tanpa ORM) |
| Build Tool      | Maven                                       |

---

## 🗂️ Struktur Project

```
src/main/java/com/dietplanner/
├── config/          # DatabaseConnection, SessionInterceptor, WebConfig
├── controller/       # Auth, Dashboard, User, Makanan, Menu, Progress
├── dao/               # Akses database (CRUD) tiap entitas
├── model/             # User, Makanan, MenuHarian, Progress
├── service/           # DietPlannerService (algoritma backtracking)
├── utils/             # BMIUtil, KaloriUtil, PasswordUtil, StreakUtil
└── view/               # MainMenu.java (versi console, arsip)

src/main/resources/
├── static/css/style.css
└── templates/
    ├── auth/           # login, register
    ├── dashboard.html
    ├── user/            # profil, form edit
    ├── makanan/         # list, form
    ├── menu/            # generate, riwayat
    ├── progress/        # grafik berat badan
    └── fragments/       # layout (sidebar/bottom-nav) & head
```

---

## 🚀 Cara Menjalankan

### 1. Prasyarat

- JDK 21+
- Maven
- MySQL (XAMPP/Laragon/dsb.)

### 2. Setup Database

Buat database `dietplanner`, lalu jalankan skrip pembuatan tabel dasar
(`user`, `makanan`, `menu_harian`) dan skrip migrasi tambahan berikut di
phpMyAdmin (tab **SQL**):

```
migration_login_progress.sql
```

Skrip ini menambahkan kolom `username`/`password` pada tabel `user`, dan
membuat tabel baru `progress` untuk pelacakan berat badan.

### 3. Konfigurasi Koneksi

Sesuaikan kredensial database di:

```
src/main/java/com/dietplanner/config/DatabaseConnection.java
```

### 4. Jalankan Aplikasi

```bash
mvn spring-boot:run
```

Buka **http://localhost:8080** di browser. Klik **Daftar** untuk membuat akun pertama.

---

## 🧮 Rumus yang Digunakan

- **BMI** = berat (kg) / (tinggi (m))²
- **BMR** (Mifflin-St Jeor):
  - Laki-laki: `88.362 + (13.397 × BB) + (4.799 × TB) − (5.677 × Umur)`
  - Perempuan: `447.593 + (9.247 × BB) + (3.098 × TB) − (4.330 × Umur)`
- **TDEE** = BMR × 1.375 (asumsi aktivitas ringan-sedang)
- **Target Kalori**:
  - Bulking: TDEE + 500 kkal
  - Cutting: TDEE − 500 kkal
  - Maintenance: TDEE

---

## 👤 Pengembang

Dikembangkan sebagai proyek tugas mata kuliah Algoritma dan struktur data ii

---

## 📄 Lisensi

Proyek ini dibuat untuk keperluan akademik.
