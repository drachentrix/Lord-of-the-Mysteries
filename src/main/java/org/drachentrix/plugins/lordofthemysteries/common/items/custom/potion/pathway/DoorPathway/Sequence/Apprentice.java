package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Sequence;

import net.minecraft.world.item.crafting.Ingredient;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability.DoorOpening;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.Sequences;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.PotionForm;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;

import java.util.ArrayList;
import java.util.List;

public class Apprentice extends PotionForm{

    private static final List<Ability> abilityList = new ArrayList<>(){{
        add(new DoorOpening(100, 9));
    }};

    private static final List<Ingredient> ingredientList = new ArrayList<>();//todo nach erstellen der Ingredients einfügen
    public Apprentice() {
        super(Sequences.APPRENTICE);
    }
}
