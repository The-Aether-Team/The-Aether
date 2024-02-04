package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherKeys {
    public final static KeyMapping OPEN_ACCESSORY_INVENTORY = new KeyMapping("key.aether.open_accessories.desc", GLFW.GLFW_KEY_I, "key.aether.category");
    public final static KeyMapping GRAVITITE_JUMP_ABILITY = new KeyMapping("key.aether.gravitite_jump_ability.desc", GLFW.GLFW_KEY_SPACE, "key.aether.category");
    public final static KeyMapping INVISIBILITY_TOGGLE = new KeyMapping("key.aether.invisibility_toggle.desc", GLFW.GLFW_KEY_V, "key.aether.category");

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_ACCESSORY_INVENTORY);
        event.register(GRAVITITE_JUMP_ABILITY);
        event.register(INVISIBILITY_TOGGLE);
    }
}
