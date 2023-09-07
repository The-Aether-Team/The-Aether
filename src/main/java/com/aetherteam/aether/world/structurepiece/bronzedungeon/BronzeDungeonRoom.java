package com.aetherteam.aether.world.structurepiece.bronzedungeon;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.loot.AetherLoot;
import com.aetherteam.aether.world.processor.DoubleDropsProcessor;
import com.aetherteam.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/**
 * A normal Bronze Dungeon room or hallway.
 */
public class BronzeDungeonRoom extends BronzeDungeonPiece {
    public BronzeDungeonRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
        super(AetherStructurePieceTypes.BRONZE_DUNGEON_ROOM.get(), manager, name, BronzeDungeonRoom.makeSettings().setRotation(rotation), pos);
    }

    public BronzeDungeonRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.BRONZE_DUNGEON_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> BronzeDungeonRoom.makeSettings());
    }

    static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings()
                .addProcessor(BronzeDungeonPiece.BRONZE_DUNGEON_STONE)
                .addProcessor(BronzeDungeonPiece.TRAPPED_CARVED_STONE)
                .addProcessor(BronzeDungeonPiece.AVOID_DUNGEONS)
                .addProcessor(DoubleDropsProcessor.INSTANCE);
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        if (name.equals("Chest")) {
            BlockState state = (random.nextInt(5) > 1 ? Blocks.CHEST : AetherBlocks.CHEST_MIMIC.get()).defaultBlockState();
            this.createChest(level, box, random, pos, AetherLoot.BRONZE_DUNGEON, state);
        }
    }
}