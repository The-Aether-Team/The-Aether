package com.aetherteam.aether.world.structurepiece.silverdungeon;


import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import com.aetherteam.aether.loot.AetherLoot;
import com.aetherteam.aether.world.processor.BorderBoxPosTest;
import com.aetherteam.aether.world.processor.BossRoomProcessor;
import com.aetherteam.aether.world.processor.DoubleDropsProcessor;
import com.aetherteam.aether.world.structurepiece.AetherStructurePieceTypes;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

/**
 * This is the arena for fighting the valkyrie queen.
 */
public class SilverBossRoom extends SilverDungeonPiece {

    public SilverBossRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
        super(AetherStructurePieceTypes.SILVER_BOSS_ROOM.get(), manager, name, makeSettings(manager, makeLocation(name)).setRotation(rotation), pos);
        this.setOrientation(rotation.rotate(Direction.SOUTH));
    }

    public SilverBossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.SILVER_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> {
            return makeSettings(context.structureTemplateManager(), new ResourceLocation(tag.getString("Template")));
        });
    }

    private static StructurePlaceSettings makeSettings(StructureTemplateManager templateManager, ResourceLocation name) {
        return new StructurePlaceSettings()
                .addProcessor(makeBoxProcessor(templateManager, name))
                .addProcessor(DoubleDropsProcessor.INSTANCE)
                .addProcessor(BossRoomProcessor.INSTANCE)
                .setFinalizeEntities(true);
    }

    private static StructureProcessor makeBoxProcessor(StructureTemplateManager templateManager, ResourceLocation id) {
        Vec3i template = templateManager.getOrCreate(id).getSize();
        BorderBoxPosTest borderTest = new BorderBoxPosTest(0, 1, 0, template.getX() - 1, template.getY() - 1, template.getZ() - 1);
        return new RuleProcessor(ImmutableList.of(
                new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.LOCKED_ANGELIC_STONE.get(), 0.05F), AlwaysTrueTest.INSTANCE, borderTest, AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get().defaultBlockState())
        ));
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox chunkBB) {
        if (name.equals("Treasure Chest")) {
            BlockPos chest = pos.below();
            RandomizableContainerBlockEntity.setLootTable(level, random, chest, AetherLoot.SILVER_DUNGEON_REWARD);
            TreasureChestBlockEntity.setDungeonType(level, chest, new ResourceLocation(Aether.MODID, "silver"));
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        }
    }
}