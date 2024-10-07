package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.builders.AetherStructureBuilders;
import com.aetherteam.aether.world.processor.BossRoomProcessor;
import com.aetherteam.aether.world.processor.DoubleDropsProcessor;
import com.aetherteam.aether.world.processor.NoReplaceProcessor;
import com.aetherteam.aether.world.processor.SurfaceRuleProcessor;
import com.aetherteam.aether.world.processor.VerticalGradientProcessor;
import com.aetherteam.aether.world.structure.BronzeDungeonStructure;
import com.aetherteam.aether.world.structurepiece.bronzedungeon.BronzeDungeonPiece;
import com.aetherteam.aether.world.structurepiece.golddungeon.GoldDungeonPiece;
import com.aetherteam.aether.world.structurepiece.silverdungeon.SilverDungeonPiece;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.AxisAlignedLinearPosTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;

public class AetherStructureProcessorLists {

    public static final ResourceKey<StructureProcessorList> BRONZE_ROOM = createKey("bronze_room");
    public static final ResourceKey<StructureProcessorList> BRONZE_TUNNEL = createKey("bronze_tunnel");
    public static final ResourceKey<StructureProcessorList> BRONZE_BOSS_ROOM = createKey("bronze_boss_room");
    public static final ResourceKey<StructureProcessorList> SILVER_ROOM = createKey("silver_room");
    public static final ResourceKey<StructureProcessorList> SILVER_FLOOR = createKey("silver_floor");
    public static final ResourceKey<StructureProcessorList> SILVER_BOSS_ROOM = createKey("silver_boss_room");

    public static final ResourceKey<StructureProcessorList> GOLD_ISLAND = createKey("gold_island");
    public static final ResourceKey<StructureProcessorList> GOLD_TUNNEL = createKey("gold_tunnel");
    public static final ResourceKey<StructureProcessorList> GOLD_BOSS_ROOM = createKey("gold_boss_room");

    private static ResourceKey<StructureProcessorList> createKey(String name) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, ResourceLocation.fromNamespaceAndPath(Aether.MODID, name));
    }

    private static final AxisAlignedLinearPosTest ON_FLOOR = new AxisAlignedLinearPosTest(1.0F, 0.0F, 0, 1, Direction.Axis.Y);

    public static void bootstrap(BootstrapContext<StructureProcessorList> context) {
        register(context, BRONZE_ROOM, List.of(
            BronzeDungeonPiece.BRONZE_DUNGEON_STONE,
            BronzeDungeonPiece.TRAPPED_CARVED_STONE,
            BronzeDungeonPiece.AVOID_DUNGEONS,
            DoubleDropsProcessor.INSTANCE
        ));
        register(context, BRONZE_TUNNEL, List.of(
            BronzeDungeonPiece.AVOID_DUNGEONS,
            BronzeDungeonPiece.BRONZE_DUNGEON_STONE,
            DoubleDropsProcessor.INSTANCE
        ));
        register(context, BRONZE_BOSS_ROOM, List.of(
            BronzeDungeonPiece.LOCKED_SENTRY_STONE,
            BossRoomProcessor.INSTANCE
        ));

        register(context, SILVER_ROOM, List.of(
            SilverDungeonPiece.LOCKED_ANGELIC_STONE,
            DoubleDropsProcessor.INSTANCE
        ));
        register(context, SILVER_FLOOR, List.of(
            SilverDungeonPiece.LOCKED_ANGELIC_STONE,
            SilverDungeonPiece.TRAPPED_ANGELIC_STONE,
            DoubleDropsProcessor.INSTANCE
        ));
        register(context, SILVER_BOSS_ROOM, List.of(
            DoubleDropsProcessor.INSTANCE,
            BossRoomProcessor.INSTANCE
        ));


        register(context, GOLD_ISLAND, List.of(
            SurfaceRuleProcessor.INSTANCE,
            VerticalGradientProcessor.INSTANCE,
            DoubleDropsProcessor.INSTANCE
        ));
        register(context, GOLD_TUNNEL, List.of(
            GoldDungeonPiece.MOSSY_HOLYSTONE,
            NoReplaceProcessor.AIR,
            DoubleDropsProcessor.INSTANCE
        ));
        register(context, GOLD_BOSS_ROOM, List.of(
            GoldDungeonPiece.LOCKED_HELLFIRE_STONE,
            BossRoomProcessor.INSTANCE
        ));


    }

    private static void register(BootstrapContext<StructureProcessorList> context, ResourceKey<StructureProcessorList> key, List<StructureProcessor> processors) {
        context.register(key, new StructureProcessorList(processors));
    }
}
