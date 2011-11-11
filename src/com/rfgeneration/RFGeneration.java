package com.rfgeneration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;

public class RFGeneration extends Activity implements OnClickListener {
	
	public static final String PREFS_NAME = "RFGenerationPrefsFile";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ((Button)findViewById(R.id.collectionButton)).setOnClickListener(this);
        ((Button)findViewById(R.id.searchButton)).setOnClickListener(this);
        findViewById(R.id.aboutrfg).setOnClickListener(this);
        
        final EditText edittext = (EditText) findViewById(R.id.collectionText);
        edittext.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
        			// Hide the keyboard
        			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                  // Perform action on key press
                  onClick(findViewById(R.id.collectionButton));
                  return true;
                }
                return false;
            }
        });
        
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String userName = settings.getString("collectionUsername", "");
        edittext.setText(userName);
        
        final EditText searchText = (EditText) findViewById(R.id.searchText);
        searchText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
        			// Hide the keyboard
        			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                  // Perform action on key press
                  onClick(findViewById(R.id.searchButton));
                  return true;
                }
                return false;
            }
        });
        
        // Restore preferences
        String searchGame = settings.getString("searchGame", "");
        searchText.setText(searchGame);
    }

	public void onClick(View arg0) {
		if(arg0.getId() == R.id.collectionButton)
		{
			String userName = ((EditText)findViewById(R.id.collectionText)).getText().toString();
			// Save the entered name
			// We need an Editor object to make preference changes.
		      // All objects are from android.context.Context
		      SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		      SharedPreferences.Editor editor = settings.edit();
		      editor.putString("collectionUsername", userName);

		      // Commit the edits!
		      editor.commit();

			
			// Try and check if this collection is valid
			
			Intent myIntent = new Intent(arg0.getContext(), CollectionList.class);
			myIntent.putExtra("COLLECTION_USERNAME", userName);
			startActivityForResult(myIntent, 0);
		}
		else if(arg0.getId() == R.id.searchButton)
		{
			String searchGame = ((EditText)findViewById(R.id.searchText)).getText().toString();
			// Save the entered name
			// We need an Editor object to make preference changes.
		      // All objects are from android.context.Context
		      SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		      SharedPreferences.Editor editor = settings.edit();
		      editor.putString("searchGame", searchGame);

		      // Commit the edits!
		      editor.commit();
			
		      Intent myIntent = new Intent(arg0.getContext(), SearchList.class);
				myIntent.putExtra("SEARCH_GAME", searchGame);
				startActivityForResult(myIntent, 0);
		}
		else if(arg0.getId() == R.id.aboutrfg)
		{
			AlertDialog ad = new AlertDialog.Builder(this).create();
			  ad.setTitle("What is RF Generation?");
			  ad.setView(LayoutInflater.from(this).inflate(R.layout.about,null));
			  ad.setButton("OK", new DialogInterface.OnClickListener() {  
			      public void onClick(DialogInterface dialog, int which) {  
			        return;  
			    } });   
			  ad.show();

		}
		
	}
}