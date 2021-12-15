package com.gildedgames.aether.client.registry;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class AetherKeys
{
    public static KeyBinding openAccessoryInventory;

    public static void registerKeys() {
        openAccessoryInventory = registerKeybinding(new KeyBinding("key.aether.open_accessories.desc", GLFW.GLFW_KEY_I, "key.aether.category"));
    }

    private static KeyBinding registerKeybinding(KeyBinding key) {
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
