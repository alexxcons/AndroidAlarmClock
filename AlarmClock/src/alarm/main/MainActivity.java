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

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Date now_ = new Date();
        alarmEnableButton_ = (ToggleButton)findViewById(R.id.AlarmEnable);
        alarmHourButton_ = (NumberPicker)findViewById(R.id.Hour);
        alarmMinuteButton_ = (NumberPicker)findViewById(R.id.Minute);
        
        alarmHourButton_.setRange(0,23);
        alarmHourButton_.setCurrent(now_.getHours());

        alarmMinuteButton_.setRange(0,59);
        alarmMinuteButton_.setCurrent(now_.getMinutes());
        
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
	        			
	        		  alarmGenerator_.SetAlarm(v.getContext(),input);
	        		  
	        		  //for debugging
	        		  //alarmGenerator_.SetAlarm(v.getContext(),now);
	        		  
	        		  String text = "Alarm set to:" + input.getHours() + ":" + input.getMinutes();
	        		  Toast.makeText(getApplicationContext(), text,Toast.LENGTH_LONG).show();
	        	  }
	          }
	          
	          public void showSlider()
	          {
	      		Intent myIntent = new Intent(getApplicationContext(), AcknowledgeActivity.class);
	      		startActivity(myIntent);   	  
	          }
	        });
        
        alarmGenerator_ = new AlarmGenerator();

        //Neues Fenster Öffnen:
        //Intent myIntent = new Intent(AlarmClockActivity.this, ListSample.class);
        //AlarmClockActivity.this.startActivity(myIntent);
    }
	
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
    
}