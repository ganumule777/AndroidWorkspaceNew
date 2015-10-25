
/*********************************************************************************
**********************************************************************************
** Copyright (C) 2014 Techroot Pvt. Ltd. Pune INDIA
** Author :         	Ganesh Mule
** Created on :     	01-10-2014
** Dept:            	SpicyDelas App Dev
** Class:          		Category
** Description:     	This is model class for category
** Last Modified on :
** Last Modified by :
***********************************************************************************
***********************************************************************************/
package com.techroot.bookdoctorstime.doctorManagement.model;

import java.io.Serializable;
import java.sql.Timestamp;


public class Doctor implements Serializable
{
	private String docId;
	
	private String docFirstName;
	private String docLastName;
	private String docPhoneNo;
	private String docMobileNo;
	private String docEmailId;
	private String docSpecilization;
	private String yearsOfExp;
	private String clinicTiming;
	private String docOwnClinicTiming;
	
	private String docOwnClinicArea;

	private String consulationFee;
	
	
	private String docDetailsProfile;

	private String state;
	private String city;
	private String area;
	private String address;
	private String appmntMode;
	private String leaveFromDate;
	private String leaveToDate;

	private String docQualfctn;

	private String docClinicName;

	private String docTiming;
	private String dayOfWork;
	
	public String getDocTiming() {
		return docTiming;
	}
	public void setDocTiming(String docTiming) {
		this.docTiming = docTiming;
	}
	public String getDayOfWork() {
		return dayOfWork;
	}
	public void setDayOfWork(String dayOfWork) {
		this.dayOfWork = dayOfWork;
	}
	

	public String getDocClinicName() {
		return docClinicName;
	}
	public void setDocClinicName(String docClinicName) {
		this.docClinicName = docClinicName;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getDocFirstName() {
		return docFirstName;
	}
	public void setDocFirstName(String docFirstName) {
		this.docFirstName = docFirstName;
	}
	public String getDocLastName() {
		return docLastName;
	}
	public void setDocLastName(String docLastName) {
		this.docLastName = docLastName;
	}
	public String getDocPhoneNo() {
		return docPhoneNo;
	}
	public void setDocPhoneNo(String docPhoneNo) {
		this.docPhoneNo = docPhoneNo;
	}
	public String getDocMobileNo() {
		return docMobileNo;
	}
	public void setDocMobileNo(String docMobileNo) {
		this.docMobileNo = docMobileNo;
	}
	public String getDocEmailId() {
		return docEmailId;
	}
	public void setDocEmailId(String docEmailId) {
		this.docEmailId = docEmailId;
	}
	public String getDocSpecilization() {
		return docSpecilization;
	}
	public void setDocSpecilization(String docSpecilization) {
		this.docSpecilization = docSpecilization;
	}
	public String getYearsOfExp() {
		return yearsOfExp;
	}
	public void setYearsOfExp(String yearsOfExp) {
		this.yearsOfExp = yearsOfExp;
	}
	public String getClinicTiming() {
		return clinicTiming;
	}
	public void setClinicTiming(String clinicTiming) {
		this.clinicTiming = clinicTiming;
	}
	public String getConsulationFee() {
		return consulationFee;
	}
	public void setConsulationFee(String consulationFee) {
		this.consulationFee = consulationFee;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAppmntMode() {
		return appmntMode;
	}
	public void setAppmntMode(String appmntMode) {
		this.appmntMode = appmntMode;
	}
	public String getLeaveFromDate() {
		return leaveFromDate;
	}
	public void setLeaveFromDate(String leaveFromDate) {
		this.leaveFromDate = leaveFromDate;
	}
	public String getLeaveToDate() {
		return leaveToDate;
	}
	public void setLeaveToDate(String leaveToDate) {
		this.leaveToDate = leaveToDate;
	}
	
	public String getDocQualfctn() {
		return docQualfctn;
	}
	public void setDocQualfctn(String docQualfctn) {
		this.docQualfctn = docQualfctn;
	}
	
	public String getDocDetailsProfile() {
		return docDetailsProfile;
	}
	public void setDocDetailsProfile(String docDetailsProfile) {
		this.docDetailsProfile = docDetailsProfile;
	}

	public String getDocOwnClinicTiming() {
		return docOwnClinicTiming;
	}
	public void setDocOwnClinicTiming(String docOwnClinicTiming) {
		this.docOwnClinicTiming = docOwnClinicTiming;
	}
	public String getDocOwnClinicArea() {
		return docOwnClinicArea;
	}
	public void setDocOwnClinicArea(String docOwnClinicArea) {
		this.docOwnClinicArea = docOwnClinicArea;
	}
	
}
	
	
	
	