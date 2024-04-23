package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.C;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;


public class FlashTarget extends Ability {

    public FlashTarget( int spiritualityUse, int sequence) {
        super("Flash", spiritualityUse, sequence);
    }

    @Override
    public void onAbilityUse(LivingEntity player) {
        Vec3 playerPos = player.getEyePosition();
        Vec3 lookVector = player.getLookAngle();
        int reachDistance = 50;
        double increment = 1.0;

        boolean foundBlockOrEntity = false;
        BlockPos targetBlock = null;

        for(double distance = 0; distance < reachDistance; distance += increment) {
            Vec3 rayEnd = playerPos.add(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance);
            BlockHitResult result = player.getCommandSenderWorld().clip(new ClipContext(playerPos,rayEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            if (result.getType() == HitResult.Type.BLOCK || result.getType() == HitResult.Type.ENTITY ){
                targetBlock = result.getBlockPos();
                break;
            }
        }

        if(!foundBlockOrEntity) {
            Vec3 rayEnd = playerPos.add(lookVector.x  * reachDistance, lookVector.y  * reachDistance, lookVector.z  * reachDistance);
            BlockHitResult result = player.getCommandSenderWorld().clip(new ClipContext(playerPos,rayEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            if (result.getType() == HitResult.Type.BLOCK){
                targetBlock = result.getBlockPos();
            }
        }
        if (targetBlock != null) {
            for (int i = 0; i < 10; i++) {
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, player.getCommandSenderWorld());
                lightningBolt.setPos(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ());
                Zombie zombie = new Zombie(EntityType.ZOMBIE, player.getCommandSenderWorld());
                zombie.setPos(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ());
                player.getCommandSenderWorld().addFreshEntity(zombie);
            }
        }

    }
}
