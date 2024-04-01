package com.aetherteam.aether.world.structurepiece.bronzedungeon;


import com.aetherteam.aether.Aether;
import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import com.aetherteam.aether.loot.AetherLoot;
import com.aetherteam.aether.world.processor.BossRoomProcessor;
import com.aetherteam.aether.world.structurepiece.AetherStructurePieceTypes;
import com.aetherteam.aether.world.structurepiece.AetherTemplateStructurePiece;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/**
 * Starting piece for the Bronze Dungeon. Has the slider.
 */
public class BronzeBossRoom extends BronzeDungeonPiece {
    public BronzeBossRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
        super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), manager, name, AetherTemplateStructurePiece.makeSettingsWithPivot(makeSettings(), manager, BronzeDungeonPiece.makeLocation(name), rotation), pos);
    }

    public BronzeBossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> BronzeBossRoom.makeSettings());
    }

    static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings()
                .addProcessor(BronzeDungeonPiece.LOCKED_SENTRY_STONE)
                .addProcessor(BossRoomProcessor.INSTANCE)
                .setFinalizeEntities(true);
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
        if (name.equals("Treasure Chest")) {
            BlockPos chest = pos.below();
            BlockEntity entity = level.getBlockEntity(chest);
            if (entity instanceof RandomizableContainerBlockEntity container) {
                container.setLootTable(AetherLoot.BRONZE_DUNGEON_REWARD, random.nextLong());
            }
            TreasureChestBlockEntity.setDungeonType(level, chest, new ResourceLocation(Aether.MODID, "bronze"));
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        }
    }
}