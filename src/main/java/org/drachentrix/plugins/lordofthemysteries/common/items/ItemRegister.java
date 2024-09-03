package org.drachentrix.plugins.lordofthemysteries.common.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.drachentrix.plugins.lordofthemysteries.LordOfTheMysteries;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.RemovePathwayItem;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.PotionForm;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.Sequences;

public class ItemRegister {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LordOfTheMysteries.MODID);

    public static final RegistryObject<Item> APPRENTICE_POTION = ITEMS.register("apprentice_potion",
           () -> new PotionForm(Sequences.APPRENTICE));

    public static final RegistryObject<Item> TRICKMASTER_POTION = ITEMS.register("trickmaster_potion",
            () -> new PotionForm(Sequences.TRICKMASTER));

    public static final RegistryObject<Item> RESET = ITEMS.register("reset",
            () -> new RemovePathwayItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
