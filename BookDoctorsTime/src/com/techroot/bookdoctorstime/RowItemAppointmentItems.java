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

public class RowItemAppointmentItems {


	private String appSlot1;
	private String appSlot2;
	private String appSlot3;
	private String appSlot4;
	private boolean isSlot1Selected;
	

	private boolean isSlot2Selected;
	private boolean isSlot3Selected;
	private boolean isSlot4Selected;

	private String strAppmntMode;

	

	public String getAppSlot1() {
		return appSlot1;
	}

	public void setAppSlot1(String appSlot1) {
		this.appSlot1 = appSlot1;
	}

	public String getAppSlot2() {
		return appSlot2;
	}

	public void setAppSlot2(String appSlot2) {
		this.appSlot2 = appSlot2;
	}

	public String getAppSlot3() {
		return appSlot3;
	}

	public void setAppSlot3(String appSlot3) {
		this.appSlot3 = appSlot3;
	}

	public String getAppSlot4() {
		return appSlot4;
	}

	public void setAppSlot4(String appSlot4) {
		this.appSlot4 = appSlot4;
	}

	public boolean isSlot1Selected() {
		return isSlot1Selected;
	}

	public void setSlot1Selected(boolean isSlot1Selected) {
		this.isSlot1Selected = isSlot1Selected;
	}

	public boolean isSlot2Selected() {
		return isSlot2Selected;
	}

	public void setSlot2Selected(boolean isSlot2Selected) {
		this.isSlot2Selected = isSlot2Selected;
	}

	public boolean isSlot3Selected() {
		return isSlot3Selected;
	}

	public void setSlot3Selected(boolean isSlot3Selected) {
		this.isSlot3Selected = isSlot3Selected;
	}

	public boolean isSlot4Selected() {
		return isSlot4Selected;
	}

	public void setSlot4Selected(boolean isSlot4Selected) {
		this.isSlot4Selected = isSlot4Selected;
	}
	
	public RowItemAppointmentItems(String strSlot1,String strSlot2,String strSlot3,String strSlot4,String strAppmntMode)
	{
		this.appSlot1 = strSlot1;
		this.appSlot2= strSlot2;
		this.appSlot3= strSlot3;
		this.appSlot4= strSlot4;
		this.strAppmntMode=strAppmntMode;
		
	}

	public RowItemAppointmentItems()
	{
		
	}
	public String getStrAppmntMode() {
		return strAppmntMode;
	}

	public void setStrAppmntMode(String strAppmntMode) {
		this.strAppmntMode = strAppmntMode;
	}
		
}
