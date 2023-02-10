package com.gildedgames.aether.world.structurepiece.golddungeon;


import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.SunSpirit;
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
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

/**
 * A room inside the island. This should contain the sun spirit.
 */
public class GoldBossRoom extends GoldDungeonPiece {

    public GoldBossRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
        super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), manager, name, makeSettingsWithPivot(makeSettings(), manager, makeLocation(name), rotation), pos);
    }

    public GoldBossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
    }

    private static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings().addProcessor(LOCKED_HELLFIRE_STONE);
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
        } else if (name.equals("Treasure Chest")) {
            BlockPos chest = pos.below();
            RandomizableContainerBlockEntity.setLootTable(level, random, chest, AetherLoot.GOLD_DUNGEON_REWARD);
            TreasureChestBlockEntity.setDungeonType(level, chest, new ResourceLocation(Aether.MODID, "gold"));
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        }
    }
}