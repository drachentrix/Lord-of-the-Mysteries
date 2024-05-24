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
            if (tickCounter >= 1 * 20) {
                tickCounter = 0;

                checkPlayerMove(Minecraft.getInstance().player);
            }
        }
    }
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        //das ist possible, aber maybe schauen, wo das lowest level ist um das umzustellen und dann preventen
        if (event.phase == TickEvent.Phase.END) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level.dimension().equals(SpiritWorld.SPIRIT_WORLD_LEVEL_KEY) && minecraft.player != null) {
                int currentRenderDistance = minecraft.options.renderDistance().get();

                if (currentRenderDistance != 2) {
                    Minecraft.getInstance().options.renderDistance().set(2);
                }
            }
        }
    }

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
                        for (int x = -xDiff - 19; x < xDiff + 19; x++) {
                            for (int z = -zDiff - 19; z < zDiff + 19; z++) {
                                    for (int y = -yDiff - 5; y < yDiff + 8; y++) {
                                    BlockPos realPos = new BlockPos((ClientEvents.playerPosition.getX() + x), ClientEvents.playerPosition.getY() + y, (ClientEvents.playerPosition.getZ() + z));
                                    BlockState state = world.getBlockState(realPos);
                                    spiritWorld.setBlock(realPos, state, 2);
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
