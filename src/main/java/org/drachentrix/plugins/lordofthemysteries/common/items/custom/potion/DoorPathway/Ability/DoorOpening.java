package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.DoorPathway.Ability;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;

public class DoorOpening extends Ability{
    public DoorOpening() {
        super("Door Opening", 100, 9);
    }

    @Override
    public void onAbilityUse(LivingEntity player) {

        int playerX = player.getBlockX();
        int playerY = player.getBlockY();
        int playerZ = player.getBlockZ();
        Level world = player.getCommandSenderWorld();

        if (world.getBlockState(new BlockPos(playerX+1, playerY, playerZ)).getBlock() == Blocks.AIR){
            Minecraft.getInstance().player.displayClientMessage(Component.literal("There is no wall to phase through"), true);

        }
        for (int i = 2; i <= 4; i++) {
            if (world.getBlockState(new BlockPos(playerX+i, playerY, playerZ)).getBlock() == Blocks.AIR){
                player.teleportTo(playerX+i, playerY, playerZ);
                break;
            }
        }
    }
}
