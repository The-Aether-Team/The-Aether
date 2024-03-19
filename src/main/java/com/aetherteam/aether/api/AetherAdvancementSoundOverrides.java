package com.aetherteam.aether.api;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.registers.AdvancementSoundOverride;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import javax.annotation.Nullable;

public class AetherAdvancementSoundOverrides {
    public static final ResourceKey<Registry<AdvancementSoundOverride>> ADVANCEMENT_SOUND_OVERRIDE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Aether.MODID, "advancement_sound_override"));
    public static final DeferredRegister<AdvancementSoundOverride> ADVANCEMENT_SOUND_OVERRIDES = DeferredRegister.create(ADVANCEMENT_SOUND_OVERRIDE_REGISTRY_KEY, Aether.MODID);
    public static final Registry<AdvancementSoundOverride> ADVANCEMENT_SOUND_OVERRIDE_REGISTRY = new RegistryBuilder<>(ADVANCEMENT_SOUND_OVERRIDE_REGISTRY_KEY).sync(true).create();

    public static final DeferredHolder<AdvancementSoundOverride, AdvancementSoundOverride> GENERAL = ADVANCEMENT_SOUND_OVERRIDES.register("general", () -> new AdvancementSoundOverride(advancement -> checkRoot(advancement, new ResourceLocation(Aether.MODID, "enter_aether")), AetherSoundEvents.UI_TOAST_AETHER_GENERAL));
    public static final DeferredHolder<AdvancementSoundOverride, AdvancementSoundOverride> BRONZE_DUNGEON = ADVANCEMENT_SOUND_OVERRIDES.register("bronze_dungeon", () -> new AdvancementSoundOverride(advancement -> advancement.id().getPath().equals("bronze_dungeon"), AetherSoundEvents.UI_TOAST_AETHER_BRONZE));
    public static final DeferredHolder<AdvancementSoundOverride, AdvancementSoundOverride> SILVER_DUNGEON = ADVANCEMENT_SOUND_OVERRIDES.register("silver_dungeon", () -> new AdvancementSoundOverride(advancement -> advancement.id().getPath().equals("silver_dungeon"), AetherSoundEvents.UI_TOAST_AETHER_SILVER));

    @Nullable
    public static AdvancementSoundOverride get(String id) {
        return ADVANCEMENT_SOUND_OVERRIDE_REGISTRY.get(new ResourceLocation(id));
    }

    /**
     * Retrieves the {@link SoundEvent} to use in an override for the given {@link AdvancementHolder}.
     *
     * @param advancement The {@link AdvancementHolder}.
     * @return The new {@link SoundEvent}.
     */
    @Nullable
    public static SoundEvent retrieveOverride(AdvancementHolder advancement) {
        @Nullable AdvancementSoundOverride usedOverride = null;
        for (AdvancementSoundOverride override : ADVANCEMENT_SOUND_OVERRIDES.getEntries().stream().map(DeferredHolder::value).toList()) {
            if (override.matches(advancement) && (usedOverride == null || override.priority() > usedOverride.priority())) {
                usedOverride = override;
            }
        }
        return usedOverride == null ? null : usedOverride.sound().get();
    }

    /**
     * Checks all the way up to the root of the advancement tree to determine if it matches a given root.
     */
    public static boolean checkRoot(AdvancementHolder holder, ResourceLocation root) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            for (AdvancementHolder current = holder; current != null && current.value().parent().isPresent(); current = player.connection.getAdvancements().get(current.value().parent().get())) {
                if (current.id().equals(root)) {
                    return true;
                }
            }
        }
        return false;
    }
}
