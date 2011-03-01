package edu.ucdavis.ecs160;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTrustee extends Activity {
    private static final String TAG = "Add_Trustee"; 

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_menu);
    	final Button addF = (Button) findViewById(R.id.addF);
        
    	//When the botton is pressed
		addF.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	try {
	            	//Process the inputs
	            	EditText nameEntry = (EditText)findViewById(R.id.name_entry);
	            	EditText numberEntry = (EditText)findViewById(R.id.number_entry);
	            	final String name = nameEntry.getText().toString();
	            	final String number = numberEntry.getText().toString();
	            	//Validation
	            	String result = new TrusteeHandler().addTrustee(
	            			getApplicationContext(),name, number);
	            	if (result != null)
	            		//Failed
	            		Toast.makeText(getApplicationContext(), 
	            			result, Toast.LENGTH_SHORT).show();
	            	else {
	            		//Added into the list
	            		Toast.makeText(getApplicationContext(), 
    						"Trustee Added!", Toast.LENGTH_SHORT).show();
	            		Toast.makeText(getApplicationContext(), 
	    					"You can go back to the last page or stay and add more users", 
	    					Toast.LENGTH_LONG).show();
	            	}//else
	            }catch (Exception e) {
	            	Log.v(TAG, e.toString());
	            }//catch
            }//onClick            
        });//addF.setOnClickListener    		
    }//onCreate
}