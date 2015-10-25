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

public class RowItem {

	
	private String doctorsName;
	private String speciality;
	private String doctorId;
	private String clinic;
	private String docWorkExp;
	private String consulationFees;
	private String appmntMode;
	private String docQulfctn;
	private boolean bHosp;
	private boolean bDocClinic;
	
	
	public boolean isbHosp() {
		return bHosp;
	}
	public void setbHosp(boolean bHosp) {
		this.bHosp = bHosp;
	}
	public boolean isbDocClinic() {
		return bDocClinic;
	}
	public void setbDocClinic(boolean bDocClinic) {
		this.bDocClinic = bDocClinic;
	}
	
	public String getClinicArea() {
		return clinicArea;
	}
	public void setClinicArea(String clinicArea) {
		this.clinicArea = clinicArea;
	}

	private String clinicArea;

	public String getDoctorsName() {
		return doctorsName;
	}
	public void setDoctorsName(String doctorsName) {
		this.doctorsName = doctorsName;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	public String getClinic() {
		return clinic;
	}
	public void setClinic(String clinic) {
		this.clinic = clinic;
	}
	

	public RowItem(String strDocId,String strDocName,String strSpeciality,String strClinic,String strArea,String workExp,String strConsualtnFees,String strQulfctn,String strAppmntMode) {
		this.doctorsName = strDocName;
		this.speciality= strSpeciality;
		this.clinic= strClinic;
		this.clinicArea= strArea;
		this.consulationFees=strConsualtnFees;
		this.docWorkExp=workExp;
		this.doctorId=strDocId;
		this.docQulfctn=strQulfctn;
		this.appmntMode=strAppmntMode;
	}

	public RowItem()
	{
		
	}
	
	public String getDocWorkExp() {
		return docWorkExp;
	}
	public void setDocWorkExp(String docWorkExp) {
		this.docWorkExp = docWorkExp;
	}
	public String getConsulationFees() {
		return consulationFees;
	}
	public void setConsulationFees(String consulationFees) {
		this.consulationFees = consulationFees;
	}
	public String getDocQulfctn() {
		return docQulfctn;
	}
	public void setDocQulfctn(String docQulfctn) {
		this.docQulfctn = docQulfctn;
	}

	public String getAppmntMode() {
		return appmntMode;
	}
	public void setAppmntMode(String appmntMode) {
		this.appmntMode = appmntMode;
	}
	
}
