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
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

public class CustomAdapterAppDocListItems extends ArrayAdapter<RowItembookedAppmnt> 
{

	String className = CustomAdapterAppDocListItems.class.getName();
	String methodName = null;
	private ValueFilter valueFilter;
	private final Context context;
    private ArrayList<RowItembookedAppmnt> itemsArrayList;

    private String strConstraint=null;
    
    private String docIdToSend;
    
    private ArrayList<RowItembookedAppmnt> listFiltered;

    private LayoutInflater mInflater;
    
     public CustomAdapterAppDocListItems(Context context, ArrayList<RowItembookedAppmnt> itemsArrayList) {

         super(context, R.layout.doc_app_booked_list_item, itemsArrayList);

         this.context = context;
         this.itemsArrayList = itemsArrayList;
         this.listFiltered=itemsArrayList;
         mInflater=LayoutInflater.from(context);
     }
     
     private class  Holder{
    	    TextView appdate;
    	    TextView appSlot;
    	    TextView pName;
    	    TextView pMblNo;   	 
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

 		// 1. Create inflater 
         LayoutInflater inflater = (LayoutInflater) context
             .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

         // 2. Get rowView from inflater
         View rowView = inflater.inflate(R.layout.doc_app_booked_list_item, parent, false);
 		 Holder viewHolder;

 	     if(convertView==null) {
	
	 	        viewHolder=new Holder();
	
	 	        final int strc=position;
	 	        
	 	        convertView=inflater.inflate(R.layout.doc_app_booked_list_item,null);
	
	 	        viewHolder.appdate=(TextView)convertView.findViewById(R.id.tvDatebookedApp);
	 	        viewHolder.appSlot=(TextView)convertView.findViewById(R.id.tvSlotBookedApp);
	 	        viewHolder.pName=(TextView)convertView.findViewById(R.id.tvPnameBookedAppmnt);
	 	        viewHolder.pMblNo=(TextView)convertView.findViewById(R.id.tvPMblNobookedAppmnt);	 	        
	 	        convertView.setTag(viewHolder);
	 	        
	 	    }else{
	
	 	        viewHolder=(Holder)convertView.getTag();
	 	       
	 	    }
 	     
 	     	String strDate=itemsArrayList.get(position).getDate();
 	     	String strDate1[]=strDate.split("-");
 	     	String strdd=strDate1[0];	     	
 	     	strdd=strdd.substring(2);
 	     	strdd=strdd+"-"+strDate1[1]+"-"+strDate1[2];
	    	viewHolder.appdate.setText(strdd);
	        viewHolder.appSlot.setText(itemsArrayList.get(position).getSlot());
	        viewHolder.pName.setText(itemsArrayList.get(position).getpName());
	        viewHolder.pMblNo.setText(itemsArrayList.get(position).getPmobileNo());
	       	        
         // 5. retrn rowView
 	    return convertView;
     }
     
     
     /*******************************************************************************
  	 ** Function Name   :	getFilter
  	 ** Created By      :	Techroot Pvt Ltd
  	 ** Description		:	This function is used to get filtered list of post when user clicks on text edit search
  	 ** Creation Date	:	20-09-2014
  	 ** Arguments		:	No arguments
  	 ** Return Type     :	Filter
  	 * 
  	 *******************************************************************************/
   //Returns a filter that can be used to constrain data with a filtering pattern.
     @Override
     public Filter getFilter() {

		if(valueFilter==null) {

             valueFilter=new ValueFilter();
         }

         return  valueFilter;
     }
     
	
     /*******************************************************************************
   	 ** Class Name   	:	ValueFilter
   	 ** Created By      :	Techroot Pvt Ltd
   	 ** Description		:	This class extends filter class which is used to filter out result on the basis of entered search criteria
   	 ** Creation Date	:	20-09-2014
   	 ** 
   	 * 
   	 *******************************************************************************/
     private class ValueFilter extends Filter {

    	    //Invoked in a worker thread to filter the data according to the constraint.
    	    @Override
    	    protected FilterResults performFiltering(CharSequence constraint) {

    	        FilterResults results=new FilterResults();
    	        strConstraint=constraint.toString();
    	        
    	        if(constraint!=null && constraint.length()>0)
    	        {
    	        	
    	            ArrayList<RowItembookedAppmnt> filterList=new ArrayList<RowItembookedAppmnt>();
    	            for(int i=0;i<listFiltered.size();i++)
    	            {
    	            	String strlistfi=listFiltered.get(i).getDocId().toLowerCase();
    	                if((listFiltered.get(i).getDocId().toLowerCase()).contains(constraint.toString().toLowerCase())) 
    	                {								
                			filterList.add(listFiltered.get(i));
    	                }
    	            }
    	            results.count=filterList.size();
    	            results.values=filterList;
    	        }
    	        else
    	        {

    	            results.count=listFiltered.size();
    	            results.values=listFiltered;
    	        }

    	        return results;
    	    }
    	    
   	    
    	     /*******************************************************************************
	  	 ** Function Name   :	publishResults
	  	 ** Created By      :	Techroot Pvt Ltd
	  	 ** Description		:	Invoked in the UI thread to publish the filtering results in the user interface.
	  	 ** Creation Date	:	20-09-2014
	  	 ** Arguments		:	No arguments
	  	 ** Return Type     :	Filter
	  	 * 
	  	 *******************************************************************************/
	    //Invoked in the UI thread to publish the filtering results in the user interface.
	    @SuppressWarnings("unchecked")
	    @Override
	    protected void publishResults(CharSequence constraint,
	            FilterResults results) 
	    {
	
	        itemsArrayList=(ArrayList<RowItembookedAppmnt>) results.values;
	
	        notifyDataSetChanged();

    	}
     }
     
     
   //How many items are in the data set represented by this Adapter.
     @Override
     public int getCount() {

         return itemsArrayList.size();
     }

     //Get the data item associated with the specified position in the data set.
     @Override
     public RowItembookedAppmnt getItem(int position) {

         return itemsArrayList.get(position);
     }

     //Get the row id associated with the specified position in the list.
     @Override
     public long getItemId(int position) {

         return position;
     }

     
}
