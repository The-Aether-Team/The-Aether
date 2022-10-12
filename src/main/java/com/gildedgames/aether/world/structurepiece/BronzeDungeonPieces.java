package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.Slider;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.util.BlockLogicUtil;
import com.gildedgames.aether.world.processor.DungeonStoneProcessor;
import com.gildedgames.aether.world.processor.NoReplaceProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

public class BronzeDungeonPieces {

    /**
     * Starting piece for the bronze dungeon. Has the slider.
     */
    public static class BossRoom extends TemplateStructurePiece {

        public BossRoom(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), 0, manager, id, id.toString(), makeSettings().setRotation(rotation), pos);
            this.setOrientation(this.getRotation().rotate(Direction.SOUTH));
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        public void addChildren(StructureTemplateManager manager, StructurePiece start, StructurePieceAccessor pieceAccessor, RandomSource random) {
            Direction direction = this.getOrientation();
            if (direction == null) {
                direction = this.getRotation().rotate(Direction.SOUTH);
            }
            HolystoneTunnel.buildTunnelFromRoom(manager, start, pieceAccessor, this.getRotation(), direction);
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

    public static class HolystoneTunnel extends TemplateStructurePiece {

        public HolystoneTunnel(StructureTemplateManager pStructureTemplateManager, ResourceLocation id, BlockPos pTemplatePosition, Rotation rotation) {
            super(AetherStructurePieceTypes.BRONZE_TUNNEL.get(), 0, pStructureTemplateManager, id, id.toString(), makeSettings().setRotation(rotation), pTemplatePosition);
        }

        public HolystoneTunnel(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.BRONZE_TUNNEL.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(NoReplaceProcessor.AIR).addProcessor(DungeonStoneProcessor.MOSSY_HOLYSTONE);
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

        }

        /**
         * Builds a tunnel from a symmetrical room to make an entrance.
         * @param manager - Used by TemplateStructurePiece
         * @param connectedRoom - The room the tunnel leads to
         * @param pieceAccessor - This builds the structure pieces in the world
         * @param rotation - The rotation of the template
         * @param direction - The direction to build in
         */
        public static void buildTunnelFromRoom(StructureTemplateManager manager, StructurePiece connectedRoom, StructurePieceAccessor pieceAccessor, Rotation rotation, Direction direction) {
            StructureTemplate template = manager.getOrCreate(new ResourceLocation(Aether.MODID, "bronze_dungeon/end_corridor"));
            BoundingBox box = connectedRoom.getBoundingBox().moved(0, 2, 0);
            BlockPos startPos = BlockLogicUtil.tunnelFromEvenSquareRoom(box, direction, template.getSize().getX());
            Aether.LOGGER.info("Start position: " + startPos);
            int length = template.getSize().getZ();
            for (int i = 0; i < 100; i+=length) {
                BlockPos pos = startPos.offset(direction.getStepX() * i, 0, direction.getStepZ() * i);
                HolystoneTunnel tunnel = new HolystoneTunnel(manager, new ResourceLocation(Aether.MODID, "bronze_dungeon/end_corridor"), pos, rotation);
                pieceAccessor.addPiece(tunnel);
            }
        }
    }
}
