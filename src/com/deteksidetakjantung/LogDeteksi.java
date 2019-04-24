package com.deteksidetakjantung;

import com.deteksidetakjantung.R;
import com.deteksidetakjantung.database.DatabaseHelper;
import android.app.Activity;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;


public class LogDeteksi extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logdeteksi);
		
		DatabaseHelper databaseHelper = new DatabaseHelper(null);
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}
}

