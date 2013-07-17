package com.github.propra13.gruppe64;

import java.io.Serializable;
import java.util.ArrayList;



public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 386380996144316489L;
	Class<? extends Object> cclass;
	public ArrayList<Object> object;
	public headers head;
	public Object[] array;
	enum headers{chatmsg,move,damage,chgready, svrmsg, setLocation, svrshutdown, clshutdown, start, attack, startLevel, switcharmor, setMap, closeLobby, setMot};
	public interface  Performer{
		public  Object performOn(NPlayer slave);
	}
	public Message(headers head, ArrayList<Object> object) {
		this.head=head;
		this.object=object;
	}
	public Message(headers head, Object[] array){
		this.head=head;
		this.array=array;
	}

}
