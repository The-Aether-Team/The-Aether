package com.gildedgames.aether.mixin.mixins.client;

import net.minecraft.client.resources.SplashManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Calendar;
import java.util.Date;

@Mixin(SplashManager.class)
public class SplashManagerMixin
{
    @Inject(at = @At("HEAD"), method = "getSplash", cancellable = true)
    private void getSplash(CallbackInfoReturnable<String> cir) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(Calendar.MONTH) + 1 == 7 && calendar.get(Calendar.DATE) == 22) {
            cir.setReturnValue("Happy anniversary to the Aether!");
        }
    }
}
