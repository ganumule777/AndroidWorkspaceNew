package com.techroot.bookdoctorstime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.techroot.bookdoctorstime.AppointmentActivity.GetDocByDocIdDetails;
import com.techroot.bookdoctorstime.constants.LeverageConstants;
import com.techroot.bookdoctorstime.doctorManagement.model.Doctor;
import com.techroot.bookdoctorstime.exception.LeverageExceptions;
import com.techroot.bookdoctorstime.exception.LeverageNonLethalException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class PatientDetailsActivity extends ActionBarActivity {

	private String strDaySelected;
	private String strtimeSlot;
	private String strDate;
	private String strDocId;
	private ArrayList<Doctor> serverDoctorsDetails;
	// post details
	private String hospCity;
	private String hospArea;
	private String docSpec;
	private String hospName;
	private String fromTime;
	private String toTime;

	private String appDate;
	private String patFirstName;
	private String patAge;
	private String patGender;
	private String patSTreet;
	private String patFullAddr;
	private String patContact;
	private String patProb;
	private String appDocId;
	private String patCity;
	private String patLastName;
	private String docName7;
	private String patEmailId;
	
	private String strAppmntMode;

	Doctor doctorListItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_details);

		serverDoctorsDetails=new ArrayList<Doctor>();
		
		doctorListItem=new Doctor();
		
		//get time details		
		strDaySelected=getIntent().getExtras().getString("strDay").toString();
		strtimeSlot=getIntent().getExtras().getString("appmnttimeSlot").toString();
		strDate=getIntent().getExtras().getString("strDate").toString();
		strAppmntMode=getIntent().getExtras().getString("strAppmntMode").toString();
		strDocId=getIntent().getExtras().getString("strDocId");
		
		TextView tvTitl=(TextView)findViewById(R.id.tvtitlePatDetails);
		if(("manual").equals(strAppmntMode))
		{
			tvTitl.setVisibility(View.GONE);
		}
		
		// WebServer Request URL
	    String serverURL = LeverageConstants.URL+"getDocByIdDetails/"+strDocId+"/"+strDaySelected;
		// Use AsyncTask execute Method To Prevent ANR Problem
		new GetDocByDocIdDetails().execute(serverURL);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_details, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_patient_details,
					container, false);
			return rootView;
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
	private class GetDocByDocIdDetails extends AsyncTask<String, Void, Void> 
	{
		RequestParams params = new RequestParams();
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		String result;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(PatientDetailsActivity.this);
		
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
								doctorListItem=serverDoctorsDetails.get(0);
								populateDocDetails();
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
	public void populateDocDetails() 
	{
		try
		{
			TextView tvDocName=(TextView)findViewById(R.id.tvDocName);
			TextView tvDocSpeciality=(TextView)findViewById(R.id.tvDocSpc1);
			TextView tvDocClinicArea=(TextView)findViewById(R.id.tvDocClinicArea);
			
			TextView tvDay=(TextView)findViewById(R.id.tvAppDay);
			TextView tvtimeSlot=(TextView)findViewById(R.id.tvAppTime);
						
			tvDay.setText(strDaySelected.substring(0,3)+","+strDate);
			tvtimeSlot.setText("Time- "+strtimeSlot);
			
			tvDocName.setText(doctorListItem.getDocFirstName()+" "+doctorListItem.getDocLastName());
			tvDocSpeciality.setText(doctorListItem.getDocSpecilization());
			tvDocClinicArea.setText(doctorListItem.getDocClinicName()+" "+doctorListItem.getCity());
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	/*******************************************************************************
	 ** Function Name   :	onBookAppmntClick
	 ** Created By      :	Ganesh Mule
	 ** Description		:	This function is used to book appointment
	 ** Creation Date	:	21/11/2014
	 ** Arguments		:	View v
	 ** Return Type     :	void
	 * @throws LeverageNonLethalException 
	 *******************************************************************************/
	public void onBookAppmntClick(View v) 
	{
		try 
		{
			 EditText etFName=(EditText)findViewById(R.id.etPatientName);	
			 EditText etMblNo=(EditText)findViewById(R.id.etPatientmblNo);

			 if(etFName.getText().toString().isEmpty())
			 {
		    		throw(new LeverageNonLethalException(RESULT_OK,"Enter Name"));
			 }
			 
			 if(etMblNo.getText().toString().isEmpty())
			 {
		    		throw(new LeverageNonLethalException(RESULT_OK,"Enter Mobile No."));
			 }
			 
			 patFirstName=etFName.getText().toString();
					 
			 EditText etEmailId=(EditText)findViewById(R.id.etPatientEmailId);
			 if(etEmailId.getText().toString().isEmpty())
			 {
				 patEmailId="";
			 }
			 else 
			 {
				 patEmailId=etEmailId.getText().toString();
			 }
			 
			 TextView tvDocName=(TextView)findViewById(R.id.tvDocName);
			 docName7=tvDocName.getText().toString();
			 
			 patContact=etMblNo.getText().toString();
 
			 EditText etAddrs=(EditText)findViewById(R.id.etPatAdd);
			 
			 if(etAddrs.getText().toString().isEmpty())
			 {
				 patFullAddr="";
			 }
			 else 
			 {
				 patFullAddr=etAddrs.getText().toString();
			 }
			  
			 TextView tvSpec=(TextView)findViewById(R.id.tvDocSpc1);
			 docSpec=tvSpec.getText().toString();
			 
			 appDocId=strDocId;
			 hospArea=doctorListItem.getAddress();
			 hospCity=doctorListItem.getCity();
			 hospName=doctorListItem.getDocClinicName();
			 fromTime=strtimeSlot;
			 appDate=strDate;
			 toTime=strtimeSlot;
			 
			 // WebServer Request URL
			  String serverURL = LeverageConstants.URL+"addAppntmnt/";
			  // Use AsyncTask execute Method To Prevent ANR Problem
			  new AddAppmntDetails().execute(serverURL);
		} 
		 catch (LeverageNonLethalException e)
		 {
			e.printStackTrace();
			Toast.makeText(this,e.getExceptionMsg(),Toast.LENGTH_LONG).show();  
	     }
		  catch (Exception e) 
		  {
			e.printStackTrace();
		  }
	}
	
	
	 
    /*********************************************************************************
     **********************************************************************************
     ** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
     ** Author :         	Ganesh Mule
     ** Created on :     	28-09-2014
     ** Dept:            	Android Based Mobile App Development
     ** Class:          	AddAppmntDetails
     ** Description:     	This is inner class used to call suitable http method from server and get its result
     *						This is used to add post on server
     ***********************************************************************************
     ***********************************************************************************/
    private class AddAppmntDetails extends AsyncTask<String, Void, Void> {

		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(PatientDetailsActivity.this);
		String data = "";
		JSONObject json;
	
		int sizeData = 0;
	
		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			// Start Progress Dialog (Message)

			Dialog.setMessage("Please wait..");
			Dialog.show();

			try {
				// Add about app dialog which showing information about app       				
				json = new JSONObject();
	
				if(null==hospCity)
				{
					json.put("hospCity","Doctors Clinic");
				}
				else
				{
					json.put("hospCity",hospCity);

				}
				
				if(null==hospArea)
				{
					json.put("hospArea", " ");
				}
				else
				{
					json.put("hospArea", hospArea);

				}
				
				if(null==docSpec)
				{
					json.put("docSplecity", " ");				
				}
				else
				{
					json.put("docSplecity",docSpec);

				}
				
				if(null==hospName)
				{
					json.put("hospName", " ");				
				}
				else
				{
					json.put("hospName", hospName);				

				}
				
			
				json.put("fromTime", fromTime);		
				json.put("toTime", toTime);
				json.put("appDate", appDate);
				json.put("patFirstName", patFirstName);
				json.put("patAge","");
				json.put("patCity","");
				json.put("patEmailId",patEmailId);
				json.put("docName",docName7);
				
				json.put("patGender", "");				
				json.put("patStreet", "");		
				json.put("patFullAddr", patFullAddr);
				json.put("patContact", patContact);
				json.put("patProb", "");
				json.put("appDocId",appDocId);
				json.put("patLastName","");
				json.put("appmntMode",strAppmntMode);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		protected Void doInBackground(String... arg0) {
			/************ Make Post Call To Web Server ***********/

			// HttpGet request = new HttpGet(arg0[0]);

			HttpPost request = new HttpPost(arg0[0]);		
			HttpClient client = new DefaultHttpClient();
			HttpResponse httpResponse;
			String response = "";
			int responseCode = 0;
			String message = "";
			try {
				StringEntity input = new StringEntity(json.toString());
				input.setContentType("application/json");
				request.setEntity(input);
				
				httpResponse = client.execute(request);
				responseCode = httpResponse.getStatusLine().getStatusCode();
				message = httpResponse.getStatusLine().getReasonPhrase();
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					response = convertStreamToString(instream);
					instream.close();
				}
			} catch (Exception e) {
			}
			
			
			Content = response;
			return null;
		}

		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.

			// Close progress dialog
			Dialog.dismiss();
			if(!(null==Content))
			{
				Content=Content.trim();
				if (!("error").equals(Content.trim())) 
				{		
					
					if(("manual").equals(strAppmntMode))
					{
						
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PatientDetailsActivity.this);	
						alertDialogBuilder.setTitle("Successfull Meassage");
						alertDialogBuilder.setMessage("Your Appointment requested has been sent successfully. Doctors need to confirm it. You will receive a confirmation SMS");
				 		// setup a dialog window
				 		alertDialogBuilder
				 				.setCancelable(false)
				 				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				 					public void onClick(DialogInterface dialog, int id) {
				 						////Call Verify method				 						
				 						try 
				 						{
				 							finish();
				 							
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} 
				 					}
				 				})
							.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int id) 
										{
										
											finish();
											dialog.cancel(); 	 								
										}
									});
				  
					 		// create an alert dialog
					 		AlertDialog alert = alertDialogBuilder.create();
					 		alert.show();
						
					}
					else
					{
						
						sendSMSMessage();
						
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PatientDetailsActivity.this);	
						alertDialogBuilder.setTitle("Successfull Meassage");
						alertDialogBuilder.setMessage("Appointment booked successfully");
				 		// setup a dialog window
				 		alertDialogBuilder
				 				.setCancelable(false)
				 				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				 					public void onClick(DialogInterface dialog, int id) {
				 						////Call Verify method				 						
				 						try 
				 						{
				 							finish();
				 							
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} 
				 					}
				 				})
							.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int id) 
										{
											finish();
											dialog.cancel(); 	 								
										}
									});
				  
					 		// create an alert dialog
					 		AlertDialog alert = alertDialogBuilder.create();
					 		alert.show();
					}
	            	
				} 
				else 
				{
					// Show Response Json On Screen (activity)
		            Toast.makeText(PatientDetailsActivity.this, "Error in Booking Appointment please try again", Toast.LENGTH_SHORT).show();				
				}
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
    
    
    protected void sendSMSMessage() {
        Log.i("Send SMS", "");

        EditText etmblNo=(EditText)findViewById(R.id.etPatientmblNo);
        
        String phoneNo = etmblNo.getText().toString();
        String message = "Your Appointment with doctor "+doctorListItem.getDocFirstName()+" is final.\r\n Appointment Time:"+fromTime+"\r\n"+"Appointment date:"+appDate;

        try {
           SmsManager smsManager = SmsManager.getDefault();
           smsManager.sendTextMessage(phoneNo, null, message, null, null);
           Toast.makeText(getApplicationContext(), "SMS sent.",
           Toast.LENGTH_LONG).show();
           
           finish();
           
        } catch (Exception e) {
           Toast.makeText(getApplicationContext(),
           "SMS faild, please try again.",
           Toast.LENGTH_LONG).show();
           e.printStackTrace();
        }
     }
}
