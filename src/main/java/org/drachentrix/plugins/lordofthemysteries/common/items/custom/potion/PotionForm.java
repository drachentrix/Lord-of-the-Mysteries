package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion;

import net.minecraft.world.item.crafting.Ingredient;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;

import java.util.List;

public class PotionForm {
    private final List<Ingredient> ingredients;
    private final String name;
    private final int sequence;
    private final List<Ability> abilityList;

    public PotionForm(List<Ingredient> ingredients, String name, int sequence, List<Ability> abilityList) {
        this.ingredients = ingredients;
        this.name = name;
        this.sequence = sequence;
        this.abilityList = abilityList;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }

    public int getSequence() {
        return sequence;
    }

    public List<Ability> getAbilityList() {
        return abilityList;
    }
}
