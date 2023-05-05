package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherKeys {
    public final static KeyMapping OPEN_ACCESSORY_INVENTORY = new KeyMapping("key.aether.open_accessories.desc", GLFW.GLFW_KEY_I, "key.aether.category");
    public final static KeyMapping GRAVITITE_JUMP_ABILITY = new KeyMapping("key.aether.gravitite_jump_ability.desc", GLFW.GLFW_KEY_SPACE, "key.aether.category");

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_ACCESSORY_INVENTORY);
        event.register(GRAVITITE_JUMP_ABILITY);
    }
}
