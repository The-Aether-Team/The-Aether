package com.aetherteam.aether.client.gui.component;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

public class Builder {
    public final Component message;
    public final Button.OnPress onPress;
    public Button.OnTooltip tooltip = Button.NO_TOOLTIP;
    public int x;
    public int y;
    public int width = 150;
    public int height = 20;

    public Builder(Component message, Button.OnPress onPress) {
        this.message = message;
        this.onPress = onPress;
    }

    public Builder pos(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Builder width(int width) {
        this.width = width;
        return this;
    }

    public Builder size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Builder bounds(int x, int y, int width, int height) {
        return this.pos(x, y).size(width, height);
    }

    public Builder tooltip(Button.OnTooltip tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public Button build() {
        return build((builder) -> new Button(builder.x, builder.y, builder.width, builder.height, builder.message, builder.onPress, builder.tooltip));
    }

    public Button build(Function<Builder, Button> builder) {
        return builder.apply(this);
    }
}
