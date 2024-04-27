package org.drachentrix.plugins.lordofthemysteries.common.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.providers.UnihexProvider;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.drachentrix.plugins.lordofthemysteries.LordOfTheMysteries;
import org.drachentrix.plugins.lordofthemysteries.client.Beyonder;
import org.drachentrix.plugins.lordofthemysteries.common.utils.KeyBinding;
import org.drachentrix.plugins.lordofthemysteries.common.world.DimTeleporter;
import org.drachentrix.plugins.lordofthemysteries.common.world.SpiritWorld;

import java.awt.*;

@Mod.EventBusSubscriber(modid = LordOfTheMysteries.MODID, value = Dist.CLIENT)
public class ClientEvents {
    private static Vec3 playerPosition;

    @SubscribeEvent
    public static void registerKey(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.ABILITY_CICLE_KEY);
        event.register(KeyBinding.ABILITY_MENU_KEY);
        event.register(KeyBinding.ABILITY_USE_KEY);
        event.register(KeyBinding.ABILIY_SWITCH_DIMENSION);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key key) {
        if (Beyonder.getPathway() != null) {
            Minecraft minecraft = Minecraft.getInstance();
            ServerPlayer player = minecraft.getSingleplayerServer().getPlayerList().getPlayerByName(minecraft.player.getGameProfile().getName());
            if (KeyBinding.ABILIY_SWITCH_DIMENSION.isDown()) {
                ResourceKey<Level> destDim = Level.OVERWORLD == player.level().dimension() ? SpiritWorld.SPIRIT_WORLD_LEVEL_KEY : Level.OVERWORLD;
                ServerLevel level = Minecraft.getInstance().getSingleplayerServer().getLevel(destDim);
                if (destDim != Level.OVERWORLD) {

                    player.changeDimension(level, new DimTeleporter(minecraft.player.getOnPos(), false));
                } else {
                    player.changeDimension(level, new DimTeleporter(minecraft.player.getOnPos(), true));
                }
                KeyBinding.ABILIY_SWITCH_DIMENSION.consumeClick();

            }

            if (KeyBinding.ABILITY_USE_KEY.isDown()) {

                KeyBinding.ABILITY_USE_KEY.consumeClick();
                Beyonder.getSelectedAbility().onAbilityUse(minecraft.player);
                //minecraft.player.displayClientMessage(Component.literal(Beyonder.getSelectedAbility().toString()), true);

            } else if (KeyBinding.ABILITY_CICLE_KEY.isDown()) {

                KeyBinding.ABILITY_CICLE_KEY.consumeClick();
                int nextAbilityIndex = Beyonder.getAbilityList().indexOf(Beyonder.getSelectedAbility()) + 1;
                Beyonder.setSelectedAbility(Beyonder.getAbilityList().get(nextAbilityIndex < Beyonder.getAbilityList().size() ? nextAbilityIndex : 0));
                Minecraft.getInstance().player.displayClientMessage(Component.literal(Beyonder.getSelectedAbility().toString()), true);
            }
        }
    }

    @SubscribeEvent
    public static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getTo().equals(SpiritWorld.SPIRIT_WORLD_LEVEL_KEY)) {
            LivingEntity player = event.getEntity();
            Level overworld = player.getCommandSenderWorld();
            Level spiritWorld = player.getServer().getLevel(SpiritWorld.SPIRIT_WORLD_LEVEL_KEY);

            if (spiritWorld != null) {
                BlockPos playerPos = player.blockPosition();
                Vec3 playerPosition = new Vec3(playerPos.getX(), playerPos.getY(), playerPos.getZ());

                int radius = 5; // Radius to copy blocks around the player

                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {
                        for (int y = playerPos.getY() - 4; y <= playerPos.getY() + 4; y++) {
                            BlockPos realPos = new BlockPos(playerPos.getX() + x, y, playerPos.getZ() + z);
                            BlockState state = overworld.getBlockState(realPos);
                            spiritWorld.setBlock(realPos, state, 2); // Copy block state to Spirit World
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public void onPlayerMoveEvent(MovementInputUpdateEvent moveEvent) {
        if (moveEvent.getEntity() instanceof Player) {
            Player player = moveEvent.getEntity();
            Level world = player.getCommandSenderWorld();
            IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
            if (server != null) {
                Level spiritWorld = Minecraft.getInstance().getSingleplayerServer().getLevel(SpiritWorld.SPIRIT_WORLD_LEVEL_KEY);

                if (world.equals(SpiritWorld.SPIRIT_WORLD_LEVEL_KEY)) {
                    double xDiff = playerPosition.x - player.getX();
                    double zDiff = playerPosition.z - player.getZ();
                    for (int x = 0; x < xDiff; x++) {
                        for (int z = 0; z < zDiff; z++) {
                            for (int y = (int) playerPosition.y; y < world.getHeight(); y++) {
                                BlockPos realPos = new BlockPos((int) (playerPosition.x + x), y, (int) (playerPosition.z + z));
                                BlockState state = world.getBlockState(realPos);
                                spiritWorld.setBlock(realPos, state, 2);//maybe anders
                            }
                        }
                    }
                }
            }
        }

    }
}
