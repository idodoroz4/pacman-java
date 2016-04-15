package main;

import javax.swing.SwingUtilities;

import controller.Controller;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Controller());
	}

}
