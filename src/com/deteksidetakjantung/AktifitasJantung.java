package com.deteksidetakjantung;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AktifitasJantung extends ListActivity {
	String aktifitas [] = {"Pemanasan", "Membakar Lemak", 
			"Kardio Jantung", "Extrim", "Maksimal"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<String>(AktifitasJantung.this, 
				android.R.layout.simple_list_item_1, aktifitas));
			}
		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);
			try{
				String x = String.valueOf(position+1);
				Class run = Class.forName("com.deteksidetakjantung.aktifitas.List"+ x +"Activity");
				Intent i = new Intent (AktifitasJantung.this, run);
				startActivity(i);
			} catch (ClassNotFoundException e){
				e.printStackTrace();	
				}
			}
			@Override
			public void finish() {
				// TODO Auto-generated method stub
				super.finish();
			}
		}