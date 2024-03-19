package com.aetherteam.aether.api;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.registers.AdvancementSoundOverride;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class AetherAdvancementSoundOverrides {
    public static final ResourceKey<Registry<AdvancementSoundOverride>> ADVANCEMENT_SOUND_OVERRIDE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Aether.MODID, "advancement_sound_override"));
    public static final DeferredRegister<AdvancementSoundOverride> ADVANCEMENT_SOUND_OVERRIDES = DeferredRegister.create(ADVANCEMENT_SOUND_OVERRIDE_REGISTRY_KEY, Aether.MODID);
    public static final Supplier<IForgeRegistry<AdvancementSoundOverride>> ADVANCEMENT_SOUND_OVERRIDE_REGISTRY = ADVANCEMENT_SOUND_OVERRIDES.makeRegistry(() -> new RegistryBuilder<AdvancementSoundOverride>().hasTags());

    public static final RegistryObject<AdvancementSoundOverride> GENERAL = ADVANCEMENT_SOUND_OVERRIDES.register("general", () -> new AdvancementSoundOverride(0, advancement -> checkRoot(advancement, new ResourceLocation(Aether.MODID, "enter_aether")), AetherSoundEvents.UI_TOAST_AETHER_GENERAL));
    public static final RegistryObject<AdvancementSoundOverride> BRONZE_DUNGEON = ADVANCEMENT_SOUND_OVERRIDES.register("bronze_dungeon", () -> new AdvancementSoundOverride(10, advancement -> advancement.getId().getPath().equals("bronze_dungeon"), AetherSoundEvents.UI_TOAST_AETHER_BRONZE));
    public static final RegistryObject<AdvancementSoundOverride> SILVER_DUNGEON = ADVANCEMENT_SOUND_OVERRIDES.register("silver_dungeon", () -> new AdvancementSoundOverride(10, advancement -> advancement.getId().getPath().equals("silver_dungeon"), AetherSoundEvents.UI_TOAST_AETHER_SILVER));

    @Nullable
    public static AdvancementSoundOverride get(String id) {
        return ADVANCEMENT_SOUND_OVERRIDE_REGISTRY.get().getValue(new ResourceLocation(id));
    }

    /**
     * Retrieves the {@link SoundEvent} to use in an override for the given {@link Advancement}.
     * @param advancement The {@link Advancement}.
     * @return The new {@link SoundEvent}.
     */
    @Nullable
    public static SoundEvent retrieveOverride(Advancement advancement) {
        @Nullable AdvancementSoundOverride usedOverride = null;
        for (AdvancementSoundOverride override : AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDE_REGISTRY.get().getEntries().stream().map(Map.Entry::getValue).toList()) {
            if (override.matches(advancement) && (usedOverride == null || override.priority() > usedOverride.priority())) {
                usedOverride = override;
            }
        }
        return usedOverride == null ? null : usedOverride.sound().get();
    }

    /**
     * Checks all the way up to the root of the advancement tree to determine if it matches a given root.
     */
    public static boolean checkRoot(Advancement holder, ResourceLocation root) {
        for (Advancement advancement = holder; advancement != null; advancement = advancement.getParent()) {
            if (advancement.getId().equals(root)) {
                return true;
            }
        }
        return false;
    }
}