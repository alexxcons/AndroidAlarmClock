package alarm.main;

import java.util.ArrayList;
import java.util.List;

import alarm.main.globalsettings.Model;
import alarm.main.globalsettings.WeekdaysArrayAdapter;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class TestActivity extends ListActivity {

	//TAke a try for: http://stackoverflow.com/questions/6071914/constructing-dynamic-list-view-in-android
	// oR read android sdk-examples
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
		list.add(get("x"));
		list.add(get("y"));
		list.add(get("z"));

		// Initially select one of the items
		list.get(1).setSelected(true);
		return list;
	}

	private Model get(String s) {
		return new Model(s);
	}

} 
    