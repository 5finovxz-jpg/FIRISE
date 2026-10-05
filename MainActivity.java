package com.firise.app;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import java.text.*;
import java.util.*;

public class MainActivity extends Activity {
    FIRISEView ui;
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.rgb(7,16,31));
        getWindow().setNavigationBarColor(Color.rgb(7,16,31));
        ui = new FIRISEView(this);
        setContentView(ui);
    }
}

class FIRISEView extends ViewGroup {
    final int BG=Color.rgb(7,16,31), PANEL=Color.rgb(13,25,48), PANEL2=Color.rgb(18,38,66), BLUE=Color.rgb(72,168,255), CYAN=Color.rgb(84,232,255), TEXT=Color.WHITE, MUTED=Color.rgb(156,175,201), GREEN=Color.rgb(74,210,130);
    final android.content.SharedPreferences prefs;
    Paint paint=new Paint(3);
    String page="Beranda";
    TextView title;
    LinearLayout menu, body;
    ScrollView scroll;
    String[] pages={"Beranda","Akademik","Nilai","Tugas","Keuangan","Ibadah","Karate","Sumber","Profil","Lainnya"};

    FIRISEView(Context c){ super(c); setWillNotDraw(false); prefs=c.getSharedPreferences("firise",Context.MODE_PRIVATE); build(); }

