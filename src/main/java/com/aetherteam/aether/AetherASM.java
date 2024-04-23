package com.aetherteam.aether;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import io.github.fabricators_of_create.porting_lib.asm.ASMUtils;
import me.shedaniel.mm.api.ClassTinkerers;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AetherASM implements Runnable {
    @Override
    public void run() {
        ClassTinkerers.enumBuilder(mapC("class_5421"))
                .addEnum("ALTAR")
                .addEnum("FREEZER")
                .addEnum("INCUBATOR")
                .build();
        ClassTinkerers.enumBuilder(mapC("class_1814"), "L" + mapC("class_124") + ";") // Rarity // ChatFormatting
                .addEnum("aether_loot", () -> new Object[] { ChatFormatting.GREEN })
                .build();
        ClassTinkerers.enumBuilder(mapC("class_314"), "[L" + mapC("class_1799") + ";")
                .addEnum("ENCHANTING_SEARCH", () -> new Object[] { new ItemStack[] {new ItemStack(Items.COMPASS) }})
                .addEnum("ENCHANTING_FOOD", () -> new Object[] { new ItemStack[] {new ItemStack(AetherItems.ENCHANTED_BERRY.get()) }})
                .addEnum("ENCHANTING_BLOCKS", () -> new Object[] { new ItemStack[] {new ItemStack(AetherBlocks.ENCHANTED_GRAVITITE.get()) }})
                .addEnum("ENCHANTING_MISC", () -> new Object[] { new ItemStack[] {new ItemStack(AetherItems.SKYROOT_REMEDY_BUCKET.get()) }})
                .addEnum("ENCHANTING_REPAIR", () -> new Object[] { new ItemStack[] {new ItemStack(AetherItems.ZANITE_PICKAXE.get()) }})

                .addEnum("FREEZABLE_SEARCH", () -> new Object[] { new ItemStack[] {new ItemStack(Items.COMPASS) }})
                .addEnum("FREEZABLE_BLOCKS", () -> new Object[] { new ItemStack[] {new ItemStack(AetherBlocks.BLUE_AERCLOUD.get()) }})
                .addEnum("FREEZABLE_MISC", () -> new Object[] { new ItemStack[] {new ItemStack(AetherItems.ICE_RING.get()) }})

                .addEnum("INCUBATION_SEARCH", () -> new Object[] { new ItemStack[] {new ItemStack(Items.COMPASS) }})
                .addEnum("INCUBATION_MISC", () -> new Object[] { new ItemStack[] {new ItemStack(AetherItems.BLUE_MOA_EGG.get()) }})
                .build();

        ClassTinkerers.enumBuilder(mapC("class_7"), float.class) // BlockPathTypes
                .addEnum("BOSS_DOORWAY", -1.0F)
                .build(); // BlockPathTypes

        ClassTinkerers.enumBuilder(mapC("class_1311"), String.class, int.class, boolean.class, boolean.class, int.class) // MobCategory
                .addEnum("AETHER_SURFACE_MONSTER", "aether_surface_monster", 15, false, false, 128)
                .addEnum("AETHER_DARKNESS_MONSTER", "aether_darkness_monster", 5, false, false, 128)
                .addEnum("AETHER_SKY_MONSTER", "aether_sky_monster", 4, false, false, 128)
                .addEnum("AETHER_AERWHALE", "aether_aerwhale", 1, true, false, 128)
                .build(); // MobCategory
    }

    public static String mapC(String intermediaryName) {
        return ASMUtils.mapC(intermediaryName).replace('.', '/');
    }
}
