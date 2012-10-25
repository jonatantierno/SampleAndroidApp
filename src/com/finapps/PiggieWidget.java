package com.finapps;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

public class PiggieWidget extends AppWidgetProvider {
	public static final String SAVE_ACTION = "com.finapps.SAVE";
	public static final String EXTRA_PERCENTAGE= "PERCENTAGE";
	
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
        int[] appWidgetIds) {
    	Toast.makeText(context, "onUpdate", Toast.LENGTH_SHORT).show();
    	
        for (int i = 0; i<appWidgetIds.length; i++)
        {
        	final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.piggie_layout);
        	
        	final Intent intent = new Intent("com.finapps.OPEN_JAR");
        	 final PendingIntent pendingIntent = PendingIntent.getActivity(context,
                     0 /* no requestCode */, intent, 0 /* no flags */);
             views.setOnClickPendingIntent(R.id.toapp_button, pendingIntent);
             
        	final Intent saveIntent = new Intent(context, PiggieWidget.class);
        	saveIntent.setAction(SAVE_ACTION);
        	 final PendingIntent savePendingIntent = PendingIntent.getBroadcast(context,
                     0 /* no requestCode */, saveIntent,PendingIntent.FLAG_UPDATE_CURRENT);
             views.setOnClickPendingIntent(R.id.save_button, savePendingIntent);
             
             appWidgetManager.updateAppWidget(appWidgetIds[i], views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
    	// TODO Auto-generated method stub
    	Toast.makeText(context, "onReceive", Toast.LENGTH_SHORT).show();
    	
    	if (intent.getAction().equals(SAVE_ACTION)){
    		double percentage = intent.getDoubleExtra(EXTRA_PERCENTAGE, 0);
    		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.piggie_layout);
        	views.setTextViewText(R.id.percentage_textview, percentage+"%");
             
        	  final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
              final ComponentName cn = new ComponentName(context, PiggieWidget.class);
              
              
              mgr.updateAppWidget(cn, views);
         }
    	
    	super.onReceive(context, intent);
    }
}
