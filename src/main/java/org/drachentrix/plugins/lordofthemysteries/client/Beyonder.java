package org.drachentrix.plugins.lordofthemysteries.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.drachentrix.plugins.lordofthemysteries.common.items.custom.potion.Sequences;
import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;

import java.util.ArrayList;
import java.util.List;

public class Beyonder{
    private static List<Ability> abilityList = new ArrayList<>();
    private static Ability selectedAbility;
    private static int sequence;
    private static String pathway; //todo maybe mit enum oder feiner als string lösen
    private static double sanity = 100;


    public static void reset(){
        abilityList.clear();
        selectedAbility = null;
        sequence = -1;
        pathway = null;
    }

    public static List<Ability> getAbilityList() {
        return abilityList;
    }
    public static void loadAbilitys(){
        if (abilityList.isEmpty()) {
            for (int i = 9; i >= sequence; i--) {
                int finalI = i;
                Sequences.getAllSequences().stream().filter(sequences -> sequences.getPathway().equals(getPathway()))
                        .filter(sequences -> sequences.getSequence() == finalI).forEach(abilityList1 -> abilityList1.getAbilityList().forEach(Beyonder::addAbility));
            }
        }
    }
    public static void addAbility(Ability ability) {
        abilityList.add(ability);
    }

    public static Ability getSelectedAbility() {
        return selectedAbility == null ? abilityList.get(0) : selectedAbility;
    }

    public static void setSelectedAbility(Ability switchAbility) {
        selectedAbility = switchAbility;
    }

    public static double getSanity() {
        return sanity;
    }

    public static void setSanity(double newSanity) {
        if (newSanity < 0){
            sanity = 0;
            return;
        }
        sanity = newSanity;
    }

    public static int getSequence() {
        return sequence;
    }

    public static void setSequence(int updatedSequence) {
        sequence = updatedSequence;
    }

    public static String getPathway() {
        return pathway;
    }

    public static void setPathway(String newPathway) {
        pathway = newPathway;
    }
}
