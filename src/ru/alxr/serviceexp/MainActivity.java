package ru.alxr.serviceexp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.LocalBroadcastManager;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
Button 	btnStart,
		btnStop,
		btnExitApp,
		btnJustGo;
Context context;
private static final int NOTIFY_ID = 1;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_main);
		
		btnStart = (Button)findViewById(R.id.button1);
		btnStop = (Button)findViewById(R.id.button2);
		btnExitApp = (Button)findViewById(R.id.button3);
		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		btnExitApp.setOnClickListener(this);
		btnJustGo = (Button)findViewById(R.id.justGoButton);
		btnJustGo.setOnClickListener(this);
		
		
			}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()){
		case (R.id.button1):
			{
			Intent intent = new Intent (this, MyServ.class);
			intent.putExtra("date", "2013-04-12 23:20");
			startService(intent);
			
//			LocalBroadcastManager a = LocalBroadcastManager.getInstance(this);
//			a.registerReceiver(this, intent);
				break;
			}
		case (R.id.button2):
			{
				stopService( 
			            new Intent(this, MyServ.class));
				break;
				}
		case (R.id.button3):
		{

			AsyncTaskSample ast = new AsyncTaskSample(this);	
			ast.execute();
			
		
			break;
		}
		case (R.id.justGoButton) : {
			this.finish();
			break;
		}
		default:break;
		
		}
		
	}
	
	class AsyncTaskSample extends AsyncTask {
		Context context;
		public AsyncTaskSample (Context _context){
		this.context =_context;}
		
		@Override
		protected Object doInBackground(Object... params) {
			IntentFilter filter = new IntentFilter(MyServ.ServiceFinishedTask.BROADCAST);
    		LocalBroadcastManager.getInstance(context).registerReceiver(onNotice, filter);
			return null;
		}
		
		public BroadcastReceiver onNotice = new BroadcastReceiver() {
	        public void onReceive(Context ctxt, Intent i) {
	        	Toast.makeText(context, "123", Toast.LENGTH_LONG).show();
	        	unreg();
	        }
	      };
	      public void unreg(){
	    	  LocalBroadcastManager.getInstance(context).unregisterReceiver(onNotice);
	      }
		
	}

}
