package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import models.*;
import models.Food;
import views.AppWindow;
import views.GameView;

public class Controller implements Runnable {

	private static final int PACMAN_LIVES = 2;
	private static final int POINT_FOR_FOOD = 1;
	private Timer _gameTimer;
	private static final int FPS = 60;
	private Map _map = Map.getMap();
	private AppWindow _window;
	private GameView _gameView = new GameView(_map);
	private Pacman _pacman;
	private int _remainingLives;
	private List<Ghost> _ghosts = new ArrayList<Ghost>();
	private int _points;
	private int _foodRemaining;

	public Controller() {

		_window = new AppWindow();
		_window.showView(_gameView);
		_window.setWindowInScreenCenter();
		_gameView.addKeyListener(new MovePacmanListener());
		_gameView.setFocusable(true);




		_gameTimer = new Timer(1000 / FPS, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameUpdate();
			}
		});



		startNewGame();
		restartGame();
	}


	public void startNewGame() {

		_gameTimer.stop();



		_remainingLives = PACMAN_LIVES;
		_map = Map.getMap();
		_foodRemaining = _map.getTotalFood();
		_gameView.newGame(_map);



		if (_ghosts.size() == 0) {
			for (int i = 0; i < 4; i++) {

				RegularGhost w = new RegularGhost(_map, i+1);
				if (i == 2 )
					w.setSpeed(2);
				if (i == 3 )
					w.setSpeed(4);


				_ghosts.add(w);
			}



			_gameView.setMonsters(_ghosts);
		} else {
			for (Ghost m : _ghosts) {
				m.startGhost();
			}
		}
	}

	public void restartGame() {

		_pacman = new Pacman(_map);
		_gameView.setPacman(_pacman);
		_pacman.setPosition(_map.getPacmanInitialPosition().x, _map.getPacmanInitialPosition().y);

		for (Ghost m : _ghosts) {

			m.setPosition(_map.getMonsterInitialPosition().x, _map.getMonsterInitialPosition().y);
		}


		_gameTimer.start();
	}


	public void gameUpdate() {

		_pacman.move();


		for (Ghost m : _ghosts){
			m.move();


			if (_pacman.getBounds().intersects(m.getBounds())) {

				_gameTimer.stop();
				_remainingLives--;

				try {
					Thread.sleep(2000);
					if (_remainingLives < 0) {

						startNewGame();
					}


					restartGame();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}


		MappedObjects[][] map = _map.getObjectsMap();
		MappedObjects collidableObject = map[_pacman.getPosition().y][_pacman.getPosition().x];

		if (collidableObject != null && collidableObject.isTouching(_pacman)) {
			map[_pacman.getPosition().y][_pacman.getPosition().x].TouchingFigure(_pacman, this);
		}


		if (_foodRemaining == 0) {

			_gameTimer.stop();
			startNewGame();
			restartGame();
		}
	}

	public void eatFood(Pacman pacman, Food food) {
		food.getParent().remove(food);
		_map.getObjectsMap()[pacman.getPosition().y][pacman.getPosition().x] = null;
		_points += POINT_FOR_FOOD;
		_foodRemaining--;
	}


	@Override
	public void run() {
		_window.setVisible(true);
	}

	class MovePacmanListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				_pacman.setDirection(Movement.UP);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				_pacman.setDirection(Movement.DOWN);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				_pacman.setDirection(Movement.RIGHT);
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				_pacman.setDirection(Movement.LEFT);
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				_pacman.setDirection(Movement.NONE);
			}



		}
	}
}
