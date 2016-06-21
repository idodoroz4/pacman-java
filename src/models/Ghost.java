package models;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import views.utils.Assets;

public abstract class Ghost extends FigureObject implements IFigureElement {
	private static final long serialVersionUID = 1991L;
	BufferedImage _image;
	private int _color;
	private int _intervalTime;
	private long _cageTime;
	private boolean isOutOfTheCage;
	private Point oldPos;
	private boolean hasBeenEaten;
	public long _TimeOfDeath;

	public Ghost(GameMap gameMap, int color) {
		super(gameMap);
		_color = color;
		setFPS(5);
		_intervalTime = 5000; // 5 seconds
		isOutOfTheCage = false;
		startGhost();
		oldPos = null;
		hasBeenEaten = false;
		_TimeOfDeath = 0;

	}

	public void startGhost() {


		try {
			_image = ImageIO.read(Assets.getResource(Ghost.class, (_color + ".png")));
			setImage(_image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setPosition(getCollisionMap().getMonsterInitialPosition().x, getCollisionMap().getMonsterInitialPosition().y);

		setDirection(Movement.RIGHT);
		setCageTime();

	}

	public void set_isOutOfCage(boolean b){ isOutOfTheCage = b;}
	public boolean get_isOutOfCage(){ return isOutOfTheCage; }

	@Override
	public void move() {
		if (isCageUp())
			setDirection(Movement.UP);
		if (this instanceof WeakGhost)   {
			if ((isStep() && getDirection() == Movement.NONE) || isIntersection()) {


				int rnd = (int) (Math.random() * 4 + 1);
				switch (rnd) {
					case 1:
						setDirection(Movement.UP);
						break;
					case 2:
						setDirection(Movement.DOWN);
						break;
					case 3:
						setDirection(Movement.LEFT);
						break;
					case 4:
						setDirection(Movement.RIGHT);
						break;
				}

			}
		}

		if (((getPosition().x == 14) || (getPosition().x == 13)) && (getPosition().y == 10))
			this.set_isOutOfCage(true);

		super.move();
	}

	public void setCageTime (){
		_cageTime = System.currentTimeMillis() + _intervalTime;
	}

	public boolean isCageOpen (){
		if (System.currentTimeMillis() > _cageTime)
				return true;
		return false;
	}

	public Point getOldPos () {return oldPos; }
	public void setOldPos (Point p){ oldPos = p;}

	public boolean get_hasBeenEaten (){ return hasBeenEaten;}
	public void set_hasBeenEaten (boolean b) { hasBeenEaten = b; }
}
