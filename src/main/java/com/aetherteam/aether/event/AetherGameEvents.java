package com.aetherteam.aether.event;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AetherGameEvents {
    public static final DeferredRegister<GameEvent> GAME_EVENTS = DeferredRegister.create(Registries.GAME_EVENT, Aether.MODID);

    public static final RegistryObject<GameEvent> ICESTONE_FREEZABLE_UPDATE = GAME_EVENTS.register("icestone_freezable_update", () -> new GameEvent("icestone_freezable_update", 4));
}
