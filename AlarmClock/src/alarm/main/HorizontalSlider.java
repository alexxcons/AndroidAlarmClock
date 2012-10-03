package alarm.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

public class HorizontalSlider extends ProgressBar {

	private OnProgressChangeListener listener;
	private static int padding = 2;

	public interface OnProgressChangeListener {
		void onProgressChanged(View v, int progress);
		}

	public HorizontalSlider(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		}

	public HorizontalSlider(Context context, AttributeSet attrs) {
		super(context, attrs, android.R.attr.progressBarStyleHorizontal);
		}

	public HorizontalSlider(Context context) {
		super(context);
		}

	public void setOnProgressChangeListener(OnProgressChangeListener l) {
		listener = l;
		}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int action = event.getAction();
		
		if (action == MotionEvent.ACTION_UP )
		{
			this.setProgress(0);
			old_progress = 0;
		}
		
		if (action == MotionEvent.ACTION_MOVE )
		{
			float x_mouse = event.getX() - padding;
			float width = getWidth() - 2*padding;
			int progress = Math.round((float) getMax() * (x_mouse / width));

			if (progress < old_progress )
			{
				return false;
			}
			
			old_progress = progress;
			
			this.setProgress(progress);
			
			if (listener != null)
				listener.onProgressChanged(this, progress);
		}
		return true;
	}
	
	private int old_progress = 0;
}
