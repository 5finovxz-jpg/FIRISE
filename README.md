# FIRISE Native Android v2.0

FIRISE adalah aplikasi Android native (bukan membuka index.html) untuk school + self-development.

## Fitur yang sudah dimasukkan di versi ini
- Dashboard / Apa yang harus dilakukan hari ini
- Motivasi harian yang berubah
- Fakta menarik harian
- Bimbel semua mapel + progres bab + bank soal/simulasi
- Rekap nilai, target, analisis, laporan
- Tugas & kalender
- Investasi/saham + simulasi + tabungan/pengeluaran
- Latihan sholat bertahap
- Latihan ngaji bertahap
- Latihan wudhu
- Karate Academy (kihon, kata, kumite, footwork, Shotokan)
- Video harian / berita / makanan sehat sebagai modul sumber
- Profil
- Input akun Google (penyimpanan email lokal pada versi ini)
- PIN aplikasi (penyimpanan lokal)
- Tambah fitur custom
- Backup/cloud sync disiapkan sebagai fondasi

## Penting tentang Google Login & Cloud
Login Google sungguhan dan sinkronisasi antar-perangkat membutuhkan konfigurasi OAuth/Firebase milik pengembang. UI dan tempat fiturnya sudah disiapkan, tetapi tidak boleh berpura-pura sudah terhubung tanpa kredensial backend.

## Cara membuat APK lewat GitHub Actions
1. Buat/upload project ini ke repository GitHub.
2. Pastikan branch bernama `main`.
3. Buka tab **Actions**.
4. Pilih workflow **Build FIRISE APK**.
5. Tekan **Run workflow**.
6. Setelah selesai, buka hasil workflow dan download artifact **FIRISE-debug-apk**.
7. Di dalam ZIP artifact ada `app-debug.apk` yang bisa dipasang di Android.

Versi ini sengaja memakai Android native Java tanpa WebView.
