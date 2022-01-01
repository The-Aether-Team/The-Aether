package com.gildedgames.aether.common.world.structure;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;

import java.util.Locale;

public class AetherStructurePieces {
    public static final StructurePieceType BURYING_JIGSAW_PIECE = register(BuryingJigsawPiece::new, "burying_jigsaw");
    public static final StructurePieceType EMPTY_BEARDED_PIECE = register(BeardedPiece::new, "empty_bearded");

    public static void init() {

    }

    public static StructurePieceType register(StructurePieceType type, String name) {
        return Registry.register(Registry.STRUCTURE_PIECE, name.toLowerCase(Locale.ROOT), type);
    }
}
