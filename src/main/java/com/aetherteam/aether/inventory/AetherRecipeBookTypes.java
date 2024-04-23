package com.aetherteam.aether.inventory;

import com.mojang.datafixers.util.Pair;
import me.shedaniel.mm.api.ClassTinkerers;
import net.minecraft.stats.RecipeBookSettings;
import net.minecraft.world.inventory.RecipeBookType;

import java.util.HashMap;

public class AetherRecipeBookTypes {
    public static final RecipeBookType ALTAR = ClassTinkerers.getEnum(RecipeBookType.class, "ALTAR");
    public static final RecipeBookType FREEZER = ClassTinkerers.getEnum(RecipeBookType.class, "FREEZER");
    public static final RecipeBookType INCUBATOR = ClassTinkerers.getEnum(RecipeBookType.class, "INCUBATOR");

    public static void init() {
        RecipeBookSettings.TAG_FIELDS = new HashMap<>(RecipeBookSettings.TAG_FIELDS);
        addTagsForTypes(ALTAR, FREEZER, INCUBATOR);
    }

    public static void addTagsForTypes(RecipeBookType ...types) {
        for (RecipeBookType type : types) {
            String name = type.name().toLowerCase(java.util.Locale.ROOT).replace("_", "");
            RecipeBookSettings.TAG_FIELDS.put(type, Pair.of("is" + name + "GuiOpen", "is" + name + "FilteringCraftable"));
        }
    }
}
