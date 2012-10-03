package alarm.main;

import java.util.Date;

import alarm.main.AlarmClockActivity.IncomingHandler;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
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
	Context activityContext_;
    
	//Test
	//Default Constructor, called by Alarm-Manager
	public AlarmGenerator()
	{
	}
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		//Intent myIntent = new Intent(activityContext_, SliderActivity.class);
		//activityContext_.startActivity(myIntent);
		
		Toast.makeText(context, "Alaaaaarm",Toast.LENGTH_LONG).show();

		
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlarmLock");
		wl.acquire();

		// Vibrate the mobile phone
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(2000);
		
		try
		{
			
			Message msg = Message.obtain(null, AlarmService.MSG_TRIGGER_ALARM);
			alarmServiceMessenger_.send(msg);
			
		}
		catch (RemoteException e)
		{
			Toast.makeText(context, "Alaaaaarm_FAIL",Toast.LENGTH_LONG).show();

				// In this case the service has crashed before we could even do anything with it
		}
		
		//AcknowledgeManager.envokeAlarm(context);
		wl.release();
	}

	public void SetAlarm(Context context,Date alarmDate)
	{
		activityContext_ = context;

		//Intent myIntent = new Intent(context, SliderActivity.class);
		//context.startActivity(myIntent);
		
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, AlarmGenerator.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		
		am.set(AlarmManager.RTC_WAKEUP, alarmDate.getTime(),pi);
		//am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10, pi); // Millisec * Second * Minute
	}

	public void CancelAlarm(Context context)
	{
		Intent intent = new Intent(context, AlarmGenerator.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
	
	private ServiceConnection alarmServiceConnection_ = new ServiceConnection()
	{
		public void onServiceConnected(ComponentName className, IBinder service)
		{
			alarmServiceMessenger_ = new Messenger(service);
		}

		public void onServiceDisconnected(ComponentName name)
		{
			alarmServiceMessenger_ = null;
		}
	};
	
	Messenger alarmServiceMessenger_ = null;
}