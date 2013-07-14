package com.github.propra13.gruppe64;

import java.io.Serializable;
import java.util.ArrayList;



public class Nmessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 386380996144316489L;
	Class<? extends Object> cclass;
	public ArrayList<Object> object;
	public headers head;
	enum headers{chatmsg,move,damage,chgready};
	
	public Nmessage(headers head, ArrayList<Object> object) {
		this.head=head;
		this.object=object;
	}
	
}
