package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class Chat extends JScrollPane{
	private Player player;
	private JTextArea textArea=new JTextArea("Chat initialized...\n");

	public Chat(Player pl) {
		super();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		//textArea.setLayout(null);
		textArea.setBounds(5,5,650,90);
		textArea.setEditable(false);
		textArea.setVisible(true);
		player=pl;
		this.setLayout(null);
		this.setPreferredSize(new Dimension(680,90));
		this.setBounds(0, 500,700,100);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setVisible(true);
		this.add(textArea);
		revalidate();
		repaint();
	}
	
	public void append(String name, String text){
		this.textArea.append("["+name+"] "+text+"\n");
		revalidate();
		repaint();
	}
	

}
