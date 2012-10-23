package com.finapps;

import roboguice.inject.InjectView;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.BounceInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GlassJar extends RelativeLayout implements OnTouchListener, OnClickListener{
	  public static final double PERCENTAGE_INCREMENT = 5;
	    public static final int MAX_JAR_PIXELS = 282;

	  
	private float initialY = 0;
	private double initialPercentage= 0;
	double mPercentage = 0;
	private RelativeLayout mCoinTileLayout;
	private RelativeLayout mLayout;
	private TextView mPercentageTextView;
	 
	
	public GlassJar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public GlassJar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GlassJar(Context context) {
		super(context);
		init();
	}

	private void init()
	{
		inflate(getContext(), R.layout.piggie_layout, this);
		
		mCoinTileLayout = (RelativeLayout) this.findViewById(R.id.cointile_layout);
		mPercentageTextView = (TextView) this.findViewById(R.id.percentage_textview);
		RelativeLayout mLayout = (RelativeLayout) this.findViewById(R.id.layout);
		   
		
        mLayout.setClickable(true);
        mLayout.setOnClickListener(this);
        mLayout.setOnTouchListener(this);
	}
	    
    public boolean onTouch(View v, MotionEvent event) {
    	
    	switch (event.getAction() & MotionEvent.ACTION_MASK) {
    	case MotionEvent.ACTION_DOWN: // Start gesture
    		initialY = event.getY();
    		initialPercentage = mPercentage;
    		break;
    	case MotionEvent.ACTION_MOVE:
    		final LayerDrawable layerDrawable = (LayerDrawable)mCoinTileLayout.getBackground();
    		final ScaleDrawable scaleDrawable = (ScaleDrawable)layerDrawable.findDrawableByLayerId(R.id.coin_drawable);
    		mPercentage = initialPercentage+(initialY - event.getY())*100f/300f;
    		
    		if (mPercentage>100)
    		{
    			mPercentage =100;
    		}
    		if (mPercentage<0)
    		{
    			mPercentage = 0;
    		}
    		
        	mPercentageTextView.setText(Integer.toString((int)mPercentage)+"%");
        	
    		scaleDrawable.setLevel((int)(mPercentage*100));
    		scaleDrawable.invalidateSelf();
    		return true;
		}    	
		
    	return super.onTouchEvent(event);
    };

	public void onClick(View v) {
		mPercentage +=PERCENTAGE_INCREMENT;
    	
    	if(mPercentage >= 100)
    	{
    		mPercentage = 100;
    	}
    	
    	mPercentageTextView.setText(Integer.toString((int)mPercentage)+"%");
    	
    	double coinPixels=MAX_JAR_PIXELS/100*mPercentage;
    	
    	final LayerDrawable layerDrawable = (LayerDrawable)mCoinTileLayout.getBackground();
    	final ScaleDrawable scaleDrawable = (ScaleDrawable)layerDrawable.findDrawableByLayerId(R.id.coin_drawable);
    	
//    	scaleDrawable.setLevel((int)mPercentage*100);
//       	scaleDrawable.invalidateSelf();
    	ObjectAnimator anim = ObjectAnimator.ofInt(scaleDrawable, "Level", (int)(mPercentage*100));
    	anim.setInterpolator(new BounceInterpolator());
    	anim.addUpdateListener(new AnimatorUpdateListener() {
			
			public void onAnimationUpdate(ValueAnimator animation) {
				
				scaleDrawable.invalidateSelf();
			}
		});
    	anim.setDuration(1000);
    	anim.start();	
    }
}
