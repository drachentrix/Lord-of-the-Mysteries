package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.C;
import org.drachentrix.plugins.lordofthemysteries.client.Beyonder;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;
import org.drachentrix.plugins.lordofthemysteries.common.utils.AbilityRegistry;

import java.util.ConcurrentModificationException;


public class FlashTarget extends Ability {

    public FlashTarget(String name, int spiritualityUse, int sequence) {
        super(name, spiritualityUse, sequence);
        AbilityRegistry.registerAbility(name, this::fromNBT);

    }

    public FlashTarget(int spiritualityUse, int sequence) {
        super("Flash Target", spiritualityUse, sequence);
        AbilityRegistry.registerAbility("Flash Target", this::fromNBT);
    }

    @Override
    public Ability fromNBT(CompoundTag nbt) {
        String name = nbt.getString("name");
        int sequence = nbt.getInt("sequence");
        int spiriualityUse = nbt.getInt("spiritualityUse");
        return new FlashTarget(name, spiriualityUse, sequence);
    }

    @Override
    public boolean onAbilityUse(LivingEntity player) {
        Vec3 playerPos = player.getEyePosition();
        Vec3 lookVector = player.getLookAngle();
        int reachDistance = 50;
        double increment = 1.0;

        boolean foundBlockOrEntity = false;
        BlockPos targetBlock = null;

        for (double distance = 0; distance < reachDistance; distance += increment) {
            Vec3 rayEnd = playerPos.add(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance);
            BlockHitResult result = player.getCommandSenderWorld().clip(new ClipContext(playerPos, rayEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            if (result.getType() == HitResult.Type.BLOCK || result.getType() == HitResult.Type.ENTITY) {
                targetBlock = result.getBlockPos();
                break;
            }
        }

        if (!foundBlockOrEntity) {
            Vec3 rayEnd = playerPos.add(lookVector.x * reachDistance, lookVector.y * reachDistance, lookVector.z * reachDistance);
            BlockHitResult result = player.getCommandSenderWorld().clip(new ClipContext(playerPos, rayEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            if (result.getType() == HitResult.Type.BLOCK) {
                targetBlock = result.getBlockPos();
            }
        }
        if (targetBlock != null) {
            try {
                Level serverLevel = Minecraft.getInstance().getSingleplayerServer().getLevel(player.level().dimension());
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);

                lightningBolt.setPos(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ());
                lightningBolt.setDamage(1f * (Beyonder.getSequence() % 9));
                lightningBolt.setSecondsOnFire(1);
                serverLevel.addFreshEntity(lightningBolt);
                return true;

            } catch (NullPointerException | ConcurrentModificationException ignored) {
            }
        }
        return false;
    }
}
