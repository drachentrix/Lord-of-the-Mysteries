package org.drachentrix.plugins.lordofthemysteries.common.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.drachentrix.plugins.lordofthemysteries.LordOfTheMysteries;
import org.drachentrix.plugins.lordofthemysteries.common.utils.BeyonderIngredient;

public class IngredientsRegister {

    public static final DeferredRegister<Item> INGREDIENTS = DeferredRegister.create(ForgeRegistries.ITEMS, LordOfTheMysteries.MODID);

    public static final RegistryObject<Item> SPIRIT_EATER_STOMACH_POUCH = INGREDIENTS.register("spirit_eater_stomach_pouch",
            () -> new BeyonderIngredient(new Item.Properties().stacksTo(1), "Spirit Eater Stomach Pouch"));

    public static final RegistryObject<Item> DEEP_SEA_MARLINS_BLOOD = INGREDIENTS.register("deep_sea_marlins_blood",
            () -> new BeyonderIngredient(new Item.Properties().stacksTo(1), "Deep Sea Marlin's Blood"));

    public static final RegistryObject<Item> HORNBEAM_ESSENTIALS_OIL = INGREDIENTS.register("hornbeam_essentials_oil",
            () -> new BeyonderIngredient(new Item.Properties().stacksTo(1), "Hornbeam Essential Oils"));

    public static final RegistryObject<Item> STRING_GRASS_POWDER = INGREDIENTS.register("string_grass_powder",
            () -> new BeyonderIngredient(new Item.Properties().stacksTo(1), "String Grass Powder"));

    public static final RegistryObject<Item> RED_CHESTNUT_FLOWER = INGREDIENTS.register("red_chestnut_flower",
            () -> new BeyonderIngredient(new Item.Properties().stacksTo(1), "Red Chestnut Flower"));

    public static void register(IEventBus eventBus){
        INGREDIENTS.register(eventBus);
    }
}
