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
		ValueStack stack = ActionContext.getContext().getValueStack();

		login = dao.find(playerAuth.getEmail(), playerAuth.getPassword());
		if (login != 0) {
			sessionmap.put("login", "true");
			sessionmap.put("loginId", String.valueOf(login));

			return SUCCESS;
		} else {
			Map<String, Object> context = new HashMap<String, Object>();
			context.put("errorMsg", new String("Invalid user name or password. Try again."));
			stack.push(context);
			return INPUT;
		}

	}
	
	public String logout() {
		sessionmap.invalidate();
		return "success";
	}

	
	@Override
	public void validate() {
		if (playerAuth.getEmail() == null || playerAuth.getEmail().trim().equals("")) {
			addFieldError("usrname", "Please enter a email to login with.");
			addActionMessage("You are valid user!");
		}
		if (playerAuth.getPassword() == null || playerAuth.getPassword().trim().equals("")) {
			addFieldError("password", "Please enter a password.");
		}
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
}