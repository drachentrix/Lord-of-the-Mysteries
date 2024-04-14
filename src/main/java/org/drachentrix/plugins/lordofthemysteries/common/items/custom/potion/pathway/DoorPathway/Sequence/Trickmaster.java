package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Sequence;

import net.minecraft.world.item.crafting.Ingredient;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability.DoorOpening;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability.FlashTarget;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability.FreezeTarget;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.PotionForm;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;

import java.util.ArrayList;
import java.util.List;

public class Trickmaster extends PotionForm {

    private static final List<Ability> abilityList = new ArrayList<>(){{
        add(new FreezeTarget(200, 8));
        add(new FlashTarget(300, 8));
    }};

    private static final List<Ingredient> ingredientList = new ArrayList<>();//todo nach erstellen der Ingredients einfügen

    public Trickmaster() {
        super(ingredientList, "Trickmaster", 8, abilityList, "Door", new Properties().stacksTo(1));
    }
}
