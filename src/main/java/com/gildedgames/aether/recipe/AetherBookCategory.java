package com.gildedgames.aether.recipe;

import net.minecraft.util.StringRepresentable;

public enum AetherBookCategory implements StringRepresentable {
    ENCHANTING_FOOD("enchanting_food"),
    ENCHANTING_BLOCKS("enchanting_blocks"),
    ENCHANTING_MISC("enchanting_misc"),
    ENCHANTING_REPAIR("enchanting_repair"),
    FREEZABLE_BLOCKS("freezable_blocks"),
    FREEZABLE_MISC("freezable_misc"),
    UNKNOWN("unknown");

    public static final StringRepresentable.EnumCodec<AetherBookCategory> CODEC = StringRepresentable.fromEnum(AetherBookCategory::values);
    private final String name;

    AetherBookCategory(String name) {
        this.name = name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
