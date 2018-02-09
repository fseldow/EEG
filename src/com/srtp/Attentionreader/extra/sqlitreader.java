package com.srtp.Attentionreader.extra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.R.string;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
public class sqlitreader{
	public boolean isDatabase = false;
	private SQLiteDatabase _db = null;
	private String _dbPath;
	private Context _cont;
	private String nl = "\n"; 
	private boolean testDBFile(String dbPath) {
		// File must start with the following 16 bytes
		// 0x53 0x51 0x4c 0x69 0x74 0x65 0x20 0x66 0x6f 0x72 0x6d 0x61 0x74 0x20 0x33 0x00
		// to be a SQLite 3 database
		File backupFile = new File(dbPath);
		FileReader f = null;
		if (backupFile.canRead()) {
			try {
				f = new FileReader(backupFile);
				char buffer[] = new char[16];
				f.read(buffer, 0, 16);
				if (buffer[0] == 0x53 && 
						buffer[1] == 0x51 &&
						buffer[2] == 0x4c &&
						buffer[3] == 0x69 &&
						buffer[4] == 0x74 &&
						buffer[5] == 0x65 &&
						buffer[6] == 0x20 &&
						buffer[7] == 0x66 &&
						buffer[8] == 0x6f &&
						buffer[9] == 0x72 &&
						buffer[10] == 0x6d && 
						buffer[11] == 0x61 &&
						buffer[12] == 0x74 &&
						buffer[13] == 0x20 &&
						buffer[14] == 0x33 &&
						buffer[15] == 0x00) {
					f.close();
					return true; 
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				f.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		return false;
	}
	public sqlitreader(String dbPath, Context cont) {
		_dbPath = dbPath;
		try {
			// Must find a way to check if it is a SQLite file!
			if (testDBFile(dbPath)) {
				// Here we know it is a SQLite 3 file
				//loge("Trying to open (RW): " + dbPath);
				_db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
				_cont = cont;
				isDatabase = true;
			}
		} catch (Exception e) {
			//Utils.logD("Trying to open Exception: " + e.getMessage());
			// It is not a database
			isDatabase = false;
		}
	}
	public void close() {
		_db.close();
	}

	/**
	 * Test the database if not open open it
	 */
	private void testDB() {
		if (_db == null) {
			_db = SQLiteDatabase.openDatabase(_dbPath, null, SQLiteDatabase.OPEN_READWRITE);
		}
		if (!_db.isOpen()) {
			_db = SQLiteDatabase.openDatabase(_dbPath, null, SQLiteDatabase.OPEN_READWRITE);
		}
	}
	
	/**
	 * Retrieve all the table names of the database
	 * @return
	 */
	public String[] getTables() {
		testDB();
		String sql ="select name from sqlite_master where type = 'table' order by name";
		Cursor res = _db.rawQuery(sql, null);
		int recs = res.getCount();
		String[] tables = new String[recs + 1];
		int i = 1;
		tables[0] = "sqlite_master";
		//Utils.logD("Tables: " + recs);
		while(res.moveToNext()) {
			tables[i] = res.getString(0);
			i++;
		}
		res.close();
		return tables;
	}
	public String[][] getTableData(String table, int offset, int limit, boolean view) {
		/*
		 * If not a query or view include rowid in data if no single field
		 * primary key exists
		 */
		//TODO implement sorting on single column asc / des
		// first time a coulms is clicked sort asc if it is clicked again sort des
		testDB();
		String sql = "";
		if (view)
			sql = "select * from " + table + " limit " + limit + " offset " + offset;
		else
			sql = "select rowid as rowid, * from " + table + " limit " + limit + " offset " + offset;
		//Utils.logD("SQL = " + sql);
		Cursor cursor = _db.rawQuery(sql, null);
		int cols = cursor.getColumnCount();
		int rows = cursor.getCount();
		String[][] res = new String[rows][cols];
		int i = 0;
		//int j = 0;
		while(cursor.moveToNext()) {
			for (int k=0; k<cols; k++) {
				res[i][k] = cursor.getString(k);
			}
			i++;
		}
		return res;
	}
}
