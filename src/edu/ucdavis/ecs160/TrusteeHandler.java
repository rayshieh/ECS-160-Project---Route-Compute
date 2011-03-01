package edu.ucdavis.ecs160;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import android.content.Context;

public class TrusteeHandler {
	/*
	 * A trustee record is stored in the format of:
	 * 	  40 bytes for the number (10 characters using UTF-16), 
	 *		this is the key of the structure 
	 * 	  4 bytes for delimiter ","
	 * 	  80 bytes for the name (20 digits using UTF-16)
	 *    4 bytes for the new line "\n" 
		 
	 */
	
	/*
	 * Add the name associate with the number into the storage file
	 * The function returns NULL if the number is in correct format
	 * and is unique in the system
	 * Otherwise, the function will return the appropriate message 
	 */
	public static String addTrustee(Context context, String name, String number) {		
		//Check the format first
		if (number.length() != 10 || Long.valueOf(number) == null)
			return "Not a valid phone number.";
		else if (name.length() == 0)
			return "The name is too short.";
		else if (name.length() > 20)
			return "The name is too long.";
		else {//Format is correct
			try {				
				if ( findTrustee(context, number) )
					return "Information already existed";
				else {//Not exist
					//Get the encoder for the writing
					Charset charset = Charset.forName(Parameters.CHARSET);
					CharsetEncoder encoder = charset.newEncoder();
					
					//Encode and write to the file
					FileOutputStream fos = context.openFileOutput(Parameters.FILENAME, Context.MODE_APPEND);
					
					int remain = 128;
					//number + ','
					ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(number+','));
					remain = remain - bbuf.array().length;
					fos.write(bbuf.array(), 0, bbuf.array().length);
					
					//name + '\n'
					bbuf = encoder.encode(CharBuffer.wrap(name+'\n'));
					remain = remain - bbuf.array().length;
					fos.write(bbuf.array(), 0, bbuf.array().length);
					
					//Fill in the space
					if(remain > 76) {
						/*
						 * There is something wrong
						 * since there should be at least 13 characters stored, 
						 * which used at least 52 bytes, remain at most 76 bytes to write
						 */
						return "Unexpected error in record size";
					}
					
					fos.write(new byte[remain]);
					
					fos.close();
				}//else
			}catch(Exception e) {
				//Show me what is wrong!
				return e.toString();
			}//catch
		}//else
		return null;
	}//addTrustee
	
	/*
	 * Search and see does the number exist in the file or not 
	 */
	public static boolean findTrustee(Context context, String number) {
		try {
			//Open the file for reading
			CharsetDecoder decoder = Charset.forName(Parameters.CHARSET).newDecoder();
			byte[] buf = new byte[128];
			ByteBuffer bbuf = ByteBuffer.allocate(128);
			FileInputStream fis = context.openFileInput(Parameters.FILENAME);
			
			//Search
			String result;
			while( fis.read(buf) > 0 ){				
				result = decoder.decode(bbuf.wrap(buf)).toString();
				if (result.contains(number) ) {
					fis.close();
					return true;
				}
			}
			
			//Reach here means didn't find the number
			fis.close();
			return false;
		}catch(Exception e) {
			return false;			
		}
	}
}
