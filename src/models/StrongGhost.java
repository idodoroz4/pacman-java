package models;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tsvika on 19/06/2016.
 */
public class StrongGhost extends Ghost implements IPositionListener {

    private Point pacmanLastPos;
    public StrongGhost(GameMap gameMap, int color) {
        super(gameMap,color);
        pacmanLastPos = null;

    }

    @Override
    public boolean eatendBy( IFigureElementVisitor visitor){
        return visitor.eats(this);
    }

    @Override
    public void positionReceived(PacmanPositionEvent event){
        if (! isCageUp())
            pacmanLastPos = event.getPacmanPos();

        //this.setPosition(event.getPacmanPos().x,event.getPacmanPos().y);
    }

    @Override
    public void move(){

        Point myPos = this.getPosition();
        Map<Movement, Integer> positions = new HashMap<Movement, Integer>();
        if (pacmanLastPos != null) {

            if (_gameGameMap.canMove(this, myPos.x + 1, myPos.y))
                positions.put(Movement.RIGHT, Math.abs(myPos.x + 1 - pacmanLastPos.x) + Math.abs(myPos.y - pacmanLastPos.y));
            if (_gameGameMap.canMove(this, myPos.x - 1, myPos.y))
                positions.put(Movement.LEFT, Math.abs(myPos.x - 1 - pacmanLastPos.x) + Math.abs(myPos.y - pacmanLastPos.y));
            if (_gameGameMap.canMove(this, myPos.x, myPos.y - 1))
                positions.put(Movement.UP, Math.abs(myPos.x - pacmanLastPos.x) + Math.abs(myPos.y - 1 - pacmanLastPos.y));
            if (_gameGameMap.canMove(this, myPos.x, myPos.y + 1))
                positions.put(Movement.DOWN, Math.abs(myPos.x - pacmanLastPos.x) + Math.abs(myPos.y + 1 - pacmanLastPos.y));

            Movement bestKey = Movement.NONE;
            int shortDist = Integer.MAX_VALUE;
            for (Movement key : positions.keySet()) {
                if (positions.get(key) < shortDist) {
                    shortDist = positions.get(key);
                    bestKey = key;
                }
            }

            this.setDirection(bestKey);
        }


        super.move();
    }



}
