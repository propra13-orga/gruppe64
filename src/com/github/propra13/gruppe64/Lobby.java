package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;




public class Lobby implements ActionListener{
	private Container cp;
	private Main main;
	JTextField chatinput;
	Chat chat;
	JButton ready,start,back;
	JTable table;
	NPlayer player;
	private PlayerTable tableModel;
	public NGame nGame;

	
	private class myJButton extends JButton{
		myJButton(String label){
			super(label);
			addActionListener((ActionListener) Lobby.this);
		}
	}

	public Lobby(Container cp, Main main, NGame nGame, boolean serverOwner) {
		this.cp=cp;
		this.main =main;
		this.nGame=nGame;
		cp.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		


		tableModel =new PlayerTable(nGame.playerList);
		table = new JTable(tableModel);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridheight = 3;
		c.gridx = 0;
		c.gridy = 0;
		cp.add( new JScrollPane(table), c);
		
		chat = new Chat(player);
		chat.setPreferredSize(new Dimension(700,500));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth=2;
		cp.add(chat, c);

		chatinput = new JTextField(60);
		chatinput.addKeyListener(main.controller);
		chatinput.setToolTipText("chatinput");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth=2;
		cp.add(chatinput, c);
		
		
		ready = new myJButton("ready");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=1;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		cp.add(ready, c);

		start = new myJButton("start");
		start.setEnabled(false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.0;
		c.gridx = 2;
		c.gridy = 2;
		cp.add(start, c);

		back = new myJButton("back");
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 2;       //aligned with button 2
		c.gridwidth = 2; 
		c.gridy = 3;       //third row
		cp.add(back, c);
		main.pack();
		// normaly first one

		
	}
	
	public void addPl(NPlayer pl){
		nGame.playerList.add(pl);	
		nGame.correctNicks();
		tableModel.fireTableDataChanged();
	}
	
	public void addlocalPl(NPlayer pl){
		if(player==null){
			this.player=pl;
			player.addChatPane(chat);
			player.addChatInput(chatinput);
			main.controller.setPlayer(pl);
			nGame.correctNicks();
			tableModel.fireTableDataChanged();
		}
	}
	
	
	public void updateTable(){
		tableModel.playerList=nGame.playerList;
		tableModel.fireTableDataChanged();tableModel.fireTableStructureChanged();
		if(player.getGame().serverOwner){
			boolean allready=true;
			if(nGame.playerList.size()==0) allready=false;
			for(NPlayer pl:nGame.playerList)
				if(!pl.isReady())allready=false;
			start.setEnabled(allready);
		}
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==this.ready){
			player.chgready();
		}
		if(ae.getSource()==this.start){
			//TODO start new NGame or something
			ArrayList<NPlayer> readyPlayer=new ArrayList<NPlayer>();
			for (NPlayer pl: nGame.playerList){
				if(pl.isReady())readyPlayer.add(pl);
			}
			System.exit(0);
		}
		if(ae.getSource()==this.back){
			main.initMain();
		}
		
	}
}
