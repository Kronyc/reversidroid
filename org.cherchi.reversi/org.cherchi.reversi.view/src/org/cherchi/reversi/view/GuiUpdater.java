package org.cherchi.reversi.view;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * Updates the Gui. It is a runnable to be able to set it from another thread
 * @author Fernando Cherchi
 *
 */
public class GuiUpdater implements Runnable {

	
	private int score1;
	private int score2;
	private Activity view;

	/**
	 * Constructs the updater
	 * @param score1
	 * @param score2
	 * @param view
	 */
	public GuiUpdater (int score1, int score2, Activity view) {
		
		this.score1 = score1;
		this.score2 = score2;
		this.view = view;
	}
	
	/**
	 * The run method
	 */
	@Override
	public void run() {
		this.setPlayersCounters(score1, score2);
		
		GameBoard board = (GameBoard) this.view.findViewById(R.id.gameBoard);
		board.drawPositions();
		board.invalidate();
	}

	/**
	 * Draws the scores
	 */
	private void setPlayersCounters(int p1Score, int p2Score) {

		TextView txtP1 = (TextView) this.view.findViewById(R.id.txtPlayer1Counter);
		txtP1.setText(String.format(" %d", p1Score));
		TextView txtP2 = (TextView) this.view.findViewById(R.id.txtPlayer2Counter);
		txtP2.setText(String.format(" %d", p2Score));
		
	}
}
