package com.github.propra13.gruppe64;


import java.awt.GridBagConstraints;

public class myGBC extends GridBagConstraints{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public myGBC(int gridx, int gridy){
			super();
			this.gridx = gridx;
			this.gridy = gridy;
			this.fill = GridBagConstraints.HORIZONTAL;
		}
		public myGBC(int gridx, int gridy, int gridwidth, double weightx){
			this(gridx,gridy);
			this.gridwidth = gridwidth;
			this.weightx = weightx;
		}
	}