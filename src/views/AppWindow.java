package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class AppWindow extends JFrame {
	private static final long serialVersionUID = 2000;

	public AppWindow() {
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initializeUI();
	}


	public void setWindowInScreenCenter() {
		Dimension size = getToolkit().getScreenSize();
		this.setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
	}

	private void initializeUI() {

		Container c = getContentPane();
		c.setLayout(new BorderLayout());


	}

	public void showView(JPanel view) {

		Container c = getContentPane();
		for (Component comp : c.getComponents()) {
			c.remove(comp);
		}


		c.add(view, BorderLayout.CENTER);
		view.updateUI();


		view.setVisible(true);
		pack();
		repaint();
	}
}
