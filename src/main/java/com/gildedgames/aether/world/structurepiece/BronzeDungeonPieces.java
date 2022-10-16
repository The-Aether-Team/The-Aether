package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.Slider;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.util.BlockLogicUtil;
import com.gildedgames.aether.world.processor.NoReplaceProcessor;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

public class BronzeDungeonPieces {
    public static RuleProcessor LOCKED_SENTRY_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.LOCKED_CARVED_STONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, AetherBlocks.LOCKED_SENTRY_STONE.get().defaultBlockState())
    ));
    public static RuleProcessor BRONZE_DUNGEON_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.CARVED_STONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, AetherBlocks.SENTRY_STONE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.HOLYSTONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, AetherBlocks.MOSSY_HOLYSTONE.get().defaultBlockState())
    ));

    public static class Builder {
        public Builder() {

        }
    }

    /**
     * Starting piece for the bronze dungeon. Has the slider.
     */
    public static class BossRoom extends TemplateStructurePiece {

        public BossRoom(StructureTemplateManager manager, int genDepth, String name, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), genDepth, manager, new ResourceLocation(Aether.MODID, "bronze_dungeon/" + name), name, makeSettings().setRotation(rotation), pos);
            this.setOrientation(this.getRotation().rotate(Direction.SOUTH));
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        public void addTemplateChildren(StructureTemplateManager manager, StructurePiece start, StructurePieceAccessor pieceAccessor, RandomSource random) {
            Direction direction = this.getOrientation();
            if (direction == null) {
                direction = this.getRotation().rotate(Direction.SOUTH);
            }
            BlockPos pos = BlockLogicUtil.tunnelFromEvenSquareRoom(start.getBoundingBox().moved(0, 2, 0), direction, 6);
            DungeonRoom hallway = new DungeonRoom(manager, "square_tunnel", pos, this.getRotation());
            pos = BlockLogicUtil.tunnelFromEvenSquareRoom(hallway.getBoundingBox(), direction, 12);
            DungeonRoom chestRoom = new DungeonRoom(manager, this.genDepth - 1, "chest_room", pos, this.getRotation());
            pieceAccessor.addPiece(chestRoom);
            pieceAccessor.addPiece(hallway);
            chestRoom.addTemplateChildren(manager, this, pieceAccessor, random);
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

    public static class DungeonRoom extends TemplateStructurePiece {
        public DungeonRoom(StructureTemplateManager manager, int genDepth, String name, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.BRONZE_DUNGEON_ROOM.get(), genDepth, manager, new ResourceLocation(Aether.MODID, "bronze_dungeon/" + name), name, makeSettings().setRotation(rotation), pos);
            this.setOrientation(this.getRotation().rotate(Direction.SOUTH));
        }

        public DungeonRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
            this(manager, 0, name, pos, rotation);
        }

        public DungeonRoom(StructurePieceSerializationContext context, CompoundTag tag) {
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
                    level.setBlock(pos, AetherBlocks.CHEST_MIMIC.get().defaultBlockState(), 3);
                }
            }
        }

        public void addTemplateChildren(StructureTemplateManager manager, StructurePiece start, StructurePieceAccessor pieceAccessor, RandomSource random) {
            if (this.genDepth > 0) {
                Rotation rotation = this.getRotation();
                int genDepth = this.genDepth - 1;
                for (Rotation rot : Rotation.values()) {
                    if (rotation != rot) {
                        rot = rotation.getRotated(rot);
                        Direction direction = rot.rotate(Direction.SOUTH);
                        BlockPos pos = BlockLogicUtil.tunnelFromEvenSquareRoom(this.getBoundingBox(), direction, 6);
                        DungeonRoom hallway = new DungeonRoom(manager, "square_tunnel", pos, rot);
                        pos = BlockLogicUtil.tunnelFromEvenSquareRoom(hallway.getBoundingBox(), direction, 12);
                        DungeonRoom chestRoom = new DungeonRoom(manager, genDepth, "chest_room", pos, rot);
                        if (pieceAccessor.findCollisionPiece(chestRoom.boundingBox) == null) {
                            pieceAccessor.addPiece(chestRoom);
                            pieceAccessor.addPiece(hallway);
                            chestRoom.addTemplateChildren(manager, this, pieceAccessor, random);
                        }
                    }
                }
            }
//            HolystoneTunnel.buildTunnelFromRoom(manager, this, pieceAccessor, rotation, direction);
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
            return new StructurePlaceSettings().addProcessor(NoReplaceProcessor.AIR).addProcessor(BRONZE_DUNGEON_STONE);
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
            BlockPos startPos = BlockLogicUtil.tunnelFromEvenSquareRoom(connectedRoom.getBoundingBox(), direction, template.getSize().getX());
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
