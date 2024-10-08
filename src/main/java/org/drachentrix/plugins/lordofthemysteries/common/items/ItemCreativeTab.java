package org.drachentrix.plugins.lordofthemysteries.common.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.drachentrix.plugins.lordofthemysteries.LordOfTheMysteries;
import org.drachentrix.plugins.lordofthemysteries.common.blocks.BlocksRegister;

public class ItemCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LordOfTheMysteries.MODID);

    public static final RegistryObject<CreativeModeTab> SEQUENCES = CREATIVE_MODE_TABS.register("sequences",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemRegister.APPRENTICE_POTION.get()))
                    .title(Component.translatable("lordofthemysteries.creative_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ItemRegister.APPRENTICE_POTION.get());
                        output.accept(ItemRegister.RESET.get());
                        output.accept(ItemRegister.TRICKMASTER_POTION.get());
                        output.accept(BlocksRegister.CRAFTING_CAULDRON.get());
                        output.accept(IngredientsRegister.SPIRIT_EATER_STOMACH_POUCH.get());
                        output.accept(IngredientsRegister.DEEP_SEA_MARLINS_BLOOD.get());
                        output.accept(IngredientsRegister.HORNBEAM_ESSENTIALS_OIL.get());
                        output.accept(IngredientsRegister.STRING_GRASS_POWDER.get());
                        output.accept(IngredientsRegister.RED_CHESTNUT_FLOWER.get());
                    } )
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
