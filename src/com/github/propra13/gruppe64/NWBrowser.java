package com.github.propra13.gruppe64;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NWBrowser extends JPanel implements ActionListener {
	private Container cp;
	private Main main;
	JTextField nickname, svrname,ip;
	JButton join,create,back;
	
	private class myJButton extends JButton{
		myJButton(String label){
			super(label);
			addActionListener((ActionListener) NWBrowser.this);
		}
	}

	public NWBrowser(Container cp, Main main) {
		this.cp=cp;
		this.main =main;
		
		cp.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();


		nickname = new JTextField("nickname",12);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		cp.add(nickname, c);

		ip = new JTextField("192.168.1.1",15);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		cp.add(ip, c);
		
		svrname = new JTextField("Hostname",20);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		cp.add(svrname, c);

		join = new myJButton("join");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		cp.add(join, c);

		create = new myJButton("create");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.0;
		c.gridx = 1;
		c.gridy = 2;
		cp.add(create, c);

		back = new myJButton("back");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 3;       //third row
		cp.add(back, c);
		main.pack();
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==this.join){
			System.exit(0);
		}
		if(ae.getSource()==this.create){
			System.exit(0);
		}
		if(ae.getSource()==this.back){
			main.initMain();
		}
		
	}
}
