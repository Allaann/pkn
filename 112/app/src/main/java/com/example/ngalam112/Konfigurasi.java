package com.example.ngalam112;

public class Konfigurasi {
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_ADD ="http://192.168.1.10/modul5/insert.php";
    public static final String URL_GET_ALL = "http://192.168.1.10/modul5/read.php";
    //public static final String URL_GET_MHS = "http://192.168.1.4/modul5/select.php?id=";
    //public static final String URL_UPDATE_MHS = "http://192.168.1.4/modul5/update.php";
    //public static final String URL_DELETE_MHS = "http://192.168.1.4/modul5/delete.php?id=";
    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Script PHP
    public static final String KEY_ID = "id_report";
    public static final String KEY_NO_TIKET = "no_tiket";
    public static final String KEY_PELAPOR = "nama_pelapor";
    public static final String KEY_PETUGAS = "nama_petugas";
    public static final String KEY_KEJADIAN = "kejadian";
    public static final String KEY_LOKASI_KEJADIAN = "lokasi_kejadian";
    public static final String KEY_TANGGAL = "tanggal";
    public static final String KEY_TINDAK_LANJUT = "tindak_lanjut";
    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_NO_TIKET = "no_tiket";
    public static final String TAG_PELAPOR = "nama_pelapor";
    public static final String TAG_PETUGAS = "nama_petugas";
    public static final String TAG_KEJADIAN = "kejadian";
    public static final String TAG_LOKASI_KEJADIAN = "lokasi_kejadian";
    public static final String TAG_TANGGAL = "tanggal";
    public static final String TAG_TINDAK_LANJUT = "tindak_lanjut";
    //ID REPORT
    public static final String REPORT_ID = "report_id";
}