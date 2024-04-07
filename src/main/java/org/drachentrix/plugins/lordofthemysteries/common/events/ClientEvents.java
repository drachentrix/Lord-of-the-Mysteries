package org.drachentrix.plugins.lordofthemysteries.common.events;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.drachentrix.plugins.lordofthemysteries.LordOfTheMysteries;
import org.drachentrix.plugins.lordofthemysteries.client.Beyonder;
import org.drachentrix.plugins.lordofthemysteries.common.utils.KeyBinding;

@Mod.EventBusSubscriber(modid = LordOfTheMysteries.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerKey(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.ABILITY_CICLE_KEY);
        event.register(KeyBinding.ABILITY_MENU_KEY);
        event.register(KeyBinding.ABILITY_USE_KEY);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key key) {
        if(Beyonder.getPathway() != null) {

            if (KeyBinding.ABILITY_USE_KEY.isDown()) {
                KeyBinding.ABILITY_USE_KEY.consumeClick();
                Minecraft minecraft = Minecraft.getInstance();
                Beyonder.getSelectedAbility().onAbilityUse(Minecraft.getInstance().player);
                minecraft.player.displayClientMessage(Component.literal("HI"), true);
            }
            //todo machen das  die Abilitys und so dann gehen
        }
    }
}
