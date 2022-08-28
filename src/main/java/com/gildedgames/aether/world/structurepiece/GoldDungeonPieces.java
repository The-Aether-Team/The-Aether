package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.SunSpirit;
import com.gildedgames.aether.world.processor.HellfireStoneProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
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

        public BossRoom(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, RandomSource random) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), 0, manager, id, id.toString(), makeSettings().setRotation(Rotation.getRandom(random)), pos);
            this.setOrientation(getRandomHorizontalDirection(random));
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(HellfireStoneProcessor.INSTANCE);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox boundingBox) {
            if (name.equals("Sun Spirit")) {
                SunSpirit sunSpirit = new SunSpirit(AetherEntityTypes.SUN_SPIRIT.get(), level.getLevel());
                sunSpirit.setPersistenceRequired();
                sunSpirit.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                sunSpirit.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
                sunSpirit.setDungeon(new DungeonTracker<>(sunSpirit,
                        sunSpirit.position(),
                        new AABB(this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX(), this.boundingBox.maxY(), this.boundingBox.maxZ()),
                        new ArrayList<>()));
                level.getLevel().addFreshEntity(sunSpirit);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }

    /**
     * The chunks of land surrounding the boss room to form an island.
     */
    public static class Island extends TemplateStructurePiece { // TODO: This might be unnecessary.

        public Island(StructureTemplateManager manager, ResourceLocation id, BlockPos pos) {
            super(AetherStructurePieceTypes.GOLD_ISLAND.get(), 0, manager, id, id.toString(), makeSettings(), pos);
        }

        public Island(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_ISLAND.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings();
        }

        @Override
        protected void handleDataMarker(String p_226906_, BlockPos p_226907_, ServerLevelAccessor p_226908_, RandomSource p_226909_, BoundingBox p_226910_) {}
    }
}
