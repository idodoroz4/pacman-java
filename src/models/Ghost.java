package models;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import views.utils.Assets;

public abstract class Ghost extends FigureObject {
	private static final long serialVersionUID = 1991L;
	BufferedImage _image;
	private int _color;

	public Ghost(Map map, int color) {
		super(map);
		_color = color;
		setFPS(5);
		startGhost();
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
	}





	@Override
	public void move() {
		if (isCageUp())
			setDirection(Movement.UP);

		if ((isStep() && getDirection() == Movement.NONE)  || isIntersection()) {


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
}
