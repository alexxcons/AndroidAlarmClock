package alarm.main;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class AlarmService extends Service
{
	private static boolean isRunning = false;
	
	Messenger alarmActivityMessenger_ = null; //The activity, which we need to notify
	private boolean alarmActivityIsRegistered_ = false;
	
	static final int REGISTER_ALARM_ACTIVITY = 1;
	static final int MSG_TRIGGER_ALARM = 3;
	
	static final int MSG_SET_INT_VALUE = 4;
	
	final Messenger inomingMessageHandler_ = new Messenger(new IncomingHandler()); // Target we publish for clients to send messages to IncomingHandler.

	public IBinder onBind(Intent intent)
	{
		return inomingMessageHandler_.getBinder();
	}

	public static boolean isRunning()
	{
		return isRunning;
	}
	
	class IncomingHandler extends Handler
	{ // Handler of incoming messages from clients.
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case REGISTER_ALARM_ACTIVITY:
					alarmActivityMessenger_ = msg.replyTo;
					alarmActivityIsRegistered_ = true;
					break;
				case MSG_TRIGGER_ALARM:
					
					Toast.makeText(getBaseContext(), "Alaaaaarm_Debug1",Toast.LENGTH_LONG).show();
					triggerAlarm(123);
					break;
				default:
					super.handleMessage(msg);
			}
	
		}
	}

	private void triggerAlarm(int valueToSend)
	{
		if(!alarmActivityIsRegistered_)
			Toast.makeText(getBaseContext(), "NotYetRegistered",Toast.LENGTH_LONG).show();
		
		try
		{
			alarmActivityMessenger_.send(Message.obtain(null, MSG_SET_INT_VALUE, valueToSend, 0));

		}
		catch (RemoteException e)
		{
			// The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
			Toast.makeText(getBaseContext(), "ClientIsdead!! Oo",Toast.LENGTH_LONG).show();
			alarmActivityIsRegistered_ = false;
		}
	}

	public void onCreate()
	{
		super.onCreate();
		isRunning = true;
	}

}
