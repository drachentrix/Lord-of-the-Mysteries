package org.drachentrix.plugins.lordofthemysteries.common.utils;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.drachentrix.plugins.lordofthemysteries.LordOfTheMysteries;
import org.lwjgl.glfw.GLFW;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class KeyBinding {

    public static final String KEY_CATEGORY_ABILITY = "key." + LordOfTheMysteries.MODID + ".ability";
    public static final String KEY_ABILITY_USE = "key." + LordOfTheMysteries.MODID + ".ability_use";
    public static final String KEY_ABILITY_CICLE = "key." + LordOfTheMysteries.MODID + ".ability_cycle";
    public static final String KEY_ABILITY_MENU = "key." + LordOfTheMysteries.MODID + ".ability_menu";
    public static final String KEY_SWITCH_DIMENSION = "key." + LordOfTheMysteries.MODID + ".switch_dimension";

    public static final KeyMapping ABILITY_USE_KEY = new KeyMapping(KEY_ABILITY_USE, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, KEY_CATEGORY_ABILITY );

    public static final KeyMapping ABILITY_CICLE_KEY = new KeyMapping(KEY_ABILITY_CICLE, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, KEY_CATEGORY_ABILITY );

    public static final KeyMapping ABILITY_MENU_KEY = new KeyMapping(KEY_ABILITY_MENU, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_ABILITY );

    public static final KeyMapping ABILIY_SWITCH_DIMENSION = new KeyMapping(KEY_SWITCH_DIMENSION, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P, KEY_CATEGORY_ABILITY );
}
