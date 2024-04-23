package com.aetherteam.aether.client.gui.component.menu;

import io.github.fabricators_of_create.porting_lib.config.ModConfigSpec;
import io.github.fabricators_of_create.porting_lib.gui.utils.ModdedButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;

import org.jetbrains.annotations.Nullable;
import java.util.List;

/**
 * Button that determines whether it can display and how much to the left it should offset depending on whether certain config options are enabled.
 */
public class DynamicMenuButton extends ModdedButton {
    private final int originX;
    private List<ModConfigSpec.ConfigValue<Boolean>> displayConfigs;
    private List<ModConfigSpec.ConfigValue<Boolean>> offsetConfigs;
    public boolean enabled = true;

    public DynamicMenuButton(Builder builder) {
        super(builder.createNarration(DEFAULT_NARRATION));
        this.originX = this.getX();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (this.shouldRender()) {
            this.enabled = true;
            this.setX(this.getOriginX() + gatherOffsets(this.offsetConfigs));
            super.render(guiGraphics, mouseX, mouseY, partialTicks);
        } else {
            this.enabled = false;
        }
    }

    private boolean shouldRender() {
        for (ModConfigSpec.ConfigValue<Boolean> value : this.displayConfigs) {
            if (!value.get()) {
                return false;
            }
        }
        return true;
    }

    private int gatherOffsets(@Nullable List<ModConfigSpec.ConfigValue<Boolean>> configs) {
        int offset = 0;
        if (configs != null) {
            for (ModConfigSpec.ConfigValue<Boolean> value : configs) {
                if (value.get()) {
                    offset -= 24;
                }
            }
        }
        return offset;
    }

    @Override
    public void onPress() {
        if (this.enabled) {
            super.onPress();
        }
    }

    @SafeVarargs
    public final void setDisplayConfigs(ModConfigSpec.ConfigValue<Boolean>... displayConfigs) {
        this.displayConfigs = List.of(displayConfigs);
    }

    @SafeVarargs
    public final void setOffsetConfigs(ModConfigSpec.ConfigValue<Boolean>... offsetConfigs) {
        this.offsetConfigs = List.of(offsetConfigs);
    }

    public int getOriginX() {
        return this.originX;
    }
}
