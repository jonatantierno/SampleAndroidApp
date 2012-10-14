/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.finapps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * This class provides a basic demonstration of how to write an Android
 * activity. Inside of its window, it places a single view: an EditText that
 * displays and edits some internal text.
 */
public class MainActivity extends RoboActivity {
    
    static final private int BACK_ID = Menu.FIRST;
    static final private int CLEAR_ID = Menu.FIRST + 1;

    @InjectView(R.id.editor)
    private EditText mEditor;
    @InjectView(R.id.test_back)
    private Button backButton;
    @InjectView(R.id.test_clear)
    private Button clearButton;
    @InjectView(R.id.download_button)
    private Button mDownloadButton;
    
    
    public MainActivity() {
    }

    /** Called with the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.main_activity);

        // Hook up button presses to the appropriate event handler.
        backButton.setOnClickListener(mBackListener);
        clearButton.setOnClickListener(mClearListener);
        mDownloadButton.setOnClickListener(mDownloadListener);
        
        mEditor.setText(getText(R.string.main_label));
    }

    /**
     * Called when the activity is about to start interacting with the user.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Called when your activity's options menu needs to be created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // We are going to create two menus. Note that we assign them
        // unique integer IDs, labels from our string resources, and
        // given them shortcuts.
        menu.add(0, BACK_ID, 0, R.string.back).setShortcut('0', 'b');
        menu.add(0, CLEAR_ID, 0, R.string.clear).setShortcut('1', 'c');

        return true;
    }

    /**
     * Called right before your activity's option menu is displayed.
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // Before showing the menu, we need to decide whether the clear
        // item is enabled depending on whether there is text to clear.
        menu.findItem(CLEAR_ID).setVisible(mEditor.getText().length() > 0);

        return true;
    }

    /**
     * Called when a menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case BACK_ID:
            finish();
            return true;
        case CLEAR_ID:
            mEditor.setText("");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A call-back for when the user presses the back button.
     */
    OnClickListener mBackListener = new OnClickListener() {
        public void onClick(View v) {
            finish();
        }

    };
    /** * A call-back for when the user presses the download button.
     */
    OnClickListener mDownloadListener= new OnClickListener() {
        public void onClick(View v) {
        	try {
				String s = download();
				mEditor.setText(s);
				Transaction t = parseTransactions(s);
			
				
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    };

    /** * A call-back for when the user presses the clear button.
     */
    OnClickListener mClearListener = new OnClickListener() {
        public void onClick(View v) {
            mEditor.setText("");
        }
    };
    
    String download() throws IOException
    {
    	 URL u = new URL("http://echo.jsontest.com/amount/10/date/24234234534534/otherParty/pepe"); 
    	 HttpURLConnection conn = (HttpURLConnection) u.openConnection(); 
    	 StringBuilder response = new StringBuilder(); 
    	 if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
    		 
    		 //Get the Stream reader ready 
    		 BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()),8192); 
    		 String line = null; 

    		 while ((line = input.readLine()) != null) 
    		 { 
    			 response.append(line); 
    		 } 

    		 input.close(); 
    	 }
    	 return response.toString(); 
    }
    
    Transaction parseTransactions(String json) throws JSONException {
    	JSONObject obj = new JSONObject(json);
    	
    	Transaction result = new Transaction();
    	result.amount = obj.getDouble("amount");
    	result.date = obj.getLong("date");
    	result.otherParty = obj.getString("otherParty");
    	return result;
    }
    
    class Transaction {
    	double amount;
    	String otherParty;
    	long date;
    }
}
