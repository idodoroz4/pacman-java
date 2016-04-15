package models;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.Timer;


public class Animation extends JLabel {
	private static final long serialVersionUID = 1996L;
	private static final int BLOCK_SIZE = 24;
	private Timer _animationTimer;
	private int _fps = 25;
	private BufferedImage _animation = null;
	private int _animationX;
	private int _animationAngle;


	public Animation() {

		_animationTimer = new Timer(1000 / _fps, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_animationX = (_animationX + BLOCK_SIZE) % _animation.getWidth();
			}
		});
	}


	public void startAnimation() {
		_animationTimer.start();
	}


	public void stopAnimation() {
		_animationTimer.stop();
	}

	protected void setFPS(int fps) {
		_fps = fps;
		_animationTimer.setDelay(1000 / _fps);
	}

	protected void setImage(BufferedImage sprite) {
		_animation = sprite;
		_animationX = 0;
	}
	
	/**
	 * Get the current sprite image
	 * @return
	 */
	protected BufferedImage getImage() {
		return _animation;
	}

	@Override
	protected void paintComponent(Graphics g) {
		setSize(BLOCK_SIZE, BLOCK_SIZE);

		BufferedImage currentFrame = _animation.getSubimage(_animationX, 0, BLOCK_SIZE, BLOCK_SIZE);

		Graphics2D g2d = (Graphics2D)g;
		AffineTransform trans = new AffineTransform();
		trans.rotate(Math.toRadians(_animationAngle), currentFrame.getWidth() / 2, currentFrame.getHeight() / 2);

		g2d.drawImage(currentFrame, trans, this);
	}


	protected void setAngle(int spriteAngle) {
		_animationAngle = spriteAngle;
	}
}
