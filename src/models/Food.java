package models;

import javax.swing.ImageIcon;

import controller.Controller;
import views.utils.Assets;

public class Food extends MappedObjects {
	private static final long serialVersionUID = 1992L;

	public Food (String imageName){
		super(new ImageIcon(Assets.getResource(Food.class, imageName)));
	}
	public Food() {
		super(new ImageIcon(Assets.getResource(Food.class, "img.png")));
	}

	public Food (ImageIcon icon){
		super(icon);
	}

	public void TouchingFigure(Pacman pacman, Controller engine) {
		engine.eatFood(pacman, this);
	}

	public int getFoodType () { return 0;}
}
