package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import views.utils.Assets;

public class Pacman extends FigureObject implements IFigureElementVisitor{
	private static final long serialVersionUID = 1992L;

	private List _listeners = new ArrayList();
	private Movement _currMove = Movement.RIGHT;

	public  Pacman(GameMap gameMap) {
		super(gameMap);
		setPosition(gameMap.getPacmanInitialPosition().x, gameMap.getPacmanInitialPosition().y);
		setDirection(Movement.NONE);
	}
	public Pacman (GameMap gameMap, Pacman p){
		super(gameMap);
	}

	public synchronized void addListener (IPositionListener listener){
		_listeners.add(listener);
	}
	public synchronized void removeListener (IPositionListener listener){
		_listeners.remove(listener);
	}

	public synchronized void _firePositionEvent () {
		PacmanPositionEvent ppe = new PacmanPositionEvent(this,this.getPosition());
		Iterator listeners = _listeners.iterator();
		while (listeners.hasNext()){
			( (IPositionListener) listeners.next()).positionReceived(ppe);
		}
	}
	
	@Override
	public void setDirection(Movement movement) {

		switch(movement) {
		case DOWN:
			if (_currMove == Movement.RIGHT)
				setAngle(90);
			else if (_currMove == Movement.LEFT)
				setAngle(270);
			startAnimation();
			break;
		case LEFT:
			try {
				setImage(ImageIO.read(Assets.getResource(this.getClass(), "pacman-left.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			_currMove = Movement.LEFT;
			setAngle(0);
			startAnimation();
			break;
		case NONE:
			if (getImage() == null) {
				setDefaultIcon();
			}
			stopAnimation();
			break;
		case RIGHT:
			try {
				setImage(ImageIO.read(Assets.getResource(this.getClass(), "pacman-right.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			_currMove = Movement.RIGHT;
			setAngle(0);
			startAnimation();
			break;
		case UP:
			if (_currMove == Movement.RIGHT)
				setAngle(270);
			else if (_currMove == Movement.LEFT)
				setAngle(90);
			startAnimation();
			break;
		default:
			break;
		
		}

		super.setDirection(movement);

	}

	public void setDefaultIcon() {
		try {
			// default image
			setImage(ImageIO.read(Assets.getResource(this.getClass(), "pacman-right.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void moveRandomly() {

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
		super.move();
	}

	@Override
	public boolean eats (WeakGhost ghost){
		return false;
	}
	@Override
	public boolean eats (StrongGhost ghost){
		return false;
	}
}
