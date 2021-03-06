package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class Chat extends JScrollPane{
	/**
	 * 
	 */
	private static final long serialVersionUID = 899637230571821011L;
	private Player player;
	private JTextArea textArea=new JTextArea();

	public Chat(Player pl) {
		super();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		//textArea.setLayout(null);
		//textArea.setBounds(5,5,650,90);
		textArea.setPreferredSize(null);
		textArea.setEditable(false);
		textArea.setVisible(true);
		textArea.setEnabled(true);
		player=pl;
		//this.setLayout(null);
		this.setPreferredSize(new Dimension(700,90));
		//this.setBounds(0, 500,700,100);
		//this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setEnabled(true);
		this.setVisible(true);
		this.setViewportView(textArea);
	}
	

	public void append(ActiveArea mv, String text){
		this.textArea.append("["+mv.getNick()+"] "+text+"\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
		revalidate();
		repaint();
	}

	public void append(String text){
		this.textArea.append("=="+text+"==\n");
		revalidate();
		repaint();
	}
	public void clearText(){
		this.textArea.setText("");
	}
	

}
