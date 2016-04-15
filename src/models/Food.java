package models;

import javax.swing.ImageIcon;

import controller.Controller;
import views.utils.Assets;

public class Food extends MappedObjects {
	private static final long serialVersionUID = 1992L;


	public Food() {
		super(new ImageIcon(Assets.getResource(Food.class, "img.png")));
	}

	public void TouchingFigure(Pacman pacman, Controller engine) {
		engine.eatFood(pacman, this);
	}
}
