package com.aetherteam.aether.world.structure;

import com.aetherteam.aether.Aether;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class AetherStructureTypes {
    public static final LazyRegistrar<StructureType<?>> STRUCTURE_TYPES = LazyRegistrar.create(Registries.STRUCTURE_TYPE, Aether.MODID);

    public static final RegistryObject<StructureType<LargeAercloudStructure>> LARGE_AERCLOUD = STRUCTURE_TYPES.register("large_aercloud", () -> () -> LargeAercloudStructure.CODEC);
    public static final RegistryObject<StructureType<BronzeDungeonStructure>> BRONZE_DUNGEON = STRUCTURE_TYPES.register("bronze_dungeon", () -> () -> BronzeDungeonStructure.CODEC);
    public static final RegistryObject<StructureType<SilverDungeonStructure>> SILVER_DUNGEON = STRUCTURE_TYPES.register("silver_dungeon", () -> () -> SilverDungeonStructure.CODEC);
    public static final RegistryObject<StructureType<GoldDungeonStructure>> GOLD_DUNGEON = STRUCTURE_TYPES.register("gold_dungeon", () -> () -> GoldDungeonStructure.CODEC);
    public static final RegistryObject<StructureType<GlowstoneRuinedPortalStructure>> RUINED_PORTAL = STRUCTURE_TYPES.register("ruined_portal", () -> () -> GlowstoneRuinedPortalStructure.CODEC);
}
