package com.example.ngalam112;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class UpdateActivity extends AppCompatActivity {
    private DbReport MyDatabase;
    private EditText NewTiket, NewKejadian, NewPetugas, NewPelapor, NewLokasi, NewTindak, NewTanggal;
    private CircularImageView NewImage;
    private String getNewTiket;
    private Button Update, Open;
    private String KodeSend = "KODE";
    private ImageView Back;
    Uri resultUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        MyDatabase = new DbReport(getBaseContext());

        NewTiket = findViewById(R.id.newformTiket);
        NewKejadian = findViewById(R.id.newformKejadian);
        NewPetugas = findViewById(R.id.newformPetugas);
        NewPelapor = findViewById(R.id.newformPelapor);
        NewLokasi = findViewById(R.id.newformLokasi);
        NewTanggal = findViewById(R.id.newformTanggal);
        NewTindak = findViewById(R.id.newformTindak);
        NewImage = findViewById(R.id.Newimage_profile);

        Back = findViewById(R.id.back);

        Bundle extras = getIntent().getExtras();
        getNewTiket = extras.getString(KodeSend);
        Update = findViewById(R.id.btnUpdate);
        Open = findViewById(R.id.newbtnOpen);

        SQLiteDatabase ReadDb = MyDatabase.getReadableDatabase();
        Cursor cursor = ReadDb.rawQuery("SELECT * FROM " + DbReport.MyColumns.NamaTabel + " WHERE " + DbReport.MyColumns.NoTiket + "=" + getNewTiket, null);

        cursor.moveToFirst();//Memulai Cursor pada Posisi Awal
        if (cursor.moveToFirst()) {
            String Kode = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.NoTiket));
            String Kejadian = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Kejadian));
            String Petugas = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Petugas));
            String Pelapor = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Pelapor));
            String Lokasi = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Lokasi));
            String Tindak = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Tindak));
            String Tanggal = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Tanggal));
            String Foto = cursor.getString(cursor.getColumnIndex(DbReport.MyColumns.Foto));


            NewTiket.setText(Kode);
            NewKejadian.setText(Kejadian);
            NewPetugas.setText(Petugas);
            NewPelapor.setText(Pelapor);
            NewLokasi.setText(Lokasi);
            NewTindak.setText(Tindak);
            NewTanggal.setText(Tanggal);
            NewImage.setImageURI(Uri.parse(Foto));

            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setUpdateData();
                    startActivity(new Intent(UpdateActivity.this, MainActivity.class));

                }
            });
            Back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                finish();
                }
            });
            Open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CropImage.startPickImageActivity(UpdateActivity.this);
                }
            });
        }
    }

    private void setUpdateData() {
        SQLiteDatabase ReadData = MyDatabase.getReadableDatabase();

            String getKode = NewTiket.getText().toString().trim();
            String getKejadian = NewKejadian.getText().toString().trim();
            String getPetugas = NewPetugas.getText().toString().trim();
            String getPelapor = NewPelapor.getText().toString().trim();
            String getLokasi = NewLokasi.getText().toString().trim();
            String getTindak = NewTindak.getText().toString().trim();
            String getTanggal = NewTanggal.getText().toString().trim();


            //Memasukan Data baru pada
            ContentValues values = new ContentValues();
            values.put(DbReport.MyColumns.NoTiket, getKode);
            values.put(DbReport.MyColumns.Kejadian, getKejadian);
            values.put(DbReport.MyColumns.Petugas, getPetugas);
            values.put(DbReport.MyColumns.Pelapor, getPelapor);
            values.put(DbReport.MyColumns.Lokasi, getLokasi);
            values.put(DbReport.MyColumns.Tindak, getTindak);
            values.put(DbReport.MyColumns.Tanggal, getTanggal);
            values.put(DbReport.MyColumns.Foto, String.valueOf(resultUri));

            //Untuk Menentukan Data/Item yang ingin diubah, berdasarkan NIM
            String selection = DbReport.MyColumns.NoTiket + " LIKE ?";
            String[] selectionArgs = {getNewTiket};
            ReadData.update(DbReport.MyColumns.NamaTabel, values, selection, selectionArgs);
            Toast.makeText(getApplicationContext(), "Berhasil Diubah", Toast.LENGTH_SHORT).show();
        }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {
            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                resultUri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                        , 0);
            } else {
                startCrop(imageuri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                NewImage.setImageURI(result.getUri());
                resultUri = result.getUri();
            }
        }
    }

    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(3, 4)
                .start(this);
        resultUri = imageuri;
    }
}