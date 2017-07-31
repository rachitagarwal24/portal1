package com.portal;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

public class LoginAction extends ActionSupport implements ModelDriven<PlayerAuth>, SessionAware {

	private static final long serialVersionUID = 1L;

	private int login;
	SessionMap<String, String> sessionmap;
	PlayerAuth playerAuth = new PlayerAuth();
	PlayerDao dao = new PlayerDao();

	@Override
	public String execute() {
		login = dao.find(playerAuth.getEmail(), playerAuth.getPassword());
		if (login != 0) {
			sessionmap.put("login", "true");
			sessionmap.put("loginId", String.valueOf(login));

			return SUCCESS;
		} else {
			return INPUT;
		}
	}
	
	public String logout() {
		System.out.println("logout method executed");
		sessionmap.invalidate();
		return "input";
	}
	
	

	public PlayerAuth getPlayerAuth() {
		return playerAuth;
	}

	public void setPlayerAuth(PlayerAuth playerAuth) {
		this.playerAuth = playerAuth;
	}

	public void setSession(Map map) {
		sessionmap = (SessionMap) map;
	}

	
	public PlayerAuth getModel() {
		return playerAuth;
	}
	/*
	@Override
	public void validate() {
		if (playerAuth.getEmail() == null || playerAuth.getEmail().trim().equals("")) {
			addFieldError("usrname", "Please enter a email to login with.");
			
		}
		if (playerAuth.getPassword() == null || playerAuth.getPassword().trim().equals("")) {
			addFieldError("password", "Please enter a password.");
		}
	}
	*/
}