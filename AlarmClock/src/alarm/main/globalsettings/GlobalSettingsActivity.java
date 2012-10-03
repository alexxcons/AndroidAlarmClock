package alarm.main.globalsettings;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class GlobalSettingsActivity extends ListActivity {

	
/** Called when the activity is first created. */

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// Create an array of Strings, that will be put to our ListActivity
		ArrayAdapter<Model> adapter = new WeekdaysArrayAdapter(this,
				getModel());
		setListAdapter(adapter);
	}

	private List<Model> getModel() {
		List<Model> list = new ArrayList<Model>();
		list.add(get("monday"));
		list.add(get("tuesday"));
		list.add(get("wednesday"));
		list.add(get("thursday"));
		list.add(get("friday"));
		list.add(get("saturday"));
		list.add(get("sunday"));
		// Initially select one of the items
		list.get(1).setSelected(true);
		return list;
	}

	private Model get(String s) {
		return new Model(s);
	}

} 
    