package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.common.registry.AetherBlockEntityTypes;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.core.NonNullList;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin
{
    @SuppressWarnings("unchecked")
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;canBurn(Lnet/minecraft/world/item/crafting/Recipe;Lnet/minecraft/core/NonNullList;I)Z", shift = At.Shift.AFTER), method = "burn", cancellable = true)
    private void burn(@Nullable Recipe<?> recipe, @Nonnull NonNullList<ItemStack> stacks, int p_155029_, CallbackInfoReturnable<Boolean> cir) {
        AbstractFurnaceBlockEntity blockEntity = (AbstractFurnaceBlockEntity) (Object) this;
        if (recipe != null && (blockEntity.getType() == AetherBlockEntityTypes.ALTAR.get() || blockEntity.getType() == AetherBlockEntityTypes.FREEZER.get())) {
            ItemStack itemStack = stacks.get(0);
            ItemStack itemStack1 = ((Recipe<WorldlyContainer>) recipe).assemble(blockEntity);
            ItemStack itemStack2 = stacks.get(2);

            if (itemStack.is(itemStack1.getItem()) || itemStack1.is(AetherTags.Items.SAVE_NBT_IN_RECIPE)) {
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(itemStack), itemStack1);
                if (itemStack.hasTag()) {
                    itemStack1.setTag(itemStack.getTag());
                }
            }
            if (itemStack.is(itemStack1.getItem())) {
                itemStack1.setDamageValue(0);
            }

            if (itemStack2.isEmpty()) {
                stacks.set(2, itemStack1.copy());
            } else if (itemStack2.is(itemStack1.getItem())) {
                itemStack2.grow(itemStack1.getCount());
            }

            itemStack.shrink(1);
            cir.setReturnValue(true);
        }
    }
}
