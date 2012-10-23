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

import org.json.JSONException;
import org.json.JSONObject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.LineAndPointRenderer;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;

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
    @InjectView(R.id.anim_button)
    private Button mAnimButton;
    
    private XYPlot mPlot;
    
    public MainActivity() {
    }

    /** Called with the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.main_activity);

        mPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
        // Hook up button presses to the appropriate event handler.
        backButton.setOnClickListener(mBackListener);
        clearButton.setOnClickListener(mClearListener);
        mDownloadButton.setOnClickListener(mDownloadListener);
        mAnimButton.setOnClickListener(mAnimListener);
        
        mEditor.setText(getText(R.string.main_label));
        
        XYSeries series = new XYSeries() {
			double[] values = new double[]{5,7,4,6,8,8,9,0,1,4,6,7};
			public int size() {
				return values.length;
			}
			
			public String getTitle() {
				return "Mi serie";
			}
			
			public Number getX(int i) {
				return i;
			}
			
			public Number getY(int i) {
				return values[i];
			}
			
			public Number getMaxY() {
				return 20;
			}
			
			private Number getMaxX() {
				return values.length;
			}
			
			private Number getMinY() {
				return 0;
			}
			
			private Number getMinX() {
				return -5;
			}
			
			
		};
		Number max = mPlot.getCalculatedMaxY();
		mEditor.setText("Max Y :"+max);
        mPlot.addSeries(new SimpleXYSeries(), new LineAndPointFormatter(Color.rgb(0xFF, 0, 0),Color.rgb(0, 0xFF, 0),Color.rgb(0, 0, 0xFF)));
        mPlot.addSeries(new SimpleXYSeries(), new BarFormatter(Color.rgb(0xFF, 0, 0),Color.rgb(0, 0xFF, 0)));
        mPlot.addSeries(series, new LineAndPointFormatter(Color.rgb(0x66, 0, 0),Color.rgb(0, 0x66, 0),Color.rgb(0, 0, 0x66)));
     
        mPlot.setDomainStepMode(XYStepMode.SUBDIVIDE);
        mPlot.setDomainStepValue(20);
 
        // thin out domain/range tick labels so they dont overlap each other:
        mPlot.setTicksPerDomainLabel(5);
        mPlot.setTicksPerRangeLabel(3);
        mPlot.disableAllMarkup();
 
        // freeze the range boundaries:
        mPlot.setRangeBoundaries(-10, 100, BoundaryMode.FIXED);
        
        new Thread(){
        	public void run(){
                try {
        			mPlot.postRedraw();
        		} catch (InterruptedException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		
        	}
        }.start();

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
    /**
     * Goes to animation activity.
     */
    OnClickListener mAnimListener= new OnClickListener() {
        public void onClick(View v) {
        	startActivity(new Intent("com.finapps.OPEN_JAR"));
        	
        }

    };
    /** * A call-back for when the user presses the download button.
     */
    OnClickListener mDownloadListener= new OnClickListener() {
        public void onClick(View v) {
        	try {
				String s = download();
				Transaction t = parseTransactions(s);
				
				mEditor.setText(t.amount+" euros from: "+t.otherParty+". JSON:" + s);
				
				
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
            MainActivity.this.sendBroadcast(new Intent(PiggieWidget.SAVE_ACTION));
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
    
    
    class SimpleXYSeries implements XYSeries {
        private final int[] vals = {0, 25, 55, 2, 80, 30, 99, 0, 44, 6};
     
        // f(x) = x
        public Number getX(int index) {
            return index;
        }
     
     
        // range begins at 0
        public Number getMinX() {
            return 0;
        }
     
        // range ends at 9
        public Number getMaxX() {
            return 9;
        }
     
        public String getTitle() {
            return "Some Numbers";
        }
     
        // range consists of all the values in vals
        public int size() {
            return vals.length;
        }
     
        public void onReadBegin() {
     
        }
     
        public void onReadEnd() {
     
        }
     
        // return vals[index]
        public Number getY(int index) {
            // make sure index isnt something unexpected:
            if(index < 0 || index > 9) {
                throw new IllegalArgumentException("Only values between 0 and 9 are allowed.");
            }
            return vals[index];
        }
     
        // smallest value in vals is 0
        public Number getMinY() {
            return 0;
        }
     
        // largest value in vals is 99
        public Number getMaxY() {
            return 99;
        }
    }
}
