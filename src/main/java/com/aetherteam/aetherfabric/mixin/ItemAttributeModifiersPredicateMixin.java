package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.ItemAttributeModifierHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.advancements.critereon.ItemAttributeModifiersPredicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(ItemAttributeModifiersPredicate.class)
public abstract class ItemAttributeModifiersPredicateMixin {
    @WrapOperation(
        method = "matches(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/component/ItemAttributeModifiers;)Z",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/ItemAttributeModifiers;modifiers()Ljava/util/List;")
    )
    private List<ItemAttributeModifiers.Entry> aetherFabric$modifyAttributeEvent(ItemAttributeModifiers instance, Operation<List<ItemAttributeModifiers.Entry>> original, @Local(argsOnly = true) ItemStack stack) {
        var event = ItemAttributeModifierHelper.invokeEvent(stack, instance);

        instance = new ItemAttributeModifiers(event.getModifiers(), instance.showInTooltip());

        return original.call(instance);
    }
}
