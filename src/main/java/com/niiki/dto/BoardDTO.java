package com.niiki.dto;

import java.sql.Timestamp;
import java.util.ArrayList;

public class BoardDTO {
	private int bno;
	private String user_id;
	private String subject;
	private String content;
	private Timestamp writeDate;
	private int readCount;
	private int recCount;
	private int comtCount;
	private String approvalStatus;
	private ArrayList<BoardAttachDTO> attachList;
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Timestamp writeDate) {
		this.writeDate = writeDate;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public int getRecCount() {
		return recCount;
	}
	public void setRecCount(int recCount) {
		this.recCount = recCount;
	}
	public int getComtCount() {
		return comtCount;
	}
	public void setComtCount(int comtCount) {
		this.comtCount = comtCount;
	}
	
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	public ArrayList<BoardAttachDTO> getAttachList() {
		return attachList;
	}
	public void setAttachList(ArrayList<BoardAttachDTO> attachList) {
		this.attachList = attachList;
	}
	@Override
	public String toString() {
		return "BoardDTO [bno=" + bno + ", user_id=" + user_id + ", subject=" + subject + ", content=" + content
				+ ", writeDate=" + writeDate + ", readCount=" + readCount + ", recCount=" + recCount + ", comtCount="
				+ comtCount + ", approvalStatus=" + approvalStatus + ", attachList=" + attachList + "]";
	}

}
