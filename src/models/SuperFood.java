package models;

import views.utils.Assets;

import javax.swing.*;

/**
 * Created by tsvika on 20/06/2016.
 */
public class SuperFood extends Food {

    public SuperFood (){
       super("superfood.png");
    }

    @Override
    public int getFoodType () { return 2;}

}
