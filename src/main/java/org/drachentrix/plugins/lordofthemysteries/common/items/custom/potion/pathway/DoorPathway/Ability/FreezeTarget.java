package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;
import org.drachentrix.plugins.lordofthemysteries.common.utils.AbilityRegistry;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Scheduler;

import java.util.List;
import java.util.stream.Collectors;

public class FreezeTarget extends Ability {
    public FreezeTarget(String name, int spiritualityUse, int sequence) {
        super(name, spiritualityUse, sequence);
        AbilityRegistry.registerAbility(name, this::fromNBT);
    }

    public FreezeTarget(int spiritualityUse, int sequence) {
        super("Freeze Target", spiritualityUse, sequence);
        AbilityRegistry.registerAbility("Freeze Target", this::fromNBT);
    }

    @Override
    public Ability fromNBT(CompoundTag nbt) {
        String name = nbt.getString("name");
        int sequence = nbt.getInt("sequence");
        int spiriualityUse = nbt.getInt("spiritualityUse");
        return new FreezeTarget(name, spiriualityUse, sequence);
    }

    @Override
    public void onAbilityUse(LivingEntity player) {
        Vec3 lookVec = player.getLookAngle();
        double rayLength = 12;
        Vec3 eyePosition = player.getEyePosition(1.5f);
        Vec3 rayEnd = eyePosition.add(lookVec.x * rayLength, lookVec.y * rayLength, lookVec.z * rayLength);

        // Calculate the bounding box from the player's position to the ray end point
        AABB boundingBox = new AABB(eyePosition, rayEnd);

        // Get entities within the bounding box
        List<Entity> entitiesInRay = player.level().getEntities(player, boundingBox, entity -> true);

        // Filter entities based on the specified conditions
        List<Entity> filteredEntities = entitiesInRay.stream()
                .filter(entity -> isWithinDistanceAndHeight((Player) player, entity, 20, 10))
                .toList();

        // Apply your effect to the filtered entities
        for (Entity entity : filteredEntities) {
            if (entity instanceof LivingEntity) {
                Player player1 = (Player) player;
                player1.displayClientMessage(Component.literal("Entity  reached"), true);
                stopMovement((LivingEntity) entity, 260);

            } else {
                Player player1 = (Player) player;
                player1.displayClientMessage(Component.literal("Entity not reached"), true);
            }
        }
    }

    private static boolean isWithinDistanceAndHeight(Player player, Entity entity, double maxDistance, double maxHeightDifference) {
        Vec3 playerPosition = player.position();
        Vec3 entityPosition = entity.position();
        Vec3 lookVec = player.getLookAngle();

        // Calculate the vector from the player to the entity
        Vec3 toEntityVec = entityPosition.subtract(playerPosition);

        // Project the vector onto the look vector
        double projectionLength = toEntityVec.dot(lookVec);
        if (projectionLength < 0 || projectionLength > maxDistance) {
            return false;
        }

        // Calculate the perpendicular distance
        Vec3 perpendicularVec = toEntityVec.subtract(lookVec.scale(projectionLength));
        double perpendicularDistance = perpendicularVec.length();
        if (perpendicularDistance > maxDistance) {
            return false;
        }

        // Check the height difference
        double heightDifference = Math.abs(playerPosition.y - entityPosition.y);
        return heightDifference <= maxHeightDifference;
    }


    public void stopMovement(LivingEntity entity, int durationInTicks) {
        double motionX = entity.getDeltaMovement().x();
        double motionY = entity.getDeltaMovement().y();
        double motionZ = entity.getDeltaMovement().z();
        float speed = entity.getSpeed();

        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, Integer.MAX_VALUE, 4));

        Scheduler.schedule(() -> {
           entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
       }, durationInTicks);
    }
}
