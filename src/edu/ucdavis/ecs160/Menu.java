package edu.ucdavis.ecs160;

import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Menu extends Activity {
    /** Called when the activity is first created. */
    private static final int DATABASE_VERSION = 2;
    private static final String TAG = "Menu";
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
		//Menu buttons        
        final Button currentB = (Button) findViewById(R.id.current_job);
        final Button sendB = (Button) findViewById(R.id.send_package);
        final Button cancelB = (Button) findViewById(R.id.cancel_package);
        final Button addB = (Button) findViewById(R.id.add_trustee);
        final Button removeB = (Button) findViewById(R.id.remove_trustee);
    	try {
            //Create a file to store the data
            TrusteeHandler th = TrusteeHandler.getInstance(getApplicationContext(), Parameters.DBNAME, null, 2);
            th.openDB();//this line should've create the database and create a table
            th.closeDB();
            
            FileOutputStream fos = openFileOutput(Parameters.FILENAME, Context.MODE_APPEND);
            fos.close();
            
	        currentB.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
		        	Intent myIntent = new Intent(Menu.this, CurrentJob.class);//menuUIwhere you would put link to map utility
		        	startActivity(myIntent);
//		        	Toast.makeText(	getApplicationContext(), 
//		        					"Communicate with the server...", 
//		        					Toast.LENGTH_LONG).show();
		        	
	            }//onClick            
	        });//currentB.setOnClickListener
	        
	        sendB.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
		        	Intent myIntent = new Intent(Menu.this, SendPackage.class);//menuUIwhere you would put link to map utility
		        	startActivity(myIntent);
	            }//onClick            
	        });//sendB.setOnClickListener

	        cancelB.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
		        	Intent myIntent = new Intent(Menu.this, CancelPackage.class);//menuUIwhere you would put link to map utility
		        	startActivity(myIntent);
	            }//onClick            
	        });//cancelB.setOnClickListener

	        addB.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
		        	Intent myIntent = new Intent(Menu.this, AddTrustee.class);//menuUIwhere you would put link to map utility
		        	startActivity(myIntent);
	            }//onClick            
	        });//addB.setOnClickListener

	        removeB.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
		        	Intent myIntent = new Intent(Menu.this, RemoveTrustee.class);//menuUIwhere you would put link to map utility
		        	startActivity(myIntent);
	            }//onClick            
	        });//removeB.setOnClickListener
	        
	        
    	}catch(Exception e) {
    		Log.v(TAG,e.toString());
    	}

    }// onCreate
}//class