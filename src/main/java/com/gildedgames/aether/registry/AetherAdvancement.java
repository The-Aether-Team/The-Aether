package com.gildedgames.aether.registry;

import com.gildedgames.aether.advancement.LoreTrigger;
import com.gildedgames.aether.advancement.MountTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class AetherAdvancement
{
    public static final MountTrigger MOUNT_ENTITY = CriteriaTriggers.register(new MountTrigger());
    public static final LoreTrigger LORE_ENTRY = CriteriaTriggers.register(new LoreTrigger());

    public static void init() {
        //This is here to make sure the class gets loaded during setup.
    }
}
