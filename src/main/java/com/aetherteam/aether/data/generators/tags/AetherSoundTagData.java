package com.aetherteam.aether.data.generators.tags;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AetherSoundTagData extends TagsProvider<SoundEvent> {
    public AetherSoundTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, Registries.SOUND_EVENT, registries, Aether.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(AetherTags.SoundEvents.ACHIEVEMENT_SOUNDS).add(
            AetherSoundEvents.UI_TOAST_AETHER_GENERAL.getKey(),
            AetherSoundEvents.UI_TOAST_AETHER_BRONZE.getKey(),
            AetherSoundEvents.UI_TOAST_AETHER_SILVER.getKey(),
            AetherSoundEvents.UI_TOAST_AETHER_GOLD.getKey());
        this.tag(AetherTags.SoundEvents.BOSS_MUSIC).add(
            AetherSoundEvents.MUSIC_BOSS_SLIDER.getKey(),
            AetherSoundEvents.MUSIC_BOSS_VALKYRIE_QUEEN.getKey(),
            AetherSoundEvents.MUSIC_BOSS_SUN_SPIRIT.getKey());
    }
}
