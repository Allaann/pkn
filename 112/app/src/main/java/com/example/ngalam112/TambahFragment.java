package com.example.ngalam112;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;


public class TambahFragment extends Fragment {

    private EditText NoTiket, Kejadian, Petugas, Pelapor, Lokasi, Tindak, Tanggal;
    private String setNoTiket, setKejadian, setPetugas, setPelapor, setLokasi, setTindak, setTanggal;
    private DbReport dbreport;
    private Button Open;
    CircularImageView imageView;
    Uri resultUri;

    public TambahFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah, container, false);
        Locale localeID = new Locale("in", "ID");
        Button simpan = view.findViewById(R.id.btnKirim);
        Open = view.findViewById(R.id.btnOpen);
        imageView = (CircularImageView) view.findViewById(R.id.image_profile);
        NoTiket = view.findViewById(R.id.formTiket);
//        String level ="Super Admin";
//        if(level.equals("Admin")){
//            NoTiket.setFocusableInTouchMode(false);
//        }
        Kejadian = view.findViewById(R.id.formKejadian);
        Petugas = view.findViewById(R.id.formPetugas);
        Pelapor = view.findViewById(R.id.formPelapor);
        Tanggal = view.findViewById(R.id.formTanggal);
        Tindak = view.findViewById(R.id.formTindak);
        Lokasi = view.findViewById(R.id.formLokasi);

        //Inisialisasi dan Mendapatkan Konteks dari DbBook
        dbreport = new DbReport(getActivity().getBaseContext());
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                if (setNoTiket.equals("") || setKejadian.equals("") || setPetugas.equals("") || setPelapor.equals("") || setLokasi.equals("") || setTindak.equals("") || setTanggal.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Laporan Belum Lengkap atau Belum diisi, Lengkapi Dahulu!", Toast.LENGTH_SHORT).show();
                } else {
                    addReport();
                    setData();
                    saveData();
                    Toast.makeText(getActivity().getApplicationContext(), "Laporan Tersimpan", Toast.LENGTH_SHORT).show();
                    clearData();
                }
            }
        });
        //intent eksternal untuk masuk kedalam folder atau galeri
        Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CropImage.activity().setAspectRatio(3, 4).getIntent(getContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        return view;
    }

    private void addReport(){

        final String setNoTiket = NoTiket.getText().toString().trim();
        final String setKejadian = Kejadian.getText().toString().trim();
        final String setPetugas = Petugas.getText().toString().trim();
        final String setPelapor = Pelapor.getText().toString().trim();
        final String setLokasi = Lokasi.getText().toString().trim();
        final String setTindak = Tindak.getText().toString().trim();
        final String setTanggal = Tanggal.getText().toString().trim();

        class AddReport extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                Log.d("tambah fragment", "masuk do in backgroudnd");
                HashMap<String,String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_NO_TIKET,setNoTiket);
                params.put(Konfigurasi.KEY_PELAPOR,setPelapor);
                params.put(Konfigurasi.KEY_PETUGAS,setPetugas);
                params.put(Konfigurasi.KEY_KEJADIAN,setKejadian);
                params.put(Konfigurasi.KEY_LOKASI_KEJADIAN,setLokasi);
                params.put(Konfigurasi.KEY_TANGGAL,setTanggal);
                params.put(Konfigurasi.KEY_TINDAK_LANJUT,setTindak);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Konfigurasi.URL_ADD, params);
                return res;
            }
        }

        AddReport ae = new AddReport();
        ae.execute();
    }

    //Berisi Statement-Statement Untuk Mendapatkan Input Dari User
    private void setData() {
        setNoTiket = NoTiket.getText().toString();
        setKejadian = Kejadian.getText().toString();
        setPetugas = Petugas.getText().toString();
        setPelapor = Pelapor.getText().toString();
        setLokasi = Lokasi.getText().toString();
        setTindak = Tindak.getText().toString();
        setTanggal = Tanggal.getText().toString();
    }

    //Berisi Statement-Statement Untuk Menyimpan Data Pada Database
    private void saveData() {
        //Mendapatkan Repository dengan Mode Menulis
        SQLiteDatabase create = dbreport.getWritableDatabase();

        //Membuat Map Baru, Yang Berisi Kejadian Kolom dan Data Yang Ingin Dimasukan
        ContentValues values = new ContentValues();
        values.put(DbReport.MyColumns.NoTiket, setNoTiket);
        values.put(DbReport.MyColumns.Kejadian, setKejadian);
        values.put(DbReport.MyColumns.Petugas, setPetugas);
        values.put(DbReport.MyColumns.Pelapor, setPelapor);
        values.put(DbReport.MyColumns.Lokasi, setLokasi);
        values.put(DbReport.MyColumns.Tindak, setTindak);
        values.put(DbReport.MyColumns.Tanggal, setTanggal);
        values.put(DbReport.MyColumns.Foto, String.valueOf(resultUri));

        create.insert(DbReport.MyColumns.NamaTabel, null, values);
    }

    private void clearData() {
        NoTiket.setText("");
        Kejadian.setText("");
        Petugas.setText("");
        Pelapor.setText("");
        Lokasi.setText("");
        Tindak.setText("");
        Tanggal.setText("");
        imageView.setImageResource(R.drawable.ic_picimg);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                resultUri = result.getUri();
                Log.e("resultUri ->", String.valueOf(resultUri));
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("error ->", String.valueOf(error));
            }
        }
    }


}
