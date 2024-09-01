package org.drachentrix.plugins.lordofthemysteries;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.drachentrix.plugins.lordofthemysteries.client.overlay.SpiritualityBar;
import org.drachentrix.plugins.lordofthemysteries.common.blocks.BlocksRegister;
import org.drachentrix.plugins.lordofthemysteries.common.events.ClientEvents;
import org.drachentrix.plugins.lordofthemysteries.common.events.PlayerMoveOnTicks;
import org.drachentrix.plugins.lordofthemysteries.common.items.ItemCreativeTab;
import org.drachentrix.plugins.lordofthemysteries.common.items.ItemRegister;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.ingredients.IngredientsRegister;
import org.drachentrix.plugins.lordofthemysteries.common.world.SpiritWorld;
import org.slf4j.Logger;

@Mod(LordOfTheMysteries.MODID)
public class LordOfTheMysteries {
    public static final String MODID = "lordofthemysteries";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public LordOfTheMysteries() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        CREATIVE_MODE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerMoveOnTicks(

        ));
        MinecraftForge.EVENT_BUS.register(new SpiritualityBar());

        //modEventBus.addListener(this::addCreative);

        SpiritWorld.register();
        ItemRegister.register(modEventBus);
        IngredientsRegister.register(modEventBus);
        BlocksRegister.register(modEventBus);
        ItemCreativeTab.register(modEventBus);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

   /* private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ItemRegister.APPRENTICE_POTION);
            event.accept(ItemRegister.TRICKMASTER_POTION);
            event.accept(ItemRegister.RESET);
        }
    } */

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
