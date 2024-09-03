package com.aetherteam.aether.world.structurepiece.silverdungeon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public record SilverProcessorSettings(Holder<StructureProcessorList> roomSettings, Holder<StructureProcessorList> floorSettings, Holder<StructureProcessorList> bossSettings) {
    public static final Codec<SilverProcessorSettings> CODEC = RecordCodecBuilder.create(builder -> builder.group(
        StructureProcessorType.LIST_CODEC.fieldOf("generic_room_processors").forGetter(SilverProcessorSettings::roomSettings),
        StructureProcessorType.LIST_CODEC.fieldOf("floor_processors").forGetter(SilverProcessorSettings::floorSettings),
        StructureProcessorType.LIST_CODEC.fieldOf("boss_room_processors").forGetter(SilverProcessorSettings::bossSettings)
    ).apply(builder, SilverProcessorSettings::new));
}
