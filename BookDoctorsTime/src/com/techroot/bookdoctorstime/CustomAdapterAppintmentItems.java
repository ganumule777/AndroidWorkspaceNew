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














import com.techroot.bookdoctorstime.appointmentManagement.model.Appointment;
import com.techroot.bookdoctorstime.exception.LeverageNonLethalException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

public class CustomAdapterAppintmentItems extends ArrayAdapter<RowItemAppointmentItems> 
{

	String className = CustomAdapterAppintmentItems.class.getName();
	String methodName = null;
	private final Context context;
	
	private String strDaySelected;
	private String strtimeSelected;
	private String strDate;
	private String strDocId;
	private String strAppmntMode;
	
    private ArrayList<RowItemAppointmentItems> itemsArrayList;
    private ArrayList<Appointment> appmntList;


    private String strConstraint=null;
    
    private ArrayList<RowItemAppointmentItems> listFiltered;

    private LayoutInflater mInflater;
    
     public CustomAdapterAppintmentItems(Context context, ArrayList<RowItemAppointmentItems> itemsArrayList,String strDaySelected, String strDateSelected,String strDocidS,ArrayList<Appointment> listAppmnt) {

         super(context, R.layout.appointment_item_list, itemsArrayList);

         this.context = context;
         this.itemsArrayList = itemsArrayList;
         this.listFiltered=itemsArrayList;
         mInflater=LayoutInflater.from(context);
         this.strDaySelected=strDaySelected;
         this.strDate=strDateSelected;
         this.strDocId=strDocidS;
         this.appmntList=listAppmnt;
     }
     
     private class  Holder{
    	    TextView appmntSlot1;
    	    TextView appmntSlot2;
    	    TextView appmntSlot3;
    	    TextView appmntSlot4;
    	    
    	 
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
    	 
    	 final ViewGroup parnt=parent;
    	 
 		// 1. Create inflater 
         LayoutInflater inflater = (LayoutInflater) context
             .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

         // 2. Get rowView from inflater
         View rowView = inflater.inflate(R.layout.appointment_item_list, parent, false);
 		 final Holder viewHolder;

