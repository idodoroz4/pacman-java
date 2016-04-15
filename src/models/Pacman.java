package models;

import java.io.IOException;

import javax.imageio.ImageIO;

import views.utils.Assets;

public class Pacman extends FigureObject {
	private static final long serialVersionUID = 1992L;

	private Movement _currMove = Movement.RIGHT;


	public Pacman(Map map) {
		super(map);
		setPosition(map.getPacmanInitialPosition().x, map.getPacmanInitialPosition().y);
		setDirection(Movement.NONE);
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



}
