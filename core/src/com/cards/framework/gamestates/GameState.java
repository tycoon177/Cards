package com.cards.framework.gamestates;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.cards.framework.CardGame;
import com.cards.framework.entities.GamePiece;
import com.cards.framework.managers.GameStateManager;

/**
 * GameState offers an easy, quick, and efficient way to handle multiple screens
 * within the game without problems of memory management or the like.
 *
 */
public abstract class GameState {
	private final GameStateManager gameStateManager;
	private static int nextZ = 0;
	private ArrayList<GamePiece> entities;
	private boolean touched = false;
	private boolean held = true, holding;
	private float timeHeld = 0;
	private final float timeToHold = .25f;

	/**
	 * Creates a GameState object that saves its GameStateManager
	 * 
	 * @param gsm
	 */
	public GameState(GameStateManager gsm) {
		this.gameStateManager = gsm;
		entities = new ArrayList<GamePiece>();
		init();
	}

	/**
	 * Initializes the game state
	 */
	public abstract void init();

	/**
	 * Handles the user input and acts accordingly
	 */
	public abstract void handleInput(float deltaTime);

	/**
	 * Updates the game logic based on deltaTime
	 * 
	 * @param deltaTime
	 */
	public abstract void update(float deltaTime);

	/**
	 * Draws the Game State
	 */
	public abstract void draw(SpriteBatch batch);

	/**
	 * Disposes of all of the used objects
	 */
	public abstract void dispose();

	/**
	 * returns the gamestatemanager
	 * 
	 * @return
	 */
	public GameStateManager getGameStateManager() {
		return gameStateManager;
	}

	/**
	 * Returns and increments the next z axis
	 * 
	 * @return
	 */
	public static int getNextZ() {
		return nextZ++;
	}
	
	public static void resetNextZ(){
		nextZ = 0;
	}

	/**
	 * Returns the entity that is on top at a given location
	 * 
	 * @param vector
	 * @return
	 */
	public GamePiece getTopEntityAtPosition(Vector3 vector) {
		Collections.sort(entities);
		for (int i = entities.size() - 1; i >= 0; i--) {
			if (entities.get(i).containsPoint(vector))
				return entities.get(i);
		}
		return null;
	}

	/**
	 * adds an entity to the gamestate
	 * 
	 * @param piece
	 */
	public void addEntity(GamePiece piece) {
		entities.add(piece);
	}

	/**
	 * Returns the arraylist of entities
	 * 
	 * @return
	 */
	public ArrayList<GamePiece> getEntities() {
		Collections.sort(entities);
		return entities;
	}

	/**
	 * Returns true only on the first loop of the mouse being held.
	 * 
	 * @return
	 */
	public boolean isJustTouched() {
		if (Gdx.input.isTouched() && touched)
			return false;
		touched = Gdx.input.isTouched();
		return touched;
	}

	public boolean isHeld(float deltaTime) {
		held = holding;
		holding = Gdx.input.isTouched();
		if (holding && held) {
			this.timeHeld += deltaTime;
			if (timeHeld >= timeToHold)
				Gdx.app.log("Hold", "Held");
			else
				Gdx.app.log("Hold", "Preparing to hold");
			return timeHeld >= timeToHold;
		}
		Gdx.app.log("Hold", "Not held");
		timeHeld = 0;
		return false;
	}

	/**
	 * Returns the mouse position in a vector2 object. This coordinate system is
	 * converted to the same coordinate plane as the drawn entities
	 * 
	 * @return
	 */
	public Vector3 getMousePosition() {
		return CardGame.camera.unproject(new Vector3(Gdx.input.getX(),
				Gdx.input.getY(), 0));
	}

}
