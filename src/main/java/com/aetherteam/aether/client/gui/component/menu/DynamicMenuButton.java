package com.aetherteam.aether.client.gui.component.menu;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class DynamicMenuButton extends Button {
    private final int originX;
    private List<ForgeConfigSpec.ConfigValue<Boolean>> displayConfigs;
    private List<ForgeConfigSpec.ConfigValue<Boolean>> offsetConfigs;
    public boolean enabled = true;

    public DynamicMenuButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.originX = x;
    }

    @Override
    public void onPress() {
        if (this.enabled) {
            super.onPress();
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        if (this.shouldRender()) {
            this.enabled = true;
            this.setX(this.getOriginX() + gatherOffsets(this.offsetConfigs));
            super.render(poseStack, mouseX, mouseY, partialTicks);
        } else {
            this.enabled = false;
        }
    }

    public int gatherOffsets(List<ForgeConfigSpec.ConfigValue<Boolean>> configs) {
        int offset = 0;
        if (configs != null) {
            for (ForgeConfigSpec.ConfigValue<Boolean> value : configs) {
                if (value.get()) {
                    offset -= 24;
                }
            }
        }
        return offset;
    }

    public boolean shouldRender() {
        for (ForgeConfigSpec.ConfigValue<Boolean> value : this.displayConfigs) {
            if (!value.get()) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    public final void setDisplayConfigs(ForgeConfigSpec.ConfigValue<Boolean>... displayConfigs) {
        this.displayConfigs = List.of(displayConfigs);
    }

    @SafeVarargs
    public final void setOffsetConfigs(ForgeConfigSpec.ConfigValue<Boolean>... offsetConfigs) {
        this.offsetConfigs = List.of(offsetConfigs);
    }

    public int getOriginX() {
        return this.originX;
    }
}
