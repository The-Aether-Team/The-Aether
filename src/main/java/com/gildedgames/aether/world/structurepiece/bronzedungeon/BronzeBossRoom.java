package com.gildedgames.aether.world.structurepiece.bronzedungeon;


import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.slider.Slider;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.world.PieceBeardifierModifier;

import java.util.ArrayList;

/**
 * Starting piece for the bronze dungeon. Has the slider.
 */
public class BronzeBossRoom extends BronzeDungeonPiece implements PieceBeardifierModifier {

    public BronzeBossRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
        super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), manager, name, makeSettingsWithPivot(makeSettings(), manager, makeLocation(name), rotation), pos);
    }

    public BronzeBossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
    }

    static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings().addProcessor(LOCKED_SENTRY_STONE);
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
        if (name.equals("Slider")) {
            Slider slider = new Slider(AetherEntityTypes.SLIDER.get(), level.getLevel());
            slider.setPersistenceRequired();
            double xPos = pos.getX();
            double zPos = pos.getZ();
            switch (this.placeSettings.getRotation()) {
                case NONE -> {
                    xPos += 1;
                    zPos += 1;
                }
                case CLOCKWISE_90 -> zPos += 1;
                case COUNTERCLOCKWISE_90 -> xPos += 1;
            }
            slider.setPos(xPos, pos.getY(), zPos);
            slider.setDungeon(new DungeonTracker<>(slider,
                    slider.position(),
                    new AABB(this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX() + 1, this.boundingBox.maxY() + 1, this.boundingBox.maxZ() + 1),
                    new ArrayList<>()));
            slider.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
            level.getLevel().addFreshEntity(slider);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        } else if (name.equals("Treasure Chest")) {
            BlockPos chest = pos.below();
            RandomizableContainerBlockEntity.setLootTable(level, random, chest, AetherLoot.BRONZE_DUNGEON_REWARD);
            TreasureChestBlockEntity.setDungeonType(level, chest, new ResourceLocation(Aether.MODID, "bronze"));
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        }
    }

    @Override
    public BoundingBox getBeardifierBox() {
        return this.boundingBox;
    }

    @Override
    public TerrainAdjustment getTerrainAdjustment() {
        return TerrainAdjustment.BURY;
    }

    @Override
    public int getGroundLevelDelta() {
        return 0;
    }
}