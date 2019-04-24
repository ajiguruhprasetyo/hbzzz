package com.deteksidetakjantung;

import com.deteksidetakjantung.AktifitasJantung;
import com.deteksidetakjantung.DetakJantung;
import com.deteksidetakjantung.LogDeteksi;
import com.deteksidetakjantung.R;

import android.app.AlertDialog;
import android.app. TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class MenuUtama extends  TabActivity{
	// atribut tabhost
	TabHost tabHost;
	//membuat method tabhost
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tabHost= getTabHost();
		
		TabSpec detSpec= tabHost.newTabSpec("Deteksi Detak Jantung");
		detSpec.setIndicator("Deteksi Detak Jantung");
		Intent detIntents = new Intent(this, DetakJantung.class);
		detSpec.setContent(detIntents);
		
		TabSpec logSpec= tabHost.newTabSpec("Log Deteksi");
		logSpec.setIndicator("Log Deteksi");
		Intent logIntents = new Intent(this, LogDeteksi.class);
		logSpec.setContent(logIntents);
		
		TabSpec aktifSpec= tabHost.newTabSpec("Aktifitas Detak Jantung");
		aktifSpec.setIndicator("Aktifitas Detak Jantung");
		Intent aktifIntents = new Intent(this, AktifitasJantung.class);
		aktifSpec.setContent(aktifIntents);
		
		TabSpec helpSpec= tabHost.newTabSpec("Help");
		helpSpec.setIndicator("Help");
		Intent helpIntents = new Intent(this, Help.class);
		helpSpec.setContent(helpIntents);
		
		TabSpec aboutSpec= tabHost.newTabSpec("About");
		aboutSpec.setIndicator("About");
		Intent aboutIntents = new Intent(this, About.class);
		aboutSpec.setContent(aboutIntents);
		
		//menambahkan menu tab host di menu utama
		
		tabHost.addTab(aktifSpec);
		tabHost.addTab(detSpec);
		tabHost.addTab(logSpec);
		tabHost.addTab(helpSpec);
		tabHost.addTab(aboutSpec);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	// alert dialog notifikasi option menu keluar  
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_exit:
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuUtama.this);
	    	// Setting Pesan Dialog
	    	alertDialog.setMessage("Apakah anda ingin keluar dari aplikasi?");
	    	// Setting tombol Positif
	    	alertDialog.setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Pengguna menekan tombol Keluar. Tulis Kode yang akan di eksekusi disini
					Toast.makeText(getApplicationContext(), "Anda telah keluar dari " +
							"Aplikasi Deteksi Detak Jantung", Toast.LENGTH_SHORT).show();
					finish();
					}
			});
	    	// Setting tombol Negatif
	    	alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Pengguna menekan tombol Cancel. Tulis Kode yang akan di eksekusi disini
					Toast.makeText(getApplicationContext(), "Anda tidak jadi keluar dari aplikasi", 
							Toast.LENGTH_SHORT).show();
					dialog.cancel();
				}
			});
	    	alertDialog.show();
		return super.onOptionsItemSelected(item);
		}
		return false;
	}
}