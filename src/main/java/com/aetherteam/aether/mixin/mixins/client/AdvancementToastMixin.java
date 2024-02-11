package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.AetherAdvancementSoundOverrides;
import com.aetherteam.aether.api.registers.AdvancementSoundOverride;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.EggItem;
import net.minecraftforge.registries.RegistryObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(AdvancementToast.class)
public class AdvancementToastMixin {
    @Final
    @Shadow
    private Advancement advancement;
    @Shadow
    private boolean playedSound;

    /**
     * Plays the Aether's advancement sounds when the player gets an Aether advancement.
     * @param guiGraphics The {@link GuiGraphics} for rendering.
     * @param toastComponent The {@link ToastComponent} for rendering.
     * @param timeSinceLastVisible The {@link Long} time since the toast was last visible.
     * @param cir The {@link net.minecraft.client.gui.components.toasts.Toast.Visibility} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At(value = "FIELD", target = "net/minecraft/client/gui/components/toasts/AdvancementToast.playedSound:Z"), method = "render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/components/toasts/ToastComponent;J)Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;")
    private void render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeSinceLastVisible, CallbackInfoReturnable<Toast.Visibility> cir) {
        if (!this.playedSound) { // Checks if a sound hasn't been played yet.
            if (this.checkRoot()) {
                this.playedSound = true;
                SoundEvent sound = AetherSoundEvents.UI_TOAST_AETHER_GENERAL.get();
                // Plays whatever sound matches based on the overrides
                for (RegistryObject<AdvancementSoundOverride> override : AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDES.getEntries()) {
                    if (override.get().matches(this.advancement.getId().getPath())) {
                        sound = override.get().getSound();
                    }
                }
                toastComponent.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(sound, 1.0F, 1.0F));
            }
        }
    }

    /**
     * Checks all the way up to the root of the advancement tree to determine if it's an Aether advancement.
     */
    private boolean checkRoot() {
        ResourceLocation enterAether = new ResourceLocation(Aether.MODID, "enter_aether");
        for (Advancement advancement = this.advancement; advancement != null; advancement = advancement.getParent()) {
            if (advancement.getId().equals(enterAether)) {
                return true;
            }
        }
        return false;
    }
}
