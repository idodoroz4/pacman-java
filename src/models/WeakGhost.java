package models;

/**
 * Created by tsvika on 19/06/2016.
 */
public class WeakGhost extends Ghost {

    public WeakGhost(GameMap gameMap, int color) {
        super(gameMap,color);

    }

    @Override
    public boolean eatendBy( IFigureElementVisitor visitor){
        return visitor.eats(this);
    }
}
