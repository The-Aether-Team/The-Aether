package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.client.gui.screen.menu.CustomBranding;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiConsumer;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @WrapOperation(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/internal/BrandingControl;forEachLine(ZZLjava/util/function/BiConsumer;)V"))
    private void forEachLine(boolean includeMC, boolean reverse, BiConsumer<Integer, String> lineConsumer, Operation<Void> original, @Local(argsOnly = true) GuiGraphics guiGraphics, @Local(ordinal = 2) int i) {
        TitleScreen titleScreen = (TitleScreen) (Object) this;
        if (!(titleScreen instanceof CustomBranding customBranding) || !customBranding.forEachLineBranding(includeMC, reverse, lineConsumer, guiGraphics, i)) {
            original.call(includeMC, reverse, lineConsumer);
        }
    }

    @WrapOperation(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/internal/BrandingControl;forEachAboveCopyrightLine(Ljava/util/function/BiConsumer;)V"))
    private void forEachLine(BiConsumer<Integer, String> lineConsumer, Operation<Void> original, @Local(argsOnly = true) GuiGraphics guiGraphics, @Local(ordinal = 2) int i) {
        TitleScreen titleScreen = (TitleScreen) (Object) this;
        if (!(titleScreen instanceof CustomBranding customBranding) || !customBranding.forEachAboveCopyrightLineBranding(lineConsumer, guiGraphics, i)) {
            original.call(lineConsumer);
        }
    }

    /**
     * Used by the world preview system.<br>
     * Sets the {@link TitleScreen} to pause the game when the world preview is active.
     * @param cir The {@link Boolean} {@link CallbackInfoReturnable} used for the method's return value.
     * @see WorldDisplayHelper#isActive()
     */
    @Inject(at = @At(value = "HEAD"), method = "isPauseScreen()Z", cancellable = true)
    public void isPauseScreen(CallbackInfoReturnable<Boolean> cir) {
        if (WorldDisplayHelper.isActive()) {
            cir.setReturnValue(true);
        }
    }
}
