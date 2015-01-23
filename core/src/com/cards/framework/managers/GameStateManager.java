package com.cards.framework.managers;

import com.cards.framework.gamestates.GameState;
import com.cards.framework.gamestates.PlayState;

public class GameStateManager {

	// current game state
	public static GameState gameState;

	public static final int MENU = 0;
	public static final int PLAY = 1;

	public GameStateManager(){
		gameState = new PlayState(this);
	}
	
	public void setState(int state) {
		
		if (gameState != null)
			gameState.dispose();
		if (state == MENU) {
			// gameState = new MenuState(this);
		}
		if (state == PLAY) {
			gameState = new PlayState(this);
		}
	}

	public void update(float dt) {
		gameState.update(dt);
	}

	public void draw() {
		gameState.draw();
	}

}
