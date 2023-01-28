package com.gildedgames.aether.world.structurepiece;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.function.Function;

/**
 * Extension of TemplateStructurePiece to make our lives easier when making custom structure pieces.
 */
public abstract class AetherTemplateStructurePiece extends TemplateStructurePiece {
    public AetherTemplateStructurePiece(StructurePieceType type, StructureTemplateManager pStructureTemplateManager, ResourceLocation name, StructurePlaceSettings placeSettings, BlockPos templatePosition) {
        super(type, 0, pStructureTemplateManager, name, name.toString(), placeSettings, templatePosition);
        this.setOrientation(this.getRotation().rotate(Direction.SOUTH));
    }

    public AetherTemplateStructurePiece(StructurePieceType type, CompoundTag tag, StructureTemplateManager manager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
        super(type, tag, manager, settingsFactory.andThen(settings -> settings.setRotation(Rotation.valueOf(tag.getString("Rotation")))));
        this.setOrientation(this.getRotation().rotate(Direction.SOUTH));
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);
        tag.putString("Rotation", this.placeSettings.getRotation().name());
    }

    public static StructurePlaceSettings makeSettingsWithPivot(StructurePlaceSettings settings, StructureTemplateManager templateManager, ResourceLocation name, Rotation rotation) {
        StructureTemplate template = templateManager.getOrCreate(name);
        Vec3i size = template.getSize();
        int xOffset = ((size.getX()) >> 1);
        int zOffset = ((size.getZ()) >> 1);
        BlockPos pivot = new BlockPos(xOffset, 0, zOffset);
        settings.setRotationPivot(pivot);
        settings.setRotation(rotation);
        return settings;
    }

    @Override
    protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

    }
}
