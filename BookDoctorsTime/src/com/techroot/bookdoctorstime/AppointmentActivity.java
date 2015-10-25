package com.techroot.bookdoctorstime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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
import com.techroot.bookdoctorstime.appointmentManagement.model.AppSlot;
import com.techroot.bookdoctorstime.appointmentManagement.model.Appointment;
import com.techroot.bookdoctorstime.constants.LeverageConstants;
import com.techroot.bookdoctorstime.doctorManagement.model.Doctor;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class AppointmentActivity extends ActionBarActivity {

	private int year;
	private int month;
	private int day;
	private DatePicker dpResult;
	static final int DATE_DIALOG_ID = 999;
	
	private String strClinicTiming;
	
	private String strDocId;
	private String strDaySelected;
	private String strDateSelected;
	private boolean bDoclag;

	private String strAppmntMode;
	private String strDocClArea;

	private ArrayList<Doctor> serverDoctorsDetails;
	
	private ArrayList<Doctor> serverTimingDetails;
	
	private ArrayList<Appointment> serverAppmntList;
	
	// adapter
    private CustomAdapterAppintmentItems valueAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appointment);

	
		// call date set value
		setCurrentDateOnView();
		
		serverDoctorsDetails=new ArrayList<Doctor>();
		serverTimingDetails=new ArrayList<Doctor>();
		serverAppmntList=new ArrayList<Appointment>();
		//populate doc details
		
		strDocId=getIntent().getExtras().getString("docId");
		strAppmntMode=getIntent().getExtras().getString("strAppmntMode");
		bDoclag=getIntent().getExtras().getBoolean("bDoc");
		strDocClArea=getIntent().getExtras().getString("docArea");

		if(null==strAppmntMode)
		{
			strAppmntMode="auto";
		}
		// WebServer Request URL
	    String serverURL = LeverageConstants.URL+"getDocByIdDetails/"+strDocId+"/"+strDaySelected;
		// Use AsyncTask execute Method To Prevent ANR Problem
		new GetDocByDocIdDetails().execute(serverURL);
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.appointment, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_appointment,
					container, false);
			return rootView;
		}
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
 
			TextView tvDate=(TextView)findViewById(R.id.tvDateValue);
			// set selected date into textview
			tvDate.setText(new StringBuilder().append(day)
			   .append("-").append(month + 1).append("-").append(year)
			   .append(" "));

			
			Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,day);
    		int iDayOfWeek=calendar.get(Calendar.DAY_OF_WEEK);          
			setDayOfWeek(iDayOfWeek);
			
			strDateSelected=tvDate.getText().toString();
			
			// WebServer Request URL
		    String serverURL = LeverageConstants.URL+"getDocByTiming/"+strDaySelected+"/"+strDocId;
			// Use AsyncTask execute Method To Prevent ANR Problem
			new GetDoctimingbyDay().execute(serverURL);
			
			
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
 
		TextView tvDisplayDate = (TextView) findViewById(R.id.tvDateValue);
 
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
 
		//
		int iDayOfWeek=c.get(Calendar.DAY_OF_WEEK);
		
		setDayOfWeek(iDayOfWeek);

		// set current date into textview
		tvDisplayDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(day).append("-").append(month + 1).append("-")
			.append(year).append(" "));
 
		strDateSelected=tvDisplayDate.getText().toString().trim();

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
    private ArrayList<RowItemAppointmentItems> generateData(ArrayList<AppSlot> listAppSlot)
    {

       ArrayList<RowItemAppointmentItems> items = new ArrayList<RowItemAppointmentItems>();
       for(int iCount=0;iCount<listAppSlot.size();iCount++)
       {
    	   
           items.add(new RowItemAppointmentItems(listAppSlot.get(iCount).getAppSlot1(),listAppSlot.get(iCount).getAppSlot2(),listAppSlot.get(iCount).getAppSlot3(),listAppSlot.get(iCount).getAppSlot4(),strAppmntMode));
       }
       
        return items;
    }
	
    
    /*******************************************************************************
	 ** Function Name   :	onSearchClick
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to open tab window
	 ** Creation Date	:	06/11/2014
	 ** Arguments		:	View v
	 ** Return Type     :	void
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public void onNextDayClick(View v) 
	{
		try 
		{
			// get text of current date
			TextView tvDateValue=(TextView)findViewById(R.id.tvDateValue);
			
			String strDate=tvDateValue.getText().toString();
			
			String[] dayList=strDate.split("-");
			
			int day1=Integer.parseInt(dayList[0].trim());
			int month1=Integer.parseInt(dayList[1].trim());
			int year1=Integer.parseInt(dayList[2].trim());		
		
			Calendar c= GregorianCalendar.getInstance(Locale.ENGLISH);
		
			c.setTime(new Date());
			
			c.set(Calendar.YEAR, year1);
	        c.set(Calendar.MONTH, month1);
	        c.set(Calendar.DAY_OF_MONTH, day1);	   
	        
	        c.add(Calendar.DATE, 1);
			
	        	      
			day1=c.get(Calendar.DAY_OF_MONTH);
			month1=c.get(Calendar.MONTH);
			year1=c.get(Calendar.YEAR);

			
			TextView tvDate=(TextView)findViewById(R.id.tvDateValue);
			// set selected date into textview
			tvDate.setText(new StringBuilder().append(day1)
			   .append("-").append(month1).append("-").append(year1)
			   .append(""));
			
	        
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/*********************************************************************************
	    **********************************************************************************
	    ** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
	    ** Author :         	Ganesh Mule
	    ** Created on :     	28-09-2014
	    ** Dept:            	Android Based Mobile App Development
	    ** Class:          		GetDocByDocId
	    ** Description:     	This is inner class used to call suitable http method from server and get its result
	    *						Here we call method to get category details from server
	    ***********************************************************************************
	    ***********************************************************************************/  
	private class GetDocByDocId extends AsyncTask<String, Void, Void> 
	{
		RequestParams params = new RequestParams();
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		String result;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(AppointmentActivity.this);
		
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.
			// Start Progress Dialog (Message)
			Dialog.setMessage("Please wait..");
			Dialog.show();
			Dialog.setIndeterminate(false);
			Dialog.setCancelable(false);
			
			params.add("strDocId", strDocId);
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
						
				serverDoctorsDetails.clear();
				if(!("error").equals(result.trim()))
				{
					// Converting gson to List of given elemnts
					Gson gson = new Gson(); 
					ArrayList<Doctor> li=gson.fromJson(result, new TypeToken<List<Doctor>>(){}.getType());									
					serverDoctorsDetails=li;
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
							
							if(!serverDoctorsDetails.isEmpty())
							{		
							    TextView tvStrDate=(TextView)findViewById(R.id.tvDateValue);
								strClinicTiming=serverDoctorsDetails.get(0).getDocTiming();

								 // WebServer Request URL
							    String serverURL = LeverageConstants.URL+"getAppointmentDetails/"+tvStrDate.getText().toString().trim()+"/"+strDocId;
								// Use AsyncTask execute Method To Prevent ANR Problem
								new GetAppmntDetails().execute(serverURL);															
							}	
							else {
								if(bDoclag)
								{
								    TextView tvStrDate=(TextView)findViewById(R.id.tvDateValue);
									// WebServer Request URL
								    String serverURL = LeverageConstants.URL+"getAppointmentDetails/"+tvStrDate.getText().toString().trim()+"/"+strDocId;
									// Use AsyncTask execute Method To Prevent ANR Problem
									new GetAppmntDetails().execute(serverURL);	
									
//									TextView tvDocClinicArea=(TextView)findViewById(R.id.tvDocClinicArea);
//	
//									tvDocClinicArea.setText("Docotrs Clinic");
//									setTimingSlot("10am to 7pm");
								}
								else
								{
						            Toast.makeText(AppointmentActivity.this, "No slot for selected hospital and doctor on selected date ", Toast.LENGTH_SHORT).show();	
								}
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
	public void populateDocDetails(Doctor doctorListItem) 
	{
		try
		{
			TextView tvDocName=(TextView)findViewById(R.id.tvDocName);
			TextView tvDocSpeciality=(TextView)findViewById(R.id.tvDocSpc);
 			TextView tvDocClinicArea=(TextView)findViewById(R.id.tvDocClinicArea);
			
			tvDocName.setText(doctorListItem.getDocFirstName()+" "+doctorListItem.getDocLastName());
			tvDocSpeciality.setText(doctorListItem.getDocSpecilization());
			if(null==doctorListItem.getDocClinicName())
			{
				tvDocClinicArea.setText(strDocClArea);
			}
			else
			{
				tvDocClinicArea.setText(doctorListItem.getDocClinicName()+" "+doctorListItem.getCity());	

			}
				// calculating the slot for timing
				// WebServer Request URL
			String serverURL = LeverageConstants.URL+"getDocById/"+strDocId+"/"+strDaySelected;
				// Use AsyncTask execute Method To Prevent ANR Problem
		   new GetDocByDocId().execute(serverURL);	
			
				
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void setDayOfWeek(int iDayOfWeek) 
	{
		if(Calendar.MONDAY==iDayOfWeek)
		{
			strDaySelected="Monday";
		}
		if(Calendar.TUESDAY==iDayOfWeek)
		{
			strDaySelected="Tuesday";

		}
		if(Calendar.WEDNESDAY==iDayOfWeek)
		{
			strDaySelected="Wednesday";
		}
		if(Calendar.THURSDAY==iDayOfWeek)
		{
			strDaySelected="Thursday";
		}
		if(Calendar.FRIDAY==iDayOfWeek)
		{
			strDaySelected="Friday";
		}
		if(Calendar.SATURDAY==iDayOfWeek)
		{
			strDaySelected="Saturday";
		}
		if(Calendar.SUNDAY==iDayOfWeek)
		{
			strDaySelected="Sunday";
		}
	}
	
	
	public void setTimingSlot(String strTiming)
	{
		try {
			ArrayList<AppSlot> listAppTime=new ArrayList<AppSlot>();

			String[] strtwotimeSlot=strTiming.split("&");
			if(strtwotimeSlot.length==2)
			{
				for(int i1=0;i1<=1;i1++)
				{
					if(i1==0)
					{
						strTiming=strtwotimeSlot[0];
					}
					else
					{
						strTiming=strtwotimeSlot[1];
					}
					
					strTiming=strTiming.trim();
					strTiming=strTiming.replaceAll("\\s+","");
					String[] strtime = null;
					String[] strTime1 = null;
					String strTime2[] = null;
					if(strTiming.contains("-"))
					{
						 strtime=strTiming.split("-");				 
					}
					if(strTiming.contains("to"))
					{
						 strtime=strTiming.split("to");				 
					}

					String strFromTime=strtime[0];
					String strtoTime=strtime[1];

					
					if(strFromTime.length()==3)
					{
						strFromTime="0"+strFromTime;
					}
					
					if(strtoTime.length()==3)
					{
						strtoTime="0"+strtoTime;
					}
					
					String strFromTime1=strFromTime.substring(0, 2);	
					
					String strFrAmPm=strFromTime.substring(2,4);
					
					String strtoTime1=strtoTime.substring(0, 2);			
					
					String strToAmPm=strtoTime.substring(2,4);

					
					SimpleDateFormat sdf = new SimpleDateFormat("hh");
					
					Date date1 = sdf.parse(strFromTime1);
					Date date2 = sdf.parse(strtoTime1);
								
					
					if(strFrAmPm.equalsIgnoreCase(strToAmPm))
					{
						if(("pm").equalsIgnoreCase(strToAmPm))
						{
							strtoTime=String.valueOf(12+Double.parseDouble(strtoTime1));

						}
						else
						{
							strtoTime=strtoTime1;
						}
						if (("pm").equals(strFrAmPm)) 
						{
							strFromTime1=String.valueOf(12+Double.parseDouble(strFromTime1));
						}
						
						
						
					}
					else
					{
						if(("PM").equalsIgnoreCase(strToAmPm))
						{
							strtoTime=String.valueOf(12+Double.parseDouble(strtoTime1));
						}
					}
					
					
					final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
					
					double iMin=15 * ONE_MINUTE_IN_MILLIS;
					
				    double dDivide=Double.parseDouble(strtoTime)-Double.parseDouble(strFromTime1);
				    
				    if(dDivide<0)
				    {
				    	dDivide=(-dDivide);
				    }
				    
			    	double dfrom=Double.parseDouble((strFromTime1));
			    	double dTott=Double.parseDouble((strFromTime1));

			    	Date d1;
			    	d1=sdf.parse(String.valueOf(dTott));
			    	Calendar cal = Calendar.getInstance();
			    	cal.setTime(d1);
			    	int hour=0;
			    	int minute =0;
			    	String timeMark;
				    for(int i=1;i<=dDivide;i++)
				    {
						AppSlot objAppSlot=new AppSlot();

				    	cal.add(Calendar.MINUTE, 15);
				    	hour = cal.get(Calendar.HOUR);
				    	minute = cal.get(Calendar.MINUTE);
				    	
				    	if(cal.get(Calendar.AM_PM)==0)
				    		timeMark="AM";
				        else
				        	timeMark="PM";
				    	
				    	objAppSlot.setAppSlot1(String.valueOf( hour)+":"+String.valueOf(minute)+timeMark);
				    	
				    	cal.add(Calendar.MINUTE, 15);
				    	hour= cal.get(Calendar.HOUR);
				    	minute = cal.get(Calendar.MINUTE);
				    	

				    	if(cal.get(Calendar.AM_PM)==0)
				    		timeMark="AM";
				        else
				        	timeMark="PM";
				    	
				    	objAppSlot.setAppSlot2(String.valueOf( hour)+":"+String.valueOf(minute)+timeMark);
				    	
				    	cal.add(Calendar.MINUTE, 15);
				    	hour= cal.get(Calendar.HOUR);
				    	minute = cal.get(Calendar.MINUTE);
				    	if(cal.get(Calendar.AM_PM)==0)
				    		timeMark="AM";
				        else
				        	timeMark="PM";
				    	objAppSlot.setAppSlot3(String.valueOf( hour)+":"+String.valueOf(minute)+timeMark);
				    	
				    	cal.add(Calendar.MINUTE, 15);
				    	hour= cal.get(Calendar.HOUR);
				    	minute = cal.get(Calendar.MINUTE);
				    	
				    	if(cal.get(Calendar.AM_PM)==0)
				    		timeMark="AM";
				        else
				        	timeMark="PM";
				    	objAppSlot.setAppSlot4(String.valueOf( hour)+":"+String.valueOf(minute)+timeMark);

						listAppTime.add(objAppSlot);

				    }
				}
			}
			else
			{
				strTiming=strTiming.trim();
				strTiming=strTiming.replaceAll("\\s+","");
				String[] strtime = null;
				String[] strTime1 = null;
				String strTime2[] = null;
				if(strTiming.contains("-"))
				{
					 strtime=strTiming.split("-");				 
				}
				if(strTiming.contains("to"))
				{
					 strtime=strTiming.split("to");				 
				}

				String strFromTime=strtime[0];
				String strtoTime=strtime[1];

				
				if(strFromTime.length()==3)
				{
					strFromTime="0"+strFromTime;
				}
				
				if(strtoTime.length()==3)
				{
					strtoTime="0"+strtoTime;
				}
				
				String strFromTime1=strFromTime.substring(0, 2);	
				
				String strFrAmPm=strFromTime.substring(2,4);
				
				String strtoTime1=strtoTime.substring(0, 2);			
				
				String strToAmPm=strtoTime.substring(2,4);

				
				SimpleDateFormat sdf = new SimpleDateFormat("hh");
				
				Date date1 = sdf.parse(strFromTime1);
				Date date2 = sdf.parse(strtoTime1);
							
				
				if(strFrAmPm.equalsIgnoreCase(strToAmPm))
				{
					if(("pm").equalsIgnoreCase(strToAmPm))
					{
						strtoTime=String.valueOf(12+Double.parseDouble(strtoTime1));

					}
					else
					{
						strtoTime=strtoTime1;
					}
					if (("pm").equals(strFrAmPm)) 
					{
						strFromTime1=String.valueOf(12+Double.parseDouble(strFromTime1));
					}				
				}
				else
				{
					if(("PM").equalsIgnoreCase(strToAmPm))
					{
						strtoTime=String.valueOf(12+Double.parseDouble(strtoTime1));
					}
				}
				
				
				final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
				
				double iMin=15 * ONE_MINUTE_IN_MILLIS;
				
			    double dDivide=Double.parseDouble(strtoTime)-Double.parseDouble(strFromTime1);
			    
			    if(dDivide<0)
			    {
			    	dDivide=(-dDivide);
			    }
			    
		    	double dfrom=Double.parseDouble((strFromTime1));
		    	double dTott=Double.parseDouble((strFromTime1));

		    	Date d1;
		    	d1=sdf.parse(String.valueOf(dTott));
		    	Calendar cal = Calendar.getInstance();
		    	cal.setTime(d1);
		    	int hour=0;
		    	int minute =0;
		    	String timeMark;
			    for(int i=1;i<=dDivide;i++)
			    {
					AppSlot objAppSlot=new AppSlot();

			    	cal.add(Calendar.MINUTE, 15);
			    	hour = cal.get(Calendar.HOUR);
			    	minute = cal.get(Calendar.MINUTE);
			    	
			    	if(cal.get(Calendar.AM_PM)==0)
			    		timeMark="AM";
			        else
			        	timeMark="PM";
			    	
			    	objAppSlot.setAppSlot1(String.valueOf( hour)+":"+String.valueOf(minute)+timeMark);
			    	
			    	cal.add(Calendar.MINUTE, 15);
			    	hour= cal.get(Calendar.HOUR);
			    	minute = cal.get(Calendar.MINUTE);
			    	

			    	if(cal.get(Calendar.AM_PM)==0)
			    		timeMark="AM";
			        else
			        	timeMark="PM";
			    	
			    	objAppSlot.setAppSlot2(String.valueOf( hour)+":"+String.valueOf(minute)+timeMark);
			    	
			    	cal.add(Calendar.MINUTE, 15);
			    	hour= cal.get(Calendar.HOUR);
			    	minute = cal.get(Calendar.MINUTE);
			    	if(cal.get(Calendar.AM_PM)==0)
			    		timeMark="AM";
			        else
			        	timeMark="PM";
			    	objAppSlot.setAppSlot3(String.valueOf( hour)+":"+String.valueOf(minute)+timeMark);
			    	
			    	cal.add(Calendar.MINUTE, 15);
			    	hour= cal.get(Calendar.HOUR);
			    	minute = cal.get(Calendar.MINUTE);
			    	
			    	if(cal.get(Calendar.AM_PM)==0)
			    		timeMark="AM";
			        else
			        	timeMark="PM";
			    	objAppSlot.setAppSlot4(String.valueOf( hour)+":"+String.valueOf(minute)+timeMark);

					listAppTime.add(objAppSlot);

			    }
				
			}
			
			
		    TextView tvStrDate=(TextView)findViewById(R.id.tvDateValue);
		    		    	 
			// 1. pass context and data to the custom adapter
		    valueAdapter = new CustomAdapterAppintmentItems(this, generateData(listAppTime),strDaySelected,tvStrDate.getText().toString(),strDocId,serverAppmntList);
	        // 2. Get ListView from activity_main.xml
	        ListView listView = (ListView) findViewById(R.id.list);
	 
	        // 3. setListAdapter
	        listView.setAdapter(valueAdapter);

			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/*********************************************************************************
	    **********************************************************************************
	    ** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
	    ** Author :         	Ganesh Mule
	    ** Created on :     	28-09-2014
	    ** Dept:            	Android Based Mobile App Development
	    ** Class:          		GetDocByDocId
	    ** Description:     	This is inner class used to call suitable http method from server and get its result
	    *						Here we call method to get category details from server
	    ***********************************************************************************
	    ***********************************************************************************/  
	private class GetDoctimingbyDay extends AsyncTask<String, Void, Void> 
	{
		RequestParams params = new RequestParams();
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		String result;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(AppointmentActivity.this);
		
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.
			// Start Progress Dialog (Message)
			Dialog.setMessage("Please wait..");
			Dialog.show();
			Dialog.setIndeterminate(false);
			Dialog.setCancelable(false);
			
			params.add("strDay", strDaySelected);
			params.add("strDocId", strDocId);

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
						
				serverTimingDetails.clear();
				if(!("error").equals(result.trim()))
				{
					// Converting gson to List of given elemnts
					Gson gson = new Gson(); 
					ArrayList<Doctor> li=gson.fromJson(result, new TypeToken<List<Doctor>>(){}.getType());									
					serverTimingDetails=li;
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
							
							if(!serverTimingDetails.isEmpty())
							{																
								TextView tvStrDate=(TextView)findViewById(R.id.tvDateValue);
								strClinicTiming=serverTimingDetails.get(0).getDocTiming();
								strDateSelected=tvStrDate.getText().toString().trim();
								 // WebServer Request URL
							    String serverURL = LeverageConstants.URL+"getAppointmentDetails/"+tvStrDate.getText().toString().trim()+"/"+strDocId;
								// Use AsyncTask execute Method To Prevent ANR Problem
								new GetAppmntDetails().execute(serverURL);		
							}
							else
							{
								valueAdapter.clear();
								valueAdapter.notifyDataSetChanged();
					            Toast.makeText(AppointmentActivity.this, "No slot for selected hospital and doctor on selected date ", Toast.LENGTH_SHORT).show();	

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
			catch (Exception e) {
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
	
	
	/*********************************************************************************
	    **********************************************************************************
	    ** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
	    ** Author :         	Ganesh Mule
	    ** Created on :     	28-09-2014
	    ** Dept:            	Android Based Mobile App Development
	    ** Class:          		GetDocByDocId
	    ** Description:     	This is inner class used to call suitable http method from server and get its result
	    *						Here we call method to get category details from server
	    ***********************************************************************************
	    ***********************************************************************************/  
	public class GetDocByDocIdDetails extends AsyncTask<String, Void, Void> 
	{
		RequestParams params = new RequestParams();
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		String result;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(AppointmentActivity.this);
		
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.
			// Start Progress Dialog (Message)
			Dialog.setMessage("Please wait..");
			Dialog.show();
			Dialog.setIndeterminate(false);
			Dialog.setCancelable(false);
			
			params.add("strDay", strDaySelected);
			params.add("strDocId", strDocId);

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
						
				serverDoctorsDetails.clear();
				if(!("error").equals(result.trim()))
				{
					// Converting gson to List of given elemnts
					Gson gson = new Gson(); 
					ArrayList<Doctor> li=gson.fromJson(result, new TypeToken<List<Doctor>>(){}.getType());									
					serverDoctorsDetails=li;
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
							
							if(!serverDoctorsDetails.isEmpty())
							{			
								populateDocDetails(serverDoctorsDetails.get(0));
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
	 ** Function Name   :	onContinueClick
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to open new activity for booking appointment
	 ** Creation Date	:	17/11/2014
	 ** Arguments		:	View v
	 ** Return Type     :	void
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public void onContinueClick(View v) 
	{
		try 
		{
			Intent intent=new Intent(AppointmentActivity.this,PatientDetailsActivity.class);							
			startActivity(intent);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*********************************************************************************
	    **********************************************************************************
	    ** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
	    ** Author :         	Ganesh Mule
	    ** Created on :     	28-09-2014
	    ** Dept:            	Android Based Mobile App Development
	    ** Class:          		GetAppmntDetails
	    ** Description:     	This is inner class used to call suitable http method from server and get its result
	    *						Here we call method to get category details from server
	    ***********************************************************************************
	    ***********************************************************************************/  
	public class GetAppmntDetails extends AsyncTask<String, Void, Void> 
	{
		RequestParams params = new RequestParams();
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		String result;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(AppointmentActivity.this);
		
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.
			// Start Progress Dialog (Message)
			Dialog.setMessage("Please wait..");
			Dialog.show();
			Dialog.setIndeterminate(false);
			Dialog.setCancelable(false);
			
			params.add("strDate",strDateSelected);
			params.add("strDocId", strDocId);

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
						
				serverAppmntList.clear();
				if(!("error").equals(result.trim()))
				{
					// Converting gson to List of given elemnts
					Gson gson = new Gson(); 
					ArrayList<Appointment> li=gson.fromJson(result, new TypeToken<List<Appointment>>(){}.getType());									
					serverAppmntList=li;
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
						if(bDoclag)
						{

							// WebServer Request URL
						    String serverURL = LeverageConstants.URL+"getDocDetailsForOwnClinic/"+strDocId;
							// Use AsyncTask execute Method To Prevent ANR Problem
							new GetDOcDetailsByItsOwnClinic().execute(serverURL);
							
						}
						else
						{
							setTimingSlot(strClinicTiming);											
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
	
	
	/*********************************************************************************
	    **********************************************************************************
	    ** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
	    ** Author :         	Ganesh Mule
	    ** Created on :     	28-09-2014
	    ** Dept:            	Android Based Mobile App Development
	    ** Class:          		GetDOcDetailsByItsOwnClinic
	    ** Description:     	This is inner class used to call suitable http method from server and get its result
	    *						Here we call method to get category details from server
	    ***********************************************************************************
	    ***********************************************************************************/  
	public class GetDOcDetailsByItsOwnClinic extends AsyncTask<String, Void, Void> 
	{
		RequestParams params = new RequestParams();
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		String result;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(AppointmentActivity.this);
		
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.
			// Start Progress Dialog (Message)
			Dialog.setMessage("Please wait..");
			Dialog.show();
			Dialog.setIndeterminate(false);
			Dialog.setCancelable(false);
			
			params.add("strDocId", strDocId);

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
						
				serverDoctorsDetails.clear();
				if(!("error").equals(result.trim()))
				{
					// Converting gson to List of given elemnts
					Gson gson = new Gson(); 
					ArrayList<Doctor> li=gson.fromJson(result, new TypeToken<List<Doctor>>(){}.getType());									
					serverDoctorsDetails=li;
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
							
							if(!serverDoctorsDetails.isEmpty())
							{
								TextView tvDocClinicArea=(TextView)findViewById(R.id.tvDocClinicArea);
								if(null==serverDoctorsDetails.get(0).getDocOwnClinicArea()|| serverDoctorsDetails.get(0).getDocOwnClinicArea().isEmpty()|| serverDoctorsDetails.get(0).getDocOwnClinicArea().equalsIgnoreCase("na"))
								{
									tvDocClinicArea.setText("Docotrs Clinic");
								}
								else
								{
									tvDocClinicArea.setText(serverDoctorsDetails.get(0).getDocOwnClinicArea());

								}
								if(null==serverDoctorsDetails.get(0).getDocOwnClinicTiming()|| serverDoctorsDetails.get(0).getDocOwnClinicTiming().isEmpty()||serverDoctorsDetails.get(0).getDocOwnClinicTiming().equalsIgnoreCase("na"))
								{
									setTimingSlot("9am to 9pm");

								}
								else
								{
									setTimingSlot(serverDoctorsDetails.get(0).getDocOwnClinicTiming());


								}
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
	
}
