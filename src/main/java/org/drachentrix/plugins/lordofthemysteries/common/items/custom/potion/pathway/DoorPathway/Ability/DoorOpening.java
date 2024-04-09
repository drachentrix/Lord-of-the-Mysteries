package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
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


        if (world.getBlockState(new BlockPos(playerX+1, playerY+2, playerZ)).getBlock() == Blocks.AIR){
            Minecraft.getInstance().player.displayClientMessage(Component.literal("There is no wall to phase through"), true);
            return;

        }
        for (int i = 2; i <= 6; i++) {
            if (world.getBlockState(new BlockPos(playerX+i, playerY+2, playerZ)).getBlock() == Blocks.AIR){
                player.setPos(playerX+i, playerY, playerZ);
                break;
            }
        }
        //if (player.getBlockX() == playerX && player.getBlockY() == playerY && player.getBlockZ() == playerZ){
           // Minecraft.getInstance().player.displayClientMessage(Component.literal("The wall seems to be to thick"), true);
        //}
    }
}
