package com.gildedgames.aether.client.registry;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class AetherKeys
{
    public static KeyMapping openAccessoryInventory;

    public static void registerKeys() {
        openAccessoryInventory = registerKeybinding(new KeyMapping("key.aether.open_accessories.desc", GLFW.GLFW_KEY_I, "key.aether.category"));
    }

    private static KeyMapping registerKeybinding(KeyMapping key) {
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
