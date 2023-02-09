package com.gildedgames.aether.world.structurepiece.bronzedungeon;


import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.world.structurepiece.AetherTemplateStructurePiece;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraftforge.common.world.PieceBeardifierModifier;

import java.util.function.Function;

/**
 * Superclass for all bronze dungeon structure pieces. This exists to simplify the code.
 */
public abstract class BronzeDungeonPiece extends AetherTemplateStructurePiece implements PieceBeardifierModifier {
    private static final AxisAlignedLinearPosTest ON_FLOOR = new AxisAlignedLinearPosTest(1F, 0F, 0, 1, Direction.Axis.Y);
    public static RuleProcessor LOCKED_SENTRY_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.LOCKED_CARVED_STONE.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherBlocks.LOCKED_SENTRY_STONE.get().defaultBlockState())
    ));
    public static RuleProcessor BRONZE_DUNGEON_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.CARVED_STONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, AetherBlocks.SENTRY_STONE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.HOLYSTONE.get(), 0.2F), AlwaysTrueTest.INSTANCE, AetherBlocks.MOSSY_HOLYSTONE.get().defaultBlockState())
    ));
    public static final RuleProcessor TRAPPED_CARVED_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.CARVED_STONE.get(), 0.13F), AlwaysTrueTest.INSTANCE, ON_FLOOR, AetherBlocks.TRAPPED_CARVED_STONE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.SENTRY_STONE.get(), 0.003F), AlwaysTrueTest.INSTANCE, ON_FLOOR, AetherBlocks.TRAPPED_SENTRY_STONE.get().defaultBlockState())
    ));

    public BronzeDungeonPiece(StructurePieceType type, StructureTemplateManager manager, String name, StructurePlaceSettings settings, BlockPos pos) {
        super(type, manager, makeLocation(name), settings, pos);
    }

    public BronzeDungeonPiece(StructurePieceType type, CompoundTag tag, StructureTemplateManager manager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
        super(type, tag, manager, settingsFactory);
    }

    protected static ResourceLocation makeLocation(String name) {
        return new ResourceLocation(Aether.MODID, "bronze_dungeon/" + name);
    }

    @Override
    public BoundingBox getBeardifierBox() {
        /*BoundingBox beardBB = new BoundingBox(this.boundingBox.getCenter());
        beardBB.inflatedBy(this.boundingBox.)
        return beardBB;*/
        return this.boundingBox.inflatedBy(-1);
    }

    @Override
    public TerrainAdjustment getTerrainAdjustment() {
        return TerrainAdjustment.BURY;
    }

    @Override
    public int getGroundLevelDelta() {
        return 7;
    }
}