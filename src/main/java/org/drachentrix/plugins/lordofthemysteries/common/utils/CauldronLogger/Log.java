package org.drachentrix.plugins.lordofthemysteries.common.utils.CauldronLogger;

import org.drachentrix.plugins.lordofthemysteries.common.utils.BeyonderIngredient;

import java.util.ArrayList;

public interface Log{

    void add(BeyonderIngredient ingredient);

    void clearLog();

    ArrayList<BeyonderIngredient> readLog();
}
