package com.gildedgames.aether.core.mixin.client;

import net.minecraft.world.storage.SaveFormat;
import org.spongepowered.asm.mixin.Mixin;

/**
 * this is done specifically so you can edit the world file
 * of the world that is loaded in the main menu.
 * Session locks should be fine to override as long as
 * it doesn't effect server files, which is why
 * the mixin is clientside.
 */
@Mixin(SaveFormat.LevelSave.class)
public class LevelSaveMixin {

    private void checkLock() {
        // levels are always going to be valid on the clientside.
        // on the serverside, levels can still be locked to prevent servers
        // from accidentally overwriting the wrong files.
        // shouldn't really matter too much clientside.
    }
}
