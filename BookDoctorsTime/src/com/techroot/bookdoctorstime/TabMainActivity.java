package com.techroot.bookdoctorstime;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.os.Build;

public class TabMainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_main);

		// adding tab
		// create the TabHost that will contain the Tabs
        final TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        TabSpec tab1 = tabHost.newTabSpec("Doctor");
        TabSpec tab2 = tabHost.newTabSpec("Speciality");
        
        /** Add the tabs  to the TabHost to display. */
        
        //call tab
        tab1.setIndicator("Doctor");
        tab1.setContent(new Intent(this,DoctorsNameSearchActivity.class));
        
        tab2.setIndicator("Speciality");
      
        tab2.setContent(new Intent(this,SpecializationSearchActivity.class));
        
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_tab_main,
					container, false);
			return rootView;
		}
	}

}
