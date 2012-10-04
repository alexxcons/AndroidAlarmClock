package alarm.main;

import java.util.Date;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.Vibrator;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmGenerator extends BroadcastReceiver 
{
	//Default Constructor, called by Alarm-Manager
	//Dont add arguments needs to stay empty !!!
	public AlarmGenerator()
	{
	}
	
//	public AlarmGenerator(Messenger callbackIncomingAlarm)
//	{
//		callbackIncomingAlarm_ = callbackIncomingAlarm;
//	}
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		//Intent myIntent = new Intent(activityContext_, SliderActivity.class);
		//activityContext_.startActivity(myIntent);
		
		//Toast.makeText(context, "Alaaaaarm",Toast.LENGTH_LONG).show();

		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlarmLock");
		wl.acquire();

		// Vibrate the mobile phone
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(2000);
		
		//start activity
		Intent i = new Intent();
		i.setClassName("alarm.main", "alarm.main.SliderActivity");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		context.startActivity(i);
		//AcknowledgeManager.envokeAlarm(context);
		wl.release();
	}

	public void SetAlarm(Context context,Date alarmDate,Handler alarmHandler)
	{
		//activityContext_ = context;

		//Intent myIntent = new Intent(context, SliderActivity.class);
		//context.startActivity(myIntent);
		
		//alarmHandler_ = alarmHandler;
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, AlarmGenerator.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		
		//for debugging
		Date now = new Date();
		now.setSeconds(0);
		am.set(AlarmManager.RTC_WAKEUP, now.getTime(),pi);
		
		//am.set(AlarmManager.RTC_WAKEUP, alarmDate.getTime(),pi);
		//am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10, pi); // Millisec * Second * Minute
	}

	public void CancelAlarm(Context context)
	{
		Intent intent = new Intent(context, AlarmGenerator.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}

}