package com.aetherteam.aether;

import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.gameevent.GameEvent;

public class AetherGameEvents {
    public static final LazyRegistrar<GameEvent> GAME_EVENTS = LazyRegistrar.create(Registries.GAME_EVENT, Aether.MODID);

    public static final RegistryObject<GameEvent> ICESTONE_FREEZABLE_UPDATE = GAME_EVENTS.register("icestone_freezable_update", () -> new GameEvent("icestone_freezable_update", 4));
}
