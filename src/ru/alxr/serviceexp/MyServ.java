package ru.alxr.serviceexp;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class MyServ extends Service {
	Context context;
	@Override
    public void onCreate() {
    	Toast.makeText(this, "Служба создана", 
                Toast.LENGTH_SHORT).show(); 
    	
        // TODO: действия, которые будут выполняться при создании сервиса
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	context = getApplicationContext();
		Intent myIntent = new Intent(MyServ.this,
				MyScheduledTask.class);

		PendingIntent pendingIntent = PendingIntent.getService(MyServ.this, 0,
				myIntent, 0);
    	
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		System.currentTimeMillis();
		/*
		 * имеем строку с датой, когда надо выполнять задание
		 * */
		String whenToStartTask ="";
		try{
		whenToStartTask = intent.getExtras().getString("date"); 
//				"2013-03-31 17:45";"2013-03-31 18:45"
		}catch (Exception e){
			Toast.makeText(this, "Exc 1" , Toast.LENGTH_LONG).show();
		}Toast.makeText(this, whenToStartTask, Toast.LENGTH_LONG).show();
		/*
		 * извлекаем из этой строки объект календаря:
		 * */
		try{
		String day = whenToStartTask.split(" ")[0];
		
		String time = whenToStartTask.split(" ")[1];
		String year = day.split("-")[0];
		String month = day.split("-")[1];
		String dayOfMonth = day.split("-")[2];
		String hour = time.split(":")[0];
		String min = time.split(":")[1];
		int yearD = Integer.valueOf(year);
		int monthD = Integer.valueOf(month) - 1;
		int dayOfMonthD = Integer.valueOf(dayOfMonth);
		int hourD = Integer.valueOf(hour);
		int minD = Integer.valueOf(min);
		calendar.set(yearD, monthD, dayOfMonthD, hourD, minD, 0);
		}catch(Exception e){
			Toast.makeText(this, "Exc 2" , Toast.LENGTH_LONG).show();
		}
		 

		try {
			alarmManager.cancel(pendingIntent);
			alarmManager.set(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), pendingIntent);
			
		} catch (Exception e) {
			Toast.makeText(this, "Exc 3" , Toast.LENGTH_LONG).show();
		}

		try {
			
			ServiceFinishedTask st = new ServiceFinishedTask();
			st.onHandleIntent(null);
			Toast.makeText(this, "no Exc 4" , Toast.LENGTH_LONG).show();

		} catch (Exception e) {
			Toast.makeText(this, "Exc 4" , Toast.LENGTH_LONG).show();
		}

		
        if ((flags & START_FLAG_RETRY) == 0) {
            // TODO Если это повторный запуск, выполнить какие-то действия.
        }
        else {
            // TODO Альтернативные действия в фоновом режиме.
        }
        
        
        return Service.START_STICKY;
    }

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
    @Override 
    public void onDestroy() 
	{ 
        Toast.makeText(this, "Служба остановлена", 
            Toast.LENGTH_SHORT).show(); 
 
    } 
    
    public class ServiceFinishedTask extends IntentService {

  	  public static final String BROADCAST= "123456789123456789";
  		      
  		  Intent broadcast = new Intent(BROADCAST);
  		  
  		  public ServiceFinishedTask() {
  		    super("ServiceFinishedTask");
  		  }
  		  
  		  @Override
  		  protected void onHandleIntent(Intent intent) {
  			  
  		    LocalBroadcastManager.getInstance(context).sendBroadcast(broadcast);
  		    
  		  }

			
  }

}
