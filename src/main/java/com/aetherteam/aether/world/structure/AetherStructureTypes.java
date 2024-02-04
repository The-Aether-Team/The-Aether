package com.aetherteam.aether.world.structure;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AetherStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, Aether.MODID);

    public static final Supplier<StructureType<LargeAercloudStructure>> LARGE_AERCLOUD = STRUCTURE_TYPES.register("large_aercloud", () -> () -> LargeAercloudStructure.CODEC);
    public static final Supplier<StructureType<BronzeDungeonStructure>> BRONZE_DUNGEON = STRUCTURE_TYPES.register("bronze_dungeon", () -> () -> BronzeDungeonStructure.CODEC);
    public static final Supplier<StructureType<SilverDungeonStructure>> SILVER_DUNGEON = STRUCTURE_TYPES.register("silver_dungeon", () -> () -> SilverDungeonStructure.CODEC);
    public static final Supplier<StructureType<GoldDungeonStructure>> GOLD_DUNGEON = STRUCTURE_TYPES.register("gold_dungeon", () -> () -> GoldDungeonStructure.CODEC);
    public static final Supplier<StructureType<GlowstoneRuinedPortalStructure>> RUINED_PORTAL = STRUCTURE_TYPES.register("ruined_portal", () -> () -> GlowstoneRuinedPortalStructure.CODEC);
}
