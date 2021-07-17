package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WorkingScreen;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorkingScreen.class)
public class WorkingScreenMixin
{
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(Lcom/mojang/blaze3d/matrix/MatrixStack;IIF)V"), method = "render", cancellable = true)
    private void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_, CallbackInfo ci) {
        WorkingScreen screen = (WorkingScreen) (Object) this;
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            RegistryKey<World> dimension = Minecraft.getInstance().player.level.dimension();
            if (dimension == AetherDimensions.AETHER_WORLD) {
                Screen.drawCenteredString(p_230430_1_, screen.getMinecraft().font, new TranslationTextComponent("gui.aether.descending"), screen.width / 2, 50, 16777215);
            } else {
                IAetherPlayer.get(Minecraft.getInstance().player).ifPresent(aetherPlayer -> {
                    if (aetherPlayer.isInPortal()) {
                        Screen.drawCenteredString(p_230430_1_, screen.getMinecraft().font, new TranslationTextComponent("gui.aether.ascending"), screen.width / 2, 50, 16777215);
                    }
                });
            }
        }
    }
}
