package org.drachentrix.plugins.lordofthemysteries.client.overlay;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.drachentrix.plugins.lordofthemysteries.LordOfTheMysteries;
import org.drachentrix.plugins.lordofthemysteries.client.Beyonder;

@Mod.EventBusSubscriber(modid = LordOfTheMysteries.MODID, value = Dist.CLIENT)
public class SpiritualityBar {

    private static final ResourceLocation FullBar = new ResourceLocation(LordOfTheMysteries.MODID,"textures/ui/spiritualitybarfull.png");
    private static final int BAR_WIDTH = 120;
    private static final int BAR_HEIGHT = 25;
    private static final int BAR_X = 0;
    private static final int BAR_Y = 190;

    @SubscribeEvent
    public void onRenderGui(RenderGuiOverlayEvent.Post event){
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null || Beyonder.getPathway() == null){
            return;
        }
        double currentSpir = Beyonder.getSpirituality();
        double maxSpirituality = Beyonder.getMaxSpirituality();
        double percentageFilled = currentSpir / maxSpirituality;

        renderSpiritualityBar(event.getGuiGraphics(), percentageFilled);
    }

    private void renderSpiritualityBar(GuiGraphics graphics, double filled){
        int filledWidth = (int) (BAR_WIDTH * filled);

        graphics.blit(FullBar, BAR_X, BAR_Y, 0, 0, BAR_WIDTH, BAR_HEIGHT);
        graphics.blit(FullBar, BAR_X, BAR_Y, 0, 0, filledWidth, BAR_HEIGHT);
    }
}
