package com.github.propra13.gruppe64;

import java.awt.Component;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JTextField;

import com.github.propra13.gruppe64.visible.Item;
import com.github.propra13.gruppe64.visible.Map;
import com.github.propra13.gruppe64.visible.Movable.axis;
import com.github.propra13.gruppe64.visible.Movable.dir;

public interface Player extends Serializable,ActiveArea{

	Level getLevel();

	Game getGame();

	void addStatBar(StatBar statBar);
	
	public class ChatterBox {
		private Chat chatPane;
		private JTextField chatInput;
		public ChatterBox(Chat chatPane, JTextField chatInput){
			this.chatPane=chatPane;
			this.chatInput=chatInput;
		}
		public ChatterBox(Player pl) {
			chatPane = new Chat(pl);
			chatInput = new JTextField();
			chatInput.setToolTipText("Chatinput");
		}
		public void addChatPane(Chat chatp) {
			this.chatPane = chatp;
			
		}
		public void addChatInput(JTextField chatinput) {
			// TODO Auto-generated method stub
			chatInput = chatinput;
		}

		public JTextField getChatInput() {
			return chatInput;
		}
		
		public Chat getChatPane(){
			return chatPane;
		}

		
		public String getChatInputText() {
			
			return getChatInput().getText();
		}
		
		public void setChatInputText(String string) {
			getChatInput().setText(string);
			
		}
		
		public boolean chatIsFocusOwner() {
			
			return getChatInput().isFocusOwner();
		}
		
		public void tell(ActiveArea player, String chatInputText) {
			writeChat(player,chatInputText);
			
		}
		
		public void writeChat(String string) {
			getChatPane().append(string);
			
		}
		public void writeChat(ActiveArea mv, String msg) {
			getChatPane().append(mv,msg);
			
		}
		public void clearInputText() {
			getChatInput().setText("");
			
		}
		public boolean ownsFocus() {
			return getChatInput().isFocusOwner();
		}
		public String getInput() {
			return getChatInputText();
		}
		
	}
	


	Rectangle getRectangle();

	void setLocation(int x, int y);

	int getX();

	int getY();

	int[] getVel();

	void setMot(dir up);



	void unsetMot(axis y);

	void attemptAttack();

	void switchweapon();

	void switcharmor();

	void performAction();

	void use(char c);



	void setLvlUnlocked(int i);

	void chgready();


	ArrayList<Item> getItems();

	int getGold();

	ArrayList<Item> getSlots();

	int getLvlUnlocked();

	void tell(ActiveArea player, String chatInputText);

	Component getSprite();

	void setMap(Map map);
	
	
	
	
	void setChatterBox(Chat chatp, JTextField chatinput);

	

	ChatterBox getChatterBox();

	void setLevel(Level level);



}
