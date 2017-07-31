package com.portal;

import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AddActionOfficer extends ActionSupport implements ModelDriven<Officer>, SessionAware {

	private static final long serialVersionUID = 1L;
	private int loginofficer;

	SessionMap<String, String> sessionmap;
	Officer officer = new Officer();
	PlayerDao dao = new PlayerDao();

	public String execute() {
		int i=dao.addOfficer(officer);
		
		if(i>0)
			addActionMessage("USER IS SUCESSFULLY REGISTERED");
		else
			addActionMessage("This email is already registered. use another email to get registered");
		return "success";
				
	}

	public String officerLogin() {
		loginofficer = dao.findOfficer(officer.getEmail(), officer.getPassword());
		if (loginofficer != 0) {

			sessionmap.put("loginofficer", "true");
			sessionmap.put("loginIdO", "23");

			return SUCCESS;
		} else {
			this.addActionError("Invalid username and password");
		}

		return INPUT;
	}

	public String logout() {
		sessionmap.invalidate();
		return "input";
	}

	public Officer getOfficer() {
		return officer;
	}

	public void setOfficer(Officer officer) {
		this.officer = officer;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map map) {
		sessionmap = (SessionMap<String, String>) map;
	}

	public Officer getModel() {
		return officer;
	}

}
