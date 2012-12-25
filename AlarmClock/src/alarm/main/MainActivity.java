package alarm.main;

import java.util.Calendar;
import java.util.Date;

import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View;
import android.os.Bundle;

import alarm.main.numberpicker.NumberPicker;
import alarm.main.AlarmGenerator;
import utility.StringUtility;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Date now = new Date();
        alarmTime_ = new Date();
        alarmEnableButton_ = (ToggleButton)findViewById(R.id.AlarmEnable);
        alarmHourButton_ = (NumberPicker)findViewById(R.id.Hour);
        alarmMinuteButton_ = (NumberPicker)findViewById(R.id.Minute);
        
        alarmHourButton_.setRange(0,23);
        alarmHourButton_.setCurrent(now.getHours());

        alarmMinuteButton_.setRange(0,59);
        alarmMinuteButton_.setCurrent(now.getMinutes());
        
        alarmEnableButton_.setOnClickListener(new AlarmEnableListener());        
        alarmGenerator_ = new AlarmGenerator();

        //Neues Fenster Öffnen:
        //Intent myIntent = new Intent(AlarmClockActivity.this, ListSample.class);
        //AlarmClockActivity.this.startActivity(myIntent);
    }
    
    class AlarmEnableListener implements OnClickListener
    {
    	public void onClick(View v)
        {
    		if(alarmEnableButton_.isChecked())
    		{
    			alarmTime_.setHours(alarmHourButton_.getCurrent());
    			alarmTime_.setMinutes(alarmMinuteButton_.getCurrent());
    			alarmTime_.setSeconds(0);
      		  
    			Date now = new Date();
    			now.setSeconds(0);
      		  	if ( alarmTime_.before(now) )
      		  	{
      		  		//set for next day, if it is in the past
      		  		alarmTime_.setDate(alarmTime_.getDate()+1);
      		  	}
      		  	
      		  	//for debugging
      		  	//alarmTime_ = now;
      		  	
      		  	alarmGenerator_.setAlarm(v.getContext(),alarmTime_);
  
      		  	alarmHourButton_.setEnabled(false);
      		  	alarmMinuteButton_.setEnabled(false);
      		  	
      		  	String text = "Alarm set to: " + StringUtility.NumberTo2DigitString(alarmTime_.getHours()) + ":" + StringUtility.NumberTo2DigitString(alarmTime_.getMinutes());
      		  	Toast.makeText(getApplicationContext(), text,Toast.LENGTH_LONG).show();
    		}
    		else
    		{
      		  	alarmGenerator_.cancelAlarm(v.getContext(),alarmTime_);
      		  	
      		  	alarmHourButton_.setEnabled(true);
      		  	alarmMinuteButton_.setEnabled(true);
      		  	
      		  	String text = "Alarm: " + StringUtility.NumberTo2DigitString(alarmTime_.getHours()) + ":" + StringUtility.NumberTo2DigitString(alarmTime_.getMinutes()) + " cancelled.";
      		  	Toast.makeText(getApplicationContext(), text,Toast.LENGTH_LONG).show();
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
	        
	        Intent myIntent = new Intent(MainActivity.this, TestActivity.class);
	        MainActivity.this.startActivity(myIntent);
		}
		return super.onKeyDown(keycode,event);
	}
	
	private AlarmGenerator alarmGenerator_;
    private ToggleButton alarmEnableButton_;
    private NumberPicker alarmHourButton_;
    private NumberPicker alarmMinuteButton_;
    
    Date alarmTime_;
    
}