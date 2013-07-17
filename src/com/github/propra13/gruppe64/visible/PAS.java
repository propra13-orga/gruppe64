package com.github.propra13.gruppe64.visible;

import javax.swing.JPanel;

/**
 * ProcessAble Sprite
 * ein Sprite, der mit einer Beschreibungsfunktion kommt, die
 * von MapGenerator in mehreren Schritten verarbeitet werden kann und 
 * leichter menschenlesbar ist als JSON
 * @author vad
 *
 */
public interface PAS {
	/**
	 * Dialogfenster zum Einrichten eines PAS im MapEditor
	 * @return JPanel mit Action Listener
	 */
	public JPanel getSetupDialog();
	/**
	 * Dieser String-Array speichert, dass Textmuster der einzelnen PAS:
	 * CharName:Klasse:regExp1:regExp2:...
	 */
	public static String[] processAbleSprites = {
		"D:Door:D,[0-9]*,[0-9]*:D,[a-z]*,[0-9]*"
	};
}
