package alarm.main;

import java.util.Calendar;
import java.util.Date;

import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import alarm.main.HorizontalSlider.OnProgressChangeListener;
import alarm.main.numberpicker.NumberPicker;
import alarm.main.AlarmGenerator;
import alarm.main.AcknowledgeManager;

public class AlarmClockActivity extends Activity
{
	
	Messenger alarmServiceMessenger_ = null;
	boolean mIsBound;
	final Messenger mMessenger = new Messenger(new IncomingHandler());
	
	class IncomingHandler extends Handler
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case AlarmService.MSG_SET_INT_VALUE:
					break;
				default:
					super.handleMessage(msg);
			}
		}
	}
	 
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
    	//needed for slider?
    	//requestWindowFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	
        if (AlarmService.isRunning())
        {
        	bindService(new Intent(this, AlarmService.class), alarmServiceConnection_, Context.BIND_AUTO_CREATE);
        	mIsBound = true;
        }
        
        Date now_ = new Date();
        alarmEnableButton_ = (ToggleButton)findViewById(R.id.AlarmEnable);
        alarmHourButton_ = (NumberPicker)findViewById(R.id.Hour);
        alarmMinuteButton_ = (NumberPicker)findViewById(R.id.Minute);
        slider_ = (HorizontalSlider)findViewById(R.id.progressBar);
        
        alarmHourButton_.setRange(0,23);
        alarmHourButton_.setCurrent(now_.getHours());

        alarmMinuteButton_.setRange(0,59);
        alarmMinuteButton_.setCurrent(now_.getMinutes());
        
        slider_.setOnProgressChangeListener(changeListener);
        slider_.setVisibility(View.GONE);
        
        alarmEnableButton_.setOnClickListener(new OnClickListener(){
	          public void onClick(View v)
	          {
	        	  if(alarmEnableButton_.isChecked())
	        	  {
	        		  Date input = new Date();
	        		  input.setHours(alarmHourButton_.getCurrent());
	        		  input.setMinutes(alarmMinuteButton_.getCurrent());
	        		  input.setSeconds(0);
	        		  
	        		  Date now = new Date();
	        		  now.setSeconds(0);
	        		  
	        		  if ( input.before(now) )
	        		  {
	        			  input.setDate(input.getDate()+1);
	        		  }
	        			
	        		  alarmControler_.SetAlarm(v.getContext(),now);
	        		  
	        		  String text = "Alarm set to:" + input.getHours() + ":" + input.getMinutes();
	        		  //Toast.makeText(getApplicationContext(), text,Toast.LENGTH_LONG).show();
	        	  }
	          }
	          
	          public void showSlider()
	          {
	      		Intent myIntent = new Intent(getApplicationContext(), SliderActivity.class);
	      		startActivity(myIntent);   	  
	          }
	        });
        
        acknowledgeManager_ = new AcknowledgeManager(slider_);
        alarmControler_ = new AlarmGenerator();

        //Neues Fenster Öffnen:
        //Intent myIntent = new Intent(AlarmClockActivity.this, SliderActivity.class);
		//AlarmClockActivity.this.startActivity(myIntent);
        //Intent myIntent = new Intent(AlarmClockActivity.this, ListSample.class);
        //AlarmClockActivity.this.startActivity(myIntent);
        
    }
    	
    //This aknowledges an alarm
    private OnProgressChangeListener changeListener = new OnProgressChangeListener()
    {
    	public void onProgressChanged(View v, int progress)
    	{
    		if (progress > 80 )
    		{
    			acknowledgeManager_.acknowledgeAlarm();
    		}
    	}
	};
	
	//Handle the menu-button, if there is any
	@Override
	public boolean onKeyDown(int keycode, KeyEvent event )
	{
		if(keycode == KeyEvent.KEYCODE_MENU)
		{
	        //Intent myIntent = new Intent(AlarmClockActivity.this, GlobalSettingsActivity.class);
	        //AlarmClockActivity.this.startActivity(myIntent);
	        
	        Intent myIntent = new Intent(AlarmClockActivity.this, TestActivity.class);
	        AlarmClockActivity.this.startActivity(myIntent);
		}
		return super.onKeyDown(keycode,event);
	}
	
	private ServiceConnection alarmServiceConnection_ = new ServiceConnection()
	{
		public void onServiceConnected(ComponentName className, IBinder service)
		{
			alarmServiceMessenger_ = new Messenger(service);
			try
			{
				Message msg = Message.obtain(null, AlarmService.REGISTER_ALARM_ACTIVITY);
				msg.replyTo = mMessenger;
				alarmServiceMessenger_.send(msg);
			}
			catch (RemoteException e)
			{
					// In this case the service has crashed before we could even do anything with it
			}
		}

		public void onServiceDisconnected(ComponentName name)
		{
			alarmServiceMessenger_ = null;
		}
	};
		
	private AlarmGenerator alarmControler_;
	private AcknowledgeManager acknowledgeManager_;
    private ToggleButton alarmEnableButton_;
    private NumberPicker alarmHourButton_;
    private NumberPicker alarmMinuteButton_;
    private HorizontalSlider slider_;

//	final String text = Integer.toString(int_value);
    
//This ends the application
//finish();
    
}