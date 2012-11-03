package alarm.main;

import java.util.Date;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class AlarmGenerator extends BroadcastReceiver 
{
	//Default Constructor, called by Alarm-Manager
	//Dont add arguments needs to stay empty !!!
	public AlarmGenerator()
	{
	}
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		//start activity
		Intent i = new Intent();
		i.setClassName("alarm.main", "alarm.main.AcknowledgeActivity");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		context.startActivity(i);
	}

	public void setAlarm(Context context,Date alarmDate)
	{
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmGenerator.class);

		String data = alarmDate.getHours() + ":" + alarmDate.getMinutes();
		Uri uri = Uri.parse(data);
		intent.setData(uri);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		
		am.set(AlarmManager.RTC_WAKEUP, alarmDate.getTime(),pi);
		//am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10, pi); // Millisec * Second * Minute
	}

	public void cancelAlarm(Context context,Date alarmDate)
	{
		Intent intent = new Intent(context, AlarmGenerator.class);
		String data = alarmDate.getHours() + ":" + alarmDate.getMinutes();
		Uri uri = Uri.parse(data);
		intent.setData(uri);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}

}