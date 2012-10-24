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

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


/**
 * This class provides a basic demonstration of how to write an Android
 * activity. Inside of its window, it places a single view: an EditText that
 * displays and edits some internal text.
 */
public class GlassJarActivity extends RoboActivity {
    
    static final private int BACK_ID = Menu.FIRST;
    static final private int CLEAR_ID = Menu.FIRST + 1;

@InjectView(R.id.save_button)
private Button saveButton;
@InjectView(R.id.glassJar1)
private GlassJar glassJar;

    public GlassJarActivity() {
    }

    /** Called with the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.piggie_widget_container);

        saveButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				final Intent saveIntent = new Intent(GlassJarActivity.this, PiggieWidget.class);
	        	saveIntent.setAction(PiggieWidget.SAVE_ACTION);
	        	saveIntent.putExtra(PiggieWidget.EXTRA_PERCENTAGE, glassJar.getPercentage());
	        	
	        	sendBroadcast(saveIntent);
			}
		});
    }

    /**
     * Called when the activity is about to start interacting with the user.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    public static final double PERCENTAGE_INCREMENT = 5;
    public static final int MAX_JAR_PIXELS = 282;

   
}
