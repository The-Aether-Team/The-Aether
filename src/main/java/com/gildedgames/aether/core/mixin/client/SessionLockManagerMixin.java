package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.event.listeners.GuiListener;
import com.gildedgames.aether.client.gui.screen.menu.AetherMainMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.server.SessionLockManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;

/**
 * Session lock should be fine to override as long as it doesn't have any effect on servers,
 * which is why I made the mixin clientside only.
 * This mixin is specifically just to allow the world that's rendered in the title screen to also
 * show up in the world selection list
 */
@Mixin(SessionLockManager.class)
public class SessionLockManagerMixin {
    @Shadow
    void close() {}

    @Inject(at = @At(value = "RETURN"), method = "<init>", cancellable = true)
    private void constructor(FileChannel fc, FileLock l, CallbackInfo info) {
        close();
    }

    @Inject(at = @At(value = "HEAD"), method = "isLocked", cancellable = true)
    private static void isLocked(Path path, CallbackInfoReturnable<Boolean> info) {
        System.out.println(path.getFileName() + ":: " + AetherMainMenuScreen.menu_world);
        info.setReturnValue(false);
    }
}