    TextView tv(String s,float size,int color){ TextView t=new TextView(getContext()); t.setText(s); t.setTextColor(color); t.setTextSize(size); t.setPadding(8,7,8,7); return t; }
    GradientDrawable bg(int color,int radius){ GradientDrawable g=new GradientDrawable(); g.setColor(color); g.setCornerRadius(radius); g.setStroke(1,Color.rgb(28,54,86)); return g; }
    Button btn(String s){ Button b=new Button(getContext()); b.setText(s); b.setTextColor(TEXT); b.setTextSize(13); b.setAllCaps(false); b.setBackground(bg(PANEL2,22)); b.setPadding(12,3,12,3); return b; }
    LinearLayout card(String heading,String text){
        LinearLayout box=new LinearLayout(getContext()); box.setOrientation(LinearLayout.VERTICAL); box.setPadding(15,13,15,13); box.setBackground(bg(PANEL,24));
        TextView h=tv(heading,17,TEXT); h.setTypeface(null,1); box.addView(h);
        TextView x=tv(text,13,MUTED); box.addView(x);
        body.addView(box,new LinearLayout.LayoutParams(-1,-2));
        LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)box.getLayoutParams(); lp.setMargins(0,0,0,10); box.setLayoutParams(lp);
        return box;
    }
    void build(){
        setBackgroundColor(BG);
        title=tv("FIRISE",24,TEXT); title.setTypeface(null,1); title.setGravity(Gravity.CENTER_VERTICAL); addView(title);
        menu=new LinearLayout(getContext()); menu.setOrientation(LinearLayout.HORIZONTAL); menu.setPadding(7,3,7,3); menu.setBackgroundColor(Color.rgb(8,20,38));
        for(String s:pages){ Button b=btn(s); b.setTextSize(11); b.setOnClickListener(v->{page=((Button)v).getText().toString(); render();}); menu.addView(b,new LinearLayout.LayoutParams(-2,58)); }
        addView(menu);
        body=new LinearLayout(getContext()); body.setOrientation(LinearLayout.VERTICAL); body.setPadding(14,14,14,100);
        scroll=new ScrollView(getContext()); scroll.setFillViewport(true); scroll.addView(body); addView(scroll); render();
    }
    void header(String h,String sub){ TextView a=tv(h,25,TEXT); a.setTypeface(null,1); body.addView(a); body.addView(tv(sub,13,MUTED)); body.addView(space(8)); }
    TextView space(int h){ TextView x=new TextView(getContext()); x.setHeight(h); return x; }
    void addActionCard(String heading,String text,String action){ LinearLayout c=card(heading,text); Button b=btn(action); b.setOnClickListener(v->action(action)); c.addView(b,new LinearLayout.LayoutParams(-1,52)); }
    void action(String a){
        if(a.contains("Motivasi")) motivation();
        else if(a.contains("Google")) google();
        else if(a.contains("Sholat")) prayer();
        else if(a.contains("Ngaji")) quran();
        else if(a.contains("Wudhu")) wudhu();
        else if(a.contains("Karate")) karateLesson();
        else if(a.contains("Fakta")) fact();
        else if(a.contains("Makanan")) food();
        else if(a.contains("Tambah")) customFeature();
        else if(a.contains("PIN")) pin();
        else Toast.makeText(getContext(),a,Toast.LENGTH_SHORT).show();
    }
    void render(){ title.setText("FIRISE  •  "+page); body.removeAllViews();
        if(page.equals("Beranda")) dashboard(); else if(page.equals("Akademik")) academic(); else if(page.equals("Nilai")) grades(); else if(page.equals("Tugas")) tasks(); else if(page.equals("Keuangan")) finance(); else if(page.equals("Ibadah")) faith(); else if(page.equals("Karate")) karate(); else if(page.equals("Sumber")) resources(); else if(page.equals("Profil")) profile(); else more();
    }
    void dashboard(){
        header("Selamat datang 👋","Personal School & Life OS • semua progres dalam satu tempat");
        addActionCard("🔥 Motivasi hari ini","Motivasi berubah setiap kali kamu membukanya.","Tampilkan Motivasi");
        card("📌 Apa yang harus aku lakukan hari ini?","1. Ikuti jadwal bimbel • 2. Selesaikan tugas terdekat • 3. Pelajari 1 materi yang belum dikuasai • 4. Catat progres.");
        card("📊 Ringkasan","Nilai • pemahaman materi • tugas • streak • tabungan • target masa depan.");
        addActionCard("🧠 Fakta Menarik Harian","Sains, tubuh, alam, teknologi, ekonomi, sejarah, hewan, luar angkasa, dan lainnya.","Fakta berikutnya");
        card("🏆 Level siswa","Level "+prefs.getInt("level",1)+" • XP "+prefs.getInt("xp",0)+" • Streak "+prefs.getInt("streak",0)+" hari");
        card("🗺️ Roadmap masa depan","Target sekolah → kemampuan → karier → cita-cita. Pecah target besar menjadi langkah kecil.");
    }
    void academic(){
        header("🎓 Akademik","Bimbel semua mata pelajaran • progres bab • latihan • evaluasi");
        String[] s={"Matematika","IPA","IPS","Bahasa Indonesia","Bahasa Inggris","PPKn","PAI","Informatika","PJOK","Seni Budaya"};
        for(String x:s) card(x,"Progress bab • materi lengkap • setelah bab selesai: 20 soal ABCD • skor & pembahasan jawaban salah");
        addActionCard("🤖 AI Academic Coach","Coach untuk menjelaskan materi, membuat jadwal, memberi latihan, dan membantu mengecek pemahaman.","Buka Academic Coach");
        card("📅 Jadwal bimbel","Senin IPS • Selasa B. Indonesia + PPKn • Rabu Informatika • Kamis PAI + Seni Budaya • Jumat Matematika • Sabtu PJOK + Inggris • Minggu IPA.");
        card("📝 Bank soal & simulasi","Latihan per bab, simulasi ujian, skor, riwayat, dan analisis kelemahan.");
    }
    void grades(){ header("📊 Nilai","Rekap semester • target • analisis naik/turun • laporan"); card("Rekap nilai","Tambahkan nilai per mata pelajaran dan semester."); card("🎯 Target nilai","Atur target setiap mata pelajaran dan lihat progress menuju target."); card("📈 Analisis","Bandingkan nilai terbaru dengan nilai sebelumnya untuk melihat peningkatan/penurunan."); card("📋 Laporan bulanan","Nilai • pemahaman • tugas • streak • kebiasaan belajar."); }
    void tasks(){ header("✅ Tugas & Kalender","Semua deadline, ujian, dan agenda"); card("Daftar tugas","Tambah tugas, deadline, status, dan prioritas."); card("🗓 Kalender akademik","Catat ujian, kegiatan sekolah, dan agenda pribadi."); card("🧰 Pembuat tugas","Kerangka dokumen, presentasi, dan gambar pembelajaran."); }
    void finance(){ header("💰 Keuangan","Belajar mengelola uang dan investasi secara edukatif"); card("💵 Tabungan","Saldo, target tabungan, dan progress."); card("💸 Pemasukan & pengeluaran","Catat transaksi dan lihat kebiasaan keuangan."); card("📚 Investasi & saham","Return, risiko, diversifikasi, dividen, saham, crypto, dan prinsip syariah."); card("🧮 Simulasi investasi","Uji skenario tanpa memakai uang nyata."); }
    void faith(){
        header("🕌 Ibadah","Latihan bertahap • fokus pada pemahaman dan praktik yang benar");
        addActionCard("🕋 Latihan Sholat","Panduan dasar: niat, takbir, berdiri, rukuk, i'tidal, sujud, duduk, tasyahud, salam. Bisa dipelajari bertahap.","Mulai Latihan Sholat");
        addActionCard("📖 Latihan Ngaji","Mulai dari huruf hijaiyah, harakat, sambung huruf, lalu bacaan bertahap.","Mulai Latihan Ngaji");
        addActionCard("🧼 Latihan Wudhu","Urutan wudhu, hal yang membatalkan, dan doa yang dipelajari bertahap.","Mulai Latihan Wudhu");
        card("⏰ Pengingat ibadah","Pengingat sholat dan belajar ngaji dapat diaktifkan pada versi notifikasi.");
    }
    void karate(){
        header("🥋 Karate Academy","Latihan aman, bertahap, dan sebaiknya dengan pelatih");
        addActionCard("🥋 Latihan Karate","Kihon, kata, kumite, footwork, timing, jarak, taktik pertandingan, dan aturan dasar.","Mulai Latihan Karate");
        card("Kihon","Teknik dasar, kuda-kuda, keseimbangan, kontrol, dan ketepatan."); card("Kata","Urutan gerakan dan latihan bersama pelatih."); card("Kumite","Timing, jarak, footwork, taktik, kontrol, dan sportivitas."); card("Shotokan","Materi Shotokan dari dasar sampai lanjutan.");
    }
    void resources(){
        header("🧠 Sumber Harian","Konten yang dapat diperbarui saat FIRISE terhubung internet");
        addActionCard("🎥 Video harian","Motivasi • masa depan & karier • keuangan • pembelajaran.","Buka Video Hari Ini");
        card("📰 Berita terbaru","Sains/teknologi, pendidikan, ekonomi, olahraga, Indonesia & dunia dari sumber tepercaya.");
        addActionCard("🥗 Makanan sehat","Ide sarapan, makan siang, makan malam, dan camilan seimbang untuk pelajar dan olahraga.","Lihat Rekomendasi Makanan");
        card("🔎 Pencarian semua materi","Cari mapel, bab, catatan, tugas, dan fitur FIRISE.");
    }
    void profile(){
        header("👤 Profil","Identitas, akun, keamanan, dan personalisasi");
        card("M. FINO SEPTIANDA PERDANA","26 September 2013 • Indonesia, Tembilahan • Islam");
        card("🎯 Cita-cita","Atlet MMA profesional dengan dasar karate dan menjadi konglomerat.");
        addActionCard("🔐 Akun Google","Masukkan akun Google untuk profil. Sinkronisasi cloud membutuhkan konfigurasi backend Google/Firebase.","Masukkan Akun Google");
        addActionCard("🔒 Keamanan","Gunakan PIN aplikasi agar data pribadi lebih terlindungi.","Atur PIN");
        card("🎨 Personalisasi","Tema, warna, background foto, dan susunan beranda.");
    }
    void more(){ header("⚙️ Lainnya","Fitur tambahan FIRISE"); card("🏆 Badge & level","Pencapaian, XP, streak, dan level siswa."); addActionCard("🧩 Tambahkan fitur sendiri","Buat nama fitur dan fungsi yang kamu inginkan.","Tambah Fitur"); card("☁️ Backup & sinkronisasi","Backup lokal dan cloud sync untuk nilai, progres, tabungan, catatan, target, pengaturan, dan pencapaian."); card("📜 Riwayat","Riwayat seluruh pembelajaran dan aktivitas."); }

    void motivation(){ String[] m={"Kamu tidak harus langsung hebat. Yang penting terus berkembang sedikit demi sedikit.","Belajar hari ini adalah investasi untuk dirimu di masa depan.","Jangan bandingkan awalmu dengan hasil orang lain. Fokus naik satu tingkat setiap hari.","Disiplin kecil yang dilakukan terus-menerus bisa menghasilkan perubahan besar.","Kalau belum paham, bukan berarti kamu tidak bisa. Coba dengan cara penjelasan yang berbeda."}; dialog("🔥 Motivasi Hari Ini",m[new Random().nextInt(m.length)]); }
    void fact(){ String[] f={"Petir dapat memanaskan udara di sekitarnya hingga jauh lebih panas daripada permukaan Matahari.","Jantung manusia berdetak sekitar 100 ribu kali dalam sehari, meski angka sebenarnya bervariasi.","Cahaya Matahari membutuhkan sekitar 8 menit untuk mencapai Bumi.","Otak manusia menggunakan banyak energi meski beratnya hanya sebagian kecil dari berat tubuh."}; dialog("🧠 Fakta Menarik",f[new Random().nextInt(f.length)]); }
    void google(){ final EditText e=new EditText(getContext()); e.setHint("contoh: nama@gmail.com"); e.setInputType(33); LinearLayout box=new LinearLayout(getContext()); box.setPadding(25,5,25,5); box.addView(e); new AlertDialog.Builder(getContext()).setTitle("Masukkan Akun Google").setMessage("Ini menyimpan email profil secara lokal. Login Google & cloud sync sungguhan perlu konfigurasi OAuth/Firebase.").setView(box).setNegativeButton("Batal",null).setPositiveButton("Simpan",(d,w)->{prefs.edit().putString("google",e.getText().toString()).apply(); Toast.makeText(getContext(),"Akun disimpan di perangkat",Toast.LENGTH_SHORT).show();}).show(); }
    void pin(){ final EditText e=new EditText(getContext()); e.setInputType(2|16); e.setHint("4–6 angka"); new AlertDialog.Builder(getContext()).setTitle("Atur PIN FIRISE").setView(e).setNegativeButton("Batal",null).setPositiveButton("Simpan",(d,w)->{String p=e.getText().toString(); if(p.length()>=4){prefs.edit().putString("pin",p).apply(); Toast.makeText(getContext(),"PIN tersimpan",Toast.LENGTH_SHORT).show();} else Toast.makeText(getContext(),"PIN minimal 4 angka",Toast.LENGTH_SHORT).show();}).show(); }
    void prayer(){ String[] steps={"1. Bersuci dan menghadap kiblat.","2. Niat sholat sesuai sholat yang dikerjakan.","3. Takbiratul ihram lalu berdiri dengan tenang.","4. Baca Al-Fatihah dan bacaan yang dipelajari.","5. Rukuk → i'tidal → sujud → duduk di antara dua sujud.","6. Ulangi sesuai jumlah rakaat.","7. Tasyahud akhir lalu salam.","Catatan: pelajari bacaan sedikit demi sedikit dan tanyakan kepada guru/ustaz jika ada yang belum yakin."}; dialog("🕋 Latihan Sholat",join(steps)); }
    void quran(){ dialog("📖 Latihan Ngaji","Tahap 1: kenali huruf hijaiyah.\n\nTahap 2: latihan harakat fathah, kasrah, dammah.\n\nTahap 3: latihan menyambung huruf.\n\nTahap 4: latihan membaca kata dan ayat pendek.\n\nTahap 5: belajar tajwid dasar bersama guru/ustaz."); }
    void wudhu(){ dialog("🧼 Latihan Wudhu","Urutan belajar: niat → cuci tangan → berkumur & membersihkan hidung → basuh wajah → basuh tangan sampai siku → usap kepala/telinga → basuh kaki sampai mata kaki. Pelajari detail sesuai tuntunan guru/ustaz."); }
    void karateLesson(){ dialog("🥋 Latihan Karate","Pemanasan ringan → kihon dasar → footwork → latihan teknik dengan kontrol → pendinginan. Untuk kumite, gunakan perlengkapan keselamatan dan berlatih bersama pelatih. Hindari latihan keras sendirian."); }
    void food(){ dialog("🥗 Rekomendasi Makanan","Contoh pola seimbang: nasi/umbi atau sumber karbohidrat + lauk berprotein + sayur + buah + air putih. Pilihan sederhana: nasi + ayam/ikan + sayur; sarapan telur + nasi/roti + buah; camilan bisa buah atau makanan rumahan. Tidak perlu diet ketat—fokus makan teratur dan cukup untuk tumbuh serta beraktivitas."); }
    void customFeature(){ final EditText e=new EditText(getContext()); e.setHint("Contoh: Jadwal latihan MMA"); new AlertDialog.Builder(getContext()).setTitle("Tambah Fitur FIRISE").setMessage("Tulis nama fitur yang ingin kamu tambahkan.").setView(e).setNegativeButton("Batal",null).setPositiveButton("Tambah",(d,w)->{if(e.getText().length()>0){card("🧩 "+e.getText().toString(),"Fitur custom ditambahkan ke rancangan FIRISE.");}}).show(); }
    void dialog(String title,String text){ new AlertDialog.Builder(getContext()).setTitle(title).setMessage(text).setPositiveButton("Selesai",null).show(); }
    String join(String[] a){StringBuilder s=new StringBuilder(); for(String x:a)s.append(x).append("\n\n"); return s.toString();}

    @Override protected void onMeasure(int w,int h){ int W=MeasureSpec.getSize(w), H=MeasureSpec.getSize(h); setMeasuredDimension(W,H); title.measure(MeasureSpec.makeMeasureSpec(W,1073741824),MeasureSpec.makeMeasureSpec(68,1073741824)); menu.measure(MeasureSpec.makeMeasureSpec(W,1073741824),MeasureSpec.makeMeasureSpec(60,1073741824)); scroll.measure(MeasureSpec.makeMeasureSpec(W,1073741824),MeasureSpec.makeMeasureSpec(H-128,1073741824)); }
    @Override protected void onLayout(boolean c,int l,int t,int r,int b){ int W=r-l; title.layout(0,0,W,68); menu.layout(0,68,W,128); scroll.layout(0,128,W,b-t); }
}
