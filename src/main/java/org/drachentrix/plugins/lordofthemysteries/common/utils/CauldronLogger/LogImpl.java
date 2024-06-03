package org.drachentrix.plugins.lordofthemysteries.common.utils.CauldronLogger;

import org.drachentrix.plugins.lordofthemysteries.common.utils.BeyIngredient;

import java.io.OutputStream;
import java.util.ArrayList;


public class LogImpl implements Log{


    private final ArrayList<BeyIngredient> ingredients = new ArrayList<>();

    @Override
    public void add(BeyIngredient ingredient) {
        ingredients.add(ingredient);
    }

    @Override
    public void clearLog() {
        ingredients.clear();
    }
}
