package com.example.ngalam112;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    private DbReport MyDatabase;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    //Menggunakan Layout Manager, Dan Membuat List Secara Vertical
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    private ArrayList KejadianList;
    private ArrayList LokasiList;
    private ArrayList TiketList;
    private ArrayList TanggalList;
    private ArrayList FotoList;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData(view);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        LoadData(view);

        return view;
    }

    private void LoadData(View view) {
        KejadianList = new ArrayList<>();
        LokasiList = new ArrayList<>();
        TiketList = new ArrayList<>();
        TanggalList = new ArrayList<>();
        FotoList = new ArrayList<>();

        MyDatabase = new DbReport(getActivity().getBaseContext());
        recyclerView = view.findViewById(R.id.recycler);
        getData();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(ListFragment.this, KejadianList, LokasiList, TiketList, TanggalList, FotoList);
        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(adapter);
        //Membuat Underline pada Setiap Item Didalam List
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
    }

    //Berisi Statement-Statement Untuk Mengambi Data dari Database
    @SuppressLint("Recycle")
    protected void getData() {
        //Mengambil Repository dengan Mode Membaca
        SQLiteDatabase ReadData = MyDatabase.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT * FROM " + DbReport.MyColumns.NamaTabel, null);

        cursor.moveToFirst();//Memulai Cursor pada Posisi Awal

        //Melooping Sesuai Dengan Jumlan Data (Count) pada cursor
        for (int count = 0; count < cursor.getCount(); count++) {

            cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir

//            Mengambil data dari sesuai kolom array
            TiketList.add(cursor.getString(0));
            KejadianList.add(cursor.getString(1));
            LokasiList.add(cursor.getString(4));
            TanggalList.add(cursor.getString(6));
            FotoList.add(cursor.getString(7));
        }
    }
}