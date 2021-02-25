package com.gildedgames.aether.registry;

import com.gildedgames.aether.advancement.MountTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class AetherAdvancement {
    public static final MountTrigger MOUNT_ENTITY = CriteriaTriggers.register(new MountTrigger());

    public static void init() {
        //This is here to make sure the class gets loaded during setup.
    }
}
