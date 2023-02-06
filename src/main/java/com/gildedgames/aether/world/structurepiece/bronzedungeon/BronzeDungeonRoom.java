package com.gildedgames.aether.world.structurepiece.bronzedungeon;


import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraftforge.common.world.PieceBeardifierModifier;

public class BronzeDungeonRoom extends BronzeDungeonPiece {
    public BronzeDungeonRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
        super(AetherStructurePieceTypes.BRONZE_DUNGEON_ROOM.get(), manager, name, makeSettings().setRotation(rotation), pos);
    }

    public BronzeDungeonRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.BRONZE_DUNGEON_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
    }

    static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings().addProcessor(BRONZE_DUNGEON_STONE);
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        if (name.equals("Chest")) {
            if (random.nextBoolean()) {
                level.setBlock(pos, Blocks.CHEST.defaultBlockState(), 2);
                if (level.getBlockEntity(pos) instanceof ChestBlockEntity chest) {
                    chest.setLootTable(AetherLoot.BRONZE_DUNGEON, random.nextLong());
                }
            } else {
                level.setBlock(pos, AetherBlocks.CHEST_MIMIC.get().defaultBlockState(), 1 | 2);
            }
        }
    }
}