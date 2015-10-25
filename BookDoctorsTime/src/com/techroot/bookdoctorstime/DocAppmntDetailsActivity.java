package com.techroot.bookdoctorstime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.techroot.bookdoctorstime.appointmentManagement.model.Appointment;
import com.techroot.bookdoctorstime.constants.LeverageConstants;
import com.techroot.bookdoctorstime.doctorManagement.model.Doctor;
import com.techroot.bookdoctorstime.doctorManagement.service.DoctorService;
import com.techroot.bookdoctorstime.exception.LeverageExceptions;
import com.techroot.bookdoctorstime.exception.LeverageNonLethalException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class DocAppmntDetailsActivity extends ActionBarActivity {

	private String strDocId;
	ArrayList<Appointment>  serverDocListByName;
    private CustomAdapterAppDocListItems valueAdapter;
	static final int DATE_DIALOG_ID = 999;
	private int year;
	private int month;
	private int day;
	private String strSelectedDate;
    DoctorService objDoctorService;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doc_appmnt_details);
		
		setCurrentDateOnView();
		serverDocListByName=new ArrayList<Appointment>();
		
		strDocId=getIntent().getExtras().getString("docId");

		// WebServer Request URL
	    String serverURL;
		try 
		{
			objDoctorService=new DoctorService(this);
			
			serverURL = LeverageConstants.URL+"getAllAppmntDetails/"+strDocId+"/"+URLEncoder.encode(strSelectedDate, "UTF-8");
			new GetAppByDocId().execute(serverURL);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Use AsyncTask execute Method To Prevent ANR Problem
 catch (LeverageNonLethalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doc_appmnt_details, menu);
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
			View rootView = inflater.inflate(
					R.layout.fragment_doc_appmnt_details, container, false);
			return rootView;
		}
	}

	
	/*********************************************************************************
	    **********************************************************************************
	    ** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
	    ** Author :         	Ganesh Mule
	    ** Created on :     	28-09-2014
	    ** Dept:            	Android Based Mobile App Development
	    ** Class:          		GetAppByDocId
	    ** Description:     	This is inner class used to call suitable http method from server and get its result
	    *						Here we call method to get category details from server
	    ***********************************************************************************
	    ***********************************************************************************/  
	private class GetAppByDocId extends AsyncTask<String, Void, Void> 
	{
		RequestParams params = new RequestParams();
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		String result;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(DocAppmntDetailsActivity.this);
		
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.
			// Start Progress Dialog (Message)
			Dialog.setMessage("Please wait..");
			Dialog.show();
			Dialog.setIndeterminate(false);
			Dialog.setCancelable(false);
			params.add("strDocId", strDocId);
			params.add("strDate", strSelectedDate);

		}
	
		@Override
		protected Void doInBackground(String... arg0) {
			/************ Make Post Call To Web Server ***********/
	
			// HttpGet request = new HttpGet(arg0[0]);
			HttpClient httpClient;
			httpClient = new DefaultHttpClient();
			HttpResponse response;		
			try 
			{
				HttpContext localContext = new BasicHttpContext();
				HttpGet httpGet = new HttpGet(arg0[0]);
				response= httpClient.execute(httpGet, localContext);				 
				HttpEntity entity = response.getEntity();							
				InputStream inputStream = null;
				inputStream= entity.getContent();					
			
				if(inputStream != null)
				result = convertStreamToString(inputStream);							
						
				serverDocListByName.clear();
				if(!("error").equals(result.trim()))
				{
					// Converting gson to List of given elemnts
					Gson gson = new Gson(); 
					ArrayList<Appointment> li=gson.fromJson(result, new TypeToken<List<Appointment>>(){}.getType());									
					serverDocListByName=li;
				}
		
				Content=result;				
												
			} 
			catch (Exception e) 
			{		
				Content=result;
				System.out.println(e.getMessage());
			}
			return null;		
		}
	
		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.
	
			// Close progress dialog
			Dialog.dismiss();
			try 
			{
				if(!(null==Content))
				{
				if (!("error").equals(Content.trim())) 
					{													
							
						if(serverDocListByName.isEmpty())
						{
							populateAppmntDetails(serverDocListByName);

						}
						else
						{
								populateAppmntDetails(serverDocListByName);
						}
				    }					
					else 
					{
						// Show Response Json On Screen (activity)
						throw new LeverageNonLethalException(LeverageExceptions.ERROR_CONNECTING_DATABASE,"Problem to coonecting to the server. Please try again");
					}
			
				}
				else
				{
					throw new LeverageNonLethalException(LeverageExceptions.ERROR_CONNECTING_DATABASE,"Problem to coonecting to the server. Please try again");
				}
			} catch (LeverageNonLethalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		private String convertStreamToString(InputStream is) {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
			} catch (IOException e) {
			}
			Log.i("*************Response*************** :: ", sb.toString());
			return sb.toString();
		}
		
	}
	
	/*******************************************************************************
	 ** Function Name   :	populateDocDetails
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to populate doc details
	 ** Creation Date	:	13/11/2014
	 ** Arguments		:	View v
	 ** Return Type     :	void
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public void populateAppmntDetails(ArrayList<Appointment> appmntList) 
	{
		try
		{										
			// 1. pass context and data to the custom adapter
		    valueAdapter = new CustomAdapterAppDocListItems(this, generateData(appmntList));
	        // 2. Get ListView from activity_main.xml
	        ListView listView = (ListView) findViewById(R.id.list);	 
	        // 3. setListAdapter
	        listView.setAdapter(valueAdapter);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
    private ArrayList<RowItembookedAppmnt> generateData(ArrayList<Appointment> listDoctor)
    {

       ArrayList<RowItembookedAppmnt> items = new ArrayList<RowItembookedAppmnt>();
       for(int iCount=0;iCount<listDoctor.size();iCount++)
       {
    	   
           items.add(new RowItembookedAppmnt(strDocId,listDoctor.get(iCount).getAppDate(),listDoctor.get(iCount).getBookFromTime(),listDoctor.get(iCount).getPatName(),listDoctor.get(iCount).getPatContact()));
       }
       
        return items;
    }
    
    /*******************************************************************************
	 ** Function Name   :	setCurrentDateOnView
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to set date
	 ** Creation Date	:	07/11/2014
	 ** Arguments		:	View v
	 ** Return Type     :	void
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
// display current date
	public void setCurrentDateOnView() {
 
		TextView tvDisplayDate = (TextView) findViewById(R.id.tvDateValueDocAppmnt);
 
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
 
		//
		int iDayOfWeek=c.get(Calendar.DAY_OF_WEEK);		
		// set current date into textview
		tvDisplayDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(day).append("-").append(month + 1).append("-")
			.append(year).append(" "));
		
		strSelectedDate=tvDisplayDate.getText().toString();
	}
    
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		}
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		

		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			year = arg1;
			month = arg2;
			day = arg3;
 
			TextView tvDate=(TextView)findViewById(R.id.tvDateValueDocAppmnt);
			// set selected date into textview
			tvDate.setText(new StringBuilder().append(day)
			   .append("-").append(month + 1).append("-").append(year)
			   .append(" "));

			
			Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,day);
    		int iDayOfWeek=calendar.get(Calendar.DAY_OF_WEEK);          
			
    		strSelectedDate=tvDate.getText().toString();
			
    		
    		// WebServer Request URL
    	    String serverURL;
    		try 
    		{
    			serverURL = LeverageConstants.URL+"getAllAppmntDetails/"+strDocId+"/"+URLEncoder.encode(strSelectedDate, "UTF-8");
    			new GetAppByDocId().execute(serverURL);

    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
			
		}
	};
	
	
	/*******************************************************************************
	 ** Function Name   :	openTabWindow
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to open tab window
	 ** Creation Date	:	04/11/2014
	 ** Arguments		:	View v
	 ** Return Type     :	void
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public void openDateEditor(View v) 
	{
		try 
		{
			showDialog(DATE_DIALOG_ID);
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
