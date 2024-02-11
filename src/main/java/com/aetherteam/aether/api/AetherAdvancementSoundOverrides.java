package com.aetherteam.aether.api;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.registers.AdvancementSoundOverride;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

public class AetherAdvancementSoundOverrides {
    public static final ResourceKey<Registry<AdvancementSoundOverride>> ADVANCEMENT_SOUND_OVERRIDE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Aether.MODID, "advancement_sound_override"));
    public static final DeferredRegister<AdvancementSoundOverride> ADVANCEMENT_SOUND_OVERRIDES = DeferredRegister.create(ADVANCEMENT_SOUND_OVERRIDE_REGISTRY_KEY, Aether.MODID);
    public static final Supplier<IForgeRegistry<AdvancementSoundOverride>> ADVANCEMENT_SOUND_OVERRIDE_REGISTRY = ADVANCEMENT_SOUND_OVERRIDES.makeRegistry(() -> new RegistryBuilder<AdvancementSoundOverride>().hasTags());

    public static final RegistryObject<AdvancementSoundOverride> BRONZE_DUNGEON = ADVANCEMENT_SOUND_OVERRIDES.register("bronze_dungeon", () -> new AdvancementSoundOverride(s -> s.equals("bronze_dungeon"), AetherSoundEvents.UI_TOAST_AETHER_BRONZE));
    public static final RegistryObject<AdvancementSoundOverride> SILVER_DUNGEON = ADVANCEMENT_SOUND_OVERRIDES.register("silver_dungeon", () -> new AdvancementSoundOverride(s -> s.equals("silver_dungeon"), AetherSoundEvents.UI_TOAST_AETHER_SILVER));

    @Nullable
    public static AdvancementSoundOverride get(String id) {
        return ADVANCEMENT_SOUND_OVERRIDE_REGISTRY.get().getValue(new ResourceLocation(id));
    }
}
