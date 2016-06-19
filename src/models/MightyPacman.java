package models;

/**
 * Created by tsvika on 19/06/2016.
 */
public class MightyPacman extends Pacman {

    public MightyPacman (GameMap gameMap){
        super(gameMap);

    }

    @Override
    public boolean eats (WeakGhost ghost){
        return true;
    }
    @Override
    public boolean eats (StrongGhost ghost){
        return false;
    }
}
