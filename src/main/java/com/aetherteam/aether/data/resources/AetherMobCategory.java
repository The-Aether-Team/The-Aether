package com.aetherteam.aether.data.resources;

import net.minecraft.world.entity.MobCategory;

public class AetherMobCategory {
    public static final MobCategory AETHER_SURFACE_MONSTER = MobCategory.create("AETHER_SURFACE_MONSTER", "aether_surface_monster", 15, false, false, 128);
    public static final MobCategory AETHER_DARKNESS_MONSTER = MobCategory.create("AETHER_DARKNESS_MONSTER", "aether_darkness_monster", 5, false, false, 128);
    public static final MobCategory AETHER_SKY_MONSTER = MobCategory.create("AETHER_SKY_MONSTER", "aether_sky_monster", 4, false, false, 128);
    public static final MobCategory AETHER_AERWHALE = MobCategory.create("AETHER_AERWHALE", "aether_aerwhale", 1, true, false, 128);
}
