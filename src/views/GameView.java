package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.StringJoiner;

import javax.swing.*;

import models.*;

public class GameView extends JPanel {
	private static final Dimension BLOCK_SIZE = new Dimension(24,24);
	private Dimension _gameDimensions;
	private GameMap _gameGameMap;
	private Pacman _pacman;
	private JPanel _mappedObjectPanel;
	private JPanel _pacmanPanel;
	private JPanel _monstersPanel;
	private List<Ghost> _ghosts;

	public GameView(GameMap gameMap) {
		setGameMap(gameMap);
		initializeUI();
	}

	public void newGame(GameMap gameMap) {
		setGameMap(gameMap);
		initializeMap(gameMap);
	}

	public void setPacman(Pacman pacman) {
		// set the pacman to its default position
		Point oldPos = pacman.getPosition();
		
		if (_pacman != null) {
			oldPos = _pacman.getPosition();
			pacman.setDirection(_pacman.getDirection());
			_pacmanPanel.remove(_pacman);
			_pacmanPanel.repaint();
		}
		_monstersPanel.repaint();
		
		_pacman = pacman;
		_pacmanPanel.add(pacman);
		_pacman.setBounds(oldPos.x * BLOCK_SIZE.width, oldPos.y * BLOCK_SIZE.height, BLOCK_SIZE.width, BLOCK_SIZE.height);
	}

	private void initializeMap(GameMap gameMap) {
		MappedObjects[][] objectsMap = gameMap.getObjectsMap();
		_mappedObjectPanel.removeAll();

		for (int i = 0; i < objectsMap.length; i++) {
			for (int j = 0; j < objectsMap[i].length; j++) {
				_mappedObjectPanel.add(objectsMap[i][j]);
			}
		}
		
		_mappedObjectPanel.validate();
		_mappedObjectPanel.repaint();
	}

	public void setMonsters(List<Ghost> ghosts) {
		_ghosts = ghosts;
		
		_monstersPanel.removeAll();
		for (Ghost m : _ghosts) {
			_monstersPanel.add(m);
			m.setBounds(m.getPosition().x * BLOCK_SIZE.width, m.getPosition().y * BLOCK_SIZE.height, BLOCK_SIZE.width, BLOCK_SIZE.height);
		}
		_monstersPanel.validate();
	}

	private void initializeUI() {
		setBackground(Color.black);
		setSize(new Dimension(_gameDimensions.width * BLOCK_SIZE.width, _gameDimensions.height * BLOCK_SIZE.height));

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(getSize());
		GridLayout _panelsLayout = new GridLayout(_gameDimensions.height, _gameDimensions.width);


		_pacmanPanel = new JPanel();
		_pacmanPanel.setOpaque(false);
		_pacmanPanel.setSize(getSize());
		_pacmanPanel.setLayout(null);
		layeredPane.add(_pacmanPanel);

		_mappedObjectPanel = new JPanel();
		_mappedObjectPanel.setOpaque(false);
		_mappedObjectPanel.setSize(getSize());
		_mappedObjectPanel.setLayout(_panelsLayout);
		layeredPane.add(_mappedObjectPanel, 1);

		_monstersPanel = new JPanel();
		_monstersPanel.setOpaque(false);
		_monstersPanel.setSize(getSize());
		_monstersPanel.setLayout(null);
		layeredPane.add(_monstersPanel, 0);

		add(layeredPane);
	}

	public void vanishGhost (Ghost g){
		if (g != null){
			g.setOldPos(g.getPosition());
			_monstersPanel.remove(g);
			_monstersPanel.repaint();
		}
	}

	public void bringBackGhost (){
		for (Ghost g : _ghosts) {
			if (g != null) {
				if (g.get_hasBeenEaten() && (System.currentTimeMillis() - g._TimeOfDeath  >= 3000)) {
					g.set_hasBeenEaten(false);
					g.setPosition (g.getOldPos().x,g.getOldPos().y);
					_monstersPanel.add(g);
					_monstersPanel.repaint();
					g.setBounds(g.getPosition().x * BLOCK_SIZE.width, g.getPosition().y * BLOCK_SIZE.height, BLOCK_SIZE.width, BLOCK_SIZE.height);
				}
			}
		}
	}

	public void switchPacman (Pacman pacman){
		// set the pacman to its default position
		Point oldPos = pacman.getPosition();

		if (_pacman != null) {
			oldPos = _pacman.getPosition();
			pacman.setDirection(_pacman.getDirection());
			_pacmanPanel.remove(_pacman);
			_pacmanPanel.repaint();

		}
		_monstersPanel.repaint();
		/*for (Ghost g :_ghosts){
			if (g instanceof StrongGhost)
				_pacman.removeListener((StrongGhost)g);
		}*/

		_pacman = pacman;
		_pacmanPanel.add(pacman);
		_pacman.setBounds(oldPos.x * BLOCK_SIZE.width, oldPos.y * BLOCK_SIZE.height, BLOCK_SIZE.width, BLOCK_SIZE.height);

		for (Ghost g :_ghosts){
			if (g instanceof StrongGhost)
				_pacman.addListener((StrongGhost)g);
		}

	}


	public Dimension getGameDimensions() {
		return _gameDimensions;
	}


	public Pacman getPacman() {
		return _pacman;
	}


	private void setGameMap(GameMap gameMap) {
		this._gameGameMap = gameMap;
		this._gameDimensions = gameMap.getGameDimension();
	}
}
