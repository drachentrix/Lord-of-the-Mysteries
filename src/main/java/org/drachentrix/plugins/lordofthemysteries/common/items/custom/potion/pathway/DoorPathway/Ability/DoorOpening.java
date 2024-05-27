package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;
import org.drachentrix.plugins.lordofthemysteries.common.utils.AbilityRegistry;

public class DoorOpening extends Ability{

    public DoorOpening(String name, int spiritualityUse, int sequence) {
        super(name, spiritualityUse, sequence);
        AbilityRegistry.registerAbility(name, this::fromNBT);
    }

    public DoorOpening(int spiritualityUse, int sequence) {
        super("Door Opening", spiritualityUse, sequence);
        AbilityRegistry.registerAbility("Door Opening", this::fromNBT);
    }

    @Override
    public void onAbilityUse(LivingEntity player) {

        int playerX = player.getBlockX();
        int playerY = player.getBlockY();
        int playerZ = player.getBlockZ();
        Level world = player.getCommandSenderWorld();


        if (world.getBlockState(new BlockPos(playerX+1, playerY+1, playerZ)).getBlock() == Blocks.AIR){
            Minecraft.getInstance().player.displayClientMessage(Component.literal("There is no wall to phase through"), true);
            return;

        }
        for (int i = 2; i <= 6; i++) {
            if (world.getBlockState(new BlockPos(playerX+i, playerY+1, playerZ)).getBlock() == Blocks.AIR){
                player.setPos(playerX+i+1, playerY, playerZ);
                return;
            }
        }
        Minecraft.getInstance().player.displayClientMessage(Component.literal("The wall seems to be to thick"), true);

    }

    @Override
    public Ability fromNBT(CompoundTag nbt) {
        String name = nbt.getString("name");
        int sequence = nbt.getInt("sequence");
        int spiriualityUse = nbt.getInt("spiritualityUse");
        return new DoorOpening(name, spiriualityUse,sequence);
    }
}
