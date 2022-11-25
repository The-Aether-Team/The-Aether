package com.gildedgames.aether.data.generators.tags;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.data.resources.AetherStructures;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class AetherStructureTagData extends StructureTagsProvider {

    public AetherStructureTagData(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Aether.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(AetherTags.Structures.DUNGEONS).add(
                AetherStructures.BRONZE_DUNGEON,
                AetherStructures.SILVER_DUNGEON,
                AetherStructures.GOLD_DUNGEON
        );
    }
}
