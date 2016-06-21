package models;

import views.utils.Assets;

import javax.swing.*;

/**
 * Created by tsvika on 20/06/2016.
 */
public class MightyFood extends Food {

    public MightyFood (){
        super("mightyfood.png");
    }

    @Override
    public int getFoodType () { return 1;}
}
