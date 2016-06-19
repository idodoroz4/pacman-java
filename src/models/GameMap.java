package models;

import java.awt.Dimension;
import java.awt.Point;

public class GameMap {

	private int[][] _map;
	private MappedObjects[][] _objectsMap;
	private Dimension _gameDimension;
	private Point ghostInitialPosition;
	private Point _pacmanInitialPosition;
	private int _totalFood;
	

	protected GameMap(int[][] map, Point cagePosition, Point pacmanInitialPosition) {
		_map = map;
		_gameDimension = new Dimension(_map[0].length, _map.length);
		ghostInitialPosition = cagePosition;
		_pacmanInitialPosition = pacmanInitialPosition;
		_objectsMap = new MappedObjects[_gameDimension.height][_gameDimension.width];
		int W = 1;
		int F = 2;
		int E = 0;
		int C = -1;

		for (int i = 0; i < _gameDimension.height; i++) {
			for (int j = 0; j < _gameDimension.width; j++) {
				if (_map[i][j] == W) {

					_objectsMap[i][j] = new Wall();
				} else if (_map[i][j] == F) {

					_objectsMap[i][j] = new Food();
					_totalFood++;
				} else if (_map[i][j] == C) {
					_objectsMap[i][j] = new GhostGate();
				} else {
					_objectsMap[i][j] = new MappedObjects(null);
				}
			}
		}
		
	}

	public int getTotalFood() {
		return _totalFood;
	}
	

	public Point getMonsterInitialPosition() {
		return ghostInitialPosition;
	}
	

	public Point getPacmanInitialPosition() {
		return _pacmanInitialPosition;
	}

	public Dimension getGameDimension() {
		return _gameDimension;
	}

	public int[][] getCollisionMap() {
		return _map;
	}

	public MappedObjects[][] getObjectsMap() {
		return _objectsMap;
	}
	
	public boolean canMove(FigureObject object, Point position) {
		return canMove(object, position.x, position.y);
	}

	public boolean canMove(FigureObject object, int x, int y) {
		int height = getGameDimension().height;
		int width = getGameDimension().width;

		// if the target position is out of the panel size
		if (y < 0)
			y = height - 1;
		else if (y >= height)
			y = 0;

		if (x < 0)
			x = width - 1;
		else if (x >= width)
			x = 0;
		
		if (_objectsMap[y][x] != null)
			return _objectsMap[y][x].isTouching(object);
		
		return true;
	}

	public static GameMap getMap() {
		int W = 1;
		int P = 2;
		int E = 0;
		int C = -1;
		int[][] map = {


				{ W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W },
				{ W,P,P,P,P,P,P,P,P,P,P,P,P,W,W,P,P,P,P,P,P,P,P,P,P,P,P,W },
				{ W,P,W,W,W,W,P,W,W,W,W,W,P,W,W,P,W,W,W,W,W,P,W,W,W,W,P,W },
				{ W,P,W,W,W,W,P,W,W,W,W,W,P,W,W,P,W,W,W,W,W,P,W,W,W,W,P,W },
				{ W,P,W,W,W,W,P,W,W,W,W,W,P,W,W,P,W,W,W,W,W,P,W,W,W,W,P,W },
				{ W,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,W },
				{ W,P,W,W,W,W,P,W,W,P,W,W,W,W,W,W,W,W,P,W,W,P,W,W,W,W,P,W },
				{ W,P,W,W,W,W,P,W,W,P,W,W,W,W,W,W,W,W,P,W,W,P,W,W,W,W,P,W },
				{ W,P,P,P,P,P,P,W,W,P,P,P,P,W,W,P,P,P,P,W,W,P,P,P,P,P,P,W },
				{ W,W,W,W,W,W,P,W,W,W,W,W,E,W,W,E,W,W,W,W,W,P,W,W,W,W,W,W },
				{ E,E,E,E,E,W,P,W,W,E,E,E,E,E,E,E,E,E,E,W,W,P,W,E,E,E,E,E },
				{ E,E,E,E,E,W,P,W,W,E,W,W,W,C,C,W,W,W,E,W,W,P,W,E,E,E,E,E },
				{ W,W,W,W,W,W,P,W,W,E,W,E,E,E,E,E,E,W,E,W,W,P,W,W,W,W,W,W },
				{ E,E,E,E,E,E,P,E,E,E,W,E,E,E,E,E,E,W,E,E,E,P,E,E,E,E,E,E },
				{ W,W,W,W,W,W,P,W,W,E,W,E,E,E,E,E,E,W,E,W,W,P,W,W,W,W,W,W },
				{ E,E,E,E,E,W,P,W,W,E,W,W,W,W,W,W,W,W,E,W,W,P,W,E,E,E,E,E },
				{ E,E,E,E,E,W,P,W,W,E,E,E,E,E,E,E,E,E,E,W,W,P,W,E,E,E,E,E },
				{ W,W,W,W,W,W,P,W,W,E,W,W,W,W,W,W,W,W,E,W,W,P,W,W,W,W,W,W },
				{ W,P,P,P,P,P,P,P,P,P,P,P,P,W,W,P,P,P,P,P,P,P,P,P,P,P,P,W },
				{ W,P,W,W,W,W,P,W,W,W,W,W,P,W,W,P,W,W,W,W,W,P,W,W,W,W,P,W },
				{ W,P,W,W,W,W,P,W,W,W,W,W,P,W,W,P,W,W,W,W,W,P,W,W,W,W,P,W },
				{ W,P,P,P,W,W,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,W,W,P,P,P,W },
				{ W,W,W,P,W,W,P,W,W,P,W,W,W,W,W,W,W,W,P,W,W,P,W,W,P,W,W,W },
				{ W,W,W,P,W,W,P,W,W,P,W,W,W,W,W,W,W,W,P,W,W,P,W,W,P,W,W,W },
				{ W,P,P,P,P,P,P,W,W,P,P,P,P,W,W,P,P,P,P,W,W,P,P,P,P,P,P,W },
				{ W,P,W,W,W,W,W,W,W,W,W,W,P,W,W,P,W,W,W,W,W,W,W,W,W,W,P,W },
				{ W,P,W,W,W,W,W,W,W,W,W,W,P,W,W,P,W,W,W,W,W,W,W,W,W,W,P,W },
				{ W,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,P,W },
				{ W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W },
				
		};
			return new GameMap(map, new Point(14, 12), new Point(14, 16));
	}
}
