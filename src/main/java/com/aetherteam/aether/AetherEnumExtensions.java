package com.aetherteam.aether;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.List;
import java.util.function.Supplier;

public class AetherEnumExtensions {
    public static final EnumProxy<Rarity> AETHER_LOOT_RARITY_PROXY = new EnumProxy<>(
        Rarity.class, -1, "aether:loot", ChatFormatting.GREEN
    );

    public static Object skyrootBoatType(int idx, Class<?> type) {
        if (idx == 5) // Lazy away around boxing the boolean
            return false;
        return type.cast(switch (idx) {
            case 0 -> (Supplier<Block>) AetherBlocks.SKYROOT_PLANKS;
            case 1 -> (String) "aether:skyroot";
            case 2 -> (Supplier<Item>) AetherItems.SKYROOT_BOAT;
            case 3 -> (Supplier<Item>) AetherItems.SKYROOT_CHEST_BOAT;
            case 4 -> (Supplier<Item>) AetherItems.SKYROOT_STICK;
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object enchantingSearchIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(Items.COMPASS));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object enchantingFoodIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(AetherItems.ENCHANTED_BERRY.get()));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object enchantingBlocksIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(AetherBlocks.ENCHANTED_GRAVITITE.get()));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object enchantingMiscIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(AetherItems.SKYROOT_REMEDY_BUCKET.get()));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object enchantingRepairIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(AetherItems.ZANITE_PICKAXE.get()));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object freezableSearchIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(Items.COMPASS));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object freezableBlocksIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(AetherBlocks.BLUE_AERCLOUD.get()));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object freezableMiscIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(AetherItems.ICE_RING.get()));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object incubationSearchIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(Items.COMPASS));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object incubationMiscIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(AetherItems.BLUE_MOA_EGG.get()));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    private static String prefix(String id) {
        return Aether.MODID + ":" + id;
    }
}
