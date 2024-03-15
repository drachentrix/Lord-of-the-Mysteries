package org.drachentrix.plugins.lordofthemysteries.client;

import org.drachentrix.plugins.lordofthemysteries.common.utils.Ability;

import java.util.ArrayList;
import java.util.List;

public class Beyonder{
    private static List<Ability> abilityList = new ArrayList<>();
    private static Ability selectedAbility;
    private static int sequence;
    private static String pathway; //todo maybe mit enum oder feiner als string lösen
    private static double sanity = 100;

    public static List<Ability> getAbilityList() {
        return abilityList;
    }

    public static void addAbility(Ability ability) {
        abilityList.add(ability);
    }

    public static Ability getSelectedAbility() {
        return selectedAbility;
    }

    public static void setSelectedAbility(Ability switchAbility) {
        selectedAbility = switchAbility;
    }

    public double getSanity() {
        return sanity;
    }

    public void setSanity(double newSanity) {
        sanity = newSanity;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int updatedSequence) {
        sequence = updatedSequence;
    }

    public String getPathway() {
        return pathway;
    }

    public void setPathway(String newPathway) {
        pathway = newPathway;
    }
}
