package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion;

import net.minecraft.world.item.Item;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability.DoorOpening;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability.FlashTarget;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability.FreezeTarget;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;
import org.drachentrix.plugins.lordofthemysteries.common.utils.BeyIngredient;

import java.util.ArrayList;
import java.util.List;

public enum Sequences{
    APPRENTICE(new ArrayList<>(){{
        add(new DoorOpening(10, 9));
    }}, new ArrayList<>(), "apprentice_potion", "apprentice_potion" , 9, "Door", 100),
    TRICKMASTER(new ArrayList<>(){{
        add(new FreezeTarget(20, 8));
        add(new FlashTarget(30, 8));
    }}, new ArrayList<>(), "Trickmaster", "trickmaster_potion" , 8, "Door", 250);

    private final List<Ability> abilityList;
    private final List<BeyIngredient> ingredientList;
    private final String name;
    private final String potionName;
    private final int sequence;
    private final double spirituality;
    private final String pathway;
    private final Item.Properties properties = new Item.Properties().stacksTo(1);
    private static final List<Sequences> allSequences = List.of(values());

    Sequences(List<Ability> abilityList, List<BeyIngredient> ingredientList, String name, String potionName, int sequence, String pathway, double spirituality) {
        this.abilityList = abilityList;
        this.ingredientList = ingredientList;
        this.name = name;
        this.potionName = potionName;
        this.sequence = sequence;
        this.spirituality = spirituality;
        this.pathway = pathway;
    }

    public List<Ability> getAbilityList() {
        return abilityList;
    }

    public static List<Sequences> getAllSequences() {
        return allSequences;
    }

    public List<BeyIngredient> getIngredientList() {
        return ingredientList;
    }

    public String getName() {
        return name;
    }

    public int getSequence() {
        return sequence;
    }

    public String getPathway() {
        return pathway;
    }

    public Item.Properties getProperties() {
        return properties;
    }

    public double getSpirituality() {
        return spirituality;
    }

    public String getPotionName() {
        return potionName;
    }
}
