package com.example.ngalam112;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbReport extends SQLiteOpenHelper {
    private static SQLiteDatabase db;



    static abstract class MyColumns implements BaseColumns {
        static final String NamaTabel = "DataLaporan";
        static final String NoTiket = "NoTiket";
        static final String Kejadian = "Kejadian";
        static final String Petugas = "Petugas";
        static final String Pelapor = "Pelapor";
        static final String Lokasi = "Lokasi";
        static final String Tindak = "Tindak";
        static final String Tanggal = "Tanggal";
        static final String Foto = "GambarPic";


    }

    private static final String NamaDatabse = "dbreport.db";
    private static final int VersiDatabase = 1;
    public static final String COLUMN_STATUS = "status";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + MyColumns.NamaTabel +
            "(" + MyColumns.NoTiket + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + MyColumns.Kejadian + " TEXT NOT NULL, "
            + MyColumns.Petugas + " TEXT NOT NULL, "
            + MyColumns.Pelapor + " TEXT NOT NULL, "
            + MyColumns.Lokasi + " TEXT NOT NULL, "
            + MyColumns.Tindak + " TEXT NOT NULL, "
            + MyColumns.Tanggal + " TEXT NOT NULL, "
            + MyColumns.Foto + " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MyColumns.NamaTabel;

    DbReport(Context context) {
        super(context, NamaDatabse, null, VersiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

//    public boolean addLaporan(int status) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("no_tiket", MyColumns.NoTiket);
//        contentValues.put("kejadian", MyColumns.Kejadian);
//        contentValues.put("nama_petugas", MyColumns.Petugas);
//        contentValues.put("nama_pelapor", MyColumns.Pelapor);
//        contentValues.put("lokasi_kejadian", MyColumns.Lokasi);
//        contentValues.put("tanggal", MyColumns.Tanggal);
//        contentValues.put("tindak_lanjut", MyColumns.Tindak);
//        contentValues.put("gambar", MyColumns.Foto);
//        contentValues.put(COLUMN_STATUS, status);
//
//
//        db.insert(MyColumns.NamaTabel, null, contentValues);
//        db.close();
//        return true;
//    }

//    //Get 1 Data By ID
//    public static Cursor oneData(Long id) {
//        Cursor cur = db.rawQuery("SELECT * FROM " + MyColumns.NamaTabel + " WHERE " + MyColumns.NoTiket + "=" + id, null);
//        return cur;
//    }


}

