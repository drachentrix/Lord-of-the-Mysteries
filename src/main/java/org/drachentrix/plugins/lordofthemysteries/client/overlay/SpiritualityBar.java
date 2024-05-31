package org.drachentrix.plugins.lordofthemysteries.client.overlay;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
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
    private static final ResourceLocation EmptyBar = new ResourceLocation(LordOfTheMysteries.MODID,"textures/ui/spiritualitybarempty.png");
    private static final int BAR_WIDTH = 200;
    private static final int BAR_HEIGHT = 50;

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
        Minecraft minecraft = Minecraft.getInstance();

        int filledWidth  = (int) (BAR_WIDTH * filled);

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        int barX = screenWidth / 2 + BAR_WIDTH / 2 - 90 ;
        int barY = screenHeight - 39 + BAR_HEIGHT - 59;

        RenderSystem.setShaderTexture(0, EmptyBar);
        graphics.blit(EmptyBar, barX, barY, 0,0, BAR_WIDTH, BAR_HEIGHT);

        RenderSystem.setShaderTexture(0, FullBar);
        graphics.blit(FullBar, barX, barY, 0, 0 , filledWidth, BAR_HEIGHT);
        /*
        int filledWidth = (int) (BAR_WIDTH * filled);

        graphics.blit(FullBar, BAR_X, BAR_Y, 0, 0, BAR_WIDTH, BAR_HEIGHT);
        graphics.blit(FullBar, BAR_X, BAR_Y, 0, 0, filledWidth, BAR_HEIGHT);

         */
    }

    private void renderSpiritualityText(GuiGraphics graphics){
        //eigentlich brauchen wir das nicht aber vielleicht aender ich meine meinung
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        int barX = screenWidth / 2 + BAR_WIDTH / 2 - 73 ;
        int barY = screenHeight - 39 + BAR_HEIGHT - 42;
        String text = "Spirituality: " + Math.round(Beyonder.getSpirituality()) + "/" + Beyonder.getMaxSpirituality();
        int textWidth = font.width(text);
        graphics.drawString(font, text, barX + (BAR_WIDTH / 2) + (textWidth / 2), barY - 10, 0xFFFFFF, true);
    }
}
