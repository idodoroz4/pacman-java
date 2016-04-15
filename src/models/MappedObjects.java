package models;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controller.Controller;

public class MappedObjects extends JLabel {
	private static final long serialVersionUID = 1997L;
	public static final Dimension BLOCK_SIZE = new Dimension(24,24);
	
	public MappedObjects(ImageIcon icon) {
		setIcon(icon);
		setPreferredSize(BLOCK_SIZE);
	}

	public void TouchingFigure(Pacman pacman, Controller engine) { }

	public boolean isTouching(FigureObject object) {
		return true;
	}
}
