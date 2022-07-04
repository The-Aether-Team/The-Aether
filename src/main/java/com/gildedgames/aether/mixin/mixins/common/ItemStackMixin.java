package com.gildedgames.aether.mixin.mixins.common;

import com.gildedgames.aether.item.combat.abilities.weapon.ZaniteWeapon;
import com.gildedgames.aether.item.tools.abilities.ValkyrieTool;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @ModifyVariable(method = "getAttributeModifiers", at = @At(value = "INVOKE",  target = "Lnet/minecraftforge/common/ForgeHooks;getAttributeModifiers(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;Lcom/google/common/collect/Multimap;)Lcom/google/common/collect/Multimap;", shift = At.Shift.BEFORE))
    private Multimap<Attribute, AttributeModifier> getAttributeModifiers(Multimap<Attribute, AttributeModifier> map, EquipmentSlot slot) {
        ItemStack itemStack = (ItemStack) (Object) this;
        map = ZaniteWeapon.increaseSpeed(map, itemStack, slot);
        map = ValkyrieTool.extendReachModifier(map, itemStack, slot);
        return map;
    }
}
