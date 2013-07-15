package com.github.propra13.gruppe64;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JTextField;

import com.github.propra13.gruppe64.Movable.axis;
import com.github.propra13.gruppe64.Movable.dir;

public interface Player {

	Level getLevel();

	Game getGame();

	void addStatBar(StatBar statBar);

	void addChatPane(Chat chatp);

	void addChatInput(JTextField chatinput);

	void setLevel(Level aLevel);

	Rectangle getRectangle();

	void tell(Movable mv, String msg);

	void setLocation(int x, int y);

	int getX();

	int getY();

	int[] getVel();

	void setMot(dir up);



	void unsetMot(axis y);

	void attemptAttack();

	boolean chatIsFocusOwner();

	String getChatInputText();

	void switchweapon();

	void switcharmor();

	void setChatInputText(String string);

	void performAction();

	void use(char c);



	void setLvlUnlocked(int i);

	String getNick();

	void chgready();

	void tell(String string);

	void setNick(String string);

	ArrayList<Item> getItems();

	int getGold();

	ArrayList<Item> getSlots();

	int getLvlUnlocked();


}
