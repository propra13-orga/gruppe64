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
	private ArrayList<NPlayer> playerList;
	NPlayer player;
	String[][] data = new String[8][2];
	private PlayerTable tableModel;

	
	private class myJButton extends JButton{
		myJButton(String label){
			super(label);
			addActionListener((ActionListener) Lobby.this);
		}
	}

	public Lobby(Container cp, Main main, NPlayer player) {
		this.cp=cp;
		this.main =main;
		playerList=new ArrayList<NPlayer>();
		
		cp.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		


		tableModel =new PlayerTable(playerList);
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
	
		addLocalPl(player);
		//Testing !! Server Edition!!
				for(int i=0;i<3;i++) {
					addPl(new NPlayer(5,150));

				}
		
	}
	
	public void addPl(NPlayer pl){
		playerList.add(pl);	
		correctNicks();
		tableModel.fireTableDataChanged();
	}
	
	public void addLocalPl(NPlayer pl){
		if(player==null){
			this.player=pl;
			player.addChatPane(chat);
			player.addChatInput(chatinput);
			main.controller.setPlayer(player);
			playerList.add(pl);	
			correctNicks();
			tableModel.fireTableDataChanged();
		}
	}
	public void correctNicks(){
		
		for(Player pl:playerList){
			Integer i = new Integer(0);
			for(Player pls:playerList){
				if(!pl.equals(pls))
					if(pl.getNick().equals(pls.getNick())){
						for(Player pl2:playerList)
							if(!pl.equals(pl2) && pl2.getNick().equals(pls.getNick()+"("+i.toString()+")"))i++;
						pls.setNick(pls.getNick()+"("+(i++).toString()+")");
					}	
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==this.ready){
			if(player.isReady()){				
				ready.setText("ready");
				player.setReadyState(false); tableModel.fireTableDataChanged();
			}else{
				ready.setText("unready");
				player.setReadyState(true); tableModel.fireTableDataChanged();
			}
		}
		if(ae.getSource()==this.start){
			//TODO start new NGame or something
			ArrayList<NPlayer> readyPlayer=new ArrayList<NPlayer>();
			for (NPlayer pl: playerList){
				if(pl.isReady())readyPlayer.add(pl);
			}
			System.exit(0);
		}
		if(ae.getSource()==this.back){
			main.initMain();
		}
		
	}
}
