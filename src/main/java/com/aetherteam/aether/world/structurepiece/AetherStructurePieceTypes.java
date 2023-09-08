package com.aetherteam.aether.world.structurepiece;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.world.structurepiece.bronzedungeon.BronzeBossRoom;
import com.aetherteam.aether.world.structurepiece.bronzedungeon.BronzeDungeonRoom;
import com.aetherteam.aether.world.structurepiece.bronzedungeon.BronzeTunnel;
import com.aetherteam.aether.world.structurepiece.golddungeon.*;
import com.aetherteam.aether.world.structurepiece.silverdungeon.SilverBossRoom;
import com.aetherteam.aether.world.structurepiece.silverdungeon.SilverDungeonRoom;
import com.aetherteam.aether.world.structurepiece.silverdungeon.SilverFloorPiece;
import com.aetherteam.aether.world.structurepiece.silverdungeon.SilverTemplePiece;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;

public class AetherStructurePieceTypes {
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registry.STRUCTURE_PIECE_REGISTRY, Aether.MODID);

    public static final RegistryObject<StructurePieceType> LARGE_AERCLOUD = register("ALC", LargeAercloudChunk::new);
    public static final RegistryObject<StructurePieceType> BRONZE_BOSS_ROOM = register("BBossRoom", BronzeBossRoom::new);
    public static final RegistryObject<StructurePieceType> BRONZE_DUNGEON_ROOM = register("BDungeonRoom", BronzeDungeonRoom::new);
    public static final RegistryObject<StructurePieceType> BRONZE_TUNNEL = register("BTunnel", BronzeTunnel::new);
    public static final RegistryObject<StructurePieceType> SILVER_TEMPLE_PIECE = register("STemplePiece", SilverTemplePiece::new);
    public static final RegistryObject<StructurePieceType> SILVER_FLOOR_PIECE = register("SFloorPiece", SilverFloorPiece::new);
    public static final RegistryObject<StructurePieceType> SILVER_DUNGEON_ROOM = register("SDungeonRoom", SilverDungeonRoom::new);
    public static final RegistryObject<StructurePieceType> SILVER_BOSS_ROOM = register("SBossRoom", SilverBossRoom::new);
    public static final RegistryObject<StructurePieceType> GOLD_BOSS_ROOM = register("GBossRoom", GoldBossRoom::new);
    public static final RegistryObject<StructurePieceType> GOLD_ISLAND = register("GIsland", GoldIsland::new);
    public static final RegistryObject<StructurePieceType> GOLD_STUB = register("GStub", GoldStub::new);
    public static final RegistryObject<StructurePieceType> GOLD_TUNNEL = register("GTunnel", GoldTunnel::new);
    public static final RegistryObject<StructurePieceType> GUMDROP_CAVE = register("GumdropCave", GoldStubCave::new);
    public static final RegistryObject<StructurePieceType> RUINED_PORTAL = register("GlowstoneRuins", GlowstoneRuinedPortalPiece::new);

    private static RegistryObject<StructurePieceType> register(String name, StructurePieceType structurePieceType) {
        return STRUCTURE_PIECE_TYPES.register(name.toLowerCase(Locale.ROOT), () -> structurePieceType);
    }
}
