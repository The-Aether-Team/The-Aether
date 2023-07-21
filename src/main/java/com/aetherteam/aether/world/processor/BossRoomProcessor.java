package com.aetherteam.aether.world.processor;

import com.aetherteam.nitrogen.entity.BossRoomTracker;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

/**
 * This processor will give every boss mob in the template the bounding box of the room to use for its dungeon tracker.
 */
public class BossRoomProcessor extends StructureProcessor {
    public static final BossRoomProcessor INSTANCE = new BossRoomProcessor();

    public static final Codec<BossRoomProcessor> CODEC = Codec.unit(INSTANCE);

    @Override
    public StructureTemplate.StructureEntityInfo processEntity(LevelReader world, BlockPos seedPos, StructureTemplate.StructureEntityInfo rawEntityInfo, StructureTemplate.StructureEntityInfo entityInfo, StructurePlaceSettings placementSettings, StructureTemplate template) {
        BoundingBox boundingBox = template.getBoundingBox(placementSettings, seedPos);
        BossRoomTracker<?> tracker = new BossRoomTracker<>(null,
                entityInfo.pos,
                new AABB(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ(), boundingBox.maxX() + 1, boundingBox.maxY() + 1, boundingBox.maxZ() + 1),
                new ArrayList<>());
        entityInfo.nbt.put("Dungeon", tracker.addAdditionalSaveData());
        return super.processEntity(world, seedPos, rawEntityInfo, entityInfo, placementSettings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherStructureProcessors.BOSS_ROOM.get();
    }
}
