package com.aetherteam.aether.world.structurepiece.bronzedungeon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public record BronzeProcessorSettings(Holder<StructureProcessorList> roomSettings, Holder<StructureProcessorList> tunnelSettings, Holder<StructureProcessorList> bossSettings) {
    public static final Codec<BronzeProcessorSettings> CODEC = RecordCodecBuilder.create(builder -> builder.group(
        StructureProcessorType.LIST_CODEC.fieldOf("generic_room_processors").forGetter(BronzeProcessorSettings::roomSettings),
        StructureProcessorType.LIST_CODEC.fieldOf("tunnel_processors").forGetter(BronzeProcessorSettings::tunnelSettings),
        StructureProcessorType.LIST_CODEC.fieldOf("boss_room_processors").forGetter(BronzeProcessorSettings::bossSettings)
    ).apply(builder, BronzeProcessorSettings::new));
}
