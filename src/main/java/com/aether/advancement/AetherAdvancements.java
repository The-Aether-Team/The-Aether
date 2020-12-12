package com.aether.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public class AetherAdvancements {
    public static final MountTrigger MOUNT_ENTITY = CriteriaTriggers.register(new MountTrigger());

    public static void init() {
        //This is here to make sure the class gets loaded during setup.
    }
}
