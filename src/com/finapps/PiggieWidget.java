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

public class PiggieWidget extends AppWidgetProvider {
	public static final String SAVE_ACTION = "com.finapps.SAVE";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
        int[] appWidgetIds) {
        // To prevent any ANR timeouts, we perform the update in a service
        context.startService(new Intent(context, UpdateService.class));
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
    	// TODO Auto-generated method stub
    	super.onReceive(context, intent);
    }
    
    public static class UpdateService extends Service {

		@Override
		public IBinder onBind(Intent arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
        @Override
        public void onStart(Intent intent, int startId) {
            // Build the widget update for today
            RemoteViews updateViews = buildUpdate(this);

            // Push update for this widget to the home screen
            ComponentName thisWidget = new ComponentName(this, PiggieWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thisWidget, updateViews);
        }
        
        private RemoteViews buildUpdate(Context context){
        	RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.piggie_layout);
        	
        	Intent intent = new Intent("com.finapps.OPEN_JAR");
        	 PendingIntent pendingIntent = PendingIntent.getActivity(context,
                     0 /* no requestCode */, intent, 0 /* no flags */);
             views.setOnClickPendingIntent(R.id.toapp_button, pendingIntent);
             
        	Intent saveIntent = new Intent(SAVE_ACTION);
        	 PendingIntent savePendingIntent = PendingIntent.getActivity(context,
                     0 /* no requestCode */, saveIntent, 0 /* no flags */);
             views.setOnClickPendingIntent(R.id.save_button, savePendingIntent);
        	return views;
        }
    }
}
