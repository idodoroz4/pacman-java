package models;

/**
 * Created by tsvika on 18/06/2016.
 */
public interface IFigureElement {

    public boolean eatendBy( IFigureElementVisitor visitor);
}
