package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.drachentrix.plugins.lordofthemysteries.common.items.IngredientsRegister;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability.DoorOpening;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability.FlashTarget;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability.FreezeTarget;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;
import java.util.ArrayList;
import java.util.List;

public enum Sequences {

    APPRENTICE(new ArrayList<>() {{
        add(new DoorOpening(10, 9));
    }}, new ArrayList<>(), "Apprentice", "apprentice_potion", 9, "Door", 100),

    TRICKMASTER(new ArrayList<>() {{
        add(new FreezeTarget(20, 8));
        add(new FlashTarget(30, 8));
    }}, new ArrayList<>() {{
        add(IngredientsRegister.SPIRIT_EATER_STOMACH_POUCH);
        add(IngredientsRegister.DEEP_SEA_MARLINS_BLOOD);
    }}, "Trickmaster", "trickmaster_potion", 8, "Door", 250);

    private final List<Ability> abilityList;
    private final List<RegistryObject<Item>> ingredientList;
    private final String name;
    private final String potionName;
    private final int sequence;
    private final double spirituality;
    private final String pathway;
    private final Item.Properties properties = new Item.Properties().stacksTo(1);
    private static final List<Sequences> allSequences = List.of(values());

    Sequences(List<Ability> abilityList, List<RegistryObject<Item>> ingredientList, String name, String potionName, int sequence, String pathway, double spirituality) {
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

    public static List<Sequences> getSequencesBasedOnPathway(String pathway) {
        return allSequences.stream().filter(sequences -> sequences.pathway.equals(pathway)).toList();
    }

    public static List<Sequences> getAllSequences() {
        return allSequences;
    }

    public List<Item> getIngredientList() {
        return ingredientList.stream()
                .map(RegistryObject::get)
                .toList();
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

    public net.minecraft.world.item.Item.Properties getProperties() {
        return properties;
    }

    public double getSpirituality() {
        return spirituality;
    }

    public String getPotionName() {
        return potionName;
    }
}
