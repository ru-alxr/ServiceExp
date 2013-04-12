package ru.alxr.serviceexp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyScheduledTask extends Service {
	private static final int NOTIFY_ID = 1;

	@Override
	public boolean onUnbind(Intent intent) {
//		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Toast.makeText(getApplicationContext(), "MyScheduledTask onStart", Toast.LENGTH_SHORT).show();

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		int icon = android.R.drawable.sym_action_email;
		CharSequence tickerText = "Эй, ученик!"; 
		long when = System.currentTimeMillis(); 
		Notification notification = new Notification(icon, tickerText, when);
		Intent intentN = new Intent(this, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intentN, 0);
		
		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.my_notification_layout); 
		contentView.setImageViewResource(R.id.image, R.drawable.books); 
		contentView.setTextViewText(R.id.text,"Открой программу и запусти игру!"); 
		

	    notification.contentIntent = pIntent; 
	    notification.contentView = contentView;
	    long[] vibrate = {	300,200, 300, 200, 300, 200, 100, 200, 100, 200, 400, 200, 100, 200, 100, 200, 100, 200, 300, 200, 100, 200};
	    notification.vibrate = vibrate;
	    notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(NOTIFY_ID, notification); 
		
		onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
