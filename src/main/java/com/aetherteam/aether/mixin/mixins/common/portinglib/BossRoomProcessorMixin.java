package com.aetherteam.aether.mixin.mixins.common.portinglib;

import com.aetherteam.aether.world.processor.BossRoomProcessor;
import com.aetherteam.nitrogen.fabric.level.EntityStructureProcessor;
import io.github.fabricators_of_create.porting_lib.extensions.extensions.StructureProcessorExtensions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BossRoomProcessor.class)
public abstract class BossRoomProcessorMixin extends StructureProcessor implements EntityStructureProcessor, StructureProcessorExtensions {
    @Override
    public StructureTemplate.StructureEntityInfo processEntity(LevelReader world, BlockPos seedPos, StructureTemplate.StructureEntityInfo rawEntityInfo, StructureTemplate.StructureEntityInfo entityInfo, StructurePlaceSettings placementSettings, StructureTemplate template) {
        return this.nitrogen_fabric$processEntity(world, seedPos, rawEntityInfo, entityInfo, placementSettings, template);
    }
}
