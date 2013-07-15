package com.github.propra13.gruppe64;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

class PlayerTable extends AbstractTableModel {
	
	String[] columnNames = {"nickname","state"};
	String[] stateMsg={"not ready", "ready"}; 
	

	ArrayList<NPlayer> playerList;
	
    public PlayerTable(ArrayList<NPlayer> playerList){
    	super();
    	this.playerList=playerList;
    }
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return playerList.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
    	switch (col){
    	case 0: return playerList.get(row).getNick(); 
    	case 1: if(playerList.get(row).isReady())return stateMsg[1]; return stateMsg[0];
    	default: return null;
    	}
   
    }

    public Class getColumnClass(int c) {
        return String.class;//getValueAt(0, c).getClass();
    }

    

    /*
     * TODO sinnvoller
     */
    public void setValueAt(Object value, int row, int col) {
        switch(col){
        case 0:playerList.set(row, (NPlayer)value); break;
        
        }
        fireTableCellUpdated(row, col);
    }
   
    public void addPlayer(NPlayer pl, String readyStateS){
    	playerList.add(pl);
    	this.fireTableDataChanged();
    }
  
 
}
