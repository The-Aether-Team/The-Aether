package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.ItemAttributeModifierHelper;
import com.aetherteam.aetherfabric.pond.ItemStackExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Set;
import java.util.function.BiConsumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackExtension {
    @WrapOperation(
        method = "forEachModifier(Lnet/minecraft/world/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/ItemAttributeModifiers;forEach(Lnet/minecraft/world/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V")
    )
    private void aetherFabric$modifyAttributeEvent_1(ItemAttributeModifiers instance, EquipmentSlot equipmentSlot, BiConsumer<Holder<Attribute>, AttributeModifier> action, Operation<Void> original) {
        var event = ItemAttributeModifierHelper.invokeEvent((ItemStack) (Object) this, instance);

        instance = new ItemAttributeModifiers(event.getModifiers(), instance.showInTooltip());

        original.call(instance, equipmentSlot, action);
    }

    @WrapOperation(
        method = "forEachModifier(Lnet/minecraft/world/entity/EquipmentSlotGroup;Ljava/util/function/BiConsumer;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/ItemAttributeModifiers;forEach(Lnet/minecraft/world/entity/EquipmentSlotGroup;Ljava/util/function/BiConsumer;)V")
    )
    private void aetherFabric$modifyAttributeEvent_2(ItemAttributeModifiers instance, EquipmentSlotGroup slotGroup, BiConsumer<Holder<Attribute>, AttributeModifier> action, Operation<Void> original) {
        var event = ItemAttributeModifierHelper.invokeEvent((ItemStack) (Object) this, instance);

        instance = new ItemAttributeModifiers(event.getModifiers(), instance.showInTooltip());

        original.call(instance, slotGroup, action);
    }

    @Override
    public int aetherFabric$getEnchantmentLevel(Holder<Enchantment> enchantment) {
        return ((ItemStack) (Object) this).getEnchantments().getLevel(enchantment);
    }

    @Override
    public Set<Object2IntMap.Entry<Holder<Enchantment>>> aetherFabric$getAllEnchantments(HolderLookup.RegistryLookup<Enchantment> registry) {
        return ((ItemStack) (Object) this).getEnchantments().entrySet();
    }
}
