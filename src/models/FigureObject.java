package models;

import java.awt.*;

public abstract class FigureObject extends Animation {
	private static final long serialVersionUID = 1990L;
	private static final int BLOCK_SIZE =  24;
	private Point _position = new Point(0, 0);
	private Movement _movement = Movement.NONE;
	private Movement _tempMove = null;
	private int _speed = 3;
	protected GameMap _gameGameMap;


	public FigureObject(GameMap collisionGameMap) {
		_gameGameMap = collisionGameMap;
	}


	public void move() {
		int dx = 0, dy = 0;
		// Check direction and if the object can be moved there
		if (_movement == Movement.UP) {
			if (getCollisionMap().canMove(this, getPosition().x, getPosition().y - 1))
				dy -= _speed;
			else
				setDirection(Movement.NONE);
			
		} else if (_movement == Movement.DOWN) {
			if (getCollisionMap().canMove(this, getPosition().x, getPosition().y + 1))
				dy += _speed;
			else
				setDirection(Movement.NONE);
			
		} else if (_movement == Movement.RIGHT) {
			if (getCollisionMap().canMove(this, getPosition().x + 1, getPosition().y))
				dx += _speed;
			else
				setDirection(Movement.NONE);
			
		} else if (_movement == Movement.LEFT) {
			if (getCollisionMap().canMove(this, getPosition().x - 1, getPosition().y))
				dx -= _speed;
			else
				setDirection(Movement.NONE);
		}

		// set the object to the position
		setBounds((getBounds().x + dx) % getParent().getSize().width, (getBounds().y + dy) % getParent().getSize().height, BLOCK_SIZE, BLOCK_SIZE);

		// make step of one block
		if (isStep()) {
			// update the current position
			setPosition((getBounds().x) / BLOCK_SIZE, (getBounds().y) / BLOCK_SIZE);
			
			// change direction on step
			if (_tempMove != null && !_tempMove.equals(_movement)) {
				_movement = _tempMove;
				_tempMove = null;
			}
		}
	}

    protected boolean isIntersection(){
        //System.out.println(this.getX());
        //System.out.println(_gameGameMap.getObjectsMap()[18][6]);
        if ((this.getPosition().x == 6) && (this.getPosition().y == 18)) { return true; }

	    if ( (((_gameGameMap.getObjectsMap()[this.getPosition().y - 1][this.getPosition().x]) == null) ||
                ((_gameGameMap.getObjectsMap()[this.getPosition().y - 1][this.getPosition().x]) instanceof Food)) &&
                (((_gameGameMap.getObjectsMap()[this.getPosition().y + 1 ][this.getPosition().x]) == null) ||
                        ((_gameGameMap.getObjectsMap()[this.getPosition().y + 1][this.getPosition().x]) instanceof Food)) &&
                (((_gameGameMap.getObjectsMap()[this.getPosition().y][this.getPosition().x - 1]) == null) ||
                        ((_gameGameMap.getObjectsMap()[this.getPosition().y][this.getPosition().x - 1]) instanceof Food)))
            return true;

        return false;


    }

	protected boolean isCageUp(){
        return  (_gameGameMap.getCollisionMap()[_position.y - 1][_position.x] == -1);


    }

	protected boolean isStep() {
		return (getBounds().x % BLOCK_SIZE == 0 && getBounds().y % BLOCK_SIZE == 0);
	}

	public Point getPosition() {
		return _position;
	}

	public void setPosition(int x, int y) {
		if (_gameGameMap.canMove(this, x, y)) {
			_position.move(x, y);
		}
		
		if (getParent() != null)
			setBounds(x * 24, y * 24, getHeight(), getWidth());
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		if (y + height > getParent().getHeight())
			y = 0;
		else if (y < 0)
			y = getParent().getHeight() - getHeight();

		if (x + width > getParent().getWidth())
			x = 0;
		else if (x < 0)
			x = getParent().getWidth() - getWidth();

		super.setBounds(x, y, width, height);
	}


	public Movement getDirection() {
		return _movement;
	}

	public void setDirection(Movement movement) {
		this._tempMove = movement;
	}

	public int getspeed() {
		return _speed;
	}

	protected GameMap getCollisionMap() {
		return _gameGameMap;
	}

	public void setSpeed(int speed) {
		if (BLOCK_SIZE % speed == 0)
			this._speed = speed;
	}
}
