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

public class PlayerMoveOnTicks {
    private int tickCounter = 0;

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            tickCounter++;

            // Check every 1 seconds (20 ticks per second in Minecraft)
            if (tickCounter >= 2 * 20) {
                tickCounter = 0;

                checkPlayerMove(Minecraft.getInstance().player);
            }
        }
    }
  //maybe try mit render Event
    public void checkPlayerMove(Player player) {
        if (ClientEvents.playerPosition != null) {

            Level world = Minecraft.getInstance().getSingleplayerServer().getLevel(Level.OVERWORLD);

            IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
            if (server != null) {
                Level spiritWorld = Minecraft.getInstance().getSingleplayerServer().getLevel(SpiritWorld.SPIRIT_WORLD_LEVEL_KEY);
                if (player.level().dimension().equals(spiritWorld.dimension())) {
                    int xDiff = (int) Math.abs(ClientEvents.playerPosition.getX() - player.getX());
                    int zDiff = (int) Math.abs(ClientEvents.playerPosition.getZ() - player.getZ());
                    int yDiff = (int) Math.abs(ClientEvents.playerPosition.getY() - player.getY());
                    if (xDiff > 1 || zDiff > 1 || yDiff > 1) {
                        for (int x = -xDiff - 7; x < xDiff + 7; x++) {
                            for (int z = -zDiff - 7; z < zDiff + 7; z++) {
                                for (int y = -yDiff - 2; y < yDiff + 7; y++) {
                                    //todo blockstate in setblock null, warum er das nicht macht, checken später
                                    BlockPos realPos = new BlockPos((ClientEvents.playerPosition.getX() + x), ClientEvents.playerPosition.getY() + y, (ClientEvents.playerPosition.getZ() + z));
                                    BlockState state = world.getBlockState(realPos);
                                    spiritWorld.setBlock(realPos, state, 1);
                                }
                            }

                        }
                    }
                    ClientEvents.playerPosition = player.blockPosition();
                }
            }

        }

    }
}
