package com.gildedgames.aether.world.structurepiece.silverdungeon;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.world.processor.DoubleDropsProcessor;
import com.gildedgames.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class SilverDungeonRoom extends SilverDungeonPiece {

    public SilverDungeonRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
        super(AetherStructurePieceTypes.SILVER_DUNGEON_ROOM.get(), manager, name, makeSettings(manager, rotation, new ResourceLocation(Aether.MODID, "silver_dungeon/" + name)), pos);
        this.setOrientation(rotation.rotate(Direction.SOUTH));
    }

    public SilverDungeonRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.SILVER_DUNGEON_ROOM.get(), tag, context.structureTemplateManager(), id -> makeSettings(context.structureTemplateManager(), id));
    }

    private static StructurePlaceSettings makeSettings(StructureTemplateManager manager, Rotation rotation, ResourceLocation id) {
        return makeSettings(manager, id).setRotation(rotation);
    }

    private static StructurePlaceSettings makeSettings(StructureTemplateManager manager, ResourceLocation id) {
        StructureTemplate template = manager.getOrCreate(id);
        BlockPos pivot = new BlockPos(template.getSize().getX() / 2 - 4, 0, template.getSize().getZ() / 2 - 4);
        return new StructurePlaceSettings().setRotationPivot(pivot).addProcessor(LOCKED_ANGELIC_STONE).addProcessor(DoubleDropsProcessor.INSTANCE);
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        if (name.equals("Chest")) {
            BlockPos.MutableBlockPos chestPos = pos.mutable();
            int y = pos.getY() - 1;
            chestPos.set(this.boundingBox.minX() + random.nextInt(this.boundingBox.getXSpan()), y, this.boundingBox.minZ() + random.nextInt(this.boundingBox.getZSpan()));
            if (level.isEmptyBlock(chestPos)) {
                if (random.nextBoolean()) {
                    level.setBlock(chestPos, Blocks.CHEST.defaultBlockState(), 2);
                    if (level.getBlockEntity(chestPos) instanceof ChestBlockEntity chest) {
                        chest.setLootTable(AetherLoot.SILVER_DUNGEON, random.nextLong());
                    }
                } else {
                    level.setBlock(chestPos, AetherBlocks.CHEST_MIMIC.get().defaultBlockState(), 1 | 2);
                }
            }
        }
    }
}