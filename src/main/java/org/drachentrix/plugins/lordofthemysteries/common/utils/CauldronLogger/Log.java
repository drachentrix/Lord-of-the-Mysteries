package org.drachentrix.plugins.lordofthemysteries.common.utils.CauldronLogger;

import org.drachentrix.plugins.lordofthemysteries.common.utils.BeyIngredient;

import java.util.ArrayList;

public interface Log{

    void add(BeyIngredient ingredient);

    void clearLog();

    ArrayList<BeyIngredient> readLog();
}
