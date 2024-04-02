package com.aetherteam.aether.client;

import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class AetherKeys {
    public final static KeyMapping OPEN_ACCESSORY_INVENTORY = new KeyMapping("key.aether.open_accessories.desc", GLFW.GLFW_KEY_I, "key.aether.category");
    public final static KeyMapping GRAVITITE_JUMP_ABILITY = new KeyMapping("key.aether.gravitite_jump_ability.desc", GLFW.GLFW_KEY_SPACE, "key.aether.category");
    public final static KeyMapping INVISIBILITY_TOGGLE = new KeyMapping("key.aether.invisibility_toggle.desc", GLFW.GLFW_KEY_V, "key.aether.category");

    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_ACCESSORY_INVENTORY);
        event.register(GRAVITITE_JUMP_ABILITY);
        event.register(INVISIBILITY_TOGGLE);
    }
}
