package com.deteksidetakjantung.database;

import java.sql.Date;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBLogDeteksi {
	DatabaseHelper dbHelper;
	SQLiteDatabase db;
	private final Context context;
	
	public DBLogDeteksi(Context context){
		this.context = context;
	}
	public DBLogDeteksi open() throws SQLException{
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}
	public void close(){
		dbHelper.close();
	}
	

		
}
