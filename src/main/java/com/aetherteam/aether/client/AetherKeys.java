package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class AetherKeys {
    public final static KeyMapping OPEN_ACCESSORY_INVENTORY = new KeyMapping("key.aether.open_accessories.desc", GLFW.GLFW_KEY_I, "key.aether.category");
    public final static KeyMapping GRAVITITE_JUMP_ABILITY = new KeyMapping("key.aether.gravitite_jump_ability.desc", GLFW.GLFW_KEY_SPACE, "key.aether.category");
    public final static KeyMapping INVISIBILITY_TOGGLE = new KeyMapping("key.aether.invisibility_toggle.desc", GLFW.GLFW_KEY_V, "key.aether.category");

    public static void registerKeyMappings() {
        KeyBindingHelper.registerKeyBinding(OPEN_ACCESSORY_INVENTORY);
        KeyBindingHelper.registerKeyBinding(GRAVITITE_JUMP_ABILITY);
        KeyBindingHelper.registerKeyBinding(INVISIBILITY_TOGGLE);
    }
}
