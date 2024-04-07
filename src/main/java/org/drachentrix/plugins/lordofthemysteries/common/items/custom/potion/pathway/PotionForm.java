package org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.pathway;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.drachentrix.plugins.lordofthemysteries.client.Beyonder;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;

import java.util.List;
import java.util.Random;

public class PotionForm extends Item {
    private final List<Ingredient> ingredients;
    private final String name;
    private final int sequence;
    private final List<Ability> abilityList;
    private final String pathway;

    public PotionForm(List<Ingredient> ingredients, String name, int sequence, List<Ability> abilityList, String pathway, Properties properties) {
        super(properties);
        this.ingredients = ingredients;
        this.name = name;
        this.sequence = sequence;
        this.abilityList = abilityList;
        this.pathway = pathway;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            if (Beyonder.getPathway() == null) {
                Beyonder.setPathway(pathway);
                Beyonder.setSequence(sequence);
                abilityList.forEach(Beyonder::addAbility);
                player.displayClientMessage(Component.literal("You successfully became a sequence 9 Beyonder of the Pathway " + pathway), true);
            } else {
                if (!pathway.equals(Beyonder.getPathway())) {
                    player.displayClientMessage(
                            Component.literal("You feel the Madness devouring you.\n You shouldn't have done that!"), true);
                    Beyonder.setSanity(new Random().nextInt(0, 6));
                } else {
                    if (sequence > Beyonder.getSequence()) {
                        if (sequence - 1 == Beyonder.getSequence()) {
                            Beyonder.setSequence(sequence);
                        } else {
                            if (new Random().nextInt(0, 101) < 5) {
                                player.displayClientMessage(
                                        Component.literal("You're craziness has paid off, " +
                                                "you succeeded in advancing from Sequence " + Beyonder.getSequence() + " to " + sequence + "!\n" +
                                                "But you have changed..."), true);
                                Beyonder.setSanity(Beyonder.getSanity() - new Random().nextInt(3, 10) * Math.abs(Beyonder.getSequence() - sequence));
                                Beyonder.setSequence(sequence);
                            }
                        }
                    }
                }
            }
        }
        return super.use(level, player, interactionHand);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }

    public int getSequence() {
        return sequence;
    }

    public List<Ability> getAbilityList() {
        return abilityList;
    }
}
