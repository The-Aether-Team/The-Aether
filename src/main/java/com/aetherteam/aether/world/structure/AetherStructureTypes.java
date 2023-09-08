package com.aetherteam.aether.world.structure;

import com.aetherteam.aether.Aether;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AetherStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, Aether.MODID);

    public static final RegistryObject<StructureType<LargeAercloudStructure>> LARGE_AERCLOUD = STRUCTURE_TYPES.register("large_aercloud", () -> () -> LargeAercloudStructure.CODEC);
    public static final RegistryObject<StructureType<BronzeDungeonStructure>> BRONZE_DUNGEON = STRUCTURE_TYPES.register("bronze_dungeon", () -> () -> BronzeDungeonStructure.CODEC);
    public static final RegistryObject<StructureType<SilverDungeonStructure>> SILVER_DUNGEON = STRUCTURE_TYPES.register("silver_dungeon", () -> () -> SilverDungeonStructure.CODEC);
    public static final RegistryObject<StructureType<GoldDungeonStructure>> GOLD_DUNGEON = STRUCTURE_TYPES.register("gold_dungeon", () -> () -> GoldDungeonStructure.CODEC);
    public static final RegistryObject<StructureType<GlowstoneRuinedPortalStructure>> RUINED_PORTAL = STRUCTURE_TYPES.register("ruined_portal", () -> () -> GlowstoneRuinedPortalStructure.CODEC);
}
