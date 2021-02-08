package com.niiki.dto;

import java.sql.Date;

public class MemberDTO {
	private String user_id;
	private String user_pw;
	private String user_name;
	private Date user_birth; 
	private String user_gender;
	private String user_email;
	private Date regDate;
	private Date logDate;
	private String approvalStatus;
	private String approvalKey;
	private String profile_photo;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Date getUser_birth() {
		return user_birth;
	}
	public void setUser_birth(Date user_birth) {
		this.user_birth = user_birth;
	}
	public String getUser_gender() {
		return user_gender;
	}
	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getApprovalKey() {
		return approvalKey;
	}
	public void setApprovalKey(String approvalKey) {
		this.approvalKey = approvalKey;
	}
	public String getProfile_photo() {
		return profile_photo;
	}
	public void setProfile_photo(String profile_photo) {
		this.profile_photo = profile_photo;
	}
	
	@Override
	public String toString() {
		return "MemberDTO [user_id=" + user_id + ", user_pw=" + user_pw + ", user_name=" + user_name + ", user_birth="
				+ user_birth + ", user_gender=" + user_gender + ", user_email=" + user_email + ", regDate=" + regDate
				+ ", logDate=" + logDate + ", approvalStatus=" + approvalStatus + ", approvalKey=" + approvalKey
				+ ", profile_photo=" + profile_photo + "]";
	}
}