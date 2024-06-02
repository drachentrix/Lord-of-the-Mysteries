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

public class DoorOpening extends Ability {

    public DoorOpening(String name, int spiritualityUse, int sequence) {
        super(name, spiritualityUse, sequence);
        AbilityRegistry.registerAbility(name, this::fromNBT);
    }

    public DoorOpening(int spiritualityUse, int sequence) {
        super("Door Opening", spiritualityUse, sequence);
        AbilityRegistry.registerAbility("Door Opening", this::fromNBT);
    }

    @Override
    public boolean onAbilityUse(LivingEntity player) {

        double playerX = player.getBlockX();
        double playerY = player.getBlockY();
        double playerZ = player.getBlockZ();
        Level world = player.getCommandSenderWorld();

        double lookX = player.getLookAngle().x;
        double lookY = player.getLookAngle().y ;
        double lookZ = player.getLookAngle().z;

        BlockPos posToCheck = new BlockPos((int) Math.round(playerX + lookX), (int) Math.round(playerY + player.getEyeHeight() + lookY), (int) Math.round(playerZ + lookZ));
        if (world.getBlockState(posToCheck).getBlock() == Blocks.AIR) {
            Minecraft.getInstance().player.displayClientMessage(Component.literal("There is no wall to phase through"), true);
            return false;
        }

        for (int i = 2; i <= 6; i++) { //problem mit manchen richtungen. Funktioniert ned ganz in alle
            lookX = player.getLookAngle().x * i;
            lookY = player.getLookAngle().y * i;
            lookZ = player.getLookAngle().z * i;
            posToCheck = new BlockPos((int) Math.round(playerX + lookX), (int) Math.round(playerY + player.getEyeHeight() + lookY), (int) Math.round(playerZ + lookZ));
            if (world.getBlockState(posToCheck).getBlock() == Blocks.AIR) {
                player.setPosRaw((int) Math.round(playerX + lookX), (int) Math.round(playerY + player.getEyeHeight() + lookY), (int) Math.round(playerZ + lookZ));
                return true;
            }
        }
        return false;
    }

    @Override
    public Ability fromNBT(CompoundTag nbt) {
        String name = nbt.getString("name");
        int sequence = nbt.getInt("sequence");
        int spiriualityUse = nbt.getInt("spiritualityUse");
        return new DoorOpening(name, spiriualityUse, sequence);
    }
}
