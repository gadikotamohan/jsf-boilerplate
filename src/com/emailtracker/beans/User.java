package com.emailtracker.beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.primefaces.context.RequestContext;

@ManagedBean(name = "user")
public class User {
	private DataSource ds;
	public User() throws NamingException{
		Context ctx = new InitialContext();
		ds = (DataSource)ctx.lookup("java:comp/env/jdbc/postgres");	  
	}

    private String username;
    
    private String password;
 
    public String getUsername() throws SQLException{
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
   
    public void login(ActionEvent event) throws SQLException{
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
    	PreparedStatement ps = ds.getConnection().prepareStatement("select * from users WHERE username = ? AND password= ? LIMIT 1");
    	ps.setString(1, username);
    	ps.setString(2, password);
    	ResultSet rs = ps.executeQuery();
//    	while(rs.next()){
//    		this.username = rs.getString("username");
//    		System.err.println("Username --> "+rs.getString("username"));
//    		System.err.println("Password --> "+rs.getString("password"));
//    	}

        if(rs.next()) {
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }
         
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    } 
    public void signup(ActionEvent event) throws SQLException{
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;

    	PreparedStatement ps = ds.getConnection().prepareStatement("insert into users values(?,?)");
    	ps.setString(1, username);
    	ps.setString(2, password);
    	boolean signedUp = ps.executeUpdate() > 0; // check for success
//    	while(rs.next()){
//    		this.username = rs.getString("username");
//    		System.err.println("Username --> "+rs.getString("username"));
//    		System.err.println("Password --> "+rs.getString("password"));
//    	}

        if(signedUp) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }
         
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("signedup", signedUp);
    } 
	public String getEmail(){
		return "hello@example.com";
	}
}
