package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway.DoorPathway.Ability;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

class Utils {

    public static List<Entity> rayCastEntitiesInRange(Player player){
        double rayLength = 12;

        Vec3 lookVec = player.getLookAngle();
        Vec3 eyePosition = player.getEyePosition(1.5f);
        Vec3 rayEnd = eyePosition.add(lookVec.x * rayLength, lookVec.y * rayLength, lookVec.z * rayLength);

        AABB boundingBox = new AABB(eyePosition, rayEnd);

        List<Entity> entitiesInRay = player.level().getEntities(player, boundingBox, entity -> true);
        return entitiesInRay.stream()
                .filter(entity -> isWithinDistanceAndHeight(player, entity, 20, 10))
                .toList();
    }
    public static boolean isWithinDistanceAndHeight(Player player, Entity entity, double maxDistance, double maxHeightDifference) {
        Vec3 playerPosition = player.position();
        Vec3 entityPosition = entity.position();
        Vec3 lookVec = player.getLookAngle();

        Vec3 toEntityVec = entityPosition.subtract(playerPosition);

        double projectionLength = toEntityVec.dot(lookVec);
        if (projectionLength < 0 || projectionLength > maxDistance) {
            return false;
        }

        Vec3 perpendicularVec = toEntityVec.subtract(lookVec.scale(projectionLength));
        double perpendicularDistance = perpendicularVec.length();
        if (perpendicularDistance > maxDistance) {
            return false;
        }

        double heightDifference = Math.abs(playerPosition.y - entityPosition.y);
        return heightDifference <= maxHeightDifference;
    }
}
