package org.drachentrix.plugins.lordofthemysteries.common.items.custom.ingredients;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.drachentrix.plugins.lordofthemysteries.LordOfTheMysteries;

public class IngredientsRegister {

    public static final DeferredRegister<Item> INGREDIENTS = DeferredRegister.create(ForgeRegistries.ITEMS, LordOfTheMysteries.MODID);

    public static final RegistryObject<Item> SPIRIT_EATER_STOMACH_POUCH = INGREDIENTS.register("spirit_eater_stomach_pouch",
            SpiritEaterStomachPouch::new);

    public static void register(IEventBus eventBus){
        INGREDIENTS.register(eventBus);
    }
}
