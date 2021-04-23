package com.example.ngalam112;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import java.text.NumberFormat;
import java.util.Locale;


public class DetailActivity extends AppCompatActivity {

    private DbReport MyDatabase;
    private TextView ShowNoTiket, ShowKejadian, ShowPetugas, ShowPelapor, ShowLokasi, ShowTindak, ShowTanggal;
    private ImageView ShowImage, Back;
    private String sendVal = "id";
    Locale localeID = new Locale("in", "ID");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MyDatabase = new DbReport(getBaseContext());

        Back = findViewById(R.id.back);
        ShowNoTiket = findViewById(R.id.detailTiket);
        ShowImage = findViewById(R.id.imageDetail);
        ShowKejadian = findViewById(R.id.detailKejadian);
        ShowPetugas = findViewById(R.id.detailPetugas);
        ShowPelapor = findViewById(R.id.detailPelapor);
        ShowLokasi = findViewById(R.id.detailLokasi);
        ShowTindak = findViewById(R.id.detailTindak);
        ShowTanggal = findViewById(R.id.detailTanggal);


        getData();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NoTiket = ShowNoTiket.getText().toString();
                String Tanggal = ShowTanggal.getText().toString();
                Intent intent = new Intent(DetailActivity.this,MainActivity.class);
                intent.putExtra(sendVal,NoTiket);
                startActivity(intent);
            }
        });


    }
    private void getData(){
        Intent intent = getIntent();
        String id = intent.getStringExtra("SendTiket");

        SQLiteDatabase ReadData = MyDatabase.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT * FROM " + DbReport.MyColumns.NamaTabel + " WHERE " + DbReport.MyColumns.NoTiket + "=" + id, null);

        cursor.moveToFirst();//Memulai Cursor pada Posisi Awal
        if(cursor.moveToFirst()){
            String NoTiket = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.NoTiket));
            String Kejadian = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Kejadian));
            String Petugas = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Petugas));
            String Pelapor = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Pelapor));
            String Lokasi = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Lokasi));
            String Tindak = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Tindak));
            String Tanggal = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Tanggal));
            String Foto = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Foto));

            ShowNoTiket.setText(NoTiket);
            ShowKejadian.setText(Kejadian);
            ShowPetugas.setText(Petugas);
            ShowPelapor.setText(Pelapor);
            ShowLokasi.setText(Lokasi);
            ShowTindak.setText(Tindak);
            ShowTanggal.setText(Tanggal);
            ShowImage.setImageURI(Uri.parse(Foto));

        }
    }
}