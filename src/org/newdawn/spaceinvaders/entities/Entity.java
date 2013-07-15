package org.newdawn.spaceinvaders.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.spaceinvaders.sprite.Sprite;
import org.newdawn.spaceinvaders.sprite.SpriteStore;

/**
 * An entity represents any element that appears in the game. The
 * entity is responsible for resolving collisions and movement
 * based on a set of properties defined either by subclass or externally.
 * 
 * Note that doubles are used for positions. This may seem strange
 * given that pixels locations are integers. However, using double means
 * that an entity can move a partial pixel. It doesn't of course mean that
 * they will be display half way through a pixel but allows us not lose
 * accuracy as we move.
 * 
 * @author Kevin Glass
 * @author Davide Pastore
 */
public abstract class Entity {
	/** The current x location of this entity */ 
	protected double x;
	/** The current y location of this entity */
	protected double y;
	/** The sprite that represents this entity */
	protected ArrayList<Sprite> sprite = new ArrayList<Sprite>();
	/** The sprite index */
	private int spriteIndex = 0;
	/** The current speed of this entity horizontally (pixels/sec) */
	protected double dx;
	/** The current speed of this entity vertically (pixels/sec) */
	protected double dy;
	/** The rectangle used for this entity during collisions  resolution */
	private Rectangle me = new Rectangle();
	/** The rectangle used for other entities during collision resolution */
	private Rectangle him = new Rectangle();
	/** The number of milliseconds to display each sprite */
	private int timeSpriteChange = 1000;
	/** The number of milliseconds (time spent) */
	private long timeSpent = System.currentTimeMillis();
	
	/**
	 * Construct a entity based on a sprite image and a location.
	 * 
	 * @param ref The reference to the image to be displayed for this entity
 	 * @param x The initial x location of this entity
	 * @param y The initial y location of this entity
	 */
	public Entity(String ref,int x,int y) {
		this.sprite.add(SpriteStore.get().getSprite(ref));
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Construct a entity based on a list of sprite images and a location.
	 * 
	 * @param ref The reference to the images to be displayed for this entity
 	 * @param x The initial x location of this entity
	 * @param y The initial y location of this entity
	 */
	public Entity(String[] ref, int x, int y) {
		for(int i = 0; i < ref.length; i++){
			this.sprite.add(SpriteStore.get().getSprite(ref[i]));
		}
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Request that this entity move itself based on a certain ammount
	 * of time passing.
	 * 
	 * @param delta The ammount of time that has passed in milliseconds
	 */
	public void move(long delta) {
		// update the location of the entity based on move speeds
		x += (delta * dx) / 1000;
		y += (delta * dy) / 1000;
	}
	
	/**
	 * Set the horizontal speed of this entity
	 * 
	 * @param dx The horizontal speed of this entity (pixels/sec)
	 */
	public void setHorizontalMovement(double dx) {
		this.dx = dx;
	}

	/**
	 * Set the vertical speed of this entity
	 * 
	 * @param dx The vertical speed of this entity (pixels/sec)
	 */
	public void setVerticalMovement(double dy) {
		this.dy = dy;
	}
	
	/**
	 * Get the horizontal speed of this entity
	 * 
	 * @return The horizontal speed of this entity (pixels/sec)
	 */
	public double getHorizontalMovement() {
		return dx;
	}

	/**
	 * Get the vertical speed of this entity
	 * 
	 * @return The vertical speed of this entity (pixels/sec)
	 */
	public double getVerticalMovement() {
		return dy;
	}
	
	/**
	 * Draw this entity to the graphics context provided
	 * 
	 * @param g The graphics context on which to draw
	 */
	public void draw(Graphics g) {
		if(System.currentTimeMillis() >= timeSpriteChange + timeSpent ){
			timeSpent = System.currentTimeMillis();
			if(spriteIndex + 1 == sprite.size() ){
				spriteIndex = 0;
			}
			else{
				spriteIndex++;
			}
		}
		
		sprite.get(spriteIndex).draw(g,(int) x,(int) y);
	}
	
	/**
	 * Do the logic associated with this entity. This method
	 * will be called periodically based on game events
	 */
	public void doLogic() {
	}
	
	/**
	 * Get the x location of this entity
	 * 
	 * @return The x location of this entity
	 */
	public int getX() {
		return (int) x;
	}

	/**
	 * Get the y location of this entity
	 * 
	 * @return The y location of this entity
	 */
	public int getY() {
		return (int) y;
	}
	
	
	/**
	 * Get the sprite index of this entity
	 * 
	 * @return The sprite index of this entity
	 */
	public int getSpriteIndex(){
		return spriteIndex;
	}
	
	/**
	 * Check if this entity collised with another.
	 * 
	 * @param other The other entity to check collision against
	 * @return True if the entities collide with each other
	 */
	public boolean collidesWith(Entity other) {
		me.setBounds((int) x, (int) y, sprite.get(spriteIndex).getWidth(), sprite.get(spriteIndex).getHeight());
		him.setBounds((int) other.x, (int) other.y, other.sprite.get(other.getSpriteIndex()).getWidth(), other.sprite.get(other.getSpriteIndex()).getHeight());

		return me.intersects(him);
	}
	
	/**
	 * Notification that this entity collided with another.
	 * 
	 * @param other The entity with which this entity collided.
	 */
	public abstract void collidedWith(Entity other);
}