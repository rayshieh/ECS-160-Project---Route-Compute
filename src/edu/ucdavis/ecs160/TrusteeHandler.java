package edu.ucdavis.ecs160;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class TrusteeHandler {

    private static final int DATABASE_VERSION = 2;
    private TrusteeOpenHelper toh;
	private SQLiteDatabase db;
	private Context context;
	private static final String DATABASE_TBLNAME = Parameters.TBLNAME;
	private static final String DATABASE_KEY_NUMBER = Parameters.KEY_NUMBER;
	private static final String DATABASE_KEY_NAME = Parameters.KEY_NAME;
	
	/*
	 * Constructor
	 */
	public TrusteeHandler (Context context, String name, CursorFactory factory, int version) throws SQLException{
		this.context = context;
		toh = new TrusteeOpenHelper(context, name, factory, version);
	}
	
	/*
	 * Add the name associate with the number into the storage file
	 * The function returns NULL if the number is in correct format
	 * and is unique in the system
	 * Otherwise, the function will return the appropriate message 
	 */
	public String addTrustee(String name, String number) {		
		//Check the format first
		if (number.length() != 10 || Long.valueOf(number) == null)
			return "Not a valid phone number.";
		else if (name.length() == 0)
			return "The name is too short.";
		else if (name.length() > 20)
			return "The name is too long.";
		else {//Format is correct
			try {//Find the number
				openDB();
				if (findTrustee(context,number) != null) {
					closeDB();
					return "Information already existed";
				}
				else {//Not exist
					//Add the data into the db and close it 
					long result = insertTrustee(name,number);
					closeDB();
					if (result == -1)
						return "Unexpected database error";
					else
						return null;
				}//else
			}catch(Exception e) {
				//Show me what is wrong!
				closeDB();
				return e.toString();
			}//catch
		}//else
	}//addTrustee
	
	/*
	 * Search and see does the number exist in the file or not 
	 * with the format "Name,Number"
	 */
	public Cursor findTrustee(Context context, String number) {
		//select KEY_NAME, KEY_NUMBER from TBLNAME where KEY_NUMBER = number
    	Cursor mCursor = db.query(true, DATABASE_TBLNAME, 
                		new String[] {DATABASE_KEY_NAME,DATABASE_KEY_NUMBER}, 
                		DATABASE_KEY_NUMBER + "=" + number, 
                		null, null, null, null, null);
    	
        if (mCursor != null)
            mCursor.moveToFirst();
        
        return mCursor;
    
	}
	
	/*
	 * Open the database
	 */
	public void openDB() throws SQLException{
		db = toh.getWritableDatabase();
	}

	/*
	 * Close the database
	 */
	public void closeDB() throws SQLException{
		//Only close the db if it is opened
		if (db.isOpen())
			toh.close();
	}
	
	/*
	 * Insert a trustee into the table
	 */
	public long insertTrustee(String name, String number) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DATABASE_KEY_NAME, name);
        initialValues.put(DATABASE_KEY_NUMBER, number);
        return db.insert(DATABASE_TBLNAME, null, initialValues);
    }
	/*
	 * The global variable
	 */
	public static TrusteeHandler instance = null;
	//	How to use it
	//	TrusteeHandler.getInstance( getApplicationContext(), Parameters.DBNAME, null, 2);

	/*
	 * Static Singleton getter
	 */
	public static TrusteeHandler getInstance( Context context, String name, 
											  CursorFactory factory, int version) {
      if(instance == null) {
         instance = new TrusteeHandler(context, name, factory, version);
      }
      return instance;
	}
	
}


//public static String addTrustee(Context context, String name, String number) {		
//	//Check the format first
//	if (number.length() != 10 || Long.valueOf(number) == null)
//		return "Not a valid phone number.";
//	else if (name.length() == 0)
//		return "The name is too short.";
//	else if (name.length() > 20)
//		return "The name is too long.";
//	else {//Format is correct
//		try {				
//			if ( findTrustee(context, number) )
//				return "Information already existed";
//			else {//Not exist
//				//Get the encoder for the writing
//				Charset charset = Charset.forName(Parameters.CHARSET);
//				CharsetEncoder encoder = charset.newEncoder();
//				
//				//Encode and write to the file
//				FileOutputStream fos = context.openFileOutput(Parameters.FILENAME, Context.MODE_APPEND);
//				
//				int remain = 128;
//				//number + ','
//				ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(number+','));
//				remain = remain - bbuf.array().length;
//				fos.write(bbuf.array(), 0, bbuf.array().length);
//				
//				//name + '\n'
//				bbuf = encoder.encode(CharBuffer.wrap(name+'\n'));
//				remain = remain - bbuf.array().length;
//				fos.write(bbuf.array(), 0, bbuf.array().length);
//				
//				//Fill in the space
//				if(remain > 76) {
//					/*
//					 * There is something wrong
//					 * since there should be at least 13 characters stored, 
//					 * which used at least 52 bytes, remain at most 76 bytes to write
//					 */
//					return "Unexpected error in record size";
//				}
//				
//				fos.write(new byte[remain]);
//				
//				fos.close();
//			}//else
//		}catch(Exception e) {
//			//Show me what is wrong!
//			return e.toString();
//		}//catch
//	}//else
//	return null;
//}//addTrustee