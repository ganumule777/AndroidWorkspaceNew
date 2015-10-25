package com.techroot.bookdoctorstime;

import java.util.ArrayList;
import java.util.List;

import com.techroot.bookdoctorstime.doctorManagement.model.Doctor;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.os.Build;

public class DoctorsProfileActivity extends ActionBarActivity {

	// adapter
    private CustomAdapter valueAdapter;
	private ArrayList<Doctor> listDoctor;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctors_profile);

		listDoctor=new ArrayList<Doctor>();
		
		listDoctor=(ArrayList<Doctor>) getIntent().getSerializableExtra("docSearchList");

		// 1. pass context and data to the custom adapter
	    valueAdapter = new CustomAdapter(this, generateData(listDoctor));
        // 2. Get ListView from activity_main.xml
        ListView listView = (ListView) findViewById(R.id.list);
 
        // 3. setListAdapter
        listView.setAdapter(valueAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctors_profile, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_doctors_profile,
					container, false);
			return rootView;
		}
	}
	
	/*******************************************************************************
	 ** Function Name   :	generateData
	 ** Created By      :	Techroot Pvt Ltd
	 ** Description		:	This function is used to add number of rows in our user defined list adapter
	 ** Creation Date	:	12/09/2014
	 ** Arguments		:	List of Post class
	 ** Return Type     :	ArrayList<RowItem>
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
    private ArrayList<RowItem> generateData(ArrayList<Doctor> listDoctor)
    {

       ArrayList<RowItem> items = new ArrayList<RowItem>();
       for(int iCount=0;iCount<listDoctor.size();iCount++)
       {    	   
           items.add(new RowItem(listDoctor.get(iCount).getDocId(),listDoctor.get(iCount).getDocFirstName()+" "+listDoctor.get(iCount).getDocLastName(),listDoctor.get(iCount).getDocSpecilization(),listDoctor.get(iCount).getDocClinicName(),listDoctor.get(iCount).getArea(),listDoctor.get(iCount).getYearsOfExp(),listDoctor.get(iCount).getConsulationFee(),listDoctor.get(iCount).getDocQualfctn(),listDoctor.get(iCount).getAppmntMode()));
       }
       
        return items;
    }

}
