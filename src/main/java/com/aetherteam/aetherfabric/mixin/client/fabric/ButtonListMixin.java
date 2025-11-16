package com.aetherteam.aetherfabric.mixin.client.fabric;

import com.aetherteam.aetherfabric.pond.client.ButtonListExtension;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.fabric.impl.client.screen.ButtonList;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ButtonList.class)
public abstract class ButtonListMixin implements ButtonListExtension {
    private Screen aetherFabric$screen = null;

    @Override
    public void setScreen(Screen screen) {
        this.aetherFabric$screen = screen;
    }

    @WrapMethod(method = "set(ILnet/minecraft/client/gui/components/AbstractWidget;)Lnet/minecraft/client/gui/components/AbstractWidget;")
    private AbstractWidget aetherFabric$hookButtonAdd1(int index, AbstractWidget element, Operation<AbstractWidget> original) {
        if (aetherFabric$screen != null) {
            var newElement = aetherFabric$screen.onScreensWidgetAdd(element);

            if (newElement == null) return element;

            element = newElement;
        }

        return original.call(index, element);
    }

    @WrapMethod(method = "add(ILnet/minecraft/client/gui/components/AbstractWidget;)V")
    private void aetherFabric$hookButtonAdd2(int index, AbstractWidget element, Operation<AbstractWidget> original) {
        if (aetherFabric$screen != null) {
            var newElement = aetherFabric$screen.onScreensWidgetAdd(element);

            if (newElement == null) return;

            element = newElement;
        }

        original.call(index, element);
    }
}
