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
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * This class provides a basic demonstration of how to write an Android
 * activity. Inside of its window, it places a single view: an EditText that
 * displays and edits some internal text.
 */
public class GlassJarActivity extends RoboActivity {
    
    static final private int BACK_ID = Menu.FIRST;
    static final private int CLEAR_ID = Menu.FIRST + 1;

    @InjectView(R.id.layout)
    private RelativeLayout mLayout;
    @InjectView(R.id.percentage_textview)
    private TextView mPercentageTextView;
    @InjectView(R.id.cointile_layout)
    private RelativeLayout mCoinTileLayout;
    @InjectView(R.id.glassjar_imageview)
    private ImageView mJarImageView;
    
    double mPercentage = 0;
    public GlassJarActivity() {
    }

    /** Called with the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.anims_activity);

        mLayout.setOnClickListener(mJarListener);
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

    /**
     * A call-back for when the user presses the back button.
     */
    OnClickListener mJarListener = new OnClickListener() {
        public void onClick(View v) {
        	mPercentage +=PERCENTAGE_INCREMENT;
        	
        	mPercentageTextView.setText(Double.toString(mPercentage));
        	
        	double coinPixels=MAX_JAR_PIXELS/100*mPercentage;
        	
        	final LayerDrawable layerDrawable = (LayerDrawable)mCoinTileLayout.getBackground();
        	final ScaleDrawable scaleDrawable = (ScaleDrawable)layerDrawable.findDrawableByLayerId(R.id.coin_drawable);
        	
        	
//        	scaleDrawable.setLevel((int)mPercentage*100);
 //       	scaleDrawable.invalidateSelf();
        	ObjectAnimator anim = ObjectAnimator.ofInt(scaleDrawable, "Level", (int)mPercentage*100);
        	anim.setInterpolator(new BounceInterpolator());
        	anim.addUpdateListener(new AnimatorUpdateListener() {
				
				public void onAnimationUpdate(ValueAnimator animation) {
					
					scaleDrawable.invalidateSelf();
				}
			});
        	anim.setDuration(1000);
        	anim.start();	
        }

    };

    
    
}
