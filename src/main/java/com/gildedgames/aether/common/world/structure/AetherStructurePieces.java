package com.gildedgames.aether.common.world.structure;

import com.gildedgames.aether.common.world.structure.bronze.HolystoneTunnel;
import com.gildedgames.aether.common.world.structure.bronze.SliderBossRoom;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;

import java.util.Locale;

public class AetherStructurePieces {
    public static final StructurePieceType SLIDER_BOSS_ROOM = register(SliderBossRoom::new, "slider_boss_room");
    public static final StructurePieceType HOLYSTONE_TUNNEL = register(HolystoneTunnel::new, "buried_box");

    @Deprecated
    public static final StructurePieceType BURYING_JIGSAW_PIECE = register(BuryingJigsawPiece::new, "burying_jigsaw");

    public static void init() {
    }

    public static StructurePieceType register(StructurePieceType type, String name) {
        return Registry.register(Registry.STRUCTURE_PIECE, name.toLowerCase(Locale.ROOT), type);
    }
}
