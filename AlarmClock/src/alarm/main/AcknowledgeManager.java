package alarm.main;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.View;

public class AcknowledgeManager {
	
	public AcknowledgeManager(final HorizontalSlider slider)
	{
		slider_ = slider;
	}
	
	public static void envokeAlarm(Context context)
    {
      	Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
      	Ringtone alarm = RingtoneManager.getRingtone(context, notification);
      	
      	playAlarm_ = true;
      	
    	//show slider
		slider_.post(new Runnable() {
			public void run()
			{
				slider_.setVisibility(View.VISIBLE);
			}
	  	});
		while(playAlarm_)
		{
			alarm.play();
		}
    	//hide slider
		slider_.post(new Runnable() {
			public void run()
			{
				slider_.setVisibility(View.GONE);
			}
	  	});
    }

	public void acknowledgeAlarm()
	{
		playAlarm_ = false;
	}
	
	private static HorizontalSlider slider_;
	private static boolean playAlarm_ = false;
}
