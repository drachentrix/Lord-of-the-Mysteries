package org.drachentrix.plugins.lordofthemysteries.common.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.drachentrix.plugins.lordofthemysteries.LordOfTheMysteries;
import org.drachentrix.plugins.lordofthemysteries.common.blocks.custom.CraftingCauldron;
import org.drachentrix.plugins.lordofthemysteries.common.items.ItemRegister;

import java.util.function.Supplier;

public class BlocksRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LordOfTheMysteries.MODID);

    public static final RegistryObject<Block> CRAFTING_CAULDRON = registryObject("potion_cauldron", CraftingCauldron::new);

    private static <T extends Block> RegistryObject<T> registryObject(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registryObject("potion_cauldron", toReturn);
        return toReturn;
    }

    private static <T extends  Block> void registryObject(String name, RegistryObject<T> block){
        ItemRegister.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(1)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
