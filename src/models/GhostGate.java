package models;

import java.awt.Color;

import javax.swing.border.MatteBorder;


public class GhostGate extends MappedObjects {
	private static final long serialVersionUID = 1989L;


	public GhostGate() {
		super(null);
		setOpaque(false);
		setBorder(new MatteBorder(5, 0, 5, 0, new Color(254, 255, 143)));
	}

	@Override
	public boolean isTouching(FigureObject object) {
		if (object instanceof Ghost) {
			return true;
		}
		else
			return false;
	}
}
