package alarm.main;

import java.util.Calendar;
import java.util.Date;

import android.view.View.OnClickListener;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.ToggleButton;
import android.widget.TextView;
import android.view.View;
import android.os.Bundle;
import android.os.SystemClock;

import alarm.main.HorizontalSlider.OnProgressChangeListener;
import alarm.main.R.id;
import alarm.main.R.layout;
import alarm.main.R;

public class SliderActivity extends Activity
{
	
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
    	//needed for slider?
    	//requestWindowFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider);
    	
        slider_ = (HorizontalSlider)findViewById(R.id.progressBar);
        debugText_ = (TextView)findViewById(R.id.DebugText);

        slider_.setOnProgressChangeListener(changeListener);
        //slider_.setVisibility(View.GONE);
    }

    
    //This aknowledges an alarm
    private OnProgressChangeListener changeListener = new OnProgressChangeListener()
    {
    	public void onProgressChanged(View v, int progress)
    	{
    		if (progress > 80 )
    		{
    			//acknowledgeManager_.acknowledgeAlarm();
    		}
    	}
	};
	
    private HorizontalSlider slider_;
    private static TextView debugText_;
    
    public static void debug()
    {
    	debugText_.post(new Runnable() {
            public void run() {
            	debugText_.append("debug");
            }
        });
    }
//Async write to debug
//	final String text = Integer.toString(int_value);
//	debugText_.post(new Runnable() {
//        public void run() {
//        	debugText_.append(text);
//        }
//    });
    
//This ends the application
//finish();
    
}