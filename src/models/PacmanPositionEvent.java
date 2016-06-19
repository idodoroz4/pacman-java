package models;

import java.awt.*;
import java.util.EventObject;

/**
 * Created by tsvika on 19/06/2016.
 */
public class PacmanPositionEvent extends EventObject{
    private Point _pos;

    public PacmanPositionEvent (Object source,Point pos){
        super(source);
        _pos = pos;
    }

    public Point getPacmanPos(){
        return _pos;
    }
}
