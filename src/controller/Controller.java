package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import models.*;
import models.Food;
import views.AppWindow;
import views.GameView;
import views.LoginView;

public class Controller implements Runnable {
	private static final int UPGRADE_TIME = 10;
	private static final int GHOST_REVIVE = 3;
	private static final int PACMAN_LIVES = 2;
	private static final int POINT_FOR_FOOD = 1;
	private Timer _gameTimer;
	private Timer _upgradePacman;
	private Timer _ghostRevive;
	private static final int FPS = 60;
	private GameMap _Game_map = GameMap.getMap();
	private AppWindow _window;
    private AppWindow _loginWindow;
	private GameView _gameView = new GameView(_Game_map);
    private LoginView _loginView;
	private Pacman _pacman;
	private int _remainingLives;
	private List<Ghost> _ghosts = new ArrayList<Ghost>();
	private int _points;
	private int _foodRemaining;
	private String _userEmail;
	private byte[] _userPass;
	private int _numGhostsEaten;

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

	public Point getPacmanPos (){
		return _pacman.getPosition();
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
			_userEmail = "comp@comp.comp";
			_userPass = enc.encrypt("12345678");
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

		// initialize the special stage timer
		_upgradePacman = new Timer(UPGRADE_TIME * 1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_pacman = new Pacman(_Game_map);
				for (Ghost g :_ghosts){
					if (g instanceof StrongGhost)
						_pacman.addListener((StrongGhost)g);
				}
				_gameView.setPacman(_pacman);
				_upgradePacman.stop();
			}
		});

		_ghostRevive = new Timer(GHOST_REVIVE * 1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_gameView.bringBackGhost();
				_ghostRevive.stop();
			}
		});

        startNewGame();
        restartGame();

    }
	public void startNewGame() {

		_gameTimer.stop();
		_remainingLives = PACMAN_LIVES;
		_Game_map = GameMap.getMap();
		_foodRemaining = _Game_map.getTotalFood();
		_gameView.newGame(_Game_map);
		_firstDeathTime = 0;
		_numGhostsEaten = 0;

		_pacman = PacmanCreator.createPacman(_Game_map,0, new Point(_Game_map.getPacmanInitialPosition().x,_Game_map.getPacmanInitialPosition().y));
		_gameView.setPacman(_pacman);

		int num_of_ghosts = 4;
		if (!_isPacmanAI)
			_gameView.addKeyListener(new MovePacmanListener());
        else
            _difficulty = 1;

		if (_difficulty == 0)
			num_of_ghosts = 2;
		if (_ghosts.size() == 0) {
			for (int i = 0; i < num_of_ghosts; i++) {

				if (i % 2 == 0) { // create strong ghost
					StrongGhost s = new StrongGhost(_Game_map, i + 1);
					_pacman.addListener(s);
					if (i == 0 )
						s.setSpeed(2);
					_ghosts.add(s);
				}
				else { // create weak ghost
					WeakGhost w = new WeakGhost(_Game_map, i + 1);
					if (i == 1 )
						w.setSpeed(4);
					_ghosts.add(w);
				}

			}
			_gameView.setMonsters(_ghosts);
		} else {
			for (Ghost m : _ghosts) {
				m.startGhost();
			}
		}
        if (_remainingLives == PACMAN_LIVES) {
            _firstDeathTimeStart = System.currentTimeMillis();
            _completeDeathTimeStart = System.currentTimeMillis();
        }
	}

    public int getScore (){
        return (_remainingLives + 1) * 20 + (_Game_map.getTotalFood() - _foodRemaining) + (_numGhostsEaten * 10);
    }
    public long get_firstDeathTime(){
        return _firstDeathTime;
    }
    public long get_completeDeathTime(){
        return _completeDeathTime;
    }

	public void restartGame() {

		_pacman.setPosition(_Game_map.getPacmanInitialPosition().x,_Game_map.getPacmanInitialPosition().y);

		for (Ghost m : _ghosts) {


			m.setPosition(_Game_map.getMonsterInitialPosition().x, _Game_map.getMonsterInitialPosition().y);
			m.set_isOutOfCage(false);
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
		else {
			_pacman.move();
		}

		_gameView.bringBackGhost();
		for (Ghost m : _ghosts){
			if (m.get_hasBeenEaten())
				continue;
			m.move();


			if (_pacman.getBounds().intersects(m.getBounds())) {
				if (m.eatendBy(_pacman)){ // true - monster can be eaten by pacman
					m.set_hasBeenEaten(true);
					m._TimeOfDeath = System.currentTimeMillis();
					m.setOldPos(m.getPosition());
					_gameView.vanishGhost(m);
					_ghostRevive.restart();
					_numGhostsEaten++;
					continue;
				}

                if (_remainingLives == PACMAN_LIVES) {
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
							db.sendEndOfGameStatistics("comp@comp.comp","12345678",getScore(),0,0);
						}

						startNewGame();
					}


					restartGame();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}


		MappedObjects[][] map = _Game_map.getObjectsMap();
		MappedObjects collidableObject = map[_pacman.getPosition().y][_pacman.getPosition().x];

		if (collidableObject != null && collidableObject.isTouching(_pacman)) {
			map[_pacman.getPosition().y][_pacman.getPosition().x].TouchingFigure(_pacman, this);
		}


		if (_foodRemaining == 0) {
            if (!_isPacmanAI) {

                _completeDeathTime = -1; // pacman didn't die
				db.sendEndOfGameStatistics(_userEmail,enc.decrypt(_userPass),getScore(),(int)_firstDeathTime,(int)_completeDeathTime);
            }
			else{
				db.sendEndOfGameStatistics("comp@comp.comp","12345678",getScore(),0,0);
			}
			_gameTimer.stop();

			startNewGame();
			restartGame();
		}
	}

	public void eatFood(Pacman pacman, Food food) {
		food.getParent().remove(food);
		_Game_map.getObjectsMap()[pacman.getPosition().y][pacman.getPosition().x] = null;
		_points += POINT_FOR_FOOD;
		_foodRemaining--;
		switch (food.getFoodType()){
			case 1: //Mighty food
				_pacman = PacmanCreator.createPacman(_Game_map,1, new Point(pacman.getPosition().x,pacman.getPosition().y));
				_gameView.switchPacman(_pacman);
				_upgradePacman.restart();
				break;
			case 2: //Super food
				_pacman = PacmanCreator.createPacman(_Game_map,2, new Point(pacman.getPosition().x,pacman.getPosition().y));
				_gameView.switchPacman(_pacman);
				_upgradePacman.restart();
				break;
		}
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
			_pacman._firePositionEvent();



		}
	}

    /*public void sendAuthenticationData (String user,String password) {
        db.sendAuthenticationData(user,password);
    }

    /*public boolean sendEndOfGameStatistics(String user,String password ,int score, int timeOfFirstDeath, int timeOfGame) {
        return db.sendEndOfGameStatistics(user,password,score,timeOfFirstDeath,timeOfGame);
    }*/
}
