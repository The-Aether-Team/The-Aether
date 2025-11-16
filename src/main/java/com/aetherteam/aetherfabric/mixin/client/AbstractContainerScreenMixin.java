package com.aetherteam.aetherfabric.mixin.client;

import com.aetherteam.aetherfabric.pond.client.ScreenExtension;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin implements ScreenExtension {
    @Shadow
    protected int leftPos;

    @Shadow
    protected int topPos;

    @Shadow
    public int imageWidth;

    @Shadow
    public int imageHeight;

    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    @Override
    public int aetherFabric$getGuiLeft() {
        return this.leftPos;
    }

    @Override
    public int aetherFabric$getGuiTop() {
        return this.topPos;
    }

    @Override
    public int aetherFabric$getXSize() {
        return this.imageWidth;
    }

    @Override
    public int aetherFabric$getYSize() {
        return this.imageHeight;
    }

    @Override
    @Nullable
    public Slot aetherFabric$getSlotUnderMouse() {
        return this.hoveredSlot;
    }
}
