package edu.ucdavis.ecs160;

/*
 * Using the tutorial from:
 * "Creating and Using Databases in Android" by Wei-Meng Lee
 * http://www.devx.com/wireless/Article/40842
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TrusteeOpenHelper extends SQLiteOpenHelper {

	public static final String KEY_NUMBER = "Number";
	public static final String KEY_NAME = "Name";

	private static final String TAG = "TrusteeOpenHelper";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = Parameters.DBNAME;
    private static final String DATABASE_TABLE = Parameters.TBLNAME;
    private final Context context;
    
    //Create database query
    private static final String DATABASE_CREATE =
        "Create table " + DATABASE_NAME + " (" +  
        	KEY_NUMBER + " integer primary key, " +
        	KEY_NAME + " text not null);";
        
    //Auto generated constructor
	public TrusteeOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		//TODO some of the private final parameters may need to these arguments
		//Need to check later on how did the constructor called
		this.context = context;
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
