package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.toasts.AdvancementToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AdvancementToast.class)
public abstract class AdvancementToastMixin
{
    @Final
    @Shadow
    private Advancement advancement;
    @Shadow
    private boolean playedSound;

    /**
     * {@link AdvancementToast#render(MatrixStack, ToastGui, long)}
     * Injector mixin to play the Aether's advancement sounds when the player gets an Aether advancement.
     */
    @Inject(at = @At("HEAD"), method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/gui/toasts/ToastGui;J)Lnet/minecraft/client/gui/toasts/IToast$Visibility;")
    private void onRender(MatrixStack p_230444_1_, ToastGui p_230444_2_, long p_230444_3_, CallbackInfoReturnable<IToast.Visibility> cir) {
        if (!this.playedSound) {
            ResourceLocation enterAether = new ResourceLocation(Aether.MODID, "enter_aether");
            if (advancement.getId().equals(enterAether) || (advancement.getParent() != null && advancement.getParent().getId().equals(enterAether))) {
                this.playedSound = true;
                switch (advancement.getId().getPath()) {
                    case "like_a_bossaru":
                        p_230444_2_.getMinecraft().getSoundManager().play(SimpleSound.forUI(AetherSoundEvents.UI_TOAST_AETHER_BRONZE.get(), 1.0F, 1.0F));
                        break;
                    case "dethroned":
                        p_230444_2_.getMinecraft().getSoundManager().play(SimpleSound.forUI(AetherSoundEvents.UI_TOAST_AETHER_SILVER.get(), 1.0F, 1.0F));
                        break;
                    default:
                        p_230444_2_.getMinecraft().getSoundManager().play(SimpleSound.forUI(AetherSoundEvents.UI_TOAST_AETHER_GENERAL.get(), 1.0F, 1.0F));
                        break;
                }
            }
        }
    }
}
