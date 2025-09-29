package com.aetherteam.aether.integration;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.inventory.container.AccessoryContainer;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.client.event.GatherSkippedAttributeTooltipsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.common.util.AttributeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AccessoryUtil {
    public static ItemStack getInFirstSlot(LivingEntity livingEntity, AccessoryContainer.SlotType slot) {
        return getInSlot(livingEntity, slot, 0);
    }

    public static boolean hasInSlot(LivingEntity livingEntity, AccessoryContainer.SlotType slot) {
        return !getInSlot(livingEntity, slot).isEmpty();
    }

    public static ItemStack getInSlot(LivingEntity livingEntity, AccessoryContainer.SlotType slot, int index) {
        List<ItemStack> itemStacks = getInSlot(livingEntity, slot);
        return itemStacks.isEmpty() ? ItemStack.EMPTY : itemStacks.get(index);
    }

    public static List<ItemStack> getInSlot(LivingEntity livingEntity, AccessoryContainer.SlotType slot) {
        AccessoryContainer container = livingEntity.getData(AetherDataAttachments.ACCESSORIES);
        List<ItemStack> items = new ArrayList<>();
        for (int i : slot.getIndex()) {
            ItemStack itemStack = container.getItem(i);
            if (!itemStack.isEmpty()) {
                items.add(itemStack);
            }
        }
        return items;
    }

    public static ItemStack getFirstItem(LivingEntity livingEntity, Predicate<ItemStack> predicate) {
        return getItems(livingEntity, predicate).get(0);
    }

    public static boolean hasItem(LivingEntity livingEntity, Predicate<ItemStack> predicate) {
        return !getItems(livingEntity, predicate).isEmpty();
    }

    public static List<ItemStack> getItems(LivingEntity livingEntity, Predicate<ItemStack> predicate) {
        AccessoryContainer container = livingEntity.getData(AetherDataAttachments.ACCESSORIES);
        List<ItemStack> items = new ArrayList<>();
        for (ItemStack itemStack : container.getItems()) {
            if (predicate.test(itemStack)) {
                items.add(itemStack);
            }
        }
        return items;
    }

    public static boolean setItemBySlot(LivingEntity livingEntity, ItemStack stack, AccessoryContainer.SlotType slot) {
        AccessoryContainer container = livingEntity.getData(AetherDataAttachments.ACCESSORIES);
        for (int i : slot.getIndex()) {
            ItemStack itemStack = container.getItem(i);
            if (itemStack.isEmpty()) {
                container.setItem(i, stack);
                return true;
            }
        }
        return false;
    }

    public static InteractionResult equip(Player player, ItemStack equipStack, AccessoryContainer.SlotType slot) {
        AccessoryContainer container = player.getData(AetherDataAttachments.ACCESSORIES);
        int index = getValidSlot(player, slot);
        ItemStack containerStack = container.getItem(index);
        if ((!EnchantmentHelper.has(containerStack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE) || player.isCreative()) && !ItemStack.isSameItemSameComponents(equipStack, containerStack)) {
            if (!player.level().isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(equipStack.getItem()));
            }
            if (equipStack.getCount() <= 1) {
                ItemStack resultStack = containerStack.isEmpty() ? equipStack : containerStack.copyAndClear();
                ItemStack insertedStack = player.isCreative() ? equipStack.copy() : equipStack.copyAndClear();
                container.setItem(index, insertedStack);
                return InteractionResult.SUCCESS.heldItemTransformedTo(resultStack);
            } else {
                ItemStack copiedStack = containerStack.copyAndClear();
                ItemStack insertedStack = equipStack.consumeAndReturn(1, player);
                container.setItem(index, insertedStack);
                if (!player.getInventory().add(copiedStack)) {
                    player.drop(copiedStack, false);
                }
                return InteractionResult.SUCCESS.heldItemTransformedTo(equipStack);
            }
        } else {
            return InteractionResult.FAIL;
        }
    }

    private static int getValidSlot(Player player, AccessoryContainer.SlotType slot) {
        int firstEmptyIndex = -1;
        int firstFullIndex = -1;
        for (int i : slot.getIndex()) {
            if (firstEmptyIndex < 0 && getInSlot(player, slot).isEmpty()) {
                firstEmptyIndex = i;
            }
            if (firstFullIndex < 0 && !getInSlot(player, slot).isEmpty()) {
                firstFullIndex = i;
            }
        }
        if (firstEmptyIndex >= 0) {
            return firstEmptyIndex;
        } else {
            return firstFullIndex;
        }
    }

    public static void addAttributeTooltips(ItemStack stack, Consumer<Component> tooltip, AttributeTooltipContext ctx, Multimap<Holder<Attribute>, AttributeModifier> modifiers, String group) {
        var event = NeoForge.EVENT_BUS.post(new GatherSkippedAttributeTooltipsEvent(stack, ctx));
        if (event.isSkippingAll()) {
            return;
        }

        // Remove any skipped modifiers before doing any logic
        modifiers.values().removeIf(m -> event.isSkipped(m.id()));

        if (modifiers.isEmpty()) {
            return;
        }

        // Add an empty line, then the name of the group, then the modifiers.
        tooltip.accept(Component.empty());
        tooltip.accept(Component.translatable("item.modifiers." + group).withStyle(ChatFormatting.GRAY));

        AttributeUtil.applyTextFor(stack, tooltip, modifiers, ctx);
    }
}
