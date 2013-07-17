package com.github.propra13.gruppe64;

import java.io.Serializable;

public interface MessageCallbacks {
	public class AutoMessage implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6464091768546185681L;
		AMH header;
		NPlayer sPlayer;
		public AutoMessage(AMH header, NPlayer senderPlayer){
			this.header=header;
			this.sPlayer=senderPlayer;
		}
		void call(NPlayer rPlayer){
			switch(header){
			case setMot: rPlayer.setVel(sPlayer.getVel());
			break;
			case attack:
				break;
			case damage:
				break;
			case setLocation:
				break;
			case setMap: 	
				if(rPlayer.nGame.getCP()==null)rPlayer.nGame.initGamefield();
				rPlayer.nGame.startMapProcessor(sPlayer.map, rPlayer);
				break;
			default:
					System.out.println("wrong Header?!");
				break;
			}
		}
		
	}
	enum AMH{setMap, setLocation, damage, attack, setMot};
}
