package com.emailtracker.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name="spinnerBean")
@SessionScoped
public class SpinnerBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int spinnerValue; 
	
	public SpinnerBean() {
		this.spinnerValue = 0;
	}

	public int getSpinnerValue() {
		return this.spinnerValue;
	}
	
	public void setSpinnerValue(int spinnerValue) {
		this.spinnerValue = spinnerValue; 
	}
		
}