package com.aetherteam.aether.world.structurepiece.golddungeon;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public record GoldProcessorSettings(Holder<StructureProcessorList> islandSettings, Holder<StructureProcessorList> tunnelSettings, Holder<StructureProcessorList> bossSettings) {
    public static final MapCodec<GoldProcessorSettings> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
        StructureProcessorType.LIST_CODEC.fieldOf("island_processors").forGetter(GoldProcessorSettings::islandSettings),
        StructureProcessorType.LIST_CODEC.fieldOf("tunnel_processors").forGetter(GoldProcessorSettings::tunnelSettings),
        StructureProcessorType.LIST_CODEC.fieldOf("boss_room_processors").forGetter(GoldProcessorSettings::bossSettings)
    ).apply(builder, GoldProcessorSettings::new));
}
