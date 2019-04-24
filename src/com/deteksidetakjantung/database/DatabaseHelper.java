package com.deteksidetakjantung.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "log_deteksi";
	public static final String COLUMN_ID = "id_detakjantung";
	public static final String COLUMN_NAME = "hitung_jantung";
	public static final String COLUMN_DATE = "tanggal_hitung";
	private static final String db_name = "Log_deteksi.db";
	private static final int db_version = 1;
	
	private static final String db_create = "create_table"
			+ TABLE_NAME + "log_deteksi"
			+ COLUMN_ID + "integer primary key autoincrement, "
			+ COLUMN_NAME + "integer not null, "
			+ COLUMN_DATE + " date not null; ";
	
	
			
	
	public DatabaseHelper(Context context) {
		super(context, db_name, null, db_version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(db_create);
	}

	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE log_deteksi");
	}
}
