package org.newdawn.spaceinvaders;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Davide Pastore
 *
 */
/**
 * A class to handle keyboard input from the user. The class
 * handles both dynamic input during game play, i.e. left/right 
 * and shoot, and more static type input (i.e. press any key to
 * continue)
 * 
 * This has been implemented as an inner class more through 
 * habbit then anything else. Its perfectly normal to implement
 * this as seperate class if slight less convienient.
 * 
 * @author Kevin Glass
 */
public class KeyInputHandler extends KeyAdapter {
	/** The number of key presses we've had while waiting for an "any key" press */
	private int pressCount = 1;
	
	private Game game;
	
	/**
	 * Construct the instance of the input handler with the {@link Game} instance.
	 * @param game The {@link Game} instance.
	 */
	public KeyInputHandler(Game game) {
		this.game = game;
	}

	/**
	 * Notification from AWT that a key has been pressed. Note that
	 * a key being pressed is equal to being pushed down but *NOT*
	 * released. Thats where keyTyped() comes in.
	 *
	 * @param e The details of the key that was pressed 
	 */
	public void keyPressed(KeyEvent e) {
		// if we're waiting for an "any key" typed then we don't 
		// want to do anything with just a "press"
		if (game.isWaitingForKeyPress()) {
			return;
		}
		
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			game.setLeftPressed(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			game.setRightPressed(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			game.setFirePressed(true);
		}
		
	} 
	
	/**
	 * Notification from AWT that a key has been released.
	 *
	 * @param e The details of the key that was released 
	 */
	public void keyReleased(KeyEvent e) {
		// if we're waiting for an "any key" typed then we don't 
		// want to do anything with just a "released"
		if (game.isWaitingForKeyPress()) {
			return;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			game.setLeftPressed(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			game.setRightPressed(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			game.setFirePressed(false);
		}
	}

	/**
	 * Notification from AWT that a key has been typed. Note that
	 * typing a key means to both press and then release it.
	 *
	 * @param e The details of the key that was typed. 
	 */
	public void keyTyped(KeyEvent e) {
		// if we're waiting for a "any key" type then
		// check if we've recieved any recently. We may
		// have had a keyType() event from the user releasing
		// the shoot or move keys, hence the use of the "pressCount"
		// counter.
		if (game.isWaitingForKeyPress()) {
			if (pressCount == 1) {
				// since we've now recieved our key typed
				// event we can mark it as such and start 
				// our new game
				game.setWaitingForKeyPress(false);
				game.startGame();
				pressCount = 0;
			} else {
				pressCount++;
			}
		}
		
		// if we hit escape, then quit the game
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}

		if(e.getKeyChar() == (char) 80 || e.getKeyChar() ==  112){
			game.setPausedGame(!game.isPausedGame());
		}
	}
}
