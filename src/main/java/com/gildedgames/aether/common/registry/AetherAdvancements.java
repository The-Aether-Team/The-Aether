package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.common.advancement.LoreTrigger;
import com.gildedgames.aether.common.advancement.MountTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class AetherAdvancements
{
	public static final MountTrigger MOUNT_ENTITY = CriteriaTriggers.register(new MountTrigger());
	public static final LoreTrigger LORE_ENTRY = CriteriaTriggers.register(new LoreTrigger());

	public static void init() {
		// This is here to make sure the class gets loaded during setup.
	}
}
