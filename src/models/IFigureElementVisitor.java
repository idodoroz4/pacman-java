package models;

/**
 * Created by tsvika on 18/06/2016.
 */
public interface IFigureElementVisitor {

    public boolean eats(WeakGhost wg);
    public boolean eats(StrongGhost sg);
}
