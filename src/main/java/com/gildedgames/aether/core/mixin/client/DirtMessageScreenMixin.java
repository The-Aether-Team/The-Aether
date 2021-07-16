package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.util.TriviaReader;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DirtMessageScreen.class)
public class DirtMessageScreenMixin
{
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(Lcom/mojang/blaze3d/matrix/MatrixStack;IIF)V"), method = "render", cancellable = true)
    private void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_, CallbackInfo ci) {
        DirtMessageScreen screen = (DirtMessageScreen) (Object) this;
        ITextComponent triviaLine = TriviaReader.getTriviaLine();
        if (triviaLine != null && !screen.getTitle().equals(new TranslationTextComponent("menu.savingLevel")) && AetherConfig.CLIENT.enable_trivia.get()) {
            Screen.drawCenteredString(p_230430_1_, screen.getMinecraft().font, triviaLine, screen.width / 2, screen.height - 16, 16777113);
        }
    }
}
