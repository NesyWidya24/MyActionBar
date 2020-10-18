package com.kagu.myactionbar;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//  Baris dibawah menunjukan bahwa untuk menampilkan custom item pada action bar, hanya menggunakan xml, dan 1 baris code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

//        Cara memasang listener pada SearchView
//        Untuk mengambil komponen SearchView yg sebelumnya sudah di inflate, menggunakan fungsi menu.findItem().getActionView()
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null){
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
//        setQueryHint() berguna untuk memberikan hint pada penggina tentang query search apa yg
//          telah dimasukkan, yg memudahkan user untuk memasukkan kata
//        Pada setOnQueryTextListener() ada 2 metode yg diterapkan yaitu onQueryTextSubmit() dan onQueryTextChange()
//        Metode onQueryTextSubmit() akan dipanggil saat Submit di tekan
//        Sementara itu, onQueryTextChange() akan dipanggil setiap kali user memasukkan or mengubah query yg ada inputan searchView


        return true;
    }

//    Setelah menampilkan item menggunkan onCreateOptionMenu(), lalu memasang event listener untuk
//      dijalankan ketika item tersebut dipilih. Listener click pada menu Action Bar dapat memanfatkan onOptionItemSelected()

//    untuk menangani kejadian user menyentuh salah satu item, dapat menggunkan code dibawah
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//    dengan melakukan percabangan menggunakan switch dapat memberikan listener pada setiap item

        switch (item.getItemId()) {
            //        Seperti ketika id-nya adalah R.id.menu1 maka yg terjadi adalah fragment pada
            //          R.id.fragment_container diganti dengan fragment baru yaitu menuFragment
            case R.id.menu1:
                MenuFragment menuFragment = new MenuFragment();
                FragmentManager mFragmentManager = getSupportFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container, menuFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();

                return true;

            //         ketika R.id.menu2 di klikmaka aplikasi akan menjalankan activity baru yaitu MenuActivity
            case R.id.menu2:
                Intent i = new Intent(this, MenuActivity.class);
                startActivity(i);
                return true;
            default:
                return true;
        }
    }
}
