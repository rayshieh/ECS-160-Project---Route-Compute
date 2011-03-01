package edu.ucdavis.ecs160;

/*
 * Using the tutorial from:
 * "Creating and Using Databases in Android" by Wei-Meng Lee
 * http://www.devx.com/wireless/Article/40842
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class TrusteeOpenHelper extends SQLiteOpenHelper {

	public static final String NUMBER = Parameters.KEY_NUMBER;
	public static final String NAME = Parameters.KEY_NAME;

	private static final String TAG = "TrusteeOpenHelper";
    private static final String DATABASE_TBLNAME= Parameters.TBLNAME;
    
    //Create database query
    private static final String DATABASE_CREATE =
        "Create table " + DATABASE_TBLNAME + " (" +  
        NAME + " text not null, " +
    	NUMBER + " integer primary key);";
        
    //Auto generated constructor
	public TrusteeOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		Log.v(TAG,"Database Created!");
	}//onCreate

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.v(TAG, "Upgrading database from version " + oldVersion 
                + " to "
                + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + Parameters.TBLNAME);
		onCreate(db);
	}//onUpgrade

}//class TrusteeOpenHelper
