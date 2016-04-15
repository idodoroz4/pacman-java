package models;

import java.awt.Color;

public class Wall extends MappedObjects {
	private static final long serialVersionUID = 1999L;

	public Wall() {
		super(null);
		setOpaque(true);
		setBackground(new Color(31, 137, 12));
	}

	@Override
	public boolean isTouching(FigureObject object) {
		return false;
	}

}
