package com.example.ngalam112;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.format.DateFormat;
import android.util.Log;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList KejadianList;
    private ArrayList lokasiList;
    private ArrayList tiketList;
    private ArrayList tanggalList;
    private ArrayList fotoList;
    private Context context;
    RecyclerView mRecyclerView;


    //Membuat Konstruktor pada Class RecyclerViewAdapter
    RecyclerViewAdapter(ListFragment listFragment, ArrayList KejadianList, ArrayList lokasiList, ArrayList tiketList, ArrayList tanggalList, ArrayList fotoList) {
        this.KejadianList = KejadianList;
        this.lokasiList = lokasiList;
        this.tiketList = tiketList;
        this.tanggalList = tanggalList;
        this.fotoList = fotoList;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Detail, Kejadian, Lokasi, Tiket, Tanggal;
        private CircularImageView Foto;
        private ImageButton Delete;


        ViewHolder(View itemView) {
            super(itemView);

            //Mendapatkan Context dari itemView yang terhubung dengan Activity ViewData
            context = itemView.getContext();

            //Menginisialisasi View-View untuk kita gunakan pada RecyclerView
            mRecyclerView = itemView.findViewById(R.id.recycler);
            Kejadian = itemView.findViewById(R.id.Kejadian);
            Lokasi = itemView.findViewById(R.id.listLokasi);
            Tiket = itemView.findViewById(R.id.listTiket);
            Tanggal = itemView.findViewById(R.id.listTanggal);
            Detail = itemView.findViewById(R.id.Detail);
            Foto = itemView.findViewById(R.id.image);
            Delete = itemView.findViewById(R.id.delete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recycler, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        //Memanggil Nilai/Value Pada View-View Yang Telah Dibuat pada Posisi Tertentu
        final String Kejadian = (String) KejadianList.get(position);//Mengambil data (Kejadian) sesuai dengan posisi yang telah ditentukan

        final String Lokasi = (String) lokasiList.get(position);
        final String Tiket = (String) tiketList.get(position);
        final String Tanggal = (String) tanggalList.get(position);
        final String Foto = (String) fotoList.get(position);
        holder.Kejadian.setText(Kejadian);
        holder.Lokasi.setText(Lokasi);
        holder.Tiket.setText(Tiket);
        holder.Tanggal.setText(Tanggal);
        holder.Foto.setImageURI(Uri.parse(Foto));

//      Panggil Onclik Hapus untuk Hapus db dan juga recyclveiw didalam showdialog
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                showDialog();
            }

//          dialog hapus
            private void showDialog(){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title dialog
                alertDialogBuilder.setTitle("Apakah Anda Ingin Menghapus Data ini?");

                // set pesan dari dialog
                alertDialogBuilder
                        .setIcon(R.drawable.ic_delete)
                        .setCancelable(false)
                        .setPositiveButton("Hapus",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // jika tombol diklik, maka akan menutup activity ini

                                //Menghapus Data Dari Database
                                DbReport getDatabase = new DbReport(context);
                                SQLiteDatabase DeleteData = getDatabase.getWritableDatabase();
                                //Menentukan di mana bagian kueri yang akan dipilih
                                String selection = DbReport.MyColumns.NoTiket + " LIKE ?";
                                //Menentukan Kejadian Dari Data Yang Ingin Dihapus
                                String[] selectionArgs = {holder.Tiket.getText().toString()};
                                DeleteData.delete(DbReport.MyColumns.NamaTabel, selection, selectionArgs);

                                //Menghapus Data pada List dari Posisi Tertentu
                                int position = tiketList.indexOf(Tiket);
                                tiketList.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(context,"Data Dihapus",Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // jika tombol ini diklik, akan menutup dialog
                                // dan tidak terjadi apa2
                                dialog.cancel();
                            }
                        });

                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();

                // menampilkan alert dialog
                alertDialog.show();
            }


        });

//        onklik see detail
        holder.Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent dataForm = new Intent(view.getContext(), DetailActivity.class);
                dataForm.putExtra("SendTiket", holder.Tiket.getText().toString());
                context.startActivity(dataForm);
                ((Activity)context).finish();
                }

            });
    }
    @Override
    public int getItemCount() {
        return tiketList.size();
    }
}
