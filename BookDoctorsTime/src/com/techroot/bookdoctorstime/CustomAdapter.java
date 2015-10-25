/*********************************************************************************
**********************************************************************************
** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
** Author :         	Ganesh Mule
** Created on :     	15-09-2014
** Dept:            	Android Based Mobile App Development
** Class:          		CustomAdapter
** Description:     	This is class used to make custom adapter for list view in android
***********************************************************************************
***********************************************************************************/
package com.techroot.bookdoctorstime;

import java.util.ArrayList;
import java.util.List;









import com.techroot.bookdoctorstime.doctorManagement.model.Doctor;
import com.techroot.bookdoctorstime.exception.LeverageExceptions;
import com.techroot.bookdoctorstime.exception.LeverageNonLethalException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.opengl.Visibility;
import android.text.Html;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<RowItem> 
{

	String className = CustomAdapter.class.getName();
	String methodName = null;
	private final Activity context;
    private ArrayList<RowItem> itemsArrayList;

    private String strConstraint=null;   
    private String strAppmntMode;
    
    private String docIdToSend;
    private String strDocClinicArea;

    
    private ArrayList<RowItem> listFiltered;

    private LayoutInflater mInflater;
    
    private boolean bDocClnc;
    
    
     public CustomAdapter(Activity context, ArrayList<RowItem> itemsArrayList) {

         super(context, R.layout.doctors_profile_list_item, itemsArrayList);

         this.context = context;
         this.itemsArrayList = itemsArrayList;
         this.listFiltered=itemsArrayList;
         mInflater=LayoutInflater.from(context);
     }
     
     private class  Holder{
    	 
    	 	protected ImageButton ibPhoto;
    	 	protected TextView docName;
    	 	protected TextView docSpeciality;
    	   // TextView docClinic;
    	 	protected TextView docArea;
    	 	protected TextView docWorkExp;
    	 	protected  TextView docConsualtionFees;
    	 	protected TextView docQualification;
    	 	protected TextView docId;
    	 	protected Button b1;
    	 	protected Button b2;
    	 	protected RadioButton rb1;
    	 	protected RadioButton rb2;
    	    

    	 
    	}

     
 	/*******************************************************************************
 	 ** Function Name   :	getView
 	 ** Created By      :	Techroot Pvt Ltd
 	 ** Description		:	This function is used to populate and show details in listview
 	 ** Creation Date	:	20-09-2014
 	 ** Arguments		:	int position of item, View and ViewGroup
 	 ** Return Type     :	void
 	 * @throws LeverageNonLethalException 
 	 * @throws ParseException 
 	 *******************************************************************************/
     
     @Override
     public View getView(int position, View convertView, ViewGroup parent) 
     {
    	 this.methodName="getView";

 		
         // 2. Get rowView from inflater
 		  final Holder viewHolder;

 	     if(convertView==null) {
	
    
	            LayoutInflater inflator = context.getLayoutInflater();

	 	        final int strc=position;
	 	        
	 	        convertView=inflator.inflate(R.layout.doctors_profile_list_item,null);
	 	        viewHolder=new Holder();

	 	        viewHolder.docName=(TextView)convertView.findViewById(R.id.tvDocName);
	 	        viewHolder.docSpeciality=(TextView)convertView.findViewById(R.id.tvDocSpeciality);
	 	        //viewHolder.docClinic=(TextView)convertView.findViewById(R.id.tvClinicName);
	 	        viewHolder.docArea=(TextView)convertView.findViewById(R.id.tvClinicArea);
	 	        viewHolder.docConsualtionFees=(TextView)convertView.findViewById(R.id.tvConsulationFees);
	 	        viewHolder.docWorkExp=(TextView)convertView.findViewById(R.id.tvDocExp);
		        viewHolder.docId= (TextView) convertView.findViewById(R.id.tvDocId);
		        viewHolder.docQualification= (TextView) convertView.findViewById(R.id.tvQulfctn);
		        viewHolder.rb1= (RadioButton) convertView.findViewById(R.id.rbDocHospArea);
		        viewHolder.rb2= (RadioButton) convertView.findViewById(R.id.rbDocClinic);

		        viewHolder.rb1.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) 
                    {
                    	// Set List Adapter
	       	   	     	LayoutInflater layoutInflater = LayoutInflater.from(context);
	       	   	     	final View promptView = layoutInflater.inflate(R.layout.doctors_profile_list_item, null);
	       	   	        // Add search field in product  list and add text changed event on it

	       	 	 	    int getPosition = (Integer) v.getTag();  // Here we get the position that we have set for the checkbox using setTag.
	                  
                  	    itemsArrayList.get(getPosition).setbHosp(((RadioButton) v).isChecked());
	       	 	 		
                    	// Is the button now checked?
                        boolean checked = ((RadioButton) v).isChecked();
                        if(checked)
                        {
                        	bDocClnc=false;
                      	    itemsArrayList.get(getPosition).setbDocClinic(false);

                        }
                        else
                        {
                        	bDocClnc=true;
                        }
                        
                     // Check which radio button was clicked
                        switch (v.getId()) {
                        case R.id.rbDocClinic:
                            // set inch button to unchecked
                            viewHolder.rb1.setChecked(!checked);                            
                            break;
                        case R.id.rbDocHospArea:
                            // set MM button to unchecked
                            viewHolder.rb2.setChecked(!checked);                          
                            break;
                        }
                      
                    }                
                    });
                    
		        
		        viewHolder.rb2.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) 
                    {
                    	
                    	// Set List Adapter
	       	   	     	LayoutInflater layoutInflater = LayoutInflater.from(context);
	       	   	     	final View promptView = layoutInflater.inflate(R.layout.doctors_profile_list_item, null);
	       	   	        // Add search field in product  list and add text changed event on it

	       	 	 	    int getPosition = (Integer) v.getTag();  // Here we get the position that we have set for the checkbox using setTag.
	                  
                  	    itemsArrayList.get(getPosition).setbDocClinic(((RadioButton) v).isChecked());

                    	// Is the button now checked?
                        boolean checked = ((RadioButton) v).isChecked();

                        if(checked)
                        {
                        	bDocClnc=true;
                      	    itemsArrayList.get(getPosition).setbHosp(false);
                        }
                        else
                        {
                        	bDocClnc=false;
                        }
                     // Check which radio button was clicked
                        switch (v.getId()) {
                        case R.id.rbDocClinic:
                            // set inch button to unchecked
                            viewHolder.rb1.setChecked(!checked);                            
                            break;
                        case R.id.rbDocHospArea:
                            // set MM button to unchecked
                            viewHolder.rb2.setChecked(!checked);                          
                            break;
                        }
                    }                
                    });
                    
		        
		        viewHolder.b1= (Button) convertView.findViewById(R.id.btBookAppmnt);		        
		        viewHolder.b1.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) 
                    {       
                    	try
                    	{
	                	    View parentView = (View) v.getParent();
	                	    String textview1 = ((TextView)parentView.findViewById(R.id.tvDocId)).getText().toString();                   	
	                    	Intent intent = new Intent(context,AppointmentActivity.class);
	                    	strAppmntMode=itemsArrayList.get(strc).getAppmntMode();
	                    	Log.d("Value", itemsArrayList.get(strc).getDoctorId());
	                    	
		       	 	 	    int getPosition = (Integer) v.getTag();  // Here we get the position that we have set for the checkbox using setTag.
	
	                    	if(!itemsArrayList.get(getPosition).isbDocClinic()&& !itemsArrayList.get(getPosition).isbHosp())
	                    	{
	        					throw new LeverageNonLethalException(LeverageExceptions.ERROR_CONNECTING_DATABASE,"Select doctor clinic or hospital attached");
	                    	}
		       	 	 	    
	                    	docIdToSend=textview1;
	                    	intent.putExtra("docId", docIdToSend);
	                    	intent.putExtra("docArea", viewHolder.docArea.getText().toString());
	                   	
	                    	intent.putExtra("strAppmntMode", strAppmntMode);
	                    	intent.putExtra("bDoc", bDocClnc);
	
	                    	context.startActivity(intent);
                    	}
                    	catch(LeverageNonLethalException e)
                    	{
				            Toast.makeText(context, e.getExceptionMsg(), Toast.LENGTH_SHORT).show();	

                    	}
                    }
		        });
		        
		        viewHolder.b2= (Button) convertView.findViewById(R.id.btRequestAppmnt);
		        
		        viewHolder.b2.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) 
                    {  
                    	try
                    	{
	                	    View parentView = (View) v.getParent();
	                	    String textview1 = ((TextView)parentView.findViewById(R.id.tvDocId)).getText().toString();
	                    	
	                    	Intent intent = new Intent(context,AppointmentActivity.class);
	                    	strAppmntMode=itemsArrayList.get(strc).getAppmntMode();
	
	                    	int getPosition = (Integer) v.getTag();  // Here we get the position that we have set for the checkbox using setTag.
	                    	
	                    	if(!itemsArrayList.get(getPosition).isbDocClinic()&& !itemsArrayList.get(getPosition).isbHosp())
	                    	{
	        					throw new LeverageNonLethalException(LeverageExceptions.ERROR_CONNECTING_DATABASE,"Select doctor clinic or hospital attached");
	                    	}
	                    	
	                    	Log.d("Value", itemsArrayList.get(strc).getDoctorId());
	                    	intent.putExtra("docArea", viewHolder.docArea.getText().toString());
	                    	docIdToSend=textview1;
	                    	intent.putExtra("docId", docIdToSend);
	                    	intent.putExtra("strAppmntMode", strAppmntMode);
	                    	intent.putExtra("bDoc", bDocClnc);	
	                    	context.startActivity(intent);
                      }
	                	catch(LeverageNonLethalException e)
	                	{
				            Toast.makeText(context, e.getExceptionMsg(), Toast.LENGTH_SHORT).show();	
	
	                	}
                    }
		        });
		        
		        viewHolder.ibPhoto= (ImageButton) convertView.findViewById(R.id.ibDocPhoto);

		        viewHolder.ibPhoto.setOnClickListener(new OnClickListener() {
		        	 
					@Override
					public void onClick(View v) {
		 
						View parentView = (View) v.getParent();
                	   // String textview1 = ((TextView)parentView.findViewById(R.id.tvDocId)).getText().toString();                   	
						 int getPosition = (Integer) v.getTag();          	
                    	// Set List Adapter
	       	   	     	LayoutInflater layoutInflater = LayoutInflater.from(context);
	       	   	     	final View promptView = layoutInflater.inflate(R.layout.doctors_profile_list_item, null);
	       	   	        // Add search field in product  list and add text changed event on it
	       	 	 		final TextView tvDocId=(TextView)promptView.findViewById(R.id.tvDocId);

                    	Intent intent = new Intent(context,DoctorsDetailsProfileActivity.class);                   	       	
                    	docIdToSend=itemsArrayList.get(getPosition).getDoctorId();
                    	intent.putExtra("docId", docIdToSend);
                    	context.startActivity(intent);
					}
		 
				});
		        
		       	        
	 	        convertView.setTag(viewHolder);	 
	            convertView.setTag(R.id.ibDocPhoto, viewHolder.ibPhoto);
	            convertView.setTag(R.id.rbDocClinic, viewHolder.rb1);
	            convertView.setTag(R.id.rbDocHospArea, viewHolder.rb2);
	            convertView.setTag(R.id.btBookAppmnt, viewHolder.b1);
	            convertView.setTag(R.id.btRequestAppmnt, viewHolder.b2);
	            convertView.setTag(R.id.tvDocId, viewHolder.docId); 	        
	 	    }
 	      else{
	 	    	
	 	        viewHolder=(Holder)convertView.getTag();
	 	       
	 	    }
 	     
 	     	viewHolder.ibPhoto.setTag(position);
	        viewHolder.rb1.setTag(position); 
	        viewHolder.b1.setTag(position); 
	        viewHolder.b2.setTag(position); 

	        viewHolder.rb2.setTag(position); 
	    	viewHolder.docName.setText(itemsArrayList.get(position).getDoctorsName());
	       // viewHolder.docClinic.setText(itemsArrayList.get(position).getClinic());
	        viewHolder.docSpeciality.setText(itemsArrayList.get(position).getSpeciality());
	        
	        if(null==itemsArrayList.get(position).getClinic())
	        {
		        viewHolder.docArea.setText("Not Attached To Hospital");
	        }
	        else
	        {
		        viewHolder.docArea.setText(itemsArrayList.get(position).getClinic()+", "+itemsArrayList.get(position).getClinicArea());
	        }
	        
	        viewHolder.docWorkExp.setText(itemsArrayList.get(position).getDocWorkExp());
	        viewHolder.docConsualtionFees.setText(itemsArrayList.get(position).getConsulationFees());
	        viewHolder.docId.setText(itemsArrayList.get(position).getDoctorId());
	        viewHolder.docQualification.setText(itemsArrayList.get(position).getDocQulfctn());
	        viewHolder.rb1.setChecked(itemsArrayList.get(position).isbHosp());
	        viewHolder.rb2.setChecked(itemsArrayList.get(position).isbDocClinic());
	        if(("manual").equals(itemsArrayList.get(position).getAppmntMode()))
	        {
	        	viewHolder.b1.setVisibility(View.GONE);
	        	viewHolder.b2.setVisibility(View.VISIBLE);
	        }
	        else
	        {
	        	viewHolder.b1.setVisibility(View.VISIBLE);
	        	viewHolder.b2.setVisibility(View.GONE);
	        }
	        
         // 5. retrn rowView
 	    return convertView;
     }
     
     
     
     
	
   

     
}
