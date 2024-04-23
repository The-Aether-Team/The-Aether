package com.aetherteam.aether.data.generators.tags;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.data.resources.registries.AetherStructures;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;

import java.util.concurrent.CompletableFuture;

public class AetherStructureTagData extends StructureTagsProvider {
    public AetherStructureTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(AetherTags.Structures.DUNGEONS).add(
                AetherStructures.BRONZE_DUNGEON,
                AetherStructures.SILVER_DUNGEON,
                AetherStructures.GOLD_DUNGEON
        );
    }
}