 	     if(convertView==null) {
	
	 	        viewHolder=new Holder();

	 	        convertView=inflater.inflate(R.layout.appointment_item_list,null);
	
	 	        viewHolder.appmntSlot1=(TextView)convertView.findViewById(R.id.tvSlot1);
	 	        viewHolder.appmntSlot2=(TextView)convertView.findViewById(R.id.tvSlot2);
	 	        viewHolder.appmntSlot3=(TextView)convertView.findViewById(R.id.tvSlot3);
	 	        viewHolder.appmntSlot4=(TextView)convertView.findViewById(R.id.tvSlot4);
	 	        viewHolder.appmntSlot1= (TextView) convertView.findViewById(R.id.tvSlot1);	 	      		       
		        viewHolder.appmntSlot1.setOnClickListener(new OnClickListener() {
                   public void onClick(View v) 
                   {
                	   strtimeSelected=(String) ((TextView) v.findViewById(R.id.tvSlot1)).getText();
                	   
                	   int getPosition = (Integer) v.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                       itemsArrayList.get(getPosition).setSlot1Selected(v.isSelected());; // Set the value of checkbox to maintain its state.
                	   setItemNormal(parnt);
                	   viewHolder.appmntSlot1.setBackgroundResource(R.color.buttonColor);
     	   	     
                	   try 
                	   {
                		   
                		   showAlertDialog();
						
						} catch (LeverageNonLethalException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                	   
                   }
		        });
		        
		        viewHolder.appmntSlot2= (TextView) convertView.findViewById(R.id.tvSlot2);
		        viewHolder.appmntSlot2.setOnClickListener(new OnClickListener() {
                   public void onClick(View v) 
                   {
                	   strtimeSelected=(String) ((TextView) v.findViewById(R.id.tvSlot2)).getText();

                	   int getPosition = (Integer) v.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                       itemsArrayList.get(getPosition).setSlot2Selected(v.isSelected());; // Set the value of checkbox to maintain its state.

                	   setItemNormal(parnt);
                	   viewHolder.appmntSlot2.setBackgroundResource(R.color.buttonColor);
                	   try 
                	   {
                		   
                		   showAlertDialog();
						
						} catch (LeverageNonLethalException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                	   
                	   
                   }
		        });
		        
		        viewHolder.appmntSlot3= (TextView) convertView.findViewById(R.id.tvSlot3);
		        viewHolder.appmntSlot3.setOnClickListener(new OnClickListener() {
                   public void onClick(View v) 
                   {
                	   strtimeSelected=(String) ((TextView) v.findViewById(R.id.tvSlot3)).getText();
                	   
                	   int getPosition = (Integer) v.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                       itemsArrayList.get(getPosition).setSlot3Selected(v.isSelected());; // Set the value of checkbox to maintain its state.

                	   setItemNormal(parnt);
                	   viewHolder.appmntSlot3.setBackgroundResource(R.color.buttonColor);
                	   
                	   try 
                	   {
                		   
                		   showAlertDialog();
						
						} catch (LeverageNonLethalException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                   }
		        });
		        
		        viewHolder.appmntSlot4= (TextView) convertView.findViewById(R.id.tvSlot4);
		        viewHolder.appmntSlot4.setOnClickListener(new OnClickListener() {
                   public void onClick(View v) 
                   {
                	   strtimeSelected=(String) ((TextView) v.findViewById(R.id.tvSlot4)).getText();
                	   
                	   int getPosition = (Integer) v.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                       itemsArrayList.get(getPosition).setSlot4Selected(v.isSelected());; // Set the value of checkbox to maintain its state.

                	   setItemNormal(parnt);
                	   viewHolder.appmntSlot4.setBackgroundResource(R.color.buttonColor);
                	   
                	   try 
                	   {
                		   
                		   showAlertDialog();
						
						} catch (LeverageNonLethalException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                	   
                	   	
                   }
		        });
	 	        
	 	        convertView.setTag(viewHolder);
	 	        convertView.setTag(R.id.tvSlot1, viewHolder.appmntSlot1);
	            convertView.setTag(R.id.tvSlot2, viewHolder.appmntSlot2);
	            convertView.setTag(R.id.tvSlot3, viewHolder.appmntSlot3);
	            convertView.setTag(R.id.tvSlot4, viewHolder.appmntSlot4);

	            
	 	    }else{
	
	 	        viewHolder=(Holder)convertView.getTag();
	 	       
	 	    }
	 	    
 	     	// if slot is book already then make it disabled	    
 	        viewHolder.appmntSlot1.setTag(position); 
 	        viewHolder.appmntSlot2.setTag(position); 
 	        viewHolder.appmntSlot3.setTag(position); 
 	        viewHolder.appmntSlot4.setTag(position); 

 	        
	    	viewHolder.appmntSlot1.setText(itemsArrayList.get(position).getAppSlot1());
	        viewHolder.appmntSlot2.setText(itemsArrayList.get(position).getAppSlot2());
	        viewHolder.appmntSlot3.setText(itemsArrayList.get(position).getAppSlot3());
	        viewHolder.appmntSlot4.setText(itemsArrayList.get(position).getAppSlot4());
	        

            for(int i=0;i<appmntList.size();i++)
	 	       {
	        	  if((appmntList.get(i).getBookFromTime()).equals(viewHolder.appmntSlot1.getText().toString()))
	        	  {
	        		  itemsArrayList.get(position).setSlot1Selected(true);
	        	  }
	        	  if((appmntList.get(i).getBookFromTime()).equals(viewHolder.appmntSlot2.getText().toString()))
	        	  {
	        		  itemsArrayList.get(position).setSlot2Selected(true);
	        	  }
	        	  if((appmntList.get(i).getBookFromTime()).equals(viewHolder.appmntSlot3.getText().toString()))
	        	  {
	        		  itemsArrayList.get(position).setSlot3Selected(true);

	        	  }
	        	  if((appmntList.get(i).getBookFromTime()).equals(viewHolder.appmntSlot4.getText().toString()))
	        	  {
	        		  itemsArrayList.get(position).setSlot4Selected(true);
	        	  }
	 	       }
	        
            viewHolder.appmntSlot1.setEnabled(!(itemsArrayList.get(position).isSlot1Selected()));
 	        viewHolder.appmntSlot2.setEnabled(!(itemsArrayList.get(position).isSlot2Selected()));
 	        viewHolder.appmntSlot3.setEnabled(!(itemsArrayList.get(position).isSlot3Selected()));
 	        viewHolder.appmntSlot4.setEnabled(!(itemsArrayList.get(position).isSlot4Selected()));
	        
	        strAppmntMode=itemsArrayList.get(position).getStrAppmntMode();	        
         // 5. retrn rowView
 	    return convertView;
     }
     
    
  

 
     public void setItemNormal(ViewGroup parent)
     {
    	 
         for (int i=0; i< parent.getChildCount(); i++)
         {
             View v = parent.getChildAt(i);
             //TextView txtview = ((TextView) v.findViewById(R.id.menurow_title));
             TextView txtview1 = ((TextView)v.findViewById(R.id.tvSlot1));
             txtview1.setBackgroundResource(R.color.base_color);
             
             TextView txtview2 = ((TextView)v.findViewById(R.id.tvSlot2));
             txtview2.setBackgroundResource(R.color.base_color);
             
             TextView txtview3= ((TextView)v.findViewById(R.id.tvSlot3));
             txtview3.setBackgroundResource(R.color.base_color);
             
             TextView txtview4= ((TextView)v.findViewById(R.id.tvSlot4));
             txtview4.setBackgroundResource(R.color.base_color);
         }
     }
     
     /* *************************************
     function name : showAlertDialog()
     return type   : void
     arguments     : No arguments
     description   : This function used to show alert dialog which is having active distributor  list in spainner
     created by    : Ganesh Mule
     ****************************************** */
 	public void showAlertDialog() throws LeverageNonLethalException 
 	{

  		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
  		alertDialogBuilder.setTitle("Continue");
  		alertDialogBuilder.setMessage("Do you want to continue?");
  		
  	    // setup a dialog window
 		alertDialogBuilder
 				.setCancelable(false)
 				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
 					public void onClick(DialogInterface dialog, int id) {					
 						try 
 						{
 							Intent intent = new Intent(context,PatientDetailsActivity.class);                    	 	                    	
 	                    	intent.putExtra("appmnttimeSlot", strtimeSelected);
 	                    	intent.putExtra("strDay", strDaySelected);	                    	
 	                    	intent.putExtra("strDate",strDate);
 	                    	intent.putExtra("strDocId",strDocId);                    	
 	                    	intent.putExtra("strAppmntMode", strAppmntMode);
 	                    	context.startActivity(intent);
						}  												
 						catch (Exception e) 
 						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
 						
 					}
 				})
			.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) 
						{						
							dialog.cancel(); 													
						}
					});
	 		// create an alert dialog
	 		AlertDialog alert = alertDialogBuilder.create(); 		
	 		alert.show();
 	}
     
}
