package com.aetherteam.aether.world.structurepiece.bronzedungeon;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.world.structurepiece.AetherTemplateStructurePiece;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.function.Function;

/**
 * Superclass for all Bronze Dungeon structure pieces. This exists to simplify the code.
 */
public abstract class BronzeDungeonPiece extends AetherTemplateStructurePiece {
    private static final AxisAlignedLinearPosTest ON_FLOOR = new AxisAlignedLinearPosTest(1.0F, 0.0F, 0, 1, Direction.Axis.Y);
    // This helps Bronze Dungeons merge more cleanly when they overlap, and blends the tunnels in with the landscape.
    public static final ProtectedBlockProcessor AVOID_DUNGEONS = new ProtectedBlockProcessor(AetherTags.Blocks.NON_BRONZE_DUNGEON_REPLACEABLE);

    public static final RuleProcessor LOCKED_SENTRY_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.LOCKED_CARVED_STONE.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherBlocks.LOCKED_SENTRY_STONE.get().defaultBlockState())
    ));
    public static final RuleProcessor BRONZE_DUNGEON_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.CARVED_STONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, AetherBlocks.SENTRY_STONE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.HOLYSTONE.get(), 0.2F), AlwaysTrueTest.INSTANCE, AetherBlocks.MOSSY_HOLYSTONE.get().defaultBlockState())
    ));
    public static final RuleProcessor TRAPPED_CARVED_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.CARVED_STONE.get(), 0.13F), AlwaysTrueTest.INSTANCE, ON_FLOOR, AetherBlocks.TRAPPED_CARVED_STONE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.SENTRY_STONE.get(), 0.003F), AlwaysTrueTest.INSTANCE, ON_FLOOR, AetherBlocks.TRAPPED_SENTRY_STONE.get().defaultBlockState())
    ));

    public BronzeDungeonPiece(StructurePieceType type, StructureTemplateManager manager, String name, StructurePlaceSettings settings, BlockPos pos, Holder<StructureProcessorList> processors) {
        this(type, manager, makeLocation(name), settings, pos, processors);
    }

    public BronzeDungeonPiece(StructurePieceType type, StructureTemplateManager manager, ResourceLocation name, StructurePlaceSettings settings, BlockPos pos, Holder<StructureProcessorList> processors) {
        super(type, manager, name, settings, pos, processors);
    }

    public BronzeDungeonPiece(StructurePieceType type, RegistryAccess access, CompoundTag tag, StructureTemplateManager manager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
        super(type, access, tag, manager, settingsFactory);
    }

    protected static ResourceLocation makeLocation(String name) {
        return new ResourceLocation(Aether.MODID, "bronze_dungeon/" + name);
    }
}
