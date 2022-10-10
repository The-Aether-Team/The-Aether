package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.Slider;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.world.processor.DungeonStoneProcessor;
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
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BronzeDungeonPieces {

    public static void startBronzeDungeon(List<StructurePiece> pieces, StructureTemplateManager manager, BlockPos startPos, RandomSource random) {
        Rotation rotation = Rotation.getRandom(random);
        BossRoom bossRoom = new BossRoom(manager, new ResourceLocation(Aether.MODID, "bronze_dungeon/boss_room"), startPos, rotation);
        pieces.add(bossRoom);

        TunnelPiece bossTunnel = new TunnelPiece(
                manager,
                new ResourceLocation(Aether.MODID, "bronze_dungeon/square_tunnel"),
                startPos,
                rotation
        );
        pieces.add(bossTunnel);
    }

    public static abstract class BronzeDungeonPiece extends TemplateStructurePiece {
        public BronzeDungeonPiece(StructurePieceType pType, int pGenDepth, StructureTemplateManager pStructureTemplateManager, ResourceLocation pLocation, String pTemplateName, StructurePlaceSettings pPlaceSettings, BlockPos pTemplatePosition) {
            super(pType, pGenDepth, pStructureTemplateManager, pLocation, pTemplateName, pPlaceSettings, pTemplatePosition);
        }

        public BronzeDungeonPiece(StructurePieceType type, StructurePieceSerializationContext context, CompoundTag tag, Function<ResourceLocation, StructurePlaceSettings> settings) {
            super(type, tag, context.structureTemplateManager(), settings);
        }
    }

    public static class TunnelPiece extends TemplateStructurePiece {

        public TunnelPiece(StructureTemplateManager pStructureTemplateManager, ResourceLocation id, BlockPos pTemplatePosition, Rotation rotation) {
            super(AetherStructurePieceTypes.BRONZE_TUNNEL.get(), 0, pStructureTemplateManager, id, id.toString(), makeSettings().setRotation(rotation), pTemplatePosition);
        }

        public TunnelPiece(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.BRONZE_TUNNEL.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings()/*.addProcessor(NoReplaceProcessor.AIR)*/.addProcessor(DungeonStoneProcessor.MOSSY_HOLYSTONE);
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

        }

        @Nullable
        protected TunnelPiece extendCorridor(List<StructurePiece> pieces, StructureTemplateManager manager) {
            int x = 0, z = 0;
            boolean flag = false;
            switch (this.getRotation()) {
                case NONE -> {
                    x = (this.boundingBox.minX() + this.boundingBox.maxX()) / 2;
                    z = this.boundingBox.minZ();
                }
                case CLOCKWISE_90 -> {
                    x = this.boundingBox.maxX();
                    z = (this.boundingBox.minZ() + this.boundingBox.maxZ()) / 2;
                }
                case CLOCKWISE_180 -> {
                    x = (this.boundingBox.minX() + this.boundingBox.maxX()) / 2;
                    z = this.boundingBox.maxZ();
                }
                case COUNTERCLOCKWISE_90 -> {
                    x = this.boundingBox.minX();
                    z = (this.boundingBox.minZ() + this.boundingBox.maxZ()) / 2;
                }
                default -> flag = true;
            }
            if (flag) {
                return null;
            }
            return new TunnelPiece(
                    manager,
                    new ResourceLocation(Aether.MODID, "bronze_dungeon/end_corridor"),
                    new BlockPos(x, this.boundingBox.minY(), z),
                    this.getRotation()
            );
        }
    }

    public static class BossRoom extends TemplateStructurePiece {

        public BossRoom(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), 0, manager, id, id.toString(), makeSettings().setRotation(rotation), pos);
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(DungeonStoneProcessor.SENTRY);
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
                        new AABB(this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX(), this.boundingBox.maxY(), this.boundingBox.maxZ()),
                        new ArrayList<>()));
                slider.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
                level.getLevel().addFreshEntity(slider);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
            else if (name.equals("Treasure Chest")) {
                BlockPos chest = pos.below();
                RandomizableContainerBlockEntity.setLootTable(level, random, chest, AetherLoot.BRONZE_DUNGEON_REWARD);
                TreasureChestBlockEntity.setDungeonType(level, chest, new ResourceLocation(Aether.MODID, "bronze"));
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }
}
