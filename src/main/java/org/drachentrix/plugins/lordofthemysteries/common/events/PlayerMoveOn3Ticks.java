package org.drachentrix.plugins.lordofthemysteries.common.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.drachentrix.plugins.lordofthemysteries.common.world.SpiritWorld;

public class PlayerMoveOn3Ticks {
    private int tickCounter = 0;

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            tickCounter++;

            // Check every 25 seconds (20 ticks per second in Minecraft)
            if (tickCounter >= 3 * 20) {
                tickCounter = 0; // Reset tick counter

                checkPlayerMove(Minecraft.getInstance().player);
            }
        }
    }

    public void checkPlayerMove(Player player) {
        if (ClientEvents.playerPosition != null) {
            if (Math.abs(player.distanceToSqr(ClientEvents.playerPosition.getX(), ClientEvents.playerPosition.getY(), ClientEvents.playerPosition.getZ())) > 0.5) {
                Level world = Minecraft.getInstance().getSingleplayerServer().getLevel(Level.OVERWORLD);
                IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
                if (server != null) {
                    Level spiritWorld = Minecraft.getInstance().getSingleplayerServer().getLevel(SpiritWorld.SPIRIT_WORLD_LEVEL_KEY);
                    if (player.level().dimension().equals(spiritWorld.dimension())) {
                        int xDiff = (int) Math.abs(ClientEvents.playerPosition.getX() - player.getX());
                        int zDiff = (int) Math.abs(ClientEvents.playerPosition.getZ() - player.getZ());
                        int yDiff = (int) Math.abs(ClientEvents.playerPosition.getY() - player.getY());

                        for (int x = -xDiff - 2; x < xDiff + 2; x++) {
                            for (int z = -zDiff - 2; z < zDiff + 2; z++) {
                                for (int y = -yDiff - 2; y < yDiff + 2; y++) {
                                    BlockPos realPos = new BlockPos((ClientEvents.playerPosition.getX() + x), y, (ClientEvents.playerPosition.getZ() + z));
                                    BlockState state = world.getBlockState(realPos);
                                    spiritWorld.setBlock(realPos, state, 2);
                                }
                            }

                        }
                        ClientEvents.playerPosition = player.blockPosition();
                    }
                }
            }
        }

    }
}
