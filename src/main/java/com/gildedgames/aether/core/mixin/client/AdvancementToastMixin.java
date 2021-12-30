package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AdvancementToast.class)
public class AdvancementToastMixin
{
    @Final
    @Shadow
    private Advancement advancement;
    @Shadow
    private boolean playedSound;

    /**
     * {@link AdvancementToast#render(PoseStack, ToastComponent, long)}
     * Injector mixin to play the Aether's advancement sounds when the player gets an Aether advancement.
     */
    @Inject(at = @At("HEAD"), method = "render")
    private void render(PoseStack poseStack, ToastComponent toastComponent, long timeSinceLastVisible, CallbackInfoReturnable<Toast.Visibility> cir) {
        if (!this.playedSound) {
            ResourceLocation enterAether = new ResourceLocation(Aether.MODID, "enter_aether");
            if (this.advancement.getId().equals(enterAether) || (this.advancement.getParent() != null && this.advancement.getParent().getId().equals(enterAether))) {
                this.playedSound = true;
                switch (this.advancement.getId().getPath()) {
                    case "like_a_bossaru" -> toastComponent.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(AetherSoundEvents.UI_TOAST_AETHER_BRONZE.get(), 1.0F, 1.0F));
                    case "dethroned" -> toastComponent.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(AetherSoundEvents.UI_TOAST_AETHER_SILVER.get(), 1.0F, 1.0F));
                    default -> toastComponent.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(AetherSoundEvents.UI_TOAST_AETHER_GENERAL.get(), 1.0F, 1.0F));
                }
            }
        }
    }
}
