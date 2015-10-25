/*********************************************************************************
**********************************************************************************
** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
** Author :         	Ganesh Mule
** Created on :     	01-09-2014
** Dept:            	Android Based Mobile App Development
** Class:          		RowItem
** Description:     	This is the class having Row Item Details for Custom Adapter
**
***********************************************************************************
***********************************************************************************/

package com.techroot.bookdoctorstime;

public class RowItembookedAppmnt {

	
	private String docId;
	private String date;
	private String slot;
	private String pName;
	private String PmobileNo;
	
	
	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getPmobileNo() {
		return PmobileNo;
	}

	public void setPmobileNo(String pmobileNo) {
		PmobileNo = pmobileNo;
	}


	public RowItembookedAppmnt(String strDocId,String strDate,String strSlot,String strPName,String strPMblNo) {
		this.slot = strSlot;
		this.date= strDate;
		this.pName= strPName;
		this.PmobileNo= strPMblNo;
		this.docId=strDocId;
	}

	public RowItembookedAppmnt()
	{
		
	}
	

	
}
