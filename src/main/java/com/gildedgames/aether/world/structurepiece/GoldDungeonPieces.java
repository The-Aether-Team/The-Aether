package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.data.generators.AetherLootTableData;
import com.gildedgames.aether.data.resources.AetherConfiguredFeatures;
import com.gildedgames.aether.data.resources.AetherPlacedFeatures;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.SunSpirit;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.world.feature.AetherFeatures;
import com.gildedgames.aether.world.processor.HellfireStoneProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

/**
 * Class for all the pieces of the gold dungeon.
 */
public class GoldDungeonPieces {

    /**
     * A room inside the island. This should contain the sun spirit.
     */
    public static class BossRoom extends TemplateStructurePiece {

        public BossRoom(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, RandomSource random, Rotation rotation) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), 0, manager, id, id.toString(), makeSettings().setRotation(rotation), pos);
            this.setOrientation(getRandomHorizontalDirection(random));
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(HellfireStoneProcessor.INSTANCE);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox chunkBB) {
            if (name.equals("Sun Spirit")) {
                SunSpirit sunSpirit = new SunSpirit(AetherEntityTypes.SUN_SPIRIT.get(), level.getLevel());
                sunSpirit.setPersistenceRequired();
                sunSpirit.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                sunSpirit.setDungeon(new DungeonTracker<>(sunSpirit,
                        sunSpirit.position(),
                        new AABB(this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX() + 1, this.boundingBox.maxY(), this.boundingBox.maxZ() + 1),
                        new ArrayList<>()));
                sunSpirit.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
                level.getLevel().addFreshEntity(sunSpirit);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
            else if (name.equals("Treasure Chest")) {
                BlockPos chest = pos.below();
                RandomizableContainerBlockEntity.setLootTable(level, random, chest, AetherLoot.GOLD_DUNGEON_REWARD);
                TreasureChestBlockEntity.setDungeonType(level, chest, new ResourceLocation(Aether.MODID, "gold"));
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }

    /**
     * The chunks of land surrounding the boss room to form an island.
     */
    public static class Island extends FeatureStructurePiece {
        public Island(ResourceKey<PlacedFeature> feature, RegistryAccess access, BlockPos pos) {
            super(AetherStructurePieceTypes.GOLD_ISLAND.get(), feature, pos);
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {}
    }

    public static class TemplateIsland extends TemplateStructurePiece {
        public TemplateIsland(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, RandomSource random, Rotation rotation) {
            super(AetherStructurePieceTypes.GOLD_ISLAND.get(), 0, manager, id, id.toString(), makeSettings().setRotation(rotation), pos);
            this.setOrientation(getRandomHorizontalDirection(random));
        }

        public TemplateIsland(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_ISLAND.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings();
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox chunkBB) {}
    }
}
