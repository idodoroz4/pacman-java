package models;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tsvika on 19/06/2016.
 */
public class PacmanCreator {
    private static Map <Integer,Pacman> _pacmanDic = new HashMap<Integer,Pacman>();

    public static Pacman createPacman(GameMap gameMap, int type, Point pos){
        if (!_pacmanDic.containsKey(type)){
            switch (type) {
                case 0:
                    _pacmanDic.put(type, new Pacman(gameMap));
                    break;
                case 1:
                    _pacmanDic.put(type, new MightyPacman(gameMap));
                    break;
                case 2:
                    _pacmanDic.put(type, new SuperPacman(gameMap));
                    break;
            }
        }
        _pacmanDic.get(type).setPosition(pos.x,pos.y);
        return _pacmanDic.get(type);
    }
}
