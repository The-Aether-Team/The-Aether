package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;

public class AetherStructurePieceTypes {
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, Aether.MODID);

    public static RegistryObject<StructurePieceType> LARGE_AERCLOUD = register("ALC", LargeAercloudPiece::new);
    public static final RegistryObject<StructurePieceType> BRONZE_BOSS_ROOM = register("BBossRoom", BronzeDungeonPieces.BossRoom::new);
    public static final RegistryObject<StructurePieceType> BRONZE_DUNGEON_ROOM = register("BDungeonRoom", BronzeDungeonPieces.DungeonRoom::new);
    public static final RegistryObject<StructurePieceType> BRONZE_TUNNEL = register("BTunnel", BronzeDungeonPieces.HolystoneTunnel::new);
    public static final RegistryObject<StructurePieceType> SILVER_TEMPLE_PIECE = register("STemplePiece", SilverDungeonPieces.TemplePiece::new);
    public static final RegistryObject<StructurePieceType> SILVER_FLOOR_PIECE = register("SFloorPiece", SilverDungeonPieces.FloorPiece::new);
    public static final RegistryObject<StructurePieceType> SILVER_DUNGEON_ROOM = register("SDungeonRoom", SilverDungeonPieces.DungeonRoom::new);
    public static final RegistryObject<StructurePieceType> SILVER_BOSS_ROOM = register("SBossRoom", SilverDungeonPieces.BossRoom::new);
    public static final RegistryObject<StructurePieceType> SILVER_BOSS_DETAIL = register("SBossDetail", SilverDungeonPieces.BossDetail::new);
    public static final RegistryObject<StructurePieceType> LEGACY_CLOUD_BED = register("LegacyCloudBed", SilverDungeonPieces.LegacyCloudBed::new);
    public static final RegistryObject<StructurePieceType> GOLD_BOSS_ROOM = register("GBossRoom", GoldDungeonPieces.BossRoom::new);
    public static final RegistryObject<StructurePieceType> GOLD_ISLAND = register("GIsland", GoldDungeonPieces.Island::new);
    public static final RegistryObject<StructurePieceType> GOLD_LEGACY_ISLAND = register("GLegacyIsland", GoldDungeonPieces.LegacyIslandPiece::new);
    public static final RegistryObject<StructurePieceType> GOLD_LEGACY_STUB = register("GLegacyStub", GoldDungeonPieces.LegacyStubPiece::new);
    public static final RegistryObject<StructurePieceType> GOLD_LEGACY_TUNNEL = register("GLegacyTunnel", GoldDungeonPieces.LegacyTunnelPiece::new);

    private static RegistryObject<StructurePieceType> register(String name, StructurePieceType structurePieceType) {
        return STRUCTURE_PIECE_TYPES.register(name.toLowerCase(Locale.ROOT), () -> structurePieceType);
    }
}
