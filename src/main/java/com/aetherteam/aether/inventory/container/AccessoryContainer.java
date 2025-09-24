package com.aetherteam.aether.inventory.container;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Collection;
import java.util.List;

public class AccessoryContainer extends SimpleContainer {
    public static final Codec<AccessoryContainer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ItemStack.OPTIONAL_CODEC.sizeLimitedListOf(4).fieldOf("items").forGetter(container -> container.lastItems)
    ).apply(instance, AccessoryContainer::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AccessoryContainer> STREAM_CODEC = StreamCodec.composite(
        ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list(4)), (container) -> container.lastItems,
        AccessoryContainer::new);

    private final NonNullList<ItemStack> lastItems;

    protected AccessoryContainer(List<ItemStack> lastItems) {
        super(4);
        this.lastItems = NonNullList.copyOf(lastItems);
    }

    public AccessoryContainer() {
        super(4);
        this.lastItems = NonNullList.withSize(4, ItemStack.EMPTY);
    }

    public void postTickUpdate(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            if (!this.lastItems.equals(this.getItems())) {
                this.lastItems.clear();
                for (int i = 0; i < this.getItems().size(); i++) {
                    this.lastItems.set(i, this.getItem(i));
                }
            }
        }
    }

    public void dropItems(LivingEntity entity, Collection<ItemEntity> drops) {
        NonNullList<ItemStack> items = this.getItems();
        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.get(i);
            if (!stack.isEmpty()) {
                if (!EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) {
                    ItemEntity itemEntity = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack);
                    itemEntity.setDefaultPickUpDelay();
                    drops.add(itemEntity);
                }
                this.setItem(i, ItemStack.EMPTY);
            }
        }
    }

    public enum SlotType {
        PENDANT(new int[] { 0 }),
        RING(new int[] { 1, 3 }),
        CAPE(new int[] { 2 }),
        SHIELD(new int[] { 4 }),
        GLOVES(new int[] { 5 }),
        ACCESSORY(new int[] { 6, 7 });

        private final int[] index;

        SlotType(int[] index) {
            this.index = index;
        }

        public int[] getIndex() {
            return this.index;
        }
    }
}
