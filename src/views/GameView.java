package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import models.Map;
import models.Ghost;
import models.Pacman;
import models.MappedObjects;

public class GameView extends JPanel {
	private static final Dimension BLOCK_SIZE = new Dimension(24,24);
	private Dimension _gameDimensions;
	private Map _gameMap;
	private Pacman _pacman;
	private JPanel _mappedObjectPanel;
	private JPanel _pacmanPanel;
	private JPanel _monstersPanel;
	private List<Ghost> _ghosts;

	public GameView(Map map) {
		setGameMap(map);
		initializeUI();
	}

	public void newGame(Map map) {
		setGameMap(map);
		initializeMap(map);
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
		
		_pacman = pacman;
		_pacmanPanel.add(pacman);
		_pacman.setBounds(oldPos.x * BLOCK_SIZE.width, oldPos.y * BLOCK_SIZE.height, BLOCK_SIZE.width, BLOCK_SIZE.height);
	}

	private void initializeMap(Map map) {
		MappedObjects[][] objectsMap = map.getObjectsMap();
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


	public Dimension getGameDimensions() {
		return _gameDimensions;
	}


	public Pacman getPacman() {
		return _pacman;
	}


	private void setGameMap(Map map) {
		this._gameMap = map;
		this._gameDimensions = map.getGameDimension();
	}
}
