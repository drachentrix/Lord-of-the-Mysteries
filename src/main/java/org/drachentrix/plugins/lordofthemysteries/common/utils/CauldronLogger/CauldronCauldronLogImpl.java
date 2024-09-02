package org.drachentrix.plugins.lordofthemysteries.common.utils.CauldronLogger;

import org.drachentrix.plugins.lordofthemysteries.common.utils.BeyonderIngredient;

import java.util.ArrayList;


public class CauldronCauldronLogImpl implements CauldronLog {


    private final ArrayList<BeyonderIngredient> ingredients = new ArrayList<>();

    @Override
    public void add(BeyonderIngredient ingredient) {
        ingredients.add(ingredient);
    }

    @Override
    public ArrayList<BeyonderIngredient> readLog(){
        return ingredients;
    }
    @Override
    public void clearLog() {
        ingredients.clear();
    }
}
