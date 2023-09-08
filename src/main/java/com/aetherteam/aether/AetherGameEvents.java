package com.aetherteam.aether;

import net.minecraft.core.Registry;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AetherGameEvents {
    public static final DeferredRegister<GameEvent> GAME_EVENTS = DeferredRegister.create(Registry.GAME_EVENT_REGISTRY, Aether.MODID);

    public static final RegistryObject<GameEvent> ICESTONE_FREEZABLE_UPDATE = GAME_EVENTS.register("icestone_freezable_update", () -> new GameEvent("icestone_freezable_update", 4));
}
