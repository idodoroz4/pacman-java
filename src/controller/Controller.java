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
import views.LoginView;

public class Controller implements Runnable {

	private static final int PACMAN_LIVES = 2;
	private static final int POINT_FOR_FOOD = 1;
	private Timer _gameTimer;
	private static final int FPS = 60;
	private Map _map = Map.getMap();
	private AppWindow _window;
    private AppWindow _loginWindow;
	private GameView _gameView = new GameView(_map);
    private LoginView _loginView;
	private Pacman _pacman;
	private int _remainingLives;
	private List<Ghost> _ghosts = new ArrayList<Ghost>();
	private int _points;
	private int _foodRemaining;
	private String _userEmail;
	private byte[] _userPass;

	private int _difficulty; // 0 - easy , 1 - hard
	private boolean _isPacmanAI;

    private long _firstDeathTime;
    private long _firstDeathTimeStart;
    private long _firstDeathTimeEnd;

    private long _completeDeathTime;
    private long _completeDeathTimeStart;
    private long _completeDeathTimeEnd;

    private API db;
    private Encryption enc;

	public Controller() {

        db = new API();
        enc = new Encryption();

        startLoginWindow();
		_isPacmanAI = false;

	}

    private void startLoginWindow (){
        _loginView = new LoginView();


       while (!_loginView._startGame){
            try {
                Thread.sleep(1000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        if (_loginView._isDemo) {

            _isPacmanAI = true;
        }
        else {
            _userEmail = _loginView._email;
			_userPass = enc.encrypt(_loginView._pass);
            _difficulty = _loginView._difficultyChosen;
        }
        _window = new AppWindow();
        _window.showView(_gameView);
        _window.setWindowInScreenCenter();

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


		int num_of_ghosts = 4;
		if (!_isPacmanAI)
			_gameView.addKeyListener(new MovePacmanListener());
        else
            _difficulty = 1;



		if (_difficulty == 0)
			num_of_ghosts = 2;
		if (_ghosts.size() == 0) {
			for (int i = 0; i < num_of_ghosts; i++) {

				RegularGhost w = new RegularGhost(_map, i+1);
				if (i == 0 )
					w.setSpeed(2);
				if (i == 1 )
					w.setSpeed(4);


				_ghosts.add(w);
			}
			_gameView.setMonsters(_ghosts);
		} else {
			for (Ghost m : _ghosts) {
				m.startGhost();
			}
		}
        if (_remainingLives == PACMAN_LIVES && !_isPacmanAI) {
            _firstDeathTimeStart = System.currentTimeMillis();
            _completeDeathTimeStart = System.currentTimeMillis();
        }
	}

    public int getScore (){
        return (_remainingLives + 1) * 20 + (_map.getTotalFood() - _foodRemaining);
    }
    public long get_firstDeathTime(){
        return _firstDeathTime;
    }
    public long get_completeDeathTime(){
        return _completeDeathTime;
    }

	public void restartGame() {

		_pacman = new Pacman(_map);
		_gameView.setPacman(_pacman);
		_pacman.setPosition(_map.getPacmanInitialPosition().x, _map.getPacmanInitialPosition().y);

		for (Ghost m : _ghosts) {

			m.setPosition(_map.getMonsterInitialPosition().x, _map.getMonsterInitialPosition().y);
			m.setCageTime();
		}
		if (_loginView._isDemo) {
            _pacman.setSpeed(3);
            _pacman.setDirection(Movement.RIGHT);
        }

		_gameTimer.start();
	}


	public void gameUpdate() {

		if (_loginView._isDemo) {
			_pacman.moveRandomly();
		}
		else
			_pacman.move();


		for (Ghost m : _ghosts){
			m.move();


			if (_pacman.getBounds().intersects(m.getBounds())) {
                if (_remainingLives == PACMAN_LIVES && !_isPacmanAI) {
                    _firstDeathTimeEnd = System.currentTimeMillis();
                    _firstDeathTime = (_firstDeathTimeEnd - _firstDeathTimeStart) / 1000;
                }
				_gameTimer.stop();
				_remainingLives--;


				try {
					Thread.sleep(2000);
					if (_remainingLives < 0) { // pacman die
                        if (!_isPacmanAI) {
                            _completeDeathTimeEnd = System.currentTimeMillis();
                            _completeDeathTime = (_completeDeathTimeEnd - _completeDeathTimeStart) / 1000;
                            db.sendEndOfGameStatistics(_userEmail,enc.decrypt(_userPass),getScore(),(int)_firstDeathTime,(int)_completeDeathTime);
                        }
						else {
							db.sendEndOfGameStatistics("computer@computer.computer","retupmoc",0,0,0);
						}
                        System.out.println(getScore());
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
            if (!_isPacmanAI) {
                System.out.println(getScore());
                _completeDeathTime = -1; // pacman didn't die
            }
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
        if (_window != null)
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

    public void sendAuthenticationData (String user,String password) {
        db.sendAuthenticationData(user,password);
    }

    public boolean sendEndOfGameStatistics(String user,String password ,int score, int timeOfFirstDeath, int timeOfGame) {
        return db.sendEndOfGameStatistics(user,password,score,timeOfFirstDeath,timeOfGame);
    }
}
