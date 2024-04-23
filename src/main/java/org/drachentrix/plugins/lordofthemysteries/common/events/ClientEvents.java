package org.drachentrix.plugins.lordofthemysteries.common.events;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.drachentrix.plugins.lordofthemysteries.LordOfTheMysteries;
import org.drachentrix.plugins.lordofthemysteries.client.Beyonder;
import org.drachentrix.plugins.lordofthemysteries.common.utils.KeyBinding;
import org.drachentrix.plugins.lordofthemysteries.common.world.SpiritWorld;

@Mod.EventBusSubscriber(modid = LordOfTheMysteries.MODID, value = Dist.CLIENT)
public class ClientEvents {
    private static Vec3 playerPosition;

    @SubscribeEvent
    public static void registerKey(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.ABILITY_CICLE_KEY);
        event.register(KeyBinding.ABILITY_MENU_KEY);
        event.register(KeyBinding.ABILITY_USE_KEY);
        event.register(KeyBinding.ABILIY_SWITCH_DIMENSION);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key key) {
        if(Beyonder.getPathway() != null) {
            Minecraft minecraft = Minecraft.getInstance();
            if (KeyBinding.ABILIY_SWITCH_DIMENSION.isDown()) {
                KeyBinding.ABILIY_SWITCH_DIMENSION.consumeClick();

            }
            if (KeyBinding.ABILITY_USE_KEY.isDown()) {
                KeyBinding.ABILITY_USE_KEY.consumeClick();
                Beyonder.getSelectedAbility().onAbilityUse(minecraft.player);
                //minecraft.player.displayClientMessage(Component.literal(Beyonder.getSelectedAbility().toString()), true);
            } else if (KeyBinding.ABILITY_CICLE_KEY.isDown()) {
                KeyBinding.ABILITY_CICLE_KEY.consumeClick();
                int nextAbilityIndex = Beyonder.getAbilityList().indexOf(Beyonder.getSelectedAbility())+1;
                Beyonder.setSelectedAbility(Beyonder.getAbilityList().get(nextAbilityIndex < Beyonder.getAbilityList().size() ? nextAbilityIndex : 0));
                Minecraft.getInstance().player.displayClientMessage(Component.literal(Beyonder.getSelectedAbility().toString()), true);
            }
        }
    }

    @SubscribeEvent
    public void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getTo().equals(SpiritWorld.SPIRIT_WORLD_LEVEL_KEY)){
            LivingEntity player = event.getEntity();
            Level world = player.getCommandSenderWorld();
            Level spiritWorld =  player.getServer().getLevel(SpiritWorld.SPIRIT_WORLD_LEVEL_KEY);

            BlockPos playerPos = player.getOnPos();
            playerPosition = new Vec3(playerPos.getX(), playerPos.getY(), playerPos.getZ());
            for (int x = -25; x < 25; x++) {
                for (int z = -25; z < 25; z++) {
                    for (int y = playerPos.getY()- 4; y < world.getHeight(); y++) {
                        BlockPos realPos = new BlockPos(playerPos.getX() + x,  y, playerPos.getZ() + z);
                        BlockState state = world.getBlockState(realPos);
                        spiritWorld.setBlock(realPos, state, 2);//maybe anders
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public void onPlayerMoveEvent(MovementInputUpdateEvent moveEvent){
        if (moveEvent.getEntity() instanceof Player) {
            Player player = (Player) moveEvent.getEntity();
            Level world = player.getCommandSenderWorld();
            Level spiritWorld =  player.getServer().getLevel(SpiritWorld.SPIRIT_WORLD_LEVEL_KEY);

            if(world.equals(SpiritWorld.SPIRIT_WORLD_LEVEL_KEY)){
                double xDiff = playerPosition.x - player.getX();
                double zDiff = playerPosition.z - player.getZ();
                for (int x = 0; x < xDiff; x++) {
                    for (int z = 0; z < zDiff; z++) {
                        for (int y = (int) playerPosition.y; y < world.getHeight(); y++) {
                            BlockPos realPos = new BlockPos((int) (playerPosition.x + x),  y, (int) (playerPosition.z + z));
                            BlockState state = world.getBlockState(realPos);
                            spiritWorld.setBlock(realPos, state, 2);//maybe anders
                        }
                    }
                }
            }
        }

    }
}
